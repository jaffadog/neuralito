/**
 * 
 */
package edu.unicen.experimenter.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;

import edu.unicen.experimenter.datasetgenerator.DataSet;
import edu.unicen.experimenter.datasetgenerator.DataSetInstance;

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

	/**
	 * @param beach
	 */
	public List<DataSet> getDataSetsByBeach(String beach);

	/**
	 * @param dataSets
	 * @return
	 */
	List<DataSet> loadEager(List<DataSet> dataSets);

	/**
	 * @param id
	 * @return
	 */
	public Collection<DataSetInstance> getInstances(int dataSetId);

	public SessionFactory getSession2();

	/**
	 * @param dataSetGroupName
	 * @return
	 */
	public boolean existsDataSetGroup(String dataSetGroupName);
}
