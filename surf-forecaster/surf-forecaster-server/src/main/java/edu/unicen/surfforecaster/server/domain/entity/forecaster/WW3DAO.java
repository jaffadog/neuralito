/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster;

import java.util.Collection;
import java.util.Date;

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

}
