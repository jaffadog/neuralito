/**
 * 
 */
package edu.unicen.surfforecaster.server.domain;

import java.util.Date;

/**
 * This class is used to hold the grib decoded data.As the grib decoder decodes
 * many forecasts at a time a simple object to hold this data was needed.
 * 
 * @author esteban
 * 
 */
public class ForecastPlain {
	// date
	private final Date issuedDate;
	private final int validTime;
	// location
	private final Float latitude;
	private final Float longitude;
	// Wave Parameters
	private final Float windWaveHeight;
	private final Float windWavePeriod;
	private final Float windWaveDirection;
	private final Float swellWaveHeight;
	private final Float swellWavePeriod;
	private final Float swellWaveDirection;
	private final Float combinedWaveHeight;
	private final Float peakWavePeriod;
	private final Float peakWaveDirection;
	private final Float windSpeed;
	private final Float windDirection;
	private final Float windU;
	private final Float windV;

	/**
	 * @param issuedDate
	 * @param validTime
	 * @param latitude
	 * @param longitude
	 * @param windWaveHeight
	 * @param windWavePeriod
	 * @param windWaveDirection
	 * @param swellWaveHeight
	 * @param swellWavePeriod
	 * @param swellWaveDirection
	 * @param combinedWaveHeight
	 * @param peakWavePeriod
	 * @param peakWaveDirection
	 * @param windSpeed
	 * @param windDirection
	 * @param windU
	 * @param windV
	 */
	public ForecastPlain(final Date issuedDate,
			final int validTime, final Float latitude, final Float longitude,
			final Float windWaveHeight, final Float windWavePeriod,
			final Float windWaveDirection, final Float swellWaveHeight,
			final Float swellWavePeriod, final Float swellWaveDirection,
			final Float combinedWaveHeight, final Float peakWavePeriod,
			final Float peakWaveDirection, final Float windSpeed,
			final Float windDirection, final Float windU, final Float windV) {
		super();
		this.issuedDate = issuedDate;
		this.validTime = validTime;
		this.latitude = latitude;
		this.longitude = longitude;
		this.windWaveHeight = windWaveHeight;
		this.windWavePeriod = windWavePeriod;
		this.windWaveDirection = windWaveDirection;
		this.swellWaveHeight = swellWaveHeight;
		this.swellWavePeriod = swellWavePeriod;
		this.swellWaveDirection = swellWaveDirection;
		this.combinedWaveHeight = combinedWaveHeight;
		this.peakWavePeriod = peakWavePeriod;
		this.peakWaveDirection = peakWaveDirection;
		this.windSpeed = windSpeed;
		this.windDirection = windDirection;
		this.windU = windU;
		this.windV = windV;
	}

	/**
	 * @return the issuedDate
	 */
	public Date getIssuedDate() {
		return issuedDate;
	}

	/**
	 * @return the validTime
	 */
	public int getValidTime() {
		return validTime;
	}

	/**
	 * @return the latitude
	 */
	public Float getLatitude() {
		return latitude;
	}

	/**
	 * @return the longitude
	 */
	public Float getLongitude() {
		return longitude;
	}

	/**
	 * @return the windWaveHeight
	 */
	public Float getWindWaveHeight() {
		return windWaveHeight;
	}

	/**
	 * @return the windWavePeriod
	 */
	public Float getWindWavePeriod() {
		return windWavePeriod;
	}

	/**
	 * @return the windWaveDirection
	 */
	public Float getWindWaveDirection() {
		return windWaveDirection;
	}

	/**
	 * @return the swellWaveHeight
	 */
	public Float getSwellWaveHeight() {
		return swellWaveHeight;
	}

	/**
	 * @return the swellWavePeriod
	 */
	public Float getSwellWavePeriod() {
		return swellWavePeriod;
	}

	/**
	 * @return the swellWaveDirection
	 */
	public Float getSwellWaveDirection() {
		return swellWaveDirection;
	}

	/**
	 * @return the combinedWaveHeight
	 */
	public Float getCombinedWaveHeight() {
		return combinedWaveHeight;
	}

	/**
	 * @return the peakWavePeriod
	 */
	public Float getPeakWavePeriod() {
		return peakWavePeriod;
	}

	/**
	 * @return the peakWaveDirection
	 */
	public Float getPeakWaveDirection() {
		return peakWaveDirection;
	}

	/**
	 * @return the windSpeed
	 */
	public Float getWindSpeed() {
		return windSpeed;
	}

	/**
	 * @return the windDirection
	 */
	public Float getWindDirection() {
		return windDirection;
	}

	/**
	 * @return the windU
	 */
	public Float getWindU() {
		return windU;
	}

	/**
	 * @return the windV
	 */
	public Float getWindV() {
		return windV;
	}

}
