package edu.unicen.surfforecaster.gwt.client.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import edu.unicen.surfforecaster.common.services.dto.ForecastAttributeDTO;

/**
 * A forecast issued at Base Time, and valid for BaseTime + forecastTime; The
 * forecast is composed of several parameters like: Wave Height, Wind Direction,
 * Wave Period...
 * 
 * 
 * @author maxi
 * 
 */
public class ForecastGwtDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<String, ForecastAttributeDTO> forecastParameters;
	private Integer forecastTime;
	private Date baseDate;

	/**
	 * 
	 */
	public ForecastGwtDTO() {
		// GWT purpose
	}

	/**
	 * Constructor.
	 * 
	 * @param baseDate
	 * @param forecastTime
	 * @param map
	 */
	public ForecastGwtDTO(final Date baseDate, final Integer forecastTime, final Map<String, ForecastAttributeDTO> forecastParameters) {
		this.baseDate = baseDate;
		this.forecastTime = forecastTime;
		this.forecastParameters = forecastParameters;
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