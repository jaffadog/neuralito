package edu.unicen.surfforecaster.gwt.client.utils.images.waves.s50;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;

public final class Waves50PxFactory {
	
	private final static WavesImageBundle wavesImageBundle = (WavesImageBundle) GWT.create(WavesImageBundle.class);
	
	
	public static Image getWaveIcon(String waveHeight, Unit unit) {
		
		double height = 0;
		try {
			height = UnitConverter.convertValue(waveHeight, unit, Unit.Meters);
		} catch (NeuralitoException e) {
			// TODO hacer algo cuando salte esta excepcion
			e.printStackTrace();
		}
		
		if (height <= 0.74)
			return wavesImageBundle.wave_0_0d74().createImage();
		
		if (height >= 0.75 && height <= 1.49 )
			return wavesImageBundle.wave_0d75_1d49().createImage();
		
		if (height >= 1.5 && height <= 2.24 )
			return wavesImageBundle.wave_1d5_2d24().createImage();
		
		if (height >= 2.25 && height <= 2.99 )
			return wavesImageBundle.wave_2d25_2d99().createImage();
		
		if (height >= 3 && height <= 3.74 )
			return wavesImageBundle.wave_3_3d74().createImage();
		
		if (height >= 3.75 && height <= 4.49 )
			return wavesImageBundle.wave_3d75_4d49().createImage();
		
		if (height >= 4.5 && height <= 5.24 )
			return wavesImageBundle.wave_4d5_5d24().createImage();
		
		if (height >= 5.25 && height <= 5.99 )
			return wavesImageBundle.wave_5d25_5d99().createImage();
		
		if (height >= 6 && height <= 6.74 )
			return wavesImageBundle.wave_6_6d74().createImage();
		
		if (height >= 6.75 && height <= 7.49 )
			return wavesImageBundle.wave_6d75_7d49().createImage();
		
		if (height >= 7.5 && height <= 8.24 )
			return wavesImageBundle.wave_7d5_8d24().createImage();
		
		if (height >= 8.25 && height <= 8.99 )
			return wavesImageBundle.wave_8d25_8d99().createImage();
		
		if (height >= 9 && height <= 9.74 )
			return wavesImageBundle.wave_9_9d74().createImage();
		
		if (height >= 9.75 && height <= 10.49 )
			return wavesImageBundle.wave_9d75_10d49().createImage();
		
		if (height >= 10.5 && height <= 11.24 )
			return wavesImageBundle.wave_10d5_11d24().createImage();
		
		if (height >= 11.25 && height <= 12.99 )
			return wavesImageBundle.wave_11d25_12d99().createImage();
		
		if (height >= 13 && height <= 13.74 )
			return wavesImageBundle.wave_13_13d74().createImage();
		
		if (height >= 13.75 && height <= 14.49 )
			return wavesImageBundle.wave_13d75_14d49().createImage();
		
		if (height >= 14.5 && height <= 15.24 )
			return wavesImageBundle.wave_14d5_15d24().createImage();
		
		if (height >= 15.25 )
			return wavesImageBundle.wave_15d25_more().createImage();
		
		//by default
		return wavesImageBundle.wave_15d25_more().createImage();
	}
	
	

}//end class GWTUtils
