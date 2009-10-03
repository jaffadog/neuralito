/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster;

/**
 * @author esteban
 * 
 */
public enum WW3Attribute {
	WAVE_HEIGHT("The_wave_heigt");

	private String value;

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 
	 */
	private WW3Attribute(final String value) {
		this.value = value;
	}
}
