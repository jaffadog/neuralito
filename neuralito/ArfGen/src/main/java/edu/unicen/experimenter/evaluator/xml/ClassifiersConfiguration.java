/**
 * 
 */
package edu.unicen.experimenter.evaluator.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * Container of ClassifierConfiguration objects.
 * 
 * @author esteban
 * 
 */
public class ClassifiersConfiguration {
	/**
	 * List of classifierConfiguration
	 */
	List<ClassifierConfiguration> classifiers = new ArrayList<ClassifierConfiguration>();

	/**
	 * @return the classifiers
	 */
	public List<ClassifierConfiguration> getClassifiers() {
		return classifiers;
	}

	/**
	 * @param classifiers
	 *            the classifiers to set
	 */
	public void setClassifiers(final List<ClassifierConfiguration> classifiers) {
		this.classifiers = classifiers;
	}
}
