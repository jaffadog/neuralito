/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.surfforecaster.server.domain.entity.Comparation;

/**
 * @author esteban
 * 
 */
public class ComparationDAOHibernateImpl extends HibernateDaoSupport implements
		ComparationDAO {

	/**
	 * @see edu.unicen.surfforecaster.server.dao.ComparationDAO#saveComparation(edu.unicen.surfforecaster.server.domain.entity.Comparation)
	 */
	@Override
	public Integer saveComparation(final Comparation c) {
		getHibernateTemplate().saveOrUpdate(c);
		return c.getId();
	}

	public Comparation getComparationById(final Integer comparationId) {
		return (Comparation) getHibernateTemplate().get(Comparation.class,
				comparationId);
	}

}
