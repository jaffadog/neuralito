/**
 * 
 */
package edu.unicen.surfforecaster.common.services.dto;

/**
 * @author esteban
 * 
 */
public class SpotDTO {
	/**
	 * Spot Id.
	 */
	private final Integer id;
	/**
	 * Spot name
	 */
	private final String name;
	/**
	 * Longitude where this spot is located.
	 */
	private final double longitude;
	/**
	 * Latitude where this spot is located.
	 */
	private final double latitude;
	/**
	 * Zone that this spot belongs.
	 */
	private final ZoneDTO zone;
	/**
	 * Country that this spot belongs.
	 */
	private final CountryDTO country;
	/**
	 * Area that this spot belongs.
	 */
	private final AreaDTO area;
	/**
	 * The user id of the user who created this spot.
	 */
	private final Integer userId;
	/**
	 * Visibility of spot.
	 */
	private final boolean publik;

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
	public SpotDTO(final Integer id, final String name, final double longitude,
			final double latitude, final ZoneDTO zone,
			final CountryDTO country, final AreaDTO area, final Integer userId,
			final boolean publik) {
		super();
		this.id = id;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.zone = zone;
		this.country = country;
		this.area = area;
		this.userId = userId;
		this.publik = publik;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
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

}
