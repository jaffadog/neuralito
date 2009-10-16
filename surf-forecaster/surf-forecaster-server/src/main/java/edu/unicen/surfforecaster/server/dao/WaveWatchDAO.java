/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastArchive;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastPoints;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.LatestForecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ValidGridPoints;
import edu.unicen.surfforecaster.server.services.ForecastArch;

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
	 * @param validGridPoints
	 */
	public void save(ValidGridPoints validGridPoints);

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

	/**
	 * @return
	 */
	public ValidGridPoints getValidGridPoints();

	/**
	 * 
	 * @param latestForecasts
	 */
	void save(LatestForecast latestForecasts);

	/**
	 * @param latestForecasts
	 */
	void saveDirect(LatestForecast latestForecasts);

	/**
	 * @param forecasts
	 */
	public void saveDirect(List forecasts);

	/**
	 * @param lat
	 * @param lon
	 * @param from
	 * @param to
	 * @return
	 */
	public List<ForecastArch> getForecasts(float lat, float lon,
			GregorianCalendar from, GregorianCalendar to);

	/**
	 * Moves the 0h and 3h forecasts from LatestForecasts to ArchiveForecasts.
	 */
	public void archiveLatestForecasts();

	/**
	 * @param textFile
	 */
	public void insertIntoLatestForecastFromFile(File textFile);

}
