package edu.unicen.surfforecaster.gwt.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Level;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ForecastService;
import edu.unicen.surfforecaster.common.services.SpotService;
import edu.unicen.surfforecaster.common.services.dto.AreaDTO;
import edu.unicen.surfforecaster.common.services.dto.CountryDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.common.services.dto.SimpleForecasterDTO;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.common.services.dto.ZoneDTO;
import edu.unicen.surfforecaster.gwt.client.SpotServices;
import edu.unicen.surfforecaster.gwt.client.dto.AreaGwtDTO;
import edu.unicen.surfforecaster.gwt.client.dto.CountryGwtDTO;
import edu.unicen.surfforecaster.gwt.client.dto.SpotGwtDTO;

@SuppressWarnings("serial")
public class SpotServicesImpl extends ServicesImpl implements SpotServices {
	
	private SpotService spotService;
	private ForecastService forecastService;

	/**
	 * @param service the service to set
	 */
	public void setSpotService(final SpotService service) {
		spotService = service;
	}

	/**
	 * @return the spot service
	 */
	public SpotService getSpotService() {
		return spotService;
	}
	
	/**
	 * @param service the service to set
	 */
	public void setForecastService(final ForecastService service) {
		forecastService = service;
	}

	/**
	 * @return the forecast service
	 */
	public ForecastService getForecastService() {
		return forecastService;
	}
	
	public List<AreaGwtDTO> getAreas(String localeCode) throws NeuralitoException {
		List<AreaGwtDTO> result = new ArrayList<AreaGwtDTO>();
		List<AreaDTO> areaDTOs = spotService.getAreas();
		Iterator<AreaDTO> i = areaDTOs.iterator();
		while (i.hasNext()) {
			AreaDTO areaDTO = i.next();
			result.add(this.getAreaGwtDTO(areaDTO, localeCode));
		}
		Collections.sort(result);
		return result;
	}
	
	private AreaGwtDTO getAreaGwtDTO(AreaDTO areaDTO, String localeCode) {
		return new AreaGwtDTO(areaDTO.getId(), areaDTO.getNames().get(localeCode));
	}
	
	public List<CountryGwtDTO> getCountries(String localeCode) throws NeuralitoException {
		List<CountryGwtDTO> result = new ArrayList<CountryGwtDTO>();
		List<CountryDTO> countryDTOs = spotService.getCountries();
		Iterator<CountryDTO> i = countryDTOs.iterator();
		while (i.hasNext()) {
			CountryDTO countryDTO = i.next();
			result.add(this.getCountryGwtDTO(countryDTO, localeCode));
		}
		Collections.sort(result);
		return result;
	}
	
	private CountryGwtDTO getCountryGwtDTO(CountryDTO countryDTO, String localeCode) {
		return new CountryGwtDTO(countryDTO.getId(), countryDTO.getNames().get(localeCode), this.getAreaGwtDTO(countryDTO.getAreaDTO(), localeCode));
	}
	
	public List<ZoneDTO> getZones(final Integer country) throws NeuralitoException {
		List<ZoneDTO> zones = new ArrayList<ZoneDTO>();
		if (this.getLoggedUser() != null)
			zones = spotService.getZones(country, this.getLoggedUser().getId());
		else
			zones = spotService.getZones(country);
		Collections.sort(zones);
		return zones;
	}
	
	public List<SpotGwtDTO> getSpots(Integer zone) throws NeuralitoException {
		List<SpotDTO> spots = new ArrayList<SpotDTO>();
		List<SpotGwtDTO> spotsGwt = new ArrayList<SpotGwtDTO>();
		if (this.getLoggedUser() != null) 
			spots = spotService.getSpots(zone, this.getLoggedUser().getId());
		else
			spots = spotService.getSpots(zone);
		Collections.sort(spots);
		Iterator<SpotDTO> i = spots.iterator();
		while (i.hasNext()) {
			SpotDTO spot = i.next();
			spotsGwt.add(this.getSpotGwtDTO(spot));
		}
		return spotsGwt;
	}
	
	/**
	 * 
	 */
	public Integer addSpot(final String spotName, final String spotLatitude, final String spotLongitude,
			final String buoyLatitude, final String buoyLongitude, final Integer zoneId,
			final Integer countryId, final String zoneName,
			final boolean public_, final String timezone) throws NeuralitoException {
		
		logger.log(Level.INFO,"SpotServicesImpl - addSpot - Trying to add a new spot: '" + spotName + "'...");
		
		if (super.hasAccessTo("addSpot")){
			final float spotLongitudeNum = new Float(spotLongitude.replace(",", "."));
			final float spotLatitudeNum = new Float(spotLatitude.replace(",", "."));
			final float buoyLongitudeNum = new Float(buoyLongitude.replace(",", "."));
			final float buoyLatitudeNum = new Float(buoyLatitude.replace(",", "."));
			final Integer userId = super.getLoggedUser().getId();
			Integer result = null;
			TimeZone tz = TimeZone.getTimeZone(timezone);
			if (zoneName.trim().equals("")) {
				logger.log(Level.INFO,"SpotServicesImpl - addSpot - Adding only the spot: '" + spotName + "'...");
				result = spotService.addSpot(spotName, spotLatitudeNum, spotLongitudeNum, zoneId, userId, public_, tz);
			} else {
				logger.log(Level.INFO,"SpotServicesImpl - addSpot - Adding both zone: '"  + zoneName.trim() + "' and spot: '" + spotName + "'...");	
				result = spotService.addZoneAndSpot(zoneName.trim(), countryId, spotName, spotLatitudeNum, spotLongitudeNum, userId, public_, tz);
			}
			
			if (result != null) {
				forecastService.createWW3Forecaster(result, new PointDTO(buoyLatitudeNum, buoyLongitudeNum));
				logger.log(Level.INFO,"SpotServicesImpl - addSpot - New spot '" + spotName + "' added");
			}
			return result;
		}
		logger.log(Level.INFO,"SpotServicesImpl - addSpot - Permissions denied to the current user to perform this action.");
		return null;
	}

	/**
	 * Retrieve the spots created by the logged in user, without the public spots
	 */
	public List<SpotGwtDTO> getSpotsCreatedBy() throws NeuralitoException {
		logger.log(Level.INFO,"SpotServicesImpl - getSpotsCreatedBy - Trying to retrieve the spots created by the current logged in user...");
		if (super.hasAccessTo("getSpotCreatedByUser")){
			List<SpotDTO> spots = new ArrayList<SpotDTO>();
			List<SpotGwtDTO> spotsGwt = new ArrayList<SpotGwtDTO>();
			final Integer userId = super.getLoggedUser().getId();
			spots = spotService.getSpotsCreatedByUser(userId);
			Collections.sort(spots);
			Iterator<SpotDTO> i = spots.iterator();
			while (i.hasNext()) {
				SpotDTO spot = i.next();
				SpotGwtDTO spotGwtDTO = super.getSpotGwtDTO(spot);
				//Retrieve gridpoint of the spot (right now we are using the first simple forecaster(ww3) from the list because we know that we have just one for spot)
				//If this changes we need a litle more complex logic for this
				SimpleForecasterDTO simpleForecasterDTO = forecastService.getSimpleForecastersForSpot(spot.getId()).get(0);
				spotGwtDTO.setGridPoint(simpleForecasterDTO.getGridPoint());
				spotsGwt.add(spotGwtDTO);
			}
			logger.log(Level.INFO,"SpotServicesImpl - getSpotsCreatedBy - Retrieved " + spotsGwt.size() + " for the current logged in user.");
			return spotsGwt;
		}
		logger.log(Level.INFO,"SpotServicesImpl - getSpotsCreatedBy - Permissions denied to the current user to perform this action.");
		return null;
	}

	public boolean deleteSpot(Integer spotId) throws NeuralitoException {
		logger.log(Level.INFO,"SpotServicesImpl - deleteSpot - Trying to delete spot: " + spotId + "...");
		if (super.hasAccessTo("deleteSpot")){
			final Integer userId = super.getLoggedUser().getId();
			if (spotService.getSpotById(spotId).getUserId().intValue() == userId.intValue()) {
				spotService.removeSpot(spotId);
				logger.log(Level.INFO,"SpotServicesImpl - deleteSpot - Spot removed successfully.");
				return true;
			} else {
				logger.log(Level.INFO,"SpotServicesImpl - deleteSpot - The current user is not the owner of this spot.");
				throw new NeuralitoException(ErrorCode.USER_NOT_SPOT_OWNER);
			}
		}
		logger.log(Level.INFO,"SpotServicesImpl - deleteSpot - Permissions denied to the current user to perform this action.");
		return false;
	}

	@Override
	public Integer editSpot(Integer spotId, String spotName,
			String spotLatitude, String spotLongitude, String buoyLatitude,
			String buoyLongitude, Integer zoneId, Integer countryId,
			String zoneName, boolean public1, String timezone, boolean changedGridPoint)
			throws NeuralitoException {
		
		logger.log(Level.INFO,"SpotServicesImpl - editSpot - Trying to edit spot: '" + spotId + "'...");
		if (super.hasAccessTo("editSpot")){
			final Integer userId = super.getLoggedUser().getId();
			if (spotService.getSpotById(spotId).getUserId().intValue() == userId.intValue()) {
				final float spotLongitudeNum = new Float(spotLongitude.replace(",", "."));
				final float spotLatitudeNum = new Float(spotLatitude.replace(",", "."));
				final float buoyLongitudeNum = new Float(buoyLongitude.replace(",", "."));
				final float buoyLatitudeNum = new Float(buoyLatitude.replace(",", "."));
				Integer result = null;
				TimeZone tz = TimeZone.getTimeZone(timezone);
				if (zoneName.trim().equals("")) {
					logger.log(Level.INFO,"SpotServicesImpl - editSpot - editing only the spot: '" + spotId + "'...");
					result = spotService.updateSpot(spotId, spotName, spotLatitudeNum, spotLongitudeNum, zoneId, public1, tz).getId();
				} else {
					logger.log(Level.INFO,"SpotServicesImpl - editSpot - Adding zone: '"  + zoneName.trim() + "'...");	
					zoneId = spotService.addZone(zoneName.trim(), countryId);
					logger.log(Level.INFO,"SpotServicesImpl - editSpot - Zone: '"  + zoneName.trim() + "' added.");
					logger.log(Level.INFO,"SpotServicesImpl - editSpot - editing only the spot: '" + spotId + "'...");
					result = spotService.updateSpot(spotId, spotName, spotLatitudeNum, spotLongitudeNum, zoneId, public1, tz).getId();
				}
				if (result != null && changedGridPoint) {
					//Retrieving the first ww3forecaster for the spot, assuming that had just one spot
					logger.log(Level.INFO,"SpotServicesImpl - editSpot - Gridpoint changed, updating ww3 forecaster...");
					List<SimpleForecasterDTO> simpleForecasters = forecastService.getSimpleForecastersForSpot(spotId);
					if (simpleForecasters.size() > 0)
						forecastService.removeForecaster(simpleForecasters.get(0).getId());
					forecastService.createWW3Forecaster(result, new PointDTO(buoyLatitudeNum, buoyLongitudeNum));
					logger.log(Level.INFO,"SpotServicesImpl - editSpot - ww3 forecaster updated.");
				}
				logger.log(Level.INFO,"SpotServicesImpl - editSpot - Spot '" + spotName + "' updated.");
				return result;
			} else {
				logger.log(Level.INFO,"SpotServicesImpl - editSpot - The current user is not the owner of this spot.");
				throw new NeuralitoException(ErrorCode.USER_NOT_SPOT_OWNER);
			}
		}
		logger.log(Level.INFO,"SpotServicesImpl - editSpot - Permissions denied to the current user to perform this action.");
		return null;
	}

	@Override
	public SpotGwtDTO getSpot(Integer spotId) throws NeuralitoException {
		logger.log(Level.INFO,"SpotServicesImpl - getSpot - Trying to retrieve the spot: " + spotId + "...");
		SpotDTO spotDTO = spotService.getSpotById(spotId);
		SpotGwtDTO spotGwtDTO = super.getSpotGwtDTO(spotDTO);
		//Retrieve gridpoint of the spot (right now we are using the first simple forecaster(ww3) from the list because we know that we have just one for spot)
		//If this changes we need a litle more complex logic for this
		SimpleForecasterDTO simpleForecasterDTO = forecastService.getSimpleForecastersForSpot(spotDTO.getId()).get(0);
		spotGwtDTO.setGridPoint(simpleForecasterDTO.getGridPoint());
		logger.log(Level.INFO,"SpotServicesImpl - getSpot - Spot retrieved successfully.");
		return spotGwtDTO;
	}
}
