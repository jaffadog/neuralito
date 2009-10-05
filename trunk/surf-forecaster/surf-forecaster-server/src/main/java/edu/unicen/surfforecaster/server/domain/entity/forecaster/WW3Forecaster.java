/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import edu.unicen.surfforecaster.common.services.dto.Unit;

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
	 * Grid points used for giving the forecast.
	 */
	@ManyToMany(cascade = CascadeType.ALL)
	private Collection<Point> gridPoints;

	/**
	 * Forecast location
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	private Point location;

	public static final Collection<Point> getNearbyGridPoints(
			final Point location) {

		final Point poin = new Point(1D, 4D);
		final ArrayList<Point> points = new ArrayList<Point>();
		points.add(poin);
		return points;

	}

	public static final Collection<Point> getAllGridPoints() {
		return null;

	}

	/**
	 * 
	 */
	public WW3Forecaster() {
		// ORM purpose
	}

	/**
	 * @param configuration
	 */
	public WW3Forecaster(final Collection<Point> gridPoints, final Point location) {
		this.gridPoints = gridPoints;
		this.location = location;
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
	public Collection<Forecast> getForecasts() {
		final Collection<Forecast> forecasts = new ArrayList<Forecast>();
		final Map<String, ForecastParameter> attributes = new HashMap<String, ForecastParameter>();
		attributes.put("wave height", new ForecastParameter("wave height", 2D,
				Unit.Meters));
		final Forecast forecast = new Forecast(new Date(), 3, attributes, null);
		forecasts.add(forecast);
		return forecasts;
	}

	// private void getForecast() {
	// // get latest forecast for grid points:Collection<Forecast>
	// final Collection<Forecast> forecasts = ww3Dao
	// .getLatestForecast(gridPoints);
	// }

	/**
	 * @return the gridPoints
	 */
	public Collection<Point> getGridPoints() {
		return Collections.unmodifiableCollection(gridPoints);
	}

	/**
	 * @return the location
	 */
	public Point getLocation() {
		return location;
	}

}
