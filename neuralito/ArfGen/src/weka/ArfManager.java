package weka;

import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import Observations.ObsData;
import buoy.BuoyData;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import ww3.WaveWatchData;

public class ArfManager {
	
	
	public ArfManager(){
		
	}
	
	public Vector<ArfData> generateTrainningData(Vector<BuoyData> buoyDataSet, Vector<ObsData> obsDataSet, Vector<WaveWatchData> ww3DataSet){
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
			
			
			if (obsData != null){
				ArfData arfData = new ArfData(buoyData, obsData, ww3Data);
				arfData.setDate(buoyData.getDate());
				arfDataSet.add(arfData);
			}
		}
		return arfDataSet;
	}
	
	public void print(Vector<ArfData> arfDataSet){
		for (Enumeration<ArfData> e = arfDataSet.elements(); e.hasMoreElements();){
			ArfData data = e.nextElement();
			String ww3height = ""; 
			if (data.getWw3Data() == null) ww3height= "null"; else ww3height = data.getWw3Data().getHeight() + "";
			System.out.println(data.getDate().get(Calendar.YEAR) + "/" + (data.getDate().get(Calendar.MONTH) + 1) + "/" + data.getDate().get(Calendar.DAY_OF_MONTH) + " --BH--> " + data.getBuoyData().getWaveHeight() + " --OH--> " + 
					data.getObsData().getNShore() * 0.3048 * 2 + " --WW3H-> " + ww3height);
		}
		
	}
}
