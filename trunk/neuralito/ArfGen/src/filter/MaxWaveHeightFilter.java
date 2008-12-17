package filter;

import java.util.Enumeration;
import java.util.Vector;

import buoy.BuoyData;


public class MaxWaveHeightFilter extends Filter {
	
	public MaxWaveHeightFilter(){}
	
	@Override
	public Vector<BuoyData> executeFilter(Vector<?> dataSet) {
		Vector<BuoyData> dataset = (Vector<BuoyData>) dataSet;
		Vector<BuoyData> dataFiltered = new Vector<BuoyData>();
		BuoyData currentMaxHeight = null;
		for (Enumeration<BuoyData> e = dataset.elements(); e.hasMoreElements();){
			BuoyData data = e.nextElement();
			
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
	
	private BuoyData maxHeightRead(BuoyData currentMaxHeight, BuoyData currentData){
		if (currentMaxHeight.getWaveHeight() < currentData.getWaveHeight())
			return currentData;
		else
			return currentMaxHeight;
	}
	
}
