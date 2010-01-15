/**
 * 
 */
package edu.unicen.surfforecaster.server.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ComparationService;
import edu.unicen.surfforecaster.common.services.SpotService;
import edu.unicen.surfforecaster.common.services.UserService;
import edu.unicen.surfforecaster.common.services.dto.ComparationDTO;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.common.services.dto.UserType;

/**
 * @author esteban
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/services.xml" })
public class ComparationServiceImplementationTest {
	private final Logger log = Logger.getLogger(this.getClass());
	@Autowired
	protected SpotService spotService;
	@Autowired
	protected UserService userService;
	@Autowired
	protected ComparationService comparationService;
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
	/**
	 * Id of a zone with no spots.
	 */
	private Integer emptyZoneId;
	private Integer spot5Id;
	private Integer spot6Id;

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
			emptyZoneId = spotService.addZone(zoneName4 + "one", countryId);

			// Create 2 Users

			userId1 = userService.addUser("name", "lastName", System
					.currentTimeMillis()
					+ "@.com", System.currentTimeMillis() + "user", "pass",
					UserType.ADMINISTRATOR);
			userId2 = userService.addUser("name", "lastName", System
					.currentTimeMillis()
					+ "@.com", System.currentTimeMillis() + "user", "pass",
					UserType.ADMINISTRATOR);

			// Create 6 Spots
			final TimeZone timeZone = TimeZone.getTimeZone("UTC");
			spot1Id = spotService.addSpot("Guanchaco", 52.6963610F,
					52.6963677777F, zoneId1, userId1, true, timeZone);
			spot2Id = spotService.addSpot("Guanchaco", 2.0F, 1.0F, zoneId2,
					userId1, false, timeZone);
			spot3Id = spotService.addSpot("Guanchaco", 2.0F, 1.0F, zoneId3,
					userId2, false, timeZone);
			spot4Id = spotService.addSpot("Guanchaco", 2.0F, 1.0F, zoneId4,
					userId2, true, timeZone);
			spot5Id = spotService.addSpot("Guanchaco", 2.0F, 1.0F, zoneId1,
					userId2, true, timeZone);
			spot6Id = spotService.addSpot("Guanchaco", 2.0F, 1.0F, zoneId1,
					userId2, false, timeZone);
		} catch (final NeuralitoException e) {
			log.info(e.getMessage());
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
			spotService.removeSpot(spot5Id);
			spotService.removeSpot(spot6Id);

			spotService.removeZone(zoneId1);
			spotService.removeZone(zoneId2);
			spotService.removeZone(zoneId3);
			spotService.removeZone(zoneId4);
			spotService.removeZone(emptyZoneId);

			spotService.removeCountry(countryId);

			spotService.removeArea(areaId);

			userService.removeUser(userId1);
			userService.removeUser(userId2);

		} catch (final NeuralitoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void addComparation() throws NeuralitoException {
		final String comparationName = "Willa Comparation";
		final String comparationDescription = "A test comparation";
		final List<Integer> spotsIds = new ArrayList<Integer>();

		spotsIds.add(spot1Id);
		spotsIds.add(spot2Id);

		final ComparationDTO addComparation = comparationService
				.addComparation(comparationName, comparationDescription,
						userId1, spotsIds);
		Assert.assertTrue(addComparation.getId() > 0);
		Assert.assertEquals(comparationName, addComparation.getName());
		Assert.assertEquals(comparationDescription, addComparation
				.getDescription());

		Assert.assertEquals(spotsIds.size(), addComparation.getSpots().size());

		final List<SpotDTO> spots = addComparation.getSpots();

		for (int i = 0; i < spots.size(); i++) {
			Assert.assertEquals(spotsIds.toArray()[i], ((SpotDTO) spots
					.toArray()[i]).getId());
		}
	}

	@Test
	public void updateComparation() throws NeuralitoException {
		final String comparationName = "Willa Comparation";
		final String comparationDescription = "A test comparation";
		final List<Integer> spotsIds = new ArrayList<Integer>();
		spotsIds.add(spot1Id);
		spotsIds.add(spot2Id);

		final ComparationDTO originalComparation = comparationService
				.addComparation(comparationName, comparationDescription,
						userId1, spotsIds);

		final String comparationDescriptionModified = originalComparation
				.getDescription()
				+ "modified";
		final List<Integer> spotsIdsModified = new ArrayList<Integer>();
		spotsIdsModified.add(spot3Id);
		//
		final ComparationDTO modifiedComparation = comparationService
				.updateComparation(originalComparation.getId(),
						comparationDescriptionModified, spotsIdsModified);

		Assert.assertEquals(originalComparation.getId(), modifiedComparation
				.getId());
		Assert.assertEquals(comparationDescriptionModified, modifiedComparation
				.getDescription());
		Assert.assertEquals(spotsIdsModified.size(), modifiedComparation
				.getSpots().size());
		final List<SpotDTO> spots = modifiedComparation.getSpots();
		for (int i = 0; i < spots.size(); i++) {
			Assert.assertEquals(spotsIdsModified.toArray()[i], ((SpotDTO) spots
					.toArray()[i]).getId());
		}

	}

	@Test
	public void removeComparation() throws NeuralitoException {
		final String comparationName = "Willa Comparation";
		final String comparationDescription = "A test comparation";
		final List<Integer> spotsIds = new ArrayList<Integer>();
		spotsIds.add(spot1Id);
		spotsIds.add(spot2Id);

		final ComparationDTO originalComparation = comparationService
				.addComparation(comparationName, comparationDescription,
						userId1, spotsIds);

		comparationService.removeComparation(originalComparation.getId());
		// Check that comparation doesnt exist anymore
		final ComparationDTO comparation = comparationService
				.getComparationById(originalComparation.getId());

		Assert.assertNull(comparation);

		final List<ComparationDTO> comparations = comparationService
				.getComparationsForUserId(userId1);
		// Check that association between comparation and user was removed.
		for (final ComparationDTO comparationDTO : comparations) {
			if (comparationDTO.getId().equals(originalComparation.getId())) {
				Assert.fail();
			}
		}
	}

	@Test
	public void getComparationsForUserId() throws NeuralitoException {
		final String comparationName = "Willa Comparation";
		final String comparationDescription = "A test comparation";
		final List<Integer> spotsIds = new ArrayList<Integer>();
		spotsIds.add(spot1Id);
		spotsIds.add(spot2Id);

		final ComparationDTO originalComparation = comparationService
				.addComparation(comparationName, comparationDescription,
						userId1, spotsIds);

		final List<ComparationDTO> comparationsForUserId = comparationService
				.getComparationsForUserId(userId1);

		Assert.assertEquals(1, comparationsForUserId.size());
		Assert.assertEquals(comparationName, comparationsForUserId.get(0)
				.getName());
		Assert.assertEquals(originalComparation.getId(), comparationsForUserId
				.get(0).getId());
		//
		final List<ComparationDTO> comparationsForUserId2 = comparationService
				.getComparationsForUserId(userId2);
		Assert.assertEquals(0, comparationsForUserId2.size());
	}

}
