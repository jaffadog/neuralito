/**
 * 
 */
package edu.unicen.surfforecaster.server;

import org.junit.Test;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder.GribDecoderNetCdf;

/**
 * @author esteban
 * 
 */
public class GribDecoderTest {
	@Test
	public void test() {
		GribDecoderNetCdf.printVariables();
		// final GribDecoderNetCdf decoder = new GribDecoderNetCdf();
		// decoder.getWaveHeightForecast(null);
	}
}
