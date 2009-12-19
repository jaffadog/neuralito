package edu.unicen.surfforecaster.server.domain.weka.filter;

import java.util.Enumeration;
import java.util.Vector;


public class AndFilter extends CompuestFilter{
	
	public AndFilter() {
		super();
	}

	public AndFilter(Vector<Filter> filters) {
		super(filters);
	}

	public Vector<?> executeFilter(Vector<?> dataSet){
		Vector filteredData = new Vector();
		for (Enumeration<Filter> e = this.filters.elements(); e.hasMoreElements();){
			Filter filter = e.nextElement();
			dataSet = (Vector) filter.executeFilter(dataSet);	
		}
		filteredData.addAll(dataSet);
		return filteredData;
	}
}
