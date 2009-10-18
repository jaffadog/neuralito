/**
 * 
 */
package edu.unicen.surfforecaster.server.services;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ForecastService;
import edu.unicen.surfforecaster.common.services.ForecasterDTO;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;

/**
 * @author esteban
 * 
 */

public class ForecastServiceImplementationTest extends
		SpotServiceImplementationTest {

	private final ForecastService forecastService;

	@Override
	@Test
	public void test() {

	}

	/**
	 *
	 */
	public ForecastServiceImplementationTest() {
		super();
		forecastService = (ForecastService) context.getBean("forecastService");

	}

	/**
	 * Create a ww3 forecaster for a given spot.
	 */
	@Test
	public void createWW3ForecastAndListForecasters() {
		try {
			final Point point2 = new Point(23.1, 400D);
			// dataManager.registerPoint(point2);
			// TODO remove this. Datamanager should automatically decode
			// forecast of an new registerd point.
			// dataManager.update(new DownloaderJobListener(), new File(
			// "src/test/resources/multi_1.glo_30m.all.grb2"));

			final Integer initialForecastersForSpot = spotService
					.getSpotForecasters(spot1Id).size();
			final PointDTO gridPoint = point2.getDTO();
			// Create a ww3 forecaster and associate it to spot 1.
			final Integer forecasterId = forecastService.createWW3Forecaster(
					spot1Id, gridPoint);
			// Obtain forecast of this forecaster.
			final List<ForecastDTO> forecasts = forecastService
					.getForecasts(forecasterId);
			Assert.assertNotNull(forecasts);
			Assert.assertTrue("Forecast should not be empty",
					forecasts.size() > 0);
			// Obtain Forecasters of a spot.
			final Collection<ForecasterDTO> spotForecasters = spotService
					.getSpotForecasters(spot1Id);
			// Check if the created forecasts is on the forecasters list of the
			// spot.
			boolean found = false;
			for (final Iterator iterator = spotForecasters.iterator(); iterator
					.hasNext();) {
				final ForecasterDTO forecasterDTO = (ForecasterDTO) iterator
						.next();
				if (forecasterDTO.getId().equals(forecasterId)) {
					found = true;
				}

			}
			Assert.assertTrue("The created forecaster for spot id: " + spot1Id
					+ " was not found on the list of forecasters of the spot.",
					found);
			// We should have one more forecaster than we have before creating
			// one.
			Assert.assertEquals(initialForecastersForSpot + 1, spotForecasters
					.size());

		} catch (final NeuralitoException e) {
			Assert.fail();
		}
	}

}
