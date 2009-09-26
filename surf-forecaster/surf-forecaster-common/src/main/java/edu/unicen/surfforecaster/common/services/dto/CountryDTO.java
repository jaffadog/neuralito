/**
 * 
 */
package edu.unicen.surfforecaster.common.services.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * @author esteban
 * 
 */
public class CountryDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	public Integer id;
	public Map<String, String> names;

	/**
	 * @param id
	 * @param names2
	 */
	public CountryDTO(final Integer id, final Map<String, String> names) {
		this.id = id;
		this.names = names;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the names
	 */
	public Map<String, String> getNames() {
		return names;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * @param names
	 *            the names to set
	 */
	public void setNames(final Map<String, String> names) {
		this.names = names;
	}
}
