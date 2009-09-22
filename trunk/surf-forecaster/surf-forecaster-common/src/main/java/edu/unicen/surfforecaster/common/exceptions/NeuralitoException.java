/**
 * 
 */
package edu.unicen.surfforecaster.common.exceptions;

import edu.unicen.surfforecaster.common.services.ErrorCode;

/**
 * @author esteban
 * 
 */
public class NeuralitoException extends Exception {
	/**
	 * The error code associated with this exception.
	 */
	private final ErrorCode errorCode;

	/**
	 * @param duplicatedUser
	 */
	public NeuralitoException(final ErrorCode duplicatedUser) {
		errorCode = duplicatedUser;
	}

	/**
	 * @return the errorCode
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}

}
