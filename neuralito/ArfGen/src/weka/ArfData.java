package weka;

import java.util.Calendar;

import Observations.ObsData;
import buoy.BuoyData;

import weka.core.Attribute;
import ww3.WaveWatchData;

public class ArfData {
	
	private Calendar date = null;
	private BuoyData buoyData = null;
	private ObsData obsData = null;
	private WaveWatchData ww3Data = null;
	
	public ArfData(BuoyData buoyData, ObsData obsData, WaveWatchData ww3Data){
		this.buoyData = buoyData;
		this.obsData = obsData;
		this.ww3Data = ww3Data;
	}

	public BuoyData getBuoyData() {
		return buoyData;
	}

	public void setBuoyData(BuoyData buoyData) {
		this.buoyData = buoyData;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public ObsData getObsData() {
		return obsData;
	}

	public void setObsData(ObsData obsData) {
		this.obsData = obsData;
	}

	public WaveWatchData getWw3Data() {
		return ww3Data;
	}

	public void setWw3Data(WaveWatchData ww3Data) {
		this.ww3Data = ww3Data;
	}
	
	public String toString(){
		
		String buoyString = this.buoyData != null ? this.buoyData.toString() + " || " : ""; 
		String ww3String = this.ww3Data != null ? this.ww3Data.toString() + " || " : "";
		String obsString = this.obsData != null ? this.obsData.toString() : "";
		
		return buoyString + ww3String + obsString;
	}
	
	public double getValue(String attribute){
		if (attribute == "buoyHeight"){
			return this.buoyData.getWaveHeight();
		}
		if (attribute == "buoyPeriod"){
			return this.buoyData.getWavePeriod();
		}
		if (attribute == "buoyDirection"){
			return this.buoyData.getWaveDirection();
		}
		if (attribute == "ww3Height"){
			return this.ww3Data.getWaveHeight();
		}
		if (attribute == "ww3Period"){
			return this.ww3Data.getWavePeriod();
		}
		if (attribute == "ww3Direction"){
			return this.ww3Data.getWaveDirection();
		}
		if (attribute == "visualObservation"){
			return this.obsData.getNShore();
		}
		return 0;
	}
}
