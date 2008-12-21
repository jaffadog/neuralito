package util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;



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
	public static void printCollection(String name, Collection col){
		System.out.println("---------------------------------------------");
		System.out.println("---------------------------------------------");
		System.out.println("Collection: " + name + ". Number of elements: " + col.size());
		System.out.println("---------------------------------------------");
		System.out.println("---------------------------------------------");
		int i = 0;
		String pattern = "0";
				if (col.size()>10000){
					pattern = "00000";
				}
				else
					if (col.size()>1000)
					 pattern = "0000";
					 else
						 if ( col.size()>100 )
						 pattern = "000";
		for (Iterator iterator = col.iterator(); iterator.hasNext();) {
			
			Object object = (Object) iterator.next();
			System.out.println(Util.getDecimalFormatter(pattern).format(i) + " :" + object);
			i++;
		}
				
	}
	public static void printWekaInstances(Instances instances){
		
		System.out.println("---------------------------------------------");
		System.out.println("---------------------------------------------");
		System.out.println("Weka Instances, relation Name: " + instances.relationName());
		System.out.println("---------------------------------------------");
		System.out.println("---------------------------------------------");
		Enumeration attributes = instances.enumerateAttributes();
		System.out.println("Attributes Description:");
		System.out.println("---------------------------------------------");
		while (attributes.hasMoreElements()) {
			Attribute attribute = (Attribute) attributes.nextElement();
			System.out.print("Attribute Name: \t" + attribute.name());
			System.out.println("\t\t Attribute Index:\t\t" + attribute.index());
			
			
		}
		System.out.println();
		System.out.print("Class Attribute: \t" + instances.classAttribute().name());
		System.out.println("\t Class Index:\t\t\t" + instances.classIndex());
		
		System.out.println("---------------------------------------------");
		System.out.println("---------------------------------------------");
		System.out.println("Instances - Number of Instances: " + instances.numInstances());
		System.out.println("---------------------------------------------");
		System.out.println("---------------------------------------------");
		Enumeration en = instances.enumerateInstances();
		while (en.hasMoreElements()) {
			Instance instance = (Instance) en.nextElement();
			
			System.out.println("B.WH:" + Util.getDecimalFormatter().format(instance.value(0))+ "||B.WP:" + Util.getDecimalFormatter().format(instance.value(1)) + "||B.WD:" + Util.getDecimalFormatter().format(instance.value(2)) + "||WW3.WH:" + Util.getDecimalFormatter().format(instance.value(3)) + "||WW3.WP:" + Util.getDecimalFormatter().format(instance.value(4))+ "||WW3.WD:" + Util.getDecimalFormatter().format(instance.value(5)) + "||VO.WH:" + Util.getDecimalFormatter().format( instance.value(6) ) );
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
	public static DecimalFormat getDecimalFormatter(String pattern){
		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.applyPattern(pattern);
		return decimalFormat;
	}
}

