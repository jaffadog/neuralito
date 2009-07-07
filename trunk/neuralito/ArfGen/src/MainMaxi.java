

import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;

import util.Util;
import weka.ArfManager;
import weka.DataSet;
import weka.InstancesCreator;
import weka.core.Instances;
import weka.datasetStrategy.GenerationStrategy;
import weka.datasetStrategy.MonthPeriodAndMaxWaveHeightStrategy;
import ww3.WWManager;
import ww3.WaveWatchData;
import Observations.ObsData;
import Observations.ObsDataLoader;
import buoy.BuoyData;
import buoy.BuoyDataLoader;

public class MainMaxi {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Vector<BuoyData> buoyDataSet;
		Vector<WaveWatchData> ww3DataSet;
		Vector<WaveWatchData> ww3DataSet2;
		Vector<ObsData> obsDataSet;
	// Set data files for load

		String[] buoyFiles = new String[]{"b106-2001", "b106-2002", "b106-2003", "b106-2004"};
		String[] obsFiles = new String[]{"2002","2003"};		
		String[] years = new String[]{"2002","2003"};
		Double ww3Y = Util.NORTH;
		Double ww3X = Util.WEST;
		
	//	Load buoy data Ww3 Vobs
		buoyDataSet = new BuoyDataLoader().loadBuoyData(buoyFiles);
		ww3DataSet  = (Vector<WaveWatchData>) new WWManager().getWWData(years,ww3Y,ww3X);
		//ww3DataSet2  = (Vector<WaveWatchData>) new WWManager().getWWData(years,21.00,-158.75);
		obsDataSet  = new ObsDataLoader().loadObsData(obsFiles);
		
		Hashtable<String, Object> dataCollection = new Hashtable<String, Object>();
		dataCollection.put("buoyData", buoyDataSet);
		dataCollection.put("obsData", obsDataSet);
		dataCollection.put("ww3Data", ww3DataSet);
		//dataCollection.put("ww3Data2", ww3DataSet2);
//		System.out.println("**************************************************************");
//		System.out.println("***********************Before Applying Filters****************");
//		System.out.println("**************************************************************");
//		Util.printCollection("Buoy Data Set",buoyDataSet);
//		Util.printCollection("WW3 Data Set",ww3DataSet);
//		Util.printCollection("Visual Observations",obsDataSet);
		
	//Choose generation Strategy
		//GenerationStrategy generationStrategy = new FullFiltersStrategy();
		//GenerationStrategy generationStrategy = new FullFiltersSimilarValuesStrategy(Util.DELTA_HEIGHT, Util.DELTA_DIRECTION, Util.DELTA_PERIOD);
		//GenerationStrategy generationStrategy = new FullFiltersAllSimilarValuesStrategy(Util.DELTA_HEIGHT, Util.DELTA_DIRECTION, Util.DELTA_PERIOD, Util.DELTA_OBSERVATION);
		//GenerationStrategy generationStrategy = new MonthPeriodStrategy(Util.OCTOBER, Util.APRIL);

		GenerationStrategy generationStrategy = new MonthPeriodAndMaxWaveHeightStrategy("nshore",GregorianCalendar.OCTOBER,GregorianCalendar.MARCH,7.0,ww3Y,ww3X,years);

		
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
		Util.generateResultPackage(generationStrategy, years, wekaDataSet);
		//creator.generateFile(dataSet.getName(), wekaDataSet);
	}
	
	

}
