package edu.unicen.surfforecaster.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.UserDTO;
import edu.unicen.surfforecaster.gwt.client.utils.SessionData;

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
	
	Integer addUser(String name, String lastname, String email, String username, String password, int type) throws NeuralitoException;

	SessionData getSessionData();

	void closeSession();

}
