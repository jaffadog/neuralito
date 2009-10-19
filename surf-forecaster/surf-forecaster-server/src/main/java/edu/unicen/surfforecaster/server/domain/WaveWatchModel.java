/**
 * 
 */
package edu.unicen.surfforecaster.server.domain;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastArch;

/**
 * Wave Watch Model abstraction. Each Wave Watch model is responsible for
 * providing the latest forecast and the archive of forecast. Also it
 * responsible for describing the grid points surrounding a given location.
 * 
 * @author esteban
 * 
 */
public interface WaveWatchModel {

	/**
	 * Obtain the latest forecast for the given grid point.
	 * 
	 * @param gridPoint
	 * @return
	 */
	public List<ForecastArch> getLatestForecast(Point gridPoint);

	/**
	 * Obtain the model forecast for the given point in the given time range.
	 * 
	 * @param gridPoint
	 * @param from
	 * @param to
	 * @return
	 */
	public List<ForecastArch> getArchivedForecasts(Point gridPoint,
			GregorianCalendar from, GregorianCalendar to);

	/**
	 * Obtain all the model grid points surrounding the given point.
	 * 
	 * @param point
	 * @return
	 */
	public List<Point> getNearbyGridPoints(final Point point);

	/**
	 * Obtain the date of the latest forecast.
	 * 
	 * @return
	 */
	public Date getLatestForecastTime();

	/**
	 * @param point
	 * @return
	 */
	public boolean isGridPoint(Point point);

}