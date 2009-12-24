/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.decoder;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

import edu.unicen.surfforecaster.server.domain.ForecastPlain;

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
	Collection<ForecastPlain> getForecastForTime(File file, int time)
			throws IOException;
}
