package edu.unicen.surfforecaster.gwt.client;

import java.util.List;

import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ForecastServicesAsync {
	void getNearbyGridPoints(double spotLatitude, double spotLongitude, AsyncCallback<List<PointDTO>> callback);
}
