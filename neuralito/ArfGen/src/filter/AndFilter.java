package filter;

import java.util.Enumeration;
import java.util.Vector;

import buoy.BuoyData;


public class AndFilter extends CompuestFilter{
	
	public AndFilter() {
		super();
	}

	public AndFilter(Vector<Filter> filters) {
		super(filters);
	}

	public Vector<BuoyData> executeFilter(Vector<?> dataSet){
		Vector<BuoyData> dataset = (Vector<BuoyData>) dataSet;
		Vector<BuoyData> filteredData = new Vector<BuoyData>();
		for (Enumeration<Filter> e = this.filters.elements(); e.hasMoreElements();){
			Filter filter = e.nextElement();
			dataset = (Vector<BuoyData>) filter.executeFilter(dataset);	
		}
		filteredData.addAll(dataset);
		return filteredData;
	}
}
