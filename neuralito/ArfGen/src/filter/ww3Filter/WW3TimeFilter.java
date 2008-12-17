package filter.ww3Filter;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Vector;

import ww3.WaveWatchData;

import filter.Filter;

import buoy.BuoyData;


public class WW3TimeFilter extends Filter {

	private Calendar minTime = null;
	
	private Calendar maxTime = null;
	
	public WW3TimeFilter(){}
	
	public WW3TimeFilter(Calendar minTime, Calendar maxTime){
		this.minTime = minTime;
		this.maxTime = maxTime;
	}
	
	@Override
	public Vector<WaveWatchData> executeFilter(Vector<?> dataSet) {
		Vector<WaveWatchData> dataset = (Vector<WaveWatchData>) dataSet;
		Vector<WaveWatchData> dataFiltered = new Vector<WaveWatchData>();
		for (Enumeration<WaveWatchData> e = dataset.elements(); e.hasMoreElements();){
			WaveWatchData data = e.nextElement();
			if (this.compareTime(data.getDate(), this.minTime) == -1 ||
				this.compareTime(data.getDate(), this.maxTime) == 1){
				//do nothing
			}
			else
				dataFiltered.add(data);
		}
		return dataFiltered;
	}

	public Calendar getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(Calendar maxTime) {
		this.maxTime = maxTime;
	}

	public Calendar getMinTime() {
		return minTime;
	}

	public void setMinTime(Calendar minTime) {
		this.minTime = minTime;
	}
	
	private int compareTime(Calendar time1, Calendar time2){
		if (time1.get(Calendar.HOUR_OF_DAY) < time2.get(Calendar.HOUR_OF_DAY))
			return -1;
		else 
			if (time1.get(Calendar.HOUR_OF_DAY) > time2.get(Calendar.HOUR_OF_DAY))
				return 1;
			else 
				if (time1.get(Calendar.HOUR_OF_DAY) == time2.get(Calendar.HOUR_OF_DAY)){
					if (time1.get(Calendar.MINUTE) < time2.get(Calendar.MINUTE))
						return -1;
					else
						if (time1.get(Calendar.MINUTE) > time2.get(Calendar.MINUTE))
							return 1;
						else
							if (time1.get(Calendar.MINUTE) == time2.get(Calendar.MINUTE))
								return 0;
				}
		return 0;
	}

}
