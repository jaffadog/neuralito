/**
 * 
 */
package edu.unicen.surfforecaster.server;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.unicen.surfforecaster.common.services.dto.WW3Parameter;
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
		log.info(forecast.getParameter(WW3Parameter.WIND_SPEED.toString()));
		log.info(forecast.getDTO(TimeZone.getTimeZone("UTC")).getMap().get(
				WW3Parameter.WIND_SPEED.toString()).getValue());
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
	@Ignore
	public void getLatestForecastUpdate() {
		log.info("Latest forecast update time: "
				+ model.getLatestForecastTime().getDay());
	}


}