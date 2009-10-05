/**
 * 
 */
package edu.unicen.surfforecaster.server;

import java.util.Collection;
import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.DataManager;

/**
 * @author esteban
 * 
 */
public class DataManagerTest {

	protected ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			"/services.xml");
	private final DataManager dataManager;

	/**
	 * 
	 */

	public DataManagerTest() {
		dataManager = (DataManager) context.getBean("ww3DataManager");

	}

	/**
	 * 
	 */
	@Test
	public void test() {
		final Point point = new Point(39D, 170D);
		final Point point2 = new Point(23D, 170D);
		dataManager.registerPoint(point);
		// dataManager.update(new DownloaderJobListener(), new File(
		// "src/test/resources/multi_1.glo_30m.all.grb2"));

		final Collection<Forecast> latestForecast = dataManager
				.getLatestForecast(point2);
		Assert.assertEquals(0, latestForecast.size());

		final Collection<Forecast> latestForecast2 = dataManager
				.getLatestForecast(point);
		Assert.assertEquals(31, latestForecast2.size());
	}

	/**
	 * 
	 */
	@Test
	public void testGetValidGridPoints() {

		// dataManager.registerPoint(point);
		// dataManager.update(new DownloaderJobListener(), new File(
		// "src/test/resources/multi_1.glo_30m.all.grb2"));

		// final Collection<Forecast> latestForecast = dataManager
		// .getLatestForecast(point2);
		// Assert.assertEquals(0, latestForecast.size());
		//
		// final Collection<Forecast> latestForecast2 = dataManager
		// .getLatestForecast(point);
		// Assert.assertEquals(31, latestForecast2.size());
		final Collection<Point> nearbyGridPoints = dataManager
				.getNearbyGridPoints(new Point(39.1, 133.1));
		for (final Iterator iterator = nearbyGridPoints.iterator(); iterator
				.hasNext();) {
			final Point point3 = (Point) iterator.next();
			System.out.println(point3.getLatitude() + "    "
					+ point3.getLongitude());

		}
	}

}
