package edu.unicen.experimenter.datasetgenerator.data.visualobservation;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Vector;

import edu.unicen.experimenter.util.FileDataIO;
import edu.unicen.experimenter.util.Util;

public class VisualObservationLoader {

	private FileDataIO fileReader = null;
	private final String[] availableYears = { "1997", "1998", "1999", "2000",
			"2001", "2002", "2003", "2004" };

	public VisualObservationLoader() {
		fileReader = new FileDataIO();
	}

	public Vector<VisualObservation> loadAllVisualObservations() {
		return loadVisualObservations(availableYears);
	}

	public Vector<VisualObservation> loadVisualObservations(final String[] years) {
		final Vector<VisualObservation> obsData = new Vector<VisualObservation>();
		for (int f = 0; f < years.length; f++) {
			final String fileName = ".//files//observations//oahu" + years[f]
					+ ".dat";
			final Vector<Vector<String>> linesValues = fileReader
					.readFile(fileName);
			for (final Enumeration<Vector<String>> e = linesValues.elements(); e
					.hasMoreElements();) {
				final Vector<String> lineValues = e.nextElement();
				obsData.add(generateData(lineValues));
			}
			fileReader = new FileDataIO();
		}
		return obsData;
	}

	// specific method to generate the data, depends of the input file structure
	private VisualObservation generateData(final Vector<String> lineValues) {
		final VisualObservation data = new VisualObservation();

		// date

		final Calendar date = new GregorianCalendar(Util.UTC_TIME_ZONE);
		date.set(new Integer(lineValues.elementAt(0)), new Integer(lineValues
				.elementAt(1)) - 1, new Integer(lineValues.elementAt(2)));
		data.setDate(date);

		// North Shore height
		data.setWaveHeight("nshore", new Double(lineValues.elementAt(3)));

		// West shore height
		data.setWaveHeight("wshore", new Double(lineValues.elementAt(4)));

		// Ala Moana height
		data.setWaveHeight("almo", new Double(lineValues.elementAt(5)));

		// Diamond head height
		data.setWaveHeight("dh", new Double(lineValues.elementAt(6)));

		// WindWard height
		data.setWaveHeight("windward", new Double(lineValues.elementAt(7)));

		return data;
	}

	/**
	 * @param beach
	 * @return
	 */

}
