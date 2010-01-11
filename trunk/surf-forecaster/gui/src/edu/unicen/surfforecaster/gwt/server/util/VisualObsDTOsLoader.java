package edu.unicen.surfforecaster.gwt.server.util;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.Vector;

import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.VisualObservationDTO;



public class VisualObsDTOsLoader {

	private FileDataIO fileReader= null;
	private static VisualObsDTOsLoader instance = null;
	
	public static VisualObsDTOsLoader getInstance() {
        if (instance == null) {
            instance = new VisualObsDTOsLoader();
        }
        return instance;
    }
	
	private VisualObsDTOsLoader(){}
	
	public Vector<VisualObservationDTO> loadObsData(InputStream is) throws Exception{
		this.fileReader = new FileDataIO();
		Vector<Vector<String>> linesValues = this.fileReader.readFile(is);
		Vector<VisualObservationDTO> obsData = new Vector<VisualObservationDTO>();
		for (Enumeration<Vector<String>> e = linesValues.elements(); e.hasMoreElements();){
			Vector<String> lineValues = e.nextElement();
			obsData.add(this.generateData(lineValues));
		}
		return obsData;
	}
	
	//	specific method to generate the data, depends of the input file structure
	private VisualObservationDTO generateData(Vector<String> lineValues){
		
		
		//Date date = new Date(new Integer(lineValues.elementAt(0)), new Integer(lineValues.elementAt(1)) - 1, new Integer(lineValues.elementAt(2)));
		Calendar date = new GregorianCalendar(new SimpleTimeZone(0,"UTC"));
		date.set(new Integer(lineValues.elementAt(0)), new Integer(lineValues.elementAt(1)) - 1, new Integer(lineValues.elementAt(2)));

		//date.set(new Integer(lineValues.elementAt(0)), new Integer(lineValues.elementAt(1)) - 1, new Integer(lineValues.elementAt(2)));
		double waveHeight = new Double((String)lineValues.elementAt(3));
		VisualObservationDTO data = new VisualObservationDTO(waveHeight, date.getTime(), Unit.Meters);
		
		return data;
	}
}
