/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.wavewatch;

/**
 * @author esteban
 * 
 */
public class ArchiveDetail {

	private final int year;

	private final int month;

	private final int availableForecasts;

	/**
	 * @param year
	 * @param month
	 * @param availableForecasts
	 */
	public ArchiveDetail(final int year, final int month,
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
