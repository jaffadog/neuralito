/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import java.util.List;

import edu.unicen.surfforecaster.server.domain.entity.Area;
import edu.unicen.surfforecaster.server.domain.entity.Country;
import edu.unicen.surfforecaster.server.domain.entity.Forecaster;
import edu.unicen.surfforecaster.server.domain.entity.Point;
import edu.unicen.surfforecaster.server.domain.entity.Spot;
import edu.unicen.surfforecaster.server.domain.entity.User;
import edu.unicen.surfforecaster.server.domain.entity.Zone;

/**
 * @author esteban
 * 
 */
public interface SpotDAO {

	public Area getAreaById(int id);

	public List<Area> getAllAreas();

	public Spot updateSpot(Spot surfSpot);

	public Integer saveArea(Area area);

	public Integer saveZone(Zone zone);

	public Integer saveSpot(Spot spot);

	public Integer saveCountry(Country country);

	public Zone getZoneById(Integer zoneId);

	public List<Spot> getSpotsForUser(User user);

	/**
	 * @param countryId
	 * @return
	 */
	public Country getCountryById(Integer countryId);

	/**
	 * @param spotId
	 * @return
	 */
	public Spot getSpotById(Integer spotId);

	/**
	 * @param spotById
	 */
	public void removeSpot(Spot spot);

	/**
	 * @param zone
	 */
	public void removeZone(Zone zone);

	/**
	 * @param country
	 */
	public void removeCountry(Country country);

	/**
	 * @param areaById
	 */
	public void removeArea(Area areaById);

	/**
	 * @param zoneName
	 * @param countryId
	 * @return
	 */
	public Zone getZoneByNameAndCountry(String zoneName, Country country);

	/**
	 * @return
	 */
	public List<Spot> getPublicSpots();

	/**
	 * @param area
	 * @return
	 */
	public List<Country> getAreaCountries(Area area);

	public void addForecasterToSpot(Forecaster f, Spot s);

	/**
	 * @param point
	 */
	public void save(Point point);

	/**
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public Point getPoint(float latitude, float longitude);

	/**
	 * 
	 */
	public List<Country> getAllCountries();

	/**
	 * @param user
	 * @param zone
	 */
	public List<Spot> getSpotForUserAndZone(User user, Zone zone);

	/**
	 * @param zone
	 * @return
	 */
	public List<Spot> getPublicSpots(Zone zone);

	/**
	 * @param country
	 * @return
	 */
	public List<Zone> getPublicZones(Country country);

}
