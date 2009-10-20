/**
 * 
 */
package edu.unicen.surfforecaster.common.services.dto;

import java.io.Serializable;

/**
 * @author esteban
 * 
 */
public class ForecastAttributeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String value;
	private Unit unit;

	/**
	 * 
	 */
	public ForecastAttributeDTO() {
		// GWT purpose
	}

	/**
	 * @param string
	 * @param unit
	 */
	public ForecastAttributeDTO(final String value, final Unit unit) {
		this.value = value;
		this.unit = unit;

	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the unit
	 */
	public Unit getUnit() {
		return unit;
	}

}
