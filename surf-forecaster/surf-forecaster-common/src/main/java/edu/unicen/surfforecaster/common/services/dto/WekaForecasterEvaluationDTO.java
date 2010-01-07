package edu.unicen.surfforecaster.common.services.dto;

import java.io.Serializable;
import java.util.HashMap;

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
	 * The resume of the classifier. In different languages.
	 */
	private HashMap<String,String> resume;
	
	
	public WekaForecasterEvaluationDTO() {
		//GWT purpose.
	}
	
	public WekaForecasterEvaluationDTO(double correlation,
			double meanAbsoluteError, Integer forecasterId) {
		super();
		this.correlation = correlation;
		this.meanAbsoluteError = meanAbsoluteError;
		this.id = forecasterId;
		this.resume = resume;
	}

	public HashMap<String, String> getResume() {
		return resume;
	}

	public double getCorrelation() {
		return correlation;
	}

	public double getMeanAbsoluteError() {
		return meanAbsoluteError;
	}

	public Integer getId() {
		return this.id;
	}

	
	
}
