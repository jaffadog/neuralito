package edu.unicen.surfforecaster.gwt.client.utils;

import java.util.HashMap;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;

public class ClientI18NMessages extends HashMap<String, String>{
	
	private static final long serialVersionUID = 1L;
	private static ClientI18NMessages instance = null;
	
	public static ClientI18NMessages getInstance() {
        if (instance == null) {
            instance = new ClientI18NMessages();
        }
        return instance;
    }
	
	
	
	private ClientI18NMessages() {
		super();
		this.initialize();
	}
	
	/**
	 * Rename get method from hashMap
	 * @param String key to search the according message
	 * @return String. Returns a localized message
	 */
	public String getMessage(String key) {
		String message = this.get(key); 
		if (message == null)
			message = "The message for this key: " + key + " is not generated yet.";
		return message;
	}
	
	/**
	 * Rename get method from hashMap
	 * @param NeuralitoException to search the according message
	 * @return String. Returns a localized message
	 */
	public String getMessage(NeuralitoException e) {
		String message = this.get(e.getErrorCode().toString()); 
		if (message == null)
			message = "The message for this error: " + e.getErrorCode().toString() + " is not generated yet.";
		return message;
	}
	
	/**
	 * Load the structure data
	 */
	private void initialize() {
		this.put("CHANGES_SAVED_SUCCESFULLY", GWTUtils.LOCALE_CONSTANTS.CHANGES_SAVED_SUCCESFULLY());
		this.put("MANDATORY_FIELDS", GWTUtils.LOCALE_CONSTANTS.MANDATORY_FIELDS());
		this.put("DATABASE_ERROR", GWTUtils.LOCALE_CONSTANTS.DATABASE_ERROR());
		this.put("DUPLICATED_USER_USERNAME", GWTUtils.LOCALE_CONSTANTS.DUPLICATED_USER_USERNAME());
		this.put("DUPLICATED_USER_EMAIL", GWTUtils.LOCALE_CONSTANTS.DUPLICATED_USER_EMAIL());
		this.put("INVALID_PASSWORD", GWTUtils.LOCALE_CONSTANTS.INVALID_PASSWORD());
//		this.put("USERNAME_EMPTY", GWTUtils.LOCALE_CONSTANTS.USERNAME_EMPTY());
//		this.put("PASSWORD_EMPTY", GWTUtils.LOCALE_CONSTANTS.PASSWORD_EMPTY());
//		this.put("EMAIL_EMPTY", GWTUtils.LOCALE_CONSTANTS.EMAIL_EMPTY());
//		this.put("USER_TYPE_NULL", GWTUtils.LOCALE_CONSTANTS.USER_TYPE_NULL());
		this.put("INVALID_LOGIN", GWTUtils.LOCALE_CONSTANTS.INVALID_LOGIN());
//		this.put("USER_ID_NULL", GWTUtils.LOCALE_CONSTANTS.USER_ID_NULL());
//		this.put("USER_ID_INVALID", GWTUtils.LOCALE_CONSTANTS.USER_ID_INVALID());
//		this.put("USER_ID_DOES_NOT_EXIST", GWTUtils.LOCALE_CONSTANTS.USER_ID_DOES_NOT_EXIST());
		this.put("USER_ROLE_INSUFFICIENT", GWTUtils.LOCALE_CONSTANTS.USER_ROLE_INSUFFICIENT());
		this.put("USER_NOT_SPOT_OWNER", GWTUtils.LOCALE_CONSTANTS.USER_NOT_SPOT_OWNER());
		this.put("USER_NOT_COMPARISON_OWNER", GWTUtils.LOCALE_CONSTANTS.USER_NOT_COMPARISON_OWNER());
	}
}
