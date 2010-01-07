/**
 * 
 */
package edu.unicen.surfforecaster.server.services;

import java.io.File;
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ForecastService;
import edu.unicen.surfforecaster.common.services.SpotService;
import edu.unicen.surfforecaster.common.services.UserService;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.UserType;
import edu.unicen.surfforecaster.common.services.dto.VisualObservationDTO;
import edu.unicen.surfforecaster.common.services.dto.WekaForecasterEvaluationDTO;
import edu.unicen.surfforecaster.server.dao.ForecastDAOHibernateImpl;
import edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchParameter;
import edu.unicen.surfforecaster.util.VisualObservationsLoader;

/**
 * @author esteban
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/services.xml" })
public class ForecastServiceImplementationTest {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private ForecastService forecastService;
	@Autowired
	private ForecastDAOHibernateImpl forecastDAO;
	@Autowired
	protected SpotService spotService;
	@Autowired
	protected UserService userService;

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
			TimeZone timeZone = TimeZone.getTimeZone("UTC");
			spot1Id = spotService.addSpot("Guanchhyhaco", 75.0F, 0.5F, zoneId1,
					userId1, true, timeZone);
			spot2Id = spotService.addSpot("Guanchaco", 2.0F, 1.0F, zoneId2,
					userId1, false, timeZone);
			spot3Id = spotService.addSpot("Guanchaco", 2.0F, 1.0F, zoneId3,
					userId2, false, timeZone);
			spot4Id = spotService.addSpot("Guanchaco", 2.0F, 1.0F, zoneId4,
					userId2, true, timeZone);
		} catch (final NeuralitoException e) {
			log.error(e);
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

	@Test
	public void createWW3Forecaster() {
		try {
			final int forecasterId = forecastService.createWW3Forecaster(
					spot1Id, new PointDTO(22.0F, -158.75F));
			Assert.assertTrue(forecasterId > 0);
		} catch (final NeuralitoException e) {
			e.printStackTrace();
		}
	}

	@Test

	public void getForecaster() {
		try {
			long initial = System.currentTimeMillis();
			final int forecasterId = forecastService.createWW3Forecaster(
					spot1Id, new PointDTO(22.0F, -158.75F));
			final List<ForecastDTO> forecasts = forecastService
					.getLatestForecasts(forecasterId);
			log.info("Number of forecasts retrieved:"+forecasts.size());
			log.info(forecasts.get(0).getBaseDate());
			long end = System.currentTimeMillis();
			log.info("Elapsed time:"+(end-initial)/1000);
			Assert.assertTrue(forecasts.size() > 0);
		} catch (final NeuralitoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void createWekaForecaster() {
		try {
			long initial = System.currentTimeMillis();
			List<VisualObservationDTO> visualObservations = VisualObservationsLoader
					.loadVisualObservations(
							new File(
									"C:\\Users\\esteban\\workspace\\arfgen\\files\\observations\\oahu1997.dat"),
							Unit.Meters);
			HashMap<String, Serializable> options = new HashMap<String, Serializable>();
			options.put("latitudeGridPoint1", 22.0F);
			options.put("longitudeGridPoint1", -158.75F);
			options.put("utcSunriseHour", 17);
			options.put("utcSunriseMinute", 30);
			options.put("utcSunsetHour", 6);
			options.put("utcSunsetMinute", 30);
			WekaForecasterEvaluationDTO createWekaForecaster = forecastService
					.createWekaForecaster(visualObservations, spot2Id,
					options);

			Integer forecasterId = createWekaForecaster.getId();
			List<ForecastDTO> latestForecasts = forecastService
					.getLatestForecasts(forecasterId);
			for (ForecastDTO forecastDTO : latestForecasts) {
				log
						.info("Wave Watch Prediction:"
								+ forecastDTO
										.getMap()
										.get(
						WaveWatchParameter.COMBINED_SWELL_WIND_WAVE_HEIGHT_V2
														.getValue()).getValue()
								+ "|| Weka prediction"
								+ forecastDTO.getMap()
										.get(
"improvedWaveHeight")
								.getValue());
			}
		} catch (final Exception e) {
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
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
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
