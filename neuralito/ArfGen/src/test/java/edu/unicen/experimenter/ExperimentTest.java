package edu.unicen.experimenter;

import java.util.List;

import weka.classifiers.functions.LinearRegression;
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
		// Create one classifier
		final ClassifierConfiguration classifier = new ClassifierConfiguration();
		classifier.setClassifierName(LinearRegression.class.getName());
		// Add it to the list of classifiers to use
		final ClassifiersConfiguration classifiers = new ClassifiersConfiguration();
		classifiers.getClassifiers().add(classifier);
		// get
		final Controller controller = new Controller();
		final List<DataSet> dataSets = controller.getDataSets().subList(0, 1);
		final Evaluator demo = new Evaluator();
		demo.setResults(Experimenter.getInstance().getResultDAO());
		demo.testSetEvaluation("experimentName", classifiers, dataSets,
				dataSets.get(0));
	}

}
