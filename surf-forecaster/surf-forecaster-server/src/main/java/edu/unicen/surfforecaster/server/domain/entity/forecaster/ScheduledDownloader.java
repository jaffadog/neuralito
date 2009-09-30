/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author esteban
 * 
 */
public class ScheduledDownloader extends QuartzJobBean {
	// FileDownloader downloader;
	Logger log = Logger.getLogger(this.getClass());

	/**
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(final JobExecutionContext context)
			throws JobExecutionException {
		System.out.println("THE DOWNLOADER HAS BEEN EXECUTED AND FAILED : ");
		System.out.println(context.getMergedJobDataMap().get("esteban"));
		System.out.println("refire: " + context.getRefireCount());

		throw new JobExecutionException();

		// System.out.println("THE DOWNLOADER HAS BEEN EXECUTED");

	}

	private int timeout;

	/**
	 * Setter called after the ExampleJob is instantiated with the value from
	 * the JobDetailBean (5)
	 */
	public void setTimeout(final int timeout) {
		this.timeout = timeout;
	}
}
