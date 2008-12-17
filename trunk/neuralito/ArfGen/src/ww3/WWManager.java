package ww3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Vector;

public class WWManager {
	
public Collection getWWData(){
	// Read from disk using FileInputStream.
	FileInputStream f_in;
	try {
		f_in = new FileInputStream("files/ww3_2002.data");
		// Read object using ObjectInputStream.
		ObjectInputStream obj_in;
		obj_in = new ObjectInputStream(f_in);
		// Read an object.
		Object obj;
		obj = obj_in.readObject();
		Vector vec = (Vector) obj;
		return vec;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
}
public static void loadDataFromGrib() {
	String day;
	Collection waveData = new Vector();
	for (int i = 1; i <= 12; i++) {
		if (i < 10)
			day = "0" + Integer.toString(i);
		else
			day = Integer.toString(i);
		WaveWatchReader reader = new WaveWatchReader(
				"files/WW3_2002/nww3.hs.2002" + day + ".grb",
				"files/WW3_2002/nww3.tp.2002" + day + ".grb",
				"files/WW3_2002/nww3.dp.2002" + day + ".grb");
		waveData.addAll(reader.getWaveWatchData(22.0, -158.75));
	}
	
	try {
		// Use a FileOutputStream to send data to a file
		// called myobject.data.

		FileOutputStream f_out = new FileOutputStream("files/ww3_2002.data");

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
}

}
