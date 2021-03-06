/**
 * 
 */
package edu.unicen.experimenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import jxl.write.WriteException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.unicen.experimenter.dao.DataSetDAO;
import edu.unicen.experimenter.dao.ResultDAO;
import edu.unicen.experimenter.export.ExcelWriter;
import edu.unicen.experimenter.export.ResultTransformer;

/**
 * @author esteban
 * 
 */
public class ResultTransformerTest {
	public static void main(final String[] args) throws WriteException,
			IOException {
		final ClassPathXmlApplicationContext ctxt = new ClassPathXmlApplicationContext(
				"dao.xml");
		final DataSetDAO bean = (DataSetDAO) ctxt.getBean("dataSetDAO");
		final DataSource dataSource = (DataSource) ctxt.getBean("dataSource");
		final List<Result> testResults = getTestResults(bean, dataSource);
		final Map columnNamesToAverage = initAverageMap();
		final List<Result> averageResults = ResultTransformer.averageResults(
				testResults, columnNamesToAverage);
		final List<Result> addExtraColumns = ResultTransformer
				.addExtraColumns(averageResults);
		final Map columnNames = initMap();
		ExcelWriter.generateExcel("testOutputAveraged.xls", addExtraColumns,
				columnNames);

	}

	/**
	 * @return
	 */
	private static Map initAverageMap() {
		final Map columnNames = new HashMap();

		columnNames.put("Correlation", "Avg_Correlation_coefficient");

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
		columnNames.put("Beach", "beach");
		columnNames.put("Generation", "GenerationOption");

		return columnNames;
	}

	private static List<Result> getTestResults(final DataSetDAO bean,
			final DataSource dataSource) {

		final ResultDAO resultBrowser = new ResultDAO();
		resultBrowser.setDataSetDAO(bean);
		resultBrowser.setDataSource(dataSource);

		final List<Result> result = resultBrowser
				.getResultsByExperiment("BuoyExperiment");
		return result;
	}
}
