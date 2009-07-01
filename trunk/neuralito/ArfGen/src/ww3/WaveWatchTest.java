package ww3;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


public class WaveWatchTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(" ");	
		WWManager manager = new WWManager();
		String[] years = new String[]{"2001", "2002", "2003", "2004"};
		manager.loadDataFromGrib(years,21.00,-157.5);
		print(manager.getWWData(years,21.00,-157.5));

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
