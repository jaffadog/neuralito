package edu.unicen.surfforecaster.server.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystem;
import edu.unicen.surfforecaster.server.domain.weka.strategy.DataSetGenerationStrategy;

/**
 * Forecaster which uses a Machine Learning scheme to improve wave height of the
 * {@link WaveWatchSystem} forecasts.
 * 
 * @author esteban
 * 
 */
@Entity
public class WekaForecaster extends Forecaster {

	/**
	 * The logger.
	 */
	@Transient
	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * The Machine Learning Classifier used.
	 */
	@Column(length = 257)
	// NOTE: Length is set greater than 256 this is a workaround for 'field too long' error when serializing to DB.
	private Classifier classifier;

	/**
	 * The strategy to generate instances.
	 */
	private DataSetGenerationStrategy dataSetGenerationStrategy;

	/**
	 * Options to generate the instances.
	 */
	@Column(length = 100000)
	private HashMap<String, Serializable> strategyOptions;

	/**
	 * The component to obtain wave watch forecast data.
	 */
	@Transient
	private WaveWatchSystem waveWatch;
	/**
	 * The wave watch system name.
	 */
	private String waveWatchModelName;

	/**
	 * Performance evaluation of the classifier.
	 */
	private HashMap<String, String> evaluations;

	/**
	 * Instances used to train the classifier.
	 */
	@Column(length = 300)
	private Instances trainningInstances;

	/**
	 * Empty constructor for ORM purpose.
	 */
	public WekaForecaster() {
		// ORM purpose
	}

	/**
	 * Creates a new Machine Learning based forecaster.
	 * 
	 * @param cl
	 *            the classifier to use.
	 * @param st
	 *            the strategy to compose the instances used for trainning and
	 *            classifying.
	 * @param dataSetStrategyOptions
	 *            the options to compose the instances.
	 * @param model
	 *            the model to obtain the forecast to be improved by classifier.
	 */
	public WekaForecaster(Classifier cl, DataSetGenerationStrategy st,
			HashMap<String, Serializable> options, WaveWatchSystem model,
			Collection<VisualObservation> observations, Spot spot) {
		try {
			this.classifier = cl;
			dataSetGenerationStrategy = st;
			this.strategyOptions = options;
			this.waveWatch = model;
			trainningInstances = st.generateTrainningInstances(model,
					observations, options);

			classifier.buildClassifier(trainningInstances);
			evaluateForecaster();
			this.spot = spot;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * The name of the forecaster
	 */
	@Override
	public String getName() {
		return "Machine Learning based forecaster";
	}

	/**
	 * The description of the forecaster.
	 */
	@Override
	public String getDescription() {
		return "Machine Learning based forecaster";
	}

	/**
	 * Obtain past forecasts.
	 */
	@Override
	public Collection<Forecast> getArchivedForecasts(Date from, Date to) {
		return null;
	}

	/**
	 * Obtain latest forecast.
	 */
	@Override
	public Collection<Forecast> getLatestForecasts() {
		try {
			List<Forecast> improvedForecasts = new ArrayList<Forecast>();
			Map<Forecast, Instance> latestForecastInstances = dataSetGenerationStrategy
					.generateLatestForecastInstances(this.waveWatch,
							this.strategyOptions);
			for (Forecast forecast : latestForecastInstances.keySet()) {
				Instance forecastInstance = latestForecastInstances
						.get(forecast);
				double improvedWaveHeight = classifier
						.classifyInstance(forecastInstance);
				forecast.addParameter("improvedWaveHeight",
 new ForecastValue(
						"improvedWaveHeight",
								improvedWaveHeight, Unit.Meters));
				improvedForecasts.add(forecast);
			}

			return improvedForecasts;

		} catch (Exception e) {
			// TODO treat exception
			log.error(e);
		}
		return null;
	}

	/**
	 * @return the performance evaluation of the classifier used.
	 */
	public HashMap<String, String> getEvaluation() {
		return evaluations;
	}

	/**
	 * 
	 * @return the classifier this forecaster uses.
	 */
	public Classifier getClassifier() {
		return this.classifier;
	}

	/**
	 * Perform a cross validation of the classifier and set the proper
	 * perfomance measures.
	 */
	private void evaluateForecaster() {
		try {
			Evaluation evaluation = new Evaluation(trainningInstances);
			evaluation.crossValidateModel(classifier, trainningInstances, 10,
					new Random(1));
			evaluations = new HashMap<String, String>();
			evaluations.put("correlation", Double.toString(evaluation
					.correlationCoefficient()));
			evaluations.put("meanAbsoluteError", Double.toString(evaluation
					.meanAbsoluteError()));
			evaluations.put("resume", "resumen de desempenio");


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Train the given classifier using the trainning instances.
	 */
	private void trainForecaster() {
		try {

		} catch (Exception e) {
			log.error(e);
		}
	}
}
