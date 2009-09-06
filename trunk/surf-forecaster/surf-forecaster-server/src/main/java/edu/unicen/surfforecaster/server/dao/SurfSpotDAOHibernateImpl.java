/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import java.util.Collection;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.surfforecaster.server.domain.entity.SurfSpot;

/**
 * @author esteban
 *
 */
public class SurfSpotDAOHibernateImpl extends HibernateDaoSupport implements SurfSpotDAO{

	/**
	 * @see {@link SurfSpotDAO#getAllSurfSpots()} 
	 */
	public Collection<SurfSpot> getAllSurfSpots() {
		// TODO Auto-generated method stub
		return getHibernateTemplate().loadAll(SurfSpot.class);
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.SurfSpotDAO#saveSurfSpot(edu.unicen.surfforecaster.server.domain.entity.SurfSpot)
	 */
	 	 
	public SurfSpot saveSurfSpot(SurfSpot surfSpot) {
		getHibernateTemplate().saveOrUpdate(surfSpot);
		return surfSpot;
	}

}
