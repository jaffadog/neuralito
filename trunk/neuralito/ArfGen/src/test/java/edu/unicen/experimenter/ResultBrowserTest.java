/**
 * 
 */
package edu.unicen.experimenter;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.unicen.experimenter.dao.DataSetDAO;
import edu.unicen.experimenter.dao.ResultDAO;

/**
 * @author esteban
 * 
 */
public class ResultBrowserTest {

	public static void main(final String[] args) {
		final ClassPathXmlApplicationContext ctxt = new ClassPathXmlApplicationContext(
				"dao.xml");
		final DataSetDAO bean = (DataSetDAO) ctxt.getBean("dataSetDAO");
		final DataSource dataSource = (DataSource) ctxt.getBean("dataSource");

		final ResultDAO resultBrowser = new ResultDAO();
		resultBrowser.setDataSetDAO(bean);
		resultBrowser.setDataSource(dataSource);

		final List<Result> result = resultBrowser.getResult("nshore");
		System.out.println("Found: " + result.size() + " results");
		System.out.println(result.toString());
	}
}
