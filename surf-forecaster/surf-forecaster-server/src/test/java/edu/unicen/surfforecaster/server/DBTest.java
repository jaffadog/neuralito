/**
 * 
 */
package edu.unicen.surfforecaster.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import edu.unicen.surfforecaster.server.domain.ForecastPlain;
import edu.unicen.surfforecaster.server.domain.WaveWatchSystem;

/**
 * @author esteban
 * 
 */
public class DBTest {
	// protected ApplicationContext context = new
	// ClassPathXmlApplicationContext(
	// "/dao.xml");
Logger log = Logger.getLogger(this.getClass());
	/**
	 * 
	 */
	public DBTest() {
		// dao = (WaveWatchDAO) context.getBean("waveWatchDAO");
	}

	WaveWatchSystem dao;

	// @Test
	// @Ignore
	// public void testWritingFromFile() {
	// final File file = new File("c:/latest.csv");
	// if (file.exists()) {
	// file.delete();
	// }
	// for (int i = 0; i < 1; i++) {
	// final List forecasts = createForecasts(100);
	// generateFile(file, forecasts);
	//
	// // clearList(forecasts);
	// // forecasts.clear();
	// // System.gc();
	// }
	// dao.insertIntoLatestForecastFromFile(file);
	// }

	/**
	 * Generates the
	 * @param forecasts
	 */
	private void generateFile(final File file, final List forecasts) {
		final long init = System.currentTimeMillis();
		try {
			final BufferedWriter output = new BufferedWriter(new FileWriter(
					file, true));

			for (final Iterator iterator = forecasts.iterator(); iterator
					.hasNext();) {
				final ForecastPlain forecast = (ForecastPlain) iterator.next();
				final String line = "x2009-02-03" + ","
						+ forecast.getValidTime() + ","
						+ forecast.getLatitude() + ","
						+ forecast.getLongitude() + ","
						+ forecast.getWindWaveHeight() + ","
						+ forecast.getWindWavePeriod() + ","
						+ forecast.getWindWaveDirection() + ","
						+ forecast.getSwellWaveHeight() + ","
						+ forecast.getSwellWavePeriod() + ","
						+ forecast.getSwellWaveDirection() + ","
						+ forecast.getCombinedWaveHeight() + ","
						+ forecast.getPeakWavePeriod() + ","
						+ forecast.getPeakWaveDirection() + ","
						+ forecast.getWindSpeed() + ","
						+ forecast.getWindDirection() + ","
						+ forecast.getWindU() + "," + forecast.getWindV() + "e";
				output.append(line);
				output.newLine();

			}
			output.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		final long end = System.currentTimeMillis();
		log.info("Writing forecasts to file");
		log.info("Elapsed time: " + (end - init) / 1000);
	}

	@Test
	@Ignore
	public void testReading() {
		final float lat = 70F;
		final float lon = 21F;
		final GregorianCalendar from = null;
		final GregorianCalendar to = null;
		// final List<ForecastArch> forecasts = dao.getArchivedForecasts(
		// new Point(lat, lon), from, to);
	}

	/**
	 * @param forecasts
	 */
	private void clearList(final List forecasts) {
		for (final Iterator iterator = forecasts.iterator(); iterator.hasNext();) {
			Object object = iterator.next();
			object = null;
		}
	}

	/**
	 * @param i
	 * @return
	 */
	private List createForecasts(final int i) {
		final long init = System.currentTimeMillis();
		final List forecasts = new ArrayList();
		final Random random = new Random();

		for (int j = 0; j < i; j++) {
			final long date = random.nextInt(100000000);
			final int time = random.nextInt(7);
			final int lat = random.nextInt(400);
			final int lon = random.nextInt(400);

			final int year = 2009;
			final int month = 02;
			final int day = 03;
			forecasts.add(new ForecastPlain(new Date(), time, new Float(lat), new Float(lon), Float.NaN,
					new Float(random.nextInt(5000)), new Float(random
							.nextInt(5000)), new Float(random.nextInt(5000)),
					new Float(random.nextInt(5000)), new Float(random
							.nextInt(5000)), new Float(random.nextInt(5000)),
					new Float(random.nextInt(5000)), new Float(random
							.nextInt(5000)), new Float(random.nextInt(5000)),
					new Float(random.nextInt(5000)), new Float(random
							.nextInt(5000)), new Float(random.nextInt(5000))));
		}
		final long end = System.currentTimeMillis();
		log.info("Time to generate " + i + " forecasts: "
				+ (end - init) / 1000);
		return forecasts;
	}
	@Test
	public void test(){
		
		System.out.println(System.getProperty("file.encoding"));
	}
}
