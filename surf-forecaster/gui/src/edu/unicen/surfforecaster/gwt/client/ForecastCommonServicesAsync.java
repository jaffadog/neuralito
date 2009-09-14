package edu.unicen.surfforecaster.gwt.client;

import java.util.Map;
import java.util.Vector;

import edu.unicen.surfforecaster.common.services.dto.HelloDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;


public interface ForecastCommonServicesAsync {
	
	void testService(AsyncCallback<String> callback);
	
	void login(String userName, String password, AsyncCallback<User> callback);
	
	void getSessionData(AsyncCallback<SessionData> callback);
	
	void closeSession(AsyncCallback<Void> callback);
	
	public void test2(AsyncCallback<HelloDTO> callback);
	
	void getAreas(AsyncCallback<Map<String, Vector>> callback);
	
	void getCountries(String area, AsyncCallback<Map<String, Vector>> callback);
	
	void getZones(String country, AsyncCallback<Map<String, Vector>> callback);
	
	void getSpots(String zone, AsyncCallback<Map<String, Vector>> callback);
	
	void getArea(AsyncCallback<Area> callback);
}
