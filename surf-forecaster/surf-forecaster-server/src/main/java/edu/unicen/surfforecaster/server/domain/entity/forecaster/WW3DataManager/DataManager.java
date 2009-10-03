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

import edu.unicen.surfforecaster.server.dao.WW3DAO;
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
	private final WW3ForecastArchive ww3archive;
	/**
	 * the latest forecast
	 */
	private final WW3LatestForecast latestForecasts;
	/**
	 * the points to obtain the forecasts.
	 */
	private ForecastPoints points;
	/**
	 * The ww3 DAO.
	 */
	private WW3DAO ww3DAO;

	/**
	 * Constructor.
	 */
	public DataManager() {
		latestForecasts = ww3DAO.getLatestForecast();
		ww3archive = ww3DAO.getWW3Archive();
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

	}

	/**
	 * This method is called by the file downloader when the new forecast files
	 * has been downloaded.
	 * 
	 * @param files
	 */
	public void newForecastFilesDownloaded(final Collection<File> files) {

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
	 * 
	 */
	private void archiveLatestForecasts() {
		final Collection<Forecast> latestForecast = latestForecasts
				.getLatestForecast();
		for (final Iterator iterator = latestForecast.iterator(); iterator
				.hasNext();) {
			final Forecast forecast = (Forecast) iterator.next();
			if (forecast.getForecastTime().equals(0)
					|| forecast.getForecastTime().equals(3)) {
				ww3archive.add(forecast);
			}
		}
		ww3DAO.save(ww3archive);
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
	 * 
	 * @param ww3dao
	 *            the ww3DAO to set
	 */
	public void setWw3DAO(final WW3DAO ww3dao) {
		ww3DAO = ww3dao;
	}

	/**
	 * This method is called by the file downloader when the new forecast files
	 * has been downloaded.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void update(final Observable o, final Object files) {
		if (!(o instanceof DownloaderJobListener))
			throw new IncompatibleClassChangeError();
		if (!(files instanceof Collection<?>))
			throw new InvalidParameterException();
		archiveLatestForecasts();
		final Collection<Forecast> forecasts = gribDecoder.getForecasts(
				(Collection<File>) files, points);
		latestForecasts.setLatestForecast(forecasts);
		ww3DAO.save(latestForecasts);
	}

}
