/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import java.lang.reflect.Field;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.surfforecaster.server.domain.entity.Forecaster;
import edu.unicen.surfforecaster.server.domain.entity.SimpleForecaster;
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

	public void setWaveWatchSystem(final WaveWatchSystem waveWatchSystem) {
		this.waveWatchSystem = waveWatchSystem;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.ForecastDAO#save(edu.unicen.surfforecaster.server.domain.entity.SimpleForecaster.WW3Forecaster)
	 */
	@Override
	public Integer save(final Forecaster forecaster) {
		getHibernateTemplate().save(forecaster);
		return forecaster.getId();

	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.ForecastDAO#save(edu.unicen.surfforecaster.server.domain.entity.SimpleForecaster.WW3Forecaster)
	 */
	public void save(final DataSetGenerationStrategy dataSetGenerationStrategy) {
		getHibernateTemplate().save(dataSetGenerationStrategy);
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.ForecastDAO#getForecasterById(java.lang.Integer)
	 */
	@Override
	public Forecaster getForecasterById(final Integer forecasterId) {

		final Forecaster forecaster = (Forecaster) getHibernateTemplate().get(
				Forecaster.class, forecasterId);
		if (forecaster instanceof SimpleForecaster) {
			final SimpleForecaster simpleForecaster = (SimpleForecaster) forecaster;
			Field declaredField;
			try {
				declaredField = SimpleForecaster.class
						.getDeclaredField("modelName");
				declaredField.setAccessible(true);
				final String string = (String) declaredField
						.get(simpleForecaster);
				declaredField.setAccessible(false);
				declaredField = SimpleForecaster.class
						.getDeclaredField("model");
				declaredField.setAccessible(true);
				declaredField.set(simpleForecaster, waveWatchSystem);
				declaredField.setAccessible(false);
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (forecaster instanceof WekaForecaster) {
			final WekaForecaster wekaForecaster = (WekaForecaster) forecaster;
			Field declaredField;
			try {
				declaredField = WekaForecaster.class
						.getDeclaredField("waveWatchModelName");
				declaredField.setAccessible(true);
				final String string = (String) declaredField
						.get(wekaForecaster);
				declaredField.setAccessible(false);
				declaredField = WekaForecaster.class
						.getDeclaredField("waveWatch");
				declaredField.setAccessible(true);

				declaredField.set(wekaForecaster, waveWatchSystem);
				declaredField.setAccessible(false);
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return forecaster;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.ForecastDAO#removeForecaster(java.lang.Integer)
	 */
	@Override
	public void removeForecaster(final Integer forecasterId) {
		final Forecaster forecasterById = getForecasterById(forecasterId);
		getHibernateTemplate().delete(forecasterById);
	}

}
