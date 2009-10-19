package edu.unicen.surfforecaster.gwt.client.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This calls allow register group of async calls to the server, and when then the class who trigger all these calls
 * could ask if all finished to operate.
 * Use this when needs to wait the end of a several async calls.
 */
public class AsyncCallsQueue {
	
	private Map<String, Boolean> calls = null;
	
	public AsyncCallsQueue() {
		calls = new HashMap<String, Boolean>();
	}
	
	public void addCall(String callName) {
		calls.put(callName, false);
	}
	
	public void removeCall(String callName) {
		calls.remove(callName);
	}
	
	public boolean allFinished() {
		Iterator<Entry<String, Boolean>> i = calls.entrySet().iterator();
		while (i.hasNext()) {
			Entry<String, Boolean> entry = i.next();
			if (!entry.getValue()) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isFinished(String callName) {
		return calls.get(callName);
	}
	
	public void clearCalls() {
		calls.clear();
	}
	
	public void setCallStatus(String callName, boolean status) {
		calls.put(callName, status);
	}
	
	public void markAsFinished(String callName) {
		calls.put(callName, true);
	}

}
