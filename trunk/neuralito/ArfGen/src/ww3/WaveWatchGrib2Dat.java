package ww3;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


public class WaveWatchGrib2Dat {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(" ");	
		WWManager manager = new WWManager();
		String[] years = new String[]{"2001","2003","2004"};
		//manager.loadDataFromGrib(years,22.00,-158.75);
		print(manager.getWWData(years,22.00,-158.75));

	}
	private static void print(Collection waveData) {
		for (Iterator iterator = waveData.iterator(); iterator.hasNext();) {
			WaveWatchData data = (WaveWatchData) iterator.next();
			System.out.println("Day:" + data.getDate().getTime() + "//Height:"
					+ data.getHeight() + "//Period:" + data.getPeriod()
					+ "//Direction:" + data.getDirection());
		}
		
	}


}