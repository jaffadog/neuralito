/**
 * 
 */
package edu.unicen.experimenter.dao;

import java.util.List;

import edu.unicen.experimenter.datasetgenerator.DataSet;

/**
 * @author esteban
 * 
 */
public interface DataSetDAO {

	/**
	 * Adds the given dataset to repository.
	 * 
	 * @param dataSet
	 * @return
	 */
	public int add(DataSet dataSet);

	/**
	 * Returns all datasets in repository.
	 * 
	 * @return
	 */
	public List<DataSet> getAllDataSets();

	/**
	 * Returns the datasets with the given id.
	 * 
	 * @param id
	 * @return
	 */
	public DataSet getDataSet(int id);
}
