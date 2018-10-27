package com.app.hos.jdbc.dbcp.pool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AbandonedTrace {

	private volatile long lastUsedMillis = 0;
	
	private final List<WeakReference<AbandonedTrace>> traceList = new LinkedList<>();
	
	public AbandonedTrace () {}
	
	public AbandonedTrace(AbandonedTrace parent) {
		parent.addTrace(this);
	}
	
    public long getLastUsed() {
        return lastUsedMillis;
    }

    protected void setLastUsed() {
        lastUsedMillis = System.currentTimeMillis();
    }

	protected void addTrace(AbandonedTrace trace) {
		synchronized (this.traceList) {
			this.traceList.add(new WeakReference<AbandonedTrace>(trace));
		}
	}
	
	protected void clearTrace() {
		synchronized (this.traceList) {
			this.traceList.clear();
		}
	}
	
	protected List<AbandonedTrace> getTrace() {
		int size = traceList.size();
		if (size == 0) {
			return Collections.emptyList();
		}
		ArrayList<AbandonedTrace> result = new ArrayList<>(size);
		synchronized (this.traceList) {
			Iterator<WeakReference<AbandonedTrace>> iterator = this.traceList.iterator();
			while(iterator.hasNext()) {
				AbandonedTrace trace = iterator.next().get();
				if (trace == null) {
					iterator.remove();
				} else {
					result.add(trace);
				}
			}
		}
		return result;
	}
	
	protected void removeTrace(AbandonedTrace child) {
		synchronized (this.traceList) {
			Iterator<WeakReference<AbandonedTrace>> iterator = this.traceList.iterator();
			while(iterator.hasNext()) {
				AbandonedTrace trace = iterator.next().get();
				if (child.equals(trace)) {
					iterator.remove();
					break;
				} else if (trace == null) {
					// Clean-up since we are here anyway
					iterator.remove();
				}
			}
		}
	}
}
