/**
 * 
 */
package edu.unicen.surfforecaster.server;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerBean;

/**
 * @author esteban
 * 
 */
public class QuartzTest {
//	 Inits the application context to see if the Quartz job gets executed.
	 protected final ApplicationContext context = new
	 ClassPathXmlApplicationContext(
	 "/services.xml");

	@Test
	@Ignore
	public void doNothing() {
		try {
			Thread.sleep(1);
			CronTriggerBean bean  = (CronTriggerBean) context.getBean("simpleTrigger");
			System.out.println(bean.getNextFireTime());
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
