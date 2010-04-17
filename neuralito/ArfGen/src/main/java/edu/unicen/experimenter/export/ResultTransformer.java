/**
 * 
 */
package edu.unicen.experimenter.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math.stat.descriptive.moment.StandardDeviation;

import edu.unicen.experimenter.Result;

/**
 * 
 * @author esteban
 * 
 */
public class ResultTransformer {

	private static final Map<String, String> classifiersParameterMap = new HashMap() {
		{
			put("weka.classifiers.functions.MultilayerPerceptron",
					"-L 0.99 -M 0.01 -N 1000 -V 0 -S 0 -E 20 -H 4 -D");
			put("weka.classifiers.functions.LinearRegression", "-S 0 -R 1.0E-6");
			put(
					"weka.classifiers.functions.SMOreg",
					"-S 0.0080 -C 0.7 -T 0.0080 -P 1.0E-12 -N 0 -K \"weka.classifiers.functions.supportVector.RBFKernel -C 250007 -G 0.5\"");
		}
	};

	public static List<Result> averageResults(final List<Result> results,
			final Map columnNamesToAverage) {

		final List<Result> averagedResults = new ArrayList<Result>();
		final List<Set<Result>> crossvalidationGroups = getCrossValidationsGroups(results);
		for (final Set<Result> group : crossvalidationGroups) {
			final Result result = averageGroup(group, columnNamesToAverage);
			averagedResults.add(result);
		}
		return averagedResults;

	}

	/**
	 * @param group
	 * @param columnNames
	 * @return
	 */
	private static Result averageGroup(final Set<Result> group,
			final Map<String, String> columnNames) {
		// Clone any result of this group to override only columns that need to
		// be averaged. The rest of the columns will remain the same.
		final Result averagedResult = cloneResult(group);
		for (final String columnName : columnNames.keySet()) {
			// acumulated
			double values = 0;
			int i = 0;
			// array of all the values of the group to calculate deviation
			final double[] valuesArr = new double[group.size()];
			for (final Result result : group) {
				final double value = Double.parseDouble(result
						.getResult(columnNames.get(columnName)));
				values += value;
				valuesArr[i] = value;
				i++;
			}
			final double average = values / group.size();
			// guardaraverage
			final StandardDeviation s = new StandardDeviation();
			final Double standardDeviation = s.evaluate(valuesArr);
			averagedResult.getData().put(columnNames.get(columnName), average);
			averagedResult.getData().put(columnNames.get(columnName) + "Dev",
					standardDeviation);
		}

		return averagedResult;

	}

	/**
	 * @param group
	 * @return
	 */
	private static Result cloneResult(final Set<Result> group) {
		// get any result from the group
		final Result firstResult = group.iterator().next();
		final HashMap map = new HashMap(firstResult.getData());
		// create a new result with its cloned data.
		final Result newResult = new Result((Map) map.clone());
		newResult.setDataSet(firstResult.getDataSet());
		return newResult;
	}

	/**
	 * @param results
	 * @return
	 */
	private static List<Set<Result>> getCrossValidationsGroups(
			final List<Result> results) {
		final HashSet<Result> added = new HashSet<Result>();
		final List<Set<Result>> groups = new ArrayList<Set<Result>>();
		for (final Result result : results) {
			final String scheme = result.getResult("Key_Scheme");
			final String dataSet = result.getResult("Key_Dataset");
			final String options = result.getResult("Key_Scheme_options");
			final Set group = new HashSet<Result>();
			for (final Result result2 : results) {
				final String scheme2 = result2.getResult("Key_Scheme");
				final String dataSet2 = result2.getResult("Key_Dataset");
				final String options2 = result2.getResult("Key_Scheme_options");
				if (!added.contains(result2) && scheme.equals(scheme2)
						&& dataSet.equals(dataSet2) && options.equals(options2)) {
					group.add(result2);
					added.add(result2);
				}

			}
			if (group.size() > 0) {
				groups.add(group);
			}

		}
		return groups;
	}

	/**
	 * @param results
	 * @return
	 */
	public static List<Result> addExtraColumns(final List<Result> results) {
		for (final Result result : results) {
			result.getData().put("optimized", isOptimized(result));
			result.getData().put("beach", result.getDataSet().getBeach());
			result.getData().put("GenerationOption",
					result.getDataSet().getStrategyOptionString());
		}
		return results;
	}

	/**
	 * @param result
	 * @return
	 */
	private static Boolean isOptimized(final Result result) {
		final String classifier = (String) result.getData().get("Key_Scheme");
		final String parameters = (String) result.getData().get(
				"Key_Scheme_options");
		final String optimumParametersOfClassifier = classifiersParameterMap
				.get(classifier);
		if (optimumParametersOfClassifier == null)
			return null;
		if (optimumParametersOfClassifier == parameters)
			return true;
		else
			return false;

	}
}
