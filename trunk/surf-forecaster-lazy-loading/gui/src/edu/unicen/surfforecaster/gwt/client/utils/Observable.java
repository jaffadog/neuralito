package edu.unicen.surfforecaster.gwt.client.utils;

import java.util.ArrayList;
import java.util.Iterator;

public class Observable{

	private ArrayList<Observer> observers = null;
	private boolean stateChanged = false;
	
	public Observable() {
		observers = new ArrayList<Observer>();
	}
	
	public void addObserver(Observer o) {
		observers.add(o);
	}
	
	public void notifyObservers() {
		if (this.hasChanged()) {
			Iterator<Observer> i = observers.iterator();
			while (i.hasNext()) {
				Observer o = i.next();
				o.update(this, null);
			}
			this.clearChanged();
		}
	}
	
	public void setChanged() {
		stateChanged = true;
	}
	
	public void clearChanged() {
		stateChanged = false;
	}
	
	public boolean hasChanged() {
		return stateChanged;
	}

}
