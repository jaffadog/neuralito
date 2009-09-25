/**
 * 
 */
package edu.unicen.surfforecaster.common.services;

import java.util.Collection;
import java.util.Map;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;

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
			Integer zoneId, Integer userId, boolean publik)
			throws NeuralitoException;

	/**
	 * Obtain all the spots for the given user.
	 * 
	 * @param userId
	 * @return
	 */
	public Collection<SpotDTO> getSpotsForUser(Integer userId)
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

}
