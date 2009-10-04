/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import edu.unicen.surfforecaster.server.dao.WaveWatchDAO;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder.GribDecoder;
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
	 * The grib decoder to use.
	 */
	private GribDecoder gribDecoder;
	/**
	 * The ww3 forecast archive.
	 */
	private ForecastArchive ww3archive;
	/**
	 * the latest forecast
	 */
	private LatestForecast latestForecasts;
	/**
	 * the points to obtain the forecasts.
	 */
	private ForecastPoints points;
	/**
	 * The ww3 DAO.
	 */
	private WaveWatchDAO waveWatchDAO;

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
		// TODO implement it.
		return null;

	}

	/**
	 * Obtains the time of the last forecast.
	 * 
	 * @return
	 */
	public Date getLastForecastUpdate() {
		return latestForecasts.getForecastsDate();
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
	 * @param point
	 */
	private void validatePoint(final Point point) {
		// Point should be a grid point.
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
	 * This method is called by the file downloader when the new forecast files
	 * has been downloaded.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void update(final Observable o, final Object file) {
		if (!(o instanceof DownloaderJobListener))
			throw new IncompatibleClassChangeError();
		if (!(file instanceof File))
			throw new InvalidParameterException();
		// Now that we have new forecasts from NOOA the latest forecast goes to
		// the archive.
		archiveLatestForecasts();
		obtainLatestForecasts((File) file);

	}

	/**
	 * Decode given grib file and obtain latest forecast.
	 * 
	 * @param file
	 */
	private void obtainLatestForecasts(final File file) {
		// Decode downloaded NOAA grib files to obtain latest forecasts.
		final Collection<Forecast> forecasts = gribDecoder.getForecasts(file,
				points.getPoints());
		latestForecasts.setLatest(forecasts);
		// Save latest forecasts
		waveWatchDAO.update(latestForecasts);
	}

	/**
	 * @param waveWatchDAO
	 *            the waveWatchDAO to set
	 */
	public void setWaveWatchDAO(final WaveWatchDAO waveWatchDAO) {
		this.waveWatchDAO = waveWatchDAO;
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

	}

}
