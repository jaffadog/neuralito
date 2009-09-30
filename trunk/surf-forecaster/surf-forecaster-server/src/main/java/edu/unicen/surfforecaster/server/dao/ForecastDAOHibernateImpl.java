/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecaster;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3Forecaster;

/**
 * @author esteban
 * 
 */
public class ForecastDAOHibernateImpl extends HibernateDaoSupport implements
		ForecastDAO {

	/**
	 * @see edu.unicen.surfforecaster.server.dao.ForecastDAO#save(edu.unicen.surfforecaster.server.domain.entity.forecast.WW3Forecaster)
	 */
	@Override
	public Integer save(final WW3Forecaster forecaster) {
		getHibernateTemplate().save(forecaster);
		return forecaster.getId();

	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.ForecastDAO#getForecasterById(java.lang.Integer)
	 */
	@Override
	public Forecaster getForecasterById(final Integer forecasterId) {

		return (Forecaster) getHibernateTemplate().get(Forecaster.class,
				forecasterId);
	}

}
