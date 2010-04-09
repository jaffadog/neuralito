package edu.unicen.experimenter.datasetgenerator.generators;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import edu.unicen.experimenter.datasetgenerator.DataSet;

/**
 * Encapsulates the algorithm to generate a DataSet from raw data.
 * 
 * @author esteban
 * 
 */
public interface GenerationStrategy extends Serializable {

	/**
	 * Generate a dataSet from rawData.
	 * 
	 * @param rawData
	 * @return
	 */
	public DataSet generateDataSet(Hashtable<String, Object> rawData);

	/**
	 * Obtain strategy name
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Obtain strategy descrition
	 * 
	 * @return
	 */
	public String getDescription();

	public String getBeach();

	/**
	 * The options to set to the strategy.
	 * 
	 * @param strategyOptions
	 */
	public void setOptions(Map<String, Serializable> strategyOptions);

}
