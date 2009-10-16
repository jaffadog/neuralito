/**
 * 
 */
package edu.unicen.surfforecaster.server;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.DataManager;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.downloader.DownloaderJobListener;

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
	 * Notify data manager that a new forecast grib file exists. Datamanager
	 * should decode the file and save the latest forecasts into DB. Also if
	 * previous latest forecasts exists they should be archived.
	 */
	@Test
	public void test() {
		// final Point point = new Point(39D, 170D);
		// final Point point2 = new Point(23.1, 400D);
		// final Long initialTime = System.currentTimeMillis();
		// dataManager.registerPoint(point);
		dataManager.update(new DownloaderJobListener(), new File(

		"src/test/resources/multi_1.glo_30m.all.grb2"));

	}

	/**
	 * 
	 // Already covered by ForecastServiceImplementationTest
	 */
	@Test
	@Ignore
	public void testGetValidGridPoints() {
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
