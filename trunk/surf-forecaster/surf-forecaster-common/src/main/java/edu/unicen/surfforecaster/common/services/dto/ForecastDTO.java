/**
 * 
 */
package edu.unicen.surfforecaster.common.services.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * DTO for a Forecast
 * 
 * @author esteban
 * 
 */
public class ForecastDTO implements Serializable {

	private static long serialVersionUID = 1L;
	private Map<String, ForecastAttributeDTO> map;
	private Integer forecastTime;
	private Date baseDate;

	/**
	 * 
	 */
	public ForecastDTO() {
		// GWT purpose
	}

	/**
	 * Constructor.
	 * 
	 * @param baseDate
	 * @param forecastTime
	 * @param map
	 */
	public ForecastDTO(final Date baseDate, final Integer forecastTime,
			final Map<String, ForecastAttributeDTO> map) {
		this.baseDate = baseDate;
		this.forecastTime = forecastTime;
		this.map = map;
	}

	/**
	 * @return the map
	 */
	public Map<String, ForecastAttributeDTO> getMap() {
		return map;
	}

	/**
	 * @return the baseDate
	 */
	public Date getBaseDate() {
		return baseDate;
	}

	/**
	 * @return the forecastTime
	 */
	public Integer getForecastTime() {
		return forecastTime;
	}

}
