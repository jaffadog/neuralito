/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster;

import java.io.File;
import java.util.Collection;

/**
 * Grib decoder that uses the NETCDF library to read the grib files.
 * 
 * @author esteban
 * 
 */
public class GribDecoderNetCdf implements GribDecoder {

	/**
	 * @see edu.unicen.surfforecaster.server.domain.entity.forecaster.GribDecoder#getForecasts(java.io.File,
	 *      java.io.File, java.io.File)
	 */
	@Override
	public Collection<Forecast> getForecasts(final File windFile,
			final File wavefile, final File lungu,
			final Collection<Point> locations) {

		return null;
	}

}
