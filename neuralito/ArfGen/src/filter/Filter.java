package filter;

import java.util.Vector;

import buoy.BuoyData;


public abstract class Filter {
	
	public  abstract Vector<?> executeFilter(Vector<?> dataSet);
}
