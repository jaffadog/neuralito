package edu.unicen.surfforecaster.common.exceptions;

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
	USER_TYPE_NULL(8),
	/**
	 * Login action was incorrect. Either username not exists, or password does
	 * not correspond to user.
	 */
	INVALID_LOGIN(9),
	/**
	 * The user id is empty.
	 */
	USER_ID_NULL(10),
	/**
	 * The user id is not a valid one.
	 */
	USER_ID_INVALID(11),
	/**
	 * There is no user with the given user id.
	 */
	USER_ID_DOES_NOT_EXIST(12),
	/**
	 * Spot id does not exists.
	 */
	SPOT_ID_DOES_NOT_EXISTS(13),
	/**
	 * Spot id cannot be null.
	 */
	SPOT_ID_NULL(14),
	/**
	 * Spot id is invalid.
	 */
	SPOT_ID_INVALID(15),
	/**
	 * The zone id cannot be null.
	 */
	ZONE_ID_NULL(16),
	/**
	 * The zone id is not a valid id.
	 */
	ZONE_ID_INVALID(17),
	/**
	 * The zone id does not exists.
	 */
	ZONE_ID_DOES_NOT_EXISTS(18),
	/**
	 * The country id is null.
	 */
	COUNTRY_ID_NULL(19),
	/**
	 * The country id is invalid.
	 */
	COUNTRY_ID_INVALID(20),
	/**
	 * The country is does not exist.
	 */
	COUNTRY_ID_DOES_NOT_EXISTS(21),
	/**
	 * The area id is null.
	 */
	AREA_ID_NULL(22),
	/**
	 * The area id is invalid.
	 */
	AREA_ID_INVALID(23),
	/**
	 * The area id does not exists.
	 */
	AREA_ID_DOES_NOT_EXISTS(24),
	/**
	 * The grid point does not belong to a valid grid point.
	 */
	GRID_POINT_INVALID(25),
	/**
	 * The grid point cannot be null.
	 */
	GRID_POINT_CANNOT_BE_NULL(26),
	/**
	 * The user session expired.
	 */
	USER_SESSION_EXPIRED(27), FORECASTER_ID_CANNOT_BE_NULL(28), FORECASTER_ID_DOES_NOT_EXISTS(
			29), FORECASTER_ID_INVALID(30);

	/**
	 * 
	 */
	private ErrorCode() {
		// GWT purpose
	}

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