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
	
	private AtomicInteger createdObjectCount;
	
	private long makingObjectCount;
	
	private final Object evictionLock = new Object();
	
	private final Object createObjectLock = new Object();
	
	private volatile boolean isClosed = false;
	
	public GenericObjectPool(PooledObjectFactory<T> factory) {
		this.factory = factory;
		this.maxIdle = 8;
		this.minIdle = 0;
		this.idleObjects = new LinkedBlockingDeque<>();
		this.allObjects = new ConcurrentHashMap<>();
		this.createdObjectCount = new AtomicInteger();
	}
	
	public void evict() {
		this.assertOpen();
		if (this.idleObjects.size() > 0) {
			PooledObject<T> p = null;
			synchronized (this.evictionLock) {
				
			}
		}
	}
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

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
	public void returnObject(T object) {
		PooledObject<T> p = this.allObjects.get(new IdentityWrapper<T>(object));

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
	public void invalidate(T object) {
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
				long cc = this.createdObjectCount.incrementAndGet();
				if (cc > this.localMaxTotal) {
					this.createdObjectCount.decrementAndGet();
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
				this.createdObjectCount.decrementAndGet();
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
	
	class Evictor implements Runnable {

		Evictor () {}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}
}
