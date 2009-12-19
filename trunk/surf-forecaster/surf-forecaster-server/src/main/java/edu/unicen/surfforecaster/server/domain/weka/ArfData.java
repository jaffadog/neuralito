package edu.unicen.surfforecaster.server.domain.weka;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;

import edu.unicen.surfforecaster.server.domain.weka.util.Util;

public class ArfData {
	
	private Calendar date = null;
	private BuoyData buoyData = null;
	private VisualObservationsManager obsData = null;
	private WaveWatchData ww3Data = null;
	private String beach = null;
	private WaveWatchData ww3Data2 = null;
	
	private Hashtable<String, Double> data = null;
	
	public Hashtable<String, Double> getData() {
		return data;
	}
	
	public void setData(Hashtable<String, Double> data) {
		this.data = data;
	}
	
	public ArfData(String beach, BuoyData buoyData, VisualObservationsManager obsData, WaveWatchData ww3Data){
		this.buoyData = buoyData;
		this.obsData = obsData;
		this.ww3Data = ww3Data;
		this.beach = beach;
	
	}
//	public ArfData(String beach, BuoyData buoyData, VisualObservationsManager obsData, WaveWatchData ww3Data,WaveWatchData ww3Data2){
//		this.buoyData = buoyData;
//		this.obsData = obsData;
//		this.ww3Data = ww3Data;
//		this.beach = beach;
//		this.ww3Data2 = ww3Data2;
//	}
	
	public ArfData(String beach, Hashtable<String, Double> data){
		this.data = data;
		this.beach = beach;
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

	public VisualObservationsManager getObsData() {
		return obsData;
	}

	public void setObsData(VisualObservationsManager obsData) {
		this.obsData = obsData;
	}

	public WaveWatchData getWw3Data() {
		return ww3Data;
	}

	public void setWw3Data(WaveWatchData ww3Data) {
		this.ww3Data = ww3Data;
	}
	
	public String toString(){
		String result = "Date: " + Util.getDateFormatter().format(this.date.getTime()) + " ";
		for (Enumeration<String> e = this.data.keys(); e.hasMoreElements();){
			String key = e.nextElement();
			result += key + ": " + Util.getDecimalFormatter().format(this.data.get(key)) + " "; 
		}
		
		return result;
	}
	
	public double getValue(String attribute){
		return this.data.get(attribute);
	}
}
