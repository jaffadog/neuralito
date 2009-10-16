/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
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
import edu.unicen.surfforecaster.server.services.ForecastArch;

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
		Validate.notNull(point);
		points.addPoint(point);
		waveWatchDAO.save(points);
		// TODO here if point does not exist then the latest forecast for spot
		// should be decoded from latest grib file.
		// or if all forecasts are decoded then is not neccesary to perform
		// this.

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
		final long init = System.currentTimeMillis();
		if (!initialized)
			throw new IllegalStateException(
					"Object was not initialized before calling this method.");
		if (!(o instanceof DownloaderJobListener))
			throw new IncompatibleClassChangeError();
		if (!(file instanceof File))
			throw new InvalidParameterException();
		// Save latest forecast files. In case new spots are registered and
		// forecasts should be obtained.

		// Now that we have new forecasts from NOOA the latest forecast goes to
		// the archive.

		// TODO Begin transaction
		// Move latest forecasts to archive table.
		waveWatchDAO.archiveLatestForecasts();
		decodeAndSaveLatestForecasts((File) file);
		final long end = System.currentTimeMillis();
		System.out.println("Elapsed Time To update forecasts: " + (end - init)
				/ 1000);
		// TODO End Transaction
	}

	/**
	 * Decode given grib file and obtain latest forecast.
	 * 
	 * @param file
	 */
	private void decodeAndSaveLatestForecasts(final File gribFile) {
		// Decode downloaded NOAA grib files to obtain latest forecasts.
		final File textFile = new File("c:/latestForecast.csv");
		if (textFile.exists())
			if (textFile.isFile()) {
				textFile.delete();
			}
		for (int time = 0; time < 61; time++) {
			try {
				final Collection<ForecastArch> forecasts = gribDecoder
						.getForecastForTime(gribFile, time);
				appendForecastsToFile(textFile, forecasts);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		waveWatchDAO.insertIntoLatestForecastFromFile(textFile);
	}

	/**
	 * 
	 * @param forecasts
	 */
	private void appendForecastsToFile(final File file,
			final Collection forecasts) {
		final long init = System.currentTimeMillis();
		try {
			final BufferedWriter output = new BufferedWriter(new FileWriter(
					file, true));

			for (final Iterator iterator = forecasts.iterator(); iterator
					.hasNext();) {
				final ForecastArch forecast = (ForecastArch) iterator.next();
				final float windWaveHeight = forecast.getWindWaveHeight()
						.isNaN() ? -1F : forecast.getWindWaveHeight();
				final float windWavePeriod = forecast.getWindWavePeriod()
						.isNaN() ? -1F : forecast.getWindWavePeriod();
				final float windWaveDirection = forecast.getWindWaveDirection()
						.isNaN() ? -1F : forecast.getWindWaveDirection();
				final float swellWaveHeight = forecast.getSwellWaveHeight()
						.isNaN() ? -1F : forecast.getSwellWaveHeight();
				final float swellWavePeriod = forecast.getSwellWavePeriod()
						.isNaN() ? -1F : forecast.getSwellWavePeriod();
				final float swellWaveDirection = forecast
						.getSwellWaveDirection().isNaN() ? -1F : forecast
						.getSwellWaveDirection();
				final float combinaedWaveHeight = forecast
						.getCombinedWaveHeight().isNaN() ? -1F : forecast
						.getCombinedWaveHeight();
				final float peakWavePeriod = forecast.getPeakWavePeriod()
						.isNaN() ? -1F : forecast.getPeakWavePeriod();
				final float peakWaveDirection = forecast.getPeakWaveDirection()
						.isNaN() ? -1F : forecast.getPeakWaveDirection();
				final float windSpeed = forecast.getWindSpeed().isNaN() ? -1F
						: forecast.getWindSpeed();
				final float windDirection = forecast.getWindDirection().isNaN() ? -1F
						: forecast.getWindDirection();
				final float windU = forecast.getWindU().isNaN() ? -1F
						: forecast.getWindU();
				final float windV = forecast.getWindV().isNaN() ? -1F
						: forecast.getWindV();
				final int year = forecast.getIssuedDate().get(Calendar.YEAR);
				final int month = forecast.getIssuedDate().get(Calendar.MONTH) + 1;
				;
				final int day = forecast.getIssuedDate().get(
						Calendar.DAY_OF_MONTH);
				;
				final int hour = forecast.getIssuedDate().get(
						Calendar.HOUR_OF_DAY);
				final int minute = forecast.getIssuedDate()
						.get(Calendar.MINUTE);
				;
				final int seconds = forecast.getIssuedDate().get(
						Calendar.SECOND);
				;
				final String line = "x" + year + "-" + month + "-" + day + " "
						+ hour + ":" + minute + ":" + seconds + ","
						+ forecast.getValidTime() + ","
						+ forecast.getLatitude() + ","
						+ forecast.getLongitude() + "," + windWaveHeight + ","
						+ windWavePeriod + "," + windWaveDirection + ","
						+ swellWaveHeight + "," + swellWavePeriod + ","
						+ swellWaveDirection + "," + combinaedWaveHeight + ","
						+ peakWavePeriod + "," + peakWaveDirection + ","
						+ windSpeed + "," + windDirection + "," + windU + ","
						+ windV + "e";
				output.append(line);
				output.newLine();

			}
			output.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		final long end = System.currentTimeMillis();
		System.out.println("Writing forecasts to file");
		System.out.println("Elapsed time: " + (end - init) / 1000);
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
			// if (validGridPoints == null) {
			// final File file = new File(
			// "src/test/resources/multi_1.glo_30m.HTSGW.grb2");
			// final Collection<Point> validPoints = gribDecoder
			// .getValidPoints(file);
			// System.out.println(validPoints.size());
			//
			// validGridPoints = new ValidGridPoints();
			// validGridPoints.setValidPoints(validPoints);
			// final long startTime = System.currentTimeMillis();
			// waveWatchDAO.save(validGridPoints);
			// final long endTime = System.currentTimeMillis();
			// log.info("Elapsed time to save: " + validPoints.size()
			// + " Points records: " + (endTime - startTime) / 1000
			// + "secs.");
			//
			// }
		}
	}

	/**
	 * @param gridPoint
	 * @return
	 */
	public boolean isGridPoint(final Point gridPoint) {
		return validGridPoints.isValid(gridPoint);

	}
}
