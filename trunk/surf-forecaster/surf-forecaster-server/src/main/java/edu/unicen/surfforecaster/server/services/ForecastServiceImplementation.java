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
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import weka.classifiers.Classifier;
import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ArchiveDetailDTO;
import edu.unicen.surfforecaster.common.services.ForecastService;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.common.services.dto.SimpleForecasterDTO;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.VisualObservationDTO;
import edu.unicen.surfforecaster.common.services.dto.WekaForecasterEvaluationDTO;
import edu.unicen.surfforecaster.server.dao.ForecastDAO;
import edu.unicen.surfforecaster.server.dao.SpotDAO;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.Forecaster;
import edu.unicen.surfforecaster.server.domain.entity.Point;
import edu.unicen.surfforecaster.server.domain.entity.SimpleForecaster;
import edu.unicen.surfforecaster.server.domain.entity.Spot;
import edu.unicen.surfforecaster.server.domain.entity.VisualObservation;
import edu.unicen.surfforecaster.server.domain.entity.WekaForecaster;
import edu.unicen.surfforecaster.server.domain.wavewatch.ArchiveDetail;
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
	private final Logger log = Logger.getLogger(this.getClass());

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
		final SimpleForecaster forecaster = new SimpleForecaster(
				waveWatchSystem, point, spot.getLocation(), spot);
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
		final long initial = System.currentTimeMillis();
		log.info("Retrieving latest forecast ");
		final Forecaster forecaster = forecastDAO
				.getForecasterById(forecasterId);
		final Spot spot = forecaster.getSpot();
		final TimeZone timeZone = spot.getTimeZone();
		final Collection<Forecast> forecasts = forecaster.getLatestForecasts();
		final List<ForecastDTO> forecastsDtos = new ArrayList<ForecastDTO>();
		for (final Iterator<Forecast> iterator = forecasts.iterator(); iterator
				.hasNext();) {
			final Forecast forecast = iterator.next();
			forecastsDtos.add(forecast.getDTO(timeZone));
		}
		final long end = System.currentTimeMillis();
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
				.getPointNeighbors(new Point(latitude, longitude));
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
		final TimeZone timeZone = null;
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

	/**
	 * Creates and train a forecaster which uses a machine learner. Inputs are:
	 * 
	 * @see edu.unicen.surfforecaster.common.services.ForecastService#createWW3Forecaster(Integer,
	 *      PointDTO)
	 */
	@Override
	@Transactional
	public WekaForecasterEvaluationDTO createWekaForecaster(
			final List<VisualObservationDTO> visualObservationsDTO,
			final Integer spotId,
			final HashMap<String, Serializable> dataSetStrategyOptions) {
		log.info("Creating weka forecaster");
		final List<VisualObservation> visualObservations = translate(visualObservationsDTO);
		Classifier classifier;
		try {
			classifier = Classifier.forName(classifierName, null);
			final Spot spot = spotDAO.getSpotById(spotId);
			final WekaForecaster forecaster = new WekaForecaster(classifier,
					dataSetGenerationStrategy, dataSetStrategyOptions,
					waveWatchSystem, visualObservations, spot);
			spot.addForecaster(forecaster);
			final Integer forecasterId = forecastDAO.save(forecaster);
			final String correlation = forecaster.getEvaluation().get(
					"correlation");
			final String mae = forecaster.getEvaluation().get(
					"meanAbsoluteError");

			log.info("Weka Forecaster(Id=" + forecasterId
					+ ") trained:   Correlation: " + correlation
					+ "Mean Absolute error.: " + mae);

			return new WekaForecasterEvaluationDTO(Double
					.parseDouble(correlation), Double.parseDouble(mae),
					forecasterId, forecaster.getClassifier().getClass()
							.getName(), forecaster.getTrainningOptions());
		} catch (final Exception e) {
			log.error("Error creating weka forecaster", e);
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
			final List<VisualObservationDTO> visualObservationsDTO) {
		final List<VisualObservation> observations = new ArrayList<VisualObservation>();
		for (final VisualObservationDTO visualObservationDTO : visualObservationsDTO) {
			final VisualObservation visualObservation = new VisualObservation(
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
	private void validateName(final String setName) throws NeuralitoException {
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
	private void validateDescription(final String setDescription)
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
	private void validateFile(final File file) {
		// TODO perform validation

	}

	/**
	 * Validation
	 * 
	 * @param unit
	 * @throws NeuralitoException
	 */
	private void validateUnit(final Unit unit) throws NeuralitoException {
		if (unit == null)
			throw new NeuralitoException(
					ErrorCode.VISUAL_OBSERVATION_SET_UNIT_CANNOT_BE_NULL);

	}

	public void setDataSetGenerationStrategy(
			final DataSetGenerationStrategy dataSetGenerationStrategy) {
		this.dataSetGenerationStrategy = dataSetGenerationStrategy;
	}

	public void setClassifierName(final String classifierName) {
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

	public void setWaveWatchSystem(final WaveWatchSystem waveWatchSystem) {
		this.waveWatchSystem = waveWatchSystem;
	}

	/**
	 * @throws NeuralitoException
	 * @see edu.unicen.surfforecaster.common.services.ForecastService#getWekaForecasters(java.lang.Integer)
	 */
	@Override
	@Transactional
	public List<WekaForecasterEvaluationDTO> getWekaForecasters(
			final Integer spotId) throws NeuralitoException {
		validateSpotExists(spotId);
		final Spot spot = spotDAO.getSpotById(spotId);
		final Collection<Forecaster> forecasters = spot.getForecasters();
		final List<WekaForecasterEvaluationDTO> dtos = new ArrayList<WekaForecasterEvaluationDTO>();
		for (final Iterator iterator = forecasters.iterator(); iterator
				.hasNext();) {
			final Forecaster forecaster = (Forecaster) iterator.next();
			if (forecaster instanceof WekaForecaster) {
				final WekaForecaster wekaForecaster = (WekaForecaster) forecaster;
				final String correlation = wekaForecaster.getEvaluation().get(
						"correlation");
				final String mae = wekaForecaster.getEvaluation().get(
						"meanAbsoluteError");

				log.info("Retrieving Weka Forecaster(Id="
						+ wekaForecaster.getId() + ") :   Correlation: "
						+ correlation + "Mean Absolute error.: " + mae);

				dtos.add(new WekaForecasterEvaluationDTO(Double
						.parseDouble(correlation), Double.parseDouble(mae),
						wekaForecaster.getId(), wekaForecaster.getClassifier()
								.getClass().getName(), wekaForecaster
								.getTrainningOptions()));
			}
		}
		return dtos;
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.ForecastService#getSimpleForecastersForSpot(java.lang.Integer)
	 */
	@Override
	@Transactional
	public List<SimpleForecasterDTO> getSimpleForecastersForSpot(
			final Integer spotId) throws NeuralitoException {
		validateSpotExists(spotId);
		final Spot spotById = spotDAO.getSpotById(spotId);
		final Collection<Forecaster> forecasters = spotById.getForecasters();
		final List<SimpleForecasterDTO> simpleForecastersForSpot = new ArrayList<SimpleForecasterDTO>();
		for (final Forecaster forecaster : forecasters) {
			if (forecaster instanceof SimpleForecaster) {
				final SimpleForecaster sForecaster = (SimpleForecaster) forecaster;
				simpleForecastersForSpot.add(sForecaster.getDTO());
			}
		}
		return simpleForecastersForSpot;
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.ForecastService#getAvailableWekaArchive(edu.unicen.surfforecaster.common.services.dto.PointDTO)
	 */
	@Override
	public List<ArchiveDetailDTO> getAvailableWekaArchive(final PointDTO point)
			throws NeuralitoException {
		final Collection<ArchiveDetail> archiveDetail = waveWatchSystem
				.getArchiveDetail(new Point(point.getLatitude(), point
						.getLongitude()));
		// ArrayList<>
		for (final ArchiveDetail archiveDetail2 : archiveDetail) {

		}
		return null;
	}

	/**
	 * @throws NeuralitoException
	 * @see edu.unicen.surfforecaster.common.services.ForecastService#removeForecaster(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void removeForecaster(final Integer forecasterId)
			throws NeuralitoException {
		validateForecasterExists(forecasterId);
		forecastDAO.removeForecaster(forecasterId);
	}
}
