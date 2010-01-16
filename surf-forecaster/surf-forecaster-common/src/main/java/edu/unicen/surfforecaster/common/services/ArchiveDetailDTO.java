/**
 * 
 */
package edu.unicen.surfforecaster.common.services;

import java.io.Serializable;

/**
 * @author esteban
 * 
 */
public class ArchiveDetailDTO implements Serializable {

	/**
	 * 
	 */
	public ArchiveDetailDTO() {
		// GWT purpose
	}

	private int year;

	private int month;

	private int availableForecasts;

	/**
	 * @param year
	 * @param month
	 * @param availableForecasts
	 */
	public ArchiveDetailDTO(final int year, final int month,
			final int availableForecasts) {
		super();
		this.year = year;
		this.month = month;
		this.availableForecasts = availableForecasts;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @return the availableForecasts
	 */
	public int getAvailableForecasts() {
		return availableForecasts;
	}

}
