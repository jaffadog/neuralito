/**
 * 
 */
package edu.unicen.surfforecaster.server;

import java.io.File;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.unicen.surfforecaster.server.domain.WaveWatchModel;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.DownloaderJobListener;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.NoaaWaveWatchModel;

/**
 * @author esteban
 * 
 */
public class NoaaWaveWatchModelTest {
	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"/dao.xml");

	@Test
	public void init() {

		final WaveWatchModel model = (WaveWatchModel) context
				.getBean("waveWatchDaoTarget");
		final Collection<Forecast> latestForecast = model
				.getLatestForecast(new Point(75.0F, 125.0F));
		System.out.println(latestForecast.size());
	}

	@Test
	@Ignore
	public void updateLatestForecast() {

		final WaveWatchModel model = (WaveWatchModel) context
				.getBean("waveWatchDaoTarget");
		((NoaaWaveWatchModel) model).update(new DownloaderJobListener(),
				new File("src/test/resources/multi_1.glo_30m.all.grb2"));
		final Collection<Forecast> latestForecast = model
				.getLatestForecast(new Point(75.0F, 125.0F));
		System.out.println(latestForecast.size());
	}

	@Test
	public void getLatestForecast() {

		final WaveWatchModel model = (WaveWatchModel) context
				.getBean("waveWatchDaoTarget");
		final Collection<Forecast> latestForecast = model
				.getLatestForecast(new Point(77.0F, 0.5F));
		System.out.println(latestForecast.size());
	}

	@Test
	public void nearByGridPoints() {
		final WaveWatchModel model = (WaveWatchModel) context
				.getBean("waveWatchDaoTarget");
		final List<Point> nearbyGridPoints = model
				.getNearbyGridPoints(new Point(75.0F, 0.6F));
		for (final Iterator iterator = nearbyGridPoints.iterator(); iterator
				.hasNext();) {
			final Point point = (Point) iterator.next();
			System.out.println(" sa" + point);
		}
	}

	@Test
	public void isGridPoint() {
		final WaveWatchModel model = (WaveWatchModel) context
				.getBean("waveWatchDaoTarget");

		Assert.assertFalse(model.isGridPoint(new Point(75.0F, 0.7F)));
		Assert.assertTrue(model.isGridPoint(new Point(-75.0F, -102.5F)));
	}

	@Test
	public void getArchivedForecasts() {
		final WaveWatchModel model = (WaveWatchModel) context
				.getBean("waveWatchDaoTarget");
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
	public void getLatestForecastUpdate() {
		final WaveWatchModel model = (WaveWatchModel) context
				.getBean("waveWatchDaoTarget");
		System.out.println(model.getLatestForecastTime().getDay());
	}

}