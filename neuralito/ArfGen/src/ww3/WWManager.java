package ww3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Vector;

import org.apache.log4j.Logger;

public class WWManager {
	private static Logger logger = Logger.getLogger(WWManager.class);
	public Collection getWWData(String[] years, double latitude,
			double longitude) {
		Vector wwData = new Vector();
		for (int i = 0; i < years.length; i++) {
			String fileName = ".//files//ww3//ww3_" + years[i] + "Lat("+latitude+")Long("+longitude+ ").data";
			// Read from disk using FileInputStream.
			FileInputStream f_in;
			try {
				f_in = new FileInputStream(fileName);
				// Read object using ObjectInputStream.
				ObjectInputStream obj_in;
				obj_in = new ObjectInputStream(f_in);
				// Read an object.
				Object obj;
				obj = obj_in.readObject();
				Vector vec = (Vector) obj;
				wwData.addAll(vec);
				// return vec;
			} catch (Exception e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return wwData;
	}

	/**
	 *Loads all the gribs for the specified year and for the specified Coords, and save them into a .Data file with the corresponding label 
	 */
	public static void loadDataFromGrib(String[] years, double latitude, double longitude) {
		for (int j = 0; j < years.length; j++) {
			String day;
			Collection waveData = new Vector();
			for (int i  = 1; i <= 12; i++) {
					if (i < 10)
						day = "0" + Integer.toString(i);
					else
						day = Integer.toString(i);
					GribReader reader = new GribReader(
							"files/WW3.gribs/nww3.hs."+ years[j] + day + ".grb",
							"files/WW3.gribs/nww3.tp."+ years[j]  + day + ".grb",
							"files/WW3.gribs/nww3.dp."+ years[j]  + day + ".grb",
							"files/WW3.gribs/nww3.wind."+ years[j]  + day + ".grb");
					waveData.addAll(reader.getWaveWatchData(latitude, longitude));
					
				}
			

			try {
				// Use a FileOutputStream to send data to a file
				// called myobject.data.

				FileOutputStream f_out = new FileOutputStream(".//files//ww3//ww3_" + years[j] + "Lat("+latitude+")Long("+longitude+ ").data" );

				// Use an ObjectOutputStream to send object data to the
				// FileOutputStream for writing to disk.
				ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
				// Pass our object to the ObjectOutputStream's
				// writeObject() method to cause it to be written out
				// to disk.
				obj_out.writeObject(waveData);
			} catch (IOException e) {
				e.printStackTrace();
			}
			logger.info("Added year: " + years[j] );

					}
	}
}
