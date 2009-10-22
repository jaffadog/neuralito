package edu.unicen.surfforecaster.server.domain.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang.Validate;

import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;

/**
 * A surf spot.
 * 
 * @author esteban
 * 
 */
@Entity
public class Spot implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7077985551696073941L;
	/**
	 * The name of the surf spot.
	 */
	@Column(nullable = false, length = 100)
	private String name;
	/**
	 * The location of the spot
	 */
	@ManyToOne()
	private Point location;
	/**
	 * If this spots is public or not.
	 */
	@Column(nullable = false)
	private boolean publik;
	/**
	 * Spot creator.
	 */
	@ManyToOne()
	private User user;

	/**
	 * The zone where this spot is located.
	 */
	@ManyToOne()
	private Zone zone;
	/**
	 * Description of the spot.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private Description description;

	/**
	 * The available forecasters for this spot.
	 */
	@OneToMany(cascade = CascadeType.ALL)
	private Collection<Forecaster> forecasters;
	/**
	 * The id for ORM pupose.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(length = 11)
	private Integer id;
	/**
	 * The timezone of the spot.
	 */
	@Column(nullable = false)
	private String timeZone;

	public Integer getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public Point getLocation() {
		return location;
	}

	/**
	 * @param id
	 *            to set.
	 */
	public void setId(final Integer theId) {
		id = theId;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(final Description description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public Description getDescription() {
		return description;
	}

	/**
	 * @param publik
	 *            the publik to set
	 */
	public void setPublik(final boolean publik) {
		this.publik = publik;
	}

	/**
	 * @return the publik
	 */
	public boolean isPublik() {
		return publik;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(final User user) {
		this.user = user;
	}

	/**
	 * @return the zone
	 */
	public Zone getZone() {
		return zone;
	}

	/**
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(final Zone zone) {
		this.zone = zone;
	}

	/**
	 * Creates the dto for this instance.
	 * 
	 * @param spot
	 * @return
	 */
	public SpotDTO getDTO(final Spot spot) {
		final SpotDTO spotDTO = new SpotDTO(spot.getId(), spot.getName(),
				new PointDTO(location.getLatitude(), location.getLongitude()),
				spot.getZone().getDTO(), spot.getZone().getCountry().getDTO(),
				spot.getZone().getCountry().getArea().getDTO(), spot.getUser()
						.getId(), spot.isPublik());
		return spotDTO;
	}

	/**
	 * Add a forecaster to this spot.
	 */
	public void addForecaster(final Forecaster forecaster) {
		Validate.notNull(forecaster);
		forecasters.add(forecaster);
	}

	/**
	 * Obtain forecasters for this spot.
	 */
	public Collection<Forecaster> getForecasters() {
		return Collections.unmodifiableCollection(forecasters);

	}

	/**
	 * Add a forecaster to this spot.
	 */
	public void removeForecaster(final Forecaster forecaster) {
		Validate.notNull(forecaster);
		forecasters.remove(forecaster);
	}

	/**
	 * @param timeZone
	 */
	public void setTimeZone(final String timeZone) {
		this.timeZone = timeZone;

	}

	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(final Point location) {
		this.location = location;
	}

}
