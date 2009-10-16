/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.ForecastArch;

/**
 * @author esteban
 * 
 */
public interface GribDecoder {

	/**
	 * @param file
	 * @return
	 */
	Collection<Point> getValidPoints(File file);

	/**
	 * @param file
	 * @param time
	 * @return
	 * @throws IOException
	 */
	Collection<ForecastArch> getForecastForTime(File file, int time)
			throws IOException;
}
