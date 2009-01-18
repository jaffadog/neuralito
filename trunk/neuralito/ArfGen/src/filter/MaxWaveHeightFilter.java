package filter;

import java.util.Enumeration;
import java.util.Vector;

import util.WaveData;



public class MaxWaveHeightFilter extends Filter {
	
	public MaxWaveHeightFilter(){}
	
	@Override
	public Vector<WaveData> executeFilter(Vector<?> dataSet) {
		Vector<WaveData> dataset = (Vector<WaveData>) dataSet;
		Vector<WaveData> dataFiltered = new Vector<WaveData>();
		WaveData currentMaxHeight = null;
		for (Enumeration<WaveData> e = dataset.elements(); e.hasMoreElements();){
			WaveData data = e.nextElement();
			
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
	
	private WaveData maxHeightRead(WaveData currentMaxHeight, WaveData currentData){
		if (currentMaxHeight.getWaveHeight() < currentData.getWaveHeight())
			return currentData;
		else
			return currentMaxHeight;
	}
	
	public String toString(){
		return "MaxWaveHeightFilter\n\n";
	}
	
}
