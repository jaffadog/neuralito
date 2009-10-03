/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import java.util.Collection;
import java.util.Date;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.WW3ForecastArchive;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.WW3LatestForecast;

/**
 * @author esteban
 * 
 */
public interface WW3DAO {

	/**
	 * Obtain the forecasts for the given grid points
	 * 
	 * @param gridPoints
	 * @return
	 */
	Collection<Forecast> getLatestForecast(Collection<Point> gridPoints);

	/**
	 * Obtain all the cero hour forecast for the specified date range.
	 * 
	 * @param from
	 * @param to
	 * @param gridPoints
	 * @return
	 */
	Collection<Forecast> getForecastForDate(Date from, Date to,
			Collection<Point> gridPoints);

	/**
	 * @param latestForecasts
	 */
	public void save(WW3LatestForecast latestForecasts);

	/**
	 * @param ww3archive
	 */
	void save(WW3ForecastArchive ww3archive);

	/**
	 * @return
	 */
	WW3LatestForecast getLatestForecast();

	/**
	 * @return
	 */
	WW3ForecastArchive getWW3Archive();

}
