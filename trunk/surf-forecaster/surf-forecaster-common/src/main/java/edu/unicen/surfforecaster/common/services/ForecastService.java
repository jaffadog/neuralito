/**
 * 
 */
package edu.unicen.surfforecaster.common.services;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.common.services.dto.VisualObservationDTO;
import edu.unicen.surfforecaster.common.services.dto.WekaForecasterEvaluationDTO;

/**
 * Services for creating new forecasters and obtaining forecasts.
 * 
 * @author esteban
 * 
 */
public interface ForecastService {

	/**
	 * Creates a wave watch 3 forecaster for the given spot. The forecast data
	 * produced by this forecaster will correspond to the output of the wave
	 * watch model at the given grid point.
	 * 
	 * @param spotId
	 * @param gridPoint
	 * @return id the id of the created forecaster.
	 */
	public Integer createWW3Forecaster(Integer spotId, PointDTO gridPoint)
			throws NeuralitoException;

	/**
	 * Obtains all the wave watch 3 grid points that surrounds the given point.
	 * If given point is placed exactly on a grid point that is the only grid
	 * point returned.
	 * 
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public List<PointDTO> getNearbyGridPoints(float latitude, float longitude)
			throws NeuralitoException;

	/**
	 * Obtain the latest forecasts of the given forecaster.
	 * 
	 * @param forecasterId
	 *            the id of the forecaster who will provide the forecast.
	 * @return
	 */
	public List<ForecastDTO> getLatestForecasts(Integer forecasterId)
			throws NeuralitoException;

	/**
	 * @param gridPoint
	 * @param from
	 * @param to
	 * @return
	 * @throws NeuralitoException
	 */
	public List<ForecastDTO> getArchivedForecasts(Integer forecasterId,
			GregorianCalendar from, GregorianCalendar to)
			throws NeuralitoException;

	// /**
	// * @param gridPoint
	// * @param from
	// * @param to
	// * @return
	// * @throws NeuralitoException
	// */
	// public Integer createVisualObservationSet(File file, Integer spotId,
	// String setName, String spotDescription, Unit unit)
	// throws NeuralitoException;

	/**
	 * Create a weka forecaster. This forecaster will use a machine learning
	 * classifier to learn from past data how WaveWatch forecast affects the
	 * spot, so the wave height values of this forecaster will be improved by
	 * the classifier.
	 * 
	 * @param observationSetId
	 *            the visual observation set to train the classifier.
	 * @param modelId
	 *            the model of wavewatchIII where to aquire wavewatch III data
	 * @param strategyId
	 *            the strategy to compose the instances to the classifier.
	 * @param strategyParameters
	 *            the parameters to the strategy.
	 * @param classifierId
	 *            the weka classifier to perform machine learning from visual
	 *            observations and WW data.
	 * @param classifierParameters
	 *            the parameters to the weka classifier.
	 * @return WekaForecasterEvaluationDTO DTO containing the performance
	 *         evaluation of the trained classifier and the id of the created
	 *         forecasters.
	 */
	public WekaForecasterEvaluationDTO createWekaForecaster(
			List<VisualObservationDTO> visualObservations, Integer spotId,
			HashMap<String, Serializable> options);

	public List<WekaForecasterEvaluationDTO> getWekaForecasters(Integer spotId)
			throws NeuralitoException;

}