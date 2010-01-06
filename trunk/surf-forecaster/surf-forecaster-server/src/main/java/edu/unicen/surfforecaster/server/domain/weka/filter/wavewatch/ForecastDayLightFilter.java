package edu.unicen.surfforecaster.server.domain.weka.filter.wavewatch;

import java.sql.Time;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.weka.filter.Filter;
import edu.unicen.surfforecaster.server.domain.weka.filter.OrFilter;

/**
 * Filter all the forecasts which are between the specified hours.
 * 
 * @author esteban
 * 
 */
public class ForecastDayLightFilter extends Filter {

	private Time fromTime = null;

	private Time toTime = null;

	public ForecastDayLightFilter() {
	}

	/**
	 * Init filter.
	 * 
	 * @param fromTime
	 *            the initial time range.
	 * @param toTime
	 *            the end of the time range.
	 */
	public ForecastDayLightFilter(Time fromTime, Time toTime) {

		this.fromTime = fromTime;
		this.toTime = toTime;
	}

	@Override
	public Vector<Forecast> executeFilter(Vector<?> dataSet) {
		Vector<Forecast> dataset = (Vector<Forecast>) dataSet;
		Vector<Forecast> dataFiltered = new Vector<Forecast>();

		if (this.compareTime(this.fromTime, this.toTime) == 1) {
			Vector<Filter> filters = new Vector<Filter>();
			Time endOfDay = new Time(23, 59, 0);
			Time beginningOfDay = new Time(0, 0, 0);
			filters.add(new ForecastDayLightFilter(this.fromTime, endOfDay));
			filters.add(new ForecastDayLightFilter(beginningOfDay, this.toTime));
			Filter compuestFilter = new OrFilter(filters);
			dataFiltered = (Vector<Forecast>) compuestFilter
					.executeFilter(dataSet);
		} else {
			for (Enumeration<Forecast> e = dataset.elements(); e
					.hasMoreElements();) {
				Forecast data = e.nextElement();
				Date forecastDate = data.getForecastValidDate();
				Time forecastTime = new Time(forecastDate.getHours(),
						forecastDate.getMinutes(), 0);
				if (this.compareTime(forecastTime,
						this.fromTime) == -1
						|| this.compareTime(forecastTime,
								this.toTime) == 1) {
					// do nothing
				} else
					dataFiltered.add(data);
			}
		}
		return dataFiltered;
	}

	/**
	 * Compares two different times.
	 * 
	 * @param time1
	 * @param time2
	 * @return -1 if time1 < time 2; 0 if time1 = time 2 ; 1 time1 > time 2
	 */
	private int compareTime(Time time1, Time time2) {

		if (time1.getHours() < time2.getHours())
			return -1;
		else if (time1.getHours() > time2.getHours())
			return 1;
		else if (time1.getHours() == time2.getHours()) {
			if (time1.getMinutes() < time2.getMinutes())
				return -1;
			else if (time1.getMinutes() > time2.getMinutes())
				return 1;
			else if (time1.getMinutes() == time2.getMinutes())
				return 0;
		}
		return 0;
	}

	// public String toString() {
	// String text = "";
	// text = "\tDataTimeFilter\n\t\tDesde -> "
	// + this.fromTime.get(Calendar.HOUR_OF_DAY) + ":"
	// + this.fromTime.get(Calendar.MINUTE) + "\n\t\tHasta -> "
	// + this.toTime.get(Calendar.HOUR_OF_DAY) + ":"
	// + this.toTime.get(Calendar.MINUTE) + "\n";
	// return text;
	// }
}
