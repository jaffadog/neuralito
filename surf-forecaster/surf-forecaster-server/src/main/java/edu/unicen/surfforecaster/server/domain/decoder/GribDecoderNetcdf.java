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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import ucar.ma2.Array;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.GridDatatype;
import ucar.nc2.dt.grid.GridDataset;
import ucar.unidata.geoloc.LatLonPoint;
import edu.unicen.surfforecaster.common.services.dto.WW3Parameter;
import edu.unicen.surfforecaster.server.domain.ForecastPlain;
import edu.unicen.surfforecaster.server.domain.entity.Point;

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
	public Collection<ForecastPlain> getForecastForTime(final File file,
			final int time) throws IOException {
		log.info("Decoding forecasts from file: "+file.getAbsolutePath());
		final long init = System.currentTimeMillis();
		final GridDataset gridDataSet = GridDataset
				.open(file.getAbsolutePath());
		DateFormat formatter  = SimpleDateFormat.getInstance();
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		log.info("Forecasts for: " + formatter.format(gridDataSet.getStartDate())+ "+ "+time+"hs." );
		final List<GridDatatype> grids = gridDataSet.getGrids();
//		for (Iterator iterator = grids.iterator(); iterator.hasNext();) {
//			GridDatatype gridDatatype = (GridDatatype) iterator.next();
//			System.out.println(gridDatatype.getName());
//		}
//		log.info("Number of parameters: " +  grids.size());
		
		GridCoordSystem pwdGcs = null;
		final HashMap<String, float[][]> arrays = new HashMap<String, float[][]>();
		final Collection<ForecastPlain> forecasts = new ArrayList<ForecastPlain>();
		int imax = 0;
		int jmax = 0;
		for (final Iterator iterator = grids.iterator(); iterator.hasNext();) {
			final GridDatatype pwd = (GridDatatype) iterator.next();
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
					final ForecastPlain forecast = new ForecastPlain(gridDataSet.getStartDate(),
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
		log.info("Decoded and created: " + forecasts.size()
				+ " forecasts.");
		log.info("Elapsed Time: " + (end - init) / 1000);
		return forecasts;
	}

}