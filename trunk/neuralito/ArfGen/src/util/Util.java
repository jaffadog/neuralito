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
	
	public static void printCollection(Collection col){
		
		
		for (Iterator iterator = col.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			System.out.println(object);
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

