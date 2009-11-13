package edu.unicen.surfforecaster.gwt.client.utils;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.Unit;

public final class UnitTranslator {
	
	public static final String getUnitAbbrTranlation(Unit unit) {
		
			switch (unit) {
			case Meters:
				return GWTUtils.LOCALE_CONSTANTS.meters_abbr();
			
			case Feets:
				return GWTUtils.LOCALE_CONSTANTS.feets_abbr();
			
			case Seconds:
				return GWTUtils.LOCALE_CONSTANTS.seconds_abbr();
			
			case KilometersPerHour:
				return GWTUtils.LOCALE_CONSTANTS.kilometers_per_hour_abbr();
			
			case MilesPerHour:
				return GWTUtils.LOCALE_CONSTANTS.miles_per_hour_abbr();
			
			case Meterspersecond:
				return GWTUtils.LOCALE_CONSTANTS.meters_per_second_abbr();
			
			case Knots:
				return GWTUtils.LOCALE_CONSTANTS.knots();
			
			case Degrees:
				return GWTUtils.LOCALE_CONSTANTS.degrees_abbr();
			
			default:
				return "N/A";
			}
		}
}
