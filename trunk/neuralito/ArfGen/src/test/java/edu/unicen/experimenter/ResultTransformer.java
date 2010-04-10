/**
 * 
 */
package edu.unicen.experimenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math.stat.descriptive.moment.StandardDeviation;

/**
 * 
 * @author esteban
 * 
 */
public class ResultTransformer {

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
		final HashMap map = new HashMap(group.iterator().next().getData());
		// create a new result with its cloned data.
		return new Result((Map) map.clone());
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
		// TODO Auto-generated method stub
		return results;
	}

}
