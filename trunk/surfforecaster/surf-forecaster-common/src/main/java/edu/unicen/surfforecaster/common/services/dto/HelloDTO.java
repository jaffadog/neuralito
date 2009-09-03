package edu.unicen.surfforecaster.common.services.dto;

import java.io.Serializable;

public class HelloDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -7615524813500811273L;
private String g;

public String getG() {
	return g;
}

public void setG(String g) {
	this.g = g;
}
@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "The Hello DTO: " + this.g + " .";
	}
}
