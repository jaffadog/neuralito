package edu.unicen.surfforecaster.server.domain.weka.ww3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

import ucar.grib.NoValidGribException;
import ucar.grib.NotSupportedException;

public class gribtest {

	/**
	 * @param args
	 * @throws NoValidGribException 
	 * @throws NotSupportedException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, NotSupportedException, NoValidGribException {
		gribtest ribTest = new gribtest();
		ribTest.test();

	}
	public void test() throws FileNotFoundException, IOException, NotSupportedException, NoValidGribException{
	Collection dataSet = new Vector();
	int latIndex = 161;
	int longIndex = 56;
//	GribFile gribHeight = new GribFile("files/WW3.gribs/nww3.hs.200305.grb");
	for (int i = 1; i<20;i++){
//		System.out.println(" Date: " + gribHeight.getRecord(i).getTime().toString());
//		System.out.println(" Y: " + gribHeight.getRecord(i).getValue(latIndex, longIndex));	
		
	}
	
		
//	double[] coordinates = gribHeight.getRecord(1).getGDS().getYCoords();
	
	
//	for (int i = 0; i < coordinates.length; i++) {
//		System.out.println(i+":"+coordinates[i]);
//	}
	
	//return dataSet;
	}
}
