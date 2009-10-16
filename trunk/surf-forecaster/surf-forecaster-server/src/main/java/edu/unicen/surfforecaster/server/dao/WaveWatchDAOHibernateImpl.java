/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast2;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastArchive;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastPoints;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.LatestForecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ValidGridPoints;
import edu.unicen.surfforecaster.server.services.ForecastArch;

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

	@Override
	public void saveDirect(final LatestForecast latestForecasts) {
		final Connection connection = this.getSession().connection();
		try {
			connection.setAutoCommit(false);
		} catch (final SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (final Iterator<Forecast2> iterator = latestForecasts
				.getLatestForecast2().iterator(); iterator.hasNext();) {
			final Forecast2 forecast = iterator.next();
			try {
				final Statement st = connection.createStatement();
				st.execute("INSERT INTO forecast2" + " VALUES (0,'20/01/08',"
						+ forecast.getCombinedHeight()
						+ ",0,0,0,0,0,0,0,0,0,0,0)");
			} catch (final SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		try {
			connection.commit();
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.WaveWatchDAO#save(edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.LatestForecast)
	 */
	@Override
	public void save(final LatestForecast latestForecasts) {
		final Session session = this.getSession();

		final Transaction tx = session.beginTransaction();
		final Collection<Forecast> forecasts = latestForecasts
				.getLatestForecast();
		int i = 0;
		for (final Iterator<Forecast> iterator = forecasts.iterator(); iterator
				.hasNext();) {
			final Forecast forecast = iterator.next();
			session.save(forecast);
			if (i % 40 == 0) { // 40, same as the JDBC batch size
				// flush a batch of inserts and release memory:
				session.flush();
				session.clear();
			}
			i++;

		}
		getHibernateTemplate().save(latestForecasts);
		// flush a batch of inserts and release memory:
		tx.commit();

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
	public void save(final ValidGridPoints grid) {
		final Session session = this.getSession();
		final Transaction tx = session.beginTransaction();
		final Collection<Point> validGridPoints2 = grid.getValidGridPoints();
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
		getHibernateTemplate().save(grid);
		// flush a batch of inserts and release memory:
		tx.commit();

	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.WaveWatchDAO#saveDirect(java.util.List)
	 */
	@Override
	public void saveDirect(final List forecasts) {
		// dropIndex();
		final long init = System.currentTimeMillis();
		final Connection connection = this.getSession().connection();
		try {
			connection.setAutoCommit(false);
		} catch (final SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			final Statement st = connection.createStatement();

			for (final Iterator iterator = forecasts.iterator(); iterator
					.hasNext();) {
				final ForecastArch forecast = (ForecastArch) iterator.next();

				st
						.addBatch("INSERT INTO waveWatchForecastArchive3"
								+ " VALUES ('"
								+ forecast.getIssuedDate().get(Calendar.YEAR)
								+ "-"
								+ forecast.getIssuedDate().get(Calendar.MONTH)
								+ "-"
								+ forecast.getIssuedDate().get(
										Calendar.DAY_OF_MONTH)
								+ "',"
								+ forecast.getValidTime()
								+ ","
								+ forecast.getLatitude()
								+ ","
								+ forecast.getLongitude()
								+ ","
								+ forecast.getWindWaveHeight()
								+ ","
								+ forecast.getWindWavePeriod()
								+ ","
								+ forecast.getWindWaveDirection()
								+ ","
								+ forecast.getSwellWaveHeight()
								+ ","
								+ forecast.getSwellWavePeriod()
								+ ","
								+ forecast.getSwellWaveDirection()
								+ ","
								+ forecast.getCombinedWaveHeight()
								+ ","
								+ forecast.getPeakWavePeriod()
								+ ","
								+ forecast.getPeakWaveDirection()
								+ ","
								+ forecast.getWindSpeed()
								+ ","
								+ forecast.getWindDirection()
								+ ","
								+ forecast.getWindU()
								+ ","
								+ forecast.getWindV() + ")");
			}
			st.executeBatch();

			st.close();
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			connection.commit();
			connection.close();
			final long end = System.currentTimeMillis();
			System.out.println("Saved " + forecasts.size() + " forecasts");
			System.out.println("Elapsed time: " + (end - init) / 1000);
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// createIndex();

	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.WaveWatchDAO#getForecasts(float,
	 *      float, java.util.GregorianCalendar, java.util.GregorianCalendar)
	 */
	@Override
	public List<ForecastArch> getForecasts(final float lat, final float lon,
			final GregorianCalendar from, final GregorianCalendar to) {
		final long init = System.currentTimeMillis();
		final List<ForecastArch> forecasts = new ArrayList<ForecastArch>();
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			final ResultSet result = st
					.executeQuery("select * from wavewatchforecastarchive where latitude = "
							+ lat + " AND longitude = " + lon + "");
			while (result.next() != false) {
				final GregorianCalendar issuedDate = new GregorianCalendar(
						result.getDate("issuedDate").getYear(), result.getDate(
								"issuedDate").getMonth(), result.getDate(
								"issuedDate").getDay());
				final int validTime = result.getInt("validTime");
				final float latitude = result.getFloat("latitude");
				final float longitude = result.getFloat("longitude");
				final float windWaveHeight = result.getFloat("windWaveHeight");
				final float windWavePeriod = result.getFloat("windWavePeriod");
				final float windWaveDirection = result
						.getFloat("windWaveDirection");
				final float swellWaveHeight = result
						.getFloat("swellWaveHeight");
				final float swellWaveDirection = result
						.getFloat("swellWaveDirection");
				final float swellWavePeriod = result
						.getFloat("swellWavePeriod");
				final float combinedWaveHeight = result
						.getFloat("combinedWaveHeight");
				final float peakWavePeriod = result.getFloat("peakWavePeriod");
				final float peakWaveDirection = result
						.getFloat("peakWaveDirection");
				final float windSpeed = result.getFloat("windSpeed");
				final float windDirection = result.getFloat("windDirection");
				final float windU = result.getFloat("windU");
				final float windV = result.getFloat("windV");
				final ForecastArch arch = new ForecastArch(issuedDate,
						validTime, latitude, longitude, windWaveHeight,
						windWavePeriod, windWaveDirection, swellWaveHeight,
						swellWavePeriod, swellWaveDirection,
						combinedWaveHeight, peakWavePeriod, peakWaveDirection,
						windSpeed, windDirection, windU, windV);
				forecasts.add(arch);
			}

			st.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		final long end = System.currentTimeMillis();
		System.out.println("Readed: " + forecasts.size() + " forecasts.");
		System.out.println("Elapsed Time: " + (end - init) / 1000);
		return forecasts;
	}

	private void dropIndex() {
		System.out.println("Droping Index");
		final long init = System.currentTimeMillis();
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			st
					.execute("ALTER TABLE test.waveWatchForecastArchive2 DROP INDEX asdf");
			st.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		final long end = System.currentTimeMillis();
		System.out
				.println("Elapsed Time To drop index: " + (end - init) / 1000);

	}

	private void createIndex() {
		System.out.println("Creating Index");
		final long init = System.currentTimeMillis();
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			st
					.execute("ALTER TABLE test.waveWatchForecastArchive2 ADD INDEX asdf(latitude,longitude)");
			st.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		final long end = System.currentTimeMillis();
		System.out
				.println("Elapsed Time To drop index: " + (end - init) / 1000);

	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.WaveWatchDAO#archiveLatestForecasts()
	 */
	@Override
	public void archiveLatestForecasts() {
		System.out.println("Archiving Forecasts");
		final long init = System.currentTimeMillis();
		try {
			final Connection connection = this.getSession().connection();

			final Statement st = connection.createStatement();
			st
					.execute("insert into waveWatchForecastArchive select * from waveWatchLatestForecast where validTime=3 OR validTime=0;");
			st.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		final long end = System.currentTimeMillis();
		System.out.println("Elapsed Time To archive forecast : " + (end - init)
				/ 1000);

	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.WaveWatchDAO#insertIntoLatestForecastFromFile(java.io.File)
	 */
	@Override
	public void insertIntoLatestForecastFromFile(final File textFile) {
		System.out
				.println("Inserting data from: " + textFile.getAbsolutePath());
		final long init = System.currentTimeMillis();
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			st.execute("DROP TABLE waveWatchLatestForecast");
			st
					.execute("CREATE TABLE `waveWatchLatestForecast` (  `issuedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  `validTime` tinyint(4) NOT NULL,  `latitude` float NOT NULL,  `longitude` float NOT NULL,  `windWaveHeight` float DEFAULT NULL,  `windWavePeriod` float DEFAULT NULL,  `windWaveDirection` float DEFAULT NULL,  `swellWaveHeight` float DEFAULT NULL,  `swellWavePeriod` float DEFAULT NULL,  `swellWaveDirection` float DEFAULT NULL,  `combinedWaveHeight` float DEFAULT NULL,  `peakWavePeriod` float DEFAULT NULL,  `peakWaveDirection` float DEFAULT NULL,  `windSpeed` float DEFAULT NULL,  `windDirection` float DEFAULT NULL,  `windU` float DEFAULT NULL,  `windV` float DEFAULT NULL) ENGINE=MyISAM DEFAULT CHARSET=utf8;");
			final String query = "LOAD DATA INFILE '"
					+ textFile.getAbsolutePath().replace('\\', '/')
					+ "' INTO TABLE waveWatchLatestForecast FIELDS TERMINATED BY ','  LINES STARTING BY 'x' TERMINATED BY 'e'";
			st.execute(query);
			st
					.execute("ALTER TABLE waveWatchLatestForecast ADD INDEX location(latitude, longitude)");
			st.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		final long end = System.currentTimeMillis();
		System.out.println("Elapsed Time To insert data from file: "
				+ (end - init) / 1000);

	}
}
