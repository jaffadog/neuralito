/**
 * 
 */
package edu.unicen.experimenter.evaluator;

import weka.core.Instances;
import weka.experiment.CrossValidationResultProducer;
import weka.experiment.ResultListener;
import weka.experiment.ResultProducer;

/**
 * @author esteban
 * 
 */
public class CrossValidationProducer implements ResultProducer {
	CrossValidationResultProducer producer = new CrossValidationResultProducer();

	/**
	 * @see weka.experiment.ResultProducer#doRun(int)
	 */
	@Override
	public void doRun(final int run) throws Exception {
		producer.doRun(run);

	}

	/**
	 * @see weka.experiment.ResultProducer#doRunKeys(int)
	 */
	@Override
	public void doRunKeys(final int run) throws Exception {

	}

	/**
	 * @see weka.experiment.ResultProducer#getCompatibilityState()
	 */
	@Override
	public String getCompatibilityState() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see weka.experiment.ResultProducer#getKeyNames()
	 */
	@Override
	public String[] getKeyNames() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see weka.experiment.ResultProducer#getKeyTypes()
	 */
	@Override
	public Object[] getKeyTypes() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see weka.experiment.ResultProducer#getResultNames()
	 */
	@Override
	public String[] getResultNames() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see weka.experiment.ResultProducer#getResultTypes()
	 */
	@Override
	public Object[] getResultTypes() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see weka.experiment.ResultProducer#postProcess()
	 */
	@Override
	public void postProcess() throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @see weka.experiment.ResultProducer#preProcess()
	 */
	@Override
	public void preProcess() throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @see weka.experiment.ResultProducer#setAdditionalMeasures(java.lang.String[])
	 */
	@Override
	public void setAdditionalMeasures(final String[] additionalMeasures) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see weka.experiment.ResultProducer#setInstances(weka.core.Instances)
	 */
	@Override
	public void setInstances(final Instances instances) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see weka.experiment.ResultProducer#setResultListener(weka.experiment.ResultListener)
	 */
	@Override
	public void setResultListener(final ResultListener listener) {
		// TODO Auto-generated method stub

	}

}
