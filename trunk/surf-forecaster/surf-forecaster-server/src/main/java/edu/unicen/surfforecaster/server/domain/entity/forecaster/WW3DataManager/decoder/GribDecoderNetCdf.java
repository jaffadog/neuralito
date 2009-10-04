/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import ucar.ma2.Array;
import ucar.ma2.IndexIterator;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.GridDatatype;
import ucar.nc2.dt.grid.GridDataset;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.ForecastParameter;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3Parameter;

/**
 * 
 * Grib decoder that uses the NETCDF library to read the grib files.
 * 
 * @author esteban
 * 
 */
public class GribDecoderNetcdf implements GribDecoder {

	/**
	 * TODO: Define Exceptions to throw. TODO: See perfomance issues.
	 * 
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder.GribDecoder#getForecasts(java.util.Collection,
	 *      edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastPoints)
	 */
	@Override
	public Collection<Forecast> getForecasts(final File file,
			final Collection<Point> points) {
		final Collection<Forecast> forecasts = new ArrayList<Forecast>();
		try {

			final GridDataset gridDataSet = GridDataset.open(file
					.getAbsolutePath());
			// For each point
			for (final Iterator it = points.iterator(); it.hasNext();) {
				final Point point = (Point) it.next();
				// For each time
				for (int i = 0; i < 31; i++) {
					final HashMap<String, ForecastParameter> parameters = new HashMap<String, ForecastParameter>();
					Date forecastBaseDate = null;
					// For each parameter
					for (final WW3Parameter parameter : WW3Parameter.values()) {
						final GridDatatype pwd = gridDataSet
								.findGridDatatype(parameter.getValue());
						final GridCoordSystem pwdGcs = pwd
								.getCoordinateSystem();
						final int[] result = null;
						// Get index value for Lat 30.0 and Lon 179
						final int[] idx = pwdGcs.findXYindexFromLatLon(point
								.getLatitude(), point.getLongitude(), result);
						final Array data = pwd.readDataSlice(i, -1, idx[1],
								idx[0]);
						final IndexIterator iter = data.getIndexIterator();

						while (iter.hasNext()) {
							final float val = iter.getFloatNext();
							parameters.put(parameter.getValue(),
									new ForecastParameter(parameter.getValue(),
											val, parameter.getUnit()));
						}
						forecastBaseDate = pwdGcs.getDateRange().getStart()
								.getDate();

					}

					final Integer forecastTime = i;
					final Forecast forecast = new Forecast(forecastBaseDate,
							forecastTime * 3, parameters, point);
					forecasts.add(forecast);
				}
			}
		} catch (final Exception exc) {
			exc.printStackTrace();
		}

		return forecasts;
	}

}