package edu.unicen.surfforecaster.gwt.client.utils.images.arrows.s30;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;

public final class Arrows30PxFactory {
	private final static ArrowsSmall30PxImageBundle arrowsSmallImageBundle = (ArrowsSmall30PxImageBundle) GWT.create(ArrowsSmall30PxImageBundle.class);
	private final static ArrowsMedium30PxImageBundle arrowsMediumImageBundle = (ArrowsMedium30PxImageBundle) GWT.create(ArrowsMedium30PxImageBundle.class);
	private final static ArrowsMediumBig30PxImageBundle arrowsMediumBigImageBundle = (ArrowsMediumBig30PxImageBundle) GWT.create(ArrowsMediumBig30PxImageBundle.class);
	private final static ArrowsBig30PxImageBundle arrowsBigImageBundle = (ArrowsBig30PxImageBundle) GWT.create(ArrowsBig30PxImageBundle.class);
	
	/**
	 * Obtain an arrow icon depending on the direction and speed as parameters(util for winds)
	 * @param direction
	 * @param speed
	 * @param unitDirection
	 * @param unitSpeed
	 * @return
	 */
	public static Image getArrowIcon(String direction, String speed, Unit unitDirection, Unit unitSpeed) {
		
		double directionValue = 0;
		double speedValue = 0;
		Image result = null;
		
		try {
			directionValue = UnitConverter.convertValue(direction, unitDirection, Unit.Degrees);
		} catch (NeuralitoException e) {
			// TODO hacer algo cuando salte esta excepcion
			e.printStackTrace();
		}
		
		try {
			speedValue = UnitConverter.convertValue(speed, unitSpeed, Unit.KilometersPerHour);
		} catch (NeuralitoException e) {
			// TODO hacer algo cuando salte esta excepcion
			e.printStackTrace();
		}
		
		if (speedValue <= 5) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				{result = arrowsSmallImageBundle.small_1_n().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north()); return result;}
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				{result = arrowsSmallImageBundle.small_1_nne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northeast()); return result;}
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				{result = arrowsSmallImageBundle.small_1_ne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northeast()); return result;}
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				{result = arrowsSmallImageBundle.small_1_ene().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_northeast()); return result;}
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				{result = arrowsSmallImageBundle.small_1_e().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east()); return result;}
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				{result = arrowsSmallImageBundle.small_1_ese().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_southeast()); return result;}
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				{result = arrowsSmallImageBundle.small_1_sw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southeast()); return result;}
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				{result = arrowsSmallImageBundle.small_1_sse().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southeast()); return result;}
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				{result = arrowsSmallImageBundle.small_1_s().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south()); return result;}
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				{result = arrowsSmallImageBundle.small_1_ssw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southwest()); return result;}
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				{result = arrowsSmallImageBundle.small_1_sw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southwest()); return result;}
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				{result = arrowsSmallImageBundle.small_1_wsw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_southwest()); return result;}
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				{result = arrowsSmallImageBundle.small_1_w().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west()); return result;}
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				{result = arrowsSmallImageBundle.small_1_wnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_northwest()); return result;}
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				{result = arrowsSmallImageBundle.small_1_nw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northwest()); return result;}
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				{result = arrowsSmallImageBundle.small_1_nnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northwest()); return result;}
		}
		
		if (speedValue > 5 && speedValue <= 10) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				{result = arrowsSmallImageBundle.small_2_n().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north()); return result;}
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				{result = arrowsSmallImageBundle.small_2_nne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northeast()); return result;}
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				{result = arrowsSmallImageBundle.small_2_ne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northeast()); return result;}
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				{result = arrowsSmallImageBundle.small_2_ene().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_northeast()); return result;}
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				{result = arrowsSmallImageBundle.small_2_e().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east()); return result;}
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				{result = arrowsSmallImageBundle.small_2_ese().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_southeast()); return result;}
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				{result = arrowsSmallImageBundle.small_2_se().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southeast()); return result;}
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				{result = arrowsSmallImageBundle.small_2_sse().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southeast()); return result;}
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				{result = arrowsSmallImageBundle.small_2_s().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south()); return result;}
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				{result = arrowsSmallImageBundle.small_2_ssw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southwest()); return result;}
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				{result = arrowsSmallImageBundle.small_2_sw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southwest()); return result;}
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				{result = arrowsSmallImageBundle.small_2_wsw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_southwest()); return result;}
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				{result = arrowsSmallImageBundle.small_2_w().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west()); return result;}
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				{result = arrowsSmallImageBundle.small_2_wnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_northwest()); return result;}
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				{result = arrowsSmallImageBundle.small_2_nw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northwest()); return result;}
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				{result = arrowsSmallImageBundle.small_2_nnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northwest()); return result;}
		}
			
		if (speedValue > 10 && speedValue <= 13) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				{result = arrowsMediumImageBundle.medium_3_n().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north()); return result;}
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				{result = arrowsMediumImageBundle.medium_3_nne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northeast()); return result;}
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				{result = arrowsMediumImageBundle.medium_3_ne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northeast()); return result;}
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				{result = arrowsMediumImageBundle.medium_3_ene().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_northeast()); return result;}
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				{result = arrowsMediumImageBundle.medium_3_e().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east()); return result;}
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				{result = arrowsMediumImageBundle.medium_3_ese().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_southeast()); return result;}
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				{result = arrowsMediumImageBundle.medium_3_se().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southeast()); return result;}
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				{result = arrowsMediumImageBundle.medium_3_sse().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southeast()); return result;}
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				{result = arrowsMediumImageBundle.medium_3_s().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south()); return result;}
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				{result = arrowsMediumImageBundle.medium_3_ssw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southwest()); return result;}
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				{result = arrowsMediumImageBundle.medium_3_sw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southwest()); return result;}
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				{result = arrowsMediumImageBundle.medium_3_wsw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_southwest()); return result;}
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				{result = arrowsMediumImageBundle.medium_3_w().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west()); return result;}
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				{result = arrowsMediumImageBundle.medium_3_wnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_northwest()); return result;}
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				{result = arrowsMediumImageBundle.medium_3_nw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northwest()); return result;}
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				{result = arrowsMediumImageBundle.medium_3_nnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northwest()); return result;}
		}
		
		if (speedValue > 13 && speedValue <= 17) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				{result = arrowsMediumImageBundle.medium_4_n().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north()); return result;}
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				{result = arrowsMediumImageBundle.medium_4_nne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northeast()); return result;}
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				{result = arrowsMediumImageBundle.medium_4_ne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northeast()); return result;}
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				{result = arrowsMediumImageBundle.medium_4_ene().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_northeast()); return result;}
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				{result = arrowsMediumImageBundle.medium_4_e().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east()); return result;}
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				{result = arrowsMediumImageBundle.medium_4_ese().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_southeast()); return result;}
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				{result = arrowsMediumImageBundle.medium_4_se().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southeast()); return result;}
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				{result = arrowsMediumImageBundle.medium_4_sse().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southeast()); return result;}
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				{result = arrowsMediumImageBundle.medium_4_s().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south()); return result;}
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				{result = arrowsMediumImageBundle.medium_4_ssw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southwest()); return result;}
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				{result = arrowsMediumImageBundle.medium_4_sw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southwest()); return result;}
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				{result = arrowsMediumImageBundle.medium_4_wsw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_southwest()); return result;}
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				{result = arrowsMediumImageBundle.medium_4_w().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west()); return result;}
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				{result = arrowsMediumImageBundle.medium_4_wnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_northwest()); return result;}
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				{result = arrowsMediumImageBundle.medium_4_nw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northwest()); return result;}
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				{result = arrowsMediumImageBundle.medium_4_nnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northwest()); return result;}
		}
		
		if (speedValue > 17 && speedValue <= 21) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				{result = arrowsMediumImageBundle.medium_5_n().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north()); return result;}
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				{result = arrowsMediumImageBundle.medium_5_nne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northeast()); return result;}
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				{result = arrowsMediumImageBundle.medium_5_ne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northeast()); return result;}
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				{result = arrowsMediumImageBundle.medium_5_ene().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_northeast()); return result;}
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				{result = arrowsMediumImageBundle.medium_5_e().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east()); return result;}
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				{result = arrowsMediumImageBundle.medium_5_ese().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_southeast()); return result;}
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				{result = arrowsMediumImageBundle.medium_5_se().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southeast()); return result;}
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				{result = arrowsMediumImageBundle.medium_5_sse().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southeast()); return result;}
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				{result = arrowsMediumImageBundle.medium_5_s().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south()); return result;}
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				{result = arrowsMediumImageBundle.medium_5_ssw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southwest()); return result;}
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				{result = arrowsMediumImageBundle.medium_5_sw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southwest()); return result;}
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				{result = arrowsMediumImageBundle.medium_5_wsw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_southwest()); return result;}
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				{result = arrowsMediumImageBundle.medium_5_w().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west()); return result;}
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				{result = arrowsMediumImageBundle.medium_5_wnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_northwest()); return result;}
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				{result = arrowsMediumImageBundle.medium_5_nw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northwest()); return result;}
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				{result = arrowsMediumImageBundle.medium_5_nnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northwest()); return result;}
		}
		
		if (speedValue > 21 && speedValue <= 25) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				{result = arrowsMediumImageBundle.medium_6_n().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north()); return result;}
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				{result = arrowsMediumImageBundle.medium_6_nne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northeast()); return result;}
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				{result = arrowsMediumImageBundle.medium_6_ne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northeast()); return result;}
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				{result = arrowsMediumImageBundle.medium_6_ene().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_northeast()); return result;}
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				{result = arrowsMediumImageBundle.medium_6_e().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east()); return result;}
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				{result = arrowsMediumImageBundle.medium_6_ese().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_southeast()); return result;}
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				{result = arrowsMediumImageBundle.medium_6_se().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southeast()); return result;}
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				{result = arrowsMediumImageBundle.medium_6_sse().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southeast()); return result;}
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				{result = arrowsMediumImageBundle.medium_6_s().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south()); return result;}
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				{result = arrowsMediumImageBundle.medium_6_ssw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southwest()); return result;}
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				{result = arrowsMediumImageBundle.medium_6_sw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southwest()); return result;}
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				{result = arrowsMediumImageBundle.medium_6_wsw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_southwest()); return result;}
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				{result = arrowsMediumImageBundle.medium_6_w().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west()); return result;}
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				{result = arrowsMediumImageBundle.medium_6_wnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_northwest()); return result;}
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				{result = arrowsMediumImageBundle.medium_6_nw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northwest()); return result;}
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				{result = arrowsMediumImageBundle.medium_6_nnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northwest()); return result;}
		}
		
		if (speedValue > 25 && speedValue <= 29) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				{result = arrowsMediumBigImageBundle.mediumbig_7_n().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north()); return result;}
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				{result = arrowsMediumBigImageBundle.mediumbig_7_nne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northeast()); return result;}
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				{result = arrowsMediumBigImageBundle.mediumbig_7_ne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northeast()); return result;}
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				{result = arrowsMediumBigImageBundle.mediumbig_7_ene().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_northeast()); return result;}
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				{result = arrowsMediumBigImageBundle.mediumbig_7_e().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east()); return result;}
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				{result = arrowsMediumBigImageBundle.mediumbig_7_ese().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_southeast()); return result;}
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				{result = arrowsMediumBigImageBundle.mediumbig_7_se().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southeast()); return result;}
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				{result = arrowsMediumBigImageBundle.mediumbig_7_sse().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southeast()); return result;}
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				{result = arrowsMediumBigImageBundle.mediumbig_7_s().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south()); return result;}
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				{result = arrowsMediumBigImageBundle.mediumbig_7_ssw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southwest()); return result;}
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				{result = arrowsMediumBigImageBundle.mediumbig_7_sw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southwest()); return result;}
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				{result = arrowsMediumBigImageBundle.mediumbig_7_wsw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_southwest()); return result;}
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				{result = arrowsMediumBigImageBundle.mediumbig_7_w().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west()); return result;}
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				{result = arrowsMediumBigImageBundle.mediumbig_7_wnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_northwest()); return result;}
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				{result = arrowsMediumBigImageBundle.mediumbig_7_nw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northwest()); return result;}
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				{result = arrowsMediumBigImageBundle.mediumbig_7_nnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northwest()); return result;}
		}
		
		if (speedValue > 29 && speedValue <= 34) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				{result = arrowsMediumBigImageBundle.mediumbig_8_n().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north()); return result;}
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				{result = arrowsMediumBigImageBundle.mediumbig_8_nne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northeast()); return result;}
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				{result = arrowsMediumBigImageBundle.mediumbig_8_ne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northeast()); return result;}
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				{result = arrowsMediumBigImageBundle.mediumbig_8_ene().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_northeast()); return result;}
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				{result = arrowsMediumBigImageBundle.mediumbig_8_e().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east()); return result;}
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				{result = arrowsMediumBigImageBundle.mediumbig_8_ese().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_southeast()); return result;}
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				{result = arrowsMediumBigImageBundle.mediumbig_8_se().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southeast()); return result;}
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				{result = arrowsMediumBigImageBundle.mediumbig_8_sse().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southeast()); return result;}
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				{result = arrowsMediumBigImageBundle.mediumbig_8_s().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south()); return result;}
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				{result = arrowsMediumBigImageBundle.mediumbig_8_ssw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southwest()); return result;}
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				{result = arrowsMediumBigImageBundle.mediumbig_8_sw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southwest()); return result;}
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				{result = arrowsMediumBigImageBundle.mediumbig_8_wsw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_southwest()); return result;}
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				{result = arrowsMediumBigImageBundle.mediumbig_8_w().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west()); return result;}
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				{result = arrowsMediumBigImageBundle.mediumbig_8_wnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_northwest()); return result;}
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				{result = arrowsMediumBigImageBundle.mediumbig_8_nw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northwest()); return result;}
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				{result = arrowsMediumBigImageBundle.mediumbig_8_nnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northwest()); return result;}
		}
		
		if (speedValue > 34 && speedValue <= 39) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				{result = arrowsMediumBigImageBundle.mediumbig_9_n().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north()); return result;}
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				{result = arrowsMediumBigImageBundle.mediumbig_9_nne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northeast()); return result;}
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				{result = arrowsMediumBigImageBundle.mediumbig_9_ne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northeast()); return result;}
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				{result = arrowsMediumBigImageBundle.mediumbig_9_ene().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_northeast()); return result;}
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				{result = arrowsMediumBigImageBundle.mediumbig_9_e().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east()); return result;}
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				{result = arrowsMediumBigImageBundle.mediumbig_9_ese().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_southeast()); return result;}
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				{result = arrowsMediumBigImageBundle.mediumbig_9_se().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southeast()); return result;}
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				{result = arrowsMediumBigImageBundle.mediumbig_9_sse().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southeast()); return result;}
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				{result = arrowsMediumBigImageBundle.mediumbig_9_s().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south()); return result;}
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				{result = arrowsMediumBigImageBundle.mediumbig_9_ssw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southwest()); return result;}
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				{result = arrowsMediumBigImageBundle.mediumbig_9_sw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southwest()); return result;}
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				{result = arrowsMediumBigImageBundle.mediumbig_9_wsw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_southwest()); return result;}
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				{result = arrowsMediumBigImageBundle.mediumbig_9_w().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west()); return result;}
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				{result = arrowsMediumBigImageBundle.mediumbig_9_wnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_northwest()); return result;}
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				{result = arrowsMediumBigImageBundle.mediumbig_9_nw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northwest()); return result;}
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				{result = arrowsMediumBigImageBundle.mediumbig_9_nnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northwest()); return result;}
		}

		if (speedValue > 39 && speedValue <= 44) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				{result = arrowsMediumBigImageBundle.mediumbig_10_n().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north()); return result;}
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				{result = arrowsMediumBigImageBundle.mediumbig_10_nne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northeast()); return result;}
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				{result = arrowsMediumBigImageBundle.mediumbig_10_ne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northeast()); return result;}
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				{result = arrowsMediumBigImageBundle.mediumbig_10_ene().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_northeast()); return result;}
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				{result = arrowsMediumBigImageBundle.mediumbig_10_e().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east()); return result;}
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				{result = arrowsMediumBigImageBundle.mediumbig_10_ese().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_southeast()); return result;}
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				{result = arrowsMediumBigImageBundle.mediumbig_10_se().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southeast()); return result;}
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				{result = arrowsMediumBigImageBundle.mediumbig_10_sse().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southeast()); return result;}
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				{result = arrowsMediumBigImageBundle.mediumbig_10_s().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south()); return result;}
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				{result = arrowsMediumBigImageBundle.mediumbig_10_ssw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southwest()); return result;}
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				{result = arrowsMediumBigImageBundle.mediumbig_10_sw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southwest()); return result;}
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				{result = arrowsMediumBigImageBundle.mediumbig_10_wsw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_southwest()); return result;}
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				{result = arrowsMediumBigImageBundle.mediumbig_10_w().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west()); return result;}
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				{result = arrowsMediumBigImageBundle.mediumbig_10_wnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_northwest()); return result;}
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				{result = arrowsMediumBigImageBundle.mediumbig_10_nw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northwest()); return result;}
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				{result = arrowsMediumBigImageBundle.mediumbig_10_nnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northwest()); return result;}
		}
				
		if (speedValue > 44 && speedValue <= 49) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				{result = arrowsMediumBigImageBundle.mediumbig_11_n().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north()); return result;}
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				{result = arrowsMediumBigImageBundle.mediumbig_11_nne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northeast()); return result;}
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				{result = arrowsMediumBigImageBundle.mediumbig_11_ne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northeast()); return result;}
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				{result = arrowsMediumBigImageBundle.mediumbig_11_ene().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_northeast()); return result;}
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				{result = arrowsMediumBigImageBundle.mediumbig_11_e().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east()); return result;}
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				{result = arrowsMediumBigImageBundle.mediumbig_11_ese().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_southeast()); return result;}
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				{result = arrowsMediumBigImageBundle.mediumbig_11_se().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southeast()); return result;}
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				{result = arrowsMediumBigImageBundle.mediumbig_11_sse().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southeast()); return result;}
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				{result = arrowsMediumBigImageBundle.mediumbig_11_s().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south()); return result;}
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				{result = arrowsMediumBigImageBundle.mediumbig_11_ssw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southwest()); return result;}
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				{result = arrowsMediumBigImageBundle.mediumbig_11_sw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southwest()); return result;}
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				{result = arrowsMediumBigImageBundle.mediumbig_11_wsw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_southwest()); return result;}
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				{result = arrowsMediumBigImageBundle.mediumbig_11_w().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west()); return result;}
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				{result = arrowsMediumBigImageBundle.mediumbig_11_wnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_northwest()); return result;}
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				{result = arrowsMediumBigImageBundle.mediumbig_11_nw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northwest()); return result;}
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				{result = arrowsMediumBigImageBundle.mediumbig_11_nnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northwest()); return result;}
		}
		
		if (speedValue > 49 && speedValue <= 54) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				{result = arrowsMediumBigImageBundle.mediumbig_12_n().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north()); return result;}
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				{result = arrowsMediumBigImageBundle.mediumbig_12_nne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northeast()); return result;}
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				{result = arrowsMediumBigImageBundle.mediumbig_12_ne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northeast()); return result;}
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				{result = arrowsMediumBigImageBundle.mediumbig_12_ene().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_northeast()); return result;}
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				{result = arrowsMediumBigImageBundle.mediumbig_12_e().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east()); return result;}
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				{result = arrowsMediumBigImageBundle.mediumbig_12_ese().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_southeast()); return result;}
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				{result = arrowsMediumBigImageBundle.mediumbig_12_se().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southeast()); return result;}
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				{result = arrowsMediumBigImageBundle.mediumbig_12_sse().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southeast()); return result;}
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				{result = arrowsMediumBigImageBundle.mediumbig_12_s().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south()); return result;}
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				{result = arrowsMediumBigImageBundle.mediumbig_12_ssw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southwest()); return result;}
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				{result = arrowsMediumBigImageBundle.mediumbig_12_sw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southwest()); return result;}
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				{result = arrowsMediumBigImageBundle.mediumbig_12_wsw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_southwest()); return result;}
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				{result = arrowsMediumBigImageBundle.mediumbig_12_w().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west()); return result;}
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				{result = arrowsMediumBigImageBundle.mediumbig_12_wnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_northwest()); return result;}
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				{result = arrowsMediumBigImageBundle.mediumbig_12_nw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northwest()); return result;}
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				{result = arrowsMediumBigImageBundle.mediumbig_12_nnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northwest()); return result;}
		}

		if (speedValue > 54) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				{result = arrowsBigImageBundle.big_20_s().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south()); return result;}
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				{result = arrowsBigImageBundle.big_20_ssw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southwest()); return result;}
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				{result = arrowsBigImageBundle.big_20_sw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southwest()); return result;}
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				{result = arrowsBigImageBundle.big_20_wsw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_southwest()); return result;}
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				{result = arrowsBigImageBundle.big_20_w().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west()); return result;}
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				{result = arrowsBigImageBundle.big_20_wnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_northwest()); return result;}
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				{result = arrowsBigImageBundle.big_20_nw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northwest()); return result;}
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				{result = arrowsBigImageBundle.big_20_nnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northwest()); return result;}
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				{result = arrowsBigImageBundle.big_20_n().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north()); return result;}
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				{result = arrowsBigImageBundle.big_20_nne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northeast()); return result;}
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				{result = arrowsBigImageBundle.big_20_ne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northeast()); return result;}
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				{result = arrowsBigImageBundle.big_20_ene().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_northeast()); return result;}
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				{result = arrowsBigImageBundle.big_20_e().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east()); return result;}
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				{result = arrowsBigImageBundle.big_20_ese().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_southeast()); return result;}
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				{result = arrowsBigImageBundle.big_20_se().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southeast()); return result;}
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				{result = arrowsBigImageBundle.big_20_sse().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southeast()); return result;}
		}
		
		//by default
		result = arrowsSmallImageBundle.small_2_s().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south()); return result;
		
	}
	
	/**
	 * Method used to obtain an arrow icon regarding direction of the variable (waves)
	 * @param direction
	 * @param unitDirection
	 * @return Image
	 */
	public static Image getArrowIcon(String direction, Unit unitDirection) {
		
		double directionValue = 0;
		Image result = null;
		try {
			directionValue = UnitConverter.convertValue(direction, unitDirection, Unit.Degrees);
		} catch (NeuralitoException e) {
			// TODO hacer algo cuando salte esta excepcion
			e.printStackTrace();
		}
		
		if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
			{result = arrowsSmallImageBundle.small_2_n().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north()); return result;}
		
		if (directionValue > 11.25 && directionValue <= 33.75)
			{result = arrowsSmallImageBundle.small_2_nne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northeast()); return result;}
		
		if (directionValue > 33.75 && directionValue <= 56.25)
			{result = arrowsSmallImageBundle.small_2_ne().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northeast()); return result;}
		
		if (directionValue > 56.25 && directionValue <= 78.75)
			{result = arrowsSmallImageBundle.small_2_ene().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_northeast()); return result;}
		
		if (directionValue > 78.75 && directionValue <= 101.25)
			{result = arrowsSmallImageBundle.small_2_e().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east()); return result;}
		
		if (directionValue > 101.25 && directionValue <= 123.75)
			{result = arrowsSmallImageBundle.small_2_ese().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.east_southeast()); return result;}
		
		if (directionValue > 123.75 && directionValue <= 146.25)
			{result = arrowsSmallImageBundle.small_2_se().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southeast()); return result;}
		
		if (directionValue > 146.25 && directionValue <= 168.75)
			{result = arrowsSmallImageBundle.small_2_sse().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southeast()); return result;}
		
		if (directionValue > 168.75 && directionValue <= 191.25)
			{result = arrowsSmallImageBundle.small_2_s().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south()); return result;}
		
		if (directionValue > 191.25 && directionValue <= 213.75)
			{result = arrowsSmallImageBundle.small_2_ssw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south_southwest()); return result;}
		
		if (directionValue > 213.75 && directionValue <= 236.25)
			{result = arrowsSmallImageBundle.small_2_sw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.southwest()); return result;}
		
		if (directionValue > 236.25 && directionValue <= 258.75)
			{result = arrowsSmallImageBundle.small_2_wsw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_southwest()); return result;}
		
		if (directionValue > 258.75 && directionValue <= 281.25)
			{result = arrowsSmallImageBundle.small_2_w().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west()); return result;}
		
		if (directionValue > 281.25 && directionValue <= 303.75)
			{result = arrowsSmallImageBundle.small_2_wnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.west_northwest()); return result;}
		
		if (directionValue > 303.75 && directionValue <= 326.25)
			{result = arrowsSmallImageBundle.small_2_nw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.northwest()); return result;}
		
		if (directionValue > 326.25 && directionValue <= 348.75)
			{result = arrowsSmallImageBundle.small_2_nnw().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.north_northwest()); return result;}
			
		result = arrowsSmallImageBundle.small_2_s().createImage(); result.setTitle(GWTUtils.LOCALE_CONSTANTS.south()); return result;
	
	}

}
