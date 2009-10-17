/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import ucar.ma2.Array;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.GridDatatype;
import ucar.nc2.dt.grid.GridDataset;
import ucar.unidata.geoloc.LatLonPoint;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3Parameter;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastArch;

/**
 * 
 * Grib decoder that uses the NETCDF library to read the grib files and
 * translate forecast data into {@link ForecastArch} objects.
 * 
 * @author esteban
 * 
 */
public class GribDecoderNetcdf implements GribDecoder {

	/**
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder.GribDecoder#getValidPoints(java.io.File)
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

	@Override
	public Collection<ForecastArch> getForecastForTime(final File file,
			final int time) throws IOException {
		System.out.println("Decoding and creating forecasts for time: " + time);
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
		System.out.println("Decoded and created: " + forecasts.size()
				+ " forecasts.");
		System.out.println("Elapsed Time: " + (end - init) / 1000);
		return forecasts;
	}

}