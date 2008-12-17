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
		//manager.loadDataFromGrib();
		System.out.println(manager.getWWData());
		//print( manager.getWWData());
		//System.out.println(" ");
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
