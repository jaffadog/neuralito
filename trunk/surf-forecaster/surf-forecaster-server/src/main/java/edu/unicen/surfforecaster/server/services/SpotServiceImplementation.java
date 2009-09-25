/**
 * 
 */
package edu.unicen.surfforecaster.server.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ErrorCode;
import edu.unicen.surfforecaster.common.services.SpotService;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.server.dao.SpotDAO;
import edu.unicen.surfforecaster.server.dao.UserDAO;
import edu.unicen.surfforecaster.server.domain.entity.Area;
import edu.unicen.surfforecaster.server.domain.entity.Country;
import edu.unicen.surfforecaster.server.domain.entity.Spot;
import edu.unicen.surfforecaster.server.domain.entity.User;
import edu.unicen.surfforecaster.server.domain.entity.Zone;

/**
 * 
 * @author esteban
 * 
 */
public class SpotServiceImplementation implements SpotService {

	/**
	 * Spot data access.
	 */
	SpotDAO spotDAO;
	/**
	 * User data access.
	 */
	UserDAO userDAO;

	/**
	 * @see edu.unicen.surfforecaster.common.services.SpotService#addSpot(java.lang.String,
	 *      double, double, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Integer addSpot(final String spotName, final double longitude,
			final double latitude, final Integer zoneId, final Integer userId,
			final boolean publik) throws NeuralitoException {
		validate(spotName, longitude, latitude, zoneId, userId, publik);
		final User user = userDAO.getUserByUserId(userId);
		final Zone zone = spotDAO.getZoneById(zoneId);
		final Spot spot = new Spot();
		spot.setLatitude(latitude);
		spot.setLongitude(longitude);
		spot.setName(spotName);
		spot.setPublik(publik);
		spot.setZone(zone);
		spot.setUser(user);
		user.addSpot(spot);
		zone.addSpot(spot);
		try {
			final Integer id = spotDAO.saveSpot(spot);
			return id;
		} catch (final DataAccessException e) {
			throw new NeuralitoException(ErrorCode.DATABASE_ERROR, e);
		}
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.SpotService#getSpotsForUser(java.lang.Integer)
	 */
	@Override
	public Collection<SpotDTO> getSpotsForUser(final Integer userId)
			throws NeuralitoException {
		validate(userId);
		// Obtain all the spots this user is able to see.
		final User user = userDAO.getUserByUserId(userId);
		final Collection<Spot> userSpots = spotDAO.getSpotsForUser(user);
		// Create spots DTOs.
		final Collection<SpotDTO> userSpotsDTOs = new ArrayList<SpotDTO>();
		for (final Iterator<Spot> iterator = userSpots.iterator(); iterator
				.hasNext();) {
			final Spot spot = iterator.next();
			userSpotsDTOs.add(spot.getDTO(spot));
		}
		return userSpotsDTOs;
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.SpotService#removeSpot(java.lang.Integer)
	 */
	@Override
	public void removeSpot(final Integer spotId) throws NeuralitoException {
		validateSpotExists(spotId);
		try {
			spotDAO.removeSpot(spotDAO.getSpotById(spotId));
		} catch (final DataAccessException e) {
			throw new NeuralitoException(ErrorCode.DATABASE_ERROR);
		}
	}

	/**
	 * @param spotId
	 * @throws NeuralitoException
	 */
	private void validateSpotExists(final Integer spotId)
			throws NeuralitoException {
		if (spotId == null)
			throw new NeuralitoException(ErrorCode.SPOT_ID_NULL);
		if (spotId < 0)
			throw new NeuralitoException(ErrorCode.SPOT_ID_INVALID);
		if (spotDAO.getSpotById(spotId) == null)
			throw new NeuralitoException(ErrorCode.SPOT_ID_DOES_NOT_EXISTS);

	}

	/**
	 * @param userId
	 * @throws NeuralitoException
	 */
	private void validate(final Integer userId) throws NeuralitoException {
		if (userId == null)
			throw new NeuralitoException(ErrorCode.USER_ID_NULL);
		if (userId < 0)
			throw new NeuralitoException(ErrorCode.USER_ID_INVALID);
		if (userDAO.getUserByUserId(userId) == null)
			throw new NeuralitoException(ErrorCode.USER_ID_DOES_NOT_EXIST);
	}

	/**
	 * @param publik
	 * 
	 */
	private void validate(final String spotName, final double longitude,
			final double latitude, final Integer zoneId, final Integer userId,
			final boolean publik) throws NeuralitoException {
		// TODO:perform validations
	}

	/**
	 * @param spotDAO
	 *            the spotDAO to set
	 */
	public void setSpotDAO(final SpotDAO spotDAO) {
		this.spotDAO = spotDAO;
	}

	/**
	 * @param userDAO
	 *            the userDAO to set
	 */
	public void setUserDAO(final UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.SpotService#addZone(java.util.Map,
	 *      java.lang.Integer)
	 */
	@Override
	public Integer addZone(final String name, final Integer countryId)
			throws NeuralitoException {
		validate(name, countryId);
		final Country country = spotDAO.getCountryById(countryId);
		final Zone zone = new Zone(name, country);
		try {
			spotDAO.saveZone(zone);
			return zone.getId();
		} catch (final DataAccessException e) {
			throw new NeuralitoException(ErrorCode.DATABASE_ERROR);
		}
	}

	/**
	 * @param zoneName
	 * @param countryId
	 */
	private void validate(final String zoneName, final Integer countryId) {
		// TODO validate that country exists.
		// TODO validate that the zonename doesnt exist in the given country

	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.SpotService#addArea(java.util.Map)
	 */
	@Override
	public Integer addArea(final Map<String, String> areaNamesMap)
			throws NeuralitoException {
		try {
			final Area area = new Area(areaNamesMap);
			final Integer id = spotDAO.saveArea(area);
			return id;
		} catch (final DataAccessException e) {
			throw new NeuralitoException(ErrorCode.DATABASE_ERROR);
		}
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.SpotService#addCountry(java.util.Map,
	 *      java.lang.Integer)
	 */
	@Override
	public Integer addCountry(final Map<String, String> countryNamesMap,
			final Integer areaId) throws NeuralitoException {
		validate(countryNamesMap, areaId);
		final Area area = spotDAO.getAreaById(areaId);
		final Country country = new Country(countryNamesMap, area);
		try {
			final Integer id = spotDAO.saveCountry(country);
			return id;
		} catch (final DataAccessException e) {
			throw new NeuralitoException(ErrorCode.DATABASE_ERROR);
		}
	}

	// @SuppressWarnings("unchecked")
	// private Collection<SpotDTO> getSpotsToDisplay(final Integer userId) {
	// final User user = userDAO.getUserByUserId(userId);
	// final Collection<Spot> publicSpots = spotDAO.getUserVisibleSpots(user);
	//
	// final Collection<SpotDTO> visibleSpots = new ArrayList();
	// for (final Iterator iterator = publicSpots.iterator(); iterator
	// .hasNext();) {
	// final Spot spot = (Spot) iterator.next();
	// visibleSpots.add(spot.getDTO(spot));
	// }
	// return visibleSpots;
	// }

	/**
	 * @param countryNamesMap
	 * @param areaId
	 */
	private void validate(final Map<String, String> countryNamesMap,
			final Integer areaId) throws NeuralitoException {
		// TODO validate area id exists and country names not empty.

	}

	/**
	 * @throws NeuralitoException
	 * @see edu.unicen.surfforecaster.common.services.SpotService#removeZone(java.lang.Integer)
	 */
	@Override
	public void removeZone(final Integer zoneId) throws NeuralitoException {
		validateZoneId(zoneId);
		try {
			spotDAO.removeZone(spotDAO.getZoneById(zoneId));
		} catch (final DataAccessException e) {
			throw new NeuralitoException(ErrorCode.DATABASE_ERROR);
		}
	}

	/**
	 * @param zoneId
	 * @throws NeuralitoException
	 */
	private void validateZoneId(final Integer zoneId) throws NeuralitoException {
		if (zoneId == null)
			throw new NeuralitoException(ErrorCode.ZONE_ID_NULL);
		if (zoneId < 0)
			throw new NeuralitoException(ErrorCode.ZONE_ID_INVALID);
		if (spotDAO.getZoneById(zoneId) == null)
			throw new NeuralitoException(ErrorCode.ZONE_ID_DOES_NOT_EXISTS);

	}

	/**
	 * @throws NeuralitoException
	 * @see edu.unicen.surfforecaster.common.services.SpotService#removeCountry(java.lang.Integer)
	 */
	@Override
	public void removeCountry(final Integer countryId)
			throws NeuralitoException {
		validateCountryId(countryId);
		try {
			spotDAO.removeCountry(spotDAO.getCountryById(countryId));
		} catch (final DataAccessException e) {
			throw new NeuralitoException(ErrorCode.DATABASE_ERROR);
		}

	}

	/**
	 * @param countryId
	 * @throws NeuralitoException
	 */
	private void validateCountryId(final Integer countryId)
			throws NeuralitoException {
		if (countryId == null)
			throw new NeuralitoException(ErrorCode.COUNTRY_ID_NULL);
		if (countryId < 0)
			throw new NeuralitoException(ErrorCode.COUNTRY_ID_INVALID);
		if (spotDAO.getCountryById(countryId) == null)
			throw new NeuralitoException(ErrorCode.COUNTRY_ID_DOES_NOT_EXISTS);

	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.SpotService#removeArea(java.lang.Integer)
	 */
	@Override
	public void removeArea(final Integer areaId) throws NeuralitoException {
		validateAreaId(areaId);
		try {
			spotDAO.removeArea(spotDAO.getAreaById(areaId));
		} catch (final DataAccessException e) {
			throw new NeuralitoException(ErrorCode.DATABASE_ERROR);
		}
	}

	/**
	 * @param areaId
	 * @throws NeuralitoException
	 */
	private void validateAreaId(final Integer areaId) throws NeuralitoException {
		if (areaId == null)
			throw new NeuralitoException(ErrorCode.AREA_ID_NULL);
		if (areaId < 0)
			throw new NeuralitoException(ErrorCode.AREA_ID_INVALID);
		if (spotDAO.getAreaById(areaId) == null)
			throw new NeuralitoException(ErrorCode.AREA_ID_DOES_NOT_EXISTS);
	}

}
