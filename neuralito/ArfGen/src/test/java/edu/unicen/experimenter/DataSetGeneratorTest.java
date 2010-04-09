/**
 * 
 */
package edu.unicen.experimenter;

import java.io.File;
import java.util.List;

import edu.unicen.experimenter.datasetgenerator.DataSet;
import edu.unicen.experimenter.datasetgenerator.DataSetGenerator;

/**
 * @author esteban
 * 
 */

public class DataSetGeneratorTest {

	public static void main(final String[] args) throws Exception {
		// The dataset generator
		final DataSetGenerator t = new DataSetGenerator();
		// XML with dataset generation configuration
		final File xml = new File("src/main/resources/generation.xml");
		// Generate datasets
		final List<DataSet> generateFromXML = t.generateFromXML(xml);
		// Info
		System.out.println(generateFromXML.size()
				+ " datasets have been generated.");

	}
}
