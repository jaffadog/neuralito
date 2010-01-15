package edu.unicen.surfforecaster.gwt.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Level;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ForecastService;
import edu.unicen.surfforecaster.common.services.ForecasterDTO;
import edu.unicen.surfforecaster.common.services.SpotService;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.common.services.dto.WekaForecasterEvaluationDTO;
import edu.unicen.surfforecaster.gwt.client.ForecastServices;
import edu.unicen.surfforecaster.gwt.client.dto.ForecastGwtDTO;
import edu.unicen.surfforecaster.gwt.client.dto.WekaForecasterEvaluationGwtDTO;

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
	 * @return Map<String, List<ForecastGwtDTO>>
	 */
	public Map<String, List<ForecastGwtDTO>> getLatestForecasts(Integer spotId) throws NeuralitoException {
		logger.log(Level.INFO,"ForecastServicesImpl - getLatestForecasts(spotId) - Retrieving latest forecasts from spotId = " + spotId + "...");
		Map<String, List<ForecastGwtDTO>> result = new HashMap<String, List<ForecastGwtDTO>>();
		List<ForecasterDTO> spotForecasters = spotService.getSpotForecasters(spotId);
		Iterator<ForecasterDTO> i = spotForecasters.iterator();
		while (i.hasNext()) {
			ForecasterDTO forecaster = i.next();
			List<ForecastDTO> latestForecasts = forecastService.getLatestForecasts(forecaster.getId());
			List<ForecastGwtDTO> latestGwtForecasts = new ArrayList<ForecastGwtDTO>();
			Iterator<ForecastDTO> j = latestForecasts.iterator();
			System.out.println(forecaster.getName());
			while (j.hasNext()) {
				ForecastDTO forecastDTO = j.next();
				ForecastGwtDTO forecastGwtDTO = this.getForecastGwtDTO(forecastDTO);
				latestGwtForecasts.add(forecastGwtDTO);
				System.out.println(forecastDTO.getBaseDate().getTime() + " -> " + forecastDTO.getForecastTime());
			}
			result.put(forecaster.getName(), latestGwtForecasts);
		}
		logger.log(Level.INFO,"ForecastServicesImpl - getLatestForecasts(spotId) - Forecasts retrieved successfully.");
		return result;
	}

	/**
	 * Returns a map indexed by spotId where each value contains another map<spotName, list> with all the forecasters of that
	 * spot and each value contains the latest forecast (180h) for the spot
	 * @param List<Integer> spotsIds, the list of spots to retrieve forecasts
	 * @return Map<Integer, Map<String, List<ForecastGwtDTO>>>
	 */
	public Map<Integer, Map<String, List<ForecastGwtDTO>>> getLatestForecasts(List<Integer> spotsIds) throws NeuralitoException {
		logger.log(Level.INFO,"ForecastServicesImpl - getLatestForecasts(spotsList) - Retrieving latest forecasts of multiple spots...");
		Map<Integer, Map<String, List<ForecastGwtDTO>>> result = new HashMap<Integer, Map<String, List<ForecastGwtDTO>>>();
		Iterator<Integer> i = spotsIds.iterator();
		while (i.hasNext()) {
			Integer spotId = i.next();
			result.put(spotId, this.getLatestForecasts(spotId));
		}
		logger.log(Level.INFO,"ForecastServicesImpl - getLatestForecasts(spotsList) - Forecasts retrieved successfully.");
		return result;
	}
	
	private ForecastGwtDTO getForecastGwtDTO(ForecastDTO forecastDTO) {
		return new ForecastGwtDTO(forecastDTO.getBaseDate().getTime(), forecastDTO.getForecastTime(), forecastDTO.getMap());
	}

	@Override
	public List<WekaForecasterEvaluationGwtDTO> getWekaForecasters(Integer spotId) throws NeuralitoException {
		logger.log(Level.INFO,"ForecastServicesImpl - getWekaForecasters - Retrieving Weka forecasters evaluations for spot: " + spotId + "...");
		List<WekaForecasterEvaluationDTO> wekaForecasterEvaluationDTOs = forecastService.getWekaForecasters(spotId);
		List<WekaForecasterEvaluationGwtDTO> result = new ArrayList<WekaForecasterEvaluationGwtDTO>();
		Iterator<WekaForecasterEvaluationDTO> i = wekaForecasterEvaluationDTOs.iterator();
		while (i.hasNext()) {
			WekaForecasterEvaluationDTO wekaForecasterEvaluationDTO = i.next();
			result.add(this.getWekaForecasterEvaluationGwtDTO(wekaForecasterEvaluationDTO));
		}
		logger.log(Level.INFO,"ForecastServicesImpl - getWekaForecasters - " + result.size() + " Weka forecasters evaluations retrieved successfully.");
		return result; 
	}
	
	private WekaForecasterEvaluationGwtDTO getWekaForecasterEvaluationGwtDTO(WekaForecasterEvaluationDTO wekaForecasterEvaluationDTO) {
		Map<String, String> options = new HashMap<String, String>();
		Map<String, Serializable> trainingOptions = wekaForecasterEvaluationDTO.getTrainningOptions();
		Iterator<String> keys = trainingOptions.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			options.put(key, trainingOptions.get(key).toString());
		}
		
		return new WekaForecasterEvaluationGwtDTO(wekaForecasterEvaluationDTO.getCorrelation(), wekaForecasterEvaluationDTO.getMeanAbsoluteError(), 
				wekaForecasterEvaluationDTO.getId(), wekaForecasterEvaluationDTO.getClassifierName(), options);
	}
}
