/**
 * 
 */
package edu.unicen.experimenter.evaluator;

import weka.core.Instances;
import weka.core.Utils;
import weka.experiment.OutputZipper;
import weka.experiment.RandomSplitResultProducer;

/**
 * @author esteban
 * 
 */
public class TestSetResultProducer extends RandomSplitResultProducer {

	private Instances testInstances;

	public void setTestInstances(final Instances in) {
		testInstances = in;
	}

	/**
	 * @see weka.experiment.RandomSplitResultProducer#doRun(int)
	 */
	@Override
	public void doRun(final int run) throws Exception {

		if (getRawOutput()) {
			if (m_ZipDest == null) {
				m_ZipDest = new OutputZipper(m_OutputFile);
			}
		}

		if (m_Instances == null)
			throw new Exception("No Instances set");
		// Add in some fields to the key like run number, dataset name
		final Object[] seKey = m_SplitEvaluator.getKey();
		final Object[] key = new Object[seKey.length + 2];
		key[0] = Utils.backQuoteChars(m_Instances.relationName());
		key[1] = "" + run;
		System.arraycopy(seKey, 0, key, 2, seKey.length);
		if (m_ResultListener.isResultRequired(this, key)) {

			// Randomize on a copy of the original dataset
			final Instances runInstances = new Instances(m_Instances);

			final Instances train = runInstances;
			System.out.println("Class index: " + testInstances.classIndex());
			final Instances test = testInstances;
			try {
				final Object[] seResults = m_SplitEvaluator.getResult(train,
						test);
				final Object[] results = new Object[seResults.length + 1];
				results[0] = getTimestamp();
				System.arraycopy(seResults, 0, results, 1, seResults.length);
				if (m_debugOutput) {
					String resultName = ("" + run + "."
							+ Utils.backQuoteChars(runInstances.relationName())
							+ "." + m_SplitEvaluator.toString()).replace(' ',
							'_');
					resultName = Utils.removeSubstring(resultName,
							"weka.classifiers.");
					resultName = Utils.removeSubstring(resultName,
							"weka.filters.");
					resultName = Utils.removeSubstring(resultName,
							"weka.attributeSelection.");
					m_ZipDest.zipit(m_SplitEvaluator.getRawResultOutput(),
							resultName);
				}
				m_ResultListener.acceptResult(this, key, results);
			} catch (final Exception ex) {
				// Save the train and test datasets for debugging purposes?
				throw ex;
			}
		}
	}
}
