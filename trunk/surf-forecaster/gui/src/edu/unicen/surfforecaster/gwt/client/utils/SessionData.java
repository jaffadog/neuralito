package edu.unicen.surfforecaster.gwt.client.utils;

import com.google.gwt.user.client.rpc.IsSerializable;

import edu.unicen.surfforecaster.common.services.dto.UserDTO;

/**
 * This singleton contains information related with the current user session that is sometimes util for caching user data. Never use the userDTO data
 * from this class to check for user permissions to show/hide something due that the information in this singleton could be from an expired session
 * @author MAXI
 *
 */

public class SessionData implements IsSerializable {
	
	private UserDTO userDTO;
	
	private static SessionData instance = null;
	
	public static SessionData getInstance() {
        if (instance == null)
            instance = new SessionData();
        
        return instance;
    }
	
	public SessionData() {
		this.userDTO = null;
	}
	
	/**
	 * Clear all the session data.
	 */
	public void clear() {
		instance = null;
	}
	
	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
}
