package edu.unicen.surfforecaster.gwt.client;

import java.util.List;

import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ForecastServicesAsync {
	void getNearbyGridPoints(float spotLatitude, float spotLongitude, AsyncCallback<List<PointDTO>> callback);
}