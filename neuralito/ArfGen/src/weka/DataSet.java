package weka;

import java.util.Collection;
import java.util.Vector;

public class DataSet {
	private String DataSetName;
	private Collection instances;
	private String[] attributes;
	private String classAttribute;
	
	public DataSet(String name, Collection instances){
		this.DataSetName = name;
		this.instances = instances;
		
	}
	
	public DataSet(String name, Collection instances, String[] attributes, String classAttribute){
		this.DataSetName = name;
		this.instances = instances;
		this.attributes = attributes;
		this.classAttribute = classAttribute;
	}
		
	public Collection getInstances() {
		return instances;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return this.DataSetName;
	}

	public String[] getAttributes() {
		return attributes;
	}

	public void setAttributes(String[] attributes) {
		this.attributes = attributes;
	}

	public String getClassAttribute() {
		return classAttribute;
	}
	
	public int getClassAttributeIndex() {
		for (int i = 0; i < this.attributes.length; i++)
			if (this.attributes[i] == this.classAttribute)
				return i;
		return -1;
	}
	
	public void setClassAttribute(String classAttribute) {
		this.classAttribute = classAttribute;
	}
	
	
}
