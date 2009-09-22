package edu.unicen.surfforecaster.common.services;

import java.io.Serializable;

public enum ErrorCode implements Serializable {
	/**
	 * Database exception
	 */
	DATABASE_ERROR(1),
	/**
	 * The username already exists
	 */
	DUPLICATED_USER_USERNAME(2),
	/**
	 * The user email already exist for another user.
	 */
	DUPLICATED_USER_EMAIL(3),
	/**
	 * The password is not a valid password. It does not match security
	 * policies.
	 */
	INVALID_PASSWORD(4),
	/**
	 * Username is empty.It is null or it has no value.
	 */
	USERNAME_EMPTY(5),
	/**
	 * Password is empty.It is null or it has no value.
	 */
	PASSWORD_EMPTY(6),
	/**
	 * Email is empty.It is null or it has no value.
	 */
	EMAIL_EMPTY(7),
	/**
	 * User type is empty.It is null or it has no value.
	 */
	USER_TYPE_EMPTY(8),
	/**
	 * Login action was incorrect. Either username not exists, or password does
	 * not correspond to user.
	 */
	INVALID_LOGIN(9);

	private int code;

	ErrorCode(final int tcode) {
		code = tcode;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

}