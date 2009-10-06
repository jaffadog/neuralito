package edu.unicen.surfforecaster.gwt.client.utils;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SessionData implements IsSerializable {
	private String userName = null;
	private String userType = null;
	private String userId = null;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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
