

import java.util.Hashtable;
import java.util.Vector;

import util.Util;
import weka.ArfManager;
import weka.DataSet;
import weka.InstancesCreator;
import weka.core.Instances;
import weka.datasetStrategy.FullFiltersStrategy;
import weka.datasetStrategy.GenerationStrategy;
import weka.datasetStrategy.NoBuoyStrategy;
import weka.datasetStrategy.NoDirectionStrategy;
import ww3.WWManager;
import ww3.WaveWatchData;
import Observations.ObsData;
import Observations.ObsDataLoader;
import buoy.BuoyData;
import buoy.BuoyDataLoader;

public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Vector<BuoyData> buoyDataSet;
		Vector<WaveWatchData> ww3DataSet;
		Vector<ObsData> obsDataSet;
	// Set data files for load
		String[] buoyFiles = new String[]{"b106-2002", "b106-2003"};
		String[] obsFiles = new String[]{"2002", "2003"};		
		String[] ww3Files = new String[]{"2002", "2003"};
		
	//	Load buoy data Ww3 Vobs
		buoyDataSet = new BuoyDataLoader().loadBuoyData(buoyFiles);
		ww3DataSet  = (Vector<WaveWatchData>) new WWManager().getWWData(ww3Files,21.00,-157.5);
		obsDataSet  = new ObsDataLoader().loadObsData(obsFiles);
		
		Hashtable<String, Object> dataCollection = new Hashtable<String, Object>();
		dataCollection.put("buoyData", buoyDataSet);
		dataCollection.put("obsData", obsDataSet);
		dataCollection.put("ww3Data", ww3DataSet);
//		System.out.println("**************************************************************");
//		System.out.println("***********************Before Applying Filters****************");
//		System.out.println("**************************************************************");
//		Util.printCollection("Buoy Data Set",buoyDataSet);
//		Util.printCollection("WW3 Data Set",ww3DataSet);
//		Util.printCollection("Visual Observations",obsDataSet);
		
	//Choose generation Strategy
		GenerationStrategy generationStrategy = new NoBuoyStrategy();
		
	//Generate general DataSet	
		ArfManager arfManager = new ArfManager();
		arfManager.setGenerationStrategy(generationStrategy);
		DataSet dataSet = arfManager.generateDataSet(dataCollection);
		System.out.println("**************************************************************");
		System.out.println("*************General DataSet After Applying Filters***********");
		System.out.println("**************************************************************");
		Util.printCollection(dataSet.getInstances());
	
	//Generate Weka Data Set
		InstancesCreator creator = new InstancesCreator();
		Instances wekaDataSet = creator.generateTrainningData(dataSet);
		System.out.println("**************************************************************");
		System.out.println("*************Weka DataSet After Applying Filters**************");
		System.out.println("**************************************************************");
		Util.printWekaInstances(wekaDataSet);
		//Generate Weka arff File
		Util.generateResultPackage(generationStrategy, obsFiles, wekaDataSet);
		//creator.generateFile(dataSet.getName(), dataSet.getDescription(), wekaDataSet);
	}
	
	

}
