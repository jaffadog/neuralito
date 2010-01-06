package edu.unicen.surfforecaster.server.domain.weka.util;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

import com.enterprisedt.util.debug.Logger;



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
	
	public static final double NORTH = 22.00;
	public static final double SOUTH = 21.00;
	public static final double EAST = -157.50;
	public static final double WEST = -158.75;
	
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

	private static Instances trainningInstances;
	Logger logger = Logger.getLogger(Util.class);

	/**
	 * Convert the given shore wave height into through to crest scale meters
	 * (TCS).
	 * 
	 * @param wave
	 *            height observed from shore in hawaiian scale feet (HSF)
	 */
	public static Double HSFtoTCS(Double value) {
		return value * 0.3048 * 2; // *0.30 is to convert foot to meter, and *2
									// is to convert from Hawaiaan Scale Feet to
									// Through To Crest Scale Feet.
	}

	/**
	 * Creates an ARFF file from the given set of instances.
	 */
	public static void createArffFile() {
		ArffSaver saver = new ArffSaver();
		saver.setInstances(trainningInstances);
		try {
			saver.setFile(new File("c:/trainning.arff"));
			// saver.setDestination( new File(".//files//arff//" +
			// shortDescription + ".arff")); // **not** necessary in 3.5.4
			// and later
			saver.writeBatch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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
	
	
	public static Instances createWekaInstances(String name, Collection<Map<String,Double>> instancesData, String[] attributes, String classAttribute){
		Instances instancesDataSet = initDataSet(name,attributes,classAttribute);
		for (Iterator it = instancesData.iterator();it.hasNext();) {
			Map arf =(Map) it.next();
			Instance instance = makeInstance(instancesDataSet, arf);
			instancesDataSet.add(instance);
		}
		return instancesDataSet;
	}
	
	public static Instance createWekaInstance(Map<String,Double> instanceData, String[] attributes, String classAttribute){
		Instances instancesDataSet = initDataSet("",attributes,classAttribute);
		Instance instance = makeInstance(instancesDataSet, instanceData);
		return instance;
	}
	
	
	private static Instances initDataSet(String name, String[] attributes,String classAttribute){
		
		Instances data;
		// 	Creates the numeric attributes
		FastVector fastVectorAttributes = new FastVector(attributes.length);
		for (int i = 0; i < attributes.length; i++)
			fastVectorAttributes.addElement(new Attribute(attributes[i]));
		
		// Creates an empty data set
		data = new Instances(name, fastVectorAttributes, 100000);
		Attribute attribute = data.attribute(classAttribute);
		data.setClass(attribute);
		return data;
		
	}
	private static Instance makeInstance(Instances dataSet, Map<String,Double> data){
 		
		//Create empty instance
		Instance instance = new Instance(dataSet.numAttributes());

		// give the instances access to the data set
		instance.setDataset(dataSet);
		
		//set instance values
		for (int i = 0; i < dataSet.numAttributes(); i++ ){
			String attributeName = dataSet.attribute(i).name();
			instance.setValue(dataSet.attribute(attributeName), data.get(attributeName));
		}
		
		return instance;

	}
	
	
}

