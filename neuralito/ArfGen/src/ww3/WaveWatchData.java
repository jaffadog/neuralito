package ww3;
import java.util.Calendar;

import util.Util;
import util.WaveData;

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
		return "WW3: "+ Util.getDateFormatter().format(this.time.getTime()) + " WH:"+ Util.getDecimalFormatter().format(this.height) +" WP:"+ Util.getDecimalFormatter().format(this.period) + " WD:"+Util.getDecimalFormatter().format(this.direction);
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
	
}
