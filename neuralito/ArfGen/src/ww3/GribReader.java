package ww3;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Vector;

import net.sourceforge.jgrib.GribFile;


public class GribReader {
	
	private GribFile gribHeight;
	private GribFile gribPeriod;
	private GribFile gribDirection;
	public GribReader(String heightsFile, String periodFile, String directionFile){
		try {
			gribHeight = new GribFile(heightsFile);
			gribPeriod = new GribFile(periodFile);
			gribDirection = new GribFile(directionFile);
			System.out.println("Records Count:"+ gribDirection.getRecordCount()+"//"+ gribHeight.getRecordCount()+"//"+gribPeriod.getRecordCount());
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
		int latIndex = 161;
		int longIndex = 56;
		for(int i = 1;i< gribHeight.getRecordCount();i++ ){
			WaveWatchData data = new WaveWatchData();
			data.setLatitude(latitude);
			data.setLongitude(longitude);		
			try {
				data.setTime(gribHeight.getRecord(i).getTime());
				data.setDirection(gribDirection.getRecord(i).getValue(latIndex, longIndex ));
				data.setHeight(gribHeight.getRecord(i).getValue(latIndex, longIndex ));
				data.setPeriod(gribPeriod.getRecord(i).getValue(latIndex, longIndex ));
				dataSet.add(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dataSet;
	}
	
}