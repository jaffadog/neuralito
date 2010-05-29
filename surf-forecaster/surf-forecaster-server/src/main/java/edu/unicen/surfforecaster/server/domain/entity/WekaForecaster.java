package edu.unicen.surfforecaster.server.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.WaveWatchParameter;
import edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystem;
import edu.unicen.surfforecaster.server.domain.weka.strategy.DataSetGenerationStrategy;
import edu.unicen.surfforecaster.server.domain.weka.util.Util;

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
	private final Logger log = Logger.getLogger(this.getClass());

	private static final int MIN_NUM_INSTANCES_REQUIRED = 50;

	/**
	 * The Machine Learning Classifier used.
	 */
	@Column(length = 512)
	// NOTE: Length is set greater than 256 this is a workaround for 'field too
	// long' error when serializing to DB.
	private Classifier classifier;

	/**
	 * The strategy to generate instances.
	 */
	@Column(length = 1048)
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
	@Column(length = 3000000)
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
	public WekaForecaster(final Classifier cl,
			final DataSetGenerationStrategy st,
			final HashMap<String, Serializable> options,
			final WaveWatchSystem model,
			final Collection<VisualObservation> observations, final Spot spot)
			throws NeuralitoException {

		classifier = cl;
		dataSetGenerationStrategy = st;
		strategyOptions = options;
		waveWatch = model;
		trainningInstances = st.generateTrainningInstances(model, observations,
				options);

		if (trainningInstances.numInstances() < WekaForecaster.MIN_NUM_INSTANCES_REQUIRED)
			throw new NeuralitoException(ErrorCode.NOT_ENOUGH_WW3_HISTORY);

		try {
			classifier.buildClassifier(trainningInstances);
			evaluateForecaster();
			this.spot = spot;
		} catch (final Exception e) {
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
	public Collection<Forecast> getArchivedForecasts(final Date from,
			final Date to) {
		return null;
	}

	/**
	 * Obtain latest forecast.
	 */
	@Override
	public Collection<Forecast> getLatestForecasts() {
		try {
			final List<Forecast> improvedForecasts = new ArrayList<Forecast>();
			final Map<Forecast, Instance> latestForecastInstances = dataSetGenerationStrategy
					.generateLatestForecastInstances(waveWatch, strategyOptions);
			Collection<Forecast> latestForecasts = latestForecastInstances
					.keySet();
			latestForecasts = addWindDirectionAndSpeed(latestForecasts);
			for (final Forecast forecast : latestForecasts) {
				final Instance forecastInstance = latestForecastInstances
						.get(forecast);
				final double improvedWaveHeight = classifier
						.classifyInstance(forecastInstance);
				forecast.addParameter("improvedWaveHeight", new ForecastValue(
						"improvedWaveHeight", improvedWaveHeight, Unit.Meters));
				improvedForecasts.add(forecast);
			}
			Collections.sort(improvedForecasts);
			return improvedForecasts;

		} catch (final Exception e) {
			log.error("Error while retrieving latest forecast.", e);
		}
		return null;
	}

	/**
	 * Adds wind direction and speed parameters to forecasts. Direction and
	 * speed its derived of the WINDU and WINDV parameters.
	 * 
	 * @param forecasts
	 * @return
	 */
	private Collection<Forecast> addWindDirectionAndSpeed(
			final Collection<Forecast> forecasts) {

		// Calculate wind direction and wind speed from WINDU and WINDV
		// parameters.
		for (final Forecast forecast : forecasts) {
			final float windU = forecast.getParameter(
					WaveWatchParameter.WINDUComponent_V2.getValue())
					.getfValue();
			final float windV = forecast.getParameter(
					WaveWatchParameter.WINDVComponent_V2.getValue())
					.getfValue();
			final double windDirection = Util.calculateWindDirection(windU,
					windV);
			final double windSpeed = Util.calculateWindSpeed(windU, windV);
			// ADD wind speed and direction parameters to each forecast.
			forecast.addParameter(WaveWatchParameter.WIND_DIRECTION_V2
					.getValue(), new ForecastValue(
					WaveWatchParameter.WIND_DIRECTION_V2.getValue(),
					windDirection, Unit.KilometersPerHour));
			forecast.addParameter(WaveWatchParameter.WIND_SPEED_V2.getValue(),
					new ForecastValue(WaveWatchParameter.WIND_SPEED_V2
							.getValue(), windSpeed, Unit.KilometersPerHour));
		}
		return forecasts;
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
		return classifier;
	}

	/**
	 * Perform a cross validation of the classifier and set the proper
	 * perfomance measures.
	 */
	private void evaluateForecaster() {
		try {
			final Evaluation evaluation = new Evaluation(trainningInstances);
			evaluation.crossValidateModel(classifier, trainningInstances, 10,
					new Random(1));
			evaluations = new HashMap<String, String>();
			evaluations.put("correlation", Double.toString(evaluation
					.correlationCoefficient()));
			evaluations.put("meanAbsoluteError", Double.toString(evaluation
					.meanAbsoluteError()));
			evaluations.put("resume", "resumen de desempenio");

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Train the given classifier using the trainning instances.
	 */
	private void trainForecaster() {
		try {

		} catch (final Exception e) {
			log.error(e);
		}
	}

	/**
	 * @return the strategyOptions
	 */
	public HashMap<String, Serializable> getTrainningOptions() {
		return strategyOptions;
	}

}
