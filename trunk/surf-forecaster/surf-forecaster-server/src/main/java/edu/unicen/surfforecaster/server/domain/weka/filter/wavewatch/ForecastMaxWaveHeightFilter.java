package edu.unicen.surfforecaster.server.domain.weka.filter.wavewatch;

import java.util.Enumeration;
import java.util.Vector;

import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchParameter;
import edu.unicen.surfforecaster.server.domain.weka.filter.Filter;

/**
 * Filter to extract the forecast with higher reported wave height on each day.
 * 
 * @author esteban
 * 
 */
public class ForecastMaxWaveHeightFilter extends Filter {

	public ForecastMaxWaveHeightFilter() {
	}

	/**
	 * @param forecasts
	 *            a collection of forecasts.
	 * 
	 * @return the maximum forecast for each day.(The one with maximun wave
	 *         height)
	 */
	@Override
	public Vector<Forecast> executeFilter(Vector<?> forecasts) {
		Vector<Forecast> dataset = (Vector<Forecast>) forecasts;
		Vector<Forecast> highestForecasts = new Vector<Forecast>();
		Forecast currentMaxHeight = null;
		for (Enumeration<Forecast> e = dataset.elements(); e.hasMoreElements();) {
			Forecast data = e.nextElement();

			if (currentMaxHeight == null)
				currentMaxHeight = data;
			else {
				if (currentMaxHeight.equalsDate(data.getForecastValidDate()))
					currentMaxHeight = this.maxHeightRead(currentMaxHeight,
							data);
				else {
					highestForecasts.add(currentMaxHeight);
					currentMaxHeight = data;
				}
			}
		}
		if (currentMaxHeight != null)
			highestForecasts.add(currentMaxHeight);

		return highestForecasts;

	}

	private Forecast maxHeightRead(Forecast currentMaxHeight,
			Forecast currentData) {
		if (currentMaxHeight.getParameter(
				WaveWatchParameter.COMBINED_SWELL_WIND_WAVE_HEIGHT_V2
						.getValue()).getfValue() < currentData.getParameter(
				WaveWatchParameter.COMBINED_SWELL_WIND_WAVE_HEIGHT_V2
						.getValue()).getfValue())
			return currentData;
		else
			return currentMaxHeight;
	}


}
