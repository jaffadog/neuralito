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
	/**
	 * 
	 */
	public CountryDTO() {
		// gwt purpose
	}

	private static final long serialVersionUID = 1L;
	public Integer id;
	public Map<String, String> names;
	private AreaDTO areaDTO;

	/**
	 * @param id
	 * @param names2
	 */
	public CountryDTO(final Integer id, final Map<String, String> names,
			final AreaDTO areaDTO) {
		this.id = id;
		this.names = names;
		this.areaDTO = areaDTO;
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

	/**
	 * @return the areaDTO
	 */
	public AreaDTO getAreaDTO() {
		return areaDTO;
	}
}
