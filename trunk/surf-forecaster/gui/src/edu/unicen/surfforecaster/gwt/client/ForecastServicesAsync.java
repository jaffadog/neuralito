package edu.unicen.surfforecaster.gwt.client;

import java.util.List;
import java.util.Map;

import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.gwt.client.dto.ForecastGwtDTO;
import edu.unicen.surfforecaster.gwt.client.dto.WekaForecasterEvaluationGwtDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ForecastServicesAsync {
	void getNearbyGridPoints(float spotLatitude, float spotLongitude, AsyncCallback<List<PointDTO>> callback);
	
	void getLatestForecasts(Integer spotId, AsyncCallback<Map<String, List<ForecastGwtDTO>>> callback);
	
	void getLatestForecasts(List<Integer> spotsIds, AsyncCallback<Map<Integer, Map<String, List<ForecastGwtDTO>>>> callback);
	
	void getWekaForecasters(Integer spotId, AsyncCallback<List<WekaForecasterEvaluationGwtDTO>> callback);
}
