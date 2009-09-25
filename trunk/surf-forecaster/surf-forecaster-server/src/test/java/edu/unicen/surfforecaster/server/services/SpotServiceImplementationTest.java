/**
 * 
 */
package edu.unicen.surfforecaster.server.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ErrorCode;
import edu.unicen.surfforecaster.common.services.SpotService;
import edu.unicen.surfforecaster.common.services.UserService;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.common.services.dto.UserType;

/**
 * @author esteban
 * 
 */
public class SpotServiceImplementationTest {
	private final ApplicationContext context = new ClassPathXmlApplicationContext(
			"/services.xml");
	private final SpotService spotService;
	private final UserService userService;

	/**
	 * 
	 */
	public SpotServiceImplementationTest() {
		spotService = (SpotService) context.getBean("spotService");
		userService = (UserService) context.getBean("userService");
	}

	private Integer zoneId1;
	private Integer zoneId2;
	private Integer zoneId3;
	private Integer zoneId4;

	private Integer userId1;
	private Integer userId2;

	private Integer spot1Id;
	private Integer spot2Id;
	private Integer spot3Id;
	private Integer spot4Id;

	private Integer countryId;
	private Integer areaId;

	@Before
	public void createTestData() {
		try {
			// Create 4 Zones.
			final String zoneName1 = "zone1" + System.currentTimeMillis();
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

			spot1Id = spotService.addSpot("Guanchaco", 2.0, 1.0, zoneId1,
					userId1, true);
			spot2Id = spotService.addSpot("Guanchaco", 2.0, 1.0, zoneId2,
					userId1, false);
			spot3Id = spotService.addSpot("Guanchaco", 2.0, 1.0, zoneId3,
					userId2, false);
			spot4Id = spotService.addSpot("Guanchaco", 2.0, 1.0, zoneId4,
					userId2, true);
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
	 * Try to delete an area which has spots. This should not be allowed and
	 * DATABASE_ERROR should be thrown.
	 */
	@Test
	public void testDeleteRestrictions() {
		try {
			spotService.removeArea(areaId);
			Assert.fail("");
		} catch (final NeuralitoException e) {
			Assert
					.assertTrue(e.getErrorCode().equals(
							ErrorCode.DATABASE_ERROR));
		}
	}

	/**
	 * List all the spots user 1 is able to see. He may see all his spots plus
	 * the public spot of user 2.He should not see the private spot of user2.(
	 * See {@link SpotServiceImplementationTest#createTestData()}
	 */
	@Test
	public void test() {
		try {
			final Collection<SpotDTO> spots = spotService
					.getSpotsForUser(userId1);
			boolean spot1Founded = false;
			boolean spot2Founded = false;
			boolean spot4Founded = false;
			for (final Iterator<SpotDTO> iterator = spots.iterator(); iterator
					.hasNext();) {
				final SpotDTO spotDTO = iterator.next();
				Assert
						.assertFalse(
								"Spot with id:"
										+ spot3Id
										+ ", should not be seen by this user. Because it was created private",
								spotDTO.getId().equals(spot3Id));
				if (spotDTO.getId().equals(spot1Id)) {
					spot1Founded = true;
				}
				if (spotDTO.getId().equals(spot2Id)) {
					spot2Founded = true;
				}
				if (spotDTO.getId().equals(spot4Id)) {
					spot4Founded = true;
				}
			}
			Assert.assertTrue(
					"Spot 1 should ve been in the list of user spots.",
					spot1Founded);
			Assert.assertTrue(
					"Spot 2 should ve been in the list of user spots.",
					spot2Founded);
			Assert.assertTrue(
					"Spot 4 should ve been in the list of user spots.",
					spot4Founded);
		} catch (final NeuralitoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
