package edu.unicen.surfforecaster.gwt.client.dto;

import java.io.Serializable;
import java.util.Map;

public class WekaForecasterEvaluationGwtDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The correlation of the classifier used by the forecaster.
	 */
	private double correlation;
	/**
	 * The mean absolute error of the classifier used by the forecaster.
	 */
	private double meanAbsoluteError;
	/**
	 * The id of the forecaster for which this evaluation was made.
	 */
	private Integer id;
	/**
	 * The classifier name;
	 */
	private String classifierName;

	private Map<String, String> trainningOptions;

	public WekaForecasterEvaluationGwtDTO() {
		// GWT purpose.
	}

	public WekaForecasterEvaluationGwtDTO(final double correlation,
			final double meanAbsoluteError, final Integer forecasterId,
			final String classifierName,
			final Map<String, String> trainningOptions) {
		super();
		this.correlation = correlation;
		this.meanAbsoluteError = meanAbsoluteError;
		this.id = forecasterId;
		this.classifierName = classifierName;
		this.trainningOptions = trainningOptions;
	}

	public double getCorrelation() {
		return correlation;
	}

	public double getMeanAbsoluteError() {
		return meanAbsoluteError;
	}

	public Integer getId() {
		return id;
	}

	/**
	 * @return the classifierName
	 */
	public String getClassifierName() {
		return classifierName;
	}

	/**
	 * @return the trainningOptions
	 */
	public Map<String, String> getTrainningOptions() {
		return trainningOptions;
	}

}
