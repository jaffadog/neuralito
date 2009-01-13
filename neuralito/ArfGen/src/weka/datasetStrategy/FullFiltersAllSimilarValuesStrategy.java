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
import filter.WW3CouplingFilter;


public class FullFiltersAllSimilarValuesStrategy implements GenerationStrategy {

	private String name;
	private String description;
	private final double deltaHeight;
	private final double deltaPeriod;
	private final double deltaDirection;
	private final double deltaObservation;
	
	public FullFiltersAllSimilarValuesStrategy() {
		this.initStrategy();
		this.deltaHeight = 0;
		this.deltaDirection = 0;
		this.deltaPeriod = 0;
		this.deltaObservation = 0;
	}
	
	public FullFiltersAllSimilarValuesStrategy(double deltaHeight, double deltaDirection, double deltaPeriod, double deltaObservation) {
		this.initStrategy();
		this.deltaHeight = deltaHeight;
		this.deltaDirection = deltaDirection;
		this.deltaPeriod = deltaPeriod;
		this.deltaObservation = deltaObservation;
	}
	
	public FullFiltersAllSimilarValuesStrategy(String name, String description, double deltaHeight, double deltaDirection, double deltaPeriod, double deltaObservation) {
		this.name = name;
		this.description = description;
		this.deltaHeight = deltaHeight;
		this.deltaDirection = deltaDirection;
		this.deltaPeriod = deltaPeriod;
		this.deltaObservation = deltaObservation;
	}

	public void initStrategy(){
		this.name = "FullFiltersAllSimilarValuesStrategy";
		this.description = 
			"Esta estrategia usa los datos del ww3 y las boyas y los combina con las observaciones visuales.\n" +
			"Dado que de las boyas disponemos de datos cada una hora y las observaciones son una por dia que representa \n" +
			"la altura maxima que alcanzo una ola en el dia, las lecturas de las boyas se filtran dejando la   \n"+
			"mayor ola captada, adicionalmente dado que las observaciones fueron tomadas durante las horas del \n"+
			"dia en que hay luz solar, las lecturas de las boyas durante la noche tambien fueron filtradas.\n" +
			"Por otra parte una vez filtradas las lecturas de las boyas se ejecuta un coupling filter a las \n" +
			"lecturas del ww3 dejando por cada lectura de la boya (una por dia) la lectura correspondiente que \n" +
			"coincida en fecha y hora con la lectura de la boya (aproximadamente), si falta lectura del ww3 en una \n" +
			"determinada fecha y hora de la boya, entonces la lectura de la boya se descarta. \n" +
			"Ademas esta estrategia filtra todas las instancias en que las lecturas de la boya, del ww3 y la observacion no son \n" +
			"similares (altura, periodo y direccion, en el caso de obs solo altura) asegurando que las instancias resultantes sean unicamente \n" +
			"las mas consistentes. \n";
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
		return new DataSet( name, description, mergeData(buoyDataSet, obsDataSet, ww3DataSet), strategyAttributes, "visualObservation");
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
				if (this.similarValues(buoyData, ww3Data, obsData)){
					ArfData arfData = new ArfData(buoyData, obsData, ww3Data);
					arfData.setDate(buoyData.getDate());
					arfDataSet.add(arfData);
				}
			}
		}
		
		return arfDataSet;
	}

	private boolean similarValues(BuoyData buoyData, WaveWatchData ww3Data, ObsData obsData){
		if (Math.abs(buoyData.getWaveDirection() - ww3Data.getWaveDirection()) < this.deltaDirection)
			if (Math.abs(buoyData.getWaveHeight() - ww3Data.getWaveHeight()) < this.deltaHeight)
				if (Math.abs(buoyData.getWavePeriod() - ww3Data.getWavePeriod()) < this.deltaPeriod)
					if (Math.abs(obsData.getNShore() - ww3Data.getWaveHeight()) < this.deltaObservation)
						return true;
		return false;
	}

}
