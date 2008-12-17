package ww3;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import util.Util;



public class WaveWatchData implements java.io.Serializable{
	
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
		return 0;
	}
	public double getWavePeriod(){
		return 0;
	}
	public double getWaveDirection(){
		return 0;
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
		
		
//		System.out.println("Time zone buoy:" + date.getTimeZone());
//		System.out.println("WW3 time zone:" + this.getTime().getTimeZone());
//		
//		System.out.println(this.time.getTime() + " = " +this.time.get(Calendar.YEAR) + "/" + this.time.get(Calendar.MONTH) + "/" + this.time.get(Calendar.DAY_OF_MONTH));
//		System.out.println(date.getTime() + " = " +date.get(Calendar.YEAR) + "/" + date.get(Calendar.MONTH) + "/" + date.get(Calendar.DAY_OF_MONTH));
//		
		Calendar c = new GregorianCalendar();
		c.setTime(this.getDate().getTime());
		this.time = c;
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
		DateFormat formatter = new SimpleDateFormat();
		formatter.setTimeZone(Util.utcTimeZone);
		String date =  formatter.format(this.time.getTime()) ;
		String string  ="WW3: "+ date + " WH:"+ DecimalFormat.getInstance().format(this.height) +" WP:"+ NumberFormat.getInstance().format(this.period) + " WD:"+DecimalFormat.getInstance().format(this.direction);
		return string;
		
	}
	
}