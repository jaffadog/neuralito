package edu.unicen.surfforecaster.server.dao;

import edu.unicen.surfforecaster.server.domain.entity.VisualObservationsSet;
/**
 * Data Access for the visual observations data sets.
 * @author esteban
 *
 */
public interface VisualObservationsSetDAO {
	/**
	 * Save the given visual observation data set. 
	 * @param dataSet
	 * @return id of the saved data set.
	 */
	public Integer save(VisualObservationsSet dataSet);
	/**
	 * Obtain the visual observation data set for the given id.
	 * @param id
	 * @return the data set, null if not exists.
	 */
	public VisualObservationsSet getVisualObservationSetById(Integer id);
}
