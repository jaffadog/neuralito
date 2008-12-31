package weka;

import java.util.Hashtable;

import weka.datasetStrategy.GenerationStrategy;

public class ArfManager {
	
	
	private GenerationStrategy generationStrategy;

	public ArfManager(){
		
	}
	public void setGenerationStrategy(GenerationStrategy strat){
		this.generationStrategy = strat;
	}
	public DataSet generateDataSet(Hashtable<String, Object> dataCollection){
		return this.generationStrategy.generateTrainningData(dataCollection);
	}
	
		
	
}
