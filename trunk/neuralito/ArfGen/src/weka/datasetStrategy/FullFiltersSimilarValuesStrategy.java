package weka.datasetStrategy;
import java.lang.reflect.Array;
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
import filter.WW3CouplingFilter;


public class FullFiltersSimilarValuesStrategy implements GenerationStrategy {

	private String name;
	private String description;
	private final double deltaHeight;
	private final double deltaPeriod;
	private final double deltaDirection;
	
	public FullFiltersSimilarValuesStrategy() {
		this.initStrategy();
		this.deltaHeight = 0;
		this.deltaDirection = 0;
		this.deltaPeriod = 0;
	}
	
	public FullFiltersSimilarValuesStrategy(double deltaHeight, double deltaDirection, double deltaPeriod) {
		this.initStrategy();
		this.deltaHeight = deltaHeight;
		this.deltaDirection = deltaDirection;
		this.deltaPeriod = deltaPeriod;
	}
	
	public FullFiltersSimilarValuesStrategy(String name, String description, double deltaHeight, double deltaDirection, double deltaPeriod) {
		this.name = name;
		this.description = description;
		this.deltaHeight = deltaHeight;
		this.deltaDirection = deltaDirection;
		this.deltaPeriod = deltaPeriod;
	}

	public void initStrategy(){
		this.name = "FullFiltersSimilarValuesStrategy";
		this.description = "Esta estrategia contiene los siguientes datos: \n\n" +
		"Boyas: Periodo de ola, altura maxima por dia, tomando solo los valores en que hay luz solar, y tomando solo las olas con direccion especificada en Util.java \n" +
		"WW3: Periodo de ola, altura de la ola a la misma hora de la medicion de la Boya escogida (aprox) y , y tomando solo las olas con direccion especificada en Util.java \n" +
		"Observacion: Observacion visual que representa la altura maxima que alcanzaron las olas en ese dia \n";
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
		Vector<WaveWatchData> ww3DataSet = (Vector<WaveWatchData>) dataCollection.get("ww3Data");
		
		Vector<Filter> filters = new Vector<Filter>();
		 
		filters.add(new DataTimeFilter(new GregorianCalendar(0, 0, 0, Util.beginningHour, Util.beginningMinutes), new GregorianCalendar(0, 0, 0, Util.endHour, Util.endMinutes))); 
		filters.add(new DataWaveDirectionFilter(Util.minDirectionDegree, Util.maxDirectionDegree));
		filters.add(new MaxWaveHeightFilter());
		Filter compuestFilter = new AndFilter(filters);
		buoyDataSet = (Vector<BuoyData>) compuestFilter.executeFilter(buoyDataSet);
		
		filters.removeAllElements();
		
		filters.add(new DataWaveDirectionFilter(Util.minDirectionDegree, Util.maxDirectionDegree));
		filters.add(new WW3CouplingFilter(buoyDataSet, 12, true));
		compuestFilter = new AndFilter(filters);
		ww3DataSet = (Vector<WaveWatchData>) compuestFilter.executeFilter(ww3DataSet);

		String[] strategyAttributes = {"ww3Height", "ww3Period", "ww3Direction", "visualObservation"};
		return new DataSet( name, mergeData(buoyDataSet, obsDataSet, ww3DataSet), strategyAttributes, "visualObservation");
	}
	
	private Vector<ArfData> mergeData(Vector<BuoyData> buoyDataSet, Vector<ObsData> obsDataSet, Vector<WaveWatchData> ww3DataSet){
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
			//obsDataSet.remove(obsData);
			WaveWatchData ww3Data = null;
			for (Enumeration<WaveWatchData> g = ww3DataSet.elements(); g.hasMoreElements();){
				WaveWatchData gWW3Data = g.nextElement();
				if (buoyData.equalsDate(gWW3Data.getDate())){
					ww3Data = gWW3Data;
					break;
				}		
			}
			if (obsData != null && ww3Data != null){
				if (this.similarValues(buoyData, ww3Data)){
					ArfData arfData = new ArfData(buoyData, obsData, ww3Data);
					arfData.setDate(buoyData.getDate());
					arfDataSet.add(arfData);
				}
			}
		}
		
		return arfDataSet;
	}

	private boolean similarValues(BuoyData buoyData, WaveWatchData ww3Data){
		if (Math.abs(buoyData.getWaveDirection() - ww3Data.getWaveDirection()) < this.deltaDirection)
			if (Math.abs(buoyData.getWaveHeight() - ww3Data.getWaveHeight()) < this.deltaHeight)
				if (Math.abs(buoyData.getWavePeriod() - ww3Data.getWavePeriod()) < this.deltaPeriod)
					return true;
		return false;
	}

}
