package weka.datasetStrategy;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;

import util.Util;
import weka.ArfData;
import weka.DataSet;
import ww3.WaveWatchData;
import Observations.ObsData;
import filter.AndFilter;
import filter.DataTimeFilter;
import filter.Filter;
import filter.MaxWaveHeightFilter;


public class NoBuoyStrategy implements GenerationStrategy {

	private String name;
	private String description;
	private String strategyString;
	private String beach = null;
	private String shortDescription;

	public NoBuoyStrategy() {
		this.name = "NoBuoyStrategy";
		this.description = 
			"Esta estrategia usa los datos del ww3 y los combina con las observaciones visuales.\n" +
			"Dado que del ww3 disponemos de datos cada tres horas y las observaciones son una por dia que representa \n" +
			"la altura maxima que alcanzo una ola en el dia, las lecturas del ww3 se filtran dejando la   \n"+
			"mayor ola captada, adicionalmente dado que las observaciones fueron tomadas durante las horas del \n"+
			"dia en que hay luz solar, las lecturas del ww3 durante la noche tambien fueron filtradas";
	}
	
	public NoBuoyStrategy(String[]years, String beach, Double ww3Y, Double ww3X) {
		this();
		this.beach = beach;
		this.shortDescription = "strategy[nobuoy].beach[" + beach + "].years " + Arrays.toString(years) + ".months[1-12].height[unrestriced].ww3[" + ww3Y + "," + ww3X+"]" ;
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
		Vector<WaveWatchData> ww3DataSet = (Vector<WaveWatchData>) dataCollection.get("ww3Data");
		Vector<ObsData> obsDataSet = (Vector<ObsData>) dataCollection.get("obsData");
		
		Vector<Filter> filters = new Vector<Filter>();
		 
		filters.add(new DataTimeFilter(new GregorianCalendar(0, 0, 0, Util.BEGINNING_HOUR, Util.BEGINNING_MINUTE), new GregorianCalendar(0, 0, 0, Util.END_HOUR, Util.END_MINUTE))); 
		//filters.add(new DataWaveDirectionFilter(Util.minDirectionDegree, Util.maxDirectionDegree));
		filters.add(new MaxWaveHeightFilter());
		Filter compuestFilter = new AndFilter(filters);
		ww3DataSet = (Vector<WaveWatchData>) compuestFilter.executeFilter(ww3DataSet);
		
		String[] strategyAttributes = {"ww3Height", "ww3Period", "ww3Direction", "visualObservation"};
		this.strategyString(filters, strategyAttributes, "visualObservation");
		
		return new DataSet( name, description, mergeData(ww3DataSet, obsDataSet), strategyAttributes, "visualObservation");
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
				ArfData arfData = new ArfData(this.beach, null, obsData, ww3Data);
				arfData.setDate(ww3Data.getDate());
				arfDataSet.add(arfData);
			}
		}
		
		return arfDataSet;
	}
	
	public void strategyString(Vector<Filter> ww3Filters, String[] strategyAttributes, String classAttribute){
		
		String text = "";
		text = this.name.toUpperCase() + "\n\n\t" + this.description + "\n\n" + "Beach: " + this.beach + "\n\n";
		
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

	@Override
	public String getShortDescription() {
		return this.shortDescription;
	}
	
}
