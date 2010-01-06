/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.Validate;

import edu.unicen.surfforecaster.common.services.dto.ForecastAttributeDTO;
import edu.unicen.surfforecaster.common.services.dto.Unit;

/**
 * A forecast parameter. It may be double, integer, or string.
 * 
 * @author esteban
 * 
 */
@Entity
public class ForecastValue {
	/**
	 * The id for ORM pupose.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(length = 11)
	private Integer id;

	public Integer getId() {
		return id;
	}

	/**
	 * the parameter name
	 */
	@Column(nullable = false, length = 100)
	String parameterName;
	/**
	 * double value
	 */

	Double dValue;
	/**
	 * int value
	 */
	Integer iValue;
	/**
	 * string value
	 */
	@Column(length = 100)
	String sValue;

	/**
	 * The unit of the value.
	 */
	@Enumerated(EnumType.STRING)
	private Unit unit;
	private Float fValue;

	/**
	 * 
	 */
	public ForecastValue() {
		// ORM purpose.
	}

	/**
	 * @param string
	 * @param l
	 */
	public ForecastValue(final String parameterName, final Double value,
			final Unit unit) {
		Validate.notNull(value);
		Validate.notNull(unit);
		this.parameterName = parameterName;
		this.unit = unit;
		dValue = value;
	}

	/**
	 * @param string
	 * @param l
	 */
	public ForecastValue(final String parameterName,
			final String value,
			final Unit unit) {
		Validate.notNull(value);
		Validate.notNull(unit);
		this.parameterName = parameterName;
		this.unit = unit;
		sValue = value;
	}

	/**
	 * @param string
	 * @param l
	 */
	public ForecastValue(final String parameterName,
			final Integer value,
			final Unit unit) {
		Validate.notNull(value);
		Validate.notNull(unit);
		this.parameterName = parameterName;
		this.unit = unit;
		iValue = value;
	}

	/**
	 * @param parameterName
	 * @param f
	 * @param unit
	 */
	public ForecastValue(final String parameterName,
			final float f,
			final Unit unit) {
		Validate.notNull(f);
		Validate.notNull(unit);
		this.parameterName = parameterName;
		this.unit = unit;
		fValue = f;
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
		if (fValue != null)
			return fValue.toString();
		throw new IllegalStateException(
				"At least some of the three variables should be instantiated.");
	}

	/**
	 * @return the dValue
	 */
	public Double getdValue() {
		if (dValue == null)
			return -1D;
		return dValue;
	}

	/**
	 * @return the fValue
	 */
	public float getfValue() {
		return fValue;
	}

}
