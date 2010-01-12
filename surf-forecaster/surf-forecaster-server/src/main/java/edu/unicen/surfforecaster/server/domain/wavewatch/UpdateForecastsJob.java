package edu.unicen.surfforecaster.server.domain.wavewatch;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import edu.unicen.surfforecaster.server.domain.wavewatch.gribAccess.GribAccess;
import edu.unicen.surfforecaster.server.domain.wavewatch.gribAccess.GribAccessException;

/**
 * Job that gets fires by Quartz scheduler. On each execution it downloads the
 * latest wavewatch forecast issued by NOAA. After download file is given to
 * {@link WaveWatchSystem} to process it.
 * 
 * @author esteban
 * 
 */
public class UpdateForecastsJob implements Job {

	private final Logger log = Logger.getLogger(this.getClass());

	@Override
	public void execute(final JobExecutionContext jobContext)
			throws JobExecutionException {
		// Obtain last forecasts in grib format
		final JobDataMap data = jobContext.getJobDetail().getJobDataMap();
		final GribAccess gribAccess = (GribAccess) data.get("gribAccess");
		final WaveWatchSystemImpl waveWatchSystem = (WaveWatchSystemImpl) data
				.get("waveWatchSystem");
		File gribFile;
		try {
			gribFile = gribAccess.getLastGrib();
		} catch (final GribAccessException e1) {
			throw new JobExecutionException();
		}
		try {
			waveWatchSystem.updateForecasts(gribFile);
		} catch (final IOException e) {
			log.error("IO exception while updating forecasts.", e);
			throw new JobExecutionException(e);
		}
		gribFile.delete();
		log
				.info("Forecasts were updated successfully. Next update will be on: "
						+ jobContext.getNextFireTime());
	}

}