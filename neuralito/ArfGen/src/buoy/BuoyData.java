package buoy;


import java.util.Calendar;

import util.Util;



public class BuoyData implements Comparable{
	
	private Calendar date = null;
	private Double waveHeight = null;
	private Double wavePeriod = null;
	private Double waveDirection = null;
	
	public Calendar getDate() {
		return date;
	}
	
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	public Double getWaveDirection() {
		return waveDirection;
	}
	
	public void setWaveDirection(Double waveDirection) {
		this.waveDirection = waveDirection;
	}
	
	public Double getWaveHeight() {
		return waveHeight;
	}
	
	public void setWaveHeight(Double waveHeight) {
		this.waveHeight = waveHeight;
	}
	
	public Double getWavePeriod() {
		return wavePeriod;
	}
	
	public void setWavePeriod(Double wavePeriod) {
		this.wavePeriod = wavePeriod;
	}
	
	public boolean equalsDate(Calendar date){
		//System.out.println(this.date.getTime() + " = " +this.date.get(Calendar.YEAR) + "/" + this.date.get(Calendar.MONTH) + "/" + this.date.get(Calendar.DAY_OF_MONTH));
		//System.out.println(date.getTime() + " = " +date.get(Calendar.YEAR) + "/" + date.get(Calendar.MONTH) + "/" + date.get(Calendar.DAY_OF_MONTH));
		if (this.date.get(Calendar.YEAR) == date.get(Calendar.YEAR))
			if (this.date.get(Calendar.MONTH) == date.get(Calendar.MONTH))
				if (this.date.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH))
					return true;
		return false;
	}
	
	public boolean equalsDateTime(Calendar date){
		if (this.date.get(Calendar.YEAR) == date.get(Calendar.YEAR))
			if (this.date.get(Calendar.MONTH) == date.get(Calendar.MONTH))
				if (this.date.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH))
					if (this.date.get(Calendar.HOUR_OF_DAY) == date.get(Calendar.HOUR_OF_DAY))
						if (this.date.get(Calendar.MINUTE) == date.get(Calendar.MINUTE))
							return true;
		return false;
	}
	
	public String toString(){
				
	
		return	"Buoy: "+ Util.getDateFormatter().format(date.getTime()) + " WH:"+  Util.getDecimalFormatter().format(this.waveHeight) +" WP:"+ Util.getDecimalFormatter().format(this.wavePeriod) + " WD:"+ Util.getDecimalFormatter().format(this.waveDirection) ;
		
		
	}

	public int compareTo(Object o) {
		BuoyData buoyData = (BuoyData)o;
		int result = this.getDate().compareTo(buoyData.getDate());
		
		if (result < 0)
	        return -1;
	    if (result > 0)
	        return 1;

		return 0;
	}
}

