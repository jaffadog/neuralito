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
	private final double latitude;
	/**
	 * The longitude.
	 */
	private final double longitude;

	/**
	 * @param latitude
	 * @param longitude
	 */
	public Point(final double latitude, final double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
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
	 * @return
	 */
	public PointDTO getDTO() {
		// TODO Auto-generated method stub
		return null;
	}

}
