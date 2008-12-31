

import java.util.Vector;

import util.Util;
import weka.ArfManager;
import weka.DataSet;
import weka.InstancesCreator;
import weka.core.Instances;
import weka.datasetStrategy.GenerationStrategy;
import weka.datasetStrategy.NoDirectionStrategy;
import ww3.WWManager;
import ww3.WaveWatchData;
import Observations.ObsData;
import Observations.ObsDataLoader;
import buoy.BuoyData;
import buoy.BuoyDataLoader;

public class Main {
	public static final double minDirection = 35.0;
	public static final double maxDirection = 137.0;
	public static final int beginningHour = 6; // 7 am +10 hours to reach UTC time
	public static final int beginningMinutes = 30;
	public static final int endHour = 17;//20 pm + 10 hours to reach utc time
	public static final int endMinutes = 30;
	
	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Vector<BuoyData> buoyDataSet;
		Vector<WaveWatchData> ww3DataSet;
		Vector<ObsData> obsDataSet;

	//Choose generation Strategy
		GenerationStrategy generationStrategy = new NoDirectionStrategy("NoDirectionStrategy"," Descripcion de la estrategia....");
		
				
		
	//	Load buoy data Ww3 Vobs
		buoyDataSet = new BuoyDataLoader().loadBuoyData(".//files//b106-2002.txt");
		ww3DataSet  = (Vector<WaveWatchData>) new WWManager().getWWData();
		obsDataSet  = new ObsDataLoader().loadObsData(".//files//oahu2002.dat");
		System.out.println("**************************************************************");
		System.out.println("***********************Before Applying Filters****************");
		System.out.println("**************************************************************");
		Util.printCollection("Buoy Data Set",buoyDataSet);
		Util.printCollection("WW3 Data Set",ww3DataSet);
		Util.printCollection("Visual Observations",obsDataSet);
		
	//Generate general DataSet	
		ArfManager arfManager = new ArfManager();
		arfManager.setGenerationStrategy(generationStrategy);
		DataSet dataSet = arfManager.generateDataSet(buoyDataSet, obsDataSet, ww3DataSet);
		System.out.println("**************************************************************");
		System.out.println("*************General DataSet After Applying Filters***********");
		System.out.println("**************************************************************");
		Util.printCollection(dataSet.getInstances());
	
	//Generate Weka Data Set
		InstancesCreator creator = new InstancesCreator();
		Instances wekaDataSet = creator.generateTrainningData(dataSet.getName(), dataSet.getInstances());
		System.out.println("**************************************************************");
		System.out.println("*************Weka DataSet After Applying Filters**************");
		System.out.println("**************************************************************");
		Util.printWekaInstances(wekaDataSet);
		//Generate Weka arff File
		creator.generateFile(dataSet.getName(), wekaDataSet);
	}
	
	

}
