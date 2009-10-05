/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.decoder;

import java.io.File;
import java.util.Collection;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;

/**
 * @author esteban
 * 
 */
public interface GribDecoder {

	/**
	 * @param file
	 * @param points
	 * @return
	 */
	Collection<Forecast> getForecasts(File file, Collection<Point> points);

	/**
	 * @param file
	 * @return
	 */
	Collection<Point> getValidPoints(File file);
}
