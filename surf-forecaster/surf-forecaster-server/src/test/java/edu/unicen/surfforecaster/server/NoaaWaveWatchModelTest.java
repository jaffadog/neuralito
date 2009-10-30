/**
 * 
 */
package edu.unicen.surfforecaster.server;

import java.io.File;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.unicen.surfforecaster.common.services.dto.WW3Parameter;
import edu.unicen.surfforecaster.server.domain.NoaaWaveWatchModel;
import edu.unicen.surfforecaster.server.domain.download.DownloaderJobListener;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.Point;

/**
 * @author esteban
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/services.xml" })
public class NoaaWaveWatchModelTest {
	
	@Autowired
	NoaaWaveWatchModel model;
	
	Logger log = Logger.getLogger(this.getClass());

	@Test
	
	public void updateLatestForecast() {

		((NoaaWaveWatchModel) model).update(new DownloaderJobListener(),
				new File("src/test/resources/multi_1.glo_30m.all.grb2"));
		final Collection<Forecast> latestForecast = model
				.getLatestForecast(new Point(75.0F, 125.0F));
		
		log.info(""+latestForecast.size());
	}

	@Test
	public void getLatestForecast() {

		final List<Forecast> latestForecast = model
				.getLatestForecast(new Point(-38.5F, -57.5F));
		Forecast forecast = latestForecast.get(0);
		log.info(forecast.getParameter(WW3Parameter.WIND_SPEED.toString()));
		log.info(forecast.getDTO().getMap().get(WW3Parameter.WIND_SPEED.toString()).getValue());
		log.info("Number of latest forecasts:"+latestForecast.size());
	}

	@Test
	public void nearByGridPoints() {
		final List<Point> nearbyGridPoints = model
				.getNearbyGridPoints(new Point(75.0F, 0.6F));
		String points="";
		for (final Iterator iterator = nearbyGridPoints.iterator(); iterator
				.hasNext();) {
			final Point point = (Point) iterator.next();
			points = points +point.toString();
		}
		
		log.info("Nearby grid Points found"+ points);
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
				new Point(75.0F, 0.5F), new GregorianCalendar(1991, 02, 02),
				new GregorianCalendar(1992, 02, 02));
		Assert.assertEquals(0, archivedForecasts.size());
		archivedForecasts = model.getArchivedForecasts(new Point(75.0F, 0.5F),
				new GregorianCalendar(2008, 02, 02), new GregorianCalendar(
						2010, 02, 02));
		Assert.assertTrue(archivedForecasts.size() > 0);
	}

	@Test
	@Ignore
	public void getLatestForecastUpdate() {
		log.info("Latest forecast update time: "+model.getLatestForecastTime().getDay());
	}

}