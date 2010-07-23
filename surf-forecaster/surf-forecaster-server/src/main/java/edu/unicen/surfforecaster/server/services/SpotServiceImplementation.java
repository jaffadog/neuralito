/**
 * 
 */
package edu.unicen.surfforecaster.server.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ForecasterDTO;
import edu.unicen.surfforecaster.common.services.SpotService;
import edu.unicen.surfforecaster.common.services.dto.AreaDTO;
import edu.unicen.surfforecaster.common.services.dto.CountryDTO;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.common.services.dto.VisualObservationDTO;
import edu.unicen.surfforecaster.common.services.dto.ZoneDTO;
import edu.unicen.surfforecaster.server.dao.SpotDAO;
import edu.unicen.surfforecaster.server.dao.UserDAO;
import edu.unicen.surfforecaster.server.domain.entity.Area;
import edu.unicen.surfforecaster.server.domain.entity.Country;
import edu.unicen.surfforecaster.server.domain.entity.Forecaster;
import edu.unicen.surfforecaster.server.domain.entity.Point;
import edu.unicen.surfforecaster.server.domain.entity.Spot;
import edu.unicen.surfforecaster.server.domain.entity.User;
import edu.unicen.surfforecaster.server.domain.entity.VisualObservation;
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
	@Transactional
	public Integer addSpot(final String spotName, final float latitude,
			final float longitude, final Integer zoneId, final Integer userId,
			final boolean publik, final TimeZone timeZone)
			throws NeuralitoException {
		validate(spotName, longitude, latitude, zoneId, userId, publik,
				timeZone);
		final User user = userDAO.getUserByUserId(userId);
		final Zone zone = spotDAO.getZoneById(zoneId);
		final Spot spot = new Spot();
		Point point = spotDAO.getPoint(new Float(latitude),
				new Float(longitude));
		if (point == null) {
			point = new Point(latitude, longitude);
			spotDAO.save(point);
		}
		spot.setLocation(point);
		spot.setName(spotName);
		spot.setPublik(publik);
		spot.setZone(zone);
		spot.setUser(user);
		spot.setTimeZone(timeZone);

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
	@Transactional(readOnly = true)
	public List<SpotDTO> getSpotsForUser(final Integer userId)
			throws NeuralitoException {
		validateUserId(userId);
		// Obtain all the spots this user is able to see.
		final User user = userDAO.getUserByUserId(userId);
		final Collection<Spot> userSpots = spotDAO.getSpotsForUser(user);
		// Create spots DTOs.
		final List<SpotDTO> userSpotsDTOs = new ArrayList<SpotDTO>();
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
	@Transactional
	public void removeSpot(final Integer spotId) throws NeuralitoException {
		validateSpotExists(spotId);
		try {
			spotDAO.removeSpot(spotDAO.getSpotById(spotId));
		} catch (final DataAccessException e) {
			e.printStackTrace();
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
	private void validateUserId(final Integer userId) throws NeuralitoException {
		if (userId == null)
			throw new NeuralitoException(ErrorCode.USER_ID_NULL);
		if (userId < 0)
			throw new NeuralitoException(ErrorCode.USER_ID_INVALID);
		if (userDAO.getUserByUserId(userId) == null)
			throw new NeuralitoException(ErrorCode.USER_ID_DOES_NOT_EXIST);
	}

	/**
	 * @param publik
	 * @param timeZone
	 * 
	 */
	private void validate(final String spotName, final double longitude,
			final double latitude, final Integer zoneId, final Integer userId,
			final boolean publik, final TimeZone timeZone)
			throws NeuralitoException {
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
	@Transactional
	public Integer addZone(final String name, final Integer countryId)
			throws NeuralitoException {
		validateCountryId(countryId);
		validateZoneName(name);
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
	 * @param name
	 */
	private void validateZoneName(final String name) {
		// Validate zone name

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
	@Transactional
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
	@Transactional
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
	@Transactional
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
	@Transactional
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
	@Transactional
	public void removeArea(final Integer areaId) throws NeuralitoException {
		validateAreaId(areaId);
		spotDAO.removeArea(spotDAO.getAreaById(areaId));
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

	/**
	 * @see edu.unicen.surfforecaster.common.services.SpotService#addZoneAndSpot(java.lang.String,
	 *      java.lang.Integer, java.lang.String, double, double,
	 *      java.lang.Integer, boolean)
	 */
	@Override
	@Transactional
	public Integer addZoneAndSpot(final String zoneName,
			final Integer countryId, final String spotName,
			final float latitude, final float longitude, final Integer userId,
			final boolean publik, final TimeZone timeZone)
			throws NeuralitoException {
		validateCountryId(countryId);
		validateUserId(userId);
		validateSpotName(spotName);
		validateLatAndLong(latitude, longitude);
		validateZoneName(zoneName);
		// Check if zone for the given country exists.
		final Country country = spotDAO.getCountryById(countryId);
		final Zone zone = spotDAO.getZoneByNameAndCountry(zoneName, country);
		Integer zoneId;
		if (zone == null) {
			// Zone does not exist so we create one.
			zoneId = addZone(zoneName, countryId);
		} else {
			// Zone exist so we add the spot to the zone.
			zoneId = zone.getId();
		}
		return addSpot(spotName, latitude, longitude, zoneId, userId, publik,
				timeZone);
	}

	/**
	 * @param spotName
	 */
	private void validateSpotName(final String spotName) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param latitude
	 * @param longitude
	 */
	private void validateLatAndLong(final Float latitude, final Float longitude) {
		if (latitude == null) {

		}

	}

	/**
	 * @param spotName
	 * @param longitude
	 * @param latitude
	 */
	private void validateSpotName(final String spotName,
			final double longitude, final double latitude) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.SpotService#getSpotById(java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = true)
	public SpotDTO getSpotById(final Integer spotId) throws NeuralitoException {
		validateSpotId(spotId);
		final Spot spot = spotDAO.getSpotById(spotId);
		return spot.getDTO(spot);
	}

	/**
	 * @param spotId
	 */
	private void validateSpotId(final Integer spotId) throws NeuralitoException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.SpotService#getPublicSpots()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<SpotDTO> getPublicSpots() throws NeuralitoException {
		try {
			final List<Spot> userSpots = spotDAO.getPublicSpots();
			// Create spots DTOs.
			final List<SpotDTO> userSpotsDTOs = new ArrayList<SpotDTO>();
			for (final Iterator<Spot> iterator = userSpots.iterator(); iterator
					.hasNext();) {
				final Spot spot = iterator.next();
				userSpotsDTOs.add(spot.getDTO(spot));
			}
			return userSpotsDTOs;
		} catch (final DataAccessException e) {
			throw new NeuralitoException(ErrorCode.DATABASE_ERROR);
		}
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.SpotService#getSpotForecasters(java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ForecasterDTO> getSpotForecasters(final Integer spotId)
			throws NeuralitoException {
		validateSpotExists(spotId);
		final Spot spot = spotDAO.getSpotById(spotId);
		final Collection<Forecaster> forecasters = spot.getForecasters();
		final List<ForecasterDTO> forecastersDTOs = new ArrayList<ForecasterDTO>();
		for (final Iterator iterator = forecasters.iterator(); iterator
				.hasNext();) {
			final Forecaster forecaster = (Forecaster) iterator.next();
			forecastersDTOs.add(forecaster.getDTO());
		}
		return forecastersDTOs;
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.SpotService#getAreas()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<AreaDTO> getAreas() throws NeuralitoException {
		final List<Area> allAreas = spotDAO.getAllAreas();
		final List<AreaDTO> areasDTOs = new ArrayList<AreaDTO>();
		for (final Iterator iterator = allAreas.iterator(); iterator.hasNext();) {
			final Area area = (Area) iterator.next();
			areasDTOs.add(area.getDTO());
		}
		return areasDTOs;
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.SpotService#getCountries(java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CountryDTO> getCountries(final Integer areaId)
			throws NeuralitoException {
		validateAreaId(areaId);
		final Area area = spotDAO.getAreaById(areaId);
		final Collection<Country> countries = spotDAO.getAreaCountries(area);
		final List<CountryDTO> countriesDTOs = new ArrayList<CountryDTO>();
		for (final Iterator iterator = countries.iterator(); iterator.hasNext();) {
			final Country country = (Country) iterator.next();
			countriesDTOs.add(country.getDTO());
		}
		return countriesDTOs;
	}

	/**
	 * Obtain All zones of the country and the specified user.
	 * 
	 * @see edu.unicen.surfforecaster.common.services.SpotService#getZones(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ZoneDTO> getZones(final Integer idCountry, final Integer idUser)
			throws NeuralitoException {
		final User user = userDAO.getUserByUserId(idUser);
		final Collection<Spot> spotsForUser = spotDAO.getSpotsForUser(user);
		final Set<ZoneDTO> zonesDtos = new HashSet<ZoneDTO>();
		for (final Iterator iterator = spotsForUser.iterator(); iterator
				.hasNext();) {
			final Spot spot = (Spot) iterator.next();
			if (spot.getZone().getCountry().getId().equals(idCountry)) {

				if (!contains(zonesDtos, spot.getZone().getId())) {
					zonesDtos.add(spot.getZone().getDTO());
				}

			}
		}
		return new ArrayList<ZoneDTO>(zonesDtos);
	}

	/**
	 * Check if the given zone is not in the zonesDto collection.
	 * 
	 * @param zonesDtos
	 * @param id
	 * @return
	 */
	private boolean contains(final Set<ZoneDTO> zonesDtos, final Integer id) {
		for (final ZoneDTO zoneDTO : zonesDtos) {
			if (zoneDTO.getId() == id)
				return true;
		}
		return false;
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.SpotService#getCountries()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CountryDTO> getCountries() throws NeuralitoException {
		try {
			final List<Country> allCountries = spotDAO.getAllCountries();
			final List<CountryDTO> dtos = new ArrayList<CountryDTO>();
			for (final Iterator iterator = allCountries.iterator(); iterator
					.hasNext();) {
				final Country country = (Country) iterator.next();
				dtos.add(country.getDTO());
			}
			return dtos;
		} catch (final DataAccessException e) {
			throw new NeuralitoException(ErrorCode.DATABASE_ERROR);
		}
	}

	/**
	 * @throws NeuralitoException
	 * @see edu.unicen.surfforecaster.common.services.SpotService#getSpots(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<SpotDTO> getSpots(final Integer idZone, final Integer idUser)
			throws NeuralitoException {
		validateZoneId(idZone);
		validateUserId(idUser);
		try {
			final User user = userDAO.getUserByUserId(idUser);
			final Zone zone = spotDAO.getZoneById(idZone);
			final List<Spot> userSpots = spotDAO.getSpotForUserAndZone(user,
					zone);
			// Create spots DTOs.
			final List<SpotDTO> userSpotsDTOs = new ArrayList<SpotDTO>();
			for (final Iterator<Spot> iterator = userSpots.iterator(); iterator
					.hasNext();) {
				final Spot spot = iterator.next();
				userSpotsDTOs.add(spot.getDTO(spot));
			}
			return userSpotsDTOs;
		} catch (final DataAccessException e) {
			throw new NeuralitoException(ErrorCode.DATABASE_ERROR);

		}
	}

	/**
	 * Retrieve public spots of zone.
	 * 
	 * @throws NeuralitoException
	 * 
	 * @see edu.unicen.surfforecaster.common.services.SpotService#getSpots(java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<SpotDTO> getSpots(final Integer zoneId)
			throws NeuralitoException {
		validateZoneId(zoneId);
		try {
			final Zone zone = spotDAO.getZoneById(zoneId);
			final List<Spot> spots = spotDAO.getPublicSpots(zone);
			// Create spots DTOs.
			final List<SpotDTO> spotsDTOs = new ArrayList<SpotDTO>();
			for (final Iterator<Spot> iterator = spots.iterator(); iterator
					.hasNext();) {
				final Spot spot = iterator.next();
				spotsDTOs.add(spot.getDTO(spot));
			}
			return spotsDTOs;
		} catch (final DataAccessException e) {
			throw new NeuralitoException(ErrorCode.DATABASE_ERROR);

		}
	}

	/**
	 * Retrieve public zones of country.
	 * 
	 * @see edu.unicen.surfforecaster.common.services.SpotService#getZones(java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ZoneDTO> getZones(final Integer countryId)
			throws NeuralitoException {
		validateCountryId(countryId);
		try {
			final Country country = spotDAO.getCountryById(countryId);
			final List<Zone> spots = spotDAO.getPublicZones(country);
			final Set<ZoneDTO> zonesDtos = new HashSet<ZoneDTO>();
			for (final Iterator<Zone> iterator = spots.iterator(); iterator
					.hasNext();) {
				final Zone zone = iterator.next();
				zonesDtos.add(zone.getDTO());
			}
			return new ArrayList<ZoneDTO>(zonesDtos);
		} catch (final DataAccessException e) {
			throw new NeuralitoException(ErrorCode.DATABASE_ERROR);
		}
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.SpotService#getSpotsCreatedByUser(java.lang.Integer)
	 */
	@Override
	@Transactional
	public List<SpotDTO> getSpotsCreatedByUser(final Integer userId)
			throws NeuralitoException {
		validateUserId(userId);
		final User user = userDAO.getUserByUserId(userId);
		final Set<Spot> spots = user.getSpots();
		final List<SpotDTO> dtos = new ArrayList<SpotDTO>();
		for (final Spot spot : spots) {
			dtos.add(spot.getDTO(spot));
		}
		return dtos;
	}

	/**
	 * @throws NeuralitoException
	 * @see edu.unicen.surfforecaster.common.services.SpotService#updateSpot(java.lang.Integer,
	 *      java.lang.String, float, float, java.lang.Integer,
	 *      java.lang.Integer, boolean, java.util.TimeZone)
	 */
	@Override
	@Transactional
	public SpotDTO updateSpot(final Integer spotId, final String spotName,
			final float latitude, final float longitude, final Integer zoneId,
			final boolean publik, final TimeZone timeZone)
			throws NeuralitoException {
		validateSpotExists(spotId);
		final Spot spot = spotDAO.getSpotById(spotId);
		validate(spotName, longitude, latitude, zoneId, spot.getUser().getId(),
				publik, timeZone);
		Point point = spotDAO.getPoint(new Float(latitude),
				new Float(longitude));
		if (point == null) {
			point = new Point(latitude, longitude);
			spotDAO.save(point);
		}
		spot.setLocation(point);
		spot.setName(spotName);
		spot.setPublik(publik);
		spot.setTimeZone(timeZone);
		spot.setZone(spotDAO.getZoneById(zoneId));
		return spot.getDTO(spot);
	}

	/**
	 * @throws NeuralitoException
	 * @see edu.unicen.surfforecaster.common.services.SpotService#addVisualObservations(java.lang.Integer,
	 *      java.util.List)
	 */
	@Override
	@Transactional
	public void addVisualObservations(final Integer spotId,
			final List<VisualObservationDTO> observationsDTO)
			throws NeuralitoException {
		validateSpotId(spotId);
		final Spot spot = spotDAO.getSpotById(spotId);
		final List<VisualObservation> observations = new ArrayList<VisualObservation>();
		// translate each observationDTO into observation.
		for (final VisualObservationDTO visualObservationDTO : observationsDTO) {
			final VisualObservation vo = new VisualObservation(
					visualObservationDTO.getWaveHeight(), visualObservationDTO
							.getObservationDate(), visualObservationDTO
							.getUnit());
			observations.add(vo);
		}
		spot.addVisualObservations(observations);
		spotDAO.saveSpot(spot);
	}

	/**
	 * @throws NeuralitoException
	 * @see edu.unicen.surfforecaster.common.services.SpotService#getVisualObservations(java.lang.Integer)
	 */
	@Override
	@Transactional
	public List<VisualObservationDTO> getVisualObservations(final Integer spotId)
			throws NeuralitoException {
		validateSpotId(spotId);
		final Spot spot = spotDAO.getSpotById(spotId);
		final List<VisualObservationDTO> visualObservationsDTO = new ArrayList<VisualObservationDTO>();
		final List<VisualObservation> visualObservations = spot
				.getVisualObservations();
		for (final VisualObservation visualObservation : visualObservations) {
			visualObservationsDTO.add(visualObservation.getDTO());
		}
		return visualObservationsDTO;
	}

	/**
	 * @throws NeuralitoException
	 * @see edu.unicen.surfforecaster.common.services.SpotService#removeVisualObservations(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void removeVisualObservations(final Integer spotId)
			throws NeuralitoException {
		validateSpotId(spotId);
		final Spot spot = spotDAO.getSpotById(spotId);
		spot.removeVisualObservations();
		spotDAO.saveSpot(spot);
	}
}
