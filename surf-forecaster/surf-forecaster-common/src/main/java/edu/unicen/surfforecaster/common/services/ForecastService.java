/**
 * 
 */
package edu.unicen.surfforecaster.common.services;

import java.util.Collection;
import java.util.List;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;

/**
 * Forecast related services.
 * 
 * @author esteban
 * 
 */
public interface ForecastService {

	/**
	 * Obtains all the wave watch 3 grid points that surrounds the given point
	 * 
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	Collection<PointDTO> getWW3GridPoints(double latitude, double longitude)
			throws NeuralitoException;

	/**
	 * Creates a wave watch 3 forecaster, for the selected grid point and associates it with the given
	 * spot.
	 * 
	 * @param spot1Id
	 * @param gridPoint
	 * @return
	 */
	Integer createWW3Forecaster(Integer spot1Id, PointDTO gridPoint)
			throws NeuralitoException;

	/**
	 * Obtain the forecast of the given forecaster.
	 * 
	 * @param forecasterId
	 * @return
	 */
	List<ForecastDTO> getForecasts(Integer forecasterId)
			throws NeuralitoException;

}
