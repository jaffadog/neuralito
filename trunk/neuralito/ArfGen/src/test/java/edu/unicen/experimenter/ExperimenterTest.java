/**
 * 
 */
package edu.unicen.experimenter;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import jxl.write.WriteException;

import org.junit.Test;

/**
 * @author esteban
 * 
 */
public class ExperimenterTest implements Observer {
	@Test
	public void generateExcel() throws WriteException, IOException {
		final Experimenter e = Experimenter.getInstance();
		e.generateExcel("dh", false);

	}

	public void generateDataSets() throws Exception {
		final Experimenter e = Experimenter.getInstance();
		final File xml = new File("src/main/resources/generation.xml");
		e.generateAndSaveDataSets(xml);
		e.addObserver(this);
		Thread.sleep(10000);

	}

	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(final Observable arg0, final Object arg1) {
		System.out.println("asdas");

	}

}
