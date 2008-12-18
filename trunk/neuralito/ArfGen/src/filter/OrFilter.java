package filter;

import java.util.Enumeration;
import java.util.Vector;

import buoy.BuoyData;


public class OrFilter extends CompuestFilter{
	
	public OrFilter() {
		super();
	}

	public OrFilter(Vector<Filter> filters) {
		super(filters);
	}

	public Vector<BuoyData> executeFilter(Vector<?> dataSet){
		Vector<BuoyData> dataset = (Vector<BuoyData>) dataSet;
		Vector<BuoyData> filteredData = new Vector<BuoyData>();
		for (Enumeration<Filter> e = this.filters.elements(); e.hasMoreElements();){
			Filter filter = e.nextElement();
			Vector<BuoyData> parcialData = (Vector<BuoyData>) filter.executeFilter(dataset);
			this.addNotEquals(filteredData, parcialData);
		}
		return filteredData;
	}
	
	private void addNotEquals(Vector<BuoyData> filteredData, Vector<BuoyData> parcialData){
		for (Enumeration<BuoyData> e = parcialData.elements(); e.hasMoreElements();){
			BuoyData buoyData = e.nextElement();
			if (!filteredData.contains(buoyData))
				filteredData.add(buoyData);
		}
	}
}
