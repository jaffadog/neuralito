package weka.datasetStrategy;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;

import util.Util;
import weka.ArfData;
import weka.DataSet;
import ww3.WaveWatchData;
import Observations.ObsData;
import buoy.BuoyData;
import filter.AndFilter;
import filter.DataTimeFilter;
import filter.DataWaveDirectionFilter;
import filter.Filter;
import filter.MaxWaveHeightFilter;
import filter.MonthFilter;


public class MonthPeriodStrategy implements GenerationStrategy {

	private String name;
	private String description;
	private int startMonth;
	private int endMonth;
	
	public MonthPeriodStrategy(int startMonth, int endMonth) {
		this.name = "MonthPeriodStrategy";
		this.description = "Esta estrategia contiene los siguientes datos: \n\n" +
			"WW3: Periodo de ola, altura maxima por dia, direccion de la ola, tomando solo los valores en que hay luz solar \n" +
			"Observacion: Observacion visual que representa la altura maxima que alcanzaron las olas en ese dia \n" + 
			"Mostrando solo las observaciones entre los meses pasados por parametro \n";
		this.startMonth = startMonth;
		this.endMonth = endMonth;
	}
	
	public MonthPeriodStrategy(String name, String description, int startMonth, int endMonth) {
		this.name = name;
		this.description = description;
		this.startMonth = startMonth;
		this.endMonth = endMonth;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getName() {
		return name;
	}
	
	public int getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}

	public int getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}

	@Override
	public DataSet generateTrainningData(Hashtable<String, Object> dataCollection) {
		Vector<WaveWatchData> ww3DataSet = (Vector<WaveWatchData>) dataCollection.get("ww3Data");
		Vector<ObsData> obsDataSet = (Vector<ObsData>) dataCollection.get("obsData");
		
		Vector<Filter> filters = new Vector<Filter>();
		 
		filters.add(new MonthFilter(this.startMonth, this.endMonth));
		filters.add(new DataTimeFilter(new GregorianCalendar(0, 0, 0, Util.beginningHour, Util.beginningMinutes), new GregorianCalendar(0, 0, 0, Util.endHour, Util.endMinutes))); 
		filters.add(new MaxWaveHeightFilter());
		Filter compuestFilter = new AndFilter(filters);
		ww3DataSet = (Vector<WaveWatchData>) compuestFilter.executeFilter(ww3DataSet);
		
		String[] strategyAttributes = {"ww3Height", "ww3Period", "ww3Direction", "visualObservation"};
		return new DataSet( name, mergeData(ww3DataSet, obsDataSet), strategyAttributes, "visualObservation");
	}
	
	private Vector<ArfData> mergeData(Vector<WaveWatchData> ww3DataSet, Vector<ObsData> obsDataSet){
		Vector<ArfData> arfDataSet = new Vector<ArfData>();
		for (Enumeration<WaveWatchData> e = ww3DataSet.elements(); e.hasMoreElements();){
			WaveWatchData ww3Data = e.nextElement();
			ObsData obsData = null;
			for (Enumeration<ObsData> f = obsDataSet.elements(); f.hasMoreElements();){
				ObsData fObsData = f.nextElement();
				if (fObsData.equalsDate(ww3Data.getDate())){
					obsData = fObsData;
					break;
				}		
			}
			
			if (obsData != null){
				ArfData arfData = new ArfData(null, obsData, ww3Data);
				arfData.setDate(ww3Data.getDate());
				arfDataSet.add(arfData);
			}
		}
		
		return arfDataSet;
	}

}
