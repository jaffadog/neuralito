package Observations;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Vector;

import util.Util;
import weka.FileDataIO;



public class ObsDataLoader {

	private FileDataIO fileReader= null;
	
	public ObsDataLoader(){
		this.fileReader = new FileDataIO();
	}
	
	public Vector<ObsData> loadObsData(String year){
		Vector<Vector<String>> linesValues = this.fileReader.readFile(".//files//observations//oahu" + year + ".dat");
		Vector<ObsData> obsData = new Vector<ObsData>();
		for (Enumeration<Vector<String>> e = linesValues.elements(); e.hasMoreElements();){
			Vector<String> lineValues = e.nextElement();
			obsData.add(this.generateData(lineValues));
		}
		return obsData;
	}
	
	public Vector<ObsData> loadObsData(String[] years){
		Vector<ObsData> obsData = new Vector<ObsData>();
		for (int f = 0; f < years.length; f++){
			String fileName = ".//files//observations//oahu" + years[f] + ".dat";
			Vector<Vector<String>> linesValues = this.fileReader.readFile(fileName);
			for (Enumeration<Vector<String>> e = linesValues.elements(); e.hasMoreElements();){
				Vector<String> lineValues = e.nextElement();
				obsData.add(this.generateData(lineValues));
			}
			this.fileReader = new FileDataIO();
		}
		return obsData;
	}
	
//	specific method to generate the data, depends of the input file structure
	private ObsData generateData(Vector<String> lineValues){
		ObsData data = new ObsData();
		
		//date
		
		Calendar date = new GregorianCalendar(Util.UTC_TIME_ZONE);
		date.set(new Integer(lineValues.elementAt(0)), new Integer(lineValues.elementAt(1)) - 1, new Integer(lineValues.elementAt(2)));
		data.setDate(date);
		
		//North Shore height
		data.setWaveHeight("nshore", new Double((String)lineValues.elementAt(3)));
		
		//West shore height
		data.setWaveHeight("wshore", new Double((String)lineValues.elementAt(4)));
		
		//Ala Moana height
		data.setWaveHeight("almo", new Double((String)lineValues.elementAt(5)));
		
		//Diamond head height
		data.setWaveHeight("dh", new Double((String)lineValues.elementAt(6)));
		
		//WindWard height
		data.setWaveHeight("windward", new Double((String)lineValues.elementAt(7)));
		
		return data;
	}

}
