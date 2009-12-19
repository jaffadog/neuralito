
package edu.unicen.surfforecaster.gwt.client.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author maxi
 * 
 */
public class ComparationGwtDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String name;
	
	private Integer userId;

	private String description;
	
	private List<SpotGwtDTO> spots;
	
	
	public ComparationGwtDTO() {
		// gwt purpose
	}
	
	/**
	 * @param id
	 * @param name
	 * @param spots
	 * @param description
	 * @param userId
	 */
	public ComparationGwtDTO(final Integer id, final String name, final List<SpotGwtDTO> spots,
			final String description, final Integer userId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.spots = spots;
		this.userId = userId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SpotGwtDTO> getSpots() {
		return spots;
	}

	public void setSpots(List<SpotGwtDTO> spots) {
		this.spots = spots;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public Integer getId() {
		return id;
	}

	public Integer getUserId() {
		return userId;
	}

}
