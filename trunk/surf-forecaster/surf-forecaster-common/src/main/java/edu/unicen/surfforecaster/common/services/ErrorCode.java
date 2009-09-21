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
	DUPLICATED_USER(2);

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