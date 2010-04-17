package edu.unicen.experimenter.evaluator;

import weka.experiment.AveragingResultProducer;
import weka.experiment.CSVResultListener;
import weka.experiment.ResultListener;
import weka.experiment.ResultProducer;

/**
 * AverageResultProducer with the added functionality that the original
 * results(the non averaged results) are sent to a result listener.
 * 
 * @author esteban
 * 
 */
public class AveragingResultProducerExtension extends AveragingResultProducer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The ResultListener to send results of the original ResultProducer(The non
	 * averaged results)
	 */
	protected ResultListener secondaryResultListener = new CSVResultListener();
	private String experimentName;

	/**
	 * 
	 * @see weka.experiment.AveragingResultProducer#acceptResult(weka.experiment.ResultProducer,
	 *      java.lang.Object[], java.lang.Object[])
	 */
	@Override
	public void acceptResult(final ResultProducer rp, final Object[] key,
			final Object[] result) throws Exception {
		// Add in the name of the experiment this result producer is running
		// for to the result.
		// final Object[] newKey = new String[key.length + 1];
		// newKey[key.length] = experimentName;
		// System.arraycopy(key, 0, newKey, 0, key.length);
		secondaryResultListener.acceptResult(rp, key, result);
		super.acceptResult(rp, key, result);
	}

	/**
	 * @see weka.experiment.AveragingResultProducer#checkForMultipleDifferences()
	 */
	@Override
	protected void checkForMultipleDifferences() throws Exception {
		// TODO Auto-generated method stub

		final Object[] firstKey = (Object[]) m_Keys.elementAt(0);
		final Object[] lastKey = (Object[]) m_Keys.elementAt(m_Keys.size() - 1);
		/*
		 * System.err.println("First key:" +
		 * DatabaseUtils.arrayToString(firstKey));
		 * System.err.println("Last key :" +
		 * DatabaseUtils.arrayToString(lastKey));
		 */
		final int newIndex = m_KeyIndex;
		for (int i = 0; i < firstKey.length; i++) {
			if (i != newIndex && !firstKey[i].equals(lastKey[i])) {

				System.out.println("FirstKey" + firstKey[i]);
				System.out.println("LastKey" + lastKey[i]);
				throw new Exception("Keys differ on fields other than \""
						+ m_KeyFieldName
						+ "\" -- time to implement multiple averaging");
			}
		}

	}

	//
	// /**
	// * @see weka.experiment.AveragingResultProducer#findKeyIndex()
	// */
	// @Override
	// protected int findKeyIndex() {
	// // TODO Auto-generated method stub
	// int index = super.findKeyIndex();
	//
	// if (index == -1)
	// return index;
	// else {
	// index++;
	// return index;
	// }
	//
	// }

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

	//
	// /**
	// * @see weka.experiment.AveragingResultProducer#getKeyNames()
	// */
	// @Override
	// public String[] getKeyNames() throws Exception {
	//
	// // Add in the name of the experiment this result producer is running
	// // for.
	//
	// final String[] key = super.getKeyNames();
	// final String[] newKeyNames = new String[key.length + 1];
	// newKeyNames[key.length] = "ExperimentName";
	// System.arraycopy(key, 0, newKeyNames, 0, key.length);
	// return newKeyNames;
	//
	// }

	// /**
	// * @see weka.experiment.AveragingResultProducer#getKeyTypes()
	// */
	// @Override
	// public Object[] getKeyTypes() throws Exception {
	//
	// // Add in the name of the experiment this result producer is running
	// // for.
	// final Object[] key = super.getKeyTypes();
	// final Object[] newKeyTypes = new String[key.length + 1];
	// newKeyTypes[key.length] = "String";
	// System.arraycopy(key, 0, newKeyTypes, 0, key.length);
	// return newKeyTypes;
	// }

	/**
	 * @param experimentName2
	 */
	public void setExperimentName(final String experimentName2) {
		// TODO Auto-generated method stub
		experimentName = experimentName2;

	}

}
