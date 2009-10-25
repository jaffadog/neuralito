package edu.unicen.surfforecaster.gwt.client.utils;

import com.google.gwt.user.client.rpc.IsSerializable;

import edu.unicen.surfforecaster.common.services.dto.UserDTO;

public class SessionData implements IsSerializable {
	
	private UserDTO userDTO = null;
	
	public SessionData() {}
	
	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
}
