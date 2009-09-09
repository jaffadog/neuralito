package edu.unicen.surfforecaster.gwt.client;

import java.util.Vector;

import edu.unicen.surfforecaster.common.services.dto.HelloDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;


public interface ForecastCommonServicesAsync {
	
	void testService(AsyncCallback<String> callback);
	
	void login(String userName, String password, AsyncCallback<User> callback);
	
	void getSessionData(AsyncCallback<SessionData> callback);
	
	void closeSession(AsyncCallback<Void> callback);
	
	public void test2(AsyncCallback<HelloDTO> callback);
	
	void getAreas(AsyncCallback<Vector<Area>> callback);
	
	void getCountries(String area, AsyncCallback<Vector<Country>> callback);
	
	void getZones(String country, AsyncCallback<Vector<Zone>> callback);
	
	void getSpots(String zone, AsyncCallback<Vector<Spot>> callback);
	
	void getArea(AsyncCallback<Area> callback);
}
