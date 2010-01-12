package edu.unicen.surfforecaster.common.services.dto;

import java.io.Serializable;

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

	public WekaForecasterEvaluationDTO() {
		// GWT purpose.
	}

	public WekaForecasterEvaluationDTO(final double correlation,
			final double meanAbsoluteError, final Integer forecasterId,
			final String classifierName) {
		super();
		this.correlation = correlation;
		this.meanAbsoluteError = meanAbsoluteError;
		id = forecasterId;
		this.classifierName = classifierName;
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

}
