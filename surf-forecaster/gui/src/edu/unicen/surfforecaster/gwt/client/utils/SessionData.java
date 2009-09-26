package edu.unicen.surfforecaster.gwt.client.utils;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SessionData implements IsSerializable {
	private String userName = null;
	private String userType = null;
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}
}
