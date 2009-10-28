/**
 * 
 */
package edu.unicen.surfforecaster.server.domain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.WW3Parameter;
import edu.unicen.surfforecaster.server.domain.decoder.GribDecoder;
import edu.unicen.surfforecaster.server.domain.download.DownloaderJob;
import edu.unicen.surfforecaster.server.domain.download.DownloaderJobListener;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.ForecastParameter;
import edu.unicen.surfforecaster.server.domain.entity.Point;

/**
 * This class provides the latest forecasts issued by the NOAA and also archived
 * forecasts. NOAA place in their FTP servers the latest forecast encoded in
 * GRIB2 format. A downloader automatically download the files posted by NOAA at
 * the ftp server. When a new file is downloaded the downloader notifies this
 * class. After the notification this class decodes the files and obtain the
 * forecast info for all the locations needed and also stores the previous
 * latest forecast in the archive. For space efficiency we only store the
 * forecasts for the 0 hours and +3 hours all others are deleted.
 * 
 * 
 * @author esteban wagner.
 * 
 */
public class NoaaWaveWatchModel extends HibernateDaoSupport implements
		WaveWatchModel, Observer {
	/**
	 * The logger.
	 */
	Logger log = Logger.getLogger(this.getClass());
	/**
	 * 
	 * The grib decoder to use.
	 */
	private GribDecoder gribDecoder;
	/**
	 * Model Name
	 */
	private final String modelName;

	/**
	 * DB table to use for the archived forecasts.
	 */
	private final String archiveTableName;
	/**
	 * DB table to use for the latest forecasts.
	 */
	private final String latestForecastTableName;
	/**
	 * DB table to use for the model gridPoints.
	 */
	private final String gridPointsTableName;

	/**
	 * File path to use to perform massive inserts to DB.
	 */
	private final String massiveInsertsTempFile;
	/**
	 * Delimiter characters of massive insert file.
	 */
	public final static String lineStart = "e";
	public final static String fieldSeparator = ",";
	public final static String lineEnd = "x";
	private String validPointsFilePath;

	/**
	 * Creates a new Model with the specified name. Tables to store model data
	 * will be created according to the model name.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws IllegalStateException
	 * @throws HibernateException
	 * @throws DataAccessResourceFailureException
	 */
	public NoaaWaveWatchModel(final String modelName) {
		Validate.notEmpty(modelName);
		this.modelName = modelName;
		archiveTableName = "waveWatchArchive_" + modelName;
		latestForecastTableName = "waveWatchLatestForecast_" + modelName;
		gridPointsTableName = "waveWatchGridPoints_" + modelName;
		massiveInsertsTempFile = "c:\\waveWatch_" + modelName + ".csv";
	}

	public void init() throws IOException, DataAccessResourceFailureException,
			HibernateException, IllegalStateException, SQLException, URISyntaxException {
		// Init valid grid points table.
		if (!tableExists(gridPointsTableName)) {
			if (validPointsFilePath==null){
				validPointsFilePath="validGridPoints_"+modelName;
			}
			URL resource = ClassLoader.getSystemClassLoader().getResource("validGridPoints_"+modelName+".csv");
			massiveInsertValidGridPoints(new File(resource.getFile()));
		}
		//Init archive table
		if (!tableExists(archiveTableName)) {
			createArchiveTable();
		}
		//Init latest forecast table
		if (!tableExists(latestForecastTableName)) {
			createLatestForecastTable();
		}
	}

	public void setValidPointsFilePath(String validPointsFilePath) {
		this.validPointsFilePath = validPointsFilePath;
	}

	/**
	 * 
	 */
	private void createArchiveTable() {
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			final String query = "CREATE TABLE "
					+ archiveTableName
					+ " ( `issuedDate` DATETIME NOT NULL,"
					+ " `validTime` tinyint(4) unsigned NOT NULL,"
					+ " `latitude` float NOT NULL, `longitude` float NOT NULL,"
					+ " `windWaveHeight` float DEFAULT NULL,"
					+ " `windWavePeriod` float DEFAULT NULL,"
					+ " `windWaveDirection` float DEFAULT NULL,"
					+ " `swellWaveHeight` float DEFAULT NULL,"
					+ " `swellWavePeriod` float DEFAULT NULL,"
					+ " `swellWaveDirection` float DEFAULT NULL,"
					+ " `combinedWaveHeight` float DEFAULT NULL,"
					+ "  `peakWavePeriod` float DEFAULT NULL, "
					+ "`peakWaveDirection` float DEFAULT NULL,"
					+ " `windSpeed` float DEFAULT NULL,"
					+ " `windDirection` float DEFAULT NULL,"
					+ " `windU` float DEFAULT NULL, "
					+ "`windV` float DEFAULT NULL,"
					+ "  KEY `location` (`latitude`,`longitude`))"
					+ " ENGINE=MyISAM DEFAULT CHARSET=utf8"
					+ " PARTITION BY RANGE (TO_DAYS(issuedDate))("
					+ "	PARTITION	P1	VALUES LESS THAN (to_days('	2009-11-01	')),		"
					+ "	PARTITION	P2	VALUES LESS THAN (to_days('	2009-12-01	')),		"
					+ "	PARTITION	P3	VALUES LESS THAN (to_days('	2010-01-01	')),		"
					+ "	PARTITION	P4	VALUES LESS THAN (to_days('	2010-02-01	')),		"
					+ "	PARTITION	P5	VALUES LESS THAN (to_days('	2010-03-01	')),		"
					+ "	PARTITION	P6	VALUES LESS THAN (to_days('	2010-04-01	')),		"
					+ "	PARTITION	P7	VALUES LESS THAN (to_days('	2010-05-01	')),		"
					+ "	PARTITION	P8	VALUES LESS THAN (to_days('	2010-06-01	')),		"
					+ "	PARTITION	P9	VALUES LESS THAN (to_days('	2010-07-01	')),		"
					+ "	PARTITION	P10	VALUES LESS THAN (to_days('	2010-08-01	')),		"
					+ "	PARTITION	P11	VALUES LESS THAN (to_days('	2010-09-01	')),		"
					+ "	PARTITION	P12	VALUES LESS THAN (to_days('	2010-10-01	')),		"
					+ "	PARTITION	P13	VALUES LESS THAN (to_days('	2010-11-01	')),		"
					+ "	PARTITION	P14	VALUES LESS THAN (to_days('	2010-12-01	')),		"
					+ "	PARTITION	P15	VALUES LESS THAN (to_days('	2011-01-01	')),		"
					+ "	PARTITION	P16	VALUES LESS THAN (to_days('	2011-02-01	')),		"
					+ "	PARTITION	P17	VALUES LESS THAN (to_days('	2011-03-01	')),		"
					+ "	PARTITION	P18	VALUES LESS THAN (to_days('	2011-04-01	')),		"
					+ "	PARTITION	P19	VALUES LESS THAN (to_days('	2011-05-01	')),		"
					+ "	PARTITION	P20	VALUES LESS THAN (to_days('	2011-06-01	')),		"
					+ "	PARTITION	P21	VALUES LESS THAN (to_days('	2011-07-01	')),		"
					+ "	PARTITION	P22	VALUES LESS THAN (to_days('	2011-08-01	')),		"
					+ "	PARTITION	P23	VALUES LESS THAN (to_days('	2011-09-01	')),		"
					+ "	PARTITION	P24	VALUES LESS THAN (to_days('	2011-10-01	')),		"
					+ "	PARTITION	P25	VALUES LESS THAN (to_days('	2011-11-01	')),		"
					+ "	PARTITION	P26	VALUES LESS THAN (to_days('	2011-12-01	')),		"
					+ "	PARTITION	P27	VALUES LESS THAN (to_days('	2012-01-01	')),		"
					+ "	PARTITION	P28	VALUES LESS THAN (to_days('	2012-02-01	')),		"
					+ "	PARTITION	P29	VALUES LESS THAN (to_days('	2012-03-01	')),		"
					+ "	PARTITION	P30	VALUES LESS THAN (to_days('	2012-04-01	')),		"
					+ "	PARTITION	P31	VALUES LESS THAN (to_days('	2012-05-01	')),		"
					+ "	PARTITION	P32	VALUES LESS THAN (to_days('	2012-06-01	')),		"
					+ "	PARTITION	P33	VALUES LESS THAN (to_days('	2012-07-01	')),		"
					+ "	PARTITION	P34	VALUES LESS THAN (to_days('	2012-08-01	')),		"
					+ "	PARTITION	P35	VALUES LESS THAN (to_days('	2012-09-01	')),		"
					+ "	PARTITION	P36	VALUES LESS THAN (to_days('	2012-10-01	')),		"
					+ "	PARTITION	P37	VALUES LESS THAN (to_days('	2012-11-01	')),		"
					+ "	PARTITION	P38	VALUES LESS THAN (to_days('	2012-12-01	')),		"
					+ "	PARTITION	P39	VALUES LESS THAN (to_days('	2013-01-01	')),		"
					+ "	PARTITION	P40	VALUES LESS THAN (to_days('	2013-02-01	')),		"
					+ "	PARTITION	P41	VALUES LESS THAN (to_days('	2013-03-01	')),		"
					+ "	PARTITION	P42	VALUES LESS THAN (to_days('	2013-04-01	')),		"
					+ "	PARTITION	P43	VALUES LESS THAN (to_days('	2013-05-01	')),		"
					+ "	PARTITION	P44	VALUES LESS THAN (to_days('	2013-06-01	')),		"
					+ "	PARTITION	P45	VALUES LESS THAN (to_days('	2013-07-01	')),		"
					+ "	PARTITION	P46	VALUES LESS THAN (to_days('	2013-08-01	')),		"
					+ "	PARTITION	P47	VALUES LESS THAN (to_days('	2013-09-01	')),		"
					+ "	PARTITION	P48	VALUES LESS THAN (to_days('	2013-10-01	')),		"
					+ "	PARTITION	P49	VALUES LESS THAN (to_days('	2013-11-01	')),		"
					+ "	PARTITION	P50	VALUES LESS THAN (to_days('	2013-12-01	')),		"
					+ "	PARTITION	P51	VALUES LESS THAN (to_days('	2014-01-01	')),		"
					+ "	PARTITION	P52	VALUES LESS THAN (to_days('	2014-02-01	')),		"
					+ "	PARTITION	P53	VALUES LESS THAN (to_days('	2014-03-01	')),		"
					+ "	PARTITION	P54	VALUES LESS THAN (to_days('	2014-04-01	')),		"
					+ "	PARTITION	P55	VALUES LESS THAN (to_days('	2014-05-01	')),		"
					+ "	PARTITION	P56	VALUES LESS THAN (to_days('	2014-06-01	')),		"
					+ "	PARTITION	P57	VALUES LESS THAN (to_days('	2014-07-01	')),		"
					+ "	PARTITION	P58	VALUES LESS THAN (to_days('	2014-08-01	')),		"
					+ "	PARTITION	P59	VALUES LESS THAN (to_days('	2014-09-01	')),		"
					+ "	PARTITION	P60	VALUES LESS THAN (to_days('	2014-10-01	')),		"
					+ "	PARTITION	P61	VALUES LESS THAN (to_days('	2014-11-01	')),		"
					+ "	PARTITION	P62	VALUES LESS THAN (to_days('	2014-12-01	')),		"
					+ "	PARTITION	P63	VALUES LESS THAN (to_days('	2015-01-01	')),		"
					+ "	PARTITION	P64	VALUES LESS THAN (to_days('	2015-02-01	')),		"
					+ "	PARTITION	P65	VALUES LESS THAN (to_days('	2015-03-01	')),		"
					+ "	PARTITION	P66	VALUES LESS THAN (to_days('	2015-04-01	')),		"
					+ "	PARTITION	P67	VALUES LESS THAN (to_days('	2015-05-01	')),		"
					+ "	PARTITION	P68	VALUES LESS THAN (to_days('	2015-06-01	')),		"
					+ "	PARTITION	P69	VALUES LESS THAN (to_days('	2015-07-01	')),		"
					+ "	PARTITION	P70	VALUES LESS THAN (to_days('	2015-08-01	')),		"
					+ "	PARTITION	P71	VALUES LESS THAN (to_days('	2015-09-01	')),		"
					+ "	PARTITION	P72	VALUES LESS THAN (to_days('	2015-10-01	')),		"
					+ "	PARTITION	P73	VALUES LESS THAN (to_days('	2015-11-01	')),		"
					+ "	PARTITION	P74	VALUES LESS THAN (to_days('	2015-12-01	')),		"
					+ "	PARTITION	P75	VALUES LESS THAN (to_days('	2016-01-01	')),		"
					+ "	PARTITION	P76	VALUES LESS THAN (to_days('	2016-02-01	')),		"
					+ "	PARTITION	P77	VALUES LESS THAN (to_days('	2016-03-01	')),		"
					+ "	PARTITION	P78	VALUES LESS THAN (to_days('	2016-04-01	')),		"
					+ "	PARTITION	P79	VALUES LESS THAN (to_days('	2016-05-01	')),		"
					+ "	PARTITION	P80	VALUES LESS THAN (to_days('	2016-06-01	')),		"
					+ "	PARTITION	P81	VALUES LESS THAN (to_days('	2016-07-01	')),		"
					+ "	PARTITION	P82	VALUES LESS THAN (to_days('	2016-08-01	')),		"
					+ "	PARTITION	P83	VALUES LESS THAN (to_days('	2016-09-01	')),		"
					+ "	PARTITION	P84	VALUES LESS THAN (to_days('	2016-10-01	')),		"
					+ "	PARTITION	P85	VALUES LESS THAN (to_days('	2016-11-01	')),		"
					+ "	PARTITION	P86	VALUES LESS THAN (to_days('	2016-12-01	')),		"
					+ "	PARTITION	P87	VALUES LESS THAN (to_days('	2017-01-01	')),		"
					+ "	PARTITION	P88	VALUES LESS THAN (to_days('	2017-02-01	')),		"
					+ "	PARTITION	P89	VALUES LESS THAN (to_days('	2017-03-01	')),		"
					+ "	PARTITION	P90	VALUES LESS THAN (to_days('	2017-04-01	')),		"
					+ "	PARTITION	P91	VALUES LESS THAN (to_days('	2017-05-01	')),		"
					+ "	PARTITION	P92	VALUES LESS THAN (to_days('	2017-06-01	')),		"
					+ "	PARTITION	P93	VALUES LESS THAN (to_days('	2017-07-01	')),		"
					+ "	PARTITION	P94	VALUES LESS THAN (to_days('	2017-08-01	')),		"
					+ "	PARTITION	P95	VALUES LESS THAN (to_days('	2017-09-01	')),		"
					+ "	PARTITION	P96	VALUES LESS THAN (to_days('	2017-10-01	')),		"
					+ "	PARTITION	P97	VALUES LESS THAN (to_days('	2017-11-01	')),		"
					+ "	PARTITION	P98	VALUES LESS THAN (to_days('	2017-12-01	')),		"
					+ "	PARTITION	P99	VALUES LESS THAN (to_days('	2018-01-01	')),		"
					+ "	PARTITION	P100	VALUES LESS THAN (to_days('	2018-02-01	')),		"
					+ "	PARTITION	P101	VALUES LESS THAN (to_days('	2018-03-01	')),		"
					+ "	PARTITION	P102	VALUES LESS THAN (to_days('	2018-04-01	')),		"
					+ "	PARTITION	P103	VALUES LESS THAN (to_days('	2018-05-01	')),		"
					+ "	PARTITION	P104	VALUES LESS THAN (to_days('	2018-06-01	')),		"
					+ "	PARTITION	P105	VALUES LESS THAN (to_days('	2018-07-01	')),		"
					+ "	PARTITION	P106	VALUES LESS THAN (to_days('	2018-08-01	')),		"
					+ "	PARTITION	P107	VALUES LESS THAN (to_days('	2018-09-01	')),		"
					+ "	PARTITION	P108	VALUES LESS THAN (to_days('	2018-10-01	')),		"
					+ "	PARTITION	P109	VALUES LESS THAN (to_days('	2018-11-01	')),		"
					+ "	PARTITION	P110	VALUES LESS THAN (to_days('	2018-12-01	')),		"
					+ "	PARTITION	P111	VALUES LESS THAN (to_days('	2019-01-01	')),		"
					+ "PARTITION P112 VALUES LESS THAN MAXVALUE)";

			st.execute(query);
			st.close();
			connection.close();
		} catch (final SQLException e) {
			log.error(e);
		}
	}

	/**
	 * @param gridFile
	 * @return
	 * @throws IOException
	 */
	private List<Point> getValidGridPoints(final String gridFile)
			throws IOException {
		final Collection<ForecastPlain> forecastForTime = gribDecoder
				.getForecastForTime(new File(gridFile), 0);
		final List<Point> points = new ArrayList<Point>();
		for (final Iterator iterator = forecastForTime.iterator(); iterator
				.hasNext();) {
			final ForecastPlain forecastArch = (ForecastPlain) iterator.next();
			points.add(new Point(forecastArch.getLatitude(), forecastArch
					.getLongitude()));
		}
		return points;
	}

	/**
	 * 
	 */
	private void createGridPointTable() {
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			st
					.execute("CREATE TABLE "
							+ gridPointsTableName
							+ "  (   `latitude` float NOT NULL,  `longitude` float NOT NULL) ENGINE=MyISAM DEFAULT CHARSET=utf8;");
			st.execute("ALTER TABLE " + gridPointsTableName
					+ " ADD INDEX location(latitude, longitude)");
			st.close();
			connection.close();
		} catch (final SQLException e) {
			log.error(e);
		}

	}

	/**
	 * @param gridFile
	 * @throws IOException
	 */
	private void insertGridPoints(final Collection<Point> points)
			throws IOException {

		try {
			final Connection connection = this.getSession().connection();
			connection.setAutoCommit(false);
			final Statement st = connection.createStatement();
			for (final Iterator iterator = points.iterator(); iterator
					.hasNext();) {
				final Point point = (Point) iterator.next();
				final String query = "INSERT INTO " + gridPointsTableName
						+ " values (" + point.getLatitude() + ", "
						+ point.getLongitude() + ")";
				st.execute(query);
			}
			connection.commit();
			st.close();
			connection.close();
		} catch (final SQLException e) {
			log.error(e);
			
		}

	}

	/**
	 * @return
	 * @throws SQLException
	 * @throws IllegalStateException
	 * @throws HibernateException
	 * @throws DataAccessResourceFailureException
	 */
	private boolean tableExists(final String tableName)
			throws DataAccessResourceFailureException, HibernateException,
			IllegalStateException, SQLException {
		final DatabaseMetaData dbm = this.getSession().connection()
				.getMetaData();
		final ResultSet rs = dbm.getTables(null, null, tableName, null);
		if (rs.next())
			return true;
		else
			return false;

	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.WaveWatchModel#getArchivedForecasts(float,
	 *      float, java.util.GregorianCalendar, java.util.GregorianCalendar)
	 */
	@Override
	public List<Forecast> getArchivedForecasts(final Point point,
			final GregorianCalendar from, final GregorianCalendar to) {
		final long init = System.currentTimeMillis();
		final float lat = new Float(point.getLatitude());
		final float lon = new Float(point.getLongitude());
		final List<ForecastPlain> forecasts = new ArrayList<ForecastPlain>();
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			final String fromDate = from.get(Calendar.YEAR) + "-"
					+ (from.get(Calendar.MONTH) + 1) + "-"
					+ from.get(Calendar.DAY_OF_MONTH);
			final String toDate = to.get(Calendar.YEAR) + "-"
					+ (to.get(Calendar.MONTH) + 1) + "-"
					+ to.get(Calendar.DAY_OF_MONTH);

			final ResultSet result = st.executeQuery("select * from "
					+ archiveTableName + " where latitude = " + lat
					+ " AND longitude = " + lon + " AND issuedDate BETWEEN '"
					+ fromDate + "' AND '" + toDate + "'");

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
				final ForecastPlain arch = new ForecastPlain(issuedDate,
						validTime, latitude, longitude, windWaveHeight,
						windWavePeriod, windWaveDirection, swellWaveHeight,
						swellWavePeriod, swellWaveDirection,
						combinedWaveHeight, peakWavePeriod, peakWaveDirection,
						windSpeed, windDirection, windU, windV);
				forecasts.add(arch);
			}

			st.close();
		} catch (final SQLException e) {
			log.error(e);
			}
		final long end = System.currentTimeMillis();
		log.info("Readed: " + forecasts.size() + " forecasts.");
		log.info("Elapsed Time: " + (end - init) / 1000);
		return translate(forecasts);
	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.WaveWatchModel#getLatestForecast(edu.unicen.surfforecaster.server.domain.entity.forecasters.Point)
	 */
	@Override
	public List<Forecast> getLatestForecast(final Point gridPoint) {
		log.info("Retrieving latest forecast for: " + gridPoint);
		final long init = System.currentTimeMillis();
		final List<ForecastPlain> forecasts = new ArrayList<ForecastPlain>();
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			final ResultSet result = st.executeQuery("select * from "
					+ latestForecastTableName + " where latitude = "
					+ gridPoint.getLatitude() + " AND longitude = "
					+ gridPoint.getLongitude());
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
				final ForecastPlain arch = new ForecastPlain(issuedDate,
						validTime, latitude, longitude, windWaveHeight,
						windWavePeriod, windWaveDirection, swellWaveHeight,
						swellWavePeriod, swellWaveDirection,
						combinedWaveHeight, peakWavePeriod, peakWaveDirection,
						windSpeed, windDirection, windU, windV);
				forecasts.add(arch);
			}
		} catch (final SQLException e) {
			log.error(e);
		}
		final long end = System.currentTimeMillis();
		log.info("Elapsed Time To retrieve latest forecast : "
				+ (end - init) / 1000);
		return translate(forecasts);
	}

	/**
	 * @return the date of the latest forecasts received from NOAA. Null if no
	 *         latest forecast exists.
	 * @throws SQLException
	 * @throws IllegalStateException
	 * @throws HibernateException
	 * @throws DataAccessResourceFailureException
	 */
	public Date getLatestForecastTime() {
		log.info("Retrieving latest forecast issued date for: ");
		final long init = System.currentTimeMillis();
		try {
			if (!tableExists(latestForecastTableName))
				return null;
		} catch (final Exception e1) {
		}
		Date date = null;
		final List<ForecastPlain> forecasts = new ArrayList<ForecastPlain>();
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			final ResultSet result = st.executeQuery("select * from "
					+ latestForecastTableName + " LIMIT 1");
			while (result.next()) {
				date = result.getDate("issuedDate");
			}
		} catch (final SQLException e) {
			log.error(e);
		}
		final long end = System.currentTimeMillis();
		log.info("Elapsed Time To retrieve latest forecast issued date : "
						+ (end - init) / 1000);
		return date;
	}

	/**
	 * Obtain near by grid points of the given point.
	 * 
	 * @param point
	 * @return
	 */
	public List<Point> getNearbyGridPoints(final Point point) {

		final List<Point> validPoints = getValidGridPointsFromDB();
		final List<Point> nearbyPoints = new ArrayList<Point>();
		for (final Iterator iterator = validPoints.iterator(); iterator
				.hasNext();) {
			final Point validPoint = (Point) iterator.next();
			if (Math.abs(point.getLatitude() - validPoint.getLatitude()) < 0.5
					&& Math.abs(point.getLongitude()
							- validPoint.getLongitude()) < 0.5) {
				nearbyPoints.add(validPoint);
			}
		}
		return nearbyPoints;
	}

	/**
	 * @return
	 */
	private List<Point> getValidGridPointsFromDB() {
		log.info("Retrieving valid grid points from DB");
		final long init = System.currentTimeMillis();
		final List<Point> points = new ArrayList<Point>();
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			final ResultSet result = st.executeQuery("SELECT * FROM "
					+ gridPointsTableName);
			while (result.next()) {
				points.add(new Point(result.getFloat("latitude"), result
						.getFloat("longitude")));
			}
			st.close();
		} catch (final SQLException e) {
			log.error(e);
		}
		final long end = System.currentTimeMillis();
		log.info("Found " + points.size() + " grid points");
		log.info("Elapsed Time To retrieve valid grid points : "
				+ (end - init) / 1000);
		return points;
	}

	/**
	 * Sets the Grib decoder to use.
	 * 
	 * @param decoder
	 *            the decoder to set
	 */
	public void setGribDecoder(final GribDecoder decoder) {
		gribDecoder = decoder;
	}

	/**
	 * Called when a new Grib file with latest forecast data has been
	 * downloaded. Actions performed are: 1)Move latest forecast to the archive.
	 * 2)Decode grib file and obtain latest forecast. 3)Persist latest forecast
	 * into DB.
	 * 
	 * @param observable
	 *            {@link DownloaderJobListener} notifies when a new forecast
	 *            file has been downloaded.
	 * @param gribFile
	 *            grib2 file containing latest wave watch forecasts just
	 *            downloaded by {@link DownloaderJob}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void update(final Observable o, final Object gribFile) {

		if (!(o instanceof DownloaderJobListener))
			throw new IncompatibleClassChangeError();
		if (!(gribFile instanceof File))
			throw new InvalidParameterException();

		final long init = System.currentTimeMillis();

		// TODO Begin transaction
		archiveLatestForecasts();
		updateLatestForecasts((File) gribFile);
		// TODO End Transaction

		final long end = System.currentTimeMillis();

		log.info("Elapsed Time To update forecasts: " + (end - init)
				/ 1000);
	}

	private void archiveLatestForecasts() {
		log.info("Archiving Forecasts");
		final long init = System.currentTimeMillis();
		try {
			final Connection connection = this.getSession().connection();

			final Statement st = connection.createStatement();
			st.execute("insert into " + archiveTableName + " select * from "
					+ latestForecastTableName
					+ " where validTime=3 OR validTime=0;");
			st.close();
		} catch (final SQLException e) {
			log.error(e);
		}
		final long end = System.currentTimeMillis();
		log.info("Elapsed Time To archive forecast : " + (end - init)
				/ 1000);

	}

	/**
	 * Decode given grib file and create forecasts objects.
	 * 
	 * @param file
	 */
	private void updateLatestForecasts(final File csvFile) {
		// Decode downloaded NOAA grib files to obtain latest forecasts.
		final File textFile = new File(massiveInsertsTempFile);
		if (textFile.exists())
			if (textFile.isFile()) {
				textFile.delete();
			}
		for (int time = 0; time < 61; time++) {
			try {
				final Collection<ForecastPlain> forecasts = gribDecoder
						.getForecastForTime(csvFile, time);
				appendForecastsToFile(textFile, forecasts);
			} catch (final IOException e) {
				log.error(e);
			}
		}
		massiveInsertLatestForecast(textFile);
	}
	private void massiveInsertValidGridPoints(final File csvFile) {
		log.info("Using valid grid point data from: " + csvFile.getAbsolutePath());
		final long init = System.currentTimeMillis();
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			//Create Table Grid Points
			st
			.execute("CREATE TABLE "
					+ gridPointsTableName
					+ "  (   `latitude` float NOT NULL,  `longitude` float NOT NULL) ENGINE=MyISAM DEFAULT CHARSET=utf8;");
			//Insert into table CSV data.
			final String query = "LOAD DATA INFILE '"
					+ csvFile.getAbsolutePath().replace('\\', '/')
					+ "' INTO TABLE " + gridPointsTableName
					+ " FIELDS TERMINATED BY '" + fieldSeparator
					+ "'  LINES STARTING BY '" + lineStart
					+ "' TERMINATED BY '" + lineEnd + "'";
			st.execute(query);
			//Add indexes to table.
			st.execute("ALTER TABLE " + gridPointsTableName
					+ " ADD INDEX location(latitude, longitude)");
			st.close();
		} catch (final SQLException e) {
			throw new DataAccessResourceFailureException(e.toString(),e) ;
		}
		final long end = System.currentTimeMillis();
		log.info(gridPointsTableName +" data inserted in: "+ (end - init) / 1000  +" sec.");

	}
	/**
	 * @see edu.unicen.surfforecaster.server.domain.WaveWatchModel#insertIntoLatestForecastFromFile(java.io.File)
	 */

	private void massiveInsertLatestForecast(final File textFile) {
		log.info("Inserting data from: " + textFile.getAbsolutePath());
		final long init = System.currentTimeMillis();
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			st.execute("DROP TABLE " + latestForecastTableName);
			st
					.execute("CREATE TABLE "
							+ latestForecastTableName
							+ "  (  `issuedDate` DATETIME NOT NULL,  `validTime` tinyint(4) unsigned NOT NULL,  `latitude` float NOT NULL,  `longitude` float NOT NULL,  `windWaveHeight` float DEFAULT NULL,  `windWavePeriod` float DEFAULT NULL,  `windWaveDirection` float DEFAULT NULL,  `swellWaveHeight` float DEFAULT NULL,  `swellWavePeriod` float DEFAULT NULL,  `swellWaveDirection` float DEFAULT NULL,  `combinedWaveHeight` float DEFAULT NULL,  `peakWavePeriod` float DEFAULT NULL,  `peakWaveDirection` float DEFAULT NULL,  `windSpeed` float DEFAULT NULL,  `windDirection` float DEFAULT NULL,  `windU` float DEFAULT NULL,  `windV` float DEFAULT NULL) ENGINE=MyISAM DEFAULT CHARSET=utf8;");
			final String query = "LOAD DATA INFILE '"
					+ textFile.getAbsolutePath().replace('\\', '/')
					+ "' INTO TABLE " + latestForecastTableName
					+ " FIELDS TERMINATED BY '" + fieldSeparator
					+ "'  LINES STARTING BY '" + lineStart
					+ "' TERMINATED BY '" + lineEnd + "'";
			st.execute(query);
			st.execute("ALTER TABLE " + latestForecastTableName
					+ " ADD INDEX location(latitude, longitude)");
			st.close();
		} catch (final SQLException e) {
			log.error(e);
		}
		final long end = System.currentTimeMillis();
		log.info("Elapsed Time To insert data from file: "
				+ (end - init) / 1000);

	}

	private void createLatestForecastTable() {
		log.info("Creating table: " + latestForecastTableName);
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			st
					.execute("CREATE TABLE "
							+ latestForecastTableName
							+ "  (  `issuedDate` DATETIME NOT NULL,  `validTime` tinyint(4) unsigned NOT NULL,  `latitude` float NOT NULL,  `longitude` float NOT NULL,  `windWaveHeight` float DEFAULT NULL,  `windWavePeriod` float DEFAULT NULL,  `windWaveDirection` float DEFAULT NULL,  `swellWaveHeight` float DEFAULT NULL,  `swellWavePeriod` float DEFAULT NULL,  `swellWaveDirection` float DEFAULT NULL,  `combinedWaveHeight` float DEFAULT NULL,  `peakWavePeriod` float DEFAULT NULL,  `peakWaveDirection` float DEFAULT NULL,  `windSpeed` float DEFAULT NULL,  `windDirection` float DEFAULT NULL,  `windU` float DEFAULT NULL,  `windV` float DEFAULT NULL) ENGINE=MyISAM DEFAULT CHARSET=utf8;");
			st.close();
			connection.close();
		} catch (final SQLException e) {
			log.error(e);
		}
	}

	/**
	 * Writes the given forecasts to a text file, this file will be used to
	 * perform massive inserts into DB.
	 * 
	 * @param forecasts
	 */
	private void appendForecastsToFile(final File file,
			final Collection forecasts) {
		final long init = System.currentTimeMillis();
		try {
			final BufferedWriter output = new BufferedWriter(new FileWriter(
					file, true));

			for (final Iterator iterator = forecasts.iterator(); iterator
					.hasNext();) {
				final ForecastPlain forecast = (ForecastPlain) iterator.next();
				final float windWaveHeight = forecast.getWindWaveHeight()
						.isNaN() ? -1F : forecast.getWindWaveHeight();
				final float windWavePeriod = forecast.getWindWavePeriod()
						.isNaN() ? -1F : forecast.getWindWavePeriod();
				final float windWaveDirection = forecast.getWindWaveDirection()
						.isNaN() ? -1F : forecast.getWindWaveDirection();
				final float swellWaveHeight = forecast.getSwellWaveHeight()
						.isNaN() ? -1F : forecast.getSwellWaveHeight();
				final float swellWavePeriod = forecast.getSwellWavePeriod()
						.isNaN() ? -1F : forecast.getSwellWavePeriod();
				final float swellWaveDirection = forecast
						.getSwellWaveDirection().isNaN() ? -1F : forecast
						.getSwellWaveDirection();
				final float combinaedWaveHeight = forecast
						.getCombinedWaveHeight().isNaN() ? -1F : forecast
						.getCombinedWaveHeight();
				final float peakWavePeriod = forecast.getPeakWavePeriod()
						.isNaN() ? -1F : forecast.getPeakWavePeriod();
				final float peakWaveDirection = forecast.getPeakWaveDirection()
						.isNaN() ? -1F : forecast.getPeakWaveDirection();
				final float windSpeed = forecast.getWindSpeed().isNaN() ? -1F
						: forecast.getWindSpeed();
				final float windDirection = forecast.getWindDirection().isNaN() ? -1F
						: forecast.getWindDirection();
				final float windU = forecast.getWindU().isNaN() ? -1F
						: forecast.getWindU();
				final float windV = forecast.getWindV().isNaN() ? -1F
						: forecast.getWindV();
				final int year = forecast.getIssuedDate().get(Calendar.YEAR);
				final int month = forecast.getIssuedDate().get(Calendar.MONTH) + 1;
				;
				final int day = forecast.getIssuedDate().get(
						Calendar.DAY_OF_MONTH);
				;
				final int hour = forecast.getIssuedDate().get(
						Calendar.HOUR_OF_DAY);
				final int minute = forecast.getIssuedDate()
						.get(Calendar.MINUTE);
				;
				final int seconds = forecast.getIssuedDate().get(
						Calendar.SECOND);

				final String line = lineStart + year + "-" + month + "-" + day
						+ " " + hour + ":" + minute + ":" + seconds
						+ fieldSeparator + forecast.getValidTime()
						+ fieldSeparator + forecast.getLatitude()
						+ fieldSeparator + forecast.getLongitude()
						+ fieldSeparator + windWaveHeight + fieldSeparator
						+ windWavePeriod + fieldSeparator + windWaveDirection
						+ fieldSeparator + swellWaveHeight + fieldSeparator
						+ swellWavePeriod + fieldSeparator + swellWaveDirection
						+ fieldSeparator + combinaedWaveHeight + fieldSeparator
						+ peakWavePeriod + fieldSeparator + peakWaveDirection
						+ fieldSeparator + windSpeed + fieldSeparator
						+ windDirection + fieldSeparator + windU
						+ fieldSeparator + windV + lineEnd;
				output.append(line);
				output.newLine();

			}
			output.close();
		} catch (final Exception e) {
			log.error(e);
		}
		final long end = System.currentTimeMillis();
		log.info("Writing forecasts to file");
		log.info("Elapsed time: " + (end - init) / 1000);
	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.WaveWatchModel#isGridPoint(edu.unicen.surfforecaster.server.domain.entity.forecasters.Point)
	 */
	@Override
	public boolean isGridPoint(final Point point) {

		final long init = System.currentTimeMillis();
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			final String query = "SELECT * FROM " + gridPointsTableName
					+ " WHERE latitude = " + point.getLatitude()
					+ " AND longitude = " + point.getLongitude();
			final ResultSet result = st.executeQuery(query);
			boolean found = false;
			if (result.next()) {
				found = true;
			}
			st.close();
			connection.close();
			final long end = System.currentTimeMillis();
			log.info("Elapsed Time To find point in grid points table"
							+ (end - init) / 1000);

			return found;
		} catch (final SQLException e) {
			log.error(e);
			return false;
		}
	}

	private List<Forecast> translate(final List<ForecastPlain> forecasts) {
		final List<Forecast> translatedForecasts = new ArrayList<Forecast>();
		for (final Iterator iterator = forecasts.iterator(); iterator.hasNext();) {
			final ForecastPlain forecastArch = (ForecastPlain) iterator.next();
			final Map<String, ForecastParameter> parameters = new HashMap<String, ForecastParameter>();
			parameters.put(WW3Parameter.WIND_WAVE_HEIGHT.toString(),
					new ForecastParameter(WW3Parameter.WIND_WAVE_HEIGHT
							.toString(), forecastArch.getWindWaveHeight(),
							Unit.Meters));
			parameters.put(WW3Parameter.WIND_WAVE_PERIOD.toString(),
					new ForecastParameter(WW3Parameter.WIND_WAVE_PERIOD
							.toString(), forecastArch.getWindWavePeriod(),
							Unit.Meters));
			parameters.put(WW3Parameter.WIND_WAVE_DIRECTION.toString(),
					new ForecastParameter(WW3Parameter.WIND_WAVE_DIRECTION
							.toString(), forecastArch.getWindWaveDirection(),
							Unit.Meters));
			parameters.put(WW3Parameter.SWELL_WAVE_HEIGHT.toString(),
					new ForecastParameter(WW3Parameter.SWELL_WAVE_HEIGHT
							.toString(), forecastArch.getSwellWaveHeight(),
							Unit.Meters));
			parameters.put(WW3Parameter.SWELL_WAVE_PERIOD.toString(),
					new ForecastParameter(WW3Parameter.SWELL_WAVE_PERIOD
							.toString(), forecastArch.getSwellWavePeriod(),
							Unit.Meters));
			parameters.put(WW3Parameter.SWELL_DIRECTION.toString(),
					new ForecastParameter(WW3Parameter.SWELL_DIRECTION
							.toString(), forecastArch.getSwellWaveDirection(),
							Unit.Meters));
			parameters.put(WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT
					.toString(), new ForecastParameter(
					WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.toString(),
					forecastArch.getCombinedWaveHeight(), Unit.Meters));
			parameters.put(WW3Parameter.PRIMARY_WAVE_PERIOD.toString(),
					new ForecastParameter(WW3Parameter.PRIMARY_WAVE_PERIOD
							.toString(), forecastArch.getPeakWavePeriod(),
							Unit.Meters));
			parameters.put(WW3Parameter.PRIMARY_WAVE_DIRECTION.toString(),
					new ForecastParameter(WW3Parameter.PRIMARY_WAVE_DIRECTION
							.toString(), forecastArch.getPeakWaveDirection(),
							Unit.Meters));
			parameters.put(WW3Parameter.WIND_SPEED.toString(),
					new ForecastParameter(WW3Parameter.WIND_SPEED.toString(),
							forecastArch.getWindSpeed(), Unit.Meters));
			parameters.put(WW3Parameter.WIND_DIRECTION.toString(),
					new ForecastParameter(WW3Parameter.WIND_DIRECTION
							.toString(), forecastArch.getWindDirection(),
							Unit.Meters));
			parameters.put(WW3Parameter.WINDUComponent.toString(),
					new ForecastParameter(WW3Parameter.WINDUComponent
							.toString(), forecastArch.getWindU(), Unit.Meters));
			parameters.put(WW3Parameter.WINDVComponent.toString(),
					new ForecastParameter(WW3Parameter.WINDVComponent
							.toString(), forecastArch.getWindV(), Unit.Meters));
			final Forecast forecast = new Forecast(forecastArch.getIssuedDate()
					.getTime(), forecastArch.getValidTime(), parameters,
					new Point(forecastArch.getLatitude(), forecastArch
							.getLongitude()));
			translatedForecasts.add(forecast);
		}
		return translatedForecasts;
	}
}
