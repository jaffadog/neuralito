/**
 * 
 */
package edu.unicen.neuralito.domain.dao;

import java.util.Collection;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.neuralito.domain.entity.SurfSpot;

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
	 * @see edu.unicen.neuralito.domain.dao.SurfSpotDAO#saveSurfSpot(edu.unicen.neuralito.domain.entity.SurfSpot)
	 */
	 	 
	public SurfSpot saveSurfSpot(SurfSpot surfSpot) {
		getHibernateTemplate().saveOrUpdate(surfSpot);
		return surfSpot;
	}

}
