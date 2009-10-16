/**
 * 
 */
package edu.unicen.surfforecaster.common.services;

import java.util.List;
import java.util.Map;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.AreaDTO;
import edu.unicen.surfforecaster.common.services.dto.CountryDTO;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.common.services.dto.ZoneDTO;

/**
 * Service interface for spots related operations.
 * 
 * @author esteban
 * 
 */
public interface SpotService {
	/**
	 * Add a new spot, in the given zone, for the given user.
	 * 
	 * @param spotName
	 * @param longitude
	 * @param latitude
	 * @param zoneId
	 * @param userId
	 * @return
	 */
	public Integer addSpot(String spotName, double longitude, double latitude,
			Integer zoneId, Integer userId, boolean publik, String timeZone)
			throws NeuralitoException;

	/**
	 * Creates a zone, and adds a new spot to it. If a zone with the same name
	 * exists then zone is not created and spot is added to this zone.
	 * 
	 * @param zoneName
	 *            the zone name.
	 * @param countryId
	 *            the country to which the zone belongs.
	 * @param spotName
	 *            the spot name
	 * @param longitude
	 * @param latitude
	 * @param userId
	 *            the creator of the spot
	 * @param publik
	 *            whether this spots is public or private.
	 * @return
	 */
	public Integer addZoneAndSpot(String zoneName, Integer countryId,
			String spotName, double longitude, double latitude, Integer userId,
			boolean publik, String timeZone) throws NeuralitoException;

	/**
	 * Obtain all the spots for the given user. That is all the spots the user
	 * is able to see within the application.
	 * 
	 * @param userId
	 * @return
	 */
	public List<SpotDTO> getSpotsForUser(Integer userId)
			throws NeuralitoException;

	/**
	 * 
	 * @param spotId
	 * @throws NeuralitoException
	 */
	public void removeSpot(Integer spotId) throws NeuralitoException;

	/**
	 * @param name
	 * @param countryId
	 * @throws NeuralitoException
	 */
	public Integer addZone(String name, Integer countryId)
			throws NeuralitoException;

	/**
	 * @param areaNamesMap
	 * @return
	 */
	public Integer addArea(Map<String, String> areaNamesMap)
			throws NeuralitoException;

	/**
	 * @param countryNamesMap
	 * @param areaId
	 * @return
	 */
	public Integer addCountry(Map<String, String> countryNamesMap,
			Integer areaId) throws NeuralitoException;

	/**
	 * @param zoneId1
	 * @throws NeuralitoException
	 */
	public void removeZone(Integer zoneId) throws NeuralitoException;

	/**
	 * @param countryId
	 * @throws NeuralitoException
	 */
	public void removeCountry(Integer countryId) throws NeuralitoException;

	/**
	 * @param areaId
	 */
	public void removeArea(Integer areaId) throws NeuralitoException;

	/**
	 * @param spotId
	 */
	public SpotDTO getSpotById(Integer spotId) throws NeuralitoException;

	/**
	 * @return
	 * @throws NeuralitoException
	 */
	public List<SpotDTO> getPublicSpots() throws NeuralitoException;

	/**
	 * @param spot1Id
	 */
	public List<ForecasterDTO> getSpotForecasters(Integer spot1Id)
			throws NeuralitoException;

	/**
	 * @param spot1Id
	 */
	public List<AreaDTO> getAreas() throws NeuralitoException;

	public List<CountryDTO> getCountries(Integer idArea)
			throws NeuralitoException;

	public List<ZoneDTO> getZones(Integer idCountry, Integer idUser)
			throws NeuralitoException;

}
