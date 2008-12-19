package weka;

import java.util.Collection;

public class DataSet {
	private String DataSetName;
	private Collection instances;
	public DataSet(String name, Collection instances){
		this.DataSetName = name;
		this.instances = instances;
		
	}
		
	public Collection getInstances() {
		return instances;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return this.DataSetName;
	}

}
