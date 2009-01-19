package weka.datasetStrategy;

import java.util.Hashtable;
import java.util.Vector;

import weka.DataSet;
import ww3.WaveWatchData;
import Observations.ObsData;
import buoy.BuoyData;

public interface GenerationStrategy {
	
	public DataSet generateTrainningData(Hashtable<String,Object> dataCollection);
	
	public String getName();
	
	public String getDescription();
	
}
