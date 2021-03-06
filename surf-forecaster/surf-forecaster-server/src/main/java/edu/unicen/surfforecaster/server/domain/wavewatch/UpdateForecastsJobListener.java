/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.wavewatch;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

/**
 * Listener for the Downloader Job. When job executed successfully notifies the
 * list of observers. If not the job is reescheduled.
 * 
 * @author esteban
 * 
 */
public class UpdateForecastsJobListener implements JobListener {
	/**
	 * Max number of times to reschedule the job.
	 */
	private static final int MAX_RESCHEDULINGS = 3;
	/**
	 * The time ahead to reschedule the download job.
	 */
	private static final long REESCHEDULE_TIME = 30000;
	/**
	 * The name of the job this listener is associated with.
	 */
	private String name;
	/**
	 * Number of reeschedulings done so far.
	 */
	private int reschedulings = 0;
	/**
	 * The logger.
	 */
	private final Logger log = Logger.getLogger(this.getClass());

	public UpdateForecastsJobListener(String listenerName) {
		this.name = listenerName;
	}

	/**
	 * @see org.quartz.JobListener#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @see org.quartz.JobListener#jobExecutionVetoed(org.quartz.JobExecutionContext)
	 */
	@Override
	public void jobExecutionVetoed(final JobExecutionContext arg0) {
		// DO NOTHING
	}

	/**
	 * @see org.quartz.JobListener#jobToBeExecuted(org.quartz.JobExecutionContext)
	 */
	@Override
	public void jobToBeExecuted(final JobExecutionContext arg0) {
		// DO NOTHING
	}

	/**
	 * Verify if download job ended successfully. If download was correct, then
	 * notify observers with the list of downloaded files. If download job
	 * failed then rescheduled job. Number of rescheduling is limited to:
	 * {@link UpdateForecastsJobListener#MAX_RESCHEDULINGS}
	 * 
	 * @see org.quartz.JobListener#jobWasExecuted(org.quartz.JobExecutionContext,
	 *      org.quartz.JobExecutionException)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void jobWasExecuted(final JobExecutionContext ctxt,
			final JobExecutionException exception) {
		// If download was correct
		if (exception == null) {
			log.info("WaveWatchSystemImpl Job was executed successfully");
			reschedulings = 0;
		} else {
			log.error(
					"WaveWatchSystemImpl Job failed on execution. Reason was:",
					exception);
			if (reschedulings < MAX_RESCHEDULINGS) {
				log.info("Rescheduling job");
				final Date newExecutionTime = new Date(new Date().getTime()
						+ REESCHEDULE_TIME);
				final Trigger trigger = new SimpleTrigger("RetryTriggerNumber:"
						+ reschedulings, "DownloadTriggers", newExecutionTime);
				trigger.setJobName(ctxt.getJobDetail().getName());
				trigger.setGroup(ctxt.getJobDetail().getGroup());
				try {
					ctxt.getScheduler().scheduleJob(trigger);
					reschedulings++;
					log.info("Download Job was rescheduled to: "
							+ newExecutionTime);
					return;
				} catch (final SchedulerException e) {
					// TODO que hacemos cuando pasa esto?
					log.error("Could not reeschedule job.", e);
					throw new RuntimeException("CouldNotReeschedule Job");
				}
			} else {
				log
						.error("Download job reached the maximun of reeschedulings.");
				reschedulings = 0;
			}

		}
	}
}
