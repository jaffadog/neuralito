/**
 * 
 */
package edu.unicen.surfforecaster.server;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author esteban
 * 
 */
public class QuartzTest {
	protected final ApplicationContext context = new ClassPathXmlApplicationContext(
			"/services.xml");

	@Test
	public void doNothing() {
		try {
			Thread.sleep(30000);
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
