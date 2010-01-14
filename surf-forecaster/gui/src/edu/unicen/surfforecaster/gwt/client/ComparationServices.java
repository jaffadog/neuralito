package edu.unicen.surfforecaster.gwt.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.gwt.client.dto.ComparationGwtDTO;

@RemoteServiceRelativePath("ComparationServices")
public interface ComparationServices extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static ComparationServicesAsync instance;

		public static ComparationServicesAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(ComparationServices.class);
			}
			return instance;
		}
	}
	
	/**
	 * Crete a new comparation and associate it to the user.
	 * 
	 * @param comparationName
	 * @param comparationDescription
	 * @param spotsIds
	 * @return ComparationDTO
	 */
	public Integer addComparation(String comparationName, String comparationDescription, List<Integer> spotsIds) throws NeuralitoException;

	/**
	 * Update the the comparation with given comparationId
	 * @param comparationId
	 * @param comparationDescription
	 * @param spotsIds
	 * @return ComparationDTO
	 */
	public Integer updateComparation(Integer comparationId, String comparationDescription, List<Integer> spotsIds) throws NeuralitoException;

	/**
	 * Remove the comparation with the given id.
	 * 
	 * @param comparationId
	 * @throws NeuralitoException
	 */
	public boolean removeComparation(Integer comparationId) throws NeuralitoException;

	/**
	 * List the comparations associated with the given user id.
	 * @return List<ComparationDTO>
	 * @throws NeuralitoException
	 */
	public List<ComparationGwtDTO> getComparationsForUserId() throws NeuralitoException;
	
	/**
	 * Retrieves a comparation by id
	 * 
	 * @param userId
	 * @return ComparationDTO
	 * @throws NeuralitoException
	 */
	public ComparationGwtDTO getComparationById(Integer comparationId);
	
}
