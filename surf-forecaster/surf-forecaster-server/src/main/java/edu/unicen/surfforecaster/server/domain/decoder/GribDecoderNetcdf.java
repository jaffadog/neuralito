/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.decoder;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import ucar.ma2.Array;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.GridDatatype;
import ucar.nc2.dt.grid.GridDataset;
import ucar.unidata.geoloc.LatLonPoint;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.Point;
import edu.unicen.surfforecaster.server.domain.entity.Value;
import edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchParameter;

/**
 * 
 * Grib decoder that uses the NETCDF library to read the grib files and
 * translate forecast data into {@link ForecastPlain} objects.
 * 
 * @author esteban
 * 
 */
public class GribDecoderNetcdf implements GribDecoder {
	private Logger log = Logger.getLogger(GribDecoderNetcdf.class);

	/**
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecasters.WW3DataManager.decoder.GribDecoder#getValidPoints(java.io.File)
	 */
	public Collection<Point> getValidPoints(final File file) {
		final Collection<Point> validPoints = new ArrayList<Point>();

		try {
			final GridDataset gridDataSet = GridDataset.open(file
					.getAbsolutePath());
			final GridDatatype pwd = gridDataSet
					.findGridDatatype("Significant_height_of_combined_wind_waves_and_swell");
			final GridCoordSystem pwdGcs = pwd.getCoordinateSystem();
			final Array data = pwd.readVolumeData(0);
			final float[][] a = (float[][]) data.copyToNDJavaArray();
			for (int i = 0; i < a.length; i++) {
				for (int j = 0; j < a[i].length; j++) {
					final LatLonPoint latLon = pwdGcs.getLatLon(j, i);
					final Float val = a[i][j];
					if (!val.isNaN()) {
						validPoints.add(new Point(new Float(latLon
								.getLatitude()), new Float(latLon
								.getLongitude())));
					}
				}
			}

		} catch (final Exception exc) {
			exc.printStackTrace();
			return null;
		}

		return validPoints;

	}

	@Override
	public Collection<Forecast> decodeForecastForTime(final File file,
			List<WaveWatchParameter> parameters, final int time)
			throws IOException {
		log.info("Decoding forecasts from file: " + file.getAbsolutePath());
		final long init = System.currentTimeMillis();

		final GridDataset gridDataSet = GridDataset
				.open(file.getAbsolutePath());
		DateFormat formatter = SimpleDateFormat.getInstance();
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		log.info("Forecasts for: "
				+ formatter.format(gridDataSet.getStartDate()) + "+ " + time
				+ "hs.");
		// final List<GridDatatype> grids = gridDataSet.getGrids();
		// for (Iterator iterator = grids.iterator(); iterator.hasNext();) {
		// GridDatatype gridDatatype = (GridDatatype) iterator.next();
		// System.out.println(gridDatatype.getName());
		// }
		// log.info("Number of parameters: " + grids.size());

		GridCoordSystem pwdGcs = null;
		final HashMap<String, float[][]> arrays = new HashMap<String, float[][]>();
		final Collection<Forecast> decodedForecasts = new ArrayList<Forecast>();
		int imax = 0;
		int jmax = 0;
		final List<GridDatatype> grids = this.getGridsForParameters(
				gridDataSet, parameters);
		for (final Iterator<GridDatatype> iterator = grids.iterator(); iterator
				.hasNext();) {
			final GridDatatype pwd = iterator.next();
			pwdGcs = pwd.getCoordinateSystem();
			final Array array = pwd.readDataSlice(time, -1, -1, -1);
			log.info("Read parameter: " + pwd.getName());
			final float[][] data = (float[][]) array.copyToNDJavaArray();
			arrays.put(pwd.getName(), data);
			imax = array.getShape()[0];
			jmax = array.getShape()[1];
		}
		for (int i = 0; i < imax; i++) {
			for (int j = 0; j < jmax; j++) {
				Map<String, Value> parameter = new HashMap<String, Value>();
				final LatLonPoint latLon = pwdGcs.getLatLon(j, i);
				for (GridDatatype gridDatatype : grids) {
					Float value = arrays.get(gridDatatype.getName())[i][j];
					if (value.isNaN())
						value = -1F;
					parameter.put(gridDatatype.getName(), new Value(
							gridDatatype.getName(), value, Unit.Meters));
				}
				float latitude = new Float(latLon.getLatitude());
				float longitude = new Float(latLon.getLongitude());
				Point point = new Point(latitude, longitude);
				final Forecast forecast = new Forecast(gridDataSet
						.getStartDate(), time * 3, point, parameter);
				decodedForecasts.add(forecast);
			}
		}

		final long end = System.currentTimeMillis();
		log.info("Decoded and created: " + decodedForecasts.size()
				+ " forecasts.");
		log.info("Elapsed Time: " + (end - init) / 1000);
		return decodedForecasts;
	}

	private List<GridDatatype> getGridsForParameters(GridDataset gridDataSet,
			List<WaveWatchParameter> parameters) {
		List<GridDatatype> gridsForParameters = new ArrayList<GridDatatype>();
		List<GridDatatype> grids = gridDataSet.getGrids();
		for (GridDatatype gridDatatype : grids) {
			for (WaveWatchParameter parameter : parameters) {
				if (parameter.getValue().equals(gridDatatype.getName())) {
					gridsForParameters.add(gridDatatype);
				}
			}
		}
		return gridsForParameters;
	}

}