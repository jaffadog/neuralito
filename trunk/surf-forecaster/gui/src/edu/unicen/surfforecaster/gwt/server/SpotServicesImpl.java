package edu.unicen.surfforecaster.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ForecastService;
import edu.unicen.surfforecaster.common.services.SpotService;
import edu.unicen.surfforecaster.common.services.dto.AreaDTO;
import edu.unicen.surfforecaster.common.services.dto.CountryDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.common.services.dto.UserDTO;
import edu.unicen.surfforecaster.common.services.dto.ZoneDTO;
import edu.unicen.surfforecaster.gwt.client.SpotServices;
import edu.unicen.surfforecaster.gwt.client.utils.SessionData;

public class SpotServicesImpl extends ServicesImpl implements SpotServices {
	
	private SpotService spotService;
	private ForecastService forecastService;

	/**
	 * @param service
	 *            the service to set
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
	 * @param service
	 *            the service to set
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
	
	public List<AreaDTO> getAreas() throws NeuralitoException {
		return spotService.getAreas();
			
	}
	
	public List<CountryDTO> getCountries() throws NeuralitoException {
		return new ArrayList<CountryDTO>();
		//TODO descomentar el metodo getcountries() cuando ya este implemetnado en el server
		//return spotService.getCountries();
	}
	
	public List<ZoneDTO> getZones(final Integer country) throws NeuralitoException {
		List<ZoneDTO> zones = new ArrayList<ZoneDTO>();
		if (this.getUser() != null)
			zones = spotService.getZones(country, this.getUser().getId());
		//TODO agregar el else con getzones(idcountry) si no esta logueado el usuario
		
		return zones;
	}
	
	public List<SpotDTO> getSpots(final Integer zone, final UserDTO user) throws NeuralitoException {
		List<SpotDTO> spots = new ArrayList<SpotDTO>();
		if (this.getUser() != null) {
			//TODO get spots descomentar la sig linea
			//spots = spotService.getSpots(zone, user.getId());
		}
		//TODO agregar el else con getpots(idzone) si no esta logueado el usuario

		return spots;
	}

	public Integer addSpot(final String spotName, final String spotLatitude, final String spotLongitude,
			final String buoyLatitude, final String buoyLongitude, final Integer zoneId,
			final Integer countryId, final String zoneName,
			final boolean public_, final String timezone) throws NeuralitoException {
		
		logger.log(Level.INFO,"SpotServicesImpl - addSpot - Trying to add a new spot: '" + spotName + "'...");
		
		final SessionData sessionData = this.getSessionData();
		if (sessionData == null)
			throw new NeuralitoException(ErrorCode.USER_ID_INVALID);
		else {
			final float spotLongitudeNum = new Float(spotLongitude);
			final float spotLatitudeNum = new Float(spotLatitude);
			final float buoyLongitudeNum = new Float(buoyLongitude);
			final float buoyLatitudeNum = new Float(buoyLatitude);
			final Integer userId = new Integer(((UserDTO)sessionData.getUserDTO()).getId());
			Integer result = null;
			if (zoneName.trim().equals("")) {
				logger.log(Level.INFO,"SpotServicesImpl - addSpot - Adding only the spot: '" + spotName + "'...");
				result = spotService.addSpot(spotName, spotLatitudeNum, spotLongitudeNum, zoneId, userId, public_, timezone);
			} else {
				// result = spotService.addZoneAndSpot(zoneName, countryId,
				// spotName, longitudeNum, latitudeNum, userId, public_);
				logger.log(Level.INFO,"SpotServicesImpl - addSpot - Adding both zone: '"  + zoneName.trim() + "' and spot: '" + spotName + "'...");	
				result = spotService.addZoneAndSpot(zoneName.trim(), 1, spotName, spotLatitudeNum, spotLongitudeNum, userId, public_, timezone);
			}
			
			if (result != null) {
				forecastService.createWW3Forecaster(result, new PointDTO(buoyLatitudeNum, buoyLongitudeNum));
				logger.log(Level.INFO,"SpotServicesImpl - addSpot - New spot '" + spotName + "' added");
			}
			
			return result;
		}
	}

	@Override
	public List<SpotDTO> getSpots(Integer zone) throws NeuralitoException {
		// TODO Auto-generated method stub
		return null;
	}

}
