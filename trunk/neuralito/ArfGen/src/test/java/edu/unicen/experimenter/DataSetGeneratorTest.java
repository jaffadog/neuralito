/**
 * 
 */
package edu.unicen.experimenter;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import edu.unicen.experimenter.datasetgenerator.DataSet;
import edu.unicen.experimenter.datasetgenerator.DataSetGeneratorYears;
import edu.unicen.experimenter.datasetgenerator.DataSetInstance;
import edu.unicen.experimenter.util.InstancesCreator;
import edu.unicen.experimenter.util.Util;

/**
 * @author esteban
 * 
 */

public class DataSetGeneratorTest {

	public static void main(final String[] args) throws Exception {
		// The dataset generator
		final DataSetGeneratorYears t = new DataSetGeneratorYears();
		// XML with dataset generation configuration
		final File xml = new File("src/main/resources/generationNLectures.xml");
		// Generate datasets
		final List<DataSet> generateFromXML = t.generateFromXML(xml);
		for (final DataSet dataSet : generateFromXML) {
			System.out.println(" dataset: with "
					+ dataSet.getNumberOfInstances() + " instances");
			final Collection<DataSetInstance> instances = dataSet
					.getInstances();
			for (final Iterator iterator = instances.iterator(); iterator
					.hasNext();) {
				final DataSetInstance dataSetInstance = (DataSetInstance) iterator
						.next();

				System.out.println(Util.getDateFormatter().format(
						dataSetInstance.getDate().getTime()));
				Util.printWekaInstances(InstancesCreator
						.generateTrainningData(dataSet));

			}

			// Util.printCollection(col)

		}
		// Info
		System.out.println(generateFromXML.size()
				+ " datasets have been generated.");

	}
}
