/**
 * 
 */
package edu.unicen.surfforecaster.common.services.dto;

import edu.unicen.surfforecaster.common.services.ErrorCode;

/**
 * @author esteban
 * 
 */
public class AbstractDTO {

	private int errorCode;

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @return the errorCode
	 */
	public int setErrorCode(final ErrorCode code) {
		return errorCode;
	}

}
