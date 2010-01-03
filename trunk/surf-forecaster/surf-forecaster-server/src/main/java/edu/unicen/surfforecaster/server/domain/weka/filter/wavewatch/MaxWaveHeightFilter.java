package edu.unicen.surfforecaster.server.domain.weka.filter.wavewatch;

import java.util.Enumeration;
import java.util.Vector;

import edu.unicen.surfforecaster.common.services.dto.WW3Parameter;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.weka.filter.Filter;

public class MaxWaveHeightFilter extends Filter {

	public MaxWaveHeightFilter() {
	}

	@Override
	public Vector<Forecast> executeFilter(Vector<?> dataSet) {
		Vector<Forecast> dataset = (Vector<Forecast>) dataSet;
		Vector<Forecast> dataFiltered = new Vector<Forecast>();
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
					dataFiltered.add(currentMaxHeight);
					currentMaxHeight = data;
				}
			}
		}
		if (currentMaxHeight != null)
			dataFiltered.add(currentMaxHeight);
		return dataFiltered;
	}

	private Forecast maxHeightRead(Forecast currentMaxHeight,
			Forecast currentData) {
		if (currentMaxHeight.getParameter(
				WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.getValue())
				.getdValue() < currentData.getParameter(
				WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.getValue())
				.getdValue())
			return currentData;
		else
			return currentMaxHeight;
	}

	public String toString() {
		return "\tMaxWaveHeightFilter\n";
	}

}
