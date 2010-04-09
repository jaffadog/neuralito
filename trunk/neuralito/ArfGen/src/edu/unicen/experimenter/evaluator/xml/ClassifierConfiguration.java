/**
 * 
 */
package edu.unicen.experimenter.evaluator.xml;

/**
 * Configuration indicating a weka Classifier name and the corresponding options
 * to be set.
 * 
 * @author esteban
 * 
 */
public class ClassifierConfiguration {
	/**
	 * Classifier class name. i.e: weka.classifiers.functions.LinearRegression
	 */
	String classifierName;
	/**
	 * The options to set to the classifier.
	 *  i.e: -L 0.99 -M 0.01 -N 1000 -V 0 -S 0 -E 20 -H 4 -D
	 */
	String options;

	/**
	 * @return the classifierName
	 */
	public String getClassifierName() {
		return classifierName;
	}

	/**
	 * @param classifierName
	 *            the classifierName to set
	 */
	public void setClassifierName(final String classifierName) {
		this.classifierName = classifierName;
	}

	/**
	 * @param options
	 *            the options to set
	 */
	public void setOptions(final String options) {
		this.options = options;
	}

	/**
	 * 
	 */
	public String getOptions() {
		return options;

	}

}
