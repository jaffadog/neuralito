/**
 * 
 */
package edu.unicen.surfforecaster.common.services.dto;

import java.io.Serializable;

import edu.unicen.surfforecaster.common.services.ForecasterDTO;

/**
 * @author esteban
 * 
 */
public class SimpleForecasterDTO extends ForecasterDTO implements Serializable {
	/**
	 * Grid Point were wavewatch data is readed to perform forecast.
	 */
	private PointDTO gridPoint;

	/**
	 * 
	 */
	public SimpleForecasterDTO() {
		// GWT purpose
	}

	/**
	 * @param gridPoint
	 */
	public SimpleForecasterDTO(final Integer id, final String name,
			final String description, final PointDTO gridPoint) {
		super(id, name, description);
		this.gridPoint = gridPoint;
	}

	/**
	 * @return the gridPoint
	 */
	public PointDTO getGridPoint() {
		return gridPoint;
	}

}
