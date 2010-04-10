/**
 * 
 */
package edu.unicen.experimenter;

import java.io.IOException;

import jxl.write.WriteException;

import org.junit.Test;

/**
 * @author esteban
 * 
 */
public class ExperimenterTest {
	@Test
	public void generateExcel() throws WriteException, IOException {
		final Experimenter e = Experimenter.getInstance();
		e.generateExcel("nshore", false);

	}
}
