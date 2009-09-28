/**
 * 
 */
package edu.unicen.surfforecaster.common.services.dto;

import java.io.Serializable;

/**
 * @author esteban
 * 
 */
public class PointDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double longitude;
	private double latitude;

	/**
	 * 
	 */
	public PointDTO() {
		// GWT purpose
	}

	/**
	 * @return
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param longitude
	 * @param latitude
	 */
	public PointDTO(final double longitude, final double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/**
	 * @return
	 */
	public double getLongitude() {
		return longitude;
	}

}
