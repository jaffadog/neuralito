/**
 * 
 */
package edu.unicen.experimenter.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.experimenter.datasetgenerator.DataSet;

/**
 * @author esteban
 * 
 */
public class DataSetDAOHibernateImpl extends HibernateDaoSupport implements
		DataSetDAO {

	/**
	 * @see edu.unicen.experimenter.datasetlibrary.dao.DataSetDAO#add(edu.unicen.experimenter.datasetgenerator.edu.unicen.experimenter.core.DataSet)
	 */
	@Override
	public int add(final DataSet dataSet) {

		getHibernateTemplate().saveOrUpdate(dataSet);
		return dataSet.getId();
	}

	/**
	 * @see edu.unicen.experimenter.datasetlibrary.dao.DataSetDAO#getAllDataSets()
	 */
	@Override
	public List<DataSet> getAllDataSets() {
		return getHibernateTemplate().loadAll(DataSet.class);
	}

	/**
	 * @see edu.unicen.experimenter.datasetlibrary.dao.DataSetDAO#getDataSet(int)
	 */
	@Override
	public DataSet getDataSet(final int id) {
		return (DataSet) getHibernateTemplate().load(DataSet.class, id);
	}

}
