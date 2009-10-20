/**
 * 
 */
package edu.unicen.surfforecaster.common.services.dto;

import java.io.Serializable;

/**
 * A representation of a location.
 * 
 * @author esteban
 * 
 */
public class PointDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float longitude;
	private float latitude;

	/**
	 * 
	 */
	public PointDTO() {
		// GWT purpose
	}

	/**
	 * @return
	 */
	public float getLatitude() {
		return latitude;
	}

	/**
	 * @param longitude
	 * @param latitude
	 */
	public PointDTO(final float longitude, final float latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/**
	 * @return
	 */
	public float getLongitude() {
		return longitude;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {

		if (getLatitude() == ((PointDTO) obj).getLatitude()
				&& getLongitude() == ((PointDTO) obj).getLongitude())
			return true;
		return false;
	}
}
