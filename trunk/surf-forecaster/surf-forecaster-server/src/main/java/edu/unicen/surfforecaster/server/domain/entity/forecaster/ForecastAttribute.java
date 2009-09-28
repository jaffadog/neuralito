/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang.Validate;

import edu.unicen.surfforecaster.common.services.dto.ForecastAttributeDTO;
import edu.unicen.surfforecaster.common.services.dto.Unit;

/**
 * @author esteban
 * 
 */
public class ForecastAttribute {
	/**
	 * the attribute
	 */
	String attribute;
	/**
	 * double
	 */
	Double dValue;
	/**
	 * int value
	 */
	Integer iValue;
	/**
	 * string value
	 */
	String sValue;
	@Enumerated(EnumType.STRING)
	private final Unit unit;

	/**
	 * @param string
	 * @param l
	 */
	public ForecastAttribute(final String attribute, final Double value,
			final Unit unit) {
		Validate.notNull(attribute);
		Validate.notNull(value);
		Validate.notNull(unit);
		this.attribute = attribute;
		this.unit = unit;
		dValue = value;
	}

	/**
	 * @param string
	 * @param l
	 */
	public ForecastAttribute(final String attribute, final String value,
			final Unit unit) {
		Validate.notNull(attribute);
		Validate.notNull(value);
		Validate.notNull(unit);
		this.attribute = attribute;
		this.unit = unit;
		sValue = value;
	}

	/**
	 * @param string
	 * @param l
	 */
	public ForecastAttribute(final String attribute, final Integer value,
			final Unit unit) {
		Validate.notNull(attribute);
		Validate.notNull(value);
		Validate.notNull(unit);
		this.attribute = attribute;
		this.unit = unit;
		iValue = value;
	}

	/**
	 * @return the unit
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * @return
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * @return
	 */
	public ForecastAttributeDTO getDTO() {
		return new ForecastAttributeDTO(toString(), unit);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (dValue != null)
			return dValue.toString();
		if (iValue != null)
			return dValue.toString();
		if (sValue != null)
			return sValue.toString();
		throw new IllegalStateException(
				"At least some of the three variables should be instantiated.");
	}

}
