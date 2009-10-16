package edu.unicen.surfforecaster.gwt.client;

import java.util.Collection;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.AreaDTO;

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
	
	Integer addSpot(String spotName, String spotLongitude, String spotLatitude, String buoyLongitude, String buoyLatitude, Integer zoneId, Integer countryId, 
			String zoneName, boolean public_, String timezone) throws NeuralitoException;

	Map<String, Vector> getAreas() throws NeuralitoException;

	Map<String, Vector> getCountries(String area);

	Map<String, Vector> getZones(String country);

	Map<String, Vector> getSpots(String spot);
}
