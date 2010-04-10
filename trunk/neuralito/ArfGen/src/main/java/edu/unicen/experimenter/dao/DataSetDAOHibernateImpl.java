/**
 * 
 */
package edu.unicen.experimenter.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
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
	@SuppressWarnings("unchecked")
	@Override
	public List<DataSet> getAllDataSets() {
		return new ArrayList(new HashSet(getHibernateTemplate().loadAll(
				DataSet.class)));
	}

	/**
	 * @see edu.unicen.experimenter.datasetlibrary.dao.DataSetDAO#getDataSet(int)
	 */
	@Override
	public DataSet getDataSet(final int id) {
		return (DataSet) getHibernateTemplate().load(DataSet.class, id);
	}

	/**
	 * @see edu.unicen.experimenter.dao.DataSetDAO#getDataSetsByBeach(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DataSet> getDataSetsByBeach(final String beach) {
		final DetachedCriteria crit = DetachedCriteria.forClass(DataSet.class)
				.add(Restrictions.eq("beach", beach));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		getHibernateTemplate().findByCriteria(crit);
		return getHibernateTemplate().findByCriteria(crit);
	}
}
