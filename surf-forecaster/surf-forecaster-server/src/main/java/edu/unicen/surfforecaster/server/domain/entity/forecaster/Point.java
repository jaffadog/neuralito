/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import edu.unicen.surfforecaster.common.services.dto.PointDTO;

/**
 * @author esteban
 * 
 */
@Entity
public class Point {
	/**
	 * The id for ORM pupose.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(length = 11)
	private Integer id;

	public Integer getId() {
		return id;
	}

	/**
	 * The latitude.
	 */
	private Double latitude;
	/**
	 * The longitude.
	 */
	private Double longitude;

	/**
	 * 
	 */
	public Point() {
		// ORM purpose
	}

	/**
	 * @param latitude
	 * @param longitude
	 */
	public Point(final double latitude, final double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @return
	 */
	public PointDTO getDTO() {
		return new PointDTO(latitude, longitude);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		final Point point = (Point) obj;
		if (getLatitude().equals(point.getLatitude())
				&& getLongitude().equals(point.getLongitude()))
			return true;
		else
			return false;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (null == latitude ? 0 : latitude.intValue());
		hash = 31 * hash + (null == longitude ? 0 : longitude.intValue());
		return hash;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Lat/Lon: " + latitude + ";" + longitude;
	}
}
