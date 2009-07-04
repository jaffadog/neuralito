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
import filter.Filter;
import filter.MaxWaveHeightFilter;
import filter.WW3CouplingFilter;


public class FullFiltersSimilarValuesStrategy implements GenerationStrategy {

	private String name;
	private String description;
	private final double deltaHeight;
	private final double deltaPeriod;
	private final double deltaDirection;
	private String strategyString;
	private String beach;
	
	public FullFiltersSimilarValuesStrategy() {
		this.initStrategy();
		this.deltaHeight = 0;
		this.deltaDirection = 0;
		this.deltaPeriod = 0;
	}
	
	public FullFiltersSimilarValuesStrategy(String beach, double deltaHeight, double deltaDirection, double deltaPeriod) {
		this.initStrategy();
		this.deltaHeight = deltaHeight;
		this.deltaDirection = deltaDirection;
		this.deltaPeriod = deltaPeriod;
		this.beach = beach;
	}
	
	public FullFiltersSimilarValuesStrategy(String beach, String name, String description, double deltaHeight, double deltaDirection, double deltaPeriod) {
		this.name = name;
		this.description = description;
		this.deltaHeight = deltaHeight;
		this.deltaDirection = deltaDirection;
		this.deltaPeriod = deltaPeriod;
		this.beach = beach;
	}

	public void initStrategy(){
		this.name = "FullFiltersSimilarValuesStrategy";
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
			"Ademas esta estrategia filtra todas las instancias en que las lecturas de la boya y del ww3 no son \n" +
			"similares (altura, periodo y direccion) asegurando que las instancias resultantes sean unicamente \n" +
			"las mas consistentes. \n";
	}
	
	public String getBeach() {
		return beach;
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
		 
		filters.add(new DataTimeFilter(new GregorianCalendar(0, 0, 0, Util.BEGINNING_HOUR, Util.BEGINNING_MINUTE), new GregorianCalendar(0, 0, 0, Util.END_HOUR, Util.END_MINUTE))); 
		//filters.add(new DataWaveDirectionFilter(Util.minDirectionDegree, Util.maxDirectionDegree));
		filters.add(new MaxWaveHeightFilter());
		Filter compuestFilter = new AndFilter(filters);
		buoyDataSet = (Vector<BuoyData>) compuestFilter.executeFilter(buoyDataSet);
		
		Vector<Filter> ww3filters = new Vector<Filter>();
		
		//filters.add(new DataWaveDirectionFilter(Util.minDirectionDegree, Util.maxDirectionDegree));
		ww3filters.add(new WW3CouplingFilter(buoyDataSet, 12, true));
		compuestFilter = new AndFilter(ww3filters);
		ww3DataSet = (Vector<WaveWatchData>) compuestFilter.executeFilter(ww3DataSet);

		String[] strategyAttributes = {"ww3Height", "ww3Period", "ww3Direction", "visualObservation"};
		this.strategyString(filters, ww3filters, strategyAttributes, "visualObservation");
		
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
				if (this.similarValues(buoyData, ww3Data)){
					ArfData arfData = new ArfData(this.beach, buoyData, obsData, ww3Data);
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
	
public void strategyString(Vector<Filter> buoyFilters, Vector<Filter> ww3Filters, String[] strategyAttributes, String classAttribute){
		
		String text = "";
		text = this.name.toUpperCase() + "\n\n\t" + this.description + "\n\n" + "Beach: " + this.beach + "\n\n";
		
		text += "STRATEGY PARAMETERS:\n";
		text += "\tdeltaHeight -> " + this.deltaHeight + "\n";
		text += "\tdeltaPeriod -> " + this.deltaPeriod + "\n";
		text += "\tdeltaDirection -> " + this.deltaDirection + "\n";
		text += "\n";
		
		if (buoyFilters.size() > 0){
			text += "BUOY FILTERS:\n";
			for (Enumeration<Filter> e = buoyFilters.elements(); e.hasMoreElements();){
				Filter filter = e.nextElement();
				text += filter.toString();
			}
			text +="\n";
		}
		
		if (ww3Filters.size() > 0){
			text += "WW3 FILTERS:\n";
			for (Enumeration<Filter> e = ww3Filters.elements(); e.hasMoreElements();){
				Filter filter = e.nextElement();
				text += filter.toString();
			}
			text +="\n";
		}
		
		if (strategyAttributes.length > 0){
			text += "INSTANCE ATTRIBUTES:\n\t";
			for (int i = 0; i < strategyAttributes.length; i++)
				text += strategyAttributes[i] + ", ";
			text += "\n\nCLASS ATTRIBUTE:\n\t" + classAttribute + "\n\n";
		}
			
		this.strategyString = text;
		
	}
	
	public String toString(){
		return this.strategyString;
	}
}
