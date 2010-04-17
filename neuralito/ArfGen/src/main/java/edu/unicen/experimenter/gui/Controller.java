/**
 * 
 */
package edu.unicen.experimenter.gui;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.write.WriteException;
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
	 * @param experimentName
	 * @throws Exception
	 */
	public void evaluate(final List<DataSet> selectedDataSets,
			final File xmlClassifiers, final String experimentName)
			throws Exception {
		experimenter.evaluate(experimentName, selectedDataSets, xmlClassifiers);

	}

	/**
	 * @param experimentName
	 * @param fileName
	 * @throws IOException
	 * @throws WriteException
	 */
	public void exportResultsByExperimentName(final String experimentName,
			final String fileName) throws WriteException, IOException {
		experimenter.generateExcel(experimentName, fileName);

	}

	/**
	 * @param beach
	 * @param fileName
	 * @throws IOException
	 * @throws WriteException
	 */
	public void exportResultsByBeach(final String beach, final String fileName)
			throws WriteException, IOException {
		experimenter.generateExcelByBeach(beach, fileName);

	}
}
