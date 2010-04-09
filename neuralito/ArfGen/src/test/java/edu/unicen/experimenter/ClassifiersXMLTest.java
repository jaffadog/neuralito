/**
 * 
 */
package edu.unicen.experimenter;

import weka.classifiers.functions.MultilayerPerceptron;

import com.thoughtworks.xstream.XStream;

import edu.unicen.experimenter.evaluator.xml.ClassifierConfiguration;
import edu.unicen.experimenter.evaluator.xml.ClassifiersConfiguration;

/**
 * @author esteban
 * 
 */
public class ClassifiersXMLTest {

	public static void main(final String[] args) {
		final ClassifierConfiguration classifier = new ClassifierConfiguration();
		classifier.setClassifierName(MultilayerPerceptron.class.getName());
		classifier
				.setOptions("-L 0.99 -M 0.01 -N 1000 -V 0 -S 0 -E 20 -H 4 -D");
		final ClassifiersConfiguration classifiers = new ClassifiersConfiguration();
		classifiers.getClassifiers().add(classifier);
		final XStream xstream = new XStream();
		// XStream xstream = new XStream(new DomDriver()); // does not require
		// XPP3 library
		// xstream.alias("DataSet", DataSet.class);
		final String xml = xstream.toXML(classifiers);
		System.out.println(xml);

	}

}
