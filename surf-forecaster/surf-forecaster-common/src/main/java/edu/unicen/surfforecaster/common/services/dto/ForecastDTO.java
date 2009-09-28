/**
 * 
 */
package edu.unicen.surfforecaster.common.services.dto;

import java.util.Date;
import java.util.Map;

/**
 * @author esteban
 * 
 */
public class ForecastDTO {

	private final Map<String, ForecastAttributeDTO> map;
	private final Date forecastDate;
	private final Date runDate;
	private final Date baseDate;

	/**
	 * @param baseDate
	 * @param runDate
	 * @param forecastDate
	 * @param map
	 */
	public ForecastDTO(final Date baseDate, final Date runDate,
			final Date forecastDate, final Map<String, ForecastAttributeDTO> map) {
		this.baseDate = baseDate;
		this.runDate = runDate;
		this.forecastDate = forecastDate;
		this.map = map;
	}

	/**
	 * @return the map
	 */
	public Map<String, ForecastAttributeDTO> getMap() {
		return map;
	}

	/**
	 * @return the forecastDate
	 */
	public Date getForecastDate() {
		return forecastDate;
	}

	/**
	 * @return the runDate
	 */
	public Date getRunDate() {
		return runDate;
	}

	/**
	 * @return the baseDate
	 */
	public Date getBaseDate() {
		return baseDate;
	}

}
