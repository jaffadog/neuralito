/**
 * 
 */
package edu.unicen.surfforecaster.gwt.client.dto;

import java.io.Serializable;

/**
 * @author maxi
 * 
 */
public class CountryGwtDTO implements Serializable, Comparable<CountryGwtDTO> {
	/**
	 * 
	 */
	public CountryGwtDTO() {
		// gwt purpose
	}

	private static final long serialVersionUID = 1L;
	public Integer id;
	public String name;
	private AreaGwtDTO areaDTO;

	/**
	 * @param id
	 * @param name
	 */
	public CountryGwtDTO(final Integer id, final String name,
			final AreaGwtDTO areaDTO) {
		this.id = id;
		this.name = name;
		this.areaDTO = areaDTO;
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
	 * @param id
	 *            the id to set
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the areaDTO
	 */
	public AreaGwtDTO getAreaDTO() {
		return areaDTO;
	}

	@Override
	public int compareTo(CountryGwtDTO o) {
		return this.getName().compareTo(o.getName());
	}
}
