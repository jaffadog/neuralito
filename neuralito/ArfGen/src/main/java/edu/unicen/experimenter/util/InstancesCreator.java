package edu.unicen.experimenter.util;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import edu.unicen.experimenter.dao.DataSetDAO;
import edu.unicen.experimenter.datasetgenerator.DataSet;
import edu.unicen.experimenter.datasetgenerator.DataSetInstance;

public class InstancesCreator {
	static final DataSetDAO dataSetDAo;
	static {
		final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"dao.xml");
		dataSetDAo = (DataSetDAO) ctx.getBean("dataSetDAO");
	}

	public static Instances generateTrainningData(final DataSet dataSet) {

		final Instances instancesDataSet = initDataSet(dataSet);
		final SessionFactory openSession = dataSetDAo.getSession2();
		final Session openSession2 = openSession.openSession();
		final Transaction beginTransaction = openSession2.beginTransaction();
		openSession2.update(dataSet);
		System.out.println("To convert: " + dataSet.getInstances().size()
				+ " dataset says:" + dataSet.getNumberOfInstances());
		final Collection<DataSetInstance> data = dataSet.getInstances();
		for (final Iterator it = data.iterator(); it.hasNext();) {
			final DataSetInstance arf = (DataSetInstance) it.next();
			final Instance instance = makeInstance(instancesDataSet, arf);
			instancesDataSet.add(instance);
		}
		beginTransaction.commit();
		openSession2.close();
		return instancesDataSet;
	}

	private static Instances initDataSet(final DataSet dataSet) {

		Instances data;
		// Creates the numeric attributes
		final FastVector attributes = new FastVector(
				dataSet.getAttributes().length);
		for (int i = 0; i < dataSet.getAttributes().length; i++) {
			attributes.addElement(new Attribute(dataSet.getAttributes()[i]));
		}

		// Creates an empty data set - Set the dataset name to the ID of the
		// original dataset.
		data = new Instances(Integer.toString(dataSet.getId()), attributes,
				100000);
		data.setClassIndex(dataSet.getClassAttributeIndex());
		return data;

	}

	private static Instance makeInstance(final Instances dataSet,
			final DataSetInstance data) {

		// Create empty instance
		final Instance instance = new Instance(dataSet.numAttributes());

		// give the instances access to the data set
		instance.setDataset(dataSet);

		// set instance values
		for (int i = 0; i < dataSet.numAttributes(); i++) {
			final String attributeName = dataSet.attribute(i).name();
			instance.setValue(dataSet.attribute(attributeName), data
					.getValue(attributeName));
		}

		return instance;

	}

	// public void generateFile(final DataSet dataSet) {
	// final String shortDescription = dataSet.getDescription();
	// final Instances instancesDataSet = generateTrainningData(dataSet);
	// final ArffSaver saver = new ArffSaver();
	// saver.setInstances(instancesDataSet);
	// try {
	// saver.setFile(new File(".//files//arff//" + shortDescription
	// + ".arff"));
	// saver.setDestination(new File(".//files//arff//" + shortDescription
	// + ".arff")); // **not** necessary in 3.5.4 and later
	// saver.writeBatch();
	// } catch (final Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// // FileDataIO fileWriter = new FileDataIO();
	// // fileWriter.writeFile(".//files//arff//" + shortDescription + ".txt",
	// // dataSet.getDescription());
	// }

	// public static File generateFile(final String fileName,
	// final Instances dataSet) {
	// final ArffSaver saver = new ArffSaver();
	// saver.setInstances(dataSet);
	// File file = null;
	// try {
	// file = new File(fileName);
	// saver.setFile(file);
	// saver.setDestination(new File(fileName)); // **not** necessary in
	// // 3.5.4 and later
	// saver.writeBatch();
	//
	// } catch (final Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return file;
	// }

	public static File generateFile(final String fileName, final DataSet dataSet) {

		final ArffSaver saver = new ArffSaver();
		saver.setInstances(generateTrainningData(dataSet));

		File file = null;
		try {
			file = new File(fileName);
			saver.setFile(file);
			// saver.setDestination(new File(fileName)); // **not** necessary in
			// // 3.5.4 and later
			saver.writeBatch();

		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
}
