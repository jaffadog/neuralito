package edu.unicen.surfforecaster.gwt.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.ComparationDTO;
import edu.unicen.surfforecaster.common.services.dto.UserDTO;
import edu.unicen.surfforecaster.common.services.dto.UserType;

@RemoteServiceRelativePath("UserServices")
public interface UserServices extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static UserServicesAsync instance;

		public static UserServicesAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(UserServices.class);
			}
			return instance;
		}
	}

	UserDTO login(String userName, String password) throws NeuralitoException;
	
	Integer addUser(String name, String lastname, String email, String username, String password, UserType userType) throws NeuralitoException;
	
	boolean hasAccessTo(String action) throws NeuralitoException;

	void closeSession();
	
	List<ComparationDTO> getSpotsComparations() throws NeuralitoException;
	
	Integer saveComparation(String name, String description, List<Integer> spotsIds) throws NeuralitoException;
	
	UserDTO getLoggedUser();

}
