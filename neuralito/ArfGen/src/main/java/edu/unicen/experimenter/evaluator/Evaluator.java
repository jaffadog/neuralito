package edu.unicen.experimenter.evaluator;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;

import weka.classifiers.Classifier;
import weka.core.Utils;
import weka.experiment.CrossValidationResultProducer;
import weka.experiment.DatabaseResultListener;
import weka.experiment.Experiment;
import weka.experiment.RegressionSplitEvaluator;

import com.thoughtworks.xstream.XStream;

import edu.unicen.experimenter.datasetgenerator.DataSet;
import edu.unicen.experimenter.evaluator.xml.ClassifierConfiguration;
import edu.unicen.experimenter.evaluator.xml.ClassifiersConfiguration;
import edu.unicen.experimenter.util.InstancesCreator;

/**
 * Wrapper around WEKA Experimenter API. Given a list of classifiers and
 * datasets, evaluates each one using 10 times 10-fold-CV. Results are saved to
 * DB.
 * 
 */
public class Evaluator {
	/**
	 * Evaluates each dataset with each classifier.
	 * 
	 * @param classifiersConf
	 *            container of the configurations of each classifier to use.
	 * @param dataSets
	 *            list of datasets to evaluate.
	 * @throws Exception
	 */
	public void evaluate(final ClassifiersConfiguration classifiersConf,
			final List<DataSet> dataSets) throws Exception {

		// Obtain classifiers to use from configuration object.
		final Object[] classifiers2 = getClassifiers(classifiersConf);

		// For each classifier evaluate DataSets
		for (final Object object : classifiers2) {

			// 1. setup the experiment
			System.out.println("Setting up...");
			final Experiment exp = new Experiment();
			exp.setPropertyArray(new Classifier[0]);
			exp.setUsePropertyIterator(true);

			// regression
			RegressionSplitEvaluator se = null;
			se = new RegressionSplitEvaluator();
			se.setClassifier((Classifier) object);

			final AveragingResultProducerExtension rp = new AveragingResultProducerExtension();
			rp.setCalculateStdDevs(true);
			final DatabaseResultListener rl = new DatabaseResultListener();

			rl.setDatabaseURL("jdbc:mysql://localhost:3306/weka");
			rl.setUsername("root");
			rl.setPassword("");

			rp.setSecondaryResultListener(rl);

			final CrossValidationResultProducer cvrp = new CrossValidationResultProducer();
			cvrp.setNumFolds(10);
			cvrp.setSplitEvaluator(se);
			rp.setResultProducer(cvrp);

			exp.setResultProducer(rp);
			exp.setUsePropertyIterator(false);
			exp.setPropertyArray(null);
			exp.setPropertyPath(null);
			// RUNS
			exp.setRunLower(1);
			exp.setRunUpper(10);

			// classifier
			final Object[] classifiers = getClassifiers(classifiersConf);
			exp.setPropertyArray(classifiers);

			// datasets
			final DefaultListModel model = getDefaultListModel(dataSets);
			exp.setDatasets(model);

			// result
			final DatabaseResultListener irl = new DatabaseResultListener();
			irl.setDatabaseURL("jdbc:mysql://localhost:3306/weka");
			irl.setUsername("root");
			irl.setPassword("");

			exp.setResultListener(irl);

			// 2. run experiment
			System.out.println("Initializing...");
			exp.initialize();
			System.out.println("Running...");
			exp.runExperiment();
			System.out.println("Finishing...");
			exp.postProcess();
		}

	}

	/**
	 * Evaluates each dataset with each classifier.
	 * 
	 * @param classifiersConfXml
	 *            xml file with classifiers to use
	 * @param dataSets
	 *            list of datasets to use
	 * @throws Exception
	 */
	public void evaluate(final File classifiersConfXml,
			final List<DataSet> dataSets) throws Exception {

		// Read Classifiers configuration object from xml.
		final XStream xstream = new XStream();
		final ClassifiersConfiguration classifiersConf = (ClassifiersConfiguration) xstream
				.fromXML(new FileInputStream(classifiersConfXml));
		// Perform evaluation
		this.evaluate(classifiersConf, dataSets);

	}

	/**
	 * Instantiates Weka Classifiers from the configuration object.
	 * 
	 * @param classifiersConf
	 * @return
	 * @throws Exception
	 */
	private Object[] getClassifiers(
			final ClassifiersConfiguration classifiersConf) throws Exception {
		final List<Classifier> classifiers = new ArrayList<Classifier>();
		final List<ClassifierConfiguration> classifierConfList = classifiersConf
				.getClassifiers();
		for (final Iterator iterator = classifierConfList.iterator(); iterator
				.hasNext();) {
			final ClassifierConfiguration classifierConfiguration = (ClassifierConfiguration) iterator
					.next();
			final String classifierName = classifierConfiguration
					.getClassifierName();
			final String optionsString = classifierConfiguration.getOptions();
			final String[] options = Utils.splitOptions(optionsString);
			final Classifier c = (Classifier) Utils.forName(Classifier.class,
					classifierName, options);
			classifiers.add(c);

		}

		return classifiers.toArray();
	}

	/**
	 * Converts list of {@link DataSet} into Weka DefaultListModel.
	 * 
	 * @return list of datasets to be used as input into weka experimenter api.
	 */
	private DefaultListModel getDefaultListModel(final List<DataSet> dataSets) {
		final DefaultListModel defaultListModel = new DefaultListModel();
		for (final DataSet dataSet : dataSets) {
			final File file = InstancesCreator.generateFile("tempFile"
					+ new Date().getTime() + ".arff", dataSet);
			defaultListModel.addElement(file);
		}
		return defaultListModel;
	}

}
