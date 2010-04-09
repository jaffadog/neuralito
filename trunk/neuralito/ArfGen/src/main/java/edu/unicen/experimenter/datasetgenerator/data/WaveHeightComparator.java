package edu.unicen.experimenter.datasetgenerator.data;

import java.util.Comparator;


public class WaveHeightComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		if (o1 instanceof WaveData && o2 instanceof WaveData) {
			WaveData data1 = (WaveData)o1;
			WaveData data2 = (WaveData)o2;
			Double height1 = data1.getWaveHeight();
			Double height2 = data2.getWaveHeight();
			return height1.compareTo(height2);
		}
		return 0;
	}
}
