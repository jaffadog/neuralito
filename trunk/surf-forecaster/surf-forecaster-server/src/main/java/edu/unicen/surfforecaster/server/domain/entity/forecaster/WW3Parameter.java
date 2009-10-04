/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster;

import edu.unicen.surfforecaster.common.services.dto.Unit;

/**
 * @author esteban
 * 
 */
public enum WW3Parameter {
	SWELL_DIRECTION("Direction_of_swell_waves", Unit.Degrees);
	// /**
	// *
	// */
	// WIND_WAVE_DIRECTION("Direction_of_wind_waves", Unit.Degrees),
	// /**
	// *
	// */
	// SWELL_WAVE_PERIOD("Mean_period_of_swell_waves", Unit.Seconds),
	// /**
	// *
	// */
	// WIND_WAVE_PERIOD("Mean_period_of_wind_waves",Unit.Seconds),
	// /**
	// *
	// */
	// PRIMARY_WAVE_DIRECTION("Primary_wave_direction",Unit.Degrees),
	// /**
	// *
	// */
	// PRIMARY_WAVE_PERIOD("Primary_wave_mean_period",Unit.Seconds),
	// /**
	// *
	// */
	// COMBINED_SWELL_WIND_WAVE_HEIGHT(
	// "Significant_height_of_combined_wind_waves_and_swell",Unit.Meters),
	// /**
	// *
	// */
	// SWELL_WAVE_HEIGHT("Significant_height_of_swell_waves",Unit.Meters),
	// /**
	// *
	// */
	// WIND_WAVE_HEIGHT("Significant_height_of_wind_waves",Unit.Meters),
	// /**
	// *
	// */
	// WIND_DIRECTION("Wind_direction_from_which_blowing", Unit.Degrees),
	// /**
	// *
	// */
	// WIND_SPEED("Wind_speed", Unit.Meterspersecond);

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
	private WW3Parameter(final String value) {
		this.value = value;
	}

	/**
	 * 
	 */
	private WW3Parameter(final String value, final Unit unit) {
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
