package edu.unicen.surfforecaster.gwt.server.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.UserType;
import edu.unicen.surfforecaster.gwt.server.ServicesImpl;

public class UserRoles {
	
	/**
	 * Logger.
	 */
	Logger logger = Logger.getLogger(UserRoles.class);
	
	private Vector<String> registeredUserActions = null;
	private Vector<String> administratorUserActions = null;
	private Map<UserType, Vector<String>> userRoles = null;
	private static UserRoles instance = null;
	
	public static UserRoles getInstance() {
        if (instance == null) {
            instance = new UserRoles();
        }
        return instance;
    }
	
	private UserRoles() {
		this.setRegisteredUserActions();
		this.setAdministratorUserActions();
		userRoles = new HashMap<UserType, Vector<String>>();
		userRoles.put(UserType.REGISTERED_USER, registeredUserActions);
		userRoles.put(UserType.ADMINISTRATOR, administratorUserActions);
	}
	
	private void setRegisteredUserActions() {
		registeredUserActions = new Vector<String>();
		registeredUserActions.add("addSpot");
		//registeredUserActions.add("addComparation");
	}
	
	private void setAdministratorUserActions() {
		administratorUserActions = new Vector<String>();
		administratorUserActions.addAll(registeredUserActions);
		administratorUserActions.add("addArea");
		administratorUserActions.add("addComparation");
	}
	
	/**
	 * Check if the userType role as parameter has access or not to perform the action called
	 * @param userType
	 * @param action
	 * @return boolean Returns true if the userType role could perform the action or throws a neuralitoException if not.
	 * @throws NeuralitoException
	 */
	public boolean hasPermission(UserType userType, String action) throws NeuralitoException {
		logger.log(Level.INFO, "UserRoles - hasPermission - Checking permissions for " + userType + " to perform \"" + action + "\"....");
		Vector<String> actions = userRoles.get(userType);
		if (actions.contains(action)) {
			logger.log(Level.INFO, "UserRoles - hasPermission - " + userType + " authorized to perform \"" + action + "\".");
			return true;
		} else {
			logger.log(Level.INFO, "UserRoles - hasPermission - " + userType + " has not permission to perform \"" + action + "\".");
			throw new NeuralitoException(ErrorCode.USER_ROLE_INSUFFICIENT);
		}
	}
	
	
	
	
}
