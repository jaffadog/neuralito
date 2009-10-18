/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.surfforecaster.server.domain.WaveWatchModel;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecaster;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3Forecaster;

/**
 * @author esteban
 * 
 */
public class ForecastDAOHibernateImpl extends HibernateDaoSupport implements
		ForecastDAO {

	private Map<String, WaveWatchModel> models;

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

		final Forecaster forecaster = (Forecaster) getHibernateTemplate().get(
				Forecaster.class, forecasterId);
		if (forecaster instanceof WW3Forecaster) {
			((WW3Forecaster) forecaster).setModel(models
					.get(((WW3Forecaster) forecaster).getModelName()));
		}
		return forecaster;
	}

	/**
	 * @param models
	 *            the models to set
	 */
	public void setModels(final Map<String, WaveWatchModel> models) {
		this.models = models;
	}

}
