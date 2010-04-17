/**
 * 
 */
package edu.unicen.experimenter.datasetgenerator.xml;

import java.util.List;

/**
 * @author esteban
 * 
 */
public class DataSetGeneratorConfiguration {

	List<DataSetConfiguration> dataSets;

	String dataSetGroupName;

	/**
	 * @return the dataSets
	 */
	public List<DataSetConfiguration> getDataSets() {
		return dataSets;
	}

	/**
	 * @param dataSets
	 *            the dataSets to set
	 */
	public void setDataSets(final List<DataSetConfiguration> dataSets) {
		this.dataSets = dataSets;
	}

	/**
	 * @return the dataSetGroupName
	 */
	public String getDataSetGroupName() {
		return dataSetGroupName;
	}

	/**
	 * @param dataSetGroupName
	 *            the dataSetGroupName to set
	 */
	public void setDataSetGroupName(final String dataSetGroupName) {
		this.dataSetGroupName = dataSetGroupName;
	}

}
