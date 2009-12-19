package edu.unicen.surfforecaster.gwt.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Level;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ForecastService;
import edu.unicen.surfforecaster.common.services.SpotService;
import edu.unicen.surfforecaster.common.services.dto.AreaDTO;
import edu.unicen.surfforecaster.common.services.dto.CountryDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.common.services.dto.ZoneDTO;
import edu.unicen.surfforecaster.gwt.client.SpotServices;
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
	
	public List<AreaDTO> getAreas() throws NeuralitoException {
		return spotService.getAreas();
			
	}
	
	public List<CountryDTO> getCountries() throws NeuralitoException {
		return spotService.getCountries();
	}
	
	public List<ZoneDTO> getZones(final Integer country) throws NeuralitoException {
		List<ZoneDTO> zones = new ArrayList<ZoneDTO>();
		if (this.getLoggedUser() != null)
			zones = spotService.getZones(country, this.getLoggedUser().getId());
		else
			zones = spotService.getZones(country);
		
		return zones;
	}
	
	public List<SpotGwtDTO> getSpots(Integer zone) throws NeuralitoException {
		List<SpotDTO> spots = new ArrayList<SpotDTO>();
		List<SpotGwtDTO> spotsGwt = new ArrayList<SpotGwtDTO>();
		if (this.getLoggedUser() != null) 
			spots = spotService.getSpots(zone, this.getLoggedUser().getId());
		else
			spots = spotService.getSpots(zone);
		
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
			final float spotLongitudeNum = new Float(spotLongitude);
			final float spotLatitudeNum = new Float(spotLatitude);
			final float buoyLongitudeNum = new Float(buoyLongitude);
			final float buoyLatitudeNum = new Float(buoyLatitude);
			final Integer userId = super.getLoggedUser().getId();
			Integer result = null;
			//TODO: set time zone.
			TimeZone tz = TimeZone.getTimeZone("Etc/GMT");
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
		return null;
	}
	
	private SpotGwtDTO getSpotGwtDTO(SpotDTO spotDTO) {
		return new SpotGwtDTO(spotDTO.getId(), spotDTO.getName(), spotDTO.getPoint(), spotDTO.getZone(), 
				spotDTO.getCountry(), spotDTO.getArea(), spotDTO.getUserId(), spotDTO.isPublik(), spotDTO.getTimeZone().getID());
	}

}
