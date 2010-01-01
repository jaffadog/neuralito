package edu.unicen.surfforecaster.server.domain.wavewatch;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.Point;
import edu.unicen.surfforecaster.server.domain.entity.Value;

/**
 * 
 * 
 * @author esteban
 * 
 */
public class WaveWatchSystemPersistence extends HibernateDaoSupport implements WaveWatchSystemPersistenceI {

	/**
	 * 
	 */
	private static final int partitionFromYear = 1997;

	/**
	 * 
	 */
	private static final int partitionEndYear = 2020;

	/**
	 * The logger.
	 */
	Logger log = Logger.getLogger(this.getClass());

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
	 * The wave parameters the tables should have.
	 */
	private final WaveWatchParameter[] waveParameters;

	private String validPointsFilePath;

	/**
	 * Creates a new persister.
	 * 
	 * @param archiveTableName
	 *            The DB table to persist the archived forecasts.
	 * @param latestForecastTableName
	 *            The DB table where to persist the latest forecasts
	 * @param gridPointsTableName
	 *            The DB table which contains the valid grid points.
	 * @param validPointsFilePath
	 *            The file path where to look for the valid grid points file.
	 * @param parameters
	 *            The wave watch parameters that forecast tables will be
	 *            composed.
	 * @throws URISyntaxException
	 * @throws SQLException
	 * @throws IOException
	 * @throws IllegalStateException
	 * @throws HibernateException
	 * @throws DataAccessResourceFailureException
	 */
	public WaveWatchSystemPersistence(String archiveTableName,
			String latestForecastTableName, String gridPointsTableName,
			String validPointsFilePath, WaveWatchParameter[] parameters)
			throws DataAccessResourceFailureException, HibernateException,
			IllegalStateException, IOException, SQLException,
			URISyntaxException {
		super();
		this.archiveTableName = archiveTableName;
		this.latestForecastTableName = latestForecastTableName;
		this.gridPointsTableName = gridPointsTableName;
		this.waveParameters = parameters;
		this.validPointsFilePath = validPointsFilePath;
	}

	/* (non-Javadoc)
	 * @see edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystemPersitenceI#init()
	 */
	public void init() throws IOException,
			DataAccessResourceFailureException, HibernateException,
			IllegalStateException, SQLException, URISyntaxException {
		// Init valid grid points table.
		if (!tableExists(gridPointsTableName)) {
			URL resource = ClassLoader.getSystemClassLoader().getResource(
					this.validPointsFilePath);
			massiveInsertValidGridPoints(new File(resource.getFile()));
		}
		// Init archive table
		if (!tableExists(archiveTableName)) {
			createArchiveTable();
		}
		// Init latest forecast table
		if (!tableExists(latestForecastTableName)) {
			createLatestForecastTable();
		}
	}

	/* (non-Javadoc)
	 * @see edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystemPersitenceI#getArchivedForecasts(edu.unicen.surfforecaster.server.domain.entity.Point, java.util.Date, java.util.Date)
	 */
	public List<Forecast> getArchivedForecasts(final Point point,
			final Date fromDate, final Date toDate) {
		final long init = System.currentTimeMillis();
		final float lat = new Float(point.getLatitude());
		final float lon = new Float(point.getLongitude());
		final List<Forecast> forecasts = new ArrayList<Forecast>();
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			final Calendar from = new GregorianCalendar(TimeZone
					.getTimeZone("UTC"));
			from.setTime(fromDate);
			final Calendar to = new GregorianCalendar(TimeZone
					.getTimeZone("UTC"));
			to.setTime(toDate);
			final String fromString = from.get(Calendar.YEAR) + "-"
					+ (from.get(Calendar.MONTH) + 1) + "-"
					+ from.get(Calendar.DAY_OF_MONTH);
			final String toString = to.get(Calendar.YEAR) + "-"
					+ (to.get(Calendar.MONTH) + 1) + "-"
					+ to.get(Calendar.DAY_OF_MONTH);

			final ResultSet result = st.executeQuery("select * from "
					+ archiveTableName + " where latitude = " + lat
					+ " AND longitude = " + lon + " AND issuedDate BETWEEN '"
					+ fromString + "' AND '" + toString + "'");
			forecasts.addAll(getForecasts(result));

			st.close();
		} catch (final SQLException e) {
			log.error(e);
		}
		final long end = System.currentTimeMillis();
		log.info("Readed: " + forecasts.size() + " forecasts.");
		log.info("Elapsed Time: " + (end - init) / 1000);
		return forecasts;
	}

	/* (non-Javadoc)
	 * @see edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystemPersitenceI#getLatestForecasts(edu.unicen.surfforecaster.server.domain.entity.Point)
	 */
	public List<Forecast> getLatestForecasts(final Point gridPoint) {
		log.info("Retrieving latest forecast for: " + gridPoint);
		final long init = System.currentTimeMillis();
		final List<Forecast> forecasts = new ArrayList<Forecast>();
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			final ResultSet result = st.executeQuery("select * from "
					+ latestForecastTableName + " where latitude = "
					+ gridPoint.getLatitude() + " AND longitude = "
					+ gridPoint.getLongitude());
			forecasts.addAll(getForecasts(result));
			st.close();
		} catch (final SQLException e) {
			log.error(e);
		}
		final long end = System.currentTimeMillis();
		log.info("Elapsed Time To retrieve latest forecast : " + (end - init)
				/ 1000);
		return forecasts;
	}

	/* (non-Javadoc)
	 * @see edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystemPersitenceI#getLatestForecastTime()
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
		final List<Forecast> forecasts = new ArrayList<Forecast>();
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

	/* (non-Javadoc)
	 * @see edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystemPersitenceI#getValidGridPoints()
	 */
	public List<Point> getValidGridPoints() {
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
		log.info("Elapsed Time To retrieve valid grid points : " + (end - init)
				/ 1000);
		return points;
	}

	/* (non-Javadoc)
	 * @see edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystemPersitenceI#updateLatestForecast(edu.unicen.surfforecaster.server.domain.wavewatch.ForecastFile)
	 */
	public void updateLatestForecast(ForecastFile latestForecastsFile) {
		this.archiveLatestForecasts();
		log.info("Inserting data from: "
				+ latestForecastsFile.getAbsolutePath());
		final long init = System.currentTimeMillis();
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			st.execute("DROP TABLE " + latestForecastTableName);
			st.execute(this.getLatestForecastTable());
			final String query = "LOAD DATA INFILE '"
					+ latestForecastsFile.getAbsolutePath().replace('\\', '/')
					+ "' INTO TABLE " + latestForecastTableName
					+ " FIELDS TERMINATED BY '" + ForecastFile.fieldSeparator
					+ "'  LINES STARTING BY '" + ForecastFile.lineStart
					+ "' TERMINATED BY '" + ForecastFile.lineEnd + "'";
			st.execute(query);
			st.execute("ALTER TABLE " + latestForecastTableName
					+ " ADD INDEX location(latitude, longitude)");
			st.close();
		} catch (final SQLException e) {
			log.error(e);
		}
		final long end = System.currentTimeMillis();
		log.info("Elapsed Time To insert data from file: " + (end - init)
				/ 1000);

	}

	/**
	 * Move forecasts in latest forecast table to archive table.
	 */
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
		log.info("Elapsed Time To archive forecast : " + (end - init) / 1000);

	}

	/**
	 * 
	 * @param csvFile
	 */
	private void massiveInsertValidGridPoints(final File csvFile) {
		log.info("Using valid grid point data from: "
				+ csvFile.getAbsolutePath());
		final long init = System.currentTimeMillis();
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			// Create Table Grid Points
			st
					.execute("CREATE TABLE "
							+ gridPointsTableName
							+ "  (   `latitude` float NOT NULL,  `longitude` float NOT NULL) ENGINE=MyISAM DEFAULT CHARSET=utf8;");
			// Insert into table CSV data.
			final String query = "LOAD DATA INFILE '"
					+ csvFile.getAbsolutePath().replace('\\', '/')
					+ "' INTO TABLE " + gridPointsTableName
					+ " FIELDS TERMINATED BY '" + GridPointsFile.fieldSeparator
					+ "'  LINES STARTING BY '" + GridPointsFile.lineStart
					+ "' TERMINATED BY '" + GridPointsFile.lineEnd + "'";
			st.execute(query);
			// Add indexes to table.
			st.execute("ALTER TABLE " + gridPointsTableName
					+ " ADD INDEX location(latitude, longitude)");
			st.close();
		} catch (final SQLException e) {
			throw new DataAccessResourceFailureException(e.toString(), e);
		}
		final long end = System.currentTimeMillis();
		log.info(gridPointsTableName + " data inserted in: " + (end - init)
				/ 1000 + " sec.");

	}

	/**
	 * 
	 */
	private void createArchiveTable() {
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			final String columns = this
					.getWaveParametersColumns(this.waveParameters);
			final String partitions = generatePartitions(partitionFromYear,
					partitionEndYear);
			final String query = "CREATE TABLE " + archiveTableName
					+ " ( `issuedDate` DATETIME NOT NULL,"
					+ " `validTime` tinyint(4) unsigned NOT NULL,"
					+ " `latitude` float NOT NULL, `longitude` float NOT NULL,"
					+ columns + " , KEY `location` (`latitude`,`longitude`))"
					+ " ENGINE=MyISAM DEFAULT CHARSET=utf8" + partitions;
			st.execute(query);
			st.close();
			connection.close();
		} catch (final SQLException e) {
			log.error(e);
		}
	}

	/**
	 * Will generate partitions for partitioning ArchiveTable. One partition per
	 * month will be generated in the year range given as input.
	 * 
	 * @param fromYear
	 *            beginning year to start partitioning.
	 * @param toYear
	 *            end year of the partitioning.
	 * @return
	 */
	private String generatePartitions(int fromYear, int toYear) {
		String partitions = " PARTITION BY RANGE (TO_DAYS(issuedDate))(";
		int partitionNumber = 1;
		NumberFormat instance = NumberFormat.getInstance();
		instance.setMinimumIntegerDigits(2);
		for (int year = fromYear; year <= toYear; year++) {
			for (int month = 1; month <= 12; month++) {
				String formatedMonth = instance.format(month);
				partitions += " PARTITION	P" + partitionNumber
						+ "	VALUES LESS THAN (to_days('	" + year + "-"
						+ formatedMonth + "-01	')), \n ";
				partitionNumber++;
			}
		}
		partitions += "PARTITION	P" + partitionNumber
				+ " VALUES LESS THAN MAXVALUE)";
		return partitions;
	}

	/**
	 * 
	 */
	private void createLatestForecastTable() {
		log.info("Creating table: " + latestForecastTableName);
		try {
			final Connection connection = this.getSession().connection();
			final Statement st = connection.createStatement();
			String waveParametersColumns = getWaveParametersColumns(this.waveParameters);
			st
.execute(getLatestForecastTable());
			st.close();
			connection.close();
		} catch (final SQLException e) {
			log.error(e);
		}
	}

	private String getLatestForecastTable(){
//		"CREATE TABLE "
//		+ latestForecastTableName
//		+ "  (  `issuedDate` DATETIME NOT NULL,  `validTime` tinyint(4) unsigned NOT NULL,  `latitude` float NOT NULL,  `longitude` float NOT NULL,"
//		+ "  `windWaveHeight` float DEFAULT NULL,  `windWavePeriod` float DEFAULT NULL,  `windWaveDirection` float DEFAULT NULL,  `swellWaveHeight` float DEFAULT NULL,  `swellWavePeriod` float DEFAULT NULL,  `swellWaveDirection` float DEFAULT NULL,  `combinedWaveHeight` float DEFAULT NULL,  `peakWavePeriod` float DEFAULT NULL,  `peakWaveDirection` float DEFAULT NULL,  `windSpeed` float DEFAULT NULL,  `windDirection` float DEFAULT NULL,  `windU` float DEFAULT NULL,  `windV` float DEFAULT NULL)"
//		+ "" + " ENGINE=MyISAM DEFAULT CHARSET=utf8;"
		String waveParametersColumns = getWaveParametersColumns(this.waveParameters);
		return "CREATE TABLE "
		+ latestForecastTableName
				+ "  (  `issuedDate` DATETIME NOT NULL,  `validTime` tinyint(4) unsigned NOT NULL,  `latitude` float NOT NULL,  `longitude` float NOT NULL,"
		+ waveParametersColumns
				+ ") ENGINE=MyISAM DEFAULT CHARSET=utf8;";
		
	}

	/**
	 * 
	 * @param parameters
	 * @return
	 */
	private String getWaveParametersColumns(WaveWatchParameter[] parameters) {
		String columns = "";
		for (int i = 0; i < parameters.length; i++) {
			WaveWatchParameter waveWatchParameter = parameters[i];
			columns += "`" + waveWatchParameter.getValue()
					+ "` float DEFAULT NULL,";
		}
		// Remove last coma;
		return columns.substring(0, columns.length() - 1);
	}

	/**
	 * Translate the resultSet into a collection of Forecast.
	 * 
	 * @param result
	 * @return
	 */
	private Collection<? extends Forecast> getForecasts(ResultSet result) {
		final List<Forecast> forecasts = new ArrayList<Forecast>();
		try {
			while (result.next() != false) {
				final int validTime = result.getInt("validTime");
				final float latitude = result.getFloat("latitude");
				final float longitude = result.getFloat("longitude");
				Date issuedDate = result.getDate("issuedDate");
				Point forecastGridPoint = new Point(latitude, longitude);
				Map<String, Value> parameters = new HashMap<String, Value>();
				for (int i = 0; i < this.waveParameters.length; i++) {
					WaveWatchParameter waveWatchParameter = waveParameters[i];
					parameters.put(waveWatchParameter.getValue(), new Value(
							waveWatchParameter.getValue(),
							result.getFloat(waveWatchParameter.getValue()),
							Unit.Meters));
				}

				final Forecast forecast = new Forecast(issuedDate, validTime,
						forecastGridPoint, parameters);
				forecasts.add(forecast);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return forecasts;
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
}
