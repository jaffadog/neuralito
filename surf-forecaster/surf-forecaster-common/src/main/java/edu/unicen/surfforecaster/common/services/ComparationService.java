/**
 * 
 */
package edu.unicen.surfforecaster.common.services;

import java.util.List;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.ComparationDTO;

/**
 * @author esteban
 * 
 */
public interface ComparationService {

	/**
	 * Crete a new comparation and associate it to the user.
	 * 
	 * @param comparationName
	 * @param comparationDescription
	 * @param userId
	 * @param spotsIds
	 * @return
	 */
	public ComparationDTO addComparation(String comparationName,
			String comparationDescription, Integer userId,
			List<Integer> spotsIds) throws NeuralitoException;

	/**
	 * Update the the comparation with given comparationId
	 * 
	 * @param comparationId
	 * @param comparationDescription
	 * @param spotsIds
	 * @return
	 */
	public ComparationDTO updateComparation(Integer comparationId,
			String comparationDescription, List<Integer> spotsIds)
			throws NeuralitoException;

	/**
	 * Remove the comparation with the given id.
	 * 
	 * @param comparationId
	 * @throws NeuralitoException
	 */
	public void removeComparation(Integer comparationId)
			throws NeuralitoException;

	/**
	 * List the comparations associated with the given user id.
	 * 
	 * @param userId
	 * @return
	 * @throws NeuralitoException
	 */
	public List<ComparationDTO> getComparationsForUserId(Integer userId)
			throws NeuralitoException;

	public ComparationDTO getComparationById(Integer comparationId);

}
