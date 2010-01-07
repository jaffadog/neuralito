package edu.unicen.surfforecaster.common.services.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.Validate;

/**
 * DTO for a visual observation.
 * @author esteban
 *
 */
public class VisualObservationDTO implements Serializable {

	public VisualObservationDTO() {

	}
	/**
	 * The observed wave height.
	 */
	private double waveHeight;
	/**
	 * The date in which the observation was made.
	 */
	private Date observationDate;
	/**
	 * The unit in which the observation was made.
	 */
	private Unit unit;
	
	/**
	 * @param waveHeight
	 * @param observationDate
	 * @param waveUnit
	 * @param location
	 */
	public VisualObservationDTO(final double waveHeight,
			final Date observationDate, Unit unit) {
		super();
		Validate.notNull(observationDate);
		validateWaveheight(waveHeight);
		validateUnit(unit);
		this.waveHeight = waveHeight;
		this.observationDate = observationDate;
		

	}

	private void validateUnit(Unit unit) {
		// TODO validate only meters and feet unit should be used.
		
	}

	private void validateWaveheight(double waveHeight2) {
		// if (waveHeight2< 0 )
		// throw new InvalidParameterException();
	}

	/**
	 * @return the waveHeight
	 */
	public double getWaveHeight() {
		return waveHeight;
	}

	/**
	 * @return the observationDate
	 */
	public Date getObservationDate() {
		return observationDate;
	}
	/**
	 * 
	 * @return the unit in which the visual observation was made.
	 */
	public Unit getUnit() {
		return unit;
	}


	public boolean equalsDate(Date date){
		Calendar observationDay = new GregorianCalendar();
		observationDay.setTime(observationDate);
		
		Calendar calendarDate = new GregorianCalendar();
		observationDay.setTime(date);
		if (observationDay.get(Calendar.YEAR) == calendarDate.get(Calendar.YEAR))
			if (observationDay.get(Calendar.MONTH) == calendarDate.get(Calendar.MONTH))
				if (observationDay.get(Calendar.DAY_OF_MONTH) == calendarDate.get(Calendar.DAY_OF_MONTH))
					return true;
		return false;
	}
	
	public boolean equalsDateTime(Calendar date){
		Calendar observationDay = new GregorianCalendar();
		if (observationDay.get(Calendar.YEAR) == date.get(Calendar.YEAR))
			if (observationDay.get(Calendar.MONTH) == date.get(Calendar.MONTH))
				if (observationDay.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH))
					if (observationDay.get(Calendar.HOUR_OF_DAY) == date.get(Calendar.HOUR_OF_DAY))
						if (observationDay.get(Calendar.MINUTE) == date.get(Calendar.MINUTE))
							return true;
		return false;
	}

}
