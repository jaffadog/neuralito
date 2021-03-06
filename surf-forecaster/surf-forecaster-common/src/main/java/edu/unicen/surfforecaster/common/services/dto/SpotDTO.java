/**
 * 
 */
package edu.unicen.surfforecaster.common.services.dto;

import java.io.Serializable;
import java.util.TimeZone;

/**
 * @author esteban
 * 
 */
public class SpotDTO implements Serializable, Comparable<SpotDTO> {
	/**
	 * 
	 */
	public SpotDTO() {
		// gwt purpose
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Spot Id.
	 */
	private Integer id;
	/**
	 * Spot name
	 */
	private String name;
	/**
	 * Point DTO
	 */
	private PointDTO point;

	/**
	 * Zone that this spot belongs.
	 */
	private ZoneDTO zone;
	/**
	 * Country that this spot belongs.
	 */
	private CountryDTO country;
	/**
	 * Area that this spot belongs.
	 */
	private AreaDTO area;
	/**
	 * The user id of the user who created this spot.
	 */
	private Integer userId;
	/**
	 * Visibility of spot.
	 */
	private boolean publik;
	private TimeZone timeZone;

	/**
	 * @param id
	 * @param name
	 * @param longitude
	 * @param latitude
	 * @param zone
	 * @param country
	 * @param area
	 * @param userId
	 * @param publik
	 */
	public SpotDTO(final Integer id, final String name, final PointDTO point,
			final ZoneDTO zone, final CountryDTO country, final AreaDTO area,
			final Integer userId, final boolean publik, final TimeZone timeZone) {
		super();
		this.id = id;
		this.name = name;
		this.zone = zone;
		this.point = point;
		this.country = country;
		this.area = area;
		this.userId = userId;
		this.publik = publik;
		this.timeZone = timeZone;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the zone
	 */
	public ZoneDTO getZone() {
		return zone;
	}

	/**
	 * @param id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the area
	 */
	public AreaDTO getArea() {
		return area;
	}

	/**
	 * @return the country
	 */
	public CountryDTO getCountry() {
		return country;
	}

	/**
	 * @return the publik
	 */
	public boolean isPublik() {
		return publik;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public PointDTO getPoint() {
		return point;
	}

	public void setPoint(final PointDTO point) {
		this.point = point;
	}

	@Override
	public int compareTo(SpotDTO o) {
		return this.getName().compareTo(o.getName());
	}
}
