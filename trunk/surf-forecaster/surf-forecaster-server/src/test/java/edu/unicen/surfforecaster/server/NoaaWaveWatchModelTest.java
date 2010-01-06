/**
 * 
 */
package edu.unicen.surfforecaster.server;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.Point;
import edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystemImpl;

/**
 * @author esteban
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/services.xml" })
public class NoaaWaveWatchModelTest {

	@Autowired
	WaveWatchSystemImpl model;

	Logger log = Logger.getLogger(this.getClass());

	/**
	 * Tests the model decoding and storing of new wave watch data.
	 * 
	 * @throws JobExecutionException
	 */
	@Test
	public void updateLatestForecast() throws JobExecutionException {

		model.execute(null);
		final Collection<Forecast> latestForecast = model
				.getForecasts(new Point(75.0F, 125.0F));

		log.info("" + latestForecast.size());
	}

	@Test
	public void getLatestForecast() {

		final List<Forecast> latestForecast = model.getForecasts(new Point(
				-38.5F, -57.5F));
		Forecast forecast = latestForecast.get(0);
		Set<String> parameters = forecast.getParameters();
		for (Iterator iterator = parameters.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			log.info(string + ": " + forecast.getParameter(string));
		}
		log.info("Number of latest forecasts:" + latestForecast.size());
	}

	@Test
	public void nearByGridPoints() {
		final List<Point> nearbyGridPoints = model.getPointNeighbors(new Point(
				75.0F, 0.6F), 0.5D);
		String points = "";
		for (final Iterator iterator = nearbyGridPoints.iterator(); iterator
				.hasNext();) {
			final Point point = (Point) iterator.next();
			points = points + point.toString();
		}

		log.info("Nearby grid Points found" + points);
	}

	@Test
	public void isGridPoint() {
		Assert.assertFalse(model.isGridPoint(new Point(75.0F, 0.7F)));
		Assert.assertTrue(model.isGridPoint(new Point(-75.0F, -102.5F)));
	}

	@Test
	@Ignore
	public void getArchivedForecasts() {
		List<Forecast> archivedForecasts = model.getArchivedForecasts(
				new Point(75.0F, 0.5F), new GregorianCalendar(1991, 02, 02)
						.getTime(), new GregorianCalendar(1992, 02, 02)
						.getTime());
		Assert.assertEquals(0, archivedForecasts.size());
		archivedForecasts = model.getArchivedForecasts(new Point(75.0F, 0.5F),
				new GregorianCalendar(2008, 02, 02).getTime(),
				new GregorianCalendar(2010, 02, 02).getTime());
		Assert.assertTrue(archivedForecasts.size() > 0);
	}

	@Test
	public void getLatestForecastUpdate() {
		log.info("Latest forecast update time: "
				+ model.getLatestForecastTime().getDay());
	}

	@Test
	public void importArchives() throws IOException {
		Collection<File> files = new ArrayList<File>();
		for (int i = 1997; i <= 2004; i++) {
			for (int j = 1; j <= 12; j++) {
				files = generateFile(i, j);
				model.importForecasts(files);
			}
		}
	}

	private Collection<File> generateFile(int i, int j) {
		Collection<File> files = new ArrayList<File>();
		DecimalFormat format = new DecimalFormat();
		format.setMinimumIntegerDigits(2);

		String month = format.format(j);
		String yearMonth = i + month;
		files.add(new File(
				"C:\\Users\\esteban\\workspace\\arfgen\\files\\WW3.gribs\\nww3.wind."
						+ yearMonth + ".grb"));
		files.add(new File(
				"C:\\Users\\esteban\\workspace\\arfgen\\files\\WW3.gribs\\nww3.dp."
						+ yearMonth + ".grb"));
		files.add(new File(
				"C:\\Users\\esteban\\workspace\\arfgen\\files\\WW3.gribs\\nww3.hs."
						+ yearMonth + ".grb"));
		files.add(new File(
				"C:\\Users\\esteban\\workspace\\arfgen\\files\\WW3.gribs\\nww3.tp."
						+ yearMonth + ".grb"));

		return files;
	}

}