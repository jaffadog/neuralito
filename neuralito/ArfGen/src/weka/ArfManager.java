package weka;

import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import Observations.ObsData;
import buoy.BuoyData;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import ww3.WaveWatchData;

public class ArfManager {
	
	
	private GenerationStrategy generationStrategy;

	public ArfManager(){
		
	}
	public void setGenerationStrategy(GenerationStrategy strat){
		this.generationStrategy = strat;
	}
	public DataSet generateDataSet(Vector<BuoyData> buoyDataSet, Vector<ObsData> obsDataSet, Vector<WaveWatchData> ww3DataSet){
		return this.generationStrategy.generateTrainningData(buoyDataSet,obsDataSet,ww3DataSet);
	}
	
		
	
}
