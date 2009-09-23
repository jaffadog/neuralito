package edu.unicen.surfforecaster.gwt.client;

import java.util.Map;
import java.util.Vector;

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

		public static ForecastCommonServicesAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(ForecastCommonServices.class);
			}
			return instance;
		}
	}

	User login(String userName, String password);

	SessionData getSessionData();

	void closeSession();

	Map<String, Vector> getAreas();

	Map<String, Vector> getCountries(String area);

	Map<String, Vector> getZones(String country);

	Map<String, Vector> getSpots(String spot);

	Area getArea();
}
