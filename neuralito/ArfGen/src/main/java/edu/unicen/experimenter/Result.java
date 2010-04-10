/**
 * 
 */
package edu.unicen.experimenter;

import java.util.Map;

/**
 * @author esteban
 * 
 */
public class Result {

	private final Map data;

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

}
