package edu.unicen.surfforecaster.server.domain.wavewatch;

import edu.unicen.surfforecaster.common.services.dto.Unit;

public enum WaveWatchParameterV2 {
	/**
	 *
	 */
	WIND_WAVE_DIRECTION("Direction_of_wind_waves", Unit.Degrees),
	/**
	 *
	 */
	WIND_WAVE_PERIOD("Mean_period_of_wind_waves", Unit.Seconds),
	/**
	 *
	 */
	PRIMARY_WAVE_DIRECTION("Primary_wave_direction", Unit.Degrees),
	/**
	 *
	 */
	PRIMARY_WAVE_PERIOD("Primary_wave_mean_period", Unit.Seconds),
	/**
	 *
	 */
	SECONDARY_WAVE_DIRECTION("Secondary_wave_direction", Unit.Degrees),
	/**
	 *
	 */
	SECONDARY_WAVE_PERIOD("Secondary_wave_mean_period", Unit.Seconds),
	/**
	 *
	 */
	COMBINED_SWELL_WIND_WAVE_HEIGHT("Sig_height_of_wind_waves_and_swell",
			Unit.Meters),
	/**
	 *
	 */
	WIND_DIRECTION("Wind_direction", Unit.Degrees),
	/**
	 *
	 */
	WIND_SPEED("Wind_speed", Unit.Meterspersecond),
	/**
	 * 
	 */
	WINDUComponent("u_wind", Unit.Meterspersecond),
	/**
	 * 
	 */
	WINDVComponent("v_wind", Unit.Meterspersecond);

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
	private WaveWatchParameterV2(final String value) {
		this.value = value;
	}

	/**
	 * 
	 */
	private WaveWatchParameterV2(final String value, final Unit unit) {
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
