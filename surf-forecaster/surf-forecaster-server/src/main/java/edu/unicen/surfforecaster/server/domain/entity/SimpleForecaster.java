/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import edu.unicen.surfforecaster.common.services.dto.SimpleForecasterDTO;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.WaveWatchParameter;
import edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystem;
import edu.unicen.surfforecaster.server.domain.weka.util.Util;

/**
 * Forecaster which uses a {@link WaveWatchSystem} to obtain forecasts.
 * 
 * @author esteban
 * 
 */
@Entity
public class SimpleForecaster extends Forecaster {

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

	public SimpleForecaster() {

		// ORM purpose
	}

	/**
	 * @param configuration
	 */
	public SimpleForecaster(final WaveWatchSystem model, final Point point,
			final Point location, final Spot spot) {
		gridPoint = point;
		this.location = location;
		this.model = model;
		modelName = model.getName();
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

		List<Forecast> forecasts = model.getForecasts(gridPoint);
		forecasts = addWindDirectionAndSpeed(forecasts);
		return forecasts;
	}

	/**
	 * Adds wind direction and speed parameters to forecasts. Direction and
	 * speed its derived of the WINDU and WINDV parameters.
	 * 
	 * @param forecasts
	 * @return
	 */
	private List<Forecast> addWindDirectionAndSpeed(
			final List<Forecast> forecasts) {

		// Calculate wind direction and wind speed from WINDU and WINDV
		// parameters.
		for (final Forecast forecast : forecasts) {
			final float windU = forecast.getParameter(
					WaveWatchParameter.WINDUComponent_V2.getValue())
					.getfValue();
			final float windV = forecast.getParameter(
					WaveWatchParameter.WINDVComponent_V2.getValue())
					.getfValue();
			final double windDirection = Util.calculateWindDirection(windU,
					windV);
			final double windSpeed = Util.calculateWindSpeed(windU, windV);

			// ADD wind speed and direction parameters to each forecast.
			forecast.addParameter(WaveWatchParameter.WIND_DIRECTION_V2
					.getValue(), new ForecastValue(
					WaveWatchParameter.WIND_DIRECTION_V2.getValue(),
					windDirection, Unit.KilometersPerHour));
			forecast.addParameter(WaveWatchParameter.WIND_SPEED_V2.getValue(),
					new ForecastValue(WaveWatchParameter.WIND_SPEED_V2
							.getValue(), windSpeed, Unit.KilometersPerHour));
		}
		return forecasts;
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

	@Override
	public SimpleForecasterDTO getDTO() {
		return new SimpleForecasterDTO(getId(), getName(), getDescription(),
				getLocation().getDTO());
	}

}
