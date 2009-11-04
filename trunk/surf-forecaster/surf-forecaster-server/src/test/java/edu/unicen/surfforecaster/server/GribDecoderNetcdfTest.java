/**
 * 
 */
package edu.unicen.surfforecaster.server;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import edu.unicen.surfforecaster.server.domain.decoder.GribDecoderNetcdf;
import edu.unicen.surfforecaster.server.domain.entity.Point;

/**
 * @author esteban
 * 
 */

public class GribDecoderNetcdfTest {
	Logger log = Logger.getLogger(this.getClass());
	/**
	 * Test obtaining all points which contains values differents of NaN.
	 */
	@Test
	@Ignore
	public void getValidPoints() {
		final File file = new File(
				"src/test/resources/multi_1.glo_30m.HTSGW.grb2");

		final GribDecoderNetcdf dec = new GribDecoderNetcdf();
		final Collection<Point> validPoints = dec.getValidPoints(file);
		log.info("Points founded:" + validPoints.size());
		for (final Iterator iterator = validPoints.iterator(); iterator
				.hasNext();) {
			final Point point = (Point) iterator.next();
			log.info("Point. Lat: " + point.getLatitude() + " Lon: "
					+ point.getLongitude());
		}
	}

	@Test
	@Ignore
	public void decodeAll() {
		final GribDecoderNetcdf dec = new GribDecoderNetcdf();
		try {
			dec.getForecastForTime(new File(
					"src/test/resources/multi_1.glo_30m.all.grb2"), 1);
		} catch (final IOException e) {
			log.error(e);
		}
	}
}