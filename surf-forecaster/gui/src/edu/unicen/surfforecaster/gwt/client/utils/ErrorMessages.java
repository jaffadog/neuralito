package edu.unicen.surfforecaster.gwt.client.utils;

import java.util.HashMap;

public class ErrorMessages {
	
	private static HashMap<String, String> errors;
	
	static {
		errors.put("DATABASE_ERROR", GWTUtils.LOCALE_CONSTANTS.DATABASE_ERROR());
		errors.put("DUPLICATED_USER_USERNAME", GWTUtils.LOCALE_CONSTANTS.DUPLICATED_USER_USERNAME());
	}
	
	public static String getErrorMessage(String key) {
		System.out.println("juan: " + key);
		return "juan" + key;
		//return errors.get(key);
	}
}
