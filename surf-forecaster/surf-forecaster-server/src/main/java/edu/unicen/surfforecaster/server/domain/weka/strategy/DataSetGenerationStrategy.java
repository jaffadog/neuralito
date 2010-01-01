package edu.unicen.surfforecaster.server.domain.weka.strategy;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import weka.core.Instance;
import weka.core.Instances;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.VisualObservation;
import edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystem;

/**
 * Strategy for the assembling of instances.
 * 
 * @author esteban
 * 
 */

public interface DataSetGenerationStrategy extends Serializable {
	/**
	 * Creates a data set of classified instances. Instances are created
	 * combining the WaveWatchModel archived data with the visual observations.
	 * 
	 * @param trainningData
	 * @return
	 */
	public Instances generateTrainningInstances(WaveWatchSystem model,
			Collection<VisualObservation> observations,
			Map<String, Serializable> options);

	/**
	 * Generates unclassified instances for the last forecast available from the
	 * model.
	 * 
	 * @param forecast
	 * @return
	 */
	public Map<Forecast, Instance> generateLatestForecastInstances(
			WaveWatchSystem model, Map<String, Serializable> options);

	/**
	 * Available options to configure the generation of the instances.
	 * 
	 * @return
	 */
	public Map<String, OptionType> listAvailableOptions();

}
