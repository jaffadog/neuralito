/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecaster;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3Forecaster;

/**
 * @author esteban
 * 
 */
public interface ForecasterDAO {

	/**
	 * @param forecaster
	 * @return
	 */
	Integer save(WW3Forecaster forecaster);

	/**
	 * @param forecasterId
	 * @return
	 */
	Forecaster getForecasterById(Integer forecasterId);

}
