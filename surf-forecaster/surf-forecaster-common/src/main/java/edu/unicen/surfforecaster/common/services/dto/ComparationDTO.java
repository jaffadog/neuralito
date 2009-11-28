
package edu.unicen.surfforecaster.common.services.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author maxi
 * 
 */
public class ComparationDTO implements Serializable {
	/**
	 * 
	 */
	public ComparationDTO() {
		// gwt purpose
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Spot Id.
	 */
	private Integer id;
	/**
	 * Spot name
	 */
	private String name;
	
	
	private Integer userId;
	
	private String description;
	
	private List<SpotDTO> spots;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SpotDTO> getSpots() {
		return spots;
	}

	public void setSpots(List<SpotDTO> spots) {
		this.spots = spots;
	}

	/**
	 * @param id
	 * @param name
	 * @param spots
	 * @param description
	 * @param userId
	 */
	public ComparationDTO(final Integer id, final String name, final List<SpotDTO> spots,
			final String description, final Integer userId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.spots = spots;
		this.userId = userId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param id
	 */
	public Integer getId() {
		return id;
	}

	
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

}
