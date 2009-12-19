package edu.unicen.surfforecaster.server.domain.weka.ww3;

import java.security.InvalidParameterException;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

public class GribReaderTest {

/**
 * Test that given the lat and long the correct value is read from the grib file.
 */
	@Test
public void readDataFromGribCorrectly(){
//	String testFile = "files/testFiles/testGrib.grb";
//	GribReader gribReader = new GribReader(testFile,testFile,testFile,testFile);
//	double latitude = 22; 
//	double longitude =-158.75;
//	Collection<WaveWatchData> lecture =  gribReader.getWaveWatchData(latitude, longitude);
//	WaveWatchData lecture1 = (WaveWatchData)lecture.toArray()[0];
//	double expectedHeight = 1.59;
//	Assert.assertTrue(expectedHeight - lecture1.getHeight()<0.1);	
//	Assert.assertEquals(longitude , lecture1.getLongitude());
//	Assert.assertEquals(latitude , lecture1.getLatitude());
}
	@Test
	public void readDataFromGribCorrectly2(){
//		String testFile = "files/testFiles/testGrib.grb";
//		GribReader gribReader = new GribReader(testFile,testFile,testFile,testFile);
//		double latitude = 21;//Y 
//		double longitude =-157.5;//X
//		Collection<WaveWatchData> lecture =  gribReader.getWaveWatchData(latitude, longitude);
//		WaveWatchData lecture1 = (WaveWatchData)lecture.toArray()[0];
//		double expectedHeight = 1.47;
//		Assert.assertTrue(expectedHeight - lecture1.getHeight()<0.1);	
//		Assert.assertEquals(longitude , lecture1.getLongitude());
//		Assert.assertEquals(latitude , lecture1.getLatitude());
	}
	/**
	 * Test that given the lat and long which does not exist and exception is thrown.
	 */
	@Test(expected=InvalidParameterException.class)
	@Ignore
	public void readInexistentLatLong(){
//		String testFile = "files/testFiles/testGrib.grb";
//		GribReader gribReader = new GribReader(testFile,testFile,testFile,testFile);
//		double latitude = 22.3; 
//		double longitude =-158.75;
//		Collection<WaveWatchData> lecture =  gribReader.getWaveWatchData(latitude, longitude);
//		WaveWatchData lecture1 = (WaveWatchData)lecture.toArray()[0];
//		double expectedHeight = 1.59;
//		Assert.assertTrue(expectedHeight - lecture1.getHeight()<0.1);	
//		Assert.assertEquals(longitude , lecture1.getLongitude());
//		Assert.assertEquals(latitude , lecture1.getLatitude());
	}

}
