/**
 * 
 */
package edu.unicen.neuralito.domain.dao;

import java.util.Collection;

import edu.unicen.neuralito.domain.entity.SurfSpot;

/**
 * @author esteban
 *
 */
public interface SurfSpotDAO {

	public Collection<SurfSpot> getAllSurfSpots();
	
	public SurfSpot saveSurfSpot(SurfSpot surfSpot);
	
	
}
