/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * This forecaster will improve a forecaster using a machine learning algorithm.
 * The machine learning algoritm will improve original forecaster using a set of
 * visual observations.
 * 
 * @author esteban
 * 
 */
@Entity
public class WekaForecaster extends Forecaster {

	/**
	 * The visual observations used to train the machine learning scheme.
	 */
	@OneToMany(fetch = FetchType.EAGER)
	private Collection<VisualObservation> visualObservations;

	/**
	 * The forecaster which will be improved using the machine learning scheme.
	 */
	@OneToOne
	private Forecaster forecasterToImprove;

	/**
 * 
 */
	public WekaForecaster() {
		// ORM purpose
	}

	/**
	 * 
	 */
	public WekaForecaster(
			final Collection<VisualObservation> visualObservation,
			final Forecaster forecasterToImprove) {
		visualObservations = visualObservation;
		this.forecasterToImprove = forecasterToImprove;
	}

	// public void trainForecaster(){
	// //For each visual observation
	// //Obtain the corresponding wave watch forecast
	// //create the instances
	// //apply filters
	// //train classifier(parameter)
	// //serialize classifier
	// //return forecaster evaluation.
	// }

	/**
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecast.Forecaster#getDescription()
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecast.Forecaster#getForecasts()
	 */
	@Override
	public Collection<Forecast> getForecasts() {
		// createInstanceFromLatestForecast
		// classify instance.
		// replace wave height with predicted wave height.
		// return improved forecast.
		return null;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecast.Forecaster#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
