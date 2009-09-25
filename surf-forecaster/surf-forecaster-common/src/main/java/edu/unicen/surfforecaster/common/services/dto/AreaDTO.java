/**
 * 
 */
package edu.unicen.surfforecaster.common.services.dto;

import java.util.Map;

/**
 * @author esteban
 * 
 */
public class AreaDTO {
	private Map<String, String> names;

	/**
	 * @param names
	 * @param id
	 */
	public AreaDTO(final Integer id, final Map<String, String> names) {
		super();
		this.names = names;
		this.id = id;
	}

	private Integer id;

	/**
	 * @return the names
	 */
	public Map<String, String> getNames() {
		return names;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param names
	 *            the names to set
	 */
	public void setNames(final Map<String, String> names) {
		this.names = names;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final Integer id) {
		this.id = id;
	}
}
