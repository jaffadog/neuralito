

import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Vector;

import Observations.InstancesCreator;
import Observations.ObsData;
import Observations.ObsDataLoader;
import buoy.BuoyData;
import buoy.BuoyDataLoader;

import util.Util;
import weka.ArfData;
import weka.ArfManager;
import weka.core.Instances;
import ww3.WWManager;
import ww3.WaveWatchData;
import filter.AndFilter;
import filter.DataTimeFilter;
import filter.DataWaveDirectionFilter;
import filter.Filter;
import filter.MaxWaveHeightFilter;
import filter.Ww3MaxWaveHeightFilter;
import filter.ww3Filter.WW3DirectionFilter;

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
		Vector<Filter> filters = new Vector<Filter>();
		Vector<BuoyData> buoyDataSet;
		Vector<WaveWatchData> ww3DataSet;
		Vector<ObsData> obsDataSet;
	//Load buoy data Ww3 Vobs
		buoyDataSet = new BuoyDataLoader().loadBuoyData(".//files//b106-2002.txt");
		ww3DataSet  = (Vector<WaveWatchData>) new WWManager().getWWData();
		obsDataSet  = new ObsDataLoader().loadObsData(".//files//oahu2002.dat");
			
		
	// Init Buoy Filters. 
		filters.add(new DataTimeFilter(new GregorianCalendar(0, 0, 0, beginningHour, beginningMinutes), new GregorianCalendar(0, 0, 0, endHour, endMinutes))); 
		//filters.add(DataWaveDirectionFilter(minDirection, maxDirection));
		//filters.add(new MaxWaveHeightFilter());
		Filter compuestFilter = new AndFilter(filters);
	//Init WW3 Filter
		Filter ww3Direction = new WW3DirectionFilter(minDirection,maxDirection);
		Filter ww3MaxWaveHeight = new Ww3MaxWaveHeightFilter();
		//Filter ww3MaxWaveHeight = new Ww3MaxWaveHeightFilter();
		
	//
	//	Util.printCollection(buoyDataSet);
	// Apply filters
		buoyDataSet = (Vector<BuoyData>) compuestFilter.executeFilter(buoyDataSet);
		//ww3DataSet = (Vector<WaveWatchData>) ww3MaxWaveHeight.executeFilter(ww3DataSet);
		//ww3DataSet = (Vector<WaveWatchData>) ww3Direction.executeFilter(ww3DataSet);
		
		Util.printCollection(buoyDataSet);
		//Util.printCollection(ww3DataSet);
		//Util.printCollection(obsDataSet);
			
		//Unify buoy, observations and ww3 data.
		ArfManager arfManager = new ArfManager();
		Vector<ArfData> arfDataSet = arfManager.generateTrainningData(buoyDataSet, obsDataSet, ww3DataSet);
		
		
		// print unified data
		//arfManager.print(arfDataSet);
		//Util.printCollection(arfDataSet);
		
		//Create Weka trainning data set
		InstancesCreator creator = new InstancesCreator();
		Instances instances = (creator.generateTrainningData("North Shore Oahu", arfDataSet)); 
		//Create Weka trainning data set into file.
		creator.generateFile("North Shore Oahu", arfDataSet);
		
		//Print Data set
		//System.out.println("Class attribute: " +instances.classAttribute().name());
		//System.out.println(instances);
		
	}
	
	

}
