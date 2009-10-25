package edu.unicen.surfforecaster.gwt.server;

import java.util.ArrayList;
import java.util.List;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ForecastService;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.gwt.client.ForecastServices;

@SuppressWarnings("serial")
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
	 * @throws NeuralitoException 
	 */
	public List<PointDTO> getNearbyGridPoints(float spotLatitude, float spotLongitude) throws NeuralitoException  {
		List<PointDTO> result = new ArrayList<PointDTO>();
		result = forecastService.getNearbyGridPoints(spotLatitude, spotLongitude);
		return result;
	}
}
