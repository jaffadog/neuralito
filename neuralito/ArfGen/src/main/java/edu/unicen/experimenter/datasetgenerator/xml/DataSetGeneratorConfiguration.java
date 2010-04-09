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

}
