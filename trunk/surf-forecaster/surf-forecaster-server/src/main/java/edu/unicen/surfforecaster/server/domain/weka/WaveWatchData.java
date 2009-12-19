package edu.unicen.surfforecaster.server.domain.weka;
import java.util.Calendar;

import edu.unicen.surfforecaster.server.domain.weka.util.Util;
import edu.unicen.surfforecaster.server.domain.weka.util.WaveData;

public class WaveWatchData implements WaveData, java.io.Serializable, Comparable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Calendar time;
	private double latitude;
	private double longitude;
	private double height;
	private double period;
	private double direction;
	private double windSpeed;
	private double windU;
	private double windV;
	public double getWindU() {
		return windU;
	}
	public double getWindV() {
		return windV;
	}
	public double getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}
	public double getWindDirection() {
		return this.calculateWindDirection(windU, windV);
	}
	public double getWaveHeight(){
		return this.height;
	}
	public double getWavePeriod(){
		return this.period;
	}
	public double getWaveDirection(){
		return this.direction;
	}
	public Calendar getDate() {
		return time;
	}
	public void setTime(Calendar calendar) {
		this.time = calendar;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getPeriod() {
		return period;
	}
	public void setPeriod(double period) {
		this.period = period;
	}
	public double getDirection() {
		return direction;
	}
	public void setDirection(double direction) {
		this.direction = direction;
	}
	
	public boolean equalsDate(Calendar date){
		if (this.time.get(Calendar.YEAR) == date.get(Calendar.YEAR))
			if (this.time.get(Calendar.MONTH) == date.get(Calendar.MONTH))
				if (this.time.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH))
					return true;
		return false;
	}
	
	public boolean equalsDateTime(Calendar date){
		if (this.time.get(Calendar.YEAR) == date.get(Calendar.YEAR))
			if (this.time.get(Calendar.MONTH) == date.get(Calendar.MONTH))
				if (this.time.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH))
					if (this.time.get(Calendar.HOUR_OF_DAY) == date.get(Calendar.HOUR_OF_DAY))
						if (this.time.get(Calendar.MINUTE) == date.get(Calendar.MINUTE))
							return true;
		return false;
	}
	
	public String toString(){
		return "WW3: "+ Util.getDateFormatter().format(this.time.getTime()) + " WvH:"+ Util.getDecimalFormatter().format(this.height) +" WvP:"+ Util.getDecimalFormatter().format(this.period) + " WvD:"+Util.getDecimalFormatter().format(this.direction)+ " WnD:"+Util.getDecimalFormatter().format(this.calculateWindDirection(windU, windV))+ "WnS:"+Util.getDecimalFormatter().format(this.windSpeed)+ "WnU:"+Util.getDecimalFormatter().format(this.windU)+ "WnV:"+Util.getDecimalFormatter().format(this.windV);
	}
	
	public int compareTo(Object o) {
		WaveWatchData ww3Data = (WaveWatchData)o;
		int result = this.getDate().compareTo(ww3Data.getDate());
		
		if (result < 0)
	        return -1;
	    if (result > 0)
	        return 1;

		return 0;
	}
	public void setWind(double windU,double windV) {
		this.windU = windU;
		this.windV = windV;
		this.windSpeed = this.calculateWindSpeed(windU, windV);
	}
	/**
	 * Obtain wind speed from U and V wind components
	 * Spd = sqrt(Umet2 + Vmet2
	 * @param windU
	 * @param windV
	 * @return
	 */
	private double calculateWindSpeed(double windU, double windV) {
		return Math.sqrt(Math.pow(windU, 2) + Math.pow(windV, 2));
	}

	/**
	 * Obtain wind direction from U and V wind components.
	 * Dirmet is the direction with respect to true north, (0=north,90=east,180=south,270=west) that the wind is coming from. 
	 * Dirmet = atan2(-Umet,-Vmet) * DperR = 270 - ( atan2(Vmet,Umet) * DperR )
	 * Source:http://www.eol.ucar.edu/isf/facilities/isff/wind_ref.shtml
	 */
	private double calculateWindDirection(double windU, double windV) {
		double a = windU/windV;
		double per = (180/Math.PI);
		int tita=0; 

		if (windV>=0)
			tita = 180;
		else if (windV<0 && windU<0)
			tita = 0;
		else if (windU>=0 && windV<0) 
			tita =360;
	
		return Math.atan(a)*per + tita;
//		return (270 - ( Math.atan2(windV,windU) * (180/Math.PI)));
	}
}
