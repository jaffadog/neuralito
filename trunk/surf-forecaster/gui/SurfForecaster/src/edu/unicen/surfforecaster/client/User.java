package edu.unicen.surfforecaster.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements IsSerializable{
	
	private Integer id = null;
	private String name = null;
	private String lastName = null;
	private Integer type = null;
	private String userName = null;
	private String password = null;
	
	public User(){
		this.id = 1;
		this.name = "Administrador";
		this.lastName = "Administrador";
		this.type = 1;
		this.password = "admin";
		this.userName = "admin";
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isAdmin(){
		if (type == 1)
			return true;
		return false;
	}
}
