package edu.unicen.surfforecaster.server.domain.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class SurfSpotRegion implements Serializable{
	
	private static final long serialVersionUID = 9213046827662186685L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@OneToMany
	private Collection<SurfSpot> surfSpots;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	/**
	 * @param surfSpots the surfSpots to set
	 */
	public void setSurfSpots(Collection<SurfSpot> surfSpots) {
		this.surfSpots = surfSpots;
	}

	/**
	 * @return the surfSpots
	 */
	public Collection<SurfSpot> getSurfSpots() {
		return surfSpots;
	} 

}
