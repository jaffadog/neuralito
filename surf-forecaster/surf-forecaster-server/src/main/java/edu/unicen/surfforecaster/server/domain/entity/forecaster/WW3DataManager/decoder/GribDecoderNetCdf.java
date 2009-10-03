/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import ucar.ma2.Array;
import ucar.ma2.IndexIterator;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.GridDatatype;
import ucar.nc2.dt.grid.GridDataset;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3Attribute;

/**
 * 
 * Grib decoder that uses the NETCDF library to read the grib files.
 * 
 * @author esteban
 * 
 */
public class GribDecoderNetCdf implements GribDecoder {

	/**
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
			for (final WW3Attribute parameter : WW3Attribute.values()) {
				final GridDatatype pwd = gridDataSet.findGridDatatype(parameter
						.getValue());
				System.out.println(pwd.getInfo());

				final GridCoordSystem pwdGcs = pwd.getCoordinateSystem();
				final int[] result = null;
				for (final Iterator it = points.iterator(); it.hasNext();) {
					final Point point = (Point) it.next();
					// Get index value for Lat 30.0 and Lon 179
					final int[] idx = pwdGcs.findXYindexFromLatLon(point
							.getLatitude(), point.getLongitude(), result);

					for (int i = 0; i < 31; i++) {
						final Array data = pwd.readDataSlice(i, -1, idx[1],
								idx[0]);
						final IndexIterator iter = data.getIndexIterator();
						while (iter.hasNext()) {
							final float val = iter.getFloatNext();
							System.out
									.println(parameter.getValue() + " " + val);
							final Forecast forecast = new Forecast();
							forecasts.add(forecast);
						}
					}
				}
				System.out.println("Success");
			}
		} catch (final Exception exc) {
			exc.printStackTrace();
		}

		return forecasts;
	}

	/**
	 * @param variable
	 * @return
	 */
	private final Date getRunTime(final GridDatatype variable) {
		// TODO Auto-generated method stub
		return null;
	}
}