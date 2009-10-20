/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.decoder;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import edu.unicen.surfforecaster.server.domain.ForecastPlain;

/**
 * @author esteban
 * 
 */
public interface GribDecoder {

	/**
	 * @param file
	 * @param time
	 * @return
	 * @throws IOException
	 */
	Collection<ForecastPlain> getForecastForTime(File file, int time)
			throws IOException;
}
