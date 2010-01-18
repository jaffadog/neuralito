package edu.unicen.surfforecaster.gwt.client.dto;

import java.io.Serializable;

import edu.unicen.surfforecaster.common.services.dto.AreaDTO;
import edu.unicen.surfforecaster.common.services.dto.CountryDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.common.services.dto.ZoneDTO;

/**
 * This class is equals than SpotDTO except the timezone type as a string instead of a Timezone object
 * Gwt client side doens't support Timezone object
 * @author maxi
 * 
 */
public class SpotGwtDTO implements Serializable {
	
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
	
	/**
	 * Timezone ID
	 */
	private String timeZone;
	
	private PointDTO gridPoint;

	/**
	 * Empty constructor 
	 */
	public SpotGwtDTO() {
		// gwt purpose
	}
	
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
	public SpotGwtDTO(final Integer id, final String name, final PointDTO point,
			final ZoneDTO zone, final CountryDTO country, final AreaDTO area,
			final Integer userId, final boolean publik, String timeZone) {
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
	
	public PointDTO getGridPoint() {
		return gridPoint;
	}

	public void setGridPoint(PointDTO gridPoint) {
		this.gridPoint = gridPoint;
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

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public PointDTO getPoint() {
		return point;
	}

	public void setPoint(PointDTO point) {
		this.point = point;
	}
}
