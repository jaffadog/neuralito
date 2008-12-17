package filter;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Vector;

import buoy.BuoyData;


public class DataTimeFilter extends Filter {

	private Calendar minTime = null;
	
	private Calendar maxTime = null;
	
	public DataTimeFilter(){}
	
	public DataTimeFilter(Calendar minTime, Calendar maxTime){
		this.minTime = minTime;
		this.maxTime = maxTime;
	}
	
	@Override
	public Vector<BuoyData> executeFilter(Vector<?> dataSet) {
		Vector<BuoyData> dataset = (Vector<BuoyData>) dataSet;
		Vector<BuoyData> dataFiltered = new Vector<BuoyData>();
		for (Enumeration<BuoyData> e = dataset.elements(); e.hasMoreElements();){
			BuoyData data = e.nextElement();
			
			if (! InRange(data.getDate())){
				//do nothing
			}
			else
				dataFiltered.add(data);
		}
		return dataFiltered;
	}

	private boolean InRange(Calendar date) {
		
		if ( (date.get(Calendar.HOUR_OF_DAY) > 6 ) && (date.get(Calendar.HOUR_OF_DAY) < 17)) 
			return false;
		else return true;
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
		//-1 time1 < time 2
		//0 time1 = time 2
		//1 time1 > time 2
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
//	public boolean inBetween(Calendar time, Calendar min, Calendar max){
//		if (time.get(Calendar.HOUR_OF_DAY) < min.get(Calendar.HOUR_OF_DAY) || time.get(Calendar.HOUR_OF_DAY) > max.get(Calendar.HOUR_OF_DAY))
//		
//		return false;
//	}

}
