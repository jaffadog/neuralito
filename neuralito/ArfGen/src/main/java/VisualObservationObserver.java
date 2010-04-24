import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.math.stat.correlation.PearsonsCorrelation;

import edu.unicen.experimenter.datasetgenerator.DataSet;
import edu.unicen.experimenter.datasetgenerator.DataSetGeneratorYears;
import edu.unicen.experimenter.datasetgenerator.DataSetInstance;

/**
 * 
 */

/**
 * @author esteban
 * 
 */
public class VisualObservationObserver {

	// public static void main(final String[] args) {
	// final VisualObservationLoader loader = new VisualObservationLoader();
	// final Vector<VisualObservation> loadAllVisualObservations = loader
	// .loadAllVisualObservations();
	// final int[] years = new int[2006];
	// for (int i = 1997; i <= 2004; i++) {
	// years[i] = 0;
	// }
	// for (final VisualObservation visualObservation :
	// loadAllVisualObservations) {
	// if (visualObservation.getWaveHeight("nshore") < 2) {
	// years[visualObservation.getDate().get(Calendar.YEAR)]++;
	// }
	//
	// }
	// for (int i = 1997; i <= 2004; i++) {
	// // System.out.println("Year " + i + " has: " + years[i]
	// // + " strange events");
	// System.out.println(years[i]);
	// }
	// }
	public static void main(final String[] args) throws Exception {

		final DataSetGeneratorYears generator = new DataSetGeneratorYears();
		final List<DataSet> generateFromXML = generator
				.generateFromXML(new File(
						"src/main/resources/sizeEvaluationYearsNshore.xml"));
		for (final Iterator iterator = generateFromXML.iterator(); iterator
				.hasNext();) {
			final DataSet dataSet = (DataSet) iterator.next();
			t(dataSet);
		}
	}

	public static void t(final DataSet d) {
		final Collection<DataSetInstance> instances = d.getInstances();
		final double[] arg0 = new double[instances.size()];
		final double[] arg1 = new double[instances.size()];
		int i = 0;
		System.out.println("*************************" + d.getYears());
		System.out.println(d.getYears());
		System.out.println(d.getYears());
		System.out.println(d.getYears());
		System.out.println(d.getYears());

		for (final DataSetInstance dataSetInstance : instances) {

			arg0[i] = dataSetInstance.getData().get("ww3Height");
			arg1[i] = dataSetInstance.getData().get("visualObservation");
			System.out.println(arg0[i] + "," + arg1[i]);
			i++;

		}

		final PearsonsCorrelation co = new PearsonsCorrelation();
		System.out.println(co.correlation(arg0, arg1));
	}
}
