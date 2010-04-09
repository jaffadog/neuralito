package edu.unicen.experimenter.datasetgenerator.data;

import java.util.Calendar;

/**
 * 
 * A wave
 * 
 */
public interface WaveData extends Comparable {

	public double getWaveHeight();

	public double getWavePeriod();

	public double getWaveDirection();

	public Calendar getDate();

	public int compareTo(Object o);

	public boolean equalsDate(Calendar date);

	public boolean equalsDateTime(Calendar date);
}
