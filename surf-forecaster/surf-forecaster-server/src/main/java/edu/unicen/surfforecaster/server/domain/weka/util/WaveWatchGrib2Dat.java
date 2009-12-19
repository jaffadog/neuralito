package edu.unicen.surfforecaster.server.domain.weka.util;
import edu.unicen.surfforecaster.server.domain.weka.WaveWatchLoader;
import edu.unicen.surfforecaster.server.domain.weka.WaveWatchData;

/**
 * Reads raw WaveWatch3 data from grib files and converts it into @link {@link WaveWatchData}.    
 * @author esteban
 *
 */
public class WaveWatchGrib2Dat {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(" ");	
		WaveWatchLoader manager = new WaveWatchLoader();
		String[] years = new String[]{"1997","1998","1999","2000","2001","2002","2003","2004"};
		manager.loadDataFromGrib(years,Util.SOUTH,Util.EAST);
		manager.loadDataFromGrib(years,Util.SOUTH,Util.WEST);
		manager.loadDataFromGrib(years,Util.NORTH,Util.EAST);
		manager.loadDataFromGrib(years,Util.NORTH,Util.WEST);
	}
}
