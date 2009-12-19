package edu.unicen.surfforecaster.server.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.surfforecaster.server.domain.entity.VisualObservationsSet;

public class VisualObservationsSetDAOHibernateImpl extends HibernateDaoSupport implements
		VisualObservationsSetDAO {

	@Override
	public Integer save(VisualObservationsSet dataSet) {
		 this.getHibernateTemplate().save(dataSet);
		 return dataSet.getId();
	}
	@Override
	public VisualObservationsSet getVisualObservationSetById(Integer id) {
		 VisualObservationsSet set = (VisualObservationsSet) this.getHibernateTemplate().get(this.getClass(), id);
		 return set;
	}

}
