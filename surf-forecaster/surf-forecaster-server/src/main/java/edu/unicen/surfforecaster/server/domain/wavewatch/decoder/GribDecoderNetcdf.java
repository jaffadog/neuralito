/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.wavewatch.decoder;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import edu.unicen.surfforecaster.common.services.dto.WaveWatchParameter;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.ForecastValue;
import edu.unicen.surfforecaster.server.domain.entity.Point;

/**
 * 
 * Grib decoder that uses the NETCDF library to read the grib files and
 * translate it to {@link Forecast} objects.
 * 
 * @author esteban
 * 
 */
public class GribDecoderNetcdf implements GribDecoder {

	private static final long serialVersionUID = 1L;
	private final Logger log = Logger.getLogger(GribDecoderNetcdf.class);

	/**
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecasters.WW3DataManager.decoder.GribDecoder#getValidPoints(java.io.File)
	 */
	public Collection<Point> getValidPoints(final File file) {
		final Collection<Point> validPoints = new ArrayList<Point>();

		try {
			final GridDataset gridDataSet = GridDataset.open(file
					.getAbsolutePath());
			final GridDatatype grid = gridDataSet
					.findGridDatatype("Significant_height_of_combined_wind_waves_and_swell");
			final GridCoordSystem pwdGcs = grid.getCoordinateSystem();
			final Array data = grid.readVolumeData(0);
			final float[][] a = (float[][]) data.copyToNDJavaArray();
			for (int i = 0; i < a.length; i++) {
				for (int j = 0; j < a[i].length; j++) {
					final LatLonPoint latLon = pwdGcs.getLatLon(j, i);
					final Float val = a[i][j];

					if (!val.isNaN()) {
						validPoints.add(new Point(new Float(latLon
								.getLatitude()), new Float(latLon
								.getLongitude())));
					} else {
						log.info("Point not added:"
								+ new Point(new Float(latLon.getLatitude()),
										new Float(latLon.getLongitude())));
					}
				}
			}

		} catch (final Exception exc) {
			exc.printStackTrace();
			return null;
		}

		return validPoints;

	}

	public Collection<Forecast> decodeForecastForTime(
			final Collection<File> files,
			final List<WaveWatchParameter> parameters, final int time)
			throws IOException {
		// final long init = System.currentTimeMillis();

		final List<GridDatatype> grids = new ArrayList<GridDatatype>();
		GridCoordSystem pwdGcs = null;
		final HashMap<String, float[][]> arrays = new HashMap<String, float[][]>();
		final Collection<Forecast> decodedForecasts = new ArrayList<Forecast>();
		int imax = 0;
		int jmax = 0;
		Date startDate = null;
		for (final File file : files) {
			// log.info("Decoding forecasts from file: " +
			// file.getAbsolutePath());
			final GridDataset gridDataSet = GridDataset.open(file
					.getAbsolutePath());
			grids.addAll(getGridsForParameters(gridDataSet, parameters));
			startDate = gridDataSet.getStartDate();

		}
		// For each parameter
		for (final Iterator<GridDatatype> iterator = grids.iterator(); iterator
				.hasNext();) {

			final GridDatatype grid = iterator.next();
			pwdGcs = grid.getCoordinateSystem();
			final Array array = grid.readDataSlice(time, -1, -1, -1);
			// log.info("Read parameter: " + grid.getName());
			final float[][] data = (float[][]) array.copyToNDJavaArray();
			arrays.put(grid.getName(), data);
			imax = array.getShape()[0];
			jmax = array.getShape()[1];
		}
		// for each point
		for (int i = 0; i < imax; i++) {
			for (int j = 0; j < jmax; j++) {
				final Map<String, ForecastValue> parameter = new HashMap<String, ForecastValue>();
				final LatLonPoint latLon = pwdGcs.getLatLon(j, i);
				for (final GridDatatype gridDatatype : grids) {
					Float value = arrays.get(gridDatatype.getName())[i][j];
					if (value.isNaN()) {
						value = -1F;
					}
					parameter.put(gridDatatype.getName(), new ForecastValue(
							gridDatatype.getName(), value, Unit.Meters));
				}
				final float latitude = new Float(latLon.getLatitude());
				final float longitude = new Float(latLon.getLongitude());
				final Point point = new Point(latitude, longitude);
				final Forecast forecast = new Forecast(startDate, time * 3,
						point, parameter);
				decodedForecasts.add(forecast);
			}
		}

		final long end = System.currentTimeMillis();
		// log.info("Decoded and created: " + decodedForecasts.size()
		// + " forecasts.");
		// log.info("Elapsed Time: " + (end - init) / 1000);
		return decodedForecasts;
	}

	@Override
	public Collection<Forecast> decodeForecastForTime(final File file,
			final List<WaveWatchParameter> parameters, final int time)
			throws IOException {
		// log.info("Decoding forecasts from file: " + file.getAbsolutePath());
		// final long init = System.currentTimeMillis();

		final GridDataset gridDataSet = GridDataset
				.open(file.getAbsolutePath());
		final DateFormat formatter = SimpleDateFormat.getInstance();
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		// log.info("Forecasts for: "
		// + formatter.format(gridDataSet.getStartDate()) + "+ " + time
		// * 3
		// + "hs.");
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
		final List<GridDatatype> grids = getGridsForParameters(gridDataSet,
				parameters);
		for (final Iterator<GridDatatype> iterator = grids.iterator(); iterator
				.hasNext();) {
			final GridDatatype pwd = iterator.next();
			pwdGcs = pwd.getCoordinateSystem();

			final Array array = pwd.readDataSlice(time, -1, -1, -1);
			// log.info("Read parameter: " + pwd.getName());
			final float[][] data = (float[][]) array.copyToNDJavaArray();
			arrays.put(pwd.getName(), data);
			imax = array.getShape()[0];
			jmax = array.getShape()[1];
		}
		for (int i = 0; i < imax; i++) {
			for (int j = 0; j < jmax; j++) {
				final Map<String, ForecastValue> parameter = new HashMap<String, ForecastValue>();
				final LatLonPoint latLon = pwdGcs.getLatLon(j, i);
				for (final GridDatatype gridDatatype : grids) {
					Float value = arrays.get(gridDatatype.getName())[i][j];
					if (value.isNaN()) {
						value = -1F;
					}
					parameter.put(gridDatatype.getName(), new ForecastValue(
							gridDatatype.getName(), value, Unit.Meters));
				}
				final float latitude = new Float(latLon.getLatitude());
				final float longitude = new Float(latLon.getLongitude());
				final Point point = new Point(latitude, longitude);
				final Forecast forecast = new Forecast(gridDataSet
						.getStartDate(), time * 3, point, parameter);
				decodedForecasts.add(forecast);
			}
		}

		final long end = System.currentTimeMillis();
		// log.info("Decoded and created: " + decodedForecasts.size()
		// + " forecasts.");
		// log.info("Elapsed Time: " + (end - init) / 1000);
		return decodedForecasts;
	}

	private List<GridDatatype> getGridsForParameters(
			final GridDataset gridDataSet,
			final List<WaveWatchParameter> parameters) {
		final List<GridDatatype> gridsForParameters = new ArrayList<GridDatatype>();
		final List<GridDatatype> grids = gridDataSet.getGrids();
		for (final GridDatatype gridDatatype : grids) {
			for (final WaveWatchParameter parameter : parameters) {
				if (parameter.getValue().equals(gridDatatype.getName())) {
					gridsForParameters.add(gridDatatype);
				}
			}
		}
		return gridsForParameters;
	}

	@Override
	public List<String> listParameters(final File file) throws IOException {

		final List<String> parameters = new ArrayList<String>();
		final long init = System.currentTimeMillis();
		final GridDataset gridDataSet = GridDataset
				.open(file.getAbsolutePath());
		final DateFormat formatter = SimpleDateFormat.getInstance();
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		final List<GridDatatype> grids = gridDataSet.getGrids();
		for (final Iterator iterator = grids.iterator(); iterator.hasNext();) {
			final GridDatatype gridDatatype = (GridDatatype) iterator.next();
			parameters.add(gridDatatype.getName());
		}
		return parameters;
	}

	public int getTimes(final File file) throws IOException {
		final long init = System.currentTimeMillis();

		final GridDataset gridDataSet = GridDataset
				.open(file.getAbsolutePath());
		int time = -1;
		final List<GridDatatype> grids = gridDataSet.getGrids();
		for (final Iterator iterator = grids.iterator(); iterator.hasNext();) {
			final GridDatatype gridDatatype = (GridDatatype) iterator.next();
			time = gridDatatype.getTimeDimension().getLength();
			break;
		}
		return time;
	}

}