package edu.unicen.surfforecaster.gwt.server.util;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.Vector;



public class VisualObsDTOsLoader {

	private FileDataIO fileReader= null;
	private final static  TimeZone UTC_TIME_ZONE = new SimpleTimeZone(0,"UTC");
	private static VisualObsDTOsLoader instance = null;
	
	public static VisualObsDTOsLoader getInstance() {
        if (instance == null) {
            instance = new VisualObsDTOsLoader();
        }
        return instance;
    }
	
	private VisualObsDTOsLoader(){}
	
	public Vector<ObsData> loadObsData(InputStream is) throws Exception{
		this.fileReader = new FileDataIO();
		Vector<Vector<String>> linesValues = this.fileReader.readFile(is);
		Vector<ObsData> obsData = new Vector<ObsData>();
		for (Enumeration<Vector<String>> e = linesValues.elements(); e.hasMoreElements();){
			Vector<String> lineValues = e.nextElement();
			obsData.add(this.generateData(lineValues));
		}
		return obsData;
	}
	
	//	specific method to generate the data, depends of the input file structure
	private ObsData generateData(Vector<String> lineValues){
		ObsData data = new ObsData();
		
		//date
		Calendar date = new GregorianCalendar(UTC_TIME_ZONE);
		date.set(new Integer(lineValues.elementAt(0)), new Integer(lineValues.elementAt(1)) - 1, new Integer(lineValues.elementAt(2)));
		data.setDate(date);
		
		data.setWaveHeight(new Double((String)lineValues.elementAt(3)));
		
		return data;
	}

}
