package edu.unicen.experimenter.datasetgenerator.data.visualobservation;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Hashtable;

import edu.unicen.experimenter.util.Util;

/**
 * 
 * 
 * 
 */

public class VisualObservation implements Serializable {

	private Calendar date = null;
	/**
	 * key = Beach ; value = MaxWaveHeight observed during the day at that
	 * beach.
	 */

	Hashtable<String, Double> observations = new Hashtable<String, Double>();

	/**
	 * Convert the given shore wave height into through to crest scale meters
	 * (TCS).
	 * 
	 * @param wave
	 *            height observed from shore in hawaiian scale feet (HSF)
	 */
	private Double HSFtoTCS(final Double value) {
		return value * 0.3048 * 2; // *0.30 is to convert foot to meter, and *2
		// is to convert from Hawaiaan Scale Feet to
		// Through To Crest Scale Feet.
	}

	public Double getWaveHeight(final String beach) {
		return observations.get(beach);
	}

	public void setWaveHeight(final String beach, final Double waveHeight) {
		observations.put(beach, HSFtoTCS(waveHeight));
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(final Calendar date) {
		this.date = date;
	}

	public boolean equalsDate(final Calendar date) {
		if (this.date.get(Calendar.YEAR) == date.get(Calendar.YEAR))
			if (this.date.get(Calendar.MONTH) == date.get(Calendar.MONTH))
				if (this.date.get(Calendar.DAY_OF_MONTH) == date
						.get(Calendar.DAY_OF_MONTH))
					return true;
		return false;
	}

	public boolean equalsDateTime(final Calendar date) {
		if (this.date.get(Calendar.YEAR) == date.get(Calendar.YEAR))
			if (this.date.get(Calendar.MONTH) == date.get(Calendar.MONTH))
				if (this.date.get(Calendar.DAY_OF_MONTH) == date
						.get(Calendar.DAY_OF_MONTH))
					if (this.date.get(Calendar.HOUR_OF_DAY) == date
							.get(Calendar.HOUR_OF_DAY))
						if (this.date.get(Calendar.MINUTE) == date
								.get(Calendar.MINUTE))
							return true;
		return false;
	}

	public String print(final String beach) {
		final String date = Util.getDateFormatter().format(this.date.getTime());
		final String string = "V.Obs: " + date + " WH: "
				+ Util.getDecimalFormatter().format(observations.get(beach));
		return string;

	}

}
