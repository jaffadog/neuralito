/**
 * 
 */
package edu.unicen.surfforecaster.server.domain;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.quartz.Scheduler;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.surfforecaster.server.domain.decoder.GribDecoder;
import edu.unicen.surfforecaster.server.domain.download.DownloaderJob;
import edu.unicen.surfforecaster.server.domain.download.DownloaderJobListener;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.Point;

/**
 * This class provides the latest forecasts issued by the NOAA and also archived
 * forecasts. NOAA place in their FTP servers the latest forecast encoded in
 * GRIB2 format. A downloader automatically download the files posted by NOAA at
 * the ftp server. When a new file is downloaded the downloader notifies this
 * class. After the notification this class decodes the files and obtain the
 * forecast info for all the locations needed and also stores the previous
 * latest forecast in the archive. For space efficiency we only store the
 * forecasts for the 0 hours and +3 hours all others are deleted.
 * 
 * 
 * @author esteban wagner.
 * 
 */
public class WaveWatchSystemV3 extends HibernateDaoSupport implements
		WaveWatchSystem, Observer {
	/**
	 * The logger.
	 */
	Logger log = Logger.getLogger(this.getClass());
	/**
	 * The wave watch system name.
	 */
	private static final String systemName = "Wave Watch System Version 3.0";
	/**
	 * The component to obtain forecasts updates in grib format.
	 */
	GribAccess gribAccess;
	/**
	 * The grib decoder to use.
	 */
	private GribDecoder gribDecoder;
	/**
	 * Component that triggers forecasts update at specified times.
	 */
	private Scheduler scheduler;
	/**
	 * Chron expression at which the forecasts updates should be triggered.
	 */
	private String chronExpression;
	/**
	 * The component used to persist system data.
	 */
	private WaveWatchSystemPersistence persister;
	/**
	 * The wave parameters this system outputs.
	 */
	private static WaveWatchParameter[] parameters = {
			WaveWatchParameter.COMBINED_SWELL_WIND_WAVE_HEIGHT,
			WaveWatchParameter.PRIMARY_WAVE_DIRECTION };

	/**
	 * @see edu.unicen.surfforecaster.server.domain.WaveWatchSystem#getArchivedForecasts(float,
	 *      float, java.util.GregorianCalendar, java.util.GregorianCalendar)
	 */
	@Override
	public List<Forecast> getArchivedForecasts(final Point point,
			final Date fromDate, final Date toDate) {
		return null;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.WaveWatchSystem#getLatestForecast(edu.unicen.surfforecaster.server.domain.entity.forecasters.Point)
	 */
	@Override
	public List<Forecast> getLatestForecast(final Point gridPoint) {
		return null;
	}

	/**
	 * @return the date of the latest forecasts received from NOAA. Null if no
	 *         latest forecast exists.
	 * @throws SQLException
	 * @throws IllegalStateException
	 * @throws HibernateException
	 * @throws DataAccessResourceFailureException
	 */
	public Date getLatestForecastTime() {
		return null;
	}

	/**
	 * Obtain near by grid points of the given point.
	 * 
	 * @param point
	 * @return
	 */
	public List<Point> getNearbyGridPoints(final Point point) {

		final List<Point> validPoints = persister.getValidGridPointsFromDB();
		final List<Point> nearbyPoints = new ArrayList<Point>();
		for (final Iterator iterator = validPoints.iterator(); iterator
				.hasNext();) {
			final Point validPoint = (Point) iterator.next();
			if (Math.abs(point.getLatitude() - validPoint.getLatitude()) < 0.5
					&& Math.abs(point.getLongitude()
							- validPoint.getLongitude()) < 0.5) {
				nearbyPoints.add(validPoint);
			}
		}
		return nearbyPoints;
	}

	/**
	 * Called when a new Grib file with latest forecast data has been
	 * downloaded. Actions performed are: 1)Move latest forecast to the archive.
	 * 2)Decode grib file and obtain latest forecast. 3)Persist latest forecast
	 * into DB.
	 * 
	 * @param observable
	 *            {@link DownloaderJobListener} notifies when a new forecast
	 *            file has been downloaded.
	 * @param gribFile
	 *            grib2 file containing latest wave watch forecasts just
	 *            downloaded by {@link DownloaderJob}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void update(final Observable o, final Object gribFile) {

		if (!(o instanceof DownloaderJobListener))
			throw new IncompatibleClassChangeError();
		if (!(gribFile instanceof File))
			throw new InvalidParameterException();

		final long init = System.currentTimeMillis();
		// TODO check file has newer data.
		// TODO Begin transaction
		persister.archiveLatestForecasts();
		updateLatestForecasts((File) gribFile);
		// TODO End Transaction

		final long end = System.currentTimeMillis();

		log.info("Elapsed Time To update forecasts: " + (end - init) / 1000);
	}

	/**
	 * @param gridFile
	 * @return
	 * @throws IOException
	 */
	private List<Point> getValidGridPoints(final String gridFile)
			throws IOException {
		final Collection<ForecastPlain> forecastForTime = gribDecoder
				.getForecastForTime(new File(gridFile), 0);
		final List<Point> points = new ArrayList<Point>();
		for (final Iterator iterator = forecastForTime.iterator(); iterator
				.hasNext();) {
			final ForecastPlain forecastArch = (ForecastPlain) iterator.next();
			points.add(new Point(forecastArch.getLatitude(), forecastArch
					.getLongitude()));
		}
		return points;
	}

	/**
	 * Decode given grib file and create forecasts objects.
	 * 
	 * @param file
	 */
	private void updateLatestForecasts(final File csvFile) {
		try {
			for (int time = 0; time < 61; time++) {

				final Collection<ForecastPlain> forecasts = gribDecoder
						.getForecastForTime(csvFile, time);
				persister.appendForecastsToFile(forecasts);
			}
			persister.massiveInsertLatestForecast();
		} catch (final IOException e) {
			log.error(e);
		}
	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.WaveWatchSystem#isGridPoint(edu.unicen.surfforecaster.server.domain.entity.forecasters.Point)
	 */
	@Override
	public boolean isGridPoint(final Point point) {
		return false;
	}

	@Override
	public String getName() {
		return systemName;
	}
}
