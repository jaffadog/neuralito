package edu.unicen.surfforecaster.server.domain.weka.strategy;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.server.domain.weka.DataSet;

public interface GenerationStrategy {
	
	public DataSet generateTrainningData(Hashtable<String,Object> dataCollection);
	
	public String getName();
	
	public String getDescription();
	
	public String getBeach();
	
	public String getShortDescription();
	
	public Map<String,OptionType> listAvailableOptions();
	
	public void setOptions(HashMap<String,String> options) throws NeuralitoException;
	
	public Map<String,String> getOptions();
	
}
