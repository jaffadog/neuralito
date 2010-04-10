/**
 * 
 */
package edu.unicen.experimenter.datasetgenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import com.thoughtworks.xstream.XStream;

import edu.unicen.experimenter.datasetgenerator.data.visualobservation.VisualObservation;
import edu.unicen.experimenter.datasetgenerator.data.visualobservation.VisualObservationLoader;
import edu.unicen.experimenter.datasetgenerator.data.wavewatch.WaveWatchLoader;
import edu.unicen.experimenter.datasetgenerator.generators.GenerationStrategy;
import edu.unicen.experimenter.datasetgenerator.xml.DataSetConfiguration;
import edu.unicen.experimenter.datasetgenerator.xml.DataSetGeneratorConfiguration;

/**
 * DataSet generator. Using WaveWatch model data, VisualObservations data, and a
 * generation strategy this class generates Datasets.
 * 
 */
public class DataSetGenerator {

	private final int percentajeForParametrization = 25;

	private List<VisualObservation> observationForParametrization;

	private List<VisualObservation> observationForEvaluation;

	WaveWatchLoader waveWatchData = new WaveWatchLoader();

	/**
	 * Default constructor
	 */
	public DataSetGenerator() {
		initVisualObservations();
	}

	/**
	 * Generates DataSets from the XML configuration.
	 * 
	 * @param xmlFile
	 *            containing generation configuration.
	 * @return
	 * @throws Exception
	 */
	public List<DataSet> generateFromXML(final File xmlFile) throws Exception {
		System.out.println("Generating DataSet...");
		final List<DataSet> dataSets = new ArrayList<DataSet>();

		// Read configuration object from XML
		final DataSetGeneratorConfiguration configuration = getDataSetConfiguration(xmlFile);

		// For each dataset configuration generate data sets.
		final List<DataSetConfiguration> dataSetsConfiguration = configuration
				.getDataSets();

		for (final DataSetConfiguration dataSetConfiguration : dataSetsConfiguration) {
			final String dataSetName = dataSetConfiguration.getDataSetName();
			final int instanceNumber = dataSetConfiguration.getInstanceNumber();
			final String strategyName = dataSetConfiguration.getStrategyName();
			final Map<String, Serializable> strategyOptions = dataSetConfiguration
					.getStrategyOptions();
			final Integer percentajeIncrement = dataSetConfiguration
					.getPercentajeIncrement();
			// Determines wether single dataset or incremental generation is
			// used.
			if (!dataSetConfiguration.isIncremental()) {
				final DataSet generatedDataSet = generateDataSet(
						instanceNumber, strategyName, strategyOptions);
				dataSets.add(generatedDataSet);
			} else {
				final List<DataSet> generatedIncrementalDataSets = generateIncrementalDataSets(
						dataSetName, instanceNumber, percentajeIncrement,
						strategyName, strategyOptions);
				dataSets.addAll(generatedIncrementalDataSets);
			}

		}
		System.out
				.println(dataSets.size() + " datasets have been generated...");
		return dataSets;

	}

	/**
	 * Generates a data set.
	 * 
	 * @param instances
	 *            the number of instances the dataset should contain
	 * @param strategyName
	 *            the class name of the generation strategy to use.
	 * @param strategyOptions
	 *            the options to pass to the generation strategy to use.
	 * @return
	 * @throws Exception
	 */
	private DataSet generateDataSet(final int instances,
			final String strategyName,
			final Map<String, Serializable> strategyOptions) throws Exception {
		// Obtain the strategy to use
		final GenerationStrategy generationStrategy = getStrategy(strategyName,
				strategyOptions);
		final Hashtable<String, Object> data = new Hashtable<String, Object>();
		// Set the data to use into the generation
		data.put("obsData", observationForEvaluation.subList(0, instances));
		data.put("ww3Data", waveWatchData);

		// generate
		final DataSet dataSet = generationStrategy.generateDataSet(data);

		return dataSet;
	}

	/**
	 * Generates several datasets, with incremental number of instances.
	 * 
	 * @param dataSetsName
	 *            the name to set to the generated dataSets.
	 * @param beach
	 *            the beach to generate the dataset.
	 * @param totalInstances
	 *            total number of instances.
	 * @param percentajeIncrement
	 *            percentaje of instances that each increment will be.
	 * @param strategyName
	 *            the class name of the generation strategy to use.
	 * @param strategyOptions
	 *            the options to pass to the generation strategy to use.
	 * @return
	 * @throws Exception
	 */
	private List<DataSet> generateIncrementalDataSets(
			final String dataSetsName, final int totalInstances,
			final int percentajeIncrement, final String strategyName,
			final Map<String, Serializable> strategyOptions) throws Exception {
		final List<DataSet> dataSets = new ArrayList<DataSet>();
		// Number of instances to increment in each dataset
		final int increment = totalInstances * percentajeIncrement / 100;
		// Generate datasets with incremental number of instances
		for (int instances = increment; instances < totalInstances; instances = instances
				+ increment) {
			final DataSet dataSet = generateDataSet(instances, strategyName,
					strategyOptions);
			dataSets.add(dataSet);
		}
		// Generate one more data set with the total number of instances
		final DataSet dataSet = generateDataSet(totalInstances, strategyName,
				strategyOptions);
		dataSets.add(dataSet);

		return dataSets;
	}

	/**
	 * Instantiates a strategy given its class name. After instantiation
	 * strategy is configured setting the options.
	 * 
	 * @param strategyName
	 *            class name of the GenerationStrategy to instantiate
	 * @param strategyOptions
	 *            options to set to the instantiated GenerationStrategy
	 * @return
	 * @throws Exception
	 */
	private GenerationStrategy getStrategy(final String strategyName,
			final Map<String, Serializable> strategyOptions) throws Exception {

		Class c = null;
		try {
			c = Class.forName(strategyName);
		} catch (final Exception ex) {
			throw new Exception("Can't find class called: " + strategyName);
		}
		final Class classType = GenerationStrategy.class;
		if (!classType.isAssignableFrom(c))
			throw new Exception(classType.getName()
					+ " is not assignable from " + strategyName);
		final GenerationStrategy strategy = (GenerationStrategy) c
				.newInstance();
		strategy.setOptions(strategyOptions);

		return strategy;

	}

	/**
	 * Load all the visual observations available. VisualObservations are loaded
	 * into 2 separated dataSets. One set is for dataset which will be used for
	 * parametrization. Other set is used for dataset which will be used for
	 * perfomance evaluation.
	 * 
	 */
	private void initVisualObservations() {
		final VisualObservationLoader voLoader = new VisualObservationLoader();
		// Obtain all visual observations
		final Vector<VisualObservation> observations = voLoader
				.loadAllVisualObservations();
		// Shuffle them
		Collections.shuffle(observations, new Random(1));
		// Extract percentaje for parametrization
		final int percentajeIndex = observations.size()
				* percentajeForParametrization / 100;
		observationForParametrization = observations
				.subList(0, percentajeIndex);
		// Extract percentaje for evaluation
		observationForEvaluation = observations.subList(percentajeIndex + 1,
				observations.size());
		System.out.println("Observations for Parametrization:"
				+ observationForParametrization.size());
		System.out.println("Observations for Evaluation:"
				+ observationForEvaluation.size());

	}

	/**
	 * Reads the datasetconfiguration from the XML file.
	 * 
	 * @param xmlFile
	 * @return
	 * @throws FileNotFoundException
	 */
	private DataSetGeneratorConfiguration getDataSetConfiguration(
			final File xmlFile) throws FileNotFoundException {

		final XStream xstream = new XStream();
		xstream.alias("DataSet", DataSet.class);
		final DataSetGeneratorConfiguration fromXML = (DataSetGeneratorConfiguration) xstream
				.fromXML(new FileInputStream(xmlFile));
		return fromXML;
	}
}
