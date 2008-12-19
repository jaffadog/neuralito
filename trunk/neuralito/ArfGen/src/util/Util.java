package util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.SimpleTimeZone;
import java.util.TimeZone;



public final class Util {
	public final static  TimeZone utcTimeZone = new SimpleTimeZone(0,"UTC");
	public static final double minDirection = 35.0;
	public static final double maxDirection = 137.0;
	public static final int beginningHour = 17; // 7 am +10 hours to reach UTC time
	public static final int beginningMinutes = 30;
	public static final int endHour = 6;//20 pm + 10 hours to reach utc time
	public static final int endMinutes = 30;
	
	public static void printCollection(Collection col){
		
		int i = 0;
		for (Iterator iterator = col.iterator(); iterator.hasNext();) {
			
			Object object = (Object) iterator.next();
			System.out.println(i + " :" + object);
			i++;
		}
				
	}
	public static DateFormat getDateFormatter(){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.setTimeZone(Util.utcTimeZone);
		dateFormat.applyPattern("dd.MM.yyyy HH:mm:ss z");
		return dateFormat;
	}
	public static DecimalFormat getDecimalFormatter(){
		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.applyPattern("000.00");
		return decimalFormat;
	}
}

