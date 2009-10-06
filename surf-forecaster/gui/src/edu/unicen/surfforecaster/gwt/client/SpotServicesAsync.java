package edu.unicen.surfforecaster.gwt.client;

import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SpotServicesAsync {
		
	void addSpot(String spotName, String longitude, String latitude, Integer zoneId, Integer countryId, String zoneName, boolean public_, AsyncCallback<Integer> callback);

	void getAreas(AsyncCallback<Map<String, Vector>> callback);

	void getCountries(String area, AsyncCallback<Map<String, Vector>> callback);

	void getZones(String country, AsyncCallback<Map<String, Vector>> callback);

	void getSpots(String spot, AsyncCallback<Map<String, Vector>> callback);

}
