/**
 * 
 */
package edu.unicen.surfforecaster.server.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ForecastService;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.server.dao.ForecastDAO;
import edu.unicen.surfforecaster.server.dao.SpotDAO;
import edu.unicen.surfforecaster.server.domain.WaveWatchModel;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.Forecaster;
import edu.unicen.surfforecaster.server.domain.entity.Point;
import edu.unicen.surfforecaster.server.domain.entity.Spot;
import edu.unicen.surfforecaster.server.domain.entity.WW3Forecaster;

/**
 * @author esteban
 * 
 */
public class ForecastServiceImplementation implements ForecastService {
	/**
	 * The forecast dao.
	 */
	private ForecastDAO forecastDAO;

	Map<String, WaveWatchModel> models;

	private SpotDAO spotDAO;

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
		final String modelName = "GlobalModel";
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
		final WW3Forecaster forecaster = new WW3Forecaster(modelName, point,
				spot.getLocation());
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
		final Forecaster forecaster = forecastDAO
				.getForecasterById(forecasterId);
		final Collection<Forecast> forecasts = forecaster.getLatestForecasts();
		final List<ForecastDTO> forecastsDtos = new ArrayList<ForecastDTO>();
		for (final Iterator<Forecast> iterator = forecasts.iterator(); iterator
				.hasNext();) {
			final Forecast forecast = iterator.next();
			forecastsDtos.add(forecast.getDTO());
		}
		return forecastsDtos;
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.ForecastService#getNearbyGridPoints(double,
	 *      double)
	 */
	@Override
	public List<PointDTO> getNearbyGridPoints(final float latitude,
			final float longitude) throws NeuralitoException {
		final WaveWatchModel model = models.get("GlobalModel");
		final List<Point> surroundingGridPoints = model
				.getNearbyGridPoints(new Point(latitude, longitude));
		final List<PointDTO> pointsDTOs = new ArrayList<PointDTO>();
		for (final Iterator iterator = surroundingGridPoints.iterator(); iterator
				.hasNext();) {
			final Point point = (Point) iterator.next();
			pointsDTOs.add(point.getDTO());
		}
		return pointsDTOs;
	}

	/**
	 * @param forecastDAO
	 *            the forecastDAO to set
	 */
	public void setForecastDAO(final ForecastDAO forecastDAO) {
		this.forecastDAO = forecastDAO;
	}

	/**
	 * @param gridPoint
	 */
	private void validateGridPoint(final PointDTO gridPoint)
			throws NeuralitoException {
		if (gridPoint == null)
			throw new NeuralitoException(ErrorCode.GRID_POINT_CANNOT_BE_NULL);
		final WaveWatchModel model = models.get("GlobalModel");
		if (model.isGridPoint(new Point(gridPoint.getLatitude(), gridPoint
				.getLongitude())))
			return;
		else
			throw new NeuralitoException(ErrorCode.GRID_POINT_INVALID);

	}

	/**
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
	 * @param spotDAO
	 *            the spotDAO to set
	 */
	public void setSpotDAO(final SpotDAO spotDAO) {
		this.spotDAO = spotDAO;
	}

	/**
	 * @param models
	 *            the models to set
	 */
	public void setModels(final Map<String, WaveWatchModel> models) {
		this.models = models;
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
		validateForecasterExists(forecasterId);
		final Forecaster forecaster = forecastDAO
				.getForecasterById(forecasterId);
		final List<ForecastDTO> forecastsDTOs = new ArrayList<ForecastDTO>();
		final Collection<Forecast> forecasts = forecaster.getArchivedForecasts(
				from.getTime(), to.getTime());
		for (final Iterator iterator = forecasts.iterator(); iterator.hasNext();) {
			final Forecast forecast = (Forecast) iterator.next();
			forecastsDTOs.add(forecast.getDTO());
		}
		return forecastsDTOs;
	}

	/**
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

}
