/**
 * 
 */
package edu.unicen.surfforecaster.server;

import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author esteban
 * 
 */
public abstract class SpringContextAwareTest {

	/**
	 * The spring application context to user for the test.
	 */
	protected ClassPathXmlApplicationContext context;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("/services.xml");
	}
}
