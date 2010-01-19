package edu.unicen.surfforecaster.gwt.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.AreaDTO;
import edu.unicen.surfforecaster.common.services.dto.CountryDTO;
import edu.unicen.surfforecaster.common.services.dto.ZoneDTO;
import edu.unicen.surfforecaster.gwt.client.dto.SpotGwtDTO;

@RemoteServiceRelativePath("SpotServices")
public interface SpotServices extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static SpotServicesAsync instance;

		public static SpotServicesAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(SpotServices.class);
			}
			return instance;
		}
	}
	
	Integer addSpot(String spotName, String spotLatitude, String spotLongitude, String buoyLatitude, String buoyLongitude, Integer zoneId, Integer countryId, 
			String zoneName, boolean public_, String timezone) throws NeuralitoException;
	
	Integer editSpot(Integer spotId, String spotName, String spotLatitude, String spotLongitude, String buoyLatitude, String buoyLongitude, Integer zoneId, Integer countryId, 
			String zoneName, boolean public_, String timezone) throws NeuralitoException;
	
	List<AreaDTO> getAreas() throws NeuralitoException;
	
	List<CountryDTO> getCountries() throws NeuralitoException;
	
	List<ZoneDTO> getZones(Integer country) throws NeuralitoException;

	List<SpotGwtDTO> getSpots(Integer zone) throws NeuralitoException;
	
	List<SpotGwtDTO> getSpotsCreatedBy() throws NeuralitoException;
	
	boolean deleteSpot(Integer spotId) throws NeuralitoException;
	
	SpotGwtDTO getSpot(Integer spotId) throws NeuralitoException;
}
