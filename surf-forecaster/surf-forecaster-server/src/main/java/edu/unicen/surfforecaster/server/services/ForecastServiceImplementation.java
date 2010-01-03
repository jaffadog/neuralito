/**
 * 
 */
package edu.unicen.surfforecaster.server.services;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import weka.classifiers.Classifier;
import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ForecastService;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.VisualObservationDTO;
import edu.unicen.surfforecaster.common.services.dto.WekaForecasterEvaluationDTO;
import edu.unicen.surfforecaster.server.dao.ForecastDAO;
import edu.unicen.surfforecaster.server.dao.SpotDAO;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.Forecaster;
import edu.unicen.surfforecaster.server.domain.entity.Point;
import edu.unicen.surfforecaster.server.domain.entity.Spot;
import edu.unicen.surfforecaster.server.domain.entity.VisualObservation;
import edu.unicen.surfforecaster.server.domain.entity.WW3Forecaster;
import edu.unicen.surfforecaster.server.domain.entity.WekaForecaster;
import edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystem;
import edu.unicen.surfforecaster.server.domain.weka.strategy.DataSetGenerationStrategy;

/**
 * @author esteban
 * 
 */
public class ForecastServiceImplementation implements ForecastService {
	/**
	 * 
	 */
	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * The forecast dao.
	 */
	private ForecastDAO forecastDAO;

	/**
	 * The strategy to train the {@link WekaForecaster}..
	 */
	private DataSetGenerationStrategy dataSetGenerationStrategy;

	/**
	 * The wave watch system to generate the wave watch forecasts.
	 */
	private WaveWatchSystem waveWatchSystem;
	/**
	 * The spot dao.
	 */
	private SpotDAO spotDAO;

	private String classifierName;

	/**
	 * 
	 */
	public ForecastServiceImplementation() {
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.ForecastService#createWW3Forecaster(java.lang.Integer,
	 *      edu.unicen.surfforecaster.common.services.dto.PointDTO)
	 */
	@Override
	public Integer createWW3Forecaster(final Integer spotId,
			final PointDTO gridPointDTO) throws NeuralitoException {
		validateSpotExists(spotId);
		validateGridPoint(gridPointDTO);
		Point point = spotDAO.getPoint(new Float(gridPointDTO.getLatitude()),
				new Float(gridPointDTO.getLongitude()));
		if (point == null) {
			point = new Point(new Float(gridPointDTO.getLatitude()), new Float(
					gridPointDTO.getLongitude()));
			spotDAO.save(point);
		}
		final Spot spot = spotDAO.getSpotById(spotId);
		final WW3Forecaster forecaster = new WW3Forecaster(waveWatchSystem,
				point, spot.getLocation(), spot);
		final Integer id = forecastDAO.save(forecaster);
		spotDAO.addForecasterToSpot(forecaster, spot);
		spotDAO.saveSpot(spot);

		return id;
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.ForecastService#getLatestForecasts(java.lang.Integer)
	 */
	@Override
	public List<ForecastDTO> getLatestForecasts(final Integer forecasterId)
			throws NeuralitoException {
		long initial = System.currentTimeMillis();
		log.info("Retrieving latest forecast ");
		final Forecaster forecaster = forecastDAO
				.getForecasterById(forecasterId);
		Spot spot = forecaster.getSpot();
		TimeZone timeZone = spot.getTimeZone();
		final Collection<Forecast> forecasts = forecaster.getLatestForecasts();
		final List<ForecastDTO> forecastsDtos = new ArrayList<ForecastDTO>();
		for (final Iterator<Forecast> iterator = forecasts.iterator(); iterator
				.hasNext();) {
			final Forecast forecast = iterator.next();
			forecastsDtos.add(forecast.getDTO(timeZone));
		}
		long end = System.currentTimeMillis();
		log.info("Latest forecasts retrieved in: " + (end - initial) / 1000
				+ " seconds.");
		return forecastsDtos;
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.ForecastService#getNearbyGridPoints(double,
	 *      double)
	 */
	@Override
	public List<PointDTO> getNearbyGridPoints(final float latitude,
			final float longitude) throws NeuralitoException {

		final List<Point> surroundingGridPoints = waveWatchSystem
				.getPointNeighbors(new Point(latitude, longitude), 0.5D);
		final List<PointDTO> pointsDTOs = new ArrayList<PointDTO>();
		for (final Iterator iterator = surroundingGridPoints.iterator(); iterator
				.hasNext();) {
			final Point point = (Point) iterator.next();
			pointsDTOs.add(point.getDTO());
		}
		return pointsDTOs;
	}

	/**
	 * @throws NeuralitoException
	 * @see edu.unicen.surfforecaster.common.services.ForecastService#getArchivedForecasts(java.lang.Integer,
	 *      edu.unicen.surfforecaster.common.services.dto.PointDTO,
	 *      java.util.GregorianCalendar, java.util.GregorianCalendar)
	 */
	@Override
	public List<ForecastDTO> getArchivedForecasts(final Integer forecasterId,
			final GregorianCalendar from, final GregorianCalendar to)
			throws NeuralitoException {
		TimeZone timeZone = null;
		validateForecasterExists(forecasterId);
		final Forecaster forecaster = forecastDAO
				.getForecasterById(forecasterId);
		final List<ForecastDTO> forecastsDTOs = new ArrayList<ForecastDTO>();
		final Collection<Forecast> forecasts = forecaster.getArchivedForecasts(
				from.getTime(), to.getTime());
		for (final Iterator iterator = forecasts.iterator(); iterator.hasNext();) {
			final Forecast forecast = (Forecast) iterator.next();
			forecastsDTOs.add(forecast.getDTO(timeZone));
		}
		return forecastsDTOs;
	}

	// /**
	// * @see
	// edu.unicen.surfforecaster.common.services.ForecastService#createVisualObservationSet(File,
	// * Integer, String, String, Unit)
	// *
	// */
	// @Override
	// @Transactional
	// public Integer createVisualObservationSet(File file, Integer spotId,
	// String setName, String setDescription, Unit unit)
	// throws NeuralitoException {
	// validateSpotExists(spotId);
	// validateUnit(unit);
	// validateDescription(setDescription);
	// validateName(setName);
	// validateFile(file);
	// VisualObservationsLoader loader = new VisualObservationsLoader();
	// List<VisualObservation> observations = loader.loadVisualObservations(
	// file, unit);
	// Spot spot = spotDAO.getSpotById(spotId);
	//		
	// VisualObservationsSet observationsSet = new VisualObservationsSet(
	// setName, setDescription, observations, unit, spot);
	// spot.addVisualObservationSet(observationsSet);
	// Integer id = observationsSetDAO.save(observationsSet);
	// return id;
	// }
	/**
	 * Creates and train a forecaster which uses a machine learner. Inputs are:
	 * 
	 * @see edu.unicen.surfforecaster.common.services.ForecastService#createWW3Forecaster(Integer,
	 *      PointDTO)
	 */
	@Override
	public WekaForecasterEvaluationDTO createWekaForecaster(
			List<VisualObservationDTO> visualObservationsDTO, Integer spotId,
			HashMap<String, Serializable> dataSetStrategyOptions) {
		List<VisualObservation> visualObservations = translate(visualObservationsDTO);
		Classifier classifier;
		try {
			classifier = Classifier.forName(classifierName, null);
			final Spot spot = spotDAO.getSpotById(spotId);
			WekaForecaster forecaster = new WekaForecaster(classifier,
					dataSetGenerationStrategy, dataSetStrategyOptions,
					waveWatchSystem, visualObservations, spot);
			// Integer forecasterId = forecastDAO.save(forecaster);
			Map<String, String> evaluations = forecaster.getEvaluation();
			return new WekaForecasterEvaluationDTO(Double
					.parseDouble(evaluations.get("correlation")), Double
					.parseDouble(evaluations.get("meanAbsoluteError")),
 1, null);
		} catch (Exception e) {
			log.error(e);
		}
		return null;

	}

	/**
	 * Translate VisualObservationDTO list into VisualObservation list.
	 * 
	 * @param visualObservationsDTO
	 * @return
	 */
	private List<VisualObservation> translate(
			List<VisualObservationDTO> visualObservationsDTO) {
		List<VisualObservation> observations = new ArrayList<VisualObservation>();
		for (VisualObservationDTO visualObservationDTO : visualObservationsDTO) {
			VisualObservation visualObservation = new VisualObservation(
					visualObservationDTO.getWaveHeight(), visualObservationDTO
							.getObservationDate(), visualObservationDTO
							.getUnit());

			observations.add(visualObservation);
		}
		return observations;
	}

	/**
	 * Validation
	 * 
	 * @param gridPoint
	 */
	private void validateGridPoint(final PointDTO gridPoint)
			throws NeuralitoException {
		if (gridPoint == null)
			throw new NeuralitoException(ErrorCode.GRID_POINT_CANNOT_BE_NULL);

		if (waveWatchSystem.isGridPoint(new Point(gridPoint.getLatitude(),
				gridPoint.getLongitude())))
			return;
		else
			throw new NeuralitoException(ErrorCode.GRID_POINT_INVALID);

	}

	/**
	 * Validation
	 * 
	 * @param spotId
	 * @throws NeuralitoException
	 */
	private void validateSpotExists(final Integer spotId)
			throws NeuralitoException {
		if (spotId == null)
			throw new NeuralitoException(ErrorCode.SPOT_ID_NULL);
		if (spotId < 0)
			throw new NeuralitoException(ErrorCode.SPOT_ID_INVALID);
		if (spotDAO.getSpotById(spotId) == null)
			throw new NeuralitoException(ErrorCode.SPOT_ID_DOES_NOT_EXISTS);

	}

	/**
	 * Validation
	 * 
	 * @param setName
	 * @throws NeuralitoException
	 */
	private void validateName(String setName) throws NeuralitoException {
		if (setName == null)
			throw new NeuralitoException(
					ErrorCode.VISUAL_OBSERVATION_SET_NAME_CANNOT_BE_NULL);
		if (setName.trim().isEmpty())
			throw new NeuralitoException(
					ErrorCode.VISUAL_OBSERVATION_SET_NAME_CANNOT_BE_EMPTY);

	}

	/**
	 * Validation
	 * 
	 * @param forecasterId
	 * @throws NeuralitoException
	 */
	private void validateForecasterExists(final Integer forecasterId)
			throws NeuralitoException {
		if (forecasterId == null)
			throw new NeuralitoException(ErrorCode.FORECASTER_ID_CANNOT_BE_NULL);
		if (forecasterId <= 0)
			throw new NeuralitoException(ErrorCode.FORECASTER_ID_INVALID);
		if (forecastDAO.getForecasterById(forecasterId) == null)
			throw new NeuralitoException(
					ErrorCode.FORECASTER_ID_DOES_NOT_EXISTS);

	}

	/**
	 * Validation
	 * 
	 * @param setDescription
	 * @throws NeuralitoException
	 */
	private void validateDescription(String setDescription)
			throws NeuralitoException {
		if (setDescription == null)
			throw new NeuralitoException(
					ErrorCode.VISUAL_OBSERVATION_SET_DESCRIPTION_CANNOT_BE_NULL);
		if (setDescription.trim().isEmpty())
			throw new NeuralitoException(
					ErrorCode.VISUAL_OBSERVATION_SET_DESCRIPTION_CANNOT_BE_EMPTY);
	}

	/**
	 * Validation
	 * 
	 * @param file
	 */
	private void validateFile(File file) {
		// TODO perform validation

	}

	/**
	 * Validation
	 * 
	 * @param unit
	 * @throws NeuralitoException
	 */
	private void validateUnit(Unit unit) throws NeuralitoException {
		if (unit == null)
			throw new NeuralitoException(
					ErrorCode.VISUAL_OBSERVATION_SET_UNIT_CANNOT_BE_NULL);

	}

	public void setDataSetGenerationStrategy(
			DataSetGenerationStrategy dataSetGenerationStrategy) {
		this.dataSetGenerationStrategy = dataSetGenerationStrategy;
	}

	public void setClassifierName(String classifierName) {
		this.classifierName = classifierName;
	}

	/**
	 * For Spring injection
	 * 
	 * @param forecastDAO
	 *            the forecastDAO to set
	 */
	public void setForecastDAO(final ForecastDAO forecastDAO) {
		this.forecastDAO = forecastDAO;
	}

	/**
	 * For Spring injection
	 * 
	 * @param spotDAO
	 *            the spotDAO to set
	 */
	public void setSpotDAO(final SpotDAO spotDAO) {
		this.spotDAO = spotDAO;
	}

	public void setWaveWatchSystem(WaveWatchSystem waveWatchSystem) {
		this.waveWatchSystem = waveWatchSystem;
	}


}
