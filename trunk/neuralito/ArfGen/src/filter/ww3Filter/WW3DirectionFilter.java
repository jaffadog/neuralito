package filter.ww3Filter;

import java.util.Enumeration;
import java.util.Vector;

import buoy.BuoyData;

import ww3.WaveWatchData;

import filter.Filter;


public class WW3DirectionFilter extends Filter {
	
	private Double minDegree = null;
	private Double maxDegree = null;
	
	public WW3DirectionFilter(){}
	
	public WW3DirectionFilter(Double minDegree, Double maxDegree){
		this.minDegree = minDegree;
		this.maxDegree = maxDegree;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Vector<WaveWatchData> executeFilter(Vector<?> dataSet) {
		Vector<WaveWatchData> dataset = (Vector<WaveWatchData>) dataSet;
		Vector<WaveWatchData> dataFiltered = new Vector<WaveWatchData>();
		for (Enumeration<WaveWatchData> e = dataset.elements(); e.hasMoreElements();){
			WaveWatchData data = e.nextElement();
			if (data.getDirection() < this.minDegree || data.getDirection()> this.maxDegree){
				//do nothing
			}
			else
				dataFiltered.add(data);
		}
		return dataFiltered;
	}

	public Double getMaxDegree() {
		return maxDegree;
	}

	public void setMaxDegree(Double maxDegree) {
		this.maxDegree = maxDegree;
	}

	public Double getMinDegree() {
		return minDegree;
	}

	public void setMinDegree(Double minDegree) {
		this.minDegree = minDegree;
	}
	
}
