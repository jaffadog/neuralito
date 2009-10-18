/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import edu.unicen.surfforecaster.server.domain.WaveWatchModel;

/**
 * Wave Watch 3 Forecaster. This forecaster will use NOAA wave watch 3 output to
 * generate the forecast for the given grid points.
 * 
 * @author esteban
 * 
 */
@Entity
public class WW3Forecaster extends Forecaster {

	/**
	 * Grid point of the model to extract forecasts.
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	private Point gridPoint;

	/**
	 * Forecast location
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	private Point location;
	/**
	 * The wave watch model to use.
	 */
	@Transient
	private WaveWatchModel model;
	@Column(nullable = false, length = 100)
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
	public WW3Forecaster(final String modelName, final Point point,
			final Point location) {
		gridPoint = point;
		this.location = location;
		this.modelName = modelName;
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
		final Collection<Forecast> forecasts = new ArrayList<Forecast>();
		final Map<String, ForecastParameter> attributes = new HashMap<String, ForecastParameter>();
		model.getLatestForecast(gridPoint);
		return forecasts;
	}

	/**
	 * @return the location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecaster#getArchivedForecasts(java.util.Date,
	 *      java.util.Date)
	 */
	@Override
	public Collection<Forecast> getArchivedForecasts(final Date from,
			final Date to) {
		final GregorianCalendar fr = new GregorianCalendar();
		fr.setTime(from);
		final GregorianCalendar too = new GregorianCalendar();
		too.setTime(to);
		// return model.getArchivedForecasts(location, from, too);
		return null;
	}

	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(final WaveWatchModel model) {
		this.model = model;
	}

}
