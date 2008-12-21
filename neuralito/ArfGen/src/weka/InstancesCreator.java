package weka;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import ww3.WaveWatchData;

public class InstancesCreator {

	public Instances generateTrainningData(String relationName,Collection data){
		
		Instances dataSet = initDataSet(relationName);
		for (Iterator it = data.iterator();it.hasNext();) {
			ArfData arf =(ArfData) it.next();
			Instance instance = this.makeInstance(dataSet, arf);
			dataSet.add(instance);
		}
		return dataSet;
	}
	private Instances initDataSet(String relationName){
	
		Instances data;
		// 	Creates the numeric attributes
		FastVector attributes = new FastVector(7);
		attributes.addElement(new Attribute("buoyHeight"));
		attributes.addElement(new Attribute("buoyPeriod"));
		attributes.addElement(new Attribute("buoyDirection"));
		attributes.addElement(new Attribute("ww3Height"));
		attributes.addElement(new Attribute("ww3Period"));
		attributes.addElement(new Attribute("ww3Direction"));
		attributes.addElement(new Attribute("visualObservation"));
		// Creates an empty data set
		data = new Instances(relationName, attributes, 1000);
		data.setClassIndex(7-1);
		return data;
		
	}
	private Instance makeInstance(Instances dataSet, ArfData data){
 		
		//Create empty instance with 7 attributes
		Instance instance = new Instance(7);

		// give the instances access to the data set
		instance.setDataset(dataSet);
		
		//set instance values
		instance.setValue(dataSet.attribute("buoyHeight"), data.getBuoyData().getWaveHeight());
		instance.setValue(dataSet.attribute("buoyPeriod"), data.getBuoyData().getWavePeriod());
		instance.setValue(dataSet.attribute("buoyDirection"), data.getBuoyData().getWaveDirection());
		instance.setValue(dataSet.attribute("ww3Height"), data.getWw3Data().getHeight());
		instance.setValue(dataSet.attribute("ww3Period"), data.getWw3Data().getPeriod());
		instance.setValue(dataSet.attribute("ww3Direction"), data.getWw3Data().getDirection());
		instance.setValue(dataSet.attribute("visualObservation"), data.getObsData().getNShore());
		
		return instance;

	}
	public void generateFile(String relationName, Collection data){
		Instances dataSet = this.generateTrainningData(relationName, data);
		 ArffSaver saver = new ArffSaver();
		 saver.setInstances(dataSet);
		 try {
			saver.setFile(new File("files/" + relationName + ".arff"));
			saver.setDestination( new File("files/" + relationName + ".arff"));   // **not** necessary in 3.5.4 and later
			saver.writeBatch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 		
	}
	public void generateFile(String fileName, Instances dataSet){
		 ArffSaver saver = new ArffSaver();
		 saver.setInstances(dataSet);
		 try {
			saver.setFile(new File("files/" + dataSet.relationName() + ".arff"));
			saver.setDestination( new File("files/" + fileName + ".arff"));   // **not** necessary in 3.5.4 and later
			saver.writeBatch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 		
	}
	
	
}
