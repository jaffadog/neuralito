package edu.unicen.surfforecaster.gwt.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;

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
	
	/**
	 * Returns a map indexed by forecasterName, where each value represets the latest forecast 180h.
	 * @param Integer spotId, the spot to evaluate
	 * @return Map<String, List<ForecastDTO>>
	 */
	public Map<String, List<ForecastDTO>> getLatestForecasts(Integer spotId) throws NeuralitoException {
		logger.log(Level.INFO,"ForecastServicesImpl - getLatestForecasts(spotId) - Retrieving latest forecasts from spotId = " + spotId + "...");
		Map<String, List<ForecastDTO>> result = new HashMap<String, List<ForecastDTO>>();
		List<ForecasterDTO> spotForecasters = spotService.getSpotForecasters(spotId);
		Iterator<ForecasterDTO> i = spotForecasters.iterator();
		while (i.hasNext()) {
			ForecasterDTO forecaster = i.next();
			result.put(forecaster.getName(), forecastService.getLatestForecasts(forecaster.getId()));
		}
		logger.log(Level.INFO,"ForecastServicesImpl - getLatestForecasts(spotId) - Forecasts retrieved successfully.");
		return result;
	}

	/**
	 * Returns a map indexed by spotId where each value contains another map<spotName, list> with all the forecasters of that
	 * spot and each value contains the latest forecast (180h) for the spot
	 * @param List<Integer> spotsIds, the list of spots to retrieve forecasts
	 * @return Map<Integer, Map<String, List<ForecastDTO>>>
	 */
	public Map<Integer, Map<String, List<ForecastDTO>>> getLatestForecasts(List<Integer> spotsIds) throws NeuralitoException {
		logger.log(Level.INFO,"ForecastServicesImpl - getLatestForecasts(spotsList) - Retrieving latest forecasts of multiple spots...");
		Map<Integer, Map<String, List<ForecastDTO>>> result = new HashMap<Integer, Map<String, List<ForecastDTO>>>();
		Iterator<Integer> i = spotsIds.iterator();
		while (i.hasNext()) {
			Integer spotId = i.next();
			result.put(spotId, this.getLatestForecasts(spotId));
		}
		logger.log(Level.INFO,"ForecastServicesImpl - getLatestForecasts(spotsList) - Forecasts retrieved successfully.");
		return result;
	}
}
