package edu.unicen.surfforecaster.server.domain.weka.filter.wavewatch;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import edu.unicen.surfforecaster.server.domain.weka.filter.Filter;
import edu.unicen.surfforecaster.server.domain.weka.util.WaveData;
import edu.unicen.surfforecaster.server.domain.weka.util.WaveHeightComparator;



public class AvgWaveHeightFilter extends Filter {
	
	public AvgWaveHeightFilter(){}
	
	@Override
	public Vector<WaveData> executeFilter(Vector<?> dataSet) {
		Vector<WaveData> dataset = (Vector<WaveData>) dataSet;
		Vector<WaveData> dataFiltered = new Vector<WaveData>();
		WaveData firstDataOfDay = null; //the first read of each day´
		Vector<WaveData> readsOfDay = null; //here we put all the reads for a day to extract the avg wave height read
		for (Enumeration<WaveData> e = dataset.elements(); e.hasMoreElements();){
			WaveData data = e.nextElement();
			
			if (firstDataOfDay == null){
				firstDataOfDay = data;
				readsOfDay = new Vector<WaveData>();
				readsOfDay.add(firstDataOfDay);
			}
			else{
				if (firstDataOfDay.equalsDate(data.getDate()))
					readsOfDay.add(data);
				else{
					WaveData avgHeightData = this.getAvgWaveHeightRead(readsOfDay);
					if (avgHeightData != null)
						dataFiltered.add(avgHeightData);
					firstDataOfDay = data;
					readsOfDay = new Vector<WaveData>();
					readsOfDay.add(firstDataOfDay);
				}
			}
		}
		WaveData avgHeightData = this.getAvgWaveHeightRead(readsOfDay);
		if (avgHeightData != null)
			dataFiltered.add(avgHeightData);
		return dataFiltered;
	}
	
	private WaveData getAvgWaveHeightRead(Vector<WaveData> readsOfDay){
		if (readsOfDay == null)
			return null;
		else if (readsOfDay.size() == 0)
			return null;
		else if (readsOfDay.size() == 1)
			return readsOfDay.get(0);
		else {
			Collections.sort(readsOfDay, new WaveHeightComparator()); //innecessary
			double sumHeights = 0;
			double avgHeights = 0;
			for (Enumeration<WaveData> e = readsOfDay.elements(); e.hasMoreElements();){
				WaveData data = e.nextElement();
				sumHeights += data.getWaveHeight();
			}
			if (readsOfDay.size() > 0)
				avgHeights = sumHeights / readsOfDay.size();
			
			int index = 0;
			double diff = 9999;
			for (int i = 0; i < readsOfDay.size() - 1; i++){
				WaveData data = (WaveData)readsOfDay.get(i);
				if (Math.abs(data.getWaveHeight() - avgHeights) < diff){
					index = i;
					diff = Math.abs(data.getWaveHeight() - avgHeights);
				}
			}
			return readsOfDay.get(index);
		}
	}
	
	private WaveData maxHeightRead(WaveData currentMaxHeight, WaveData currentData){
		if (currentMaxHeight.getWaveHeight() < currentData.getWaveHeight())
			return currentData;
		else
			return currentMaxHeight;
	}
	
	public String toString(){
		return "\tMaxWaveHeightFilter\n";
	}
	
}
