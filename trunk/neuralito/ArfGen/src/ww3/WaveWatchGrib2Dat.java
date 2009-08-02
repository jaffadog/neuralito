package ww3;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import util.Util;

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
		WWManager manager = new WWManager();
		String[] years = new String[]{"1997","1998","1999","2000","2001","2002","2003","2004"};
		manager.loadDataFromGrib(years,Util.SOUTH,Util.EAST);
		manager.loadDataFromGrib(years,Util.SOUTH,Util.WEST);
		manager.loadDataFromGrib(years,Util.NORTH,Util.EAST);
		manager.loadDataFromGrib(years,Util.NORTH,Util.WEST);
	}
	


}
