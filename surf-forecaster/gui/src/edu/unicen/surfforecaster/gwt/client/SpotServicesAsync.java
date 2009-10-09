package edu.unicen.surfforecaster.gwt.client;

import java.util.Collection;
import java.util.Map;
import java.util.Vector;

import edu.unicen.surfforecaster.common.services.dto.AreaDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SpotServicesAsync {
	void addSpot(String spotName, String longitude, String latitude, Integer zoneId, Integer countryId, 
			String zoneName, boolean public_, String timezone, AsyncCallback<Integer> callback);

	void getAreas(AsyncCallback<Collection<AreaDTO>> callback);

	void getCountries(String area, AsyncCallback<Map<String, Vector>> callback);

	void getZones(String country, AsyncCallback<Map<String, Vector>> callback);

	void getSpots(String spot, AsyncCallback<Map<String, Vector>> callback);
}
