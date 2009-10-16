/**
 * 
 */
package edu.unicen.surfforecaster.common.services.dto;

import java.io.Serializable;

/**
 * @author esteban
 * 
 */
public class ZoneDTO implements Serializable {

	/**
	 * 
	 */
	public ZoneDTO() {
		// GWT purpose.
	}

	private static final long serialVersionUID = 1L;
	/**
	 * Zone id
	 */
	private Integer id;
	/**
	 * Zone name.
	 */
	private String name;

	/**
	 * @param id
	 * @param name
	 */
	public ZoneDTO(final Integer id, final String name) {
		super();
		this.id = id;
		this.name = name;
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
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		final ZoneDTO dto2 = (ZoneDTO) obj;
		if (getId().equals(dto2.getId()) && getName().equals(dto2.getName()))
			return true;
		else
			return false;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (null == id ? 0 : id);
		hash = 31 * hash + (null == name ? 0 : name.hashCode());
		return hash;

	}
}
