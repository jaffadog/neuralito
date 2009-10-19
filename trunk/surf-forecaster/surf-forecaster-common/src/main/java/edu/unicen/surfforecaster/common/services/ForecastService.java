/**
 * 
 */
package edu.unicen.surfforecaster.common.services;

import java.util.GregorianCalendar;
import java.util.List;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;

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
	public List<ForecastDTO> getForecasts(Integer forecasterId)
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

}
