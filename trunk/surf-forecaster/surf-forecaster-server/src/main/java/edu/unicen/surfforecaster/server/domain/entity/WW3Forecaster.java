/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import edu.unicen.surfforecaster.server.domain.WaveWatchSystem;

/**
 * Forecaster which uses a {@link WaveWatchSystem} to obtain forecasts.
 * 
 * @author esteban
 * 
 */
@Entity
public class WW3Forecaster extends Forecaster {

	/**
	 * Grid point of the model to extract forecasts.
	 */
	@ManyToOne()
	private Point gridPoint;

	/**
	 * Forecast location
	 */
	@ManyToOne()
	private Point location;
	/**
	 * The wave watch model to use.
	 */
	@Transient
	private WaveWatchSystem model;
	/**
	 * The wave watch model name.
	 */
	private String modelName;

	/**
	 * 
	 */

	public WW3Forecaster() {

		// ORM purpose
	}

	/**
	 * @param configuration
	 */
	public WW3Forecaster(final WaveWatchSystem model, final Point point,
			final Point location, Spot spot) {
		this.gridPoint = point;
		this.location = location;
		this.model = model;
		this.modelName = model.getName();
		this.spot = spot;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecast.Forecaster#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Forecaster that uses NOAA wave watch output.";
	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecast.Forecaster#getDescription()
	 */
	@Override
	public String getName() {
		return "WW3 Noaa Forecaster";
	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecast.Forecaster#getForecast()
	 */
	@Override
	public Collection<Forecast> getLatestForecasts() {
		return model.getLatestForecast(gridPoint);
	}

	/**
	 * @return the location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecasters.Forecaster#getArchivedForecasts(java.util.Date,
	 *      java.util.Date)
	 */
	@Override
	public Collection<Forecast> getArchivedForecasts(final Date from,
			final Date to) {
		return model.getArchivedForecasts(gridPoint, from, to);
	}

}
