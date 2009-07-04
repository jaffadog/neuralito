package weka;

import java.io.File;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class InstancesCreator {

	public Instances generateTrainningData(DataSet dataSet){
		
		Instances instancesDataSet = initDataSet(dataSet);
		Collection<ArfData> data = dataSet.getInstances();
		for (Iterator it = data.iterator();it.hasNext();) {
			ArfData arf =(ArfData) it.next();
			Instance instance = this.makeInstance(instancesDataSet, arf);
			instancesDataSet.add(instance);
		}
		return instancesDataSet;
	}
	private Instances initDataSet(DataSet dataSet){
	
		Instances data;
		// 	Creates the numeric attributes
		FastVector attributes = new FastVector(dataSet.getAttributes().length);
		for (int i = 0; i < dataSet.getAttributes().length; i++)
			attributes.addElement(new Attribute(dataSet.getAttributes()[i]));
		
		// Creates an empty data set
		data = new Instances(dataSet.getName(), attributes, 100000);
		data.setClassIndex(dataSet.getClassAttributeIndex());
		return data;
		
	}
	private Instance makeInstance(Instances dataSet, ArfData data){
 		
		//Create empty instance
		Instance instance = new Instance(dataSet.numAttributes());

		// give the instances access to the data set
		instance.setDataset(dataSet);
		
		//set instance values
		for (int i = 0; i < dataSet.numAttributes(); i++ ){
			String attributeName = dataSet.attribute(i).name();
			instance.setValue(dataSet.attribute(attributeName), data.getValue(attributeName));
		}
		
		return instance;

	}
	public void generateFile(DataSet dataSet, Collection data){
		String relationName = dataSet.getName();
		Instances instancesDataSet = this.generateTrainningData(dataSet);
		ArffSaver saver = new ArffSaver();
		saver.setInstances(instancesDataSet);
		try {
			saver.setFile(new File(".//files//arff//" + relationName + ".arff"));
			saver.setDestination( new File(".//files//arff//" + relationName + ".arff"));   // **not** necessary in 3.5.4 and later
			saver.writeBatch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileDataIO fileWriter = new FileDataIO();
		fileWriter.writeFile(".//files//arff//" + relationName + ".txt", dataSet.getDescription());
	}
	public void generateFile(String fileName, Instances dataSet){
		 ArffSaver saver = new ArffSaver();
		 saver.setInstances(dataSet);
		 try {
			saver.setFile(new File(fileName));
			saver.setDestination( new File(fileName));   // **not** necessary in 3.5.4 and later
			saver.writeBatch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
