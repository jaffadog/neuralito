/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.wavewatch;

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

/**
 * @author esteban
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/services.xml" })
public class WaveWatchSystemTest {

	@Autowired
	WaveWatchSystemImpl model;

	Logger log = Logger.getLogger(this.getClass());

	/**
	 * Tests the model decoding and storing of new wave watch data.
	 * 
	 * @throws JobExecutionException
	 */
	@Test
	@Ignore
	public void updateLatestForecast() throws JobExecutionException {

		final Collection<Forecast> latestForecast = model
				.getForecasts(new Point(75.0F, 125.0F));

		log.info("" + latestForecast.size());
	}

	@Test
	public void getLatestForecast() {

		final List<Forecast> latestForecast = model.getForecasts(new Point(
				-38.5F, -57.5F));
		final Forecast forecast = latestForecast.get(0);
		final Set<String> parameters = forecast.getParameters();
		for (final Iterator iterator = parameters.iterator(); iterator
				.hasNext();) {
			final String string = (String) iterator.next();
			log.info(string + ": " + forecast.getParameter(string));
		}
		log.info("Number of latest forecasts:" + latestForecast.size());
	}

	@Test
	public void nearByGridPoints() {
		final List<Point> nearbyGridPoints = model.getPointNeighbors(new Point(
				75.0F, 0.6F));
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
		final long init = System.currentTimeMillis();
		final Collection<Collection<File>> files = new ArrayList<Collection<File>>();
		for (int i = 1997; i <= 2004; i++) {
			for (int j = 1; j <= 12; j++) {
				files.add(generateFile(i, j));
			}
		}
		final Point point1 = new Point(22.0F, -158.75F);
		final Point point2 = new Point(21.0F, -158.75F);
		final Point point3 = new Point(22.0F, -157.50F);
		final Point point4 = new Point(21.0F, -157.50F);
		final Collection<Point> points = new ArrayList<Point>();
		points.add(point1);
		points.add(point2);
		points.add(point3);
		points.add(point4);
		model.importForecasts(files, points);
	}

	private Collection<File> generateFile(final int i, final int j) {
		final Collection<File> files = new ArrayList<File>();
		final DecimalFormat format = new DecimalFormat();
		format.setMinimumIntegerDigits(2);

		final String month = format.format(j);
		final String yearMonth = i + month;
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