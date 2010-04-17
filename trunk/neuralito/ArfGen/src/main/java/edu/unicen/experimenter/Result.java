/**
 * 
 */
package edu.unicen.experimenter;

import java.util.Map;

import org.apache.commons.lang.Validate;

import edu.unicen.experimenter.datasetgenerator.DataSet;

/**
 * @author esteban
 * 
 */
public class Result {
	/**
	 * The result data as map.
	 */
	private final Map data;
	/**
	 * The DataSet used to get this result.
	 */
	private DataSet dataSet;

	/**
	 * @param row
	 */
	public Result(final Map data) {
		this.data = data;
	}

	public String getResult(final String columnName) {

		final Object result = data.get(columnName);
		if (result != null)
			return result.toString();
		else {
			System.err.println("Column name: " + columnName + "not found");
			return null;
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return data.toString();
	}

	/**
	 * @return the data
	 */
	public Map getData() {
		return data;
	}

	/**
	 * @param dataSet
	 */
	public void setDataSet(final DataSet dataSet) {
		Validate.notNull(dataSet);
		this.dataSet = dataSet;

	}

	/**
	 * @return the dataSet
	 */
	public DataSet getDataSet() {
		return dataSet;
	}

}
