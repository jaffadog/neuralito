package util;

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
}

