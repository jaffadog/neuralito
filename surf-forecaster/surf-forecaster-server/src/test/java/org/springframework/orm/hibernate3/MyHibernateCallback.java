/**
 * 
 */
package org.springframework.orm.hibernate3;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author esteban
 * 
 */
public class MyHibernateCallback implements HibernateCallback {

	/**
	 * @see org.springframework.orm.hibernate3.HibernateCallback#doInHibernate(org.hibernate.Session)
	 */
	@Override
	public Object doInHibernate(final Session session)
			throws HibernateException, SQLException {

		return null;
	}

}
