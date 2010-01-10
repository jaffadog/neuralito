/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.wavewatch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import edu.unicen.surfforecaster.common.services.dto.WaveWatchParameter;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.Point;
import edu.unicen.surfforecaster.server.domain.wavewatch.decoder.GribDecoder;
import edu.unicen.surfforecaster.server.domain.wavewatch.decoder.GribDecoderNetcdf;
import edu.unicen.surfforecaster.server.domain.weka.util.Util;

/**
 * @author esteban
 * 
 */

public class GribDecoderNetcdfTest {
	Logger log = Logger.getLogger(this.getClass());

	/**
	 * Test obtaining all points which contains values differents of NaN.
	 */
	@Test
	@Ignore
	public void getValidPoints() {
		final File file = new File(
				"src/test/resources/multi_1.glo_30m.HTSGW.grb2");

		final GribDecoderNetcdf dec = new GribDecoderNetcdf();
		final Collection<Point> validPoints = dec.getValidPoints(file);
		log.info("Points founded:" + validPoints.size());
		for (final Iterator iterator = validPoints.iterator(); iterator
				.hasNext();) {
			final Point point = (Point) iterator.next();
			log.info("Point. Lat: " + point.getLatitude() + " Lon: "
					+ point.getLongitude());
		}
	}

	@Test
	public void decodeAll() {
		final GribDecoder dec = new GribDecoderNetcdf();
		final List<WaveWatchParameter> parameters = new ArrayList<WaveWatchParameter>();
		parameters.add(WaveWatchParameter.COMBINED_SWELL_WIND_WAVE_HEIGHT_V2);
		parameters.add(WaveWatchParameter.PRIMARY_WAVE_DIRECTION_V2);
		try {
			for (int i = 1; i < 4; i++) {
				dec
						.decodeForecastForTime(
								new File(
										/* "src/test/resources/multi_1.glo_30m.all.grb2" */"src/test/resources/nww3.all.grb"),
								parameters, i);
			}
		} catch (final IOException e) {
			log.error(e);
		}
	}

	@Test
	public void decodeAllGribV2() {
		final GribDecoder dec = new GribDecoderNetcdf();
		final List<WaveWatchParameter> parameters = new ArrayList<WaveWatchParameter>();
		parameters.add(WaveWatchParameter.COMBINED_SWELL_WIND_WAVE_HEIGHT_V2);
		parameters.add(WaveWatchParameter.PRIMARY_WAVE_DIRECTION_V2);
		parameters.add(WaveWatchParameter.PRIMARY_WAVE_PERIOD_V2);
		parameters.add(WaveWatchParameter.SECONDARY_WAVE_DIRECTION_V2);
		parameters.add(WaveWatchParameter.SECONDARY_WAVE_PERIOD_V2);
		parameters.add(WaveWatchParameter.WIND_DIRECTION_V2);
		parameters.add(WaveWatchParameter.WIND_SPEED_V2);
		parameters.add(WaveWatchParameter.WIND_WAVE_DIRECTION_V2);
		parameters.add(WaveWatchParameter.WIND_WAVE_PERIOD_V2);
		parameters.add(WaveWatchParameter.WINDUComponent_V2);
		parameters.add(WaveWatchParameter.WINDVComponent_V2);
		try {
			for (int i = 1; i < 4; i++) {
				dec
						.decodeForecastForTime(
								new File(
										/* "src/test/resources/multi_1.glo_30m.all.grb2" */"src/test/resources/nww3.all.grb"),
								parameters, i);
			}
		} catch (final IOException e) {
			log.error(e);
		}
	}

	@Test
	public void decodeAllGribV2MultipleFiles() {
		final GribDecoder dec = new GribDecoderNetcdf();
		final List<WaveWatchParameter> parameters = new ArrayList<WaveWatchParameter>();
		parameters.add(WaveWatchParameter.COMBINED_SWELL_WIND_WAVE_HEIGHT_V2);
		parameters.add(WaveWatchParameter.PRIMARY_WAVE_DIRECTION_V2);
		parameters.add(WaveWatchParameter.PRIMARY_WAVE_PERIOD_V2);
		parameters.add(WaveWatchParameter.WINDUComponent_V2);
		parameters.add(WaveWatchParameter.WINDVComponent_V2);
		final Collection<File> files = new ArrayList<File>();
		files
				.add(new File(
						"C:\\Users\\esteban\\workspace\\arfgen\\files\\WW3.gribs\\nww3.wind.199802.grb"));
		files
				.add(new File(
						"C:\\Users\\esteban\\workspace\\arfgen\\files\\WW3.gribs\\nww3.dp.199802.grb"));
		files
				.add(new File(
						"C:\\Users\\esteban\\workspace\\arfgen\\files\\WW3.gribs\\nww3.hs.199802.grb"));
		files
				.add(new File(
						"C:\\Users\\esteban\\workspace\\arfgen\\files\\WW3.gribs\\nww3.tp.199802.grb"));
		try {
			for (int i = 1; i < 4; i++) {

				dec.decodeForecastForTime(files, parameters, 249);
			}
		} catch (final IOException e) {
			log.error(e);
		}
	}

	@Test
	public void decodeAllGribV3() {
		final GribDecoder dec = new GribDecoderNetcdf();
		final List<WaveWatchParameter> parameters = new ArrayList<WaveWatchParameter>();
		parameters.add(WaveWatchParameter.COMBINED_SWELL_WIND_WAVE_HEIGHT_V3);
		parameters.add(WaveWatchParameter.PRIMARY_WAVE_DIRECTION_V3);
		parameters.add(WaveWatchParameter.WIND_WAVE_HEIGHT_V3);
		parameters.add(WaveWatchParameter.SWELL_WAVE_HEIGHT_V3);
		parameters.add(WaveWatchParameter.PRIMARY_WAVE_PERIOD_V3);
		parameters.add(WaveWatchParameter.SWELL_DIRECTION_V3);
		parameters.add(WaveWatchParameter.SWELL_WAVE_PERIOD_V3);
		parameters.add(WaveWatchParameter.WIND_DIRECTION_V3);
		parameters.add(WaveWatchParameter.WIND_SPEED_V3);
		parameters.add(WaveWatchParameter.WIND_WAVE_DIRECTION_V3);
		parameters.add(WaveWatchParameter.WIND_WAVE_PERIOD_V3);
		parameters.add(WaveWatchParameter.WINDUComponent_V3);
		parameters.add(WaveWatchParameter.WINDVComponent_V3);
		try {
			for (int i = 1; i < 2; i++) {
				final Collection<Forecast> decodeForecastForTime = dec
						.decodeForecastForTime(new File(
								"src/test/resources/multi_1.glo_30m.all.grb2"),
								parameters, i);
				for (final Forecast forecast : decodeForecastForTime) {

					final float windU = forecast.getParameter(
							WaveWatchParameter.WINDUComponent_V3.getValue())
							.getfValue();
					final float windV = forecast.getParameter(
							WaveWatchParameter.WINDVComponent_V3.getValue())
							.getfValue();
					final double calculatedWindDirection = Util
							.calculateWindDirection(windU, windV);
					final double calculatedWindSpeed = Util.calculateWindSpeed(
							windU, windV);

					final float windDirection = forecast.getParameter(
							WaveWatchParameter.WIND_DIRECTION_V3.getValue())
							.getfValue();
					final float windSpeed = forecast.getParameter(
							WaveWatchParameter.WIND_SPEED_V3.getValue())
							.getfValue();
					log.info("Real wind direction:" + windDirection
							+ " calculated: " + calculatedWindDirection);
					log.info("Real wind speed:" + windSpeed + " calculated: "
							+ calculatedWindSpeed);
				}
			}
		} catch (final IOException e) {
			log.error(e);
		}
	}

	@Test
	public void listParametersV3() {
		final GribDecoder dec = new GribDecoderNetcdf();
		List<String> parameters;
		try {
			parameters = dec.listParameters(new File(
					"src/test/resources/multi_1.glo_30m.all.grb2"));
			for (final Iterator iterator = parameters.iterator(); iterator
					.hasNext();) {
				final String string = (String) iterator.next();
				log.info(string);
			}
		} catch (final IOException e) {
			log.error(e);
		}
	}

	@Test
	public void listParameters() {
		final GribDecoder dec = new GribDecoderNetcdf();
		List<String> parameters;
		try {
			parameters = dec
					.listParameters(new File(
							/* "src/test/resources/multi_1.glo_30m.all.grb2" */"src/test/resources/nww3.all.grb"));
			for (final Iterator iterator = parameters.iterator(); iterator
					.hasNext();) {
				final String string = (String) iterator.next();
				log.info(string);
			}
		} catch (final IOException e) {
			log.error(e);
		}
	}

	@Test
	public void listParametersArchiveFiles() {
		final GribDecoder dec = new GribDecoderNetcdf();
		List<String> parameters;
		try {
			parameters = dec
					.listParameters(new File(
							/* "src/test/resources/multi_1.glo_30m.all.grb2" */"C:\\Users\\esteban\\workspace\\arfgen\\files\\WW3.gribs\\nww3.wind.199808.grb"));
			for (final Iterator iterator = parameters.iterator(); iterator
					.hasNext();) {
				final String string = (String) iterator.next();
				log.info(string);
			}
		} catch (final IOException e) {
			log.error(e);
		}
	}

}
