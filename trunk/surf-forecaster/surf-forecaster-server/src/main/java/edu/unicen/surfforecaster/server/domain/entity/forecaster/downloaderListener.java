/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

/**
 * @author esteban
 * 
 */
public class downloaderListener implements JobListener {
	private String name;
	private int numberOfTimes = 0;

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
		// TODO Auto-generated method stub

	}

	/**
	 * @see org.quartz.JobListener#jobToBeExecuted(org.quartz.JobExecutionContext)
	 */
	@Override
	public void jobToBeExecuted(final JobExecutionContext arg0) {
		// TODO Auto-generated method stub
		System.out.println("TO BE....");
	}

	/**
	 * @see org.quartz.JobListener#jobWasExecuted(org.quartz.JobExecutionContext,
	 *      org.quartz.JobExecutionException)
	 */
	@Override
	public void jobWasExecuted(final JobExecutionContext arg0,
			final JobExecutionException arg1) {
		System.out.println("WAS EXECUTED: " + numberOfTimes);
		if (arg1 != null && numberOfTimes < 8) {
			final Trigger trigger = new SimpleTrigger("RetryTriggerNumber:"
					+ numberOfTimes, "DownloadTriggers", new Date(new Date()
					.getTime() + 1000));
			trigger.setJobName(arg0.getJobDetail().getName());

			trigger.setGroup(arg0.getJobDetail().getGroup());

			trigger.getJobDataMap().put("esteban", "asdasasdasda11111111");
			arg0.getJobDetail().getJobDataMap().put("esteban",
					"adasdaasdasdadsa");
			try {
				arg0.getScheduler().scheduleJob(trigger);
			} catch (final SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if (numberOfTimes >= 3) {
				System.out
						.println("Stop retrying SEVERE ERROR DOWNLOADING FILES.");
			}
		}
		numberOfTimes++;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

}
