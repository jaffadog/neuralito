package filter;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Vector;

import buoy.BuoyData;

import ww3.WaveWatchData;


public class Ww3MaxWaveHeightFilter extends Filter {
	
	public Ww3MaxWaveHeightFilter(){}
	
	@Override
	public Vector<WaveWatchData> executeFilter(Vector<?> dataSet) {
		Vector<WaveWatchData> dataset = (Vector<WaveWatchData>) dataSet;
		Vector<WaveWatchData> dataFiltered = new Vector<WaveWatchData>();
		WaveWatchData currentMaxHeight = null;
		for (Enumeration<WaveWatchData> e = dataset.elements(); e.hasMoreElements();){
			WaveWatchData data = e.nextElement();

			if (currentMaxHeight == null)
				currentMaxHeight = data;
			else{
				if (currentMaxHeight.equalsDate(data.getDate()))
					currentMaxHeight = this.maxHeightRead(currentMaxHeight, data);
				else{
					dataFiltered.add(currentMaxHeight);
					currentMaxHeight = data;
				}
			}
		}
		if (currentMaxHeight != null)
			dataFiltered.add(currentMaxHeight);
		return dataFiltered;
	}
	
	private WaveWatchData maxHeightRead(WaveWatchData currentMaxHeight, WaveWatchData currentData){
		if (currentMaxHeight.getHeight() < currentData.getHeight())
			return currentData;
		else
			return currentMaxHeight;
	}
	
}
