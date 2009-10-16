/**
 * 
 */
package edu.unicen.surfforecaster.server.services;

import org.junit.Test;

import edu.unicen.surfforecaster.common.services.ForecastService;
import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.DataManager;

/**
 * @author esteban
 * 
 */

public class ForecastServiceImplementationTest /*
												 * extends
												 * SpotServiceImplementationTest
												 */{

	private ForecastService forecastService;
	private DataManager dataManager;

	@Test
	public void test() {

	}
	// /**
	// *
	// */
	// public ForecastServiceImplementationTest() {
	// super();
	// forecastService = (ForecastService) context.getBean("forecastService");
	// dataManager = (DataManager) context.getBean("ww3DataManager");
	//
	// }

	// /**
	// * Create a ww3 forecaster for a given spot.
	// */
	// @Test
	// @Ignore
	// public void createWW3ForecastAndListForecasters() {
	// try {
	// final Point point2 = new Point(23.1, 400D);
	// dataManager.registerPoint(point2);
	// // TODO remove this. Datamanager should automatically decode
	// // forecast of an new registerd point.
	// dataManager.update(new DownloaderJobListener(), new File(
	// "src/test/resources/multi_1.glo_30m.all.grb2"));
	//
	// final Integer initialForecastersForSpot = spotService
	// .getSpotForecasters(spot1Id).size();
	// final PointDTO gridPoint = point2.getDTO();
	// // Create a ww3 forecaster and associate it to spot 1.
	// final Integer forecasterId = forecastService.createWW3Forecaster(
	// spot1Id, gridPoint);
	// // Obtain forecast of this forecaster.
	// final List<ForecastDTO> forecasts = forecastService
	// .getForecasts(forecasterId);
	// Assert.assertNotNull(forecasts);
	// Assert.assertTrue("Forecast should not be empty",
	// forecasts.size() > 0);
	// // Obtain Forecasters of a spot.
	// final Collection<ForecasterDTO> spotForecasters = spotService
	// .getSpotForecasters(spot1Id);
	// // Check if the created forecasts is on the forecasters list of the
	// // spot.
	// boolean found = false;
	// for (final Iterator iterator = spotForecasters.iterator(); iterator
	// .hasNext();) {
	// final ForecasterDTO forecasterDTO = (ForecasterDTO) iterator
	// .next();
	// if (forecasterDTO.getId().equals(forecasterId)) {
	// found = true;
	// }
	//
	// }
	// Assert.assertTrue("The created forecaster for spot id: " + spot1Id
	// + " was not found on the list of forecasters of the spot.",
	// found);
	// // We should have one more forecaster than we have before creating
	// // one.
	// Assert.assertEquals(initialForecastersForSpot + 1, spotForecasters
	// .size());
	//
	// } catch (final NeuralitoException e) {
	// Assert.fail();
	// }
	// }
	//
	// /**
	// * Obtain surrounding grid points of a location.
	// *
	// * @throws NeuralitoException
	// */
	// @Test
	// @Ignore
	// public void getSurroundingGridPoints() throws NeuralitoException {
	//
	// double latitude = 8.2;
	// double longitude = 146.2;
	// // Correct points
	// PointDTO point1 = new PointDTO(8.0, 146.0);
	// final PointDTO point2 = new PointDTO(8.5, 146.0);
	// final PointDTO point3 = new PointDTO(8.0, 146.5);
	// final PointDTO point4 = new PointDTO(8.5, 146.5);
	//
	// Collection<PointDTO> nearbyGridPoints = forecastService
	// .getNearbyGridPoints(latitude, longitude);
	// Assert.assertEquals(4, nearbyGridPoints.size());
	// Assert.assertTrue(nearbyGridPoints.contains(point1));
	// Assert.assertTrue(nearbyGridPoints.contains(point2));
	// Assert.assertTrue(nearbyGridPoints.contains(point3));
	// Assert.assertTrue(nearbyGridPoints.contains(point4));
	//
	// // Obtain surrounding grid points of a land point.No grid point should
	// // be obtained.
	// latitude = 26.1;
	// longitude = 80.1;
	// nearbyGridPoints = forecastService.getNearbyGridPoints(latitude,
	// longitude);
	// Assert.assertEquals(0, nearbyGridPoints.size());
	// // Obtain surrounding grid points of a point placed on a grid point.
	// // Only one point should be found.
	// latitude = 8.0;
	// longitude = 146.5;
	// // Correct points
	// point1 = new PointDTO(8.0, 146.5);
	//
	// nearbyGridPoints = forecastService.getNearbyGridPoints(latitude,
	// longitude);
	// Assert.assertEquals(1, nearbyGridPoints.size());
	// Assert.assertTrue(nearbyGridPoints.contains(point1));
	//
	// }
}
