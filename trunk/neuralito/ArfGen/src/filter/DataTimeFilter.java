package filter;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Vector;

import util.WaveData;

public class DataTimeFilter extends Filter {

	private Calendar minTime = null;

	private Calendar maxTime = null;

	public DataTimeFilter() {
	}

	public DataTimeFilter(Calendar minTime, Calendar maxTime) {

		this.minTime = minTime;
		this.maxTime = maxTime;
	}

	@Override
	public Vector<WaveData> executeFilter(Vector<?> dataSet) {
		Vector<WaveData> dataset = (Vector<WaveData>) dataSet;
		Vector<WaveData> dataFiltered = new Vector<WaveData>();

		if (this.compareTime(this.minTime, this.maxTime) == 1) {
			Vector<Filter> filters = new Vector<Filter>();
			filters.add(new DataTimeFilter(this.minTime, new GregorianCalendar(0, 0, 0, 23, 59)));
			filters.add(new DataTimeFilter(new GregorianCalendar(0, 0, 0, 0, 0), this.maxTime));
			Filter compuestFilter = new OrFilter(filters);
			dataFiltered = (Vector<WaveData>) compuestFilter.executeFilter(dataSet);
		} else {
			for (Enumeration<WaveData> e = dataset.elements(); e.hasMoreElements();) {
				WaveData data = e.nextElement();
				
				if (this.compareTime(data.getDate(), this.minTime) == -1 || this.compareTime(data.getDate(), this.maxTime) == 1){
						//do nothing
				}
				else
					dataFiltered.add(data);
			}
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

	private int compareTime(Calendar time1, Calendar time2) {
		// 1 time1 < time 2
		// 0 time1 = time 2
		// 1 time1 > time 2
		if (time1.get(Calendar.HOUR_OF_DAY) < time2.get(Calendar.HOUR_OF_DAY))
			return -1;
		else if (time1.get(Calendar.HOUR_OF_DAY) > time2
				.get(Calendar.HOUR_OF_DAY))
			return 1;
		else if (time1.get(Calendar.HOUR_OF_DAY) == time2
				.get(Calendar.HOUR_OF_DAY)) {
			if (time1.get(Calendar.MINUTE) < time2.get(Calendar.MINUTE))
				return -1;
			else if (time1.get(Calendar.MINUTE) > time2.get(Calendar.MINUTE))
				return 1;
			else if (time1.get(Calendar.MINUTE) == time2.get(Calendar.MINUTE))
				return 0;
		}
		return 0;
	}
	/*
	private boolean InRange(Calendar date) {

		if ((date.get(Calendar.HOUR_OF_DAY) > 6) && (date.get(Calendar.HOUR_OF_DAY) < 17))
			return false;
		else
			return true;
	}
	*/
}
