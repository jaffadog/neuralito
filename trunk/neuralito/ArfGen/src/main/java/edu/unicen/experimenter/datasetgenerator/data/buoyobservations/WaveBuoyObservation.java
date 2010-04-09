package edu.unicen.experimenter.datasetgenerator.data.buoyobservations;

import java.io.Serializable;
import java.util.Calendar;

import edu.unicen.experimenter.datasetgenerator.data.WaveData;
import edu.unicen.experimenter.util.Util;


public class WaveBuoyObservation implements Serializable, WaveData, Comparable {

	private Calendar date = null;
	private Double waveHeight = null;
	private Double wavePeriod = null;
	private Double waveDirection = null;

	public Calendar getDate() {
		return date;
	}

	public void setDate(final Calendar date) {
		this.date = date;
	}

	public double getWaveDirection() {
		return waveDirection;
	}

	public void setWaveDirection(final Double waveDirection) {
		this.waveDirection = waveDirection;
	}

	public double getWaveHeight() {
		return waveHeight;
	}

	public void setWaveHeight(final Double waveHeight) {
		this.waveHeight = waveHeight;
	}

	public double getWavePeriod() {
		return wavePeriod;
	}

	public void setWavePeriod(final Double wavePeriod) {
		this.wavePeriod = wavePeriod;
	}

	public boolean equalsDate(final Calendar date) {
		// System.out.println(this.date.getTime() + " = "
		// +this.date.get(Calendar.YEAR) + "/" + this.date.get(Calendar.MONTH) +
		// "/" + this.date.get(Calendar.DAY_OF_MONTH));
		// System.out.println(date.getTime() + " = " +date.get(Calendar.YEAR) +
		// "/" + date.get(Calendar.MONTH) + "/" +
		// date.get(Calendar.DAY_OF_MONTH));
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

	@Override
	public String toString() {
		return "Buoy: " + Util.getDateFormatter().format(date.getTime())
				+ " WH:" + Util.getDecimalFormatter().format(waveHeight)
				+ " WP:" + Util.getDecimalFormatter().format(wavePeriod)
				+ " WD:" + Util.getDecimalFormatter().format(waveDirection);
	}

	public int compareTo(final Object o) {
		final WaveBuoyObservation buoyData = (WaveBuoyObservation) o;
		final int result = getDate().compareTo(buoyData.getDate());

		if (result < 0)
			return -1;
		if (result > 0)
			return 1;

		return 0;
	}
}
