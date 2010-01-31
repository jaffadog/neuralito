/**
 * 
 */
package edu.unicen.surfforecaster.gwt.client.dto;

import java.io.Serializable;

/**
 * @author maxi
 * 
 */
public class AreaGwtDTO implements Serializable, Comparable<AreaGwtDTO> {
	/**
	 * 
	 */
	public AreaGwtDTO() {
		// gwt purpose
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String name;

	/**
	 * @param name
	 * @param id
	 */
	public AreaGwtDTO(final Integer id, final String name) {
		super();
		this.name = name;
		this.id = id;
	}

	private Integer id;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	@Override
	public int compareTo(AreaGwtDTO o) {
		return this.getName().compareTo(o.getName());
	}
}
