/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.surfforecaster.server.domain.entity.User;

/**
 * @author esteban
 * 
 */
public class UserDAOHibernateImpl extends HibernateDaoSupport implements
		UserDAO {

	/**
	 * @see edu.unicen.surfforecaster.server.dao.UserDAO#getUserByUserName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public User getUserByUserName(final String userName) {
		final DetachedCriteria criteria = DetachedCriteria.forClass(User.class)
				.add(Restrictions.eq("userName", userName));
		final List<User> user = getHibernateTemplate().findByCriteria(criteria);
		if (user.size() > 0) {
			if (user.size() == 1)
				return user.get(0);
			else
				throw new DataIntegrityViolationException(
						"Found more than one user for the given username:'"
								+ userName + "'.");
		}
		return null;
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
	@Override
	public User getUserByEmail(final String email) {
		final DetachedCriteria criteria = DetachedCriteria.forClass(User.class)
				.add(Restrictions.eq("email", email));
		final List<User> user = getHibernateTemplate().findByCriteria(criteria);
		if (user.size() > 0) {
			if (user.size() == 1)
				return user.get(0);
			else
				throw new DataIntegrityViolationException(
						"Found more than one user for the given email:'"
								+ email + "'.");
		}
		return null;
	}

}
