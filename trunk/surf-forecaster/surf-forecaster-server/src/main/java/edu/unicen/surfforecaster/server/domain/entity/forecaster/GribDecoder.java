/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster;

import java.io.File;
import java.util.Collection;

/**
 * @author esteban
 * 
 */
public interface GribDecoder {

	public Collection<Forecast> getForecasts(File windFile, File wavefile,
			File lungu, Collection<Point> locations);
}
