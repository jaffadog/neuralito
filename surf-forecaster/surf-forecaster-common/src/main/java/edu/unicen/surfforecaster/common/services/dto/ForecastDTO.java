/**
 * 
 */
package edu.unicen.surfforecaster.common.services.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * A forecast issued at Base Time, and valid for BaseTime + forecastTime; The
 * forecast is composed of several parameters like: Wave Height, Wind Direction,
 * Wave Period...
 * 
 * 
 * @author esteban
 * 
 */
public class ForecastDTO implements Serializable {

	private static long serialVersionUID = 1L;
	private Map<String, ForecastAttributeDTO> forecastParameters;
	private Integer forecastTime;
	private Calendar baseDate;

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
	public ForecastDTO(final Calendar baseDate, final Integer forecastTime,
			final Map<String, ForecastAttributeDTO> map) {
		this.baseDate = baseDate;
		this.forecastTime = forecastTime;
		forecastParameters = map;
	}

	/**
	 * @return the map
	 */
	public Map<String, ForecastAttributeDTO> getMap() {
		return forecastParameters;
	}

	/**
	 * @return the baseDate
	 */
	public Calendar getBaseDate() {
		return baseDate;
	}

	/**
	 * @return the forecastTime
	 */
	public Integer getForecastTime() {
		return forecastTime;
	}

}
