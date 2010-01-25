package edu.unicen.surfforecaster.gwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.gwt.client.dto.ComparationGwtDTO;

public interface ComparationServicesAsync {
	/**
	 * Crete a new comparation and associate it to the user.
	 * 
	 * @param comparationName
	 * @param comparationDescription
	 * @param spotsIds
	 * @param callback the callback to return ComparationDTO
	 */
	public void addComparation(String comparationName, String comparationDescription, List<Integer> spotsIds, AsyncCallback<Integer> callback);

	/**
	 * Update the the comparation with given comparationId
	 * @param comparationId
	 * @param comparationDescription
	 * @param spotsIds
	 * @param callback the callback to return ComparationDTO
	 */
	public void updateComparation(Integer comparationId, String comparationDescription, List<Integer> spotsIds, AsyncCallback<Integer> callback);

	/**
	 * Remove the comparation with the given id.
	 * 
	 * @param comparationId
	 * @throws NeuralitoException
	 */
	public void removeComparation(Integer comparationId, AsyncCallback<Boolean> callback);

	/**
	 * List the comparations associated with the given user id.
	 * @param callback the callback to return List<ComparationDTO>
	 * @throws NeuralitoException
	 */
	public void getComparationsForUserId(AsyncCallback<List<ComparationGwtDTO>> callback);
	
	/**
	 * Retrieves a comparation by id
	 * 
	 * @param userId
	 * @param callback the callback to return ComparationDTO
	 * @throws NeuralitoException
	 */
	public void getComparationById(Integer comparationId, AsyncCallback<ComparationGwtDTO> callback);
	
}
