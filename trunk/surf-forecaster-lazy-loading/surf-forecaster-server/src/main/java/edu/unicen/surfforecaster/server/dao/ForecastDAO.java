/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import edu.unicen.surfforecaster.server.domain.entity.Forecaster;
import edu.unicen.surfforecaster.server.domain.entity.WW3Forecaster;

/**
 * @author esteban
 * 
 */
public interface ForecastDAO {

	/**
	 * @param forecaster
	 * @return
	 */
	public Integer save(WW3Forecaster forecaster);

	/**
	 * @param forecasterId
	 * @return
	 */
	public Forecaster getForecasterById(Integer forecasterId);

}
