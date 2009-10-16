/**
 * 
 */
package edu.unicen.surfforecaster.common.services;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

/**
 * A forecaster DTO. Containing the general description of a forecaster.
 * 
 * @author esteban
 * 
 */
public class ForecasterDTO implements Serializable {

	/**
	 * The id of the forecaster.
	 */
	private Integer id;
	/**
	 * The name of the forecaster.
	 */
	private String name;
	/**
	 * The description of the forecaster.
	 */
	private String description;
	/**
	 * The serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ForecasterDTO() {
		// Gwt purpose.
	}

	/**
	 * @param id
	 * @param name
	 * @param description
	 */
	public ForecasterDTO(final Integer id, final String name,
			final String description) {
		Validate.notNull(id);
		this.id = id;
		this.name = name;
		this.description = description;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

}
