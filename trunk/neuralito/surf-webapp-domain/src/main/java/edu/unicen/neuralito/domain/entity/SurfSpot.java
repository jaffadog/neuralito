package edu.unicen.neuralito.domain.entity;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * A surf spot. 
 * @author esteban
 *
 */
@Entity
public class SurfSpot implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7077985551696073941L;
	/**
	 * The name of the surf spot.
	 */
	private String name;
	/**
	 * Latitude of the surf spot. TODO: define in whic units latitude is given.
	 */
	private double latitude;
	/**
	 * Longitude of the surf spot. TODO: define in whic units latitude is given.
	 */	 
	private double longitude;
	/**
	 * The region where this spot is located.
	 */
//	
//	private SurfSpotRegion region;
//	/**
//	 * The charactetistics of the spot. Could be information about crowd, sea bed, hazzards. 
//	 */
//	private Map<String, Object> characteristics;
//	/**
//	 * The id for ORM pupose.
//	 */
	private Integer id;
	
	@Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  public Integer getId()
	  {
	    return id;
	  }
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
//	/**
//	 * @return the region
//	 */
//	public SurfSpotRegion getRegion() {
//		return region;
//	}
//	/**
//	 * @return the characteristics
//	 */
//	public Map<String, Object> getCharacteristics() {
//		return characteristics;
//	}
	/**
	 * @param id to set.
	 */
	public void setId(Integer theId) {
		this.id = theId;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
//	/**
//	 * @param region the region to set
//	 */
//	public void setRegion(SurfSpotRegion region) {
//		this.region = region;
//	}
//	/**
//	 * @param characteristics the characteristics to set
//	 */
//	public void setCharacteristics(Map<String, Object> characteristics) {
//		this.characteristics = characteristics;
//	}
//	
	

}
