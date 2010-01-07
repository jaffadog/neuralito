package edu.unicen.surfforecaster.server.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessResourceFailureException;

import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.Point;
import edu.unicen.surfforecaster.server.domain.wavewatch.ForecastFile;
import edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystem;

public interface WaveWatchSystemPersistenceI {

	public abstract void init() throws IOException,
			DataAccessResourceFailureException, HibernateException,
			IllegalStateException, SQLException, URISyntaxException;

	/**
	 * @see edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystem#getArchivedForecasts(float,
	 *      float, java.util.GregorianCalendar, java.util.GregorianCalendar)
	 */
	public abstract List<Forecast> getArchivedForecasts(final Point point,
			final Date fromDate, final Date toDate);

	/**
	 * @see edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystem#getForecasts(edu.unicen.surfforecaster.server.domain.entity.forecasters.Point)
	 */
	public abstract List<Forecast> getLatestForecasts(final Point gridPoint);

	/**
	 * @return the date of the latest forecasts received from NOAA. Null if no
	 *         latest forecast exists.
	 * @throws SQLException
	 * @throws IllegalStateException
	 * @throws HibernateException
	 * @throws DataAccessResourceFailureException
	 */
	public abstract Date getLatestForecastTime();

	/**
	 * @return
	 */
	public abstract List<Point> getValidGridPoints();

	/**
	 * Replace latest forecast with the forecasts in the latestForecastFile.
	 * Before performing the replace latest forecast are archived.
	 * 
	 * @param latestForecastsFile
	 */
	public abstract void updateLatestForecast(ForecastFile latestForecastsFile);

	public boolean isGridPoint(Point point);

	public void importIntoArchive(ForecastFile forecastsToArchive);

}