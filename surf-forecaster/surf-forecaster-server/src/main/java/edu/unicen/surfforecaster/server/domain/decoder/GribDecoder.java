/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.decoder;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import edu.unicen.surfforecaster.server.domain.WaveWatchParameter;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;

/**
 * @author esteban
 * 
 */
public interface GribDecoder extends Serializable{

	/**
	 * @param file
	 * @param time
	 * @return
	 * @throws IOException
	 */
	Collection<Forecast> decodeForecastForTime(File file,
			List<WaveWatchParameter> parametersToDecode, int time)
			throws IOException;
}
