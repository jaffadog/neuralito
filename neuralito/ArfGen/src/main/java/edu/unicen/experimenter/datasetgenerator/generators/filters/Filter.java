package edu.unicen.experimenter.datasetgenerator.generators.filters;

import java.util.Vector;


public abstract class Filter {
	
	public  abstract Vector<?> executeFilter(Vector<?> dataSet);
	
}
