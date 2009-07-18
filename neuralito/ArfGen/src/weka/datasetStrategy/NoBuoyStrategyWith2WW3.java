package weka.datasetStrategy;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import Observations.ObsData;
import filter.AndFilter;
import filter.DataTimeFilter;
import filter.Filter;
import filter.MaxWaveHeightFilter;

import util.Util;
import weka.ArfData;
import weka.DataSet;
import ww3.WaveWatchData;

public class NoBuoyStrategyWith2WW3 implements GenerationStrategy {
	private String strategyString;
	private String beach;
	private String name;
	private String description;
	private String shortDescription;
	private String[] strategyAttributes = {};
	private String classAttribute = "visualObservation";
	
	public NoBuoyStrategyWith2WW3() {
		this.name = "NoBuoyStrategy";
		this.description = 
			"Esta estrategia usa los datos del ww3 y los combina con las observaciones visuales.\n" +
			"Dado que del ww3 disponemos de datos cada tres horas y las observaciones son una por dia que representa \n" +
			"la altura maxima que alcanzo una ola en el dia, las lecturas del ww3 se filtran dejando la   \n"+
			"mayor ola captada, adicionalmente dado que las observaciones fueron tomadas durante las horas del \n"+
			"dia en que hay luz solar, las lecturas del ww3 durante la noche tambien fueron filtradas";
		
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public NoBuoyStrategyWith2WW3(String beach,String[]years,double ww3Y1,double ww3X1,double ww3Y2,double ww3X2) {
		this();
		this.beach = beach;
		this.shortDescription = "strategy[nobuoy2WW3].beach[" + beach + "].years " + Arrays.toString(years) + ".months[1-12].height[unrestriced].ww3.1[" + ww3Y1 + "," + ww3X1+"]"+"ww3.2[" + ww3Y2 + "," + ww3X2+"]" ;
	}
	@Override
	public DataSet generateTrainningData(
			Hashtable<String, Object> dataCollection) {
		Vector<WaveWatchData> ww3DataSet = (Vector<WaveWatchData>) dataCollection.get("ww3Data");
		Vector<ObsData> obsDataSet = (Vector<ObsData>) dataCollection.get("obsData");
		Vector<WaveWatchData> ww32 =  (Vector<WaveWatchData>) dataCollection.get("ww3Data2");
		Vector<Filter> filters = new Vector<Filter>();
		 
		filters.add(new DataTimeFilter(new GregorianCalendar(0, 0, 0, Util.BEGINNING_HOUR, Util.BEGINNING_MINUTE), new GregorianCalendar(0, 0, 0, Util.END_HOUR, Util.END_MINUTE))); 
		//filters.add(new DataWaveDirectionFilter(Util.minDirectionDegree, Util.maxDirectionDegree));
		filters.add(new MaxWaveHeightFilter());
		Filter compuestFilter = new AndFilter(filters);
		ww3DataSet = (Vector<WaveWatchData>) compuestFilter.executeFilter(ww3DataSet);
		
		Vector<ArfData> arfDataArr = mergeData(ww3DataSet,ww32, obsDataSet);
		DataSet dataSet = new DataSet( name, description, arfDataArr, this.strategyAttributes, this.classAttribute); 
		this.strategyString(filters, this.strategyAttributes, this.classAttribute);
		
		
		return dataSet;
	}

	@Override
	public String getDescription() {
		
		return "Same as no buoy strtegy but using two ww3 grid points";
	}

	@Override
	public String getName() {
		
		return "NoBuoyStrategyWith2WW3";
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
	
	private Vector<ArfData> mergeData(Vector<WaveWatchData> ww3DataSet, Vector<WaveWatchData> ww32, Vector<ObsData> obsDataSet){
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
				Hashtable<String, Double> data = new Hashtable<String, Double>();
				WaveWatchData ww3Data2 = (WaveWatchData)ww32.elementAt(ww3DataSet.indexOf(ww3Data));
				data.put("ww3Height", ww3Data.getHeight());
				data.put("ww3Period", ww3Data.getPeriod());
				data.put("ww3Direction", ww3Data.getDirection());
				data.put("ww3Height2", ww3Data2.getHeight());
				data.put("ww3Period2", ww3Data2.getPeriod());
				data.put("ww3Direction2", ww3Data2.getDirection());
				data.put("visualObservation", obsData.getWaveHeight(this.beach));
				
				if (this.strategyAttributes.length == 0){
					Set<String> attributes = data.keySet();
					this.strategyAttributes = new String[attributes.size()];
					Iterator<String> it = attributes.iterator();
					int i = 0;
					while (it.hasNext()){
						this.strategyAttributes[i] = it.next();
						i++;
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
