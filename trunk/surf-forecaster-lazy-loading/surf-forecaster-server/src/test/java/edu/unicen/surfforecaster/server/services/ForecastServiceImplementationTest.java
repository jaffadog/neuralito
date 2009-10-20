/**
 * 
 */
package edu.unicen.surfforecaster.server.services;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ForecastService;
import edu.unicen.surfforecaster.common.services.SpotService;
import edu.unicen.surfforecaster.common.services.UserService;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.common.services.dto.UserType;

/**
 * @author esteban
 * 
 */

public class ForecastServiceImplementationTest {

	private final ForecastService forecastService;
	protected static final ApplicationContext context = new ClassPathXmlApplicationContext(
			"/services.xml");
	protected SpotService spotService;
	protected UserService userService;

	/**
* 
*/
	public ForecastServiceImplementationTest() {
		spotService = (SpotService) context.getBean("spotService");
		userService = (UserService) context.getBean("userService");
		forecastService = (ForecastService) context.getBean("forecastService");

	}

	private Integer zoneId1;
	private Integer zoneId2;
	private Integer zoneId3;
	private Integer zoneId4;
	private String zoneName1;
	private Integer userId1;
	private Integer userId2;

	protected Integer spot1Id;
	protected Integer spot2Id;
	protected Integer spot3Id;
	protected Integer spot4Id;

	private Integer countryId;
	private Integer areaId;

	@Before
	public void createTestData() {
		try {

			// Create 4 Zones.
			zoneName1 = "zone1" + System.currentTimeMillis();
			final String zoneName2 = "zone2" + System.currentTimeMillis();
			final String zoneName3 = "zone3" + System.currentTimeMillis();
			final String zoneName4 = "zone4" + System.currentTimeMillis();
			final Map<String, String> areaNamesMap = new HashMap<String, String>();
			areaNamesMap.put("es", "chapa");
			areaId = spotService.addArea(areaNamesMap);
			final Map<String, String> countryNamesMap = new HashMap<String, String>();
			countryNamesMap.put("es", "argentina");
			countryId = spotService.addCountry(countryNamesMap, areaId);
			zoneId1 = spotService.addZone(zoneName1, countryId);
			zoneId2 = spotService.addZone(zoneName2, countryId);
			zoneId3 = spotService.addZone(zoneName3, countryId);
			zoneId4 = spotService.addZone(zoneName4, countryId);

			// Create 2 Users

			userId1 = userService.addUser("name", "lastName", System
					.currentTimeMillis()
					+ "@.com", System.currentTimeMillis() + "user", "pass",
					UserType.ADMINISTRATOR);
			userId2 = userService.addUser("name", "lastName", System
					.currentTimeMillis()
					+ "@.com", System.currentTimeMillis() + "user", "pass",
					UserType.ADMINISTRATOR);

			// Create 4 Spots

			spot1Id = spotService.addSpot("Guanchhyhaco", 75.0F, 0.5F, zoneId1,
					userId1, true, "ACT");
			spot2Id = spotService.addSpot("Guanchaco", 2.0F, 1.0F, zoneId2,
					userId1, false, "UTC");
			spot3Id = spotService.addSpot("Guanchaco", 2.0F, 1.0F, zoneId3,
					userId2, false, "UTC");
			spot4Id = spotService.addSpot("Guanchaco", 2.0F, 1.0F, zoneId4,
					userId2, true, "ACT");
		} catch (final NeuralitoException e) {
			System.out.println(e.getMessage());
			Assert.fail(e.toString());
		}
	}

	@After
	public void removeTestData() {
		try {
			spotService.removeSpot(spot1Id);
			spotService.removeSpot(spot2Id);
			spotService.removeSpot(spot3Id);
			spotService.removeSpot(spot4Id);

			spotService.removeZone(zoneId1);
			spotService.removeZone(zoneId2);
			spotService.removeZone(zoneId3);
			spotService.removeZone(zoneId4);

			spotService.removeCountry(countryId);

			spotService.removeArea(areaId);

			userService.removeUser(userId1);
			userService.removeUser(userId2);

		} catch (final NeuralitoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 *
	 */
	@Test
	public void createWW3Forecaster() {

		try {

			final int forecasterId = forecastService.createWW3Forecaster(
					spot1Id, new PointDTO(75.0F, 0.5F));
			System.out
					.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + forecasterId);
			Assert.assertTrue(forecasterId > 0);
		} catch (final NeuralitoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void getForecaster() {

		try {

			final int forecasterId = forecastService.createWW3Forecaster(
					spot1Id, new PointDTO(75.0F, 0.5F));
			final List<ForecastDTO> forecasts = forecastService
					.getLatestForecasts(forecasterId);
			System.out.println(forecasts.size());
			Assert.assertTrue(forecasts.size() > 0);
		} catch (final NeuralitoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void getNearByGridPoints() {

		try {

			List<PointDTO> nearbyGridPoints = forecastService
					.getNearbyGridPoints(75.0F, 0.5F);
			Assert.assertTrue(nearbyGridPoints.size() == 1);
			nearbyGridPoints = forecastService.getNearbyGridPoints(400F, 500F);
			Assert.assertTrue(nearbyGridPoints.size() == 0);

		} catch (final NeuralitoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void getArchivedForecasts() {

		try {
			final int forecasterId = forecastService.createWW3Forecaster(
					spot1Id, new PointDTO(75.0F, 0.5F));
			final List<ForecastDTO> nearbyGridPoints = forecastService
					.getArchivedForecasts(forecasterId, new GregorianCalendar(
							2001, 02, 02), new GregorianCalendar(2010, 02, 02));
			Assert.assertTrue(nearbyGridPoints.size() > 0);
		} catch (final NeuralitoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
