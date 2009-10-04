/**
 * 
 */
package edu.unicen.surfforecaster.server;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder.GribDecoderNetcdf;

/**
 * @author esteban
 * 
 */
public class NetcdfGribDecoderTest {
	/**
	 * Read a grib2 file and obtain the forecasts for a single point.
	 */
	@Test
	public void decodeGribFile() {
		final Collection<Point> p = new ArrayList<Point>();
		final Point point = new Point(38.1, 179.2);
		p.add(point);
		final Long initialTime = new Date().getTime();
		final GribDecoderNetcdf dec = new GribDecoderNetcdf();
		final Collection<Forecast> forecasts = dec.getForecasts(new File(
				"src/test/resources/multi_1.glo_30m.all.grb2"), p);
		final Long finalTime = new Date().getTime();
		System.out.println("It tooked: " + (finalTime - initialTime) / 1000);

		Assert.assertEquals(31, forecasts.size());

		// for (final Iterator iterator = forecasts.iterator();
		// iterator.hasNext();) {
		// final Forecast forecast = (Forecast) iterator.next();
		// System.out.println(forecast.getBaseDate());
		// }
	}
}
