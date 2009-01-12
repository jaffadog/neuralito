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
		manager.loadDataFromGrib();
		String[] ww3Files = new String[]{".//files//ww3//ww3_2002.data", ".//files//ww3//ww3_2003.data"};
		System.out.println(manager.getWWData(ww3Files));
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
