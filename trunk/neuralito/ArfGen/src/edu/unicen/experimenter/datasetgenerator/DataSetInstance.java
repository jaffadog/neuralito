package edu.unicen.experimenter.datasetgenerator;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;

import edu.unicen.experimenter.util.Util;

/**
 * An instance of a DataSet.
 * 
 * @author esteban
 * 
 */
public class DataSetInstance implements Serializable {

	private Calendar date = null;
	private Hashtable<String, Double> data = null;

	public Hashtable<String, Double> getData() {
		return data;
	}

	public void setData(final Hashtable<String, Double> data) {
		this.data = data;
	}

	public DataSetInstance(final Hashtable<String, Double> data) {
		this.data = data;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(final Calendar date) {
		this.date = date;
	}

	@Override
	public String toString() {
		String result = "Date: "
				+ Util.getDateFormatter().format(date.getTime()) + " ";
		for (final Enumeration<String> e = data.keys(); e.hasMoreElements();) {
			final String key = e.nextElement();
			result += key + ": "
					+ Util.getDecimalFormatter().format(data.get(key)) + " ";
		}

		return result;
	}

	public double getValue(final String attribute) {
		return data.get(attribute);
	}
}
