package edu.unicen.surfforecaster.gwt.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ForecastServicesAsync {
	void getNearbyGridPoints(float spotLatitude, float spotLongitude, AsyncCallback<List<PointDTO>> callback);
	
	void getLatestForecasts(Integer spotId, AsyncCallback<Map<String, List<ForecastDTO>>> callback);
	
	void getLatestForecasts(ArrayList<Integer> spotsIds, AsyncCallback<Map<Integer, Map<String, List<ForecastDTO>>>> callback);
}
