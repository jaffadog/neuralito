/**
 * 
 */
package edu.unicen.experimenter.datasetgenerator.xml;

import java.io.Serializable;
import java.util.Map;

/**
 * @author esteban
 * 
 */
public class DataSetConfiguration {

	boolean incremental;
	int instanceNumber;
	String beach;
	Map<String, Serializable> strategyOptions;
	String strategyName;
	private int percentajeIncrement;
	private String dataSetName;

	/**
	 * @return the incremental
	 */
	public boolean isIncremental() {
		return incremental;
	}

	/**
	 * @return the instanceNumber
	 */
	public int getInstanceNumber() {
		return instanceNumber;
	}

	/**
	 * @return the beach
	 */
	public String getBeach() {
		return beach;
	}

	/**
	 * @return the strategyOptions
	 */
	public Map<String, Serializable> getStrategyOptions() {
		return strategyOptions;
	}

	/**
	 * @return the strategyName
	 */
	public String getStrategyName() {
		return strategyName;
	}

	/**
	 * @param incremental
	 *            the incremental to set
	 */
	public void setIncremental(final boolean incremental) {
		this.incremental = incremental;
	}

	/**
	 * @param instanceNumber
	 *            the instanceNumber to set
	 */
	public void setInstanceNumber(final int instanceNumber) {
		this.instanceNumber = instanceNumber;
	}

	/**
	 * @param beach
	 *            the beach to set
	 */
	public void setBeach(final String beach) {
		this.beach = beach;
	}

	/**
	 * @param strategyOptions
	 *            the strategyOptions to set
	 */
	public void setStrategyOptions(
			final Map<String, Serializable> strategyOptions) {
		this.strategyOptions = strategyOptions;
	}

	/**
	 * @param strategyName
	 *            the strategyName to set
	 */
	public void setStrategyName(final String strategyName) {
		this.strategyName = strategyName;
	}

	/**
	 * 
	 */
	public int getPercentajeIncrement() {
		return percentajeIncrement;

	}

	/**
	 * @param percentajeIncrement
	 *            the percentajeIncrement to set
	 */
	public void setPercentajeIncrement(final int percentajeIncrement) {
		this.percentajeIncrement = percentajeIncrement;
	}

	/**
	 * 
	 */
	public String getDataSetName() {
		return dataSetName;
	}

	/**
	 * @param dataSetName
	 *            the dataSetName to set
	 */
	public void setDataSetName(final String dataSetName) {
		this.dataSetName = dataSetName;
	}

}
