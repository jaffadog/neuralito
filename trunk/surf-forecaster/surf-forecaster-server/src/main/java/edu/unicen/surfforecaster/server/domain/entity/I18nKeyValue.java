/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity;

import javax.persistence.Column;
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
public class I18nKeyValue {
	/**
	 * The id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	/**
	 * The key.
	 */
	@Column(nullable = false, length = 100)
	private String language;
	/**
	 * The value.
	 */
	@Column(nullable = false, length = 255)
	private String text;

	/**
	 * Constructor
	 */
	public I18nKeyValue() {
		// Orm purpose
	}

	/**
	 * Constructor
	 */
	public I18nKeyValue(final String language, final String name) {
		setLanguague(language);
		setText(name);
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
		return language;
	}

	/**
	 * @return the name
	 */
	public String getText() {
		return text;
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
		this.language = language;
	}

	/**
	 * @param text
	 *            the name to set
	 */
	public void setText(final String text) {
		Validate.notEmpty(text);
		this.text = text;
	}

}
