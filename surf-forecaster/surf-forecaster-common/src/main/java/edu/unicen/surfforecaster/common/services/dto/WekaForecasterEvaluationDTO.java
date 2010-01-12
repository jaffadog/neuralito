package edu.unicen.surfforecaster.common.services.dto;

import java.io.Serializable;
import java.util.Map;

public class WekaForecasterEvaluationDTO implements Serializable {

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

	private Map<String, Serializable> trainningOptions;

	public WekaForecasterEvaluationDTO() {
		// GWT purpose.
	}

	public WekaForecasterEvaluationDTO(final double correlation,
			final double meanAbsoluteError, final Integer forecasterId,
			final String classifierName,
			final Map<String, Serializable> trainningOptions) {
		super();
		this.correlation = correlation;
		this.meanAbsoluteError = meanAbsoluteError;
		id = forecasterId;
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
	public Map<String, Serializable> getTrainningOptions() {
		return trainningOptions;
	}

}
