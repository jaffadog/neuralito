package edu.unicen.surfforecaster.gwt.server.util;

import java.io.InputStream;
import java.util.Calendar;
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
			VisualObservationDTO visualObservationDTO = this.generateData(lineValues);
			if (!this.existSameObservationDate(obsData, visualObservationDTO))
				obsData.add(visualObservationDTO);
		}
		return obsData;
	}

	/**
	 * 	specific method to generate the data, depends of the input file structure
	 * @param lineValues
	 * @return
	 */
	private VisualObservationDTO generateData(Vector<String> lineValues){
		
		
		//Date date = new Date(new Integer(lineValues.elementAt(0)), new Integer(lineValues.elementAt(1)) - 1, new Integer(lineValues.elementAt(2)));
		Calendar date = new GregorianCalendar(new SimpleTimeZone(0,"UTC"));
		date.set(new Integer(lineValues.elementAt(0)), new Integer(lineValues.elementAt(1)) - 1, new Integer(lineValues.elementAt(2)));

		//date.set(new Integer(lineValues.elementAt(0)), new Integer(lineValues.elementAt(1)) - 1, new Integer(lineValues.elementAt(2)));
		double waveHeight = new Double((String)lineValues.elementAt(3));
		VisualObservationDTO data = new VisualObservationDTO(waveHeight, date.getTime(), Unit.Meters);
		
		return data;
	}
	
	/**
	 * Checks if the date of the visualObservationDTO already exists in the obsData collection
	 * @param obsData
	 * @param visualObservationDTO
	 * @return
	 */
	private boolean existSameObservationDate(Vector<VisualObservationDTO> obsData, VisualObservationDTO visualObservationDTO) {
		for (Enumeration<VisualObservationDTO> e = obsData.elements(); e.hasMoreElements();) {
			VisualObservationDTO observation = e.nextElement();
			if (observation.equalsDate(visualObservationDTO.getObservationDate()))
				return true;
		}
		return false;
	}
}
