package weka;

import java.util.Vector;

import ww3.WaveWatchData;
import Observations.ObsData;
import buoy.BuoyData;

public interface GenerationStrategy {

	DataSet generateTrainningData(Vector<BuoyData> buoyDataSet,
			Vector<ObsData> obsDataSet, Vector<WaveWatchData> ww3DataSet);
	public String getName();
	public String getDescription();
}
