package edu.unicen.experimenter;

import java.util.List;

import weka.classifiers.functions.MultilayerPerceptron;
import edu.unicen.experimenter.datasetgenerator.DataSet;
import edu.unicen.experimenter.evaluator.Evaluator;
import edu.unicen.experimenter.evaluator.xml.ClassifierConfiguration;
import edu.unicen.experimenter.evaluator.xml.ClassifiersConfiguration;
import edu.unicen.experimenter.gui.Controller;

/**
 * 
 */

/**
 * @author esteban
 * 
 */
public class ExperimentTest {

	/**
	 * @param args
	 * @throws Exception
	 */

	public static void main(final String[] args) throws Exception {
		final ClassifierConfiguration classifier = new ClassifierConfiguration();
		classifier.setClassifierName(MultilayerPerceptron.class.getName());
		classifier
				.setOptions("-L 0.99 -M 0.01 -N 1000 -V 0 -S 0 -E 20 -H 4 -D");
		final ClassifiersConfiguration classifiers = new ClassifiersConfiguration();
		classifiers.getClassifiers().add(classifier);

		final Controller controller = new Controller();
		final List<DataSet> dataSets = controller.getDataSets();
		final Evaluator demo = new Evaluator();
		demo.evaluate(classifiers, dataSets);
	}

}
