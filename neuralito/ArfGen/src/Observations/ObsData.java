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

	public Double getNShore() {
		return nShore;
	}

	public void setNShore(Double shore) {
		nShore = shore;
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
		DateFormat formatter = new SimpleDateFormat();
		formatter.setTimeZone(Util.utcTimeZone);
		String date =  formatter.format(this.date.getTime()) ;
		String string  ="V.Obs: "+ date + " WH: "+ this.nShore ;
		return string;
		
	}
	
}

