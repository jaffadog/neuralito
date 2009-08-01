package weka.datasetStrategy;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import util.Util;
import weka.ArfData;
import weka.DataSet;
import ww3.WaveWatchData;
import Observations.ObsData;
import filter.AndFilter;
import filter.AvgWaveHeightFilter;
import filter.DataTimeFilter;
import filter.Filter;

public class WW3Last3DaysAvgStrategy implements GenerationStrategy {
	private String strategyString;
	private String beach;
	private String name;
	private String description;
	private String shortDescription;
	private String[] strategyAttributes = {};
	private String classAttribute = "visualObservation";
	
	public WW3Last3DaysAvgStrategy() {
		this.name = "WW3Last3DaysAvgStrategy";
		this.description = 
			"Esta estrategia usa los datos del ww3 y los combina con las observaciones visuales.\n" +
			"Dado que del ww3 disponemos de datos cada tres horas y las observaciones son una por dia que representa \n" +
			"la altura maxima que alcanzo una ola en el dia, las lecturas del ww3 se filtran dejando la   \n"+
			"la lectura cuya altura de ola se acerca mas al promedio de alturas de olas de ese dia, adicionalmente dado que las observaciones fueron tomadas durante las horas del \n"+
			"dia en que hay luz solar, las lecturas del ww3 durante la noche tambien fueron filtradas. \n" + 
			"Esta estrategia agrupa las lecturas de ww3 del dia actual y dos dias antes y la combina con la observacion del dia actual \n";
		
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public WW3Last3DaysAvgStrategy(String beach, String[]years, double ww3Y, double ww3X) {
		this();
		this.beach = beach;
		this.shortDescription = "strategy[WW3Last3DaysAvg].beach[" + beach + "].years " + Arrays.toString(years) + ".months[1-12].height[unrestriced].ww3.1[" + ww3Y + "," + ww3X+"]";
	}
	@Override
	public DataSet generateTrainningData(
			Hashtable<String, Object> dataCollection) {
		Vector<WaveWatchData> ww3DataSet = (Vector<WaveWatchData>) dataCollection.get("ww3Data");
		Vector<ObsData> obsDataSet = (Vector<ObsData>) dataCollection.get("obsData");
		Vector<Filter> filters = new Vector<Filter>();
		 
		filters.add(new DataTimeFilter(new GregorianCalendar(0, 0, 0, Util.BEGINNING_HOUR, Util.BEGINNING_MINUTE), new GregorianCalendar(0, 0, 0, Util.END_HOUR, Util.END_MINUTE))); 
		//filters.add(new DataWaveDirectionFilter(Util.minDirectionDegree, Util.maxDirectionDegree));
		filters.add(new AvgWaveHeightFilter());
		Filter compuestFilter = new AndFilter(filters);
		ww3DataSet = (Vector<WaveWatchData>) compuestFilter.executeFilter(ww3DataSet);
		Util.printCollection(ww3DataSet);
		Vector<ArfData> arfDataArr = mergeData(ww3DataSet, obsDataSet);
		DataSet dataSet = new DataSet( name, description, arfDataArr, this.strategyAttributes, this.classAttribute); 
		this.strategyString(filters, this.strategyAttributes, this.classAttribute);
		
		
		return dataSet;
	}

	@Override
	public String getDescription() {
		
		return this.description;
	}

	@Override
	public String getName() {
		
		return this.name;
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
	
	private Vector<ArfData> mergeData(Vector<WaveWatchData> ww3DataSet, Vector<ObsData> obsDataSet){
		Vector<ArfData> arfDataSet = new Vector<ArfData>();
		for (int i = 2; i < ww3DataSet.size(); i++){
			/* This works because we have all days in ww3, if the strategy filter days we have to implement different */
			WaveWatchData ww3Data = ww3DataSet.get(i);
			WaveWatchData ww3Data1DayPrev = ww3DataSet.get(i - 1);
			WaveWatchData ww3Data2DayPrev = ww3DataSet.get(i - 2);
			ObsData obsData = null;
			for (Enumeration<ObsData> f = obsDataSet.elements(); f.hasMoreElements();){
				ObsData fObsData = f.nextElement();
				if (fObsData.equalsDate(ww3Data.getDate())){
					obsData = fObsData;
					break;
				}		
			}
			
			if (obsData != null){
				Hashtable<String, Double> data = new Hashtable<String, Double>();
				data.put("ww3Height", ww3Data.getHeight());
				data.put("ww3Period", ww3Data.getPeriod());
				data.put("ww3Direction", ww3Data.getDirection());
				data.put("ww3Height!DayPrev", ww3Data1DayPrev.getHeight());
				data.put("ww3Period1DayPrev", ww3Data1DayPrev.getPeriod());
				data.put("ww3Direct!DayPrev", ww3Data1DayPrev.getDirection());
				data.put("ww3Height2DayPrev", ww3Data2DayPrev.getHeight());
				data.put("ww3Period2DayPrev", ww3Data2DayPrev.getPeriod());
				data.put("ww3Direct2DayPrev", ww3Data2DayPrev.getDirection());
				data.put("visualObservation", obsData.getWaveHeight(this.beach));
				
				//Generate attributes array with the data hash keys, just first time
				if (this.strategyAttributes.length == 0){
					Set<String> attributes = data.keySet();
					this.strategyAttributes = new String[attributes.size()];
					Iterator<String> it = attributes.iterator();
					int j = 0;
					while (it.hasNext()){
						this.strategyAttributes[j] = it.next();
						j++;
					}
				}
				
				ArfData arfData = new ArfData(this.beach, data);
				arfData.setDate(ww3Data.getDate());
				arfDataSet.add(arfData);
				
				
			}
		}
		
		return arfDataSet;
	}
	@Override
	public String getBeach() {
		return this.beach;
	}

}
