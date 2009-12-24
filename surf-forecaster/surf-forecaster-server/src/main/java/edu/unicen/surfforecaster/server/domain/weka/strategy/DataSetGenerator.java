package edu.unicen.surfforecaster.server.domain.weka.strategy;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class DataSetGenerator implements DataSetGenerationStrategy {
	/**
	 * The id for ORM pupose.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(length = 11)
	private Integer id;

	public Integer getId() {
		return id;
	}
	public abstract String getName();
	protected Instances createInstances(String name,
			Collection<Map<String, Double>> instancesData, String[] attributes,
			String classAttribute) {
		Instances instancesDataSet = initDataSet(name, attributes,
				classAttribute);
		for (Iterator it = instancesData.iterator(); it.hasNext();) {
			Map arf = (Map) it.next();
			Instance instance = makeInstance(instancesDataSet, arf);
			instancesDataSet.add(instance);
		}
		return instancesDataSet;
	}

	protected Instances initDataSet(String name, String[] attributes,
			String classAttribute) {

		Instances data;
		// Creates the numeric attributes
		FastVector fastVectorAttributes = new FastVector(attributes.length);
		for (int i = 0; i < attributes.length; i++)
			fastVectorAttributes.addElement(new Attribute(attributes[i]));

		// Creates an empty data set
		data = new Instances(name, fastVectorAttributes, 100000);
		data.setClass(data.attribute(classAttribute));
		return data;

	}

	protected Instance makeInstance(Instances dataSet, Map<String, Double> data) {

		// Create empty instance
		Instance instance = new Instance(dataSet.numAttributes());

		// give the instances access to the data set
		instance.setDataset(dataSet);

		// set instance values
		for (int i = 0; i < dataSet.numAttributes(); i++) {
			String attributeName = dataSet.attribute(i).name();
			instance.setValue(dataSet.attribute(attributeName), data
					.get(attributeName));
		}

		return instance;

	}

}
