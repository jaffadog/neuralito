package edu.unicen.surfforecaster.gwt.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.unicen.surfforecaster.common.services.dto.PointDTO;

@RemoteServiceRelativePath("ForecastServices")
public interface ForecastServices extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static ForecastServicesAsync instance;

		public static ForecastServicesAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(ForecastServices.class);
			}
			return instance;
		}
	}
	
	List<PointDTO> getNearbyGridPoints(double spotLatitude, double spotLongitude);
}
