package filter;

import java.util.Enumeration;
import java.util.Vector;

import util.WaveData;


public class DataWaveDirectionFilter extends Filter {
	
	private Double minDegree = null;
	private Double maxDegree = null;
	
	public DataWaveDirectionFilter(){}
	
	public DataWaveDirectionFilter(Double minDegree, Double maxDegree){
		this.minDegree = minDegree;
		this.maxDegree = maxDegree;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Vector<WaveData> executeFilter(Vector<?> dataSet) {
		Vector<WaveData> dataset = (Vector<WaveData>) dataSet;
		Vector<WaveData> dataFiltered = new Vector<WaveData>();
		for (Enumeration<WaveData> e = dataset.elements(); e.hasMoreElements();){
			WaveData data = e.nextElement();
			if (data.getWaveDirection() < this.minDegree || data.getWaveDirection() > this.maxDegree){
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
