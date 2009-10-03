/**
 * 
 */
package edu.unicen.surfforecaster.server.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ForecastService;
import edu.unicen.surfforecaster.common.services.SpotService;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.server.dao.ForecastDAO;
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
	 * The spot service.
	 */
	private SpotService spotService;
	/**
	 * The forecast dao.
	 */
	private ForecastDAO forecastDAO;

	/**
	 * 
	 */
	public ForecastServiceImplementation() {
		System.out.println("ME LLAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); // TODO
																			// Auto-generated
																			// constructor
																			// stub
		System.out.println("ME LLAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); // TODO
																			// Auto-generated
																			// constructor
																			// stub
		System.out.println("ME LLAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); // TODO
																			// Auto-generated
																			// constructor
																			// stub
		System.out.println("ME LLAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); // TODO
																			// Auto-generated
																			// constructor
																			// stub
		System.out.println("ME LLAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); // TODO
																			// Auto-generated
																			// constructor
																			// stub
		System.out.println("ME LLAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); // TODO
																			// Auto-generated
																			// constructor
																			// stub
		System.out.println("ME LLAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); // TODO
																			// Auto-generated
																			// constructor
																			// stub
		System.out.println("ME LLAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); // TODO
																			// Auto-generated
																			// constructor
																			// stub
		System.out.println("ME LLAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); // TODO
																			// Auto-generated
																			// constructor
																			// stub
		System.out.println("ME LLAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); // TODO
																			// Auto-generated
																			// constructor
																			// stub
		System.out.println("ME LLAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); // TODO
																			// Auto-generated
																			// constructor
																			// stub
		System.out.println("ME LLAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); // TODO
																			// Auto-generated
																			// constructor
																			// stub
		System.out.println("ME LLAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); // TODO
																			// Auto-generated
																			// constructor
																			// stub
		System.out.println("ME LLAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); // TODO
																			// Auto-generated
																			// constructor
																			// stub
		System.out.println("ME LLAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); // TODO
																			// Auto-generated
																			// constructor
																			// stub
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.ForecastService#createWW3Forecaster(java.lang.Integer,
	 *      edu.unicen.surfforecaster.common.services.dto.PointDTO)
	 */
	@Override
	public Integer createWW3Forecaster(final Integer spotId,
			final PointDTO gridPoint) throws NeuralitoException {
		validateSpotExists(spotId);
		validateGridPoint(gridPoint);
		final ArrayList<Point> list = new ArrayList<Point>();
		list.add(new Point(gridPoint.getLatitude(), gridPoint.getLongitude()));
		final SpotDTO spot = spotService.getSpotById(spotId);
		final WW3Forecaster forecaster = new WW3Forecaster(list, new Point(spot
				.getLatitude(), spot.getLongitude()));
		final Integer id = forecastDAO.save(forecaster);

		return id;
	}

	/**
	 * @param gridPoint
	 */
	private void validateGridPoint(final PointDTO gridPoint) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param spotId
	 */
	private void validateSpotExists(final Integer spotId) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.ForecastService#getForecasts(java.lang.Integer)
	 */
	@Override
	public List<ForecastDTO> getForecasts(final Integer forecasterId)
			throws NeuralitoException {
		final Forecaster forecaster = forecastDAO
				.getForecasterById(forecasterId);
		final Collection<Forecast> forecasts = forecaster.getForecasts();
		final List<ForecastDTO> forecastsDtos = new ArrayList<ForecastDTO>();
		for (final Iterator<Forecast> iterator = forecasts.iterator(); iterator
				.hasNext();) {
			final Forecast forecast = iterator.next();
			forecastsDtos.add(forecast.getDTO());
		}
		return forecastsDtos;
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.ForecastService#getWW3GridPoints(double,
	 *      double)
	 */
	@Override
	public Collection<PointDTO> getWW3GridPoints(final double d, final double e)
			throws NeuralitoException {
		final Collection<Point> surroundingGridPoints = WW3Forecaster
				.getSurroundingGridPoints(new Point(d, e));
		final Collection<PointDTO> pointsDTOs = new ArrayList<PointDTO>();
		for (final Iterator iterator = surroundingGridPoints.iterator(); iterator
				.hasNext();) {
			final Point point = (Point) iterator.next();
			pointsDTOs.add(point.getDTO());
		}
		return pointsDTOs;
	}

	/**
	 * @param spotService
	 *            the spotService to set
	 */
	public void setSpotService(final SpotService spotService) {
		this.spotService = spotService;
	}

	/**
	 * @param forecastDAO
	 *            the forecastDAO to set
	 */
	public void setForecastDAO(final ForecastDAO forecastDAO) {
		this.forecastDAO = forecastDAO;
	}

}
