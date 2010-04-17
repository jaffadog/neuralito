package edu.unicen.experimenter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import jxl.write.WriteException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.unicen.experimenter.dao.DataSetDAO;
import edu.unicen.experimenter.dao.ResultDAO;
import edu.unicen.experimenter.datasetgenerator.DataSet;
import edu.unicen.experimenter.datasetgenerator.DataSetGenerator;
import edu.unicen.experimenter.evaluator.Evaluator;
import edu.unicen.experimenter.export.ExcelWriter;
import edu.unicen.experimenter.export.ResultTransformer;

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
	 * ResultBrowser
	 */
	final ResultDAO resultDAO;
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
	 * @param resultBrowser2
	 */
	private Experimenter(final DataSetDAO dao, final ResultDAO resultDAO) {
		dataSetDAO = dao;
		this.resultDAO = resultDAO;
		evaluator.setResults(resultDAO);
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
			final ResultDAO resultDAO = (ResultDAO) appContext
					.getBean("resultDAO");
			experimenter = new Experimenter(dao, resultDAO);
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
	public void evaluate(final String experimentName,
			final List<DataSet> selectedDataSets, final File xmlClassifiers)
			throws Exception {
		final Thread t = new Thread() {
			@Override
			public void run() {
				try {

					evaluator.evaluate(experimentName, xmlClassifiers,
							selectedDataSets);
					setChanged();
					notifyObservers();
				} catch (final Exception e) {

					e.printStackTrace();
				}

			}
		};
		t.start();

	}

	/**
	 * Search all the results for the specified beach and save them into an
	 * excel file.
	 * 
	 * @param beach
	 * @param optimizedParameters
	 * @throws WriteException
	 * @throws IOException
	 */
	public void generateExcelByBeach(final String beach, final String outputFile)
			throws WriteException, IOException {
		// Obtain results as written by weka
		System.out.println("Exporting results for beach \"" + beach + "\"");
		final List<Result> results = resultDAO.getResult(beach);
		// Average the results
		final Map columnNamesToAverage = initAverageMap();
		List<Result> transformedResults = ResultTransformer.averageResults(
				results, columnNamesToAverage);
		// Adds extra columns data to results.
		transformedResults = ResultTransformer
				.addExtraColumns(transformedResults);
		// Configure columns to display in the excel file
		final Map columnNames = initMap();
		// Write Excel.
		ExcelWriter.generateExcel(outputFile + ".xls", transformedResults,
				columnNames);

	}

	/**
	 * @return
	 */
	private static Map initAverageMap() {
		final Map columnNames = new HashMap();

		columnNames.put("Correlation", "Avg_Correlation_coefficient");
		// columnNames.put("Correlation", "Avg_Correlation_coefficient");

		return columnNames;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Map initMap() {
		final Map columnNames = new HashMap();
		columnNames.put("DataSetId", "Key_Dataset");
		columnNames.put("Classifier", "Key_Scheme");
		columnNames.put("Correlation", "Avg_Correlation_coefficient");
		columnNames.put("CorrelationDev", "Avg_Correlation_coefficientDev");
		columnNames.put("Number of instances",
				"Avg_Number_of_training_instances");
		columnNames.put("Beach", "beach");
		columnNames.put("Generation", "GenerationOption");

		return columnNames;
	}

	/**
	 * @param experimentName
	 * @param fileName
	 * @throws IOException
	 * @throws WriteException
	 */
	public void generateExcel(final String experimentName, final String fileName)
			throws WriteException, IOException {
		final List<Result> resultsByExperiment = resultDAO
				.getResultsByExperiment(experimentName);
		final Map columnNamesToAverage = initAverageMap();
		final List<Result> averageResults = ResultTransformer.averageResults(
				resultsByExperiment, columnNamesToAverage);
		final List<Result> addExtraColumns = ResultTransformer
				.addExtraColumns(averageResults);
		final Map columnNames = initMap();
		ExcelWriter.generateExcel(fileName + ".xls", addExtraColumns,
				columnNames);

	}

}
