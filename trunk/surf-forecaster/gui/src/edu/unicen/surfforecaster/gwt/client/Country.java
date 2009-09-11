package edu.unicen.surfforecaster.gwt.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Country implements IsSerializable {
	
	private String id = null;
	private String name = null;
	private String parent = null;
	
	public Country(String id, String name, String parent) {
		this.id = id;
		this.name = name;
		this.parent = parent;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Country() {
		// TODO Auto-generated constructor stub
	}

}
