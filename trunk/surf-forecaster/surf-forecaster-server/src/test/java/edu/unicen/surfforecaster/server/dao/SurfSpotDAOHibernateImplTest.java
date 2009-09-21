package edu.unicen.surfforecaster.server.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.unicen.surfforecaster.server.domain.entity.Area;
import edu.unicen.surfforecaster.server.domain.entity.Country;
import edu.unicen.surfforecaster.server.domain.entity.Spot;
import edu.unicen.surfforecaster.server.domain.entity.Zone;

public class SurfSpotDAOHibernateImplTest {
	private SpotDAO spotDAO;

	@Before
	public void setUp() throws Exception {
		final ApplicationContext context = new ClassPathXmlApplicationContext(
				"/spring-hibernate-jpa-config.xml");
		spotDAO = (SpotDAO) context.getBean("spotDAO");
	}

	// /**
	// * Simple tests excersing the various methods of SpotDAO
	// */
	// public void test()
	// {
	// Spot surfSpot = new Spot();
	// double latitude=2L;
	// surfSpot.setLatitude(latitude);
	// double longitude=1L;
	// surfSpot.setLongitude(longitude);
	// String name="biologia";
	// surfSpot.setName(name);
	// spotDAO.updateSpot(surfSpot);
	// assertNotNull(surfSpot.getId());
	// //Test "loadAll"
	// //Collection<Spot> loadedArtists = spotDAO.getAllSpots();
	// //assertEquals(1, loadedArtists.size());
	// }
	/**
	 * Creates an Area, with countries, with zones, with a spot persist and
	 * reload them.
	 */
	@Test
	public void saveAndLoad() {
		// number of areas in the DB before adding a new entity
		final int initialAreas = spotDAO.getAllAreas().size();
		final String areaName = "Europe";
		final String countryName = "France";
		final String zoneName = "westCoast";
		final String spotName = "biologia";
		final double latitude = 2L;
		final double longitude = 1L;

		Area area = new Area();
		Country country = new Country();

		Zone zone = new Zone(zoneName);
		Spot spot = new Spot();
		spot.setLatitude(latitude);
		spot.setLongitude(longitude);
		spot.setName(spotName);

		area.addCountry(country);
		country.addZone(zone);
		zone.addSpot(spot);

		area = spotDAO.saveArea(area);

		final List<Area> loadedAreas = spotDAO.getAllAreas();
		Assert.assertEquals(initialAreas + 1, loadedAreas.size());

		area = spotDAO.getAreaById(area.getId());

		Assert.assertEquals("Area should have 1 country and it has:"
				+ area.getCountries().size(), 1, area.getCountries().size());
		country = (Country) area.getCountries().toArray()[0];
		Assert.assertEquals("argentina", country.getName("es"));

		Assert.assertEquals("Country should have 1 zone and it has:"
				+ country.getZones().size(), 1, country.getZones().size());
		zone = (Zone) country.getZones().toArray()[0];
		Assert.assertEquals(zoneName, zone.getName());

		Assert.assertEquals("Zone should have 1 spot and it has:"
				+ zone.getSpots().size(), 1, zone.getSpots().size());
		spot = (Spot) zone.getSpots().toArray()[0];
		Assert.assertEquals(spotName, spot.getName());

	}

}