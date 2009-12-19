package edu.unicen.surfforecaster.server.domain.weka.filter;

import java.util.Vector;


public abstract class Filter {
	
	public  abstract Vector<?> executeFilter(Vector<?> dataSet);
	
}
