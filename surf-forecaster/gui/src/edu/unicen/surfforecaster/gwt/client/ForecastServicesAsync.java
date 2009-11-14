package edu.unicen.surfforecaster.gwt.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;

public interface ForecastServicesAsync {
	void getNearbyGridPoints(float spotLatitude, float spotLongitude, AsyncCallback<List<PointDTO>> callback);
	
	void getLatestForecasts(Integer spotId, AsyncCallback<Map<String, List<ForecastDTO>>> callback);
}
