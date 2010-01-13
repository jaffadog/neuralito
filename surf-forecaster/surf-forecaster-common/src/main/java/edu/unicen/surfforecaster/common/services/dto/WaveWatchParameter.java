/**
 * 
 */
package edu.unicen.surfforecaster.common.services.dto;

import java.io.Serializable;

/**
 * @author esteban
 * 
 */
public enum WaveWatchParameter implements Serializable {

	/**
	 *
	 */
	WIND_WAVE_DIRECTION_V2("Direction_of_wind_waves", Unit.Degrees),
	/**
	 *
	 */
	WIND_WAVE_PERIOD_V2("Mean_period_of_wind_waves", Unit.Seconds),
	/**
	 *
	 */
	PRIMARY_WAVE_DIRECTION_V2("Primary_wave_direction", Unit.Degrees),
	/**
	 *
	 */
	PRIMARY_WAVE_PERIOD_V2("Primary_wave_mean_period", Unit.Seconds),
	/**
	 *
	 */
	SECONDARY_WAVE_DIRECTION_V2("Secondary_wave_direction", Unit.Degrees),
	/**
	 *
	 */
	SECONDARY_WAVE_PERIOD_V2("Secondary_wave_mean_period", Unit.Seconds),
	/**
	 *
	 */
	COMBINED_SWELL_WIND_WAVE_HEIGHT_V2("Sig_height_of_wind_waves_and_swell",
			Unit.Meters),
	/**
	 *
	 */
	WIND_DIRECTION_V2("Wind_direction", Unit.Degrees),
	/**
	 *
	 */
	WIND_SPEED_V2("Wind_speed", Unit.Meterspersecond),
	/**
	 * 
	 */
	WINDUComponent_V2("u_wind", Unit.Meterspersecond),
	/**
	 * 
	 */
	WINDVComponent_V2("v_wind", Unit.Meterspersecond),

	SWELL_DIRECTION_V3("Direction_of_swell_waves", Unit.Degrees),
	/**
	 *
	 */
	WIND_WAVE_DIRECTION_V3("Direction_of_wind_waves", Unit.Degrees),
	/**
	 *
	 */
	SWELL_WAVE_PERIOD_V3("Mean_period_of_swell_waves", Unit.Seconds),
	/**
	 *
	 */
	WIND_WAVE_PERIOD_V3("Mean_period_of_wind_waves", Unit.Seconds),
	/**
	 *
	 */
	PRIMARY_WAVE_DIRECTION_V3("Primary_wave_direction", Unit.Degrees),
	/**
	 *
	 */
	PRIMARY_WAVE_PERIOD_V3("Primary_wave_mean_period", Unit.Seconds),
	/**
	 *
	 */
	COMBINED_SWELL_WIND_WAVE_HEIGHT_V3(
			"Significant_height_of_combined_wind_waves_and_swell", Unit.Meters),
	/**
	 *
	 */
	SWELL_WAVE_HEIGHT_V3("Significant_height_of_swell_waves", Unit.Meters),
	/**
	 *
	 */
	WIND_WAVE_HEIGHT_V3("Significant_height_of_wind_waves", Unit.Meters),
	/**
	 *
	 */
	WIND_DIRECTION_V3("Wind_direction_from_which_blowing", Unit.Degrees),
	/**
	 *
	 */
	WIND_SPEED_V3("Wind_speed", Unit.Meterspersecond),
	/**
	 * 
	 */
	WINDUComponent_V3("U-component_of_wind", Unit.Meterspersecond),
	/**
	 * 
	 */
	WINDVComponent_V3("V-component_of_wind", Unit.Meterspersecond);

	private String value;
	private Unit unit;

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 
	 */
	WaveWatchParameter() {
		// GWT purpose.
	}

	/**
	 * 
	 */
	private WaveWatchParameter(final String value) {
		this.value = value;
	}

	/**
	 * 
	 */
	private WaveWatchParameter(final String value, final Unit unit) {
		this.value = value;
		this.unit = unit;
	}

	/**
	 * @return
	 */
	public Unit getUnit() {
		return unit;
	}
}
