/**
 * 
 */
package edu.unicen.surfforecaster.server;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author esteban
 * 
 */
public class QuartzTest {
	// Inits the application context to see if the Quartz job gets executed.
	// protected final ApplicationContext context = new
	// ClassPathXmlApplicationContext(
	// "/services.xml");

	@Test
	@Ignore
	public void doNothing() {
		try {
			Thread.sleep(30000);
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
