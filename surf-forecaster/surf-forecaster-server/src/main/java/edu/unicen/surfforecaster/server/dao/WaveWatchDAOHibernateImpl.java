/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastArchive;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastPoints;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.LatestForecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ValidGridPoints;

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

	/**
	 * @see edu.unicen.surfforecaster.server.dao.WaveWatchDAO#getValidGridPoints()
	 */
	@Override
	public ValidGridPoints getValidGridPoints() {
		final List validGridPoints = getHibernateTemplate().loadAll(
				ValidGridPoints.class);
		if (validGridPoints != null) {
			if (validGridPoints.size() > 0)
				return (ValidGridPoints) validGridPoints.get(0);
		}
		return null;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.WaveWatchDAO#save(edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ValidGridPoints)
	 */
	@Override
	public void save(final ValidGridPoints validGridPoints) {
		final Session session = this.getSession();
		final Transaction tx = session.beginTransaction();
		final Collection<Point> validGridPoints2 = validGridPoints
				.getValidGridPoints();
		int i = 0;
		for (final Iterator iterator = validGridPoints2.iterator(); iterator
				.hasNext();) {
			final Point point = (Point) iterator.next();
			session.save(point);
			if (i % 40 == 0) { // 40, same as the JDBC batch size
				// flush a batch of inserts and release memory:
				session.flush();
				session.clear();
			}
			i++;

		}
		getHibernateTemplate().save(validGridPoints);
		// flush a batch of inserts and release memory:
		tx.commit();

	}

}
