package edu.unicen.surfforecaster.gwt.server.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.UserType;

public class UserRoles {
	
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
	}
	
	private void setAdministratorUserActions() {
		administratorUserActions = new Vector<String>();
		administratorUserActions.addAll(registeredUserActions);
		administratorUserActions.add("addArea");
	}
	
	public boolean hasPermission(UserType userType, String action) throws NeuralitoException {
		Vector<String> actions = userRoles.get(userType);
		if (actions.contains(action))
			return true;
		else
			throw new NeuralitoException(ErrorCode.USER_ROLE_INSUFFICIENT);
	}
	
	
	
	
}
