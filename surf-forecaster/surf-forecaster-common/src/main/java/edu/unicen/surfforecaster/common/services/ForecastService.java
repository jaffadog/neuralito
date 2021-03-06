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
import edu.unicen.surfforecaster.common.services.dto.SimpleForecasterDTO;
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
	 * classifier. Classifier will be trained using wavewatch archived data, and
	 * visual observations.
	 * 
	 * @param spotId
	 *            the id of the spot to which this forecaster belong.
	 * @param options
	 *            a list of options in order to train the classifier.
	 * 
	 * @return WekaForecasterEvaluationDTO DTO containing the performance
	 *         evaluation of the trained classifier and the id of the created
	 *         forecasters.
	 * @throws NeuralitoException 
	 */
	public WekaForecasterEvaluationDTO createWekaForecaster(Integer spotId,
			HashMap<String, Serializable> options) throws NeuralitoException;

	public List<WekaForecasterEvaluationDTO> getWekaForecasters(Integer spotId)
			throws NeuralitoException;

	/**
	 * Obtain all the forecasts belonging to SimpleForecaster class associated
	 * with the given spot.
	 * 
	 * @param spotId
	 * @return
	 * @throws NeuralitoException
	 */
	public List<SimpleForecasterDTO> getSimpleForecastersForSpot(Integer spotId)
			throws NeuralitoException;

	/**
	 * Returns the number of forecasts available for each month on a specific
	 * grid point. NOT FULLY IMPLEMENTED
	 * 
	 * @return
	 */
	// TODO finish this method implementation.
	public List<ArchiveDetailDTO> getAvailableWekaArchive(PointDTO point)
			throws NeuralitoException;

	/**
	 * Delete the given forecaster.
	 * 
	 * @param forecasterId
	 * @throws NeuralitoException
	 */
	public void removeForecaster(Integer forecasterId)
			throws NeuralitoException;

}