package com.app.hos.jdbc.dbcp.pool;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GenericObjectPool<T> implements ObjectPool<T> {

	private volatile boolean blockWhenExhausted = true; 
	
	private volatile long borrowMaxWaitMillis = -1L;
	
	private volatile int maxIdle;
	
	private volatile int minIdle;
	
	private volatile int localMaxTotal;
	
	private final PooledObjectFactory<T> factory;
	
	private final Map<IdentityWrapper<T>,PooledObject<T>> allObjects;
	
	private final LinkedBlockingDeque<PooledObject<T>> idleObjects;
	
	private AtomicInteger totalCreatedCount;
	
	private long makingObjectCount;
	
	private final Object evictionLock = new Object();
	
	private final Object createObjectLock = new Object();
	
	private volatile boolean isClosed = false;
	
	private final Object closeLock = new Object();
	
	public GenericObjectPool(PooledObjectFactory<T> factory) {
		this.factory = factory;
		this.maxIdle = 8;
		this.minIdle = 0;
		this.idleObjects = new LinkedBlockingDeque<>();
		this.allObjects = new ConcurrentHashMap<>();
		this.totalCreatedCount = new AtomicInteger();
	}
	
	public void evict() {
		this.assertOpen();
		if (this.idleObjects.size() > 0) {
			PooledObject<T> p = null;
			synchronized (this.evictionLock) {
				
			}
		}
	}
		
	private void destroy(PooledObject<T> toDestroy) throws Exception {
		toDestroy.invalidate();
		this.idleObjects.remove(toDestroy);
		this.allObjects.remove(new IdentityWrapper<T>(toDestroy.getObject()));
		try {
			this.factory.destroyObject(toDestroy);
		} finally {
			this.totalCreatedCount.decrementAndGet();
		}
	}
	
	@Override
	public void close() throws IOException {
		if (!this.isClosed()) {
			synchronized (this.closeLock) {
				if (!this.isClosed()) {
					this.isClosed = true;
					this.clear();
				}
			}
		}
	}

	/**
	 * Remove idle connections
	 */
	private void clear() {
		for (PooledObject<T> p = (PooledObject<T>)this.idleObjects.poll(); p != null; p = (PooledObject<T>)this.idleObjects.poll()) {
			try {
				this.destroy(p);
			} catch (Exception e) {
				/// swallow
			}
		}
	}
	
	@Override
	public T borrowObject() throws Exception {
		this.assertOpen();
		
		PooledObject<T> p = null;
		
		while(true) {
			do {
				boolean create = false;
				
				p = this.idleObjects.pollFirst();
				
				if (p == null) {
					p = this.create();
					if (p != null) {
						create = true;
					}
				}
				
				if (this.blockWhenExhausted) {
					if (p == null) {
						if (this.borrowMaxWaitMillis < 0L) {
							p = this.idleObjects.takeFirst();
						} else {
							p = this.idleObjects.poll(this.borrowMaxWaitMillis, TimeUnit.MICROSECONDS);
						}
					}
					if (p == null) {
						throw new NoSuchElementException("Timeout wainting for connection");
					}
				} else if (p == null) {
					throw new NoSuchElementException("Pool exhausetd");
				}
			} while (p == null);
		}
	}

	@Override
	public void returnObject(T object) throws Exception {
		PooledObject<T> p = this.allObjects.get(new IdentityWrapper<T>(object));
		if (p == null) {
			throw new IllegalStateException("Returned object is not a part of this pool");
		} else {
			markReturningState(p);
			if (!this.factory.validateObject(p)) {
				this.destroy(p);
				
				// TODO ensureIdle
				
			} else {
				try {
					this.factory.passivateObject(p);
				} catch (Exception e) {
					this.destroy(p);
					return;
				}
				// passivate object
				
				if (!p.deallocate()) {
					throw new IllegalStateException("Object has already been returned to this pool or is invalid"); 
				} else {
					if (this.isClosed || (this.maxIdle > -1 && this.maxIdle <= this.idleObjects.size())) {
						this.destroy(p);
					} else {
						this.idleObjects.addLast(p);
						if(this.isClosed()) {
                            this.clear();
                        }
					}
				}
			}
		}
	}
	
	public void preparePool() throws Exception {
		if(this.minIdle >= 1) {
			this.ensureMinIdle();
		}
	}
	
	private void ensureMinIdle() throws Exception {
		this.ensureIdle(this.minIdle);
	}
	
	private void ensureIdle(int idleCount) throws Exception {
		if (idleCount >= 1 && !this.isClosed) {
			while (this.idleObjects.size() < idleCount) {
				PooledObject<T> p = this.create();
				if (p == null) {
					break;
				}
				this.idleObjects.add(p);
			}
			if (this.isClosed()) {
				this.clear();
			}
		}
	}

	private void markReturningState(PooledObject<T> pooledObject) {
		
	}
	
	@Override
	public void addObject(T object) throws Exception {
		assertOpen();
		if (this.factory == null) {
			throw new IllegalStateException("Cannot add objects without a factory.");
		}
		PooledObject<T> p = this.create();
		this.idleObjects.add(p);
	}

	@Override
	public void invalidateObject(T object) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getActive() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIdle() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isClosed() {
		return this.isClosed;
	}
	
	private PooledObject<T> create() throws Exception {
		boolean creating = true;
		while(creating) {
			synchronized (createObjectLock) {
				long cc = this.totalCreatedCount.incrementAndGet();
				if (cc > this.localMaxTotal) {
					this.totalCreatedCount.decrementAndGet();
					if (this.makingObjectCount == 0L) {
						creating = false;
					} else {
						this.createObjectLock.wait();
					}
 				} else {
					this.makingObjectCount++;
					creating = true;
				}
			}
		}
		
		if (!creating) {
			return null;
		} else {
			PooledObject<T> p1;
			boolean objectMake = true;
			try {
				p1 = this.factory.makeObject();
				objectMake = false;
			} catch (Throwable t) {
				this.totalCreatedCount.decrementAndGet();
				throw t;
			} finally {
				if (objectMake) {
					synchronized(this.createObjectLock) {
                        --this.makingObjectCount;
                        this.createObjectLock.notifyAll();
                    }
				}
			}
			
			synchronized (this.createObjectLock) {
				this.makingObjectCount--;
				this.createObjectLock.notifyAll();
			}
			this.allObjects.put(new IdentityWrapper<T>(p1.getObject()), p1);
			return p1;
		}

	}
	
	private void assertOpen() {
		if (this.isClosed()) {
			throw new IllegalStateException("Connection pool is closed");
		}
	}
	
	class EvictionIterator implements Iterator<PooledObject<T>> {

		private final Iterator<PooledObject<T>> idleObjectIterator = GenericObjectPool.this.idleObjects.iterator();
		
		@Override
		public boolean hasNext() {
			return this.idleObjectIterator.hasNext();
		}

		@Override
		public PooledObject<T> next() {
			return this.idleObjectIterator.next();
		}
		
	}
	
	/**
	 * Wrapper class for objects stored in pool, use as key for map with objects
	 */
	static class IdentityWrapper<T> {
		
		private final T instance;
		
		public IdentityWrapper(T instance) {
			this.instance = instance;
		}
		
		public T getObject() {
            return this.instance;
        }
		
		public int hashCode() {
			return System.identityHashCode(this.instance);
		}
		
		public boolean equals(Object other) {
			return other instanceof GenericObjectPool.IdentityWrapper 
						&& ((GenericObjectPool.IdentityWrapper)other).instance == this.instance;
		}
	}
	
	/**
	 *  Scheduled thread to evict pool from inactive and broken connection
	 */
	class Evictor implements Runnable {

		Evictor () {}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}
}
