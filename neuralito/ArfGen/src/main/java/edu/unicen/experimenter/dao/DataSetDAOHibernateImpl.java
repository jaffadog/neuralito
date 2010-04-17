/**
 * 
 */
package edu.unicen.experimenter.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.experimenter.datasetgenerator.DataSet;
import edu.unicen.experimenter.datasetgenerator.DataSetInstance;

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
		final DetachedCriteria crit = DetachedCriteria.forClass(DataSet.class)
				.add(Restrictions.eq("id", id));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (DataSet) getHibernateTemplate().findByCriteria(crit).get(0);
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
		return getHibernateTemplate().findByCriteria(crit);
	}

	public List<DataSet> loadEager(final List<DataSet> dataSets) {
		for (DataSet dataSet : dataSets) {
			final DetachedCriteria dc = DetachedCriteria
					.forClass(DataSet.class).add(
							Expression.idEq(dataSet.getId()));
			;
			dc.setFetchMode("instances", FetchMode.EAGER);
			dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			final List results = getHibernateTemplate().findByCriteria(dc);
			if (results.size() > 1) {
				System.err.println("Found more than one row fot dataSetId:"
						+ dataSet.getId());
			}
			if (results.size() < 1)
				throw new RuntimeErrorException(null, "");
			dataSet.getInstances();
			dataSet = (DataSet) results.get(0);
		}
		return dataSets;

	}

	/**
	 * @see edu.unicen.experimenter.dao.DataSetDAO#getInstances(int)
	 */
	@Override
	public Collection<DataSetInstance> getInstances(final int dataSetId) {
		final DetachedCriteria dc = DetachedCriteria.forClass(DataSet.class)
				.add(Expression.idEq(dataSetId));
		;

		dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		final List results = getHibernateTemplate().findByCriteria(dc);
		if (results.size() > 1) {
			System.err.println("Found more than one row fot dataSetId:");
		}
		if (results.size() < 1)
			throw new RuntimeErrorException(null, "");
		final DataSet dataSet = (DataSet) results.get(0);
		return dataSet.getInstances();

	}

	/**
	 * @see edu.unicen.experimenter.dao.DataSetDAO#getSession2()
	 */
	@Override
	public SessionFactory getSession2() {
		// TODO Auto-generated method stub
		return getSessionFactory();
	}
}
