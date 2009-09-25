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
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The error code associated with this exception.
	 */
	private final ErrorCode errorCode;
	private String message;

	/**
	 * @param duplicatedUser
	 */
	public NeuralitoException(final ErrorCode duplicatedUser) {
		errorCode = duplicatedUser;
	}

	/**
	 * @param duplicatedUser
	 */
	public NeuralitoException(final ErrorCode duplicatedUser,
			final Exception exception) {
		errorCode = duplicatedUser;
		message = exception.getMessage();
	}

	/**
	 * @return the errorCode
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return message;
	}

}
