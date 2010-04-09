package edu.unicen.experimenter;

import java.io.File;
import java.util.List;
import java.util.Observable;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.unicen.experimenter.dao.DataSetDAO;
import edu.unicen.experimenter.datasetgenerator.DataSet;
import edu.unicen.experimenter.datasetgenerator.DataSetGenerator;
import edu.unicen.experimenter.evaluator.Evaluator;

/**
 * Experimenter makes possible: 1) Generation of datasets 2) Evaluation of
 * datasets. 3) Access to DB archives of 1(working) and 2(Pending).
 * 
 * @author esteban
 */
public class Experimenter extends Observable {
	/**
	 * The DataSet Data access Object.
	 */
	private final DataSetDAO dataSetDAO;
	/**
	 * The singleton of this class.
	 */
	private static Experimenter experimenter;
	/**
	 * The DataSetGenerator used to generate DataSets.
	 */
	private final DataSetGenerator dataSetGenerator = new DataSetGenerator();
	/**
	 * The Evaluator used to evaluate datasets.
	 */
	final Evaluator evaluator = new Evaluator();

	/**
	 * @param dao
	 */
	private Experimenter(final DataSetDAO dao) {
		dataSetDAO = dao;
	}

	/**
	 * @param args
	 */
	public static Experimenter getInstance() {
		if (experimenter == null) {
			final ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
					new String[] { "dao.xml", });
			final DataSetDAO dao = (DataSetDAO) appContext
					.getBean("dataSetDAO");
			experimenter = new Experimenter(dao);
		}
		return experimenter;
	}

	/**
	 * Obtain a list of all the data sets available in the db.
	 */
	public List<DataSet> getAllDataSets() {
		return dataSetDAO.getAllDataSets();
	}

	/**
	 * Runs the dataset generation and save generated datasets into DB.
	 * 
	 * @param xml
	 *            Containning the generation configuration for each dataset to
	 *            generate.
	 * @throws Exception
	 */
	public void generateAndSaveDataSets(final File xml) throws Exception {
		final Thread t1 = new Thread() {
			@Override
			public void run() {
				List<DataSet> generateFromXML;
				try {
					generateFromXML = dataSetGenerator.generateFromXML(xml);
					for (final DataSet dataSet : generateFromXML) {
						dataSetDAO.add(dataSet);
					}
					setChanged();
					notifyObservers(generateFromXML);
				} catch (final Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		t1.start();

	}

	/**
	 * Evaluates each dataSet, with the given list of classifiers.
	 * 
	 * @param selectedDataSets
	 *            the dataSets to evaluate
	 * @param xmlClassifiers
	 *            xml containning each classifier to use, and its corresponding
	 *            parameters
	 * @throws Exception
	 */
	public void evaluate(final List<DataSet> selectedDataSets,
			final File xmlClassifiers) throws Exception {

		evaluator.evaluate(xmlClassifiers, selectedDataSets);

	}

}
