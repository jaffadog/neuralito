/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.wavewatch;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.unicen.surfforecaster.common.services.dto.WaveWatchParameter;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.Point;

/**
 * Wave Watch Model abstraction. Each Wave Watch model is responsible for
 * providing the latest forecast and the archive of forecast. Also it
 * responsible for describing the grid points surrounding a given location.
 * 
 * @author esteban
 * 
 */
public interface WaveWatchSystem {

	/**
	 * Obtain the forecasts for the given grid point.
	 * 
	 * @param gridPoint
	 * @return
	 */
	public List<Forecast> getForecasts(Point gridPoint);

	/**
	 * Obtain archived forecasts.
	 * 
	 * @param gridPoint
	 *            the point of the forecast
	 * @param from
	 *            initial ti range of forecasts.
	 * @param to
	 *            final date range of the forecasts.
	 * @return list of pasts forecasts in the date range for the gridPoint
	 */
	public List<Forecast> getArchivedForecasts(Point gridPoint, Date from,
			Date to);

	/**
	 * The last time the forecasts where updated.
	 * 
	 * @return
	 */
	public Date getLatestForecastTime();

	/**
	 * Obtain the neighbors of a grid point.
	 * 
	 * @param point
	 *            the point to look for neighbors.
	 * @return the neighbors points
	 */
	public List<Point> getPointNeighbors(final Point point);

	/**
	 * Determines if the given point belongs to this system grid.
	 * 
	 * @param point
	 *            to evaluate
	 * @return true if point is a valid grid point, false otherwise.
	 */
	public boolean isGridPoint(Point point);

	/**
	 * Obtain the wave parameters that this system supports.
	 * 
	 * @return
	 */
	public List<WaveWatchParameter> getParameters();

	/**
	 * 
	 * @return
	 */
	public String getName();

	public void importForecasts(Collection<Collection<File>> gribFiles)
			throws IOException;

}
