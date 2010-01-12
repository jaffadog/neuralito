/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.wavewatch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import edu.unicen.surfforecaster.common.services.dto.WaveWatchParameter;
import edu.unicen.surfforecaster.server.dao.WaveWatchSystemPersistenceI;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.Point;
import edu.unicen.surfforecaster.server.domain.wavewatch.decoder.GribDecoder;
import edu.unicen.surfforecaster.server.domain.wavewatch.gribAccess.GribAccess;

/**
 * 
 * @author esteban wagner.
 * 
 */
public class WaveWatchSystemImpl implements WaveWatchSystem {
	/**
	 * The logger.
	 */
	private final Logger log = Logger.getLogger(this.getClass());
	/**
	 * The number of forecasts this system produces.
	 */
	private static final int forecastTimes = 61;
	/**
	 * The component to obtain forecasts updates in grib format.
	 */
	private final GribAccess gribAccess;
	/**
	 * The grib decoder to use.
	 */
	private final GribDecoder gribDecoder;
	/**
	 * Component that trigger the update of the forecasts at specified times.
	 */
	private Scheduler scheduler;
	/**
	 * CronTrigger at which the forecasts updates should be triggered.
	 */
	private CronTrigger trigger;
	/**
	 * The component used to persist forecast data.
	 */
	private final WaveWatchSystemPersistenceI persister;
	/**
	 * The wave parameters this system supports.
	 */
	private final List<WaveWatchParameter> parameters;
	/**
	 * The name identifying the system.
	 */
	private final String name;
	/**
	 * The size of the grid this wave watch system uses.(In degrees)
	 */
	private final float gridSizeY;
	/**
	 * The size of the grid this wave watch system uses.(In degrees)
	 */
	private final float gridSizeX;
	// 1.5GB
	private final long maxFileSize = 1610612736;
	private final String updateTempFilePath = "updateTempFile";
	private final String importTempFile = "importTempFile";

	/**
	 * Initialize System.
	 * 
	 * @param parameters
	 *            the wave parameters that this wavewatch system will support.
	 * @param gridSizeX
	 *            the size of the grid this wave watch system uses.(In degrees)
	 * @param gridSizeY
	 *            the size of the grid this wave watch system uses.(In degrees)
	 * @param gribDecoder
	 *            the decoder to decode grib format files.
	 * @param cronExpression
	 *            times at which forecasts should be updated.
	 * @param gribAccess
	 *            provides latest forecasts in grib format.
	 * @param persister
	 *            component used to persist forecasts into DB.
	 * 
	 */
	public WaveWatchSystemImpl(final String name,
			final List<WaveWatchParameter> parameters, final float gridSizeX,
			final float gridSizeY, final GribDecoder gribDecoder,
			final String cronExpression, final GribAccess gribAccess,
			final WaveWatchSystemPersistenceI persister) {
		this.name = name;
		this.parameters = parameters;
		this.gridSizeX = gridSizeX;
		this.gridSizeY = gridSizeY;
		this.gribDecoder = gribDecoder;
		this.gribAccess = gribAccess;
		this.persister = persister;
		configureScheduler(cronExpression);
		log.info("Next forecast update time:" + trigger.getNextFireTime());
	}

	private void configureScheduler(final String cronExpression) {
		try {
			// Create the job
			final JobDetail jobDetail = new JobDetail("ForecastUpdateJob",
					null, UpdateForecastsJob.class);
			// Create the trigger
			trigger = new CronTrigger();
			trigger.setName("NOAA Grib Download Trigger");
			trigger.setCronExpression(cronExpression);
			// Create job listener
			final JobListener listener = new UpdateForecastsJobListener(name);
			jobDetail.addJobListener(listener.getName());
			final JobDataMap data = new JobDataMap();
			data.put("gribAccess", gribAccess);
			data.put("waveWatchSystem", this);
			jobDetail.setJobDataMap(data);
			// Create Scheduler, register Job and Listener
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.addJobListener(listener);
			// Start Scheduler
			scheduler.start();
		} catch (final Exception e) {
			scheduler = null;
			log.error(e);
		}
	}

	/**
	 * Shuts down the scheduler on object destruction.
	 */
	@Override
	protected void finalize() {
		// Shutdown scheduler.
		try {
			scheduler.shutdown();
		} catch (final SchedulerException e) {
			log.error(e);
		}
	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystem#getArchivedForecasts(float,
	 *      float, java.util.GregorianCalendar, java.util.GregorianCalendar)
	 */
	@Override
	public List<Forecast> getArchivedForecasts(final Point point,
			final Date fromDate, final Date toDate) {

		return persister.getArchivedForecasts(point, fromDate, toDate);
	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystem#getForecasts(edu.unicen.surfforecaster.server.domain.entity.forecasters.Point)
	 */
	@Override
	public List<Forecast> getForecasts(final Point gridPoint) {

		return persister.getLatestForecasts(gridPoint);
	}

	/**
	 * Decode the given grib file to obtain latest forecast information. Newly
	 * forecasts are persisted.
	 * 
	 * @param gribFile
	 * @throws IOException
	 */
	public void updateForecasts(final File gribFile) throws IOException {
		// Decode forecasts and persist them
		final ForecastFile forecastFile = createNewForecastFile(updateTempFilePath);
		try {
			for (int time = 0; time < forecastTimes; time++) {
				final Collection<Forecast> decodedForecasts = gribDecoder
						.decodeForecastForTime(gribFile, parameters, time);
				forecastFile.writeForecasts(decodedForecasts);
			}
			persister.updateLatestForecast(forecastFile);
		} catch (final IOException e) {
			log.error(e);
		}
		// Delete the temporal forecast file.
		if (!forecastFile.delete()) {
			log.warn("Temporary file:" + forecastFile.getAbsolutePath()
					+ " could not be deleted.");
		}

	}

	/**
	 * Input is a collection of grib file. Each grib file contains one
	 * parameter.s
	 * 
	 */

	public synchronized void importForecasts(
			final Collection<Collection<File>> files) throws IOException {
		log.info("Importing forecasts");
		// Disable automatic download of forecasts while performing import
		try {
			scheduler.standby();
		} catch (final SchedulerException e) {
			log.error("Error while standby scheduler", e);
		}
		// Drop indexes
		persister.startImportingForecasts();
		ForecastFile forecastFile = createNewForecastFile(importTempFile);
		// Decode forecasts and write them to csv file.
		for (final Iterator iterator = files.iterator(); iterator.hasNext();) {
			final Collection<File> collection = (Collection<File>) iterator
					.next();
			log.info("Writing to csv forecast for files: "
					+ collection.toArray()[0] + ";" + collection.toArray()[1]
					+ ";" + collection.toArray()[2]);
			// Get all available times in file
			final int times = gribDecoder
					.getTimes((File) collection.toArray()[0]);
			log.info("Total number of forecast times in file is:" + times);
			// Decode and write to file all times
			for (int time = 0; time < times; time++) {
				final long init = System.currentTimeMillis();
				final Collection<Forecast> decodedForecasts = gribDecoder
						.decodeForecastForTime(collection, parameters, time);
				// If file reached maximum size insert it into DB and empty
				// file.
				final long end = System.currentTimeMillis();
				forecastFile.writeForecasts(decodedForecasts);
				log.debug("Writed to csv time:" + time + " of: " + times
						+ " in: " + (end - init) + "ms.");
				if (forecastFile.length() > maxFileSize) {
					log.info("Inserting csv file of size(KBytes):"
							+ forecastFile.length() / 1024 + " into DB.");
					persister.importIntoArchive(forecastFile);
					log.info("Csv file inserted into DB successfully.");
					forecastFile = createNewForecastFile(importTempFile);
				}

			}
		}
		log.info("Inserting csv file of size(KBytes):" + forecastFile.length()
				/ 1024 + " into DB.");
		persister.importIntoArchive(forecastFile);
		log.info("Csv file inserted into DB successfully.");
		// Delete the temporal forecast file.
		if (!forecastFile.delete()) {
			log.warn("Temporary file:" + forecastFile.getAbsolutePath()
					+ " could not be deleted.");
		}

		log.info("Import of forecasts done successfully");
		// Create indexes
		persister.stopImportingForecasts();
		// Enable scheduler
		try {
			scheduler.start();
		} catch (final SchedulerException e) {
			log.error("Error while re starting scheduler", e);
		}

	}

	/**
	 * Determines if the given point belongs to this system grid.
	 */
	@Override
	public boolean isGridPoint(final Point point) {
		return persister.isGridPoint(point);
	}

	/**
	 * Obtain the neighbors of a grid point.
	 * 
	 * @param point
	 *            the point to look for neighbors.
	 * @param distance
	 *            the maximum distance of the neighbors. (In degrees).
	 * @return the neighbors points
	 */
	@Override
	public List<Point> getPointNeighbors(final Point point) {
		final List<Point> gridPoints = persister.getValidGridPoints();
		final List<Point> neighbors = new ArrayList<Point>();
		for (final Iterator<Point> iterator = gridPoints.iterator(); iterator
				.hasNext();) {
			final Point gridPoint = iterator.next();

			if (Math.abs(point.getLatitude() - gridPoint.getLatitude()) < gridSizeY
					&& Math
							.abs(point.getLongitude()
									- gridPoint.getLongitude()) < gridSizeX) {
				neighbors.add(gridPoint);
			}
		}
		return neighbors;
	}

	private ForecastFile createNewForecastFile(final String filePath)
			throws IOException {
		// create a csv file
		final ForecastFile forecastFile = new ForecastFile(filePath, parameters);
		// Delete if file exists
		if (forecastFile.exists()) {
			final boolean deleted = forecastFile.delete();
			if (!deleted)
				throw new RuntimeException("Error deleting temporary file("
						+ forecastFile.getAbsolutePath()
						+ "). Could not delete. Try removing file manually.");
		}
		// Create new file
		forecastFile.createNewFile();
		return forecastFile;
	}

	/**
	 * @return the list of {@link WaveWatchParameter} that this system supports.
	 * 
	 */
	@Override
	public List<WaveWatchParameter> getParameters() {
		return parameters;
	}

	/**
	 * The last time that the system successfully updated forecasts.
	 * 
	 * @return
	 */
	@Override
	public Date getLatestForecastTime() {
		return persister.getLatestForecastTime();
	}

	/**
	 * The next time that the system will try to update the forecasts.
	 * 
	 * @return
	 */
	public Date getNextUpdateTime() {
		return trigger.getNextFireTime();
	}

	/**
	 * The last time in which the system tried to update the forecasts.
	 * 
	 * @return
	 */
	public Date getLastUpdateTime() {
		return trigger.getPreviousFireTime();
	}

	@Override
	public String getName() {
		return name;
	}

}
