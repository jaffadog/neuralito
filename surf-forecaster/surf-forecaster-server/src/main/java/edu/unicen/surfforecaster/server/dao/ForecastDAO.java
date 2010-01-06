/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import edu.unicen.surfforecaster.server.domain.entity.Forecaster;
import edu.unicen.surfforecaster.server.domain.entity.SimpleForecaster;

/**
 * @author esteban
 * 
 */
public interface ForecastDAO {

	/**
	 * @param forecaster
	 * @return
	 */
	public Integer save(Forecaster forecaster);

	/**
	 * @param forecasterId
	 * @return
	 */
	public Forecaster getForecasterById(Integer forecasterId);

}
