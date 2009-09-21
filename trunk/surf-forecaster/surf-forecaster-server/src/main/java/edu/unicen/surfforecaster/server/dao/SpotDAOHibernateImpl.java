/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.surfforecaster.server.domain.entity.Area;
import edu.unicen.surfforecaster.server.domain.entity.Country;
import edu.unicen.surfforecaster.server.domain.entity.Spot;
import edu.unicen.surfforecaster.server.domain.entity.Zone;

/**
 * @author esteban
 *
 */
public class SpotDAOHibernateImpl extends HibernateDaoSupport implements SpotDAO{

	/**
	 * @see {@link SurfSpotDAO#getAllSpots()} 
	 */
	@SuppressWarnings("unchecked")
	public Set<Spot> getAllSpots() {
		// TODO Auto-generated method stub
		return new HashSet<Spot>(getHibernateTemplate().loadAll(Spot.class));
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.SurfSpotDAO#saveSurfSpot(edu.unicen.surfforecaster.server.domain.entity.Spot)
	 */
	 	 
	public Spot updateSpot(Spot surfSpot) {
		Validate.notNull(surfSpot, "The surf spot to update cannot be null");
		getHibernateTemplate().update(surfSpot);
		return surfSpot;
	}
	/* (non-Javadoc)
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#saveArea(edu.unicen.surfforecaster.server.domain.entity.Area)
	 */
	@Override
	public Area saveArea(Area area) {
		Validate.notNull(area, "The area to save cannot be null");
		this.getHibernateTemplate().saveOrUpdate(area);
		return area;
	}

	/* (non-Javadoc)
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#saveCountry(edu.unicen.surfforecaster.server.domain.entity.Country)
	 */
	@Override
	public Country saveCountry(Country country) {
		Validate.notNull(country, "The country to save cannot be null");
		this.getHibernateTemplate().saveOrUpdate(country);
		return country;
	}

	/* (non-Javadoc)
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#saveZone(edu.unicen.surfforecaster.server.domain.entity.Zone)
	 */
	@Override
	public Zone saveZone(Zone zone) {
		Validate.notNull(zone, "The zone to save cannot be null");
		this.getHibernateTemplate().saveOrUpdate(zone);
		return zone;
	}

	/* (non-Javadoc)
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#getAllAreas()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Area> getAllAreas() {
		return this.getHibernateTemplate().loadAll(Area.class);
	}

	/* (non-Javadoc)
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#getAreaById(int)
	 */
	@Override
	public Area getAreaById(int id) {
		return (Area)this.getHibernateTemplate().get(Area.class, id);
	}
	
}



