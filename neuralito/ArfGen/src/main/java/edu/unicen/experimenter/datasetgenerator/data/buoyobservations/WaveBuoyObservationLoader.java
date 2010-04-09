package edu.unicen.experimenter.datasetgenerator.data.buoyobservations;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Vector;

import edu.unicen.experimenter.util.FileDataIO;
import edu.unicen.experimenter.util.Util;



public class WaveBuoyObservationLoader {
	
	private FileDataIO fileReader= null;
	
	public WaveBuoyObservationLoader(){
		this.fileReader = new FileDataIO();
	}
	
	public Vector<WaveBuoyObservation> loadBuoyData(String buoyAndyear){
		Vector<Vector<String>> linesValues = this.fileReader.readFile(".//files//buoys//" + buoyAndyear + ".txt");
		Vector<WaveBuoyObservation> buoyData = new Vector<WaveBuoyObservation>();
		for (Enumeration<Vector<String>> e = linesValues.elements(); e.hasMoreElements();){
			Vector<String> lineValues = e.nextElement();
			buoyData.add(this.generateData(lineValues));
		}
		return buoyData;
	}
	
	public Vector<WaveBuoyObservation> loadBuoyData(String[] buoyAndyears){
		Vector<WaveBuoyObservation> buoyData = new Vector<WaveBuoyObservation>();
		for (int f = 0; f < buoyAndyears.length; f++){
			String fileName = ".//files//buoys//" + buoyAndyears[f] + ".txt";
			Vector<Vector<String>> linesValues = this.fileReader.readFile(fileName);
			for (Enumeration<Vector<String>> e = linesValues.elements(); e.hasMoreElements();){
				Vector<String> lineValues = e.nextElement();
				buoyData.add(this.generateData(lineValues));
			}
			this.fileReader = new FileDataIO();
		}
		return buoyData;
	}
//	specific method to generate the data, depends of the input file structure
	private WaveBuoyObservation generateData(Vector<String> lineValues){
		WaveBuoyObservation data = new WaveBuoyObservation();
		
		//date
		Calendar date = new GregorianCalendar(Util.UTC_TIME_ZONE);
		date.set(new Integer(lineValues.elementAt(0)), new Integer(lineValues.elementAt(1)) - 1, new Integer(lineValues.elementAt(2)), new Integer(lineValues.elementAt(3)), new Integer(lineValues.elementAt(4)));
		//Calendar date = new GregorianCalendar(new Integer(lineValues.elementAt(0)), new Integer(lineValues.elementAt(1)) - 1, new Integer(lineValues.elementAt(2)), new Integer(lineValues.elementAt(3)), new Integer(lineValues.elementAt(4)));
		data.setDate(date);
			
		//wave height
		data.setWaveHeight(new Double((String)lineValues.elementAt(5)));
		
		//wave period
		data.setWavePeriod(new Double((String)lineValues.elementAt(6)));
		
		//wave direction
		data.setWaveDirection(new Double((String)lineValues.elementAt(7)));
		
		return data;
	}

}
