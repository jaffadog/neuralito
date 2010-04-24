/**
 * 
 */
package edu.unicen.experimenter.evaluator;

import weka.experiment.DatabaseResultListener;
import weka.experiment.ResultProducer;

/**
 * @author esteban
 * 
 */
public class MyDBResultListener extends DatabaseResultListener {

	private String experimentName;

	/**
	 * @throws Exception
	 */
	public MyDBResultListener() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see weka.experiment.DatabaseResultListener#isResultRequired(weka.experiment.ResultProducer,
	 *      java.lang.Object[])
	 */
	@Override
	public boolean isResultRequired(final ResultProducer rp, final Object[] key)
			throws Exception {

		return true;
	}

	@Override
	public void putResultInTable(final String tableName,
			final ResultProducer rp, final Object[] key, final Object[] result)
			throws Exception {

		String query = "INSERT INTO " + tableName + " VALUES ( '"
				+ experimentName + "'";
		// Add the results to the table
		for (int i = 0; i < key.length; i++) {
			if (true) {
				query += ',';
			}
			if (key[i] != null) {
				if (key[i] instanceof String) {
					query += "'" + processKeyString(key[i].toString()) + "'";
				} else if (key[i] instanceof Double) {
					query += safeDoubleToString((Double) key[i]);
				} else {
					query += key[i].toString();
				}
			} else {
				query += "NULL";
			}
		}
		for (int i = 0; i < result.length; i++) {
			query += ',';
			if (result[i] != null) {
				if (result[i] instanceof String) {
					query += "'" + result[i].toString() + "'";
				} else if (result[i] instanceof Double) {
					query += safeDoubleToString((Double) result[i]);
				} else {
					query += result[i].toString();
					// !!
					// System.err.println("res: "+ result[i].toString());
				}
			} else {
				query += "NULL";
			}
		}
		query += ')';

		if (m_Debug) {
			System.err.println("Submitting result: " + query);
		}
		update(query);
		close();
	}

	@Override
	public String createResultsTable(final ResultProducer rp,
			final String tableName) throws Exception {

		if (m_Debug) {
			System.err.println("Creating results table " + tableName + "...");
		}
		String query = "CREATE TABLE " + tableName + " ( ";
		// Loop over the key fields
		String[] names = rp.getKeyNames();
		Object[] types = rp.getKeyTypes();
		if (names.length != types.length)
			throw new Exception("key names types differ in length");
		query += "ExperimentName TEXT, ";
		for (int i = 0; i < names.length; i++) {
			query += "Key_" + names[i] + " ";
			if (types[i] instanceof Double) {
				query += m_doubleType;
			} else if (types[i] instanceof String) {

				// Workaround for MySQL (doesn't support LONGVARCHAR)
				// Also for InstantDB which attempts to interpret numbers when
				// storing
				// in LONGVARBINARY
				/*
				 * if (m_Connection.getMetaData().getDriverName().
				 * equals("Mark Matthews' MySQL Driver") ||
				 * (m_Connection.getMetaData().getDriverName().
				 * indexOf("InstantDB JDBC Driver")) != -1) { query += "TEXT ";
				 * } else {
				 */
				// query += "LONGVARCHAR ";
				query += m_stringType + " ";
				// }
			} else
				throw new Exception("Unknown/unsupported field type in key");
			query += ", ";
		}
		// Loop over the result fields
		names = rp.getResultNames();
		types = rp.getResultTypes();
		if (names.length != types.length)
			throw new Exception("result names and types differ in length");
		for (int i = 0; i < names.length; i++) {
			query += names[i] + " ";
			if (types[i] instanceof Double) {
				query += m_doubleType;
			} else if (types[i] instanceof String) {

				// Workaround for MySQL (doesn't support LONGVARCHAR)
				// Also for InstantDB which attempts to interpret numbers when
				// storing
				// in LONGVARBINARY
				/*
				 * if (m_Connection.getMetaData().getDriverName().
				 * equals("Mark Matthews' MySQL Driver") ||
				 * (m_Connection.getMetaData().getDriverName().
				 * equals("InstantDB JDBC Driver"))) { query += "TEXT "; } else
				 * {
				 */
				// query += "LONGVARCHAR ";
				query += m_stringType + " ";
				// }
			} else
				throw new Exception("Unknown/unsupported field type in key");
			if (i < names.length - 1) {
				query += ", ";
			}
		}
		query += " )";

		update(query);
		if (m_Debug) {
			System.err.println("table created");
		}
		close();

		if (m_createIndex) {
			query = "CREATE UNIQUE INDEX Key_IDX ON " + tableName + " (";

			final String[] keyNames = rp.getKeyNames();

			boolean first = true;
			for (int i = 0; i < keyNames.length; i++) {
				if (keyNames[i] != null) {
					if (first) {
						first = false;
						query += "Key_" + keyNames[i];
					} else {
						query += ",Key_" + keyNames[i];
					}
				}
			}
			query += ")";

			update(query);
		}
		return tableName;
	}

	private String safeDoubleToString(final Double number) {
		// NaN is treated as NULL
		if (number.isNaN())
			return "NULL";

		final String orig = number.toString();

		final int pos = orig.indexOf('E');
		if (pos == -1 || orig.charAt(pos + 1) == '-')
			return orig;
		else {
			final StringBuffer buff = new StringBuffer(orig);
			buff.insert(pos + 1, '+');
			return new String(buff);
		}
	}

	/**
	 * @param string
	 *            the experimentName to set
	 */
	public void setExperimentName(final String string) {
		experimentName = string;
	}
}
