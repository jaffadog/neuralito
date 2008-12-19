package Observations;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import util.Util;

public class ObsData {
	
	private Calendar date = null;
	private Double nShore = null;//Wave height
	
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
	public Double getNShore() {
		return nShore;
	}
	/**
	 * Convert the given shore wave height into  through to crest scale meters. 
	 * @param shore wave height observed from shore in hawaiian scale feet
	 */
	public void setNShore(Double shore) {
		nShore = shore*0.3048*2; //*0.30 is to convert foot to meter, and *2 is to convert from Hawaiaan Scale Feet to Through To Crest Scale Feet.
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
	public String toString(){
		String date =  Util.getDateFormatter().format(this.date.getTime()) ;
		String string  ="V.Obs: "+ date + " WH: "+ Util.getDecimalFormatter().format(this.nShore );
		return string;
		
	}
	
}

