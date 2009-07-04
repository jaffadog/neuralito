package filter;

import java.util.ArrayList;
import java.util.Vector;

import Observations.ObsData;

public class WaveHeightFilter extends Filter{
private double height;
private String beach;
/*
 * Removes all observation greater than the specified Height.
 */

public WaveHeightFilter(double theHeight, String theBeach) {
	this.height = theHeight;
	this.beach = theBeach;
}

@SuppressWarnings("unchecked")
@Override
public Vector<ObsData> executeFilter(Vector<?> dataSet) {
	Vector<ObsData> result = new Vector<ObsData>();
	Vector<ObsData> obsDataSet = (Vector<ObsData>) dataSet;
	for (ObsData obsData : obsDataSet) {
		if (obsData.getWaveHeight(this.beach)<this.height){
			result.add(obsData);
		}
	}
	return result;
}

}
