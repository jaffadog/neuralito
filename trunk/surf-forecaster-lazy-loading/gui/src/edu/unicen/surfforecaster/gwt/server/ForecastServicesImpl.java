package edu.unicen.surfforecaster.gwt.server;

import java.util.ArrayList;
import java.util.List;

import edu.unicen.surfforecaster.common.services.ForecastService;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.gwt.client.ForecastServices;

public class ForecastServicesImpl extends ServicesImpl implements ForecastServices {
	
	private ForecastService forecastService;

	/**
	 * @param service
	 *            the service to set
	 */
	public void setForecastService(final ForecastService service) {
		forecastService = service;
	}

	/**
	 * @return the forecast service
	 */
	public ForecastService getSpotService() {
		return forecastService;
	}
	
	/**
	 * Return the grid point around and specific spot latitude and longitude
	 * @param double spotLatitude
	 * @param double spotLongitude
	 * @return List<PointDTO> A list with all first grid points around the spot coordinates
	 */
	public List<PointDTO> getNearbyGridPoints(double spotLatitude, double spotLongitude) {
		List<PointDTO> result = new ArrayList<PointDTO>();
		
		//result = forecastService.getNearbyGridPoints(spotLatitude, spotLongitude);
		
		PointDTO point = new PointDTO(spotLongitude - 1, spotLatitude - 1);
		result.add(point);
		point = new PointDTO(spotLongitude - 1, spotLatitude + 1);
		result.add(point);
		point = new PointDTO(spotLongitude + 1, spotLatitude - 1);
		result.add(point);
		point = new PointDTO(spotLongitude + 1, spotLatitude + 1);
		result.add(point);
		
		return result;
	}
}
