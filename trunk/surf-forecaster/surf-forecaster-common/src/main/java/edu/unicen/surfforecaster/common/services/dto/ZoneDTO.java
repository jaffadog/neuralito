/**
 * 
 */
package edu.unicen.surfforecaster.common.services.dto;


/**
 * @author esteban
 * 
 */
public class ZoneDTO {
	/**
	 * Zone id
	 */
	private final Integer id;
	/**
	 * Zone name.
	 */
	private final String name;

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
