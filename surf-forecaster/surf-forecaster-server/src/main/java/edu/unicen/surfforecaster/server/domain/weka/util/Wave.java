package edu.unicen.surfforecaster.server.domain.weka.util;

import java.util.Calendar;

public interface Wave extends Comparable {
	
	public double getWaveHeight();
	
	public double getWavePeriod();
	
	public double getWaveDirection();
	
	public Calendar getDate();
	
	public int compareTo(Object o);
	
	public boolean equalsDate(Calendar date);
	
	public boolean equalsDateTime(Calendar date);
}
