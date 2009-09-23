package edu.unicen.surfforecaster.server.domain.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
 * Class that represents a geographic zone containing several countries.
 * 
 * @author esteban
 * 
 */
@Entity
public class Area implements Serializable {

	private static final long serialVersionUID = 9213046827662186685L;

	/**
	 * Entity autogenerated id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	/**
	 * The countries this area contains.
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private final Set<Country> countries = new HashSet<Country>();
	/**
	 * Map containing language/area-name values.
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@MapKey(name = "language")
	private final Map<String, I18nKeyValue> names = new HashMap<String, I18nKeyValue>();

	/**
	 * Constructor
	 */
	public Area() {
		// ORM purpose
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the countries
	 */
	public Set<Country> getCountries() {
		return Collections.unmodifiableSet(countries);
	}

	/**
	 * Adds a country to this area.
	 * 
	 * @param theCountry
	 */
	public void addCountry(final Country theCountry) {
		Validate.notNull(theCountry, "the country to be added cannot be null");
		countries.add(theCountry);
	}

	/**
	 * Removes a country from this area.
	 * 
	 * @param theCountry
	 */
	public void removeCountry(final Country theCountry) {
		Validate
				.notNull(theCountry, "the country to be removed cannot be null");
		countries.remove(theCountry);
	}

	/**
	 * Obtains the area name for the specified language.
	 * 
	 * @param language
	 * @return area name for the given language, null if no name for the given
	 *         language.
	 */
	public String getName(final String language) {
		Validate.notEmpty(language, "Language cannot be empty");
		final I18nKeyValue value = names.get(language);
		if (value != null)
			return names.get(language).getText();
		else
			return null;
	}

}
