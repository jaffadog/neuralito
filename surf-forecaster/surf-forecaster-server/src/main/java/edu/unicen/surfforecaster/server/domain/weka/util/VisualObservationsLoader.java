package edu.unicen.surfforecaster.server.domain.weka.util;

import java.io.File;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.server.domain.entity.VisualObservation;
import edu.unicen.surfforecaster.server.domain.weka.VisualObservationsManager;

/**
 * Loads visual observations.
 * 
 * @author esteban
 * 
 */
public class VisualObservationsLoader {

	private SpaceDelimitedFileReader fileReader = null;

	public VisualObservationsLoader() {
		this.fileReader = new SpaceDelimitedFileReader();
	}

//	public Vector<VisualObservationsManager> loadObsData(String year) {
//		Vector<Vector<String>> linesValues = this.fileReader
//				.readLines(".//files//observations//oahu" + year + ".dat");
//		Vector<VisualObservationsManager> obsData = new Vector<VisualObservationsManager>();
//		for (Enumeration<Vector<String>> e = linesValues.elements(); e
//				.hasMoreElements();) {
//			Vector<String> lineValues = e.nextElement();
//			obsData.add(this.generateData(lineValues));
//		}
//		return obsData;
//	}

//	public Vector<VisualObservationsManager> loadObsData(String[] years) {
//		Vector<VisualObservationsManager> obsData = new Vector<VisualObservationsManager>();
//		for (int f = 0; f < years.length; f++) {
//			String fileName = ".//files//observations//oahu" + years[f]
//					+ ".dat";
//			Vector<Vector<String>> linesValues = this.fileReader
//					.readLines(fileName);
//			for (Enumeration<Vector<String>> e = linesValues.elements(); e
//					.hasMoreElements();) {
//				Vector<String> lineValues = e.nextElement();
//				obsData.add(this.generateData(lineValues));
//			}
//			this.fileReader = new SpaceDelimitedFileReader();
//		}
//		return obsData;
//	}

//	// specific method to generate the data, depends of the input file structure
//	private VisualObservationsManager generateData(Vector<String> lineValues) {
//		VisualObservationsManager data = new VisualObservationsManager();
//
//		// date
//
//		Calendar date = new GregorianCalendar(Util.UTC_TIME_ZONE);
//		date.set(new Integer(lineValues.elementAt(0)), new Integer(lineValues
//				.elementAt(1)) - 1, new Integer(lineValues.elementAt(2)));
//		data.setDate(date);
//
//		// North Shore height
//		data.setWaveHeight("nshore", new Double((String) lineValues
//				.elementAt(3)));
//
//		// West shore height
//		data.setWaveHeight("wshore", new Double((String) lineValues
//				.elementAt(4)));
//
//		// Ala Moana height
//		data
//				.setWaveHeight("almo", new Double((String) lineValues
//						.elementAt(5)));
//
//		// Diamond head height
//		data.setWaveHeight("dh", new Double((String) lineValues.elementAt(6)));
//
//		// WindWard height
//		data.setWaveHeight("windward", new Double((String) lineValues
//				.elementAt(7)));
//
//		return data;
//	}

	/**
	 * Instantiate {@link VisualObservation} from a text file. Each line in the
	 * file correspond to a visual observation. First 3 columns are for date
	 * following column is the observed wave height.
	 * 
	 * @param file
	 * @param unit the unit in which the heights are reported.
	 * @return
	 */
	public List<VisualObservation> loadVisualObservations(File file, Unit unit) {
		Vector<Vector<String>> lines = this.fileReader
				.readLines(file.getPath());
		Vector<VisualObservation> observations = new Vector<VisualObservation>();
		// For each line, read columns and generate visual observation
		for (Enumeration<Vector<String>> e = lines.elements(); e
				.hasMoreElements();) {
			Vector<String> line = e.nextElement();
			// Obtain date info
			Calendar date = new GregorianCalendar(Util.UTC_TIME_ZONE);
			date.set(new Integer(line.elementAt(0)), new Integer(
					line.elementAt(1)) - 1, new Integer(line
					.elementAt(2)));
			// Obtain wave height info
			double waveHeight = Double.valueOf(line.elementAt(3));
			// Create visual observation.
			VisualObservation vo = new VisualObservation(waveHeight, date
					.getTime());
			observations.add(vo);
		}
		return observations;
	}

}
