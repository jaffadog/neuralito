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

}
