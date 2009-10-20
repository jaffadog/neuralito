/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity;

import java.security.InvalidParameterException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import edu.unicen.surfforecaster.common.services.dto.Unit;

/**
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
	 * The unit in which the observation is made.Should be in meters.
	 */
	@Enumerated(EnumType.STRING)
	private Unit waveUnit;

	/**
	 * 
	 */
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
			final Date observationDate, final Unit waveUnit) {
		super();
		validate(waveUnit);
		this.waveHeight = waveHeight;
		this.observationDate = observationDate;
		this.waveUnit = waveUnit;

	}

	/**
	 * @param waveUnit2
	 */
	private void validate(final Unit waveUnit2) {
		if (waveUnit2 != Unit.Meters)
			throw new InvalidParameterException("Wave unit must be in meters");
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
	public Date getObservationDate() {
		return observationDate;
	}

	/**
	 * @return the waveUnit
	 */
	public Unit getWaveUnit() {
		return waveUnit;
	}

}
