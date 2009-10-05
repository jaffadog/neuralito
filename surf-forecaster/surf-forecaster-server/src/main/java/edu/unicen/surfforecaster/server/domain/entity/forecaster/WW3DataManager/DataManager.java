/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import edu.unicen.surfforecaster.server.dao.WaveWatchDAO;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder.GribDecoder;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.downloader.DownloaderJob;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.downloader.DownloaderJobListener;

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
 * @author esteban
 * 
 */
public class DataManager implements Observer {
	/**
	 * The logger.
	 */
	Logger log = Logger.getLogger(this.getClass());
	/**
	 * 
	 * The grib decoder to use.
	 */
	private GribDecoder gribDecoder;
	/**
	 * The ww3 forecast archive.
	 */
	private ForecastArchive ww3archive;
	/**
	 * The latest forecast
	 */
	private LatestForecast latestForecasts;
	/**
	 * The points to obtain the forecasts.
	 */
	private ForecastPoints points;
	/**
	 * The ww3 DAO.
	 */
	private WaveWatchDAO waveWatchDAO;
	/**
	 * The valid grid points.
	 */
	private ValidGridPoints validGridPoints;
	/**
	 * Whether this class was initiliazed by invoking {@link DataManager#init()}
	 */
	private boolean initialized = false;

	/**
	 * Constructor.
	 */
	public DataManager() {

	}

	/**
	 * Adds a new location to which a forecast should be obtained from the grib2
	 * file.
	 * 
	 * @param point
	 */
	public void registerPoint(final Point point) {
		validatePoint(point);

		points.addPoint(point);
		waveWatchDAO.save(points);

	}

	/**
	 * Obtain the latest forecast for the given location.
	 * 
	 * @param point
	 * @return
	 */
	public Collection<Forecast> getLatestForecast(final Point point) {
		if (!initialized)
			throw new IllegalStateException(
					"Object was not initialized before calling this method.");
		return latestForecasts.getLatestForecastForLocation(point);
	}

	/**
	 * Obtain the archived forecast for the given location in the specified time
	 * range.
	 * 
	 * @param from
	 * @param to
	 * @param point
	 * @return
	 */
	public Collection<Forecast> getArchiveForecast(final Date from,
			final Date to, final Point point) {
		if (!initialized)
			throw new IllegalStateException(
					"Object was not initialized before calling this method.");
		Validate.notNull(from);
		Validate.notNull(to);
		Validate.notNull(point);
		return ww3archive.getArchiveForecast(from, to, point);

	}

	/**
	 * @return the date of the latest forecasts received from NOAA. Null if no
	 *         latest forecast exists.
	 */
	public Date getLatestForecastUpdate() {
		if (!initialized)
			throw new IllegalStateException(
					"Object was not initialized before calling this method.");
		return latestForecasts.getForecastsDate();
	}

	/**
	 * Obtain near by grid points of the given point.
	 * 
	 * @param point
	 * @return
	 */
	public Collection<Point> getNearbyGridPoints(final Point point) {

		final Collection<Point> validPoints = validGridPoints
				.getValidGridPoints();
		final Collection<Point> nearbyPoints = new ArrayList<Point>();
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
	 * Archive latest forecasts corresponding to times of 0 hours and 3 hours.
	 */
	private void archiveLatestForecasts() {
		final Collection<Forecast> latestForecast = latestForecasts
				.getLatestForecast();
		for (final Iterator<Forecast> iterator = latestForecast.iterator(); iterator
				.hasNext();) {
			final Forecast forecast = iterator.next();
			if (forecast.getForecastTime().equals(0)
					|| forecast.getForecastTime().equals(3)) {
				ww3archive.add(forecast);
			}
		}
		waveWatchDAO.update(ww3archive);
	}

	/**
	 * Sets the waveWatchDAO to use.
	 * 
	 * @param waveWatchDAO
	 *            the waveWatchDAO to set
	 */
	public void setWaveWatchDAO(final WaveWatchDAO waveWatchDAO) {
		this.waveWatchDAO = waveWatchDAO;
		latestForecasts = waveWatchDAO.getLatestForecast();

	}

	/**
	 * Sets the Grib decoder to use.
	 * 
	 * @param decoder
	 *            the decoder to set
	 */
	public void setGribDecoder(final GribDecoder decoder) {
		gribDecoder = decoder;
	}

	/**
	 * This method is called by the file downloader when a new forecast file has
	 * been downloaded.
	 * 
	 * @param observable
	 *            This should be the {@link DownloaderJobListener}
	 * @param file
	 *            grib2 file containing latest wave watch forecasts just
	 *            downloaded by {@link DownloaderJob}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void update(final Observable o, final Object file) {
		if (!initialized)
			throw new IllegalStateException(
					"Object was not initialized before calling this method.");
		if (!(o instanceof DownloaderJobListener))
			throw new IncompatibleClassChangeError();
		if (!(file instanceof File))
			throw new InvalidParameterException();
		// Now that we have new forecasts from NOOA the latest forecast goes to
		// the archive.
		archiveLatestForecasts();
		// The new grib2 forecasts files received should be decoded and saved
		// into latest
		// forecasts.
		decodeAndSaveLatestForecasts((File) file);

	}

	/**
	 * @param point
	 */
	private void validatePoint(final Point point) {
		// Point should be a grid point.
	}

	/**
	 * Decode given grib file and obtain latest forecast.
	 * 
	 * @param file
	 */
	private void decodeAndSaveLatestForecasts(final File file) {
		// Decode downloaded NOAA grib files to obtain latest forecasts.
		final Collection<Forecast> forecasts = gribDecoder.getForecasts(file,
				points.getPoints());
		latestForecasts.setLatest(forecasts);
		// Save latest forecasts
		waveWatchDAO.update(latestForecasts);
	}

	/**
	 * Initializes the data manager.
	 */
	public void init() {
		if (!initialized) {
			initialized = true;
			latestForecasts = waveWatchDAO.getLatestForecast();
			if (latestForecasts == null) {
				latestForecasts = new LatestForecast();
				waveWatchDAO.save(latestForecasts);
			}
			ww3archive = waveWatchDAO.getWW3Archive();
			if (ww3archive == null) {
				ww3archive = new ForecastArchive();
				waveWatchDAO.save(ww3archive);
			}
			points = waveWatchDAO.getForecastPoints();
			if (points == null) {
				points = new ForecastPoints();
				waveWatchDAO.save(points);
			}
			validGridPoints = waveWatchDAO.getValidGridPoints();
			// If no list of valid grid points exists, read a grib2 file and
			// save
			// the valid
			// grid points into DB.
			if (validGridPoints == null) {
				final File file = new File(
						"src/test/resources/multi_1.glo_30m.HTSGW.grb2");
				final Collection<Point> validPoints = gribDecoder
						.getValidPoints(file);
				System.out.println(validPoints.size());

				validGridPoints = new ValidGridPoints();
				validGridPoints.setValidPoints(validPoints);
				final long startTime = System.currentTimeMillis();
				waveWatchDAO.save(validGridPoints);
				final long endTime = System.currentTimeMillis();
				log.info("Elapsed time to save: " + validPoints.size()
						+ " Points records: " + (endTime - startTime) / 1000
						+ "secs.");

			}
		}
	}
}
