/**
 * 
 */
package edu.unicen.experimenter.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.experimenter.DataSet;

/**
 * @author esteban
 * 
 */
public class DataSetDAOHibernateImpl extends HibernateDaoSupport implements
		DataSetDAO {

	/**
	 * @see edu.unicen.experimenter.dao.DataSetDAO#add(edu.unicen.experimenter.DataSet)
	 */
	@Override
	public int add(final DataSet dataSet) {

		getHibernateTemplate().saveOrUpdate(dataSet);
		return dataSet.getId();
	}

	/**
	 * @see edu.unicen.experimenter.dao.DataSetDAO#getAllDataSets()
	 */
	@Override
	public List<DataSet> getAllDataSets() {
		return getHibernateTemplate().loadAll(DataSet.class);
	}

	/**
	 * @see edu.unicen.experimenter.dao.DataSetDAO#getDataSet(int)
	 */
	@Override
	public DataSet getDataSet(final int id) {
		return (DataSet) getHibernateTemplate().load(DataSet.class, id);
	}

}
