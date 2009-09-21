/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.apache.commons.lang.Validate;

/**
 * Description for a given spot.
 * 
 * @author esteban
 * 
 */
@Entity
public class Description {
	/**
	 * Map containing language/text-description values.
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@MapKey(name = "key")
	private final Map<String, StringAttribute> texts = new HashMap<String, StringAttribute>();
	/**
	 * The id for ORM pupose.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	/**
	 * Constructor
	 */
	public Description() {
		// ORM purpose.
	}

	/**
	 * Add a text description for the given language
	 * 
	 * @param language
	 *            cannot be empty.
	 * @param text
	 *            cannot be empty.
	 */
	public void addText(final String language, final String text) {
		Validate.notEmpty(language, "Language cannot be empty");
		Validate.notEmpty(text, "Text cannot be empty");
		texts.put(language, new StringAttribute(language, text));
	}

	/**
	 * Obtain the text description for the given language
	 * 
	 * @param language
	 * @return text description, null if no description for given language.
	 */
	public String getText(final String language) {
		final StringAttribute value = texts.get(language);
		if (value != null)
			return value.getName();
		else
			return null;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final Integer id) {
		this.id = id;
	}
}
