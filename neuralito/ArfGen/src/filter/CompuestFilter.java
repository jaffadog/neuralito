package filter;

import java.util.Vector;

import buoy.BuoyData;


public abstract class CompuestFilter extends Filter{
	protected Vector<Filter> filters = null;
	
	public CompuestFilter(){
		this.filters = new Vector();
	}
	
	public CompuestFilter(Vector<Filter> filters){
		this.filters = filters;
	}
	
	public void addFilter(Filter filter){
		this.filters.add(filter);
	}
	
	public Vector<Filter> getFilters() {
		return filters;
	}

	public void setFilters(Vector<Filter> filters) {
		this.filters = filters;
	}

	public  abstract Vector<BuoyData> executeFilter(Vector<?> dataSet);
}
