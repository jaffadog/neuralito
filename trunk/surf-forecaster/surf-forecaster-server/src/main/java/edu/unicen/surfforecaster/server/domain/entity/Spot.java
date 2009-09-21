package edu.unicen.surfforecaster.server.domain.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
	private String name;
	/**
	 * Latitude of the surf spot. TODO: define in whic units latitude is given.
	 */
	private double latitude;
	/**
	 * Longitude of the surf spot. TODO: define in whic units latitude is given.
	 */
	private double longitude;
	/**
	 * The region where this spot is located.
	 */
	@ManyToOne
	private Area region;

	/**
	 * Description of the spot.
	 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Description description;
	/**
	 * The id for ORM pupose.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	public Integer getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @return the region
	 */
	public Area getRegion() {
		return region;
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
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(final double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(final double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @param region
	 *            the region to set
	 */
	public void setRegion(final Area region) {
		this.region = region;
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

}
