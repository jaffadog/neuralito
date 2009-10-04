/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastArchive;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastPoints;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.LatestForecast;

/**
 * @author esteban
 * 
 */
public class WaveWatchDAOHibernateImpl extends HibernateDaoSupport implements
		WaveWatchDAO {

	/**
	 * @see edu.unicen.surfforecaster.server.dao.WaveWatchDAO#getForecastForDate(java.util.Date,
	 *      java.util.Date, java.util.Collection)
	 */
	@Override
	public Collection<Forecast> getForecastForDate(final Date from,
			final Date to, final Collection<Point> gridPoints) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.WaveWatchDAO#getLatestForecast(java.util.Collection)
	 */
	@Override
	public Collection<Forecast> getLatestForecast(
			final Collection<Point> gridPoints) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.WaveWatchDAO#getLatestForecast()
	 */
	@Override
	public LatestForecast getLatestForecast() {
		final List latestForecast = getHibernateTemplate().loadAll(
				LatestForecast.class);
		if (latestForecast != null) {
			if (latestForecast.size() > 0)
				return (LatestForecast) latestForecast.get(0);
		}
		return null;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.WaveWatchDAO#getWW3Archive()
	 */
	@Override
	public ForecastArchive getWW3Archive() {
		final List forecastArchive = getHibernateTemplate().loadAll(
				ForecastArchive.class);
		if (forecastArchive != null) {
			if (forecastArchive.size() > 0)
				return (ForecastArchive) forecastArchive.get(0);
		}
		return null;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.WaveWatchDAO#save(edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.LatestForecast)
	 */
	@Override
	public void save(final LatestForecast latestForecasts) {
		getHibernateTemplate().save(latestForecasts);

	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.WaveWatchDAO#save(edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.LatestForecast)
	 */
	@Override
	public void update(final LatestForecast latestForecasts) {
		getHibernateTemplate().update(latestForecasts);

	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.WaveWatchDAO#save(edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.LatestForecast)
	 */
	@Override
	public void update(final ForecastArchive ww3archive) {
		getHibernateTemplate().update(ww3archive);

	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.WaveWatchDAO#save(edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastArchive)
	 */
	@Override
	public void save(final ForecastArchive ww3archive) {
		getHibernateTemplate().save(ww3archive);

	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.WaveWatchDAO#save(edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastPoints)
	 */
	@Override
	public void save(final ForecastPoints points) {
		getHibernateTemplate().save(points);

	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.WaveWatchDAO#getForecastPoints()
	 */
	@Override
	public ForecastPoints getForecastPoints() {
		final List forecastPoints = getHibernateTemplate().loadAll(
				ForecastPoints.class);
		if (forecastPoints != null) {
			if (forecastPoints.size() > 0)
				return (ForecastPoints) forecastPoints.get(0);
		}
		return null;
	}

}
