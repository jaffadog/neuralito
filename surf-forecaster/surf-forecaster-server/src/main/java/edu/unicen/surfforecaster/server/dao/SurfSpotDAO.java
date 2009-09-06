/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import java.util.Collection;

import edu.unicen.surfforecaster.server.domain.entity.SurfSpot;

/**
 * @author esteban
 *
 */
public interface SurfSpotDAO {

	public Collection<SurfSpot> getAllSurfSpots();
	
	public SurfSpot saveSurfSpot(SurfSpot surfSpot);
	
	
}
