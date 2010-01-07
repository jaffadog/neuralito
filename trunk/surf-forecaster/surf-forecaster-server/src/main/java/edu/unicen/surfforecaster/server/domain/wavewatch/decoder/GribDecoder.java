/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.wavewatch.decoder;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchParameter;

/**
 * Interface to a GRIB decoder.
 * 
 * @author esteban
 * 
 */
public interface GribDecoder extends Serializable{

	/**
	 * List all the parameters that the grib file contains.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public List<String> listParameters(File file) throws IOException;

	/**
	 * Decode the grib file for the specified time and parameters.
	 * 
	 * @param file
	 * @param parameters
	 * @param time
	 * @return
	 * @throws IOException
	 */
	public Collection<Forecast> decodeForecastForTime(File file,
			List<WaveWatchParameter> parameters, int time) throws IOException;

	/**
	 * Decode the given grib files for the specified time and parameters.
	 * 
	 * @param files
	 * @param parameters
	 * @param time
	 * @return
	 * @throws IOException
	 */
	public Collection<Forecast> decodeForecastForTime(Collection<File> files,
			List<WaveWatchParameter> parameters, int time) throws IOException;

	/**
	 * Obtain all the times the given grib file has.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public int getTimes(File file) throws IOException;
}
