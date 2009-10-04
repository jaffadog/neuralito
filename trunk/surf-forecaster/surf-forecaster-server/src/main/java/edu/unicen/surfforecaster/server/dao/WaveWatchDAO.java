/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import java.util.Collection;
import java.util.Date;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastArchive;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastPoints;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.LatestForecast;

/**
 * @author esteban
 * 
 */
public interface WaveWatchDAO {

	/**
	 * Obtain the forecasts for the given grid points
	 * 
	 * @param gridPoints
	 * @return
	 */
	public Collection<Forecast> getLatestForecast(Collection<Point> gridPoints);

	/**
	 * Obtain all the cero hour forecast for the specified date range.
	 * 
	 * @param from
	 * @param to
	 * @param gridPoints
	 * @return
	 */
	public Collection<Forecast> getForecastForDate(Date from, Date to,
			Collection<Point> gridPoints);

	/**
	 * @param latestForecasts
	 */
	public void save(LatestForecast latestForecasts);

	/**
	 * @param ww3archive
	 */
	void save(ForecastArchive ww3archive);

	/**
	 * @return
	 */
	public LatestForecast getLatestForecast();

	/**
	 * @return
	 */
	public ForecastArchive getWW3Archive();

	/**
	 * @param points
	 */
	void save(ForecastPoints points);

	/**
	 * @return
	 */
	public ForecastPoints getForecastPoints();

	/**
	 * @param latestForecasts
	 */
	void update(LatestForecast latestForecasts);

	/**
	 * @param ww3archive
	 */
	void update(ForecastArchive ww3archive);

}
