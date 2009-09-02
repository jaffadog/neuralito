package edu.unicen.surfforecaster.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SessionData implements IsSerializable {
	private String userName = null;
	private Integer userType = null;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
}
