/**
 * 
 */
package edu.unicen.surfforecaster.server.services;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ForecastService;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;

/**
 * @author esteban
 * 
 */
public class ForecastServiceImplementationTest extends
		SpotServiceImplementationTest {

	private final ForecastService forecastService;

	/**
	 * 
	 */
	public ForecastServiceImplementationTest() {
		super();// TODO Auto-generated constructor stub
		forecastService = (ForecastService) context.getBean("forecastService");
	}

	/**
	 * Create a ww3 forecaster for a given spot.
	 */
	@Test
	public void createWW3Forecast() {
		try {
			final Collection<PointDTO> gridPoints = forecastService
					.getWW3GridPoints(1D, 2D);
			final PointDTO gridPoint = new PointDTO(2D, 24D);
			final Integer forecasterId = forecastService.createWW3Forecaster(
					spot1Id, gridPoint);

			final List<ForecastDTO> forecasts = forecastService
					.getForecasts(forecasterId);
			Assert.assertNotNull(forecasts);
			Assert.assertTrue("Forecast should not be empty",
					forecasts.size() > 0);
		} catch (final NeuralitoException e) {
			Assert.fail();
		}
	}
}
