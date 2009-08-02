package ww3;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.Vector;

import org.apache.log4j.Logger;

import net.sourceforge.jgrib.GribFile;
import net.sourceforge.jgrib.NoValidGribException;
import net.sourceforge.jgrib.NotSupportedException;


public class GribReader {
	
	private GribFile gribHeight;
	private GribFile gribPeriod;
	private GribFile gribDirection;
	private Logger logger = Logger.getLogger(this.getClass());
	private GribFile gribWind; 
	public GribReader(String heightsFile, String periodFile, String directionFile, String windFile){
		try {
			gribHeight = new GribFile(heightsFile);
			gribPeriod = new GribFile(periodFile);
			gribDirection = new GribFile(directionFile);
			gribWind = new GribFile(windFile);
			logger.info("Records Count:"+ gribDirection.getRecordCount()+"(Direction)"+"//"+ gribHeight.getRecordCount()+"(Height)"+"//"+gribPeriod.getRecordCount()+"(Period)"+"//"+gribWind.getRecordCount()+"(Wind)");
			//validate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public GribReader( String windFile){
		try {
			gribWind = new GribFile(windFile);
			logger.info("Records Count:"+ gribDirection.getRecordCount()+"(Direction)"+"//"+ gribHeight.getRecordCount()+"(Height)"+"//"+gribPeriod.getRecordCount()+"(Period)"+"(Height)"+"//"+gribWind.getRecordCount()+"(Wind)");
			//validate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Reads the grib file and obtain the wavewatch data.
	 * @param latitude The latitude to extract the grib data
	 * @param longitude The longitud to extract the grib data
	 * @return Collection of WaveWatchData at the given latitud longitud. 
	 */
	public Collection<WaveWatchData> getWaveWatchData(double latitude, double longitude){
				
		Collection dataSet = new Vector();
//		int latIndex = 161;
//		int longIndex = 56;
		Integer latIndex = this.findIndexForLatitude(latitude);
		Integer longIndex = this.findIndexForLongitud(longitude);
		for(int i = 1;i< gribHeight.getRecordCount();i++ ){
			WaveWatchData data = new WaveWatchData();
			data.setLatitude(latitude);
			data.setLongitude(longitude);		
			try {
				data.setTime(gribHeight.getRecord(i).getTime());
				data.setDirection(gribDirection.getRecord(i).getValue(longIndex,latIndex ));
				data.setHeight(gribHeight.getRecord(i).getValue(longIndex,latIndex ));
				data.setPeriod(gribPeriod.getRecord(i).getValue(longIndex,latIndex  ));
				double windU = gribWind.getRecord(2*i-1).getValue(longIndex, latIndex);
				double windV = gribWind.getRecord(2*i).getValue(longIndex, latIndex);
//			
//				logger.info("Index U: " + gribWind.getRecord(2*i-1).getDescription() +" " + windU);
//				logger.info("Index V: " + gribWind.getRecord(2*i).getDescription() +" " + windV);
////				logger.info(gribWind.getRecord(2*i-1).getTime());
////				logger.info(gribWind.getRecord(2*i).getTime());
				data.setWind(windU,windV);				
				dataSet.add(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dataSet;
	}

	
	/**
	 * Reads the grib file and obtain the wavewatch data.
	 * @param latitude The latitude to extract the grib data
	 * @param longitude The longitud to extract the grib data
	 * @return Collection of WaveWatchData at the given latitud longitud. 
	 */
	public Collection<WaveWatchData> getWindData(double latitude, double longitude){
				
		Collection dataSet = new Vector();
//		int latIndex = 161;
//		int longIndex = 56;
		Integer latIndex = this.findIndexForLatitude(latitude);
		Integer longIndex = this.findIndexForLongitud(longitude);
		for(int i = 1;i< gribHeight.getRecordCount();i++ ){
			WaveWatchData data = new WaveWatchData();
			data.setLatitude(latitude);
			data.setLongitude(longitude);		
			try {
//				data.setTime(gribHeight.getRecord(i).getTime());
//				data.setDirection(gribDirection.getRecord(i).getValue(longIndex,latIndex ));
//				data.setHeight(gribHeight.getRecord(i).getValue(longIndex,latIndex ));
//				data.setPeriod(gribPeriod.getRecord(i).getValue(longIndex,latIndex  ));
//				dataSet.add(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dataSet;
	}
	private Integer findIndexForLatitude(double latitude){
		try {
			double[] yCoords = gribHeight.getRecord(1).getGDS().getYCoords();
			for (int i=1;i< yCoords.length;i++){
				if (yCoords[i]==latitude){
					logger.info("Obtained Index:"+i+" for latitude:" + latitude);
					return i;
					
				}
			}
		} catch (Exception e) {}
		throw  new InvalidParameterException("The given latitude is not defined for the given Grib File");
	}
	private Integer findIndexForLongitud(double longitud){
		try {
			double[] xCoords = gribHeight.getRecord(1).getGDS().getXCoords();
			for (int i=1;i< xCoords.length;i++){
				if (xCoords[i]==longitud){
					logger.info("Obtained Index:"+i+" for longitud:" + longitud);
					return i;
				}
			}
		} catch (Exception e) {}
		throw  new InvalidParameterException("The given longitud is not defined for the given Grib File");
	}
}
