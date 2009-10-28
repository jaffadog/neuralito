package edu.unicen.surfforecaster.gwt.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ForecastService;
import edu.unicen.surfforecaster.common.services.ForecasterDTO;
import edu.unicen.surfforecaster.common.services.SpotService;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.gwt.client.ForecastServices;

@SuppressWarnings("serial")
public class ForecastServicesImpl extends ServicesImpl implements ForecastServices {
	
	private ForecastService forecastService;
	private SpotService spotService;

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
	public ForecastService getForecastService() {
		return forecastService;
	}
	
	/**
	 * @param spot service
	 *            the service to set
	 */
	public void setSpotService(final SpotService service) {
		spotService = service;
	}

	/**
	 * @return the spot service
	 */
	public SpotService getSpotService() {
		return spotService;
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
	
	public List<ForecastDTO> getWW3LatestForecasts(Integer spotId) throws NeuralitoException {
		
		List<ForecasterDTO> forecasters = spotService.getSpotForecasters(spotId);
		ForecasterDTO ww3Forecaster = forecasters.get(0);
		
		List<ForecastDTO> result = forecastService.getLatestForecasts(ww3Forecaster.getId());
		
		return result;
	}
}
