/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang.Validate;

import edu.unicen.surfforecaster.common.services.dto.ZoneDTO;

/**
 * A zone its a geographic area inside a country.
 * 
 * @author esteban
 * 
 */
@Entity
public class Zone {
	/**
	 * Entity autogenerated id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	/**
	 * The zone name.
	 */
	@Column(nullable = false, length = 255)
	private String name;
	/**
	 * The spots this zone contains.
	 */

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "zone")
	private Set<Spot> spots;
	/**
	 * The country this zone belongs.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private Country country;

	/**
	 * 
	 */
	public Zone() {
		// ORM purpose.
	}

	/**
	 * Creates a new Zone.
	 */
	public Zone(final String name) {
		Validate.notEmpty(name, "The zone name cannot be null");
		this.name = name;
		spots = new HashSet<Spot>();
	}

	/**
	 * @param names
	 * @param countryId
	 */
	public Zone(final String name, final Country country) {
		this.name = name;
		this.country = country;
	}

	/**
	 * Sets the area id.
	 * 
	 * @param id
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Set<Spot> getSpots() {
		return Collections.unmodifiableSet(spots);
	}

	public void addSpot(final Spot theSpot) {
		Validate.notNull(theSpot, "The spot to be added cannot be null");
		spots.add(theSpot);
	}

	public void removeSpot(final Spot theSpot) {
		Validate.notNull(theSpot, "The spot to be removed cannot be null");
		spots.remove(theSpot);
	}

	/**
	 * @return
	 */
	public ZoneDTO getDTO() {
		final ZoneDTO dto = new ZoneDTO(id, name);
		return dto;
	}

	/**
	 * @return
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @param country2
	 */
	public void setCountry(final Country country2) {
		country = country2;

	}
}
