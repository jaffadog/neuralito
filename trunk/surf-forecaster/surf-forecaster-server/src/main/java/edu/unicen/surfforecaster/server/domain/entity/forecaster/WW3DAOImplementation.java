/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster;

import java.io.File;
import java.util.Collection;
import java.util.Date;

/**
 * Provides NOAA wave watch 3 data.
 * 
 * @author esteban
 * 
 */
public class WW3DAOImplementation implements WW3DAO {

	GribDecoder decoder;

	/**
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DAO#getLatestForecast(java.util.Collection)
	 */
	@Override
	public Collection<Forecast> getLatestForecast(
			final Collection<Point> gridPoints) {
		// Obtain the latest forecast files.
		// Decode the file and Obtain the parameters for the given grid points
		// Fill the corresponding forecasts.
		// return
		return null;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DAO#getForecastForDate(java.util.Date,
	 *      java.util.Date, java.util.Collection)
	 */
	@Override
	public Collection<Forecast> getForecastForDate(final Date from,
			final Date to, final Collection<Point> gridPoints) {
		// Look all the 0 h forecasts files between dates.
		// Fill the corresponding forecast info.
		return null;
	}

	private File getLatestForecastFile() {

		return null;
	}

	private void downloadFromNOAA() {

		// waveUrl;
		// windUrl;
		// asadUrl;
		// Download wind to: resources/noaa/multi/hour-day-month-year-/
		// Download wave file
		// Download asda file
	}
}
