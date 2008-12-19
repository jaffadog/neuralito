

import java.util.Vector;

import util.Util;
import weka.ArfManager;
import weka.DataSet;
import weka.InstancesCreator;
import weka.NoDirectionStrategy;
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
	//Load buoy data Ww3 Vobs
		buoyDataSet = new BuoyDataLoader().loadBuoyData(".//files//b106-2002.txt");
		ww3DataSet  = (Vector<WaveWatchData>) new WWManager().getWWData();
		obsDataSet  = new ObsDataLoader().loadObsData(".//files//oahu2002.dat");
	//Generate DataSet	
		ArfManager arfManager = new ArfManager();
		arfManager.SetGenerationStrategy(new NoDirectionStrategy());
		DataSet dataSet = arfManager.generateDataSet(buoyDataSet, obsDataSet, ww3DataSet);
	//Print DataSet
		Util.printCollection(dataSet.getInstances());
	//Generate Weka Data Set
		InstancesCreator creator = new InstancesCreator();
		creator.generateTrainningData(dataSet.getName(), dataSet.getInstances());
			
	}
	
	

}
