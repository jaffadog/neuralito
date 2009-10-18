/**
 * 
 */
package edu.unicen.surfforecaster.server.services;

import java.util.ArrayList;
import java.util.Collection;
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
import edu.unicen.surfforecaster.server.domain.entity.Spot;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecaster;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3Forecaster;

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
		// validateGridPoint(gridPointDTO);
		final Point gridPoint = new Point(gridPointDTO.getLatitude(),
				gridPointDTO.getLongitude());
		final Spot spot = spotDAO.getSpotById(spotId);
		final WW3Forecaster forecaster = new WW3Forecaster(modelName,
				gridPoint, spot.getLocation());
		final Integer id = forecastDAO.save(forecaster);
		spot.addForecaster(forecaster);
		spotDAO.saveSpot(spot);

		return id;
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.ForecastService#getForecasts(java.lang.Integer)
	 */
	@Override
	public List<ForecastDTO> getForecasts(final Integer forecasterId)
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
	public List<PointDTO> getNearbyGridPoints(final double latitude,
			final double longitude) throws NeuralitoException {
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

}
