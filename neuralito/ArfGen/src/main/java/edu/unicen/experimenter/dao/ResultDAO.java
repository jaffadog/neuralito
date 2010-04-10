/**
 * 
 */
package edu.unicen.experimenter.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import edu.unicen.experimenter.Result;
import edu.unicen.experimenter.datasetgenerator.DataSet;

/**
 * @author esteban
 * 
 */
public class ResultDAO {

	String experimentTable = "";
	String averagedResultsTable = "results1";
	String nonAveragedResultsTable = "results0";
	String dataSetIdColumn = "Key_Dataset";

	DataSetDAO dataSetDAO;

	DataSource dataSource;
	JdbcTemplate template;

	public List<Result> getResult(final String beach) {
		// para cada dataset de una playa
		final List<DataSet> dataSetsByBeach = dataSetDAO
				.getDataSetsByBeach(beach);
		final List<Result> resultsForBeach = new ArrayList<Result>();
		// obtener los resultados
		for (final Iterator iterator = dataSetsByBeach.iterator(); iterator
				.hasNext();) {
			final DataSet dataSet = (DataSet) iterator.next();
			final List<Result> results = getResultsByDataSetId(dataSet.getId());
			resultsForBeach.addAll(results);
		}
		return resultsForBeach;
	}

	/**
	 * @param id
	 * @return
	 */
	private List<Result> getResultsByDataSetId(final int id) {

		final String query = "select * from " + averagedResultsTable
				+ " where " + dataSetIdColumn + "='" + id + "'";
		template = new JdbcTemplate(dataSource);
		System.out.println("Query was: " + query);
		final List rowResults = template.queryForList(query);
		final List<Result> results = convertRowToResults(rowResults);
		return results;
	}

	/**
	 * @param rowResults
	 * @return
	 */
	private List<Result> convertRowToResults(final List rowResults) {
		final List<Result> results = new ArrayList<Result>();
		for (final Object row : rowResults) {
			final Result result = getResultFromRow((Map) row);
			results.add(result);
		}
		return results;
	}

	/**
	 * @param row
	 * @return
	 */
	private Result getResultFromRow(final Map row) {
		final Result result = new Result(row);
		return result;
	}

	/**
	 * @return the dataSetDAO
	 */
	public DataSetDAO getDataSetDAO() {
		return dataSetDAO;
	}

	/**
	 * @return the dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSetDAO
	 *            the dataSetDAO to set
	 */
	public void setDataSetDAO(final DataSetDAO dataSetDAO) {
		this.dataSetDAO = dataSetDAO;
	}

	/**
	 * @param dataSource
	 *            the dataSource to set
	 */
	public void setDataSource(final DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
