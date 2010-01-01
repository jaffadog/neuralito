/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.surfforecaster.server.domain.entity.Forecaster;
import edu.unicen.surfforecaster.server.domain.entity.WW3Forecaster;
import edu.unicen.surfforecaster.server.domain.entity.WekaForecaster;
import edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystem;
import edu.unicen.surfforecaster.server.domain.weka.strategy.DataSetGenerationStrategy;

/**
 * @author esteban
 * 
 */
public class ForecastDAOHibernateImpl extends HibernateDaoSupport implements
		ForecastDAO {

	private WaveWatchSystem waveWatchSystem;


	public void setWaveWatchSystem(WaveWatchSystem waveWatchSystem) {
		this.waveWatchSystem = waveWatchSystem;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.ForecastDAO#save(edu.unicen.surfforecaster.server.domain.entity.forecast.WW3Forecaster)
	 */
	@Override
	public Integer save(final Forecaster forecaster) {
		getHibernateTemplate().save(forecaster);
		return forecaster.getId();

	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.ForecastDAO#save(edu.unicen.surfforecaster.server.domain.entity.forecast.WW3Forecaster)
	 */
	public Integer save(
			final DataSetGenerationStrategy dataSetGenerationStrategy) {
		Serializable save = getHibernateTemplate().save(
				dataSetGenerationStrategy);
		return 1;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.ForecastDAO#getForecasterById(java.lang.Integer)
	 */
	@Override
	public Forecaster getForecasterById(final Integer forecasterId) {

		final Forecaster forecaster = (Forecaster) getHibernateTemplate().get(
				Forecaster.class, forecasterId);
		if (forecaster instanceof WW3Forecaster) {
			WW3Forecaster ww3Forecaster = (WW3Forecaster) forecaster;
			Field declaredField;
			try {
				declaredField = WW3Forecaster.class
						.getDeclaredField("modelName");
				declaredField.setAccessible(true);
				String string = (String) declaredField.get(ww3Forecaster);
				declaredField.setAccessible(false);
				declaredField = WW3Forecaster.class.getDeclaredField("model");
				declaredField.setAccessible(true);
				declaredField.set(ww3Forecaster, waveWatchSystem);
				declaredField.setAccessible(false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (forecaster instanceof WekaForecaster) {
			WekaForecaster wekaForecaster = (WekaForecaster) forecaster;
			Field declaredField;
			try {
				declaredField = WekaForecaster.class
						.getDeclaredField("waveWatchModelName");
				declaredField.setAccessible(true);
				String string = (String) declaredField.get(wekaForecaster);
				declaredField.setAccessible(false);
				declaredField = WekaForecaster.class
						.getDeclaredField("waveWatchModel");
				declaredField.setAccessible(true);
				declaredField.set(wekaForecaster, waveWatchSystem);
				declaredField.setAccessible(false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return forecaster;
	}

}
