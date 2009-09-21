/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.Validate;

/**
 * @author esteban
 * 
 */
@Entity
public class StringAttribute {
	/**
	 * The id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	/**
	 * The key.
	 */
	private String key;
	/**
	 * The value.
	 */
	private String value;

	/**
	 * Constructor
	 */
	public StringAttribute() {
		// Orm purpose
	}

	/**
	 * Constructor
	 */
	public StringAttribute(final String language, final String name) {
		setLanguague(language);
		setName(name);
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the languague
	 */
	public String getLanguague() {
		return key;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return value;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * @param language
	 *            the languague to set
	 */
	public void setLanguague(final String language) {
		Validate.notEmpty(language);
		this.key = language;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		Validate.notEmpty(name);
		this.value = name;
	}

}
