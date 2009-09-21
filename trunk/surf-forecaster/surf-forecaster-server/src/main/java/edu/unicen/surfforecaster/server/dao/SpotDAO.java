/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import java.util.List;

import edu.unicen.surfforecaster.server.domain.entity.Area;
import edu.unicen.surfforecaster.server.domain.entity.Country;
import edu.unicen.surfforecaster.server.domain.entity.Spot;
import edu.unicen.surfforecaster.server.domain.entity.Zone;

/**
 * @author esteban
 *
 */
public interface SpotDAO {

	public Area getAreaById(int id);
	
	public List<Area> getAllAreas();
	
	public Spot updateSpot(Spot surfSpot);
	
	public Area saveArea(Area area);
	
	public Zone saveZone(Zone zone);
	
	public Country saveCountry(Country country);
	
}
