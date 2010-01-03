package edu.unicen.surfforecaster.server.domain.weka.filter.wavewatch;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Vector;

import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.weka.filter.Filter;
import edu.unicen.surfforecaster.server.domain.weka.filter.OrFilter;

public class DataTimeFilter extends Filter {

	private Calendar minTime = null;

	private Calendar maxTime = null;

	public DataTimeFilter() {}

	public DataTimeFilter(Calendar minTime, Calendar maxTime) {

		this.minTime = minTime;
		this.maxTime = maxTime;
	}

	@Override
	public Vector<Forecast> executeFilter(Vector<?> dataSet) {
		Vector<Forecast> dataset = (Vector<Forecast>) dataSet;
		Vector<Forecast> dataFiltered = new Vector<Forecast>();

		if (this.compareTime(this.minTime, this.maxTime) == 1) {
			Vector<Filter> filters = new Vector<Filter>();
			filters.add(new DataTimeFilter(this.minTime, new GregorianCalendar(0, 0, 0, 23, 59)));
			filters.add(new DataTimeFilter(new GregorianCalendar(0, 0, 0, 0, 0), this.maxTime));
			Filter compuestFilter = new OrFilter(filters);
			dataFiltered = (Vector<Forecast>) compuestFilter
					.executeFilter(dataSet);
		} else {
			for (Enumeration<Forecast> e = dataset.elements(); e
					.hasMoreElements();) {
				Forecast data = e.nextElement();
				Calendar cal = new GregorianCalendar();
				cal.setTime(data.getForecastValidDate());
				if (this.compareTime(cal, this.minTime) == -1
						|| this.compareTime(cal, this.maxTime) == 1) {
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
	
	public String toString(){
		String text = "";
		text = "\tDataTimeFilter\n\t\tDesde -> " + this.minTime.get(Calendar.HOUR_OF_DAY) + ":" + this.minTime.get(Calendar.MINUTE) + "\n\t\tHasta -> " +
				this.maxTime.get(Calendar.HOUR_OF_DAY) + ":" + this.maxTime.get(Calendar.MINUTE) + "\n";
		return text;
	}
}
