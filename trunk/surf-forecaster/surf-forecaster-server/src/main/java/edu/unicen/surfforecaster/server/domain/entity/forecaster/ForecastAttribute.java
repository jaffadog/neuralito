/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster;

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
 * A forecast attribute. It may be double, integer, or string.
 * 
 * @author esteban
 * 
 */
@Entity
public class ForecastAttribute {
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
	 * the attribute
	 */
	@Column(nullable = false, length = 100)
	String attributeName;
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
	 * The unit of the attribute.
	 */
	@Enumerated(EnumType.STRING)
	private Unit unit;

	/**
	 * 
	 */
	public ForecastAttribute() {
		// ORM purpose.
	}

	/**
	 * @param string
	 * @param l
	 */
	public ForecastAttribute(final String attribute, final Double value,
			final Unit unit) {
		Validate.notNull(attribute);
		Validate.notNull(value);
		Validate.notNull(unit);
		attributeName = attribute;
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
		attributeName = attribute;
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
		attributeName = attribute;
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
	public String getAttributeName() {
		return attributeName;
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
