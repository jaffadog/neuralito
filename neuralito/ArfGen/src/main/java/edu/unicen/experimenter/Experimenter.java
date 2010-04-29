package edu.unicen.experimenter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import jxl.write.WriteException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.thoughtworks.xstream.XStream;

import edu.unicen.experimenter.dao.DataSetDAO;
import edu.unicen.experimenter.dao.ResultDAO;
import edu.unicen.experimenter.datasetgenerator.DataSet;
import edu.unicen.experimenter.datasetgenerator.DataSetGeneratorYears;
import edu.unicen.experimenter.datasetgenerator.xml.DataSetGeneratorConfiguration;
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
	private final DataSetGeneratorYears dataSetGenerator = new DataSetGeneratorYears();
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
					// Read configuration object from XML
					final XStream xstream = new XStream();
					xstream.alias("DataSet", DataSet.class);
					final DataSetGeneratorConfiguration configuration = (DataSetGeneratorConfiguration) xstream
							.fromXML(new FileInputStream(xml));
					final String dataSetGroupName = configuration
							.getDataSetGroupName();
					if (!dataSetDAO.existsDataSetGroup(dataSetGroupName)) {
						generateFromXML = dataSetGenerator.generateFromXML(xml);
						for (final DataSet dataSet : generateFromXML) {
							dataSetDAO.add(dataSet);
						}
						setChanged();
						notifyObservers(generateFromXML);
					} else {
						System.out
								.println("DataSet Group name already exists.");
					}
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

	public void evaluateTestInstances(final String experimentName,
			final List<DataSet> selectedDataSets,
			final List<DataSet> selectedTestDataSets, final File xmlClassifiers)
			throws Exception {
		final Thread t = new Thread() {
			@Override
			public void run() {
				try {

					evaluator.testSetEvaluation(experimentName, xmlClassifiers,
							selectedDataSets, selectedTestDataSets.get(0));
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
		columnNames.put("Years", "Years");
		columnNames.put("Number of trainning instances",
				"Avg_Number_of_training_instances");
		columnNames.put("Correlation", "Avg_Correlation_coefficient");
		columnNames.put("CorrelationDev", "Avg_Correlation_coefficientDev");
		columnNames.put("MAE", "Avg_Mean_absolute_error");
		columnNames.put("MAEDev", "Dev_Mean_absolute_error");
		columnNames.put("Beach", "beach");
		columnNames.put("GenerationStrategy", "Strategy");
		columnNames.put("Generation", "GenerationOption");
		return columnNames;
	}

	/**
	 * @return
	 */
	private static Map initAverageTestMap() {
		final Map columnNames = new HashMap();

		columnNames.put("Correlation", "Correlation_coefficient");
		columnNames.put("Absolute Error", "Mean_absolute_error");
		// columnNames.put("Correlation", "Avg_Correlation_coefficient");

		// columnNames.put("Correlation", "Avg_Correlation_coefficient");

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
		List<Result> resultsByExperiment = resultDAO
				.getResultsByExperiment(experimentName);
		final Map excelColumnNames;
		if (isCrossValidationExperiment(resultsByExperiment)) {
			final Map columnNamesToAverage = initAverageMap();
			resultsByExperiment = ResultTransformer.averageResults(
					resultsByExperiment, columnNamesToAverage);
			excelColumnNames = initMap();
		} else {
			final Map columnToAverage = initAverageTestMap();
			resultsByExperiment = ResultTransformer
					.averageSameTrainningSizeResults(resultsByExperiment,
							columnToAverage);
			excelColumnNames = initMapTestDataSet();
		}
		final List<Result> addExtraColumns = ResultTransformer
				.addExtraColumns(resultsByExperiment);

		ExcelWriter.generateExcel(fileName + ".xls", addExtraColumns,
				excelColumnNames);

	}

	/**
	 * @return
	 */
	private Map initMapTestDataSet() {
		final Map columnNames = new HashMap();
		columnNames.put("DataSetId", "Key_Dataset");
		columnNames.put("Classifier", "Key_Scheme");
		columnNames.put("Years", "Years");
		columnNames.put("Number of trainning instances",
				"Number_of_training_instances");
		columnNames.put("Number of testing instances",
				"Number_of_testing_instances");
		columnNames.put("N Correlation", "Correlation_coefficient");
		columnNames.put("N CorrelationDev", "Correlation_coefficientDev");
		// columnNames.put("CorrelationDev", "Avg_Correlation_coefficientDev");
		columnNames.put("MAE", "Mean_absolute_error");
		columnNames.put("MAEDev", "Mean_absolute_errorDev");
		// columnNames.put("MAEDev", "Dev_Mean_absolute_error");
		columnNames.put("Beach", "beach");
		columnNames.put("GenerationStrategy", "Strategy");
		columnNames.put("Generation", "GenerationOption");
		return columnNames;
	}

	/**
	 * @param resultsByExperiment
	 * @return
	 */
	private boolean isCrossValidationExperiment(
			final List<Result> resultsByExperiment) {
		final Result result = resultsByExperiment.get(0);
		if (result.getResult("Num_Fold") == null)
			return false;
		else
			return true;
	}

	/**
	 * @return
	 * 
	 */
	public DataSetDAO getDataSetDAO() {
		// TODO Auto-generated method stub
		return dataSetDAO;
	}

	/**
	 * @return the resultDAO
	 */
	public ResultDAO getResultDAO() {
		return resultDAO;
	}

}
