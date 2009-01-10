package weka.datasetStrategy;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;

import util.Util;
import weka.ArfData;
import weka.DataSet;
import Observations.ObsData;
import buoy.BuoyData;
import filter.AndFilter;
import filter.DataTimeFilter;
import filter.DataWaveDirectionFilter;
import filter.Filter;
import filter.MaxWaveHeightFilter;


public class NoWW3Strategy implements GenerationStrategy {

	private String name;
	private String description;
	
	public NoWW3Strategy() {
		this.name = "NoWW3Strategy";
		this.description = "Esta estrategia contiene los siguientes datos: \n\n" +
			"Boyas: Periodo de ola, altura maxima por dia, direccion de la ola, tomando solo los valores en que hay luz solar y la ola se dirige hacia la isla \n" +
			"Observacion: Observacion visual que representa la altura maxima que alcanzaron las olas en ese dia \n";
	}
	
	public NoWW3Strategy(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public DataSet generateTrainningData(Hashtable<String, Object> dataCollection) {
		Vector<BuoyData> buoyDataSet = (Vector<BuoyData>) dataCollection.get("buoyData");
		Vector<ObsData> obsDataSet = (Vector<ObsData>) dataCollection.get("obsData");
		
		Vector<Filter> filters = new Vector<Filter>();
		 
		filters.add(new DataTimeFilter(new GregorianCalendar(0, 0, 0, Util.beginningHour, Util.beginningMinutes), new GregorianCalendar(0, 0, 0, Util.endHour, Util.endMinutes))); 
		filters.add(new DataWaveDirectionFilter(Util.minDirectionDegree, Util.maxDirectionDegree));
		filters.add(new MaxWaveHeightFilter());
		Filter compuestFilter = new AndFilter(filters);
		buoyDataSet = (Vector<BuoyData>) compuestFilter.executeFilter(buoyDataSet);
				
		return new DataSet( name, mergeData(buoyDataSet, obsDataSet));
	}
	
	private Vector<ArfData> mergeData(Vector<BuoyData> buoyDataSet, Vector<ObsData> obsDataSet){
		Vector<ArfData> arfDataSet = new Vector<ArfData>();
		for (Enumeration<BuoyData> e = buoyDataSet.elements(); e.hasMoreElements();){
			BuoyData buoyData = e.nextElement();
			ObsData obsData = null;
			for (Enumeration<ObsData> f = obsDataSet.elements(); f.hasMoreElements();){
				ObsData fObsData = f.nextElement();
				if (fObsData.equalsDate(buoyData.getDate())){
					obsData = fObsData;
					break;
				}		
			}
			
			if (obsData != null){
				ArfData arfData = new ArfData(buoyData, obsData, null);
				arfData.setDate(buoyData.getDate());
				arfDataSet.add(arfData);
			}
		}
		
		return arfDataSet;
	}

}