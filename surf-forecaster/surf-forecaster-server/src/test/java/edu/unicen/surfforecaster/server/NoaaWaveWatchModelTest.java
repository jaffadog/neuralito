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
public class NoaaWaveWatchModelTest {
	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"/dao.xml");

	@Test
	public void init() {
		System.out.println("Holas \n asda");
	}
}
