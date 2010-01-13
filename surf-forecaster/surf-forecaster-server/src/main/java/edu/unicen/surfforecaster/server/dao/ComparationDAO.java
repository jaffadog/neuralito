/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import edu.unicen.surfforecaster.server.domain.entity.Comparation;

/**
 * @author esteban
 * 
 */
public interface ComparationDAO {
	public Integer saveComparation(Comparation c);

	/**
	 * @param comparationId
	 * @return
	 */
	public Comparation getComparationById(Integer comparationId);
}
