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
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastArch;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ValidGridPoints;

/**
 * @author esteban
 * 
 */
public class WaveWatchDAOHibernateImpl extends HibernateDaoSupport implements
		WaveWatchDAO {

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
	 * @see edu.unicen.surfforecaster.server.dao.WaveWatchDAO#getLatestForecast(edu.unicen.surfforecaster.server.domain.entity.forecaster.Point)
	 */
	@Override
	public Collection<Forecast> getLatestForecast(final Point gridPoint) {
		// TODO Auto-generated method stub
		return null;
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
					.execute("CREATE TABLE `waveWatchLatestForecast` (  `issuedDate` DATETIME NOT NULL,  `validTime` tinyint(4) unsigned NOT NULL,  `latitude` float NOT NULL,  `longitude` float NOT NULL,  `windWaveHeight` float DEFAULT NULL,  `windWavePeriod` float DEFAULT NULL,  `windWaveDirection` float DEFAULT NULL,  `swellWaveHeight` float DEFAULT NULL,  `swellWavePeriod` float DEFAULT NULL,  `swellWaveDirection` float DEFAULT NULL,  `combinedWaveHeight` float DEFAULT NULL,  `peakWavePeriod` float DEFAULT NULL,  `peakWaveDirection` float DEFAULT NULL,  `windSpeed` float DEFAULT NULL,  `windDirection` float DEFAULT NULL,  `windU` float DEFAULT NULL,  `windV` float DEFAULT NULL) ENGINE=MyISAM DEFAULT CHARSET=utf8;");
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

}
