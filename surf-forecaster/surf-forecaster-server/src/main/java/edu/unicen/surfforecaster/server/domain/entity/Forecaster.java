/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import edu.unicen.surfforecaster.common.services.ForecasterDTO;

/**
 * Abstract class to be extended by all forecasters.
 * 
 * @author esteban
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Forecaster {
	/**
	 * The id for ORM pupose.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(length = 11)
	private Integer id;
	@ManyToOne(cascade = CascadeType.PERSIST)
	// @JoinColumn(name = "spot_id", nullable = false)
	protected Spot spot;

	public Integer getId() {
		return id;
	}

	/**
	 * Obtain the description of the forecaster.
	 * 
	 * @return
	 */
	public abstract String getDescription();

	/**
	 * Obtain the name of the forecaster.
	 * 
	 * @return
	 */
	public abstract String getName();

	/**
	 * Obtain the most recent forecasts;
	 * 
	 * @param inputData
	 * @return
	 */
	public abstract Collection<Forecast> getLatestForecasts();

	/**
	 * Obtain the most recent forecasts;
	 * 
	 * @param inputData
	 * @return
	 */
	public abstract Collection<Forecast> getArchivedForecasts(Date from, Date to);

	/**
	 * @return
	 */
	public ForecasterDTO getDTO() {
		return new ForecasterDTO(id, getName(), getDescription());
	}
	
	public Spot getSpot(){
		return this.spot;
	}

}
