/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.unicen.surfforecaster.server.domain.entity.Area;
import edu.unicen.surfforecaster.server.domain.entity.Country;
import edu.unicen.surfforecaster.server.domain.entity.Forecaster;
import edu.unicen.surfforecaster.server.domain.entity.Point;
import edu.unicen.surfforecaster.server.domain.entity.Spot;
import edu.unicen.surfforecaster.server.domain.entity.User;
import edu.unicen.surfforecaster.server.domain.entity.Zone;

/**
 * @author esteban
 * 
 */

public class SpotDAOHibernateImpl extends HibernateDaoSupport implements
		SpotDAO {

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

	public Spot updateSpot(final Spot surfSpot) {
		Validate.notNull(surfSpot, "The surf spot to update cannot be null");
		getHibernateTemplate().update(surfSpot);
		return surfSpot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeedu.unicen.surfforecaster.server.dao.SpotDAO#saveArea(edu.unicen.
	 * surfforecaster.server.domain.entity.Area)
	 */
	@Override
	public Integer saveArea(final Area area) {
		Validate.notNull(area, "The area to save cannot be null");
		getHibernateTemplate().saveOrUpdate(area);
		return area.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeedu.unicen.surfforecaster.server.dao.SpotDAO#saveCountry(edu.unicen.
	 * surfforecaster.server.domain.entity.Country)
	 */
	@Override
	public Integer saveCountry(final Country country) {
		Validate.notNull(country, "The country to save cannot be null");
		getHibernateTemplate().saveOrUpdate(country);
		return country.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeedu.unicen.surfforecaster.server.dao.SpotDAO#saveZone(edu.unicen.
	 * surfforecaster.server.domain.entity.Zone)
	 */
	@Override
	public Integer saveZone(final Zone zone) {
		Validate.notNull(zone, "The zone to save cannot be null");
		getHibernateTemplate().saveOrUpdate(zone);
		return zone.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#getAllAreas()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Area> getAllAreas() {
		return getHibernateTemplate().loadAll(Area.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#getAreaById(int)
	 */
	@Override
	public Area getAreaById(final int id) {
		return (Area) getHibernateTemplate().get(Area.class, id);
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#getZoneById(java.lang.Integer)
	 */
	@Override
	public Zone getZoneById(final Integer zoneId) {
		return (Zone) getHibernateTemplate().get(Zone.class, zoneId);
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#getPublicSpots(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Spot> getSpotsForUser(final User user) {
		// Find all public spots in the system which doesnt belong to the user.
		final DetachedCriteria criteria = DetachedCriteria.forClass(Spot.class)
				.add(Restrictions.eq("publik", true));
		criteria.add(Restrictions.ne("user", user));
		final List publicSpots = getHibernateTemplate()
				.findByCriteria(criteria);
		// Find all the spots from the user.(Publics and privates).
		final DetachedCriteria criteria2 = DetachedCriteria
				.forClass(Spot.class).add(Restrictions.eq("user", user));
		final List userSpecificSpots = getHibernateTemplate().findByCriteria(
				criteria2);
		// Return a list containing the results of both queries.
		final List allVisibleSpots = new ArrayList();
		allVisibleSpots.addAll(publicSpots);
		allVisibleSpots.addAll(userSpecificSpots);

		return allVisibleSpots;
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#saveSpot(edu.unicen.surfforecaster.server.domain.entity.Spot)
	 */
	@Override
	public Integer saveSpot(final Spot spot) {
		getHibernateTemplate().saveOrUpdate(spot);
		return spot.getId();
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#getCountryById(java.lang.Integer)
	 */
	@Override
	public Country getCountryById(final Integer countryId) {
		Validate.notNull(countryId);
		return (Country) getHibernateTemplate().get(Country.class, countryId);
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#getSpotById(java.lang.Integer)
	 */
	@Override
	public Spot getSpotById(final Integer spotId) {
		Validate.notNull(spotId);
		return (Spot) getHibernateTemplate().get(Spot.class, spotId);
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#removeSpot(edu.unicen.surfforecaster.server.domain.entity.Spot)
	 */
	@Override
	public void removeSpot(final Spot spot) {
		Validate.notNull(spot);
		getHibernateTemplate().update(spot);
		final Collection<Forecaster> forecasters = spot.getForecasters();
		for (final Iterator iterator = forecasters.iterator(); iterator
				.hasNext();) {
			final Forecaster forecaster = (Forecaster) iterator.next();
			getHibernateTemplate().delete(forecaster);
		}
		getHibernateTemplate().delete(spot);
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#removeZone(edu.unicen.surfforecaster.server.domain.entity.Zone)
	 */
	@Override
	public void removeZone(final Zone zone) {
		Validate.notNull(zone);
		getHibernateTemplate().delete(zone);
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#removeCountry(edu.unicen.surfforecaster.server.domain.entity.Country)
	 */
	@Override
	public void removeCountry(final Country country) {
		Validate.notNull(country);
		getHibernateTemplate().delete(country);

	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#removeArea(edu.unicen.surfforecaster.server.domain.entity.Area)
	 */
	@Override
	public void removeArea(final Area area) {
		Validate.notNull(area);
		getHibernateTemplate().delete(area);

	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#getZoneByNameAndCountry(java.lang.String,
	 *      java.lang.Integer)
	 */
	@Override
	public Zone getZoneByNameAndCountry(final String zoneName,
			final Country country) {
		Validate.notEmpty(zoneName);
		Validate.notNull(country);
		final DetachedCriteria criteria = DetachedCriteria.forClass(Zone.class)
				.add(Restrictions.eq("name", zoneName));
		criteria.add(Restrictions.eq("country", country));
		final List<Zone> zones = getHibernateTemplate()
				.findByCriteria(criteria);
		if (zones.size() > 1)
			throw new DataIntegrityViolationException(
					"zone name is not unique for a given Country");
		if (zones.size() == 0)
			return null;
		return zones.get(0);
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#getPublicSpots()
	 */
	@Override
	public List<Spot> getPublicSpots() {
		final DetachedCriteria criteria = DetachedCriteria.forClass(Spot.class)
				.add(Restrictions.eq("publik", true));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#getAreaCountries(edu.unicen.surfforecaster.server.domain.entity.Area)
	 */
	@Override
	public List<Country> getAreaCountries(final Area area) {
		final DetachedCriteria criteria = DetachedCriteria.forClass(
				Country.class).add(Restrictions.eq("area", area));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#addForecasterToSpot(edu.unicen.surfforecaster.server.domain.entity.forecasters.Forecaster,
	 *      edu.unicen.surfforecaster.server.domain.entity.Spot)
	 */
	@Override
	public void addForecasterToSpot(final Forecaster f, final Spot s) {
		getHibernateTemplate().update(s);
		s.addForecaster(f);
		getHibernateTemplate().update(s);
	}

	@Override
	public void save(final Point point) {
		getHibernateTemplate().save(point);
	}

	@Override
	public Point getPoint(final float latitude, final float longitude) {

		final DetachedCriteria criteria = DetachedCriteria
				.forClass(Point.class).add(
						Restrictions.eq("latitude", latitude));
		criteria.add(Restrictions.eq("longitude", longitude));
		final List findByCriteria = getHibernateTemplate().findByCriteria(
				criteria);
		if (findByCriteria == null || findByCriteria.isEmpty())
			return null;
		return (Point) findByCriteria.get(0);
	}

	/**
	 * @see edu.unicen.surfforecaster.server.dao.SpotDAO#getAllCountries()
	 */
	@Override
	public List<Country> getAllCountries() {
		return getHibernateTemplate().loadAll(Country.class);
	}
}
