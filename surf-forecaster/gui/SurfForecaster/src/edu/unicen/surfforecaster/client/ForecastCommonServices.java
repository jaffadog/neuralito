package edu.unicen.surfforecaster.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("ForecastCommonServices")
public interface ForecastCommonServices extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static ForecastCommonServicesAsync instance;
		public static ForecastCommonServicesAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(ForecastCommonServices.class);
			}
			return instance;
		}	
	}
	
	String testService();
	User login(String userName, String password);
	SessionData getSessionData();
	void closeSession();
}
