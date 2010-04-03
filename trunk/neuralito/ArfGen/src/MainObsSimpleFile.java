

import java.util.Vector;

import Observations.ObsData;
import Observations.ObsDataLoader;

public class MainObsSimpleFile { 
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Vector<ObsData> obsDataSet;
	// Set data files for load
		String[] obsFiles = new String[]{"1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004"};		
		
	//	Load buoy data Ww3 Vobs
		obsDataSet  = new ObsDataLoader().loadObsData(obsFiles);
	}
	
	

}
