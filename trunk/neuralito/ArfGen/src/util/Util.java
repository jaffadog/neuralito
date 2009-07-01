package util;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import weka.FileDataIO;
import weka.InstancesCreator;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.datasetStrategy.GenerationStrategy;



public final class Util {
	public final static  TimeZone UTC_TIME_ZONE = new SimpleTimeZone(0,"UTC");
	public static final double MIN_DIRECTION_DEGREE = 35.0;
	public static final double MAX_DIRECTION_DEGREE = 360.0;
	public static final int BEGINNING_HOUR = 17; // 7 am +10 hours to reach UTC time
	public static final int BEGINNING_MINUTE = 30;
	public static final int END_HOUR = 6;//20 pm + 10 hours to reach utc time
	public static final int END_MINUTE = 30;
	public static final double DELTA_HEIGHT = 0.7;
	public static final double DELTA_PERIOD = 2;
	public static final double DELTA_DIRECTION = 30;
	public static final double DELTA_OBSERVATION = 0.7;
	
	
	public static final int JANUARY = 1;
	public static final int FEBRUARY = 2;
	public static final int MARCH = 3;
	public static final int APRIL = 4;
	public static final int MAY = 5;
	public static final int JUNE = 6;
	public static final int JULY = 7;
	public static final int AUGUST = 8;
	public static final int SEPTEMBER = 9;
	public static final int OCTOBER = 10;
	public static final int NOBEMBER = 11;
	public static final int DECEMBER = 12;
	
	public static void printCollection(Collection col){
		
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
			System.out.println(Util.getDecimalFormatter(pattern).format(i)+" :" + object);
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
			String instanceString = "";
			for (int i = 0; i < instance.numAttributes(); i++){
				instanceString += instance.attribute(i).name() + ":" + Util.getDecimalFormatter().format(instance.value(i)) + "  ";
			}
			System.out.println(instanceString);
			//System.out.println("B.WH:" + Util.getDecimalFormatter().format(instance.value(0))+ "||B.WP:" + Util.getDecimalFormatter().format(instance.value(1)) + "||B.WD:" + Util.getDecimalFormatter().format(instance.value(2)) + "||WW3.WH:" + Util.getDecimalFormatter().format(instance.value(3)) + "||WW3.WP:" + Util.getDecimalFormatter().format(instance.value(4))+ "||WW3.WD:" + Util.getDecimalFormatter().format(instance.value(5)) + "||VO.WH:" + Util.getDecimalFormatter().format( instance.value(6) ) );
		}
		
		
	}
	
	public static DateFormat getDateFormatter(){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.setTimeZone(Util.UTC_TIME_ZONE);
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
	
	public static void generateResultPackage(GenerationStrategy generationStrategy, String[] years, Instances wekaDataSet){
		
		
		String yearsDirectory = "";
		for (int i = 0; i < years.length; i++)
			yearsDirectory += years[i] + "-";
		
		File directory = new File(".//files//wekaResults//" + generationStrategy.getName());
		directory.mkdir();
		
		directory = new File(directory.getPath() + "//" + yearsDirectory + "//v0");
		int i = 1;
		while (directory.exists()){
			directory = new File(directory.getParent() + "//v" + i);
			i++;
		}
		directory.mkdir();
		
		//Save arff file
		InstancesCreator creator = new InstancesCreator();
		creator.generateFile(directory.getPath() + "//" + generationStrategy.getName() + ".arff", wekaDataSet);
		
		//Save strategy description
		FileDataIO fileWriter = new FileDataIO();
		fileWriter.writeFile(directory.getPath() + "//StrategyDescription.txt", generationStrategy.toString());
	}
}

