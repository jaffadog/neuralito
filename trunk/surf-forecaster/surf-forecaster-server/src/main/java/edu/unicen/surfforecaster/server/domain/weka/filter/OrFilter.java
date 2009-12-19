package edu.unicen.surfforecaster.server.domain.weka.filter;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;


public class OrFilter extends CompuestFilter{
	
	public OrFilter() {
		super();
	}

	public OrFilter(Vector<Filter> filters) {
		super(filters);
	}

	public Vector<?> executeFilter(Vector<?> dataSet){
		Vector filteredData = new Vector();
		for (Enumeration<Filter> e = this.filters.elements(); e.hasMoreElements();){
			Filter filter = e.nextElement();
			Vector parcialData = (Vector) filter.executeFilter(dataSet);
			this.addNotEquals(filteredData, parcialData);
		}
		Collections.sort(filteredData);
		return filteredData;
	}
	
	private void addNotEquals(Vector filteredData, Vector parcialData){
		for (Enumeration<Object> e = parcialData.elements(); e.hasMoreElements();){
			Object waveData = e.nextElement();
			if (!filteredData.contains(waveData))
				filteredData.add(waveData);
		}
	}
}
