package edu.unicen.surfforecaster.gwt.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SpotServicesAsync {
	void addSpot(String spotName, String spotLongitude, String spotLatitude, String buoyLongitude, String buoyLatitude, Integer zoneId, Integer countryId, 
			String zoneName, boolean public_, String timezone, AsyncCallback<Integer> callback);

	void getAreas(AsyncCallback<Map<String, List>> callback);

	void getCountries(String area, AsyncCallback<Map<String, List>> callback);

	void getZones(String country, AsyncCallback<Map<String, List>> callback);

	void getSpots(String spot, AsyncCallback<Map<String, List>> callback);
}
