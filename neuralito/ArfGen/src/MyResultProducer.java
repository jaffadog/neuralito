import weka.experiment.AveragingResultProducer;
import weka.experiment.CSVResultListener;
import weka.experiment.ResultListener;
import weka.experiment.ResultProducer;

/**
 * @author esteban
 * 
 */
public class MyResultProducer extends AveragingResultProducer {

	/** The ResultListener to send results of the original ResultProducer */
	protected ResultListener secondaryResultListener = new CSVResultListener();

	/**
	 * @see weka.experiment.AveragingResultProducer#acceptResult(weka.experiment.ResultProducer,
	 *      java.lang.Object[], java.lang.Object[])
	 */
	@Override
	public void acceptResult(final ResultProducer rp, final Object[] key,
			final Object[] result) throws Exception {
		secondaryResultListener.acceptResult(rp, key, result);
		super.acceptResult(rp, key, result);
	}

	/**
	 * @see weka.experiment.AveragingResultProducer#preProcess(weka.experiment.ResultProducer)
	 */
	@Override
	public void preProcess(final ResultProducer rp) throws Exception {
		secondaryResultListener.preProcess(rp);
		super.preProcess(rp);
	}

	/**
	 * @see weka.experiment.AveragingResultProducer#postProcess(weka.experiment.ResultProducer)
	 */
	@Override
	public void postProcess(final ResultProducer rp) throws Exception {
		secondaryResultListener.postProcess(rp);
		super.postProcess(rp);
	}

	/**
	 * Sets the secondary result listener where to send output.
	 * 
	 * @param rl
	 */
	public void setSecondaryResultListener(final ResultListener rl) {
		secondaryResultListener = rl;
	};

}