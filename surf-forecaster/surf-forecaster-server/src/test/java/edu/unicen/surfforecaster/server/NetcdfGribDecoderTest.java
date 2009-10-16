/**
 * 
 */
package edu.unicen.surfforecaster.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.zip.GZIPOutputStream;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import ucar.ma2.Array;
import edu.unicen.surfforecaster.server.dao.WaveWatchDAO;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast2;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3Parameter;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.LatestForecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder.Filter;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder.GribDecoderNetcdf;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder.PointValue;

/**
 * @author esteban
 * 
 */

public class NetcdfGribDecoderTest {
	protected ApplicationContext context; /*
										 * new ClassPathXmlApplicationContext(
										 * "/dao.xml");
										 */

	/**
	 * 
	 */
	public NetcdfGribDecoderTest() {
		// dao = (WaveWatchDAO) context.getBean("waveWatchDAO");
	}

	WaveWatchDAO dao;

	/**
	 * Read a grib2 file and obtain the forecasts for a single point.
	 */
	@Test
	@Ignore
	public void decodeGribFile() {
		final Collection<Point> p = new ArrayList<Point>();
		final Point point = new Point(38.1, 179.2);
		p.add(point);
		final Long initialTime = new Date().getTime();
		final GribDecoderNetcdf dec = new GribDecoderNetcdf();
		final Collection<Forecast> forecasts = dec.getForecasts(new File(
				"src/test/resources/multi_1.glo_30m.all.grb2"), p);
		final Long finalTime = new Date().getTime();
		System.out.println("It tooked: " + (finalTime - initialTime) / 1000);

		Assert.assertEquals(31, forecasts.size());

		// for (final Iterator iterator = forecasts.iterator();
		// iterator.hasNext();) {
		// final Forecast forecast = (Forecast) iterator.next();
		// System.out.println(forecast.getBaseDate());
		// }
	}

	/**
	 * Read a grib2 file and obtain the forecasts for a single point.
	 */
	@Test
	public void decodeGribFileForAllPoints2() {
		Long initialTime = System.currentTimeMillis();
		final GribDecoderNetcdf dec = new GribDecoderNetcdf();
		final Collection<Forecast2> forecasts = dec
				.getForecastsForAllGridPoints2(new File(
						"src/test/resources/multi_1.glo_30m.all.grb2"));
		final Long decodeTime = (System.currentTimeMillis() - initialTime) / 1000;
		System.out.println("Decode time: " + decodeTime);
		System.out.println("Forecasts decoded: " + forecasts.size());
		final LatestForecast latestForecasts = new LatestForecast();
		latestForecasts.setLatest2(forecasts);
		initialTime = System.currentTimeMillis();
		dao.saveDirect(latestForecasts);
		final Long saveTime = (System.currentTimeMillis() - initialTime) / 1000;
		System.out.println("Save time: " + saveTime);
		System.out.println("Elapsed time to decode and save "
				+ forecasts.size() + " forecasts:" + (decodeTime + saveTime));

	}

	/**
	 * Read a grib2 file and obtain the forecasts for a single point.
	 */
	@Test
	public void decodeGribFileForAllPoints22() {
		Long initialTime = System.currentTimeMillis();
		final GribDecoderNetcdf dec = new GribDecoderNetcdf();
		final Vector<Array> forecasts = dec
				.getForecastsForAllGridPoints22(new File(
						"src/test/resources/multi_1.glo_30m.all.grb2"));
		// System.gc();
		final Long decodeTime = (System.currentTimeMillis() - initialTime) / 1000;
		System.out.println("Decode time: " + decodeTime);
		System.out.println("Forecasts decoded: " + forecasts.size());
		initialTime = System.currentTimeMillis();
		// serialize(forecasts.get(0), "param0.array");
		final Long saveTime = (System.currentTimeMillis() - initialTime) / 1000;
		System.out.println("Transform and serialize time: " + saveTime);
		System.out.println("Elapsed time to decode and save "
				+ forecasts.size() + " forecasts:" + (decodeTime + saveTime));

	}

	private static void print(final Array data) {
		final int[] shape = data.getShape();
		for (int i = 0; i < shape.length; i++) {
			System.out.println(shape[i]);
		}

	}

	/**
	 * @param forecasts
	 */
	private void serialize(final Vector<Array> forecasts) {
		int i = 0;
		for (final Iterator iterator = forecasts.iterator(); iterator.hasNext();) {
			final Array array = (Array) iterator.next();
			try {
				final ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream("parameter" + i + ".ser"));
				out.writeObject(array.copyToNDJavaArray());
				out.flush();
				out.close();

			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}

	}

	/**
	 * @param forecasts
	 */
	private void serialize(final List<PointValue> values, final String filename) {
		System.out.println("Serializing values to: " + filename);
		final long initialTime = System.currentTimeMillis();
		try {
			final ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(filename));
			out.writeObject(values);
			out.flush();
			out.close();
			final long end = System.currentTimeMillis();
			System.out.println("Serialized " + values.size() + " items.");
			System.out.println("Elapsed Time " + (end - initialTime) / 1000);

		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

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
		System.out.println("Points founded:" + validPoints.size());
		for (final Iterator iterator = validPoints.iterator(); iterator
				.hasNext();) {
			final Point point = (Point) iterator.next();
			System.out.println("Point. Lat: " + point.getLatitude() + " Lon: "
					+ point.getLongitude());
		}
	}

	/**
	 * Read a grib2 file and obtain the forecasts for a single point.
	 */
	@Test
	public void decodeGribFileForPointReloaded() {
		Long initialTime = System.currentTimeMillis();
		final GribDecoderNetcdf dec = new GribDecoderNetcdf();
		final Vector<Array> forecasts = dec.getForecastsForPointReloaded(
				new File("src/test/resources/multi_1.glo_30m.all.grb2"),
				new Point(39.0F, 140.0F));
		final Array array = forecasts.get(0);
		final int[] shape = array.getShape();
		for (int i = 0; i < shape.length; i++) {
			System.out.println(shape[i]);
		}
		System.gc();
		final Long decodeTime = (System.currentTimeMillis() - initialTime) / 1000;
		System.out.println("Decode time: " + decodeTime);
		System.out.println("Forecasts decoded: " + forecasts.size());
		forecasts.clear();
		initialTime = System.currentTimeMillis();
		// serialize(forecasts.get(0), "array");
		final Long saveTime = (System.currentTimeMillis() - initialTime) / 1000;
		System.out.println("Transform and serialize time: " + saveTime);
		System.out.println("Elapsed time to decode and save "
				+ forecasts.size() + " forecasts:" + (decodeTime + saveTime));

	}

	/**
	 * @param array
	 * @param filename
	 */
	private void serialize(final float[][][] array, final String filename) {
		System.out.println("Serializing 3DArray");
		final long initialTime = System.currentTimeMillis();
		try {
			final ObjectOutputStream out = new ObjectOutputStream(
					new GZIPOutputStream(new FileOutputStream(filename)));
			out.writeObject(array);
			out.flush();
			out.close();
			final long end = System.currentTimeMillis();
			System.out.println("Time to serialize UCAR Array with "
					+ array.length + " values: " + (end - initialTime) / 1000);

		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void filterArray() {
		final Filter filter = new Filter();
		final GribDecoderNetcdf dec = new GribDecoderNetcdf();
		final Vector<Array> forecasts = dec
				.getForecastsForAllGridPoints22(new File(
						"src/test/resources/multi_1.glo_30m.all.grb2"));
		final ArrayList<PointValue> values = filter
				.filterOnlyValidValues(forecasts.get(0));
		serialize(values, "valuesArrayList1.ser");
	}

	@Test
	public void decodeGribAndSerialize() {
		final Filter filter = new Filter();
		final GribDecoderNetcdf dec = new GribDecoderNetcdf();
		final Vector<Array> forecasts = dec
				.getForecastsForAllGridPoints22(new File(
						"src/test/resources/multi_1.glo_30m.all.grb2"));
		final float[][][] array = (float[][][]) forecasts.get(0)
				.copyToNDJavaArray();
		serialize(array, "3DArray.ser.zip");
	}

	public float[][][] deserialize(final String file) {
		try {
			System.out.println("Deserializing array:");
			final long initialTime = System.currentTimeMillis();
			final ObjectInputStream in = new ObjectInputStream(
					new FileInputStream(file));
			// Deserialize the object
			final float[][][] array = (float[][][]) in.readObject();
			in.close();
			final long end = System.currentTimeMillis();
			System.out.println("Time to deserialize array: "
					+ (end - initialTime) / 1000);
			return array;

		} catch (final ClassNotFoundException e) {
		} catch (final IOException e) {
		}
		return null;

	}

	@Test
	public void deserialize() {

		final float[][][] values = this.deserialize("3DArray.ser");

	}

	@Test
	public void numberOfPoints() {
		final GribDecoderNetcdf dec = new GribDecoderNetcdf();
		final Filter filter = new Filter();
		final Array array = dec.getAllForecastForTimeAndParam(new File(
				"src/test/resources/multi_1.glo_30m.all.grb2"), 0,
				WW3Parameter.SWELL_WAVE_HEIGHT.getValue());
		final Collection values = filter.getNonNanValues(array);

		System.out.println("Number of values per time per parameter: "
				+ values.size());
	}

	@Test
	public void serializeOneParameterForTimes0and1() {
		final GribDecoderNetcdf dec = new GribDecoderNetcdf();
		final Filter filter = new Filter();
		Array array = dec.getAllForecastForTimeAndParam(new File(
				"src/test/resources/multi_1.glo_30m.all.grb2"), 0,
				WW3Parameter.WIND_SPEED.getValue());
		final List values = filter.getNonNanValues(array);

		array = dec.getAllForecastForTimeAndParam(new File(
				"src/test/resources/multi_1.glo_30m.all.grb2"), 1,
				WW3Parameter.WIND_SPEED.getValue());
		values.addAll(filter.getNonNanValues(array));

		// this.serialize(values, "0hAnd3HSwellWaveHeight.ser");
		writeToFile(values, "0And3h.csv");

		System.out
				.println("Number of values for 0 and 3 hourse and one parameter: "
						+ values.size());
	}

	private Array getArrayFromFile(final File file) {
		try {
			System.out.println("Deserializing array:");
			final long initialTime = System.currentTimeMillis();
			final ObjectInputStream in = new ObjectInputStream(
					new FileInputStream(file));
			// Deserialize the object
			final Array array = (Array) in.readObject();
			in.close();
			final long end = System.currentTimeMillis();
			System.out.println("Time to deserialize array: "
					+ (end - initialTime) / 1000);
			return array;

		} catch (final ClassNotFoundException e) {
		} catch (final IOException e) {
		}
		return null;
	}

	private void writeToFile(final List<PointValue> values,
			final String fileName) {
		try {
			final BufferedWriter output = new BufferedWriter(new FileWriter(
					new File(fileName)));

			for (final Iterator iterator = values.iterator(); iterator
					.hasNext();) {
				final PointValue pointValue = (PointValue) iterator.next();
				final String line = pointValue.getLat() + ","
						+ pointValue.getLon() + "," + pointValue.getVal()
						+ ",25/12/90 6H";
				// use buffering

				// FileWriter always assumes default encoding is OK!
				output.write(line);
				output.newLine();

			}

			output.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void decodeAll() {
		final GribDecoderNetcdf dec = new GribDecoderNetcdf();
		try {
			dec.getForecastForTime(new File(
					"src/test/resources/multi_1.glo_30m.all.grb2"), 1);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
