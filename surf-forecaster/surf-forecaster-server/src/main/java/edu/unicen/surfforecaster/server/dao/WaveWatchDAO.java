/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import java.io.File;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastArch;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ValidGridPoints;

/**
 * @author esteban
 * 
 */
public interface WaveWatchDAO {

	/**
	 * Obtain the forecasts for the given grid point
	 * 
	 * @param gridPoint
	 * @return
	 */
	public Collection<Forecast> getLatestForecast(Point gridPoint);

	/**
	 * @param validGridPoints
	 */
	public void save(ValidGridPoints validGridPoints);

	/**
	 * @return
	 */
	public ValidGridPoints getValidGridPoints();

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
	 * Insert all the data from the given file into LatestForecast table.
	 * 
	 * @param textFile
	 */
	public void insertIntoLatestForecastFromFile(File textFile);

}
