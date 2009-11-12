package edu.unicen.surfforecaster.gwt.client.utils.images.arrows.s50;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;

public final class Arrows30PxFactory {
	
	private final static ArrowsSmall50PxImageBundle arrowsSmallImageBundle = (ArrowsSmall50PxImageBundle) GWT.create(ArrowsSmall50PxImageBundle.class);
	private final static ArrowsMedium50PxImageBundle arrowsMediumImageBundle = (ArrowsMedium50PxImageBundle) GWT.create(ArrowsMedium50PxImageBundle.class);
	private final static ArrowsMediumBig50PxImageBundle arrowsMediumBigImageBundle = (ArrowsMediumBig50PxImageBundle) GWT.create(ArrowsMediumBig50PxImageBundle.class);
	private final static ArrowsBig50PxImageBundle arrowsBigImageBundle = (ArrowsBig50PxImageBundle) GWT.create(ArrowsBig50PxImageBundle.class);
	
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
				return arrowsSmallImageBundle.small_1_s().createImage();
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				return arrowsSmallImageBundle.small_1_ssw().createImage();
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				return arrowsSmallImageBundle.small_1_sw().createImage();
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				return arrowsSmallImageBundle.small_1_wsw().createImage();
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				return arrowsSmallImageBundle.small_1_w().createImage();
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				return arrowsSmallImageBundle.small_1_wnw().createImage();
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				return arrowsSmallImageBundle.small_1_nw().createImage();
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				return arrowsSmallImageBundle.small_1_nnw().createImage();
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				return arrowsSmallImageBundle.small_1_n().createImage();
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				return arrowsSmallImageBundle.small_1_nne().createImage();
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				return arrowsSmallImageBundle.small_1_ne().createImage();
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				return arrowsSmallImageBundle.small_1_ene().createImage();
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				return arrowsSmallImageBundle.small_1_e().createImage();
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				return arrowsSmallImageBundle.small_1_ese().createImage();
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				return arrowsSmallImageBundle.small_1_se().createImage();
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				return arrowsSmallImageBundle.small_1_sse().createImage();
		} else if (speedValue > 5 && speedValue <= 10) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				return arrowsSmallImageBundle.small_2_s().createImage();
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				return arrowsSmallImageBundle.small_2_ssw().createImage();
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				return arrowsSmallImageBundle.small_2_sw().createImage();
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				return arrowsSmallImageBundle.small_2_wsw().createImage();
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				return arrowsSmallImageBundle.small_2_w().createImage();
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				return arrowsSmallImageBundle.small_2_wnw().createImage();
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				return arrowsSmallImageBundle.small_2_nw().createImage();
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				return arrowsSmallImageBundle.small_2_nnw().createImage();
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				return arrowsSmallImageBundle.small_2_n().createImage();
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				return arrowsSmallImageBundle.small_2_nne().createImage();
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				return arrowsSmallImageBundle.small_2_ne().createImage();
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				return arrowsSmallImageBundle.small_2_ene().createImage();
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				return arrowsSmallImageBundle.small_2_e().createImage();
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				return arrowsSmallImageBundle.small_2_ese().createImage();
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				return arrowsSmallImageBundle.small_2_se().createImage();
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				return arrowsSmallImageBundle.small_2_sse().createImage();
		
		} else if (speedValue > 10 && speedValue <= 13) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				return arrowsMediumImageBundle.medium_3_s().createImage();
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				return arrowsMediumImageBundle.medium_3_ssw().createImage();
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				return arrowsMediumImageBundle.medium_3_sw().createImage();
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				return arrowsMediumImageBundle.medium_3_wsw().createImage();
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				return arrowsMediumImageBundle.medium_3_w().createImage();
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				return arrowsMediumImageBundle.medium_3_wnw().createImage();
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				return arrowsMediumImageBundle.medium_3_nw().createImage();
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				return arrowsMediumImageBundle.medium_3_nnw().createImage();
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				return arrowsMediumImageBundle.medium_3_n().createImage();
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				return arrowsMediumImageBundle.medium_3_nne().createImage();
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				return arrowsMediumImageBundle.medium_3_ne().createImage();
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				return arrowsMediumImageBundle.medium_3_ene().createImage();
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				return arrowsMediumImageBundle.medium_3_e().createImage();
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				return arrowsMediumImageBundle.medium_3_ese().createImage();
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				return arrowsMediumImageBundle.medium_3_se().createImage();
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				return arrowsMediumImageBundle.medium_3_sse().createImage();
		
		} else if (speedValue > 13 && speedValue <= 17) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				return arrowsMediumImageBundle.medium_4_s().createImage();
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				return arrowsMediumImageBundle.medium_4_ssw().createImage();
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				return arrowsMediumImageBundle.medium_4_sw().createImage();
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				return arrowsMediumImageBundle.medium_4_wsw().createImage();
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				return arrowsMediumImageBundle.medium_4_w().createImage();
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				return arrowsMediumImageBundle.medium_4_wnw().createImage();
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				return arrowsMediumImageBundle.medium_4_nw().createImage();
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				return arrowsMediumImageBundle.medium_4_nnw().createImage();
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				return arrowsMediumImageBundle.medium_4_n().createImage();
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				return arrowsMediumImageBundle.medium_4_nne().createImage();
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				return arrowsMediumImageBundle.medium_4_ne().createImage();
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				return arrowsMediumImageBundle.medium_4_ene().createImage();
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				return arrowsMediumImageBundle.medium_4_e().createImage();
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				return arrowsMediumImageBundle.medium_4_ese().createImage();
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				return arrowsMediumImageBundle.medium_4_se().createImage();
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				return arrowsMediumImageBundle.medium_4_sse().createImage();
		
		} else if (speedValue > 17 && speedValue <= 21) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				return arrowsMediumImageBundle.medium_5_s().createImage();
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				return arrowsMediumImageBundle.medium_5_ssw().createImage();
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				return arrowsMediumImageBundle.medium_5_sw().createImage();
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				return arrowsMediumImageBundle.medium_5_wsw().createImage();
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				return arrowsMediumImageBundle.medium_5_w().createImage();
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				return arrowsMediumImageBundle.medium_5_wnw().createImage();
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				return arrowsMediumImageBundle.medium_5_nw().createImage();
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				return arrowsMediumImageBundle.medium_5_nnw().createImage();
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				return arrowsMediumImageBundle.medium_5_n().createImage();
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				return arrowsMediumImageBundle.medium_5_nne().createImage();
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				return arrowsMediumImageBundle.medium_5_ne().createImage();
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				return arrowsMediumImageBundle.medium_5_ene().createImage();
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				return arrowsMediumImageBundle.medium_5_e().createImage();
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				return arrowsMediumImageBundle.medium_5_ese().createImage();
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				return arrowsMediumImageBundle.medium_5_se().createImage();
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				return arrowsMediumImageBundle.medium_5_sse().createImage();
		
		} else if (speedValue > 21 && speedValue <= 25) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				return arrowsMediumImageBundle.medium_6_s().createImage();
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				return arrowsMediumImageBundle.medium_6_ssw().createImage();
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				return arrowsMediumImageBundle.medium_6_sw().createImage();
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				return arrowsMediumImageBundle.medium_6_wsw().createImage();
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				return arrowsMediumImageBundle.medium_6_w().createImage();
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				return arrowsMediumImageBundle.medium_6_wnw().createImage();
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				return arrowsMediumImageBundle.medium_6_nw().createImage();
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				return arrowsMediumImageBundle.medium_6_nnw().createImage();
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				return arrowsMediumImageBundle.medium_6_n().createImage();
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				return arrowsMediumImageBundle.medium_6_nne().createImage();
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				return arrowsMediumImageBundle.medium_6_ne().createImage();
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				return arrowsMediumImageBundle.medium_6_ene().createImage();
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				return arrowsMediumImageBundle.medium_6_e().createImage();
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				return arrowsMediumImageBundle.medium_6_ese().createImage();
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				return arrowsMediumImageBundle.medium_6_se().createImage();
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				return arrowsMediumImageBundle.medium_6_sse().createImage();
		} else if (speedValue > 25 && speedValue <= 29) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				return arrowsMediumBigImageBundle.mediumbig_7_s().createImage();
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				return arrowsMediumBigImageBundle.mediumbig_7_ssw().createImage();
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				return arrowsMediumBigImageBundle.mediumbig_7_sw().createImage();
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				return arrowsMediumBigImageBundle.mediumbig_7_wsw().createImage();
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				return arrowsMediumBigImageBundle.mediumbig_7_w().createImage();
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				return arrowsMediumBigImageBundle.mediumbig_7_wnw().createImage();
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				return arrowsMediumBigImageBundle.mediumbig_7_nw().createImage();
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				return arrowsMediumBigImageBundle.mediumbig_7_nnw().createImage();
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				return arrowsMediumBigImageBundle.mediumbig_7_n().createImage();
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				return arrowsMediumBigImageBundle.mediumbig_7_nne().createImage();
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				return arrowsMediumBigImageBundle.mediumbig_7_ne().createImage();
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				return arrowsMediumBigImageBundle.mediumbig_7_ene().createImage();
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				return arrowsMediumBigImageBundle.mediumbig_7_e().createImage();
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				return arrowsMediumBigImageBundle.mediumbig_7_ese().createImage();
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				return arrowsMediumBigImageBundle.mediumbig_7_se().createImage();
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				return arrowsMediumBigImageBundle.mediumbig_7_sse().createImage();
		
		} else if (speedValue > 29 && speedValue <= /*34*/95) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				return arrowsMediumBigImageBundle.mediumbig_8_s().createImage();
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				return arrowsMediumBigImageBundle.mediumbig_8_ssw().createImage();
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				return arrowsMediumBigImageBundle.mediumbig_8_sw().createImage();
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				return arrowsMediumBigImageBundle.mediumbig_8_wsw().createImage();
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				return arrowsMediumBigImageBundle.mediumbig_8_w().createImage();
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				return arrowsMediumBigImageBundle.mediumbig_8_wnw().createImage();
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				return arrowsMediumBigImageBundle.mediumbig_8_nw().createImage();
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				return arrowsMediumBigImageBundle.mediumbig_8_nnw().createImage();
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				return arrowsMediumBigImageBundle.mediumbig_8_n().createImage();
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				return arrowsMediumBigImageBundle.mediumbig_8_nne().createImage();
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				return arrowsMediumBigImageBundle.mediumbig_8_ne().createImage();
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				return arrowsMediumBigImageBundle.mediumbig_8_ene().createImage();
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				return arrowsMediumBigImageBundle.mediumbig_8_e().createImage();
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				return arrowsMediumBigImageBundle.mediumbig_8_ese().createImage();
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				return arrowsMediumBigImageBundle.mediumbig_8_se().createImage();
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				return arrowsMediumBigImageBundle.mediumbig_8_sse().createImage();
		
		} else if (speedValue > 95) {
			
			if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
				return arrowsBigImageBundle.big_20_s().createImage();
			
			if (directionValue > 11.25 && directionValue <= 33.75)
				return arrowsBigImageBundle.big_20_ssw().createImage();
			
			if (directionValue > 33.75 && directionValue <= 56.25)
				return arrowsBigImageBundle.big_20_sw().createImage();
			
			if (directionValue > 56.25 && directionValue <= 78.75)
				return arrowsBigImageBundle.big_20_wsw().createImage();
			
			if (directionValue > 78.75 && directionValue <= 101.25)
				return arrowsBigImageBundle.big_20_w().createImage();
			
			if (directionValue > 101.25 && directionValue <= 123.75)
				return arrowsBigImageBundle.big_20_wnw().createImage();
			
			if (directionValue > 123.75 && directionValue <= 146.25)
				return arrowsBigImageBundle.big_20_nw().createImage();
			
			if (directionValue > 146.25 && directionValue <= 168.75)
				return arrowsBigImageBundle.big_20_nnw().createImage();
			
			if (directionValue > 168.75 && directionValue <= 191.25)
				return arrowsBigImageBundle.big_20_n().createImage();
			
			if (directionValue > 191.25 && directionValue <= 213.75)
				return arrowsBigImageBundle.big_20_nne().createImage();
			
			if (directionValue > 213.75 && directionValue <= 236.25)
				return arrowsBigImageBundle.big_20_ne().createImage();
			
			if (directionValue > 236.25 && directionValue <= 258.75)
				return arrowsBigImageBundle.big_20_ene().createImage();
			
			if (directionValue > 258.75 && directionValue <= 281.25)
				return arrowsBigImageBundle.big_20_e().createImage();
			
			if (directionValue > 281.25 && directionValue <= 303.75)
				return arrowsBigImageBundle.big_20_ese().createImage();
			
			if (directionValue > 303.75 && directionValue <= 326.25)
				return arrowsBigImageBundle.big_20_se().createImage();
			
			if (directionValue > 326.25 && directionValue <= 348.75)
				return arrowsBigImageBundle.big_20_sse().createImage();
		}
		
		//by default
		return arrowsSmallImageBundle.small_1_s().createImage();
	}
	
	/**
	 * Method used to obtain an arrow icon regarding direction of the variable (waves)
	 * @param direction
	 * @param unitDirection
	 * @return Image
	 */
	public static Image getArrowIcon(String direction, Unit unitDirection) {
		
		double directionValue = 0;
		double speedValue = 0;
		try {
			directionValue = UnitConverter.convertValue(direction, unitDirection, Unit.Degrees);
		} catch (NeuralitoException e) {
			// TODO hacer algo cuando salte esta excepcion
			e.printStackTrace();
		}
		
		if ((directionValue >= 0 && directionValue <= 11.25) || (directionValue > 348.75 && directionValue <= 360))
			return arrowsMediumImageBundle.medium_3_s().createImage();
		
		if (directionValue > 11.25 && directionValue <= 33.75)
			return arrowsMediumImageBundle.medium_3_ssw().createImage();
		
		if (directionValue > 33.75 && directionValue <= 56.25)
			return arrowsMediumImageBundle.medium_3_sw().createImage();
		
		if (directionValue > 56.25 && directionValue <= 78.75)
			return arrowsMediumImageBundle.medium_3_wsw().createImage();
		
		if (directionValue > 78.75 && directionValue <= 101.25)
			return arrowsMediumImageBundle.medium_3_w().createImage();
		
		if (directionValue > 101.25 && directionValue <= 123.75)
			return arrowsMediumImageBundle.medium_3_wnw().createImage();
		
		if (directionValue > 123.75 && directionValue <= 146.25)
			return arrowsMediumImageBundle.medium_3_nw().createImage();
		
		if (directionValue > 146.25 && directionValue <= 168.75)
			return arrowsMediumImageBundle.medium_3_nnw().createImage();
		
		if (directionValue > 168.75 && directionValue <= 191.25)
			return arrowsMediumImageBundle.medium_3_n().createImage();
		
		if (directionValue > 191.25 && directionValue <= 213.75)
			return arrowsMediumImageBundle.medium_3_nne().createImage();
		
		if (directionValue > 213.75 && directionValue <= 236.25)
			return arrowsMediumImageBundle.medium_3_ne().createImage();
		
		if (directionValue > 236.25 && directionValue <= 258.75)
			return arrowsMediumImageBundle.medium_3_ene().createImage();
		
		if (directionValue > 258.75 && directionValue <= 281.25)
			return arrowsMediumImageBundle.medium_3_e().createImage();
		
		if (directionValue > 281.25 && directionValue <= 303.75)
			return arrowsMediumImageBundle.medium_3_ese().createImage();
		
		if (directionValue > 303.75 && directionValue <= 326.25)
			return arrowsMediumImageBundle.medium_3_se().createImage();
		
		if (directionValue > 326.25 && directionValue <= 348.75)
			return arrowsMediumImageBundle.medium_3_sse().createImage();
		
		return arrowsMediumImageBundle.medium_3_n().createImage();
	}

}
