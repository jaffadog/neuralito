/**
 * 
 */
package edu.unicen.experimenter.gui;

import java.io.File;
import java.util.List;

import edu.unicen.experimenter.Experimenter;
import edu.unicen.experimenter.datasetgenerator.DataSet;

/**
 * @author esteban
 * 
 */
public class Controller {

	Experimenter experimenter = Experimenter.getInstance();

	/**
	 * Obtain a list of all the datasets available in the model
	 * 
	 * @return
	 */
	public List<DataSet> getDataSets() {
		return experimenter.getAllDataSets();
	}

	public void generateDataSets(final File xml) throws Exception {
		experimenter.generateAndSaveDataSets(xml);
	}

	/**
	 * @param selectedDataSets
	 * @param xmlClassifiers
	 * @throws Exception
	 */
	public void evaluate(final List<DataSet> selectedDataSets,
			final File xmlClassifiers) throws Exception {
		experimenter.evaluate(selectedDataSets, xmlClassifiers);

	}
}
