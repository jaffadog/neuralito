package Observations;

import java.util.Calendar;
import java.util.Hashtable;

import util.Util;

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
	
//	public Hashtable<String, Double> getObservations() {
//		return observations;
//	}
//
//	public void setObservations(Hashtable<String, Double> observations) {
//		this.observations = observations;
//	}
	
	public Double getWaveHeight(String beach){
		return this.observations.get(beach);
	}
	
	public void setWaveHeight(String beach, Double waveHeight){
		this.observations.put(beach,  this.HSFtoTCS(waveHeight));
	}

	/*
	public Double getAlmo() {
		return this.observations.get("almo");
	}

	public void setAlmo(Double waveHeight) {
		this.observations.put("almo", this.HSFtoTCS(waveHeight));
	}

	public Double getDh() {
		return this.observations.get("dh");
	}

	public void setDh(Double waveHeight) {
		this.observations.put("dh", this.HSFtoTCS(waveHeight));
	}
	*/
	public Calendar getDate() {
		return date;
	}
	
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	/**
	 * Return the visual observation of wave height. Observation are made from Sunset beach when surf is < 15 (HSF) and from Waimea when surf >15(HSF). 
	 * @return Wave height in  through to crest scale meters.
	 */
	
//	public Double getNShore() {
//		return this.observations.get("nshore");
//	}
	/**
	 * @param waveHeight wave height observed from shore in hawaiian scale feet
	 */
//	public void setNShore(Double waveHeight) {
//		this.observations.put("nshore", this.HSFtoTCS(waveHeight));
//	}
	
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
	public String print(String beach){
		String date =  Util.getDateFormatter().format(this.date.getTime()) ;
		String string  ="V.Obs: "+ date + " WH: "+ Util.getDecimalFormatter().format(this.observations.get(beach) );
		return string;
		
	}
	
}

