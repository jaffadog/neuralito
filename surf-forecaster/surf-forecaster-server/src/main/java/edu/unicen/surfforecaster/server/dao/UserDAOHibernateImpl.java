/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.surfforecaster.server.domain.entity.User;

/**
 * @author esteban
 * 
 */
public class UserDAOHibernateImpl extends HibernateDaoSupport implements
		UserDAO , ApplicationContextAware{

	private ApplicationContext context;

	/**
	 * @see edu.unicen.surfforecaster.server.dao.UserDAO#getUserByUserName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public User getUserByUserName(final String userName) {
		final DetachedCriteria criteria = DetachedCriteria.forClass(User.class)
				.add(Restrictions.eq("userName", userName));
		User user = (User) DataAccessUtils
				.singleResult(getHibernateTemplate().findByCriteria(
						criteria));
		return user;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.UserDAO#saveOrUpdate(edu.unicen.surfforecaster.server.domain.entity.User)
	 */
	@Override
	public User saveOrUpdate(final User user) {
		getHibernateTemplate().saveOrUpdate(user);
		return user;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.UserDAO#getUserByEmail(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public User getUserByEmail(final String email) {
		final DetachedCriteria criteria = DetachedCriteria.forClass(User.class)
				.add(Restrictions.eq("email", email));
		User user = (User)DataAccessUtils.singleResult(getHibernateTemplate().findByCriteria(criteria));
		return user;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.UserDAO#removeUser(java.lang.Integer)
	 */
	@Override
	public void removeUser(final Integer userId) {
		final User user = (User) getHibernateTemplate()
				.load(User.class, userId);
		if (user != null) {
			getHibernateTemplate().delete(user);
		}
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.UserDAO#getUserByUserId(java.lang.Integer)
	 */
	@Override
	public User getUserByUserId(final Integer userId) {
		return (User) getHibernateTemplate().get(User.class, userId);
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.context = arg0;
		
	}

}
