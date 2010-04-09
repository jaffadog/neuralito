package edu.unicen.experimenter.datasetgenerator.generators.filters;

import java.util.ArrayList;
import java.util.Vector;

import edu.unicen.experimenter.datasetgenerator.data.visualobservation.VisualObservation;


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
public Vector<VisualObservation> executeFilter(Vector<?> dataSet) {
	Vector<VisualObservation> result = new Vector<VisualObservation>();
	Vector<VisualObservation> obsDataSet = (Vector<VisualObservation>) dataSet;
	for (VisualObservation obsData : obsDataSet) {
		if (obsData.getWaveHeight(this.beach)<this.height){
			result.add(obsData);
		}
	}
	return result;
}

}
