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
 * @author esteban
 * 
 */
public interface ForecastService {

	/**
	 * @param d
	 * @param e
	 * @return
	 */
	Collection<PointDTO> getWW3GridPoints(double d, double e)
			throws NeuralitoException;

	/**
	 * @param spot1Id
	 * @param gridPoint
	 * @return
	 */
	Integer createWW3Forecaster(Integer spot1Id, PointDTO gridPoint)
			throws NeuralitoException;

	/**
	 * @param forecasterId
	 * @return
	 */
	List<ForecastDTO> getForecasts(Integer forecasterId)
			throws NeuralitoException;

}
