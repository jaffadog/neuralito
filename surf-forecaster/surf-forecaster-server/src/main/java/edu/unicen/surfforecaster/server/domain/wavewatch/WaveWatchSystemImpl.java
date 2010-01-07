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
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import edu.unicen.surfforecaster.server.dao.WaveWatchSystemPersistenceI;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.Point;
import edu.unicen.surfforecaster.server.domain.wavewatch.decoder.GribDecoder;
import edu.unicen.surfforecaster.server.domain.wavewatch.gribAccess.GribAccess;
import edu.unicen.surfforecaster.server.domain.wavewatch.gribAccess.GribAccessException;

/**
 * 
 * @author esteban wagner.
 * 
 */
public class WaveWatchSystemImpl implements WaveWatchSystem, Job {
	/**
	 * The logger.
	 */
	private Logger log = Logger.getLogger(this.getClass());
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
	private String name;

	/**
	 * Initialize System.
	 * 
	 * @param parameters
	 *            the wave parameters that this wavewatch system will support.
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
	public WaveWatchSystemImpl(String name,
			List<WaveWatchParameter> parameters,
			GribDecoder gribDecoder, String cronExpression,
			GribAccess gribAccess, WaveWatchSystemPersistenceI persister) {
		this.name = name;
		this.parameters = parameters;
		this.gribDecoder = gribDecoder;
		this.gribAccess = gribAccess;
		this.persister = persister;
		this.configureScheduler(cronExpression);
		log.info("Next forecast update time:" + this.trigger.getNextFireTime());
	}

	private void configureScheduler(String cronExpression) {
		try {
			// Create the job
			JobDetail jobDetail = new JobDetail("ForecastUpdateJob", null,
					UpdateForecastsJob.class);
			// Create the trigger
			this.trigger = new CronTrigger();
			this.trigger.setName("NOAA Grib Download Trigger");
			this.trigger.setCronExpression(cronExpression);
			// Create job listener
			JobListener listener = new UpdateForecastsJobListener(this.name);
			jobDetail.addJobListener(listener.getName());
			JobDataMap data = new JobDataMap();
			data.put("gribAccess", this.gribAccess);
			data.put("waveWatchSystem", this);
			jobDetail.setJobDataMap(data);
			// Create Scheduler, register Job and Listener
			this.scheduler = StdSchedulerFactory.getDefaultScheduler();
			this.scheduler.scheduleJob(jobDetail, trigger);
			this.scheduler.addJobListener(listener);
			// Start Scheduler
			this.scheduler.start();
		} catch (Exception e) {
			this.scheduler = null;
			log.error(e);
		}
	}

	/**
	 * Shuts down the scheduler on object destruction.
	 */
	protected void finalize() {
		// Shutdown scheduler.
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
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
	 * Method called by Scheduler when the the trigger is fired. This means that
	 * its time to obtain new forecasts and persist them into DB.
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// Obtain last forecasts in grib format
		File gribFile;
		try {
			gribFile = this.gribAccess.getLastGrib();
		} catch (GribAccessException e1) {
			throw new JobExecutionException();
		}
		updateForecasts(gribFile);


	}

	/**
	 * Decode the given grib file to obtain latest forecast information. Newly
	 * forecasts are persisted.
	 * 
	 * @param gribFile
	 */
	public void updateForecasts(File gribFile) {
		// Decode forecasts and persist them
		ForecastFile forecastFile = new ForecastFile("tempFilePath",
				this.parameters);
		try {
			for (int time = 0; time < forecastTimes; time++) {
				final Collection<Forecast> decodedForecasts = gribDecoder
						.decodeForecastForTime(gribFile, this.parameters, time);
				forecastFile.writeForecasts(decodedForecasts);
			}
			persister.updateLatestForecast(forecastFile);
		} catch (final IOException e) {
			log.error(e);
		}
		// Delete the temporal forecast file.
		forecastFile.delete();

	}

	/**
	 * Input is a collection of grib file. Each grib file contains one
	 * parameter.s
	 * 
	 */
	public void importForecasts(Collection<File> files) throws IOException {
		ForecastFile forecastFile = new ForecastFile("tempFilePath",
				this.parameters);
		int times = this.gribDecoder.getTimes((File) files.toArray()[0]);
			try {
			for (int time = 0; time < times; time++) {
					final Collection<Forecast> decodedForecasts = gribDecoder
						.decodeForecastForTime(files, this.parameters, time);
					forecastFile.writeForecasts(decodedForecasts);
				}

			} catch (final IOException e) {
				log.error(e);
			}

		persister.importIntoArchive(forecastFile);
		// Delete the temporal forecast file.
		forecastFile.delete();

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
	public List<Point> getPointNeighbors(final Point point, Double distance) {
		final List<Point> gridPoints = persister.getValidGridPoints();
		final List<Point> neighbors = new ArrayList<Point>();
		for (final Iterator<Point> iterator = gridPoints.iterator(); iterator
				.hasNext();) {
			final Point gridPoint = (Point) iterator.next();

			if (Math.abs(point.getLatitude() - gridPoint.getLatitude()) < distance
					&& Math
							.abs(point.getLongitude()
									- gridPoint.getLongitude()) < distance) {
				neighbors.add(gridPoint);
			}
		}
		return neighbors;
	}



	/**
	 * @return the list of {@link WaveWatchParameter} that this system supports.
	 * 
	 */
	@Override
	public List<WaveWatchParameter> getParameters() {
		return this.parameters;
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
		return this.trigger.getNextFireTime();
	}

	/**
	 * The last time in which the system tried to update the forecasts.
	 * 
	 * @return
	 */
	public Date getLastUpdateTime() {
		return this.trigger.getPreviousFireTime();
	}

	@Override
	public String getName() {
		return this.name;
	}



}
