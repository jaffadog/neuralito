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

/**
 * @author esteban
 * 
 */
public class ResultExporterTest {
	public static void main(final String[] args) throws WriteException,
			IOException {

		final Map columnNames = initMap();
		ExcelWriter.generateExcel("testOutput.xls", getTestResults(),
				columnNames);

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

		return columnNames;
	}

	private static List<Result> getTestResults() {
		final ClassPathXmlApplicationContext ctxt = new ClassPathXmlApplicationContext(
				"dao.xml");
		final DataSetDAO bean = (DataSetDAO) ctxt.getBean("dataSetDAO");
		final DataSource dataSource = (DataSource) ctxt.getBean("dataSource");

		final ResultDAO resultBrowser = new ResultDAO();
		resultBrowser.setDataSetDAO(bean);
		resultBrowser.setDataSource(dataSource);

		final List<Result> result = resultBrowser.getResult("nshore");
		return result;
	}
}
