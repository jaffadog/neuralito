package edu.unicen.surfforecaster.gwt.server.util;

import java.util.Calendar;
import java.util.Hashtable;

public class ObsData {
	
	private Calendar date = null;
	Hashtable<String, Double> observations = new Hashtable<String, Double>();
	
	/**
	 * Convert the given shore wave height into  through to crest scale meters (TCS). 
	 * @param wave height observed from shore in hawaiian scale feet (HSF)
	 */
	private Double HSFtoTCS(Double value){
		return value * 0.3048 * 2; //*0.30 is to convert foot to meter, and *2 is to convert from Hawaiaan Scale Feet to Through To Crest Scale Feet.
	}
	
	public Double getWaveHeight(){
		return this.observations.get("WAVE_HEIGHT");
	}
	
	public void setWaveHeight(Double waveHeight){
		this.observations.put("WAVE_HEIGHT", waveHeight);
	}

	public Calendar getDate() {
		return date;
	}
	
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	public boolean equalsDate(Calendar date){
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
}

