package edu.unicen.surfforecaster.gwt.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
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
	
	List<PointDTO> getNearbyGridPoints(float spotLatitude, float spotLongitude) throws NeuralitoException;
	
	Map<String, List<ForecastDTO>> getLatestForecasts(Integer spotId) throws NeuralitoException;
	
	Map<Integer, Map<String, List<ForecastDTO>>> getLatestForecasts(ArrayList<Integer> spotsIds) throws NeuralitoException;
}
