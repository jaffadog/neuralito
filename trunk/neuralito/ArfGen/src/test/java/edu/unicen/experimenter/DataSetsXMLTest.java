/**
 * 
 */
package edu.unicen.experimenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;

import edu.unicen.experimenter.datasetgenerator.DataSet;
import edu.unicen.experimenter.datasetgenerator.generators.OneGridPointStrategy;
import edu.unicen.experimenter.datasetgenerator.xml.DataSetConfiguration;
import edu.unicen.experimenter.datasetgenerator.xml.DataSetGeneratorConfiguration;
import edu.unicen.experimenter.util.Util;

/**
 * @author esteban
 * 
 */
public class DataSetsXMLTest {
	public static void main(final String[] args) {
		final XStream xstream = new XStream();
		// XStream xstream = new XStream(new DomDriver()); // does not require
		// XPP3 library
		xstream.alias("DataSet", DataSet.class);

		final DataSetGeneratorConfiguration config = new DataSetGeneratorConfiguration();
		final DataSetConfiguration dsConfig = new DataSetConfiguration();
		dsConfig.setBeach("nshore");
		dsConfig.setIncremental(true);
		dsConfig.setPercentajeIncrement(15);
		dsConfig.setInstanceNumber(100);
		dsConfig.setStrategyName(OneGridPointStrategy.class.getName());
		dsConfig.setByYears(true);
		dsConfig.setNumberOfMonths(12);
		dsConfig.setYears(new String[] { "1997", "1998" });
		dsConfig.setRepetitions(2);
		dsConfig.setRepetitive(true);
		final Map<String, Serializable> strategyOptions = new HashMap<String, Serializable>();
		strategyOptions
				.put(
						"beach",
						"-S 0.0080 -C 0.7 -T 0.0080 -P 1.0E-12 -N 0 -K \\\" weka.classifiers.functions.supportVector.RBFKernel -C 250007 -G 0.5\"");
		strategyOptions.put("grid1Lat", Util.NORTH);
		strategyOptions.put("grid1Lon", Util.WEST);
		dsConfig.setStrategyOptions(strategyOptions);
		final List<DataSetConfiguration> a = new ArrayList<DataSetConfiguration>();
		a.add(dsConfig);
		config.setDataSets(a);
		final String xml = xstream.toXML(config);
		System.out.println(xml);

	}
}
