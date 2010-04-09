package edu.unicen.experimenter.datasetgenerator.generators.filters;

import java.util.Enumeration;
import java.util.Vector;

import edu.unicen.experimenter.datasetgenerator.data.WaveData;

public class MaxWaveHeightFilter extends Filter {

	public MaxWaveHeightFilter() {
	}

	@Override
	public Vector<WaveData> executeFilter(final Vector<?> dataSet) {
		final Vector<WaveData> dataset = (Vector<WaveData>) dataSet;
		final Vector<WaveData> dataFiltered = new Vector<WaveData>();
		WaveData currentMaxHeight = null;
		for (final Enumeration<WaveData> e = dataset.elements(); e
				.hasMoreElements();) {
			final WaveData data = e.nextElement();

			if (currentMaxHeight == null) {
				currentMaxHeight = data;
			} else {
				if (currentMaxHeight.equalsDate(data.getDate())) {
					currentMaxHeight = maxHeightRead(currentMaxHeight, data);
				} else {
					dataFiltered.add(currentMaxHeight);
					currentMaxHeight = data;
				}
			}
		}
		if (currentMaxHeight != null) {
			dataFiltered.add(currentMaxHeight);
		}
		// Util.printCollection(dataFiltered);
		return dataFiltered;
	}

	private WaveData maxHeightRead(final WaveData currentMaxHeight,
			final WaveData currentData) {
		if (currentMaxHeight.getWaveHeight() < currentData.getWaveHeight())
			return currentData;
		else
			return currentMaxHeight;
	}

	@Override
	public String toString() {
		return "\tMaxWaveHeightFilter\n";
	}

}
