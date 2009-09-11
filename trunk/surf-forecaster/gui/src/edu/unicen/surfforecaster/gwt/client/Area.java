package edu.unicen.surfforecaster.gwt.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Area implements IsSerializable {
	
	private String id = null;
	private String name = null;
	
	public Area(){}
	
	public Area(String id, String name) {
		this.id = id;
		this.name = name;
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
}
