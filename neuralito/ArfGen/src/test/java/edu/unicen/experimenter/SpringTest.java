package edu.unicen.experimenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.unicen.experimenter.dao.DataSetDAO;
import edu.unicen.experimenter.datasetgenerator.DataSet;
import edu.unicen.experimenter.datasetgenerator.DataSetInstance;
import edu.unicen.experimenter.datasetgenerator.generators.OneGridPointStrategy;

/**
 * 
 */

/**
 * @author esteban
 * 
 */
public class SpringTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception {
		// TODO Auto-generated method stub
		final ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				new String[] { "dao.xml", });
		final DataSetDAO dsDao = (DataSetDAO) appContext.getBean("dataSetDAO");
		final OneGridPointStrategy st = new OneGridPointStrategy();
		final HashMap<String, Serializable> options = new HashMap<String, Serializable>();
		options.put("est", "teban");
		final String[] attr = { "a", "s" };
		final List a = new ArrayList();
		a.add(new DataSetInstance(null));
		final DataSet dataSet = new DataSet();
		dsDao.add(dataSet);

	}
}
