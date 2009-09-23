package edu.unicen.surfforecaster.gwt.client;

import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ForecastCommonServicesAsync {

	void login(String userName, String password, AsyncCallback<User> callback);

	void getSessionData(AsyncCallback<SessionData> callback);

	void closeSession(AsyncCallback<Void> callback);

	void getAreas(AsyncCallback<Map<String, Vector>> callback);

	void getCountries(String area, AsyncCallback<Map<String, Vector>> callback);

	void getZones(String country, AsyncCallback<Map<String, Vector>> callback);

	void getSpots(String zone, AsyncCallback<Map<String, Vector>> callback);

	void getArea(AsyncCallback<Area> callback);
}
