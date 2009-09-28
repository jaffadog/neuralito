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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import edu.unicen.surfforecaster.common.services.dto.Unit;

/**
 * @author esteban
 * 
 */
@Entity
public class WW3Forecaster extends Forecaster {
	/**
	 * The id for ORM pupose.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(length = 11)
	private Integer id;

	@Override
	public Integer getId() {
		return id;
	}

	public static final Collection<Point> getSurroundingGridPoints(
			final Point location) {
		return null;
	}

	public static final Collection<Point> getAllGridPoints() {
		return null;
	}

	/**
	 * Grid points used for giving the forecast.
	 */
	@ManyToMany
	private Collection<Point> gridPoints;
	/**
	 * Forecast location
	 */
	@ManyToOne
	private Point location;

	/**
	 * 
	 */
	public WW3Forecaster() {

	}

	/**
	 * @param configuration
	 */
	public WW3Forecaster(final Collection<Point> gridPoints,
			final Point location) {
		this.gridPoints = gridPoints;
		this.location = location;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecaster#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Forecaster that uses NOAA wave watch output.";
	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecaster#getDescription()
	 */
	@Override
	public String getName() {
		return "WW3 Noaa Forecaster";
	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecaster#getForecast()
	 */
	@Override
	public Collection<Forecast> getForecasts() {
		final Collection<Forecast> forecasts = new ArrayList<Forecast>();
		final Map<String, ForecastAttribute> attributes = new HashMap<String, ForecastAttribute>();
		attributes.put("wave height", new ForecastAttribute("wave height", 2D,
				Unit.Meters));
		final Forecast forecast = new Forecast(new Date(), new Date(),
				new Date(), attributes, this);
		forecasts.add(forecast);
		return forecasts;
	}

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
