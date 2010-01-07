package edu.unicen.surfforecaster.server.domain.wavewatch;

import java.io.File;

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

	private Logger log = Logger.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		// Obtain last forecasts in grib format
		JobDataMap data = jobContext.getJobDetail().getJobDataMap();
		GribAccess gribAccess = (GribAccess) data.get("gribAccess");
		WaveWatchSystemImpl waveWatchSystem = (WaveWatchSystemImpl) data
				.get("waveWatchSystem");
		File gribFile;
		try {
			gribFile = gribAccess.getLastGrib();
		} catch (GribAccessException e1) {
			throw new JobExecutionException();
		}
		waveWatchSystem.updateForecasts(gribFile);
		gribFile.delete();
		log
				.info("Forecasts were updated successfully. Next update will be on: "
						+ jobContext.getNextFireTime());
	}

}