package weka.datasetStrategy;

import java.util.Hashtable;

import weka.DataSet;

public interface GenerationStrategy {
	
	public DataSet generateTrainningData(Hashtable<String,Object> dataCollection);
	
	public String getName();
	
	public String getDescription();
	
	public String getBeach();
	
	public String getShortDescription();
	
}
