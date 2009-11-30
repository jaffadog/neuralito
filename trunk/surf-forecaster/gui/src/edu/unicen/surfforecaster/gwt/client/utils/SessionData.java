package edu.unicen.surfforecaster.gwt.client.utils;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

import edu.unicen.surfforecaster.common.services.dto.ComparationDTO;
import edu.unicen.surfforecaster.common.services.dto.UserDTO;

/**
 * This singleton contains information related with the current user session that is sometimes util for caching user data. Never use the userDTO data
 * from this class to check for user permissions to show/hide something due that the information in this singleton could be from an expired session
 * @author MAXI
 *
 */

//TODO si estaclase se usa solo del lado del server meterla alla. Tb evaluar si hace falta tenerla.
public class SessionData implements IsSerializable {
	
	private UserDTO userDTO;
	//private List<ComparationDTO> userComparations;
	
	private static SessionData instance = null;
	
	public static SessionData getInstance() {
        if (instance == null)
            instance = new SessionData();
        
        return instance;
    }
	
	public SessionData() {
		this.userDTO = null;
//		this.userComparations = null;
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

//	public List<ComparationDTO> getUserComparations() {
//		return userComparations;
//	}
//
//	public void setUserComparations(List<ComparationDTO> userComparations) {
//		this.userComparations = userComparations;
//	}
}
