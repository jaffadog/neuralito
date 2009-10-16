/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.Vector;

import ucar.ma2.Array;
import ucar.ma2.IndexIterator;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.GridDatatype;
import ucar.nc2.dt.grid.GridDataset;
import ucar.unidata.geoloc.LatLonPoint;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast2;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.ForecastParameter;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3Parameter;
import edu.unicen.surfforecaster.server.services.ForecastArch;

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
				for (int i = 0; i < 61; i++) {
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
							Float val = iter.getFloatNext();
							if (val.isNaN()) {
								val = -1.0F;
							}
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

	/**
	 * TODO: Define Exceptions to throw. TODO: See perfomance issues.
	 * 
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder.GribDecoder#getForecasts(java.util.Collection,
	 *      edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastPoints)
	 */
	public Collection<Forecast> getForecastsForAllGridPoints(final File file) {
		final Collection<Forecast> forecasts = new ArrayList<Forecast>();
		try {
			final GridDataset gridDataSet = GridDataset.open(file
					.getAbsolutePath());
			final List<GridDatatype> grids = gridDataSet.getGrids();
			// For each time.31.
			for (int time = 0; time < 1; time++) {
				final Map<Point, Map<String, ForecastParameter>> map = new HashMap<Point, Map<String, ForecastParameter>>();
				// For each parameter.11 parameters
				for (final Iterator iterator = grids.iterator(); iterator
						.hasNext();) {
					final GridDatatype pwd = (GridDatatype) iterator.next();
					System.out.println("--" + pwd.getName());
					final GridCoordSystem pwdGcs = pwd.getCoordinateSystem();
					final Array data = pwd.readVolumeData(time);
					final float[][] a = (float[][]) data.copyToNDJavaArray();
					// For each point
					for (int i = 0; i < a.length; i++) {
						for (int j = 0; j < a[i].length; j++) {
							final Float val = a[i][j];
							final LatLonPoint latLon = pwdGcs.getLatLon(j, i);
							final Point point = new Point(latLon.getLatitude(),
									latLon.getLongitude());
							Map<String, ForecastParameter> parameterMap = map
									.get(point);
							if (parameterMap == null) {
								// Create a map to contain the parameters for
								// this point.
								parameterMap = new HashMap<String, ForecastParameter>();
								// Associate the point with the parameters
								map.put(point, parameterMap);
							}
							if (!val.isNaN()) {
								// put into the parameters the value just
								// readed.
								parameterMap.put(pwd.getName(),
										new ForecastParameter(pwd.getName(),
												val, Unit.Meters));
							}
						}
					}
				}
				// forecasts.addAll(createForecasts(gridDataSet.getDateRange()
				// .getStart().getDate(), time, map));

			}

		} catch (final Exception exc) {
			exc.printStackTrace();
			return null;
		}
		return forecasts;
	}

	/**
	 * TODO: Define Exceptions to throw. TODO: See perfomance issues.
	 * 
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder.GribDecoder#getForecasts(java.util.Collection,
	 *      edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastPoints)
	 */
	public Collection<Forecast2> getForecastsForAllGridPoints2(final File file) {
		final Collection<Forecast2> forecasts = new ArrayList<Forecast2>();
		try {
			final GridDataset gridDataSet = GridDataset.open(file
					.getAbsolutePath());
			final List<GridDatatype> grids = gridDataSet.getGrids();
			// For each time.61.
			for (int time = 0; time < 1; time++) {
				final Map<Point, Map<String, ForecastParameter>> map = new HashMap<Point, Map<String, ForecastParameter>>();
				// For each parameter.11 parameters
				for (final Iterator iterator = grids.iterator(); iterator
						.hasNext();) {
					final GridDatatype pwd = (GridDatatype) iterator.next();
					System.out.println("--" + pwd.getName());
					final GridCoordSystem pwdGcs = pwd.getCoordinateSystem();
					final Array data = pwd.readVolumeData(time);
					final float[][] a = (float[][]) data.copyToNDJavaArray();
					// For each point
					for (int i = 0; i < a.length; i++) {
						for (int j = 0; j < a[i].length; j++) {
							final Float val = a[i][j];
							final LatLonPoint latLon = pwdGcs.getLatLon(j, i);
							final Point point = new Point(latLon.getLatitude(),
									latLon.getLongitude());
							Map<String, ForecastParameter> parameterMap = map
									.get(point);
							if (parameterMap == null) {
								// Create a map to contain the parameters for
								// this point.
								parameterMap = new HashMap<String, ForecastParameter>();
								// Associate the point with the parameters
								map.put(point, parameterMap);
							}
							if (!val.isNaN()) {
								parameterMap.put(pwd.getName(),
										new ForecastParameter(pwd.getName(),
												val, Unit.Meters));
							}

						}
					}
				}
				forecasts.addAll(createForecasts(gridDataSet.getDateRange()
						.getStart().getDate(), time, map));

			}

		} catch (final Exception exc) {
			exc.printStackTrace();
			return null;
		}
		return forecasts;
	}

	/**
	 * TODO: Define Exceptions to throw. TODO: See perfomance issues.
	 * 
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder.GribDecoder#getForecasts(java.util.Collection,
	 *      edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastPoints)
	 */
	public Vector<Array> getForecastsForAllGridPoints22(final File file) {
		final Collection<Forecast2> forecasts = new ArrayList<Forecast2>();
		final Vector readedData = new Vector();
		try {
			final GridDataset gridDataSet = GridDataset.open(file
					.getAbsolutePath());
			final List<GridDatatype> grids = gridDataSet.getGrids();

			final Map<Point, Map<String, ForecastParameter>> map = new HashMap<Point, Map<String, ForecastParameter>>();
			// For each parameter.13 parameters

			// for (final Iterator iterator = grids.iterator();
			// iterator.hasNext();) {
			final long initialTime = System.currentTimeMillis();
			final GridDatatype pwd = grids.get(0);
			System.out.println("Start decoding parameter: " + pwd.getName());
			final GridCoordSystem pwdGcs = pwd.getCoordinateSystem();
			final Array data = pwd.readDataSlice(-1, -1, -1, -1);
			readedData.add(data);
			final long endTime = System.currentTimeMillis();
			// }

		} catch (final Exception exc) {
			exc.printStackTrace();
			return null;
		}
		// final long initial = System.currentTimeMillis();
		// readedData = obtainJavaArrays(readedData);
		// final long end = System.currentTimeMillis();
		// System.out.println("Elapsed time to transfrom into java arrays: "
		// + (end - initial) / 1000);
		return readedData;
	}

	/**
	 * Create forecasts from the map.
	 * 
	 * @param time
	 * @param map
	 * @return
	 */
	private Collection<Forecast2> createForecasts(final Date baseDate,
			final int time, final Map<Point, Map<String, ForecastParameter>> map) {
		final Set<Point> points = map.keySet();
		final Collection<Forecast2> forecasts = new ArrayList<Forecast2>();
		for (final Iterator iterator = points.iterator(); iterator.hasNext();) {
			final Point point = (Point) iterator.next();
			final Map<String, ForecastParameter> parameters = map.get(point);
			// If the point has an empty parameter map we do not generate
			// forecast.
			if (!parameters.isEmpty()) {
				double swellWavesDirection = -1F;
				double swellWavesPeriod = 1F;
				double swellWavesHeight = 1F;
				double windWavesDir = 1F;
				double windWavesPeriod = 1F;
				double windWavesHeight = 1F;
				double combinedHeight = 1F;
				double windDirection = 1F;
				double windSpeed = 1F;
				double primaryWaveDirection = 1F;
				double primaryWavePeriod = 1F;
				ForecastParameter param = parameters
						.get("Direction_of_swell_waves");

				if (param != null) {
					swellWavesDirection = param.getfValue();
				}

				param = parameters.get("Mean_period_of_swell_waves");
				if (param != null) {
					swellWavesPeriod = param.getfValue();
				}

				param = parameters.get("Significant_height_of_swell_waves");
				if (param != null) {
					swellWavesHeight = param.getfValue();
				}

				param = parameters.get("Direction_of_wind_waves");
				if (param != null) {
					windWavesDir = param.getfValue();
				}

				param = parameters.get("Significant_height_of_wind_waves");
				if (param != null) {
					windWavesHeight = param.getfValue();
				}

				param = parameters.get("Mean_period_of_wind_waves");
				if (param != null) {
					windWavesPeriod = param.getfValue();
				}

				param = parameters.get("Wind_speed");
				if (param != null) {
					windSpeed = param.getfValue();
				}

				param = parameters.get("Wind_direction_from_which_blowing");
				if (param != null) {
					windDirection = param.getfValue();
				}

				param = parameters.get("Direction_of_swell_waves");
				if (param != null) {
					swellWavesDirection = param.getfValue();
				}

				param = parameters
						.get("Significant_height_of_combined_wind_waves_and_swell");
				if (param != null) {
					combinedHeight = param.getfValue();
				}
				param = parameters.get("Primary_wave_mean_period");
				if (param != null) {
					primaryWavePeriod = param.getfValue();
				}
				param = parameters.get("Primary_wave_direction");
				if (param != null) {
					primaryWaveDirection = param.getfValue();
				}

				final Forecast2 forecast = new Forecast2(baseDate, time,
						swellWavesDirection, swellWavesPeriod,
						swellWavesHeight, windWavesDir, windWavesPeriod,
						windWavesHeight, combinedHeight, windDirection,
						windSpeed, primaryWaveDirection, primaryWavePeriod);
				forecasts.add(forecast);
			}
		}

		return forecasts;
	}

	/**
	 * @param forecasts
	 * @return
	 */
	private Vector obtainJavaArrays(final Vector forecasts) {
		final Vector transformed = new Vector();
		for (final Iterator iterator = forecasts.iterator(); iterator.hasNext();) {
			final Array array = (Array) iterator.next();

			final float[][][] copy = (float[][][]) array.copyToNDJavaArray();
			transformed.add(copy);

		}
		return transformed;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder.GribDecoder#getValidPoints(java.io.File)
	 */
	@Override
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
						validPoints.add(new Point(latLon.getLatitude(), latLon
								.getLongitude()));
					}
				}
			}

		} catch (final Exception exc) {
			exc.printStackTrace();
			return null;
		}

		return validPoints;

	}

	/**
	 * Read data for the given point for all parameters for all times.
	 * 
	 * @param file
	 * @param point
	 * @return
	 */
	public Vector<Array> getForecastsForPointReloaded(final File file,
			final Point point) {
		final Vector<Array> forecasts = new Vector<Array>();
		try {

			final GridDataset gridDataSet = GridDataset.open(file
					.getAbsolutePath());
			final List<GridDatatype> grids = gridDataSet.getGrids();

			// For each parameter.11 parameters

			for (final Iterator iterator = grids.iterator(); iterator.hasNext();) {
				final GridDatatype pwd = (GridDatatype) iterator.next();
				final GridCoordSystem pwdGcs = pwd.getCoordinateSystem();
				System.out.println("Reading: " + pwd.getName());
				// final int[] result = null;
				// Get index value for Lat and Lon
				// final int[] idx = pwdGcs.findXYindexFromLatLon(point
				// .getLatitude(), point.getLongitude(), result);
				final Array data = pwd.readDataSlice(-1, -1, 102, 280);
				forecasts.add(data);

			}

		} catch (final Exception exc) {
			exc.printStackTrace();
		}

		return forecasts;
	}

	// /**
	// * @param file
	// * @param time
	// * @param parameterName
	// * @return 2d array contaning the value of the forecast for the given s
	// * location.
	// * @throws IOException
	// */
	// public Collection<ForecastArch> getForecastForTime(final File file,
	// final int time) throws IOException {
	// final long init = System.currentTimeMillis();
	// final HashMap<Point, ForecastArch> forecasts = new HashMap<Point,
	// ForecastArch>();
	// final GridDataset gridDataSet = GridDataset
	// .open(file.getAbsolutePath());
	// final List<GridDatatype> grids = gridDataSet.getGrids();
	// GridCoordSystem pwdGcs = null;
	// for (final Iterator iterator = grids.iterator(); iterator.hasNext();) {
	// final GridDatatype pwd = (GridDatatype) iterator.next();
	// pwdGcs = pwd.getCoordinateSystem();
	// System.out.println("Reading: " + pwd.getName());
	// final Array array = pwd.readDataSlice(time, -1, -1, -1);
	// final float[][] data = (float[][]) array.copyToNDJavaArray();
	// // for (int i = 0; i < array.getShape()[0]; i++) {
	// // for (int j = 0; j < array.getShape()[1]; j++) {
	// // final LatLonPoint latLon = pwdGcs.getLatLon(j, i);
	// // final float value = data[i][j];
	// // final Point point = new Point(latLon.getLatitude(), latLon
	// // .getLongitude());
	// // ForecastArch forecast = forecasts.get(point);
	// // if (forecast == null) {
	// // forecast = new ForecastArch(new GregorianCalendar(),
	// // time, new Float(point.getLatitude()),
	// // new Float(point.getLongitude()));
	// // forecasts.put(point, forecast);
	// // }
	// // if (pwd.getName().equals(
	// // WW3Parameter.WIND_WAVE_HEIGHT.getValue())) {
	// // forecast.setWindWaveHeight(value);
	// // } else if (pwd.getName().equals(
	// // WW3Parameter.WIND_WAVE_PERIOD.getValue())) {
	// // forecast.setWindWavePeriod(value);
	// // } else if (pwd.getName().equals(
	// // WW3Parameter.WIND_WAVE_DIRECTION.getValue())) {
	// // forecast.setWindWaveDirection(value);
	// // } else if (pwd.getName().equals(
	// // WW3Parameter.SWELL_WAVE_HEIGHT.getValue())) {
	// // forecast.setSwellWaveHeight(value);
	// // } else if (pwd.getName().equals(
	// // WW3Parameter.SWELL_WAVE_PERIOD.getValue())) {
	// // forecast.setSwellWavePeriod(value);
	// // } else if (pwd.getName().equals(
	// // WW3Parameter.SWELL_DIRECTION.getValue())) {
	// // forecast.setSwellWaveDirection(value);
	// // } else if (pwd.getName().equals(
	// // WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT
	// // .getValue())) {
	// // forecast.setCombinedWaveHeight(value);
	// // } else if (pwd.getName().equals(
	// // WW3Parameter.PRIMARY_WAVE_PERIOD.getValue())) {
	// // forecast.setPeakWavePeriod(value);
	// // } else if (pwd.getName().equals(
	// // WW3Parameter.PRIMARY_WAVE_DIRECTION.getValue())) {
	// // forecast.setPeakWaveDirection(value);
	// // } else if (pwd.getName().equals(
	// // WW3Parameter.WIND_SPEED.getValue())) {
	// // forecast.setWindSpeed(value);
	// // } else if (pwd.getName().equals(
	// // WW3Parameter.WIND_DIRECTION.getValue())) {
	// // forecast.setWindDirection(value);
	// // } else if (pwd.getName().equals(
	// // WW3Parameter.WINDUComponent.getValue())) {
	// // forecast.setWindU(value);
	// // } else if (pwd.getName().equals(
	// // WW3Parameter.WINDVComponent.getValue())) {
	// // forecast.setWindV(value);
	// // }
	// // }
	// // }
	// }
	// final long end = System.currentTimeMillis();
	// System.out.println("Decoded and generated: " + forecasts.size()
	// + " forecasts.");
	// System.out.println("Elapsed Time: " + (end - init) / 1000);
	// return forecasts.values();
	// }

	public Collection<ForecastArch> getForecastForTime(final File file,
			final int time) throws IOException {
		final long init = System.currentTimeMillis();
		final GridDataset gridDataSet = GridDataset
				.open(file.getAbsolutePath());
		final List<GridDatatype> grids = gridDataSet.getGrids();
		GridCoordSystem pwdGcs = null;
		final HashMap<String, float[][]> arrays = new HashMap<String, float[][]>();
		final Collection<ForecastArch> forecasts = new ArrayList<ForecastArch>();
		int imax = 0;
		int jmax = 0;
		for (final Iterator iterator = grids.iterator(); iterator.hasNext();) {
			final GridDatatype pwd = (GridDatatype) iterator.next();
			pwdGcs = pwd.getCoordinateSystem();
			System.out.println("Reading: " + pwd.getName());
			final Array array = pwd.readDataSlice(time, -1, -1, -1);
			final float[][] data = (float[][]) array.copyToNDJavaArray();
			arrays.put(pwd.getName(), data);
			imax = array.getShape()[0];
			jmax = array.getShape()[1];
		}
		for (int i = 0; i < imax; i++) {
			for (int j = 0; j < jmax; j++) {
				final LatLonPoint latLon = pwdGcs.getLatLon(j, i);

				final Float windWaveHeight = arrays
						.get(WW3Parameter.WIND_WAVE_HEIGHT.getValue())[i][j];
				final Float windWavePeriod = arrays
						.get(WW3Parameter.WIND_WAVE_PERIOD.getValue())[i][j];
				final Float windWaveDirection = arrays
						.get(WW3Parameter.WIND_WAVE_DIRECTION.getValue())[i][j];
				final Float swellWaveHeight = arrays
						.get(WW3Parameter.SWELL_WAVE_HEIGHT.getValue())[i][j];
				final Float swellWavePeriod = arrays
						.get(WW3Parameter.SWELL_WAVE_PERIOD.getValue())[i][j];
				final Float swellWaveDirection = arrays
						.get(WW3Parameter.SWELL_DIRECTION.getValue())[i][j];
				final Float combinedWaveHeight = arrays
						.get(WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT
								.getValue())[i][j];
				final Float peakWavePeriod = arrays
						.get(WW3Parameter.PRIMARY_WAVE_PERIOD.getValue())[i][j];
				final Float peakWaveDirection = arrays
						.get(WW3Parameter.PRIMARY_WAVE_DIRECTION.getValue())[i][j];
				final Float windSpeed = arrays.get(WW3Parameter.WIND_SPEED
						.getValue())[i][j];
				final Float windDirection = arrays
						.get(WW3Parameter.WIND_DIRECTION.getValue())[i][j];
				final Float windU = arrays.get(WW3Parameter.WINDUComponent
						.getValue())[i][j];
				final Float windV = arrays.get(WW3Parameter.WINDVComponent
						.getValue())[i][j];
				if (!(windWaveHeight.isNaN() && windWavePeriod.isNaN()
						&& windWaveDirection.isNaN() && swellWaveHeight.isNaN()
						&& swellWavePeriod.isNaN()
						&& swellWaveDirection.isNaN()
						&& combinedWaveHeight.isNaN() && peakWavePeriod.isNaN() && peakWaveDirection
						.isNaN() /*
								 * && windSpeed.isNaN() && windDirection.isNaN()
								 * && windU.isNaN() && windV .isNaN()
								 */)) {
					final GregorianCalendar calendar = new GregorianCalendar(
							TimeZone.getTimeZone("UTC"));
					calendar.setTime(gridDataSet.getStartDate());
					final ForecastArch forecast = new ForecastArch(calendar,
							time * 3, new Float(latLon.getLatitude()),
							new Float(latLon.getLongitude()), windWaveHeight,
							windWavePeriod, windWaveDirection, swellWaveHeight,
							swellWavePeriod, swellWaveDirection,
							combinedWaveHeight, peakWavePeriod,
							peakWaveDirection, windSpeed, windDirection, windU,
							windV);
					forecasts.add(forecast);
				}
			}
		}

		final long end = System.currentTimeMillis();
		System.out.println("Decoded and generated: " + forecasts.size()
				+ " forecasts.");
		System.out.println("Elapsed Time: " + (end - init) / 1000);
		return forecasts;
	}

	/**
	 * @param file
	 * @param i
	 * @param value
	 * @return
	 */
	public Array getAllForecastForTimeAndParam(final File file, final int i,
			final String value) {
		try {
			final GridDataset gridDataSet = GridDataset.open(file
					.getAbsolutePath());
			final GridDatatype pwd = gridDataSet.findGridDatatype(value);
			final GridCoordSystem pwdGcs = pwd.getCoordinateSystem();
			System.out.println("Reading: " + pwd.getName());
			final Array data = pwd.readDataSlice(i, -1, -1, -1);
			return data;
		} catch (final Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}

}