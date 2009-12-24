/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity;

import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.Validate;

import edu.unicen.surfforecaster.common.services.dto.Unit;

/**
 * Record of a visual observation of a wave height.
 * @author esteban
 * 
 */
@Entity
public class VisualObservation {
	
	/**
	 * The id for ORM pupose.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(length = 11)
	private Integer id;
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
	@Enumerated(EnumType.STRING)
	private Unit unit;
	
	public VisualObservation() {
		// Orm purpose
	}

	/**
	 * @param waveHeight
	 * @param observationDate
	 * @param waveUnit
	 * @param location
	 */
	public VisualObservation(final double waveHeight,
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
		if (waveHeight2< 0 )
			throw new InvalidParameterException();
	}

	public Integer getId() {
		return id;
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
	public Date getDate() {
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
