package edu.unicen.surfforecaster.gwt.client;

import java.util.Map;
import java.util.Vector;

import edu.unicen.surfforecaster.common.services.dto.UserDTO;
import edu.unicen.surfforecaster.gwt.client.utils.SessionData;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ForecastCommonServicesAsync {
	void login(String userName, String password, AsyncCallback<UserDTO> callback);
	
	void addUser(String name, String lastname, String email, String username, String password, int type, AsyncCallback<Integer> callback);

	void getSessionData(AsyncCallback<SessionData> callback);
	
	void addSpot(String spotName, String longitude, String latitude, Integer zoneId, Integer countryId, String zoneName, boolean public_, AsyncCallback<Integer> callback);

	void closeSession(AsyncCallback<Void> callback);

	void getAreas(AsyncCallback<Map<String, Vector>> callback);

	void getCountries(String area, AsyncCallback<Map<String, Vector>> callback);

	void getZones(String country, AsyncCallback<Map<String, Vector>> callback);

	void getSpots(String spot, AsyncCallback<Map<String, Vector>> callback);

	void getArea(AsyncCallback<Area> callback);
}
