package edu.unicen.surfforecaster.server.domain.weka.strategy;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import weka.core.Instance;
import weka.core.Instances;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.Point;
import edu.unicen.surfforecaster.server.domain.entity.VisualObservation;
import edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchSystem;
import edu.unicen.surfforecaster.server.domain.weka.filter.AndFilter;
import edu.unicen.surfforecaster.server.domain.weka.filter.Filter;
import edu.unicen.surfforecaster.server.domain.weka.filter.wavewatch.DataTimeFilter;
import edu.unicen.surfforecaster.server.domain.weka.filter.wavewatch.MaxWaveHeightFilter;
import edu.unicen.surfforecaster.server.domain.weka.util.Util;

/**
 * 
 * @author esteban
 * 
 */
public class NoBuoyStrategy implements DataSetGenerationStrategy {
	/**
	 * Auto-Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 6625411909943252625L;
	/**
	 * The name of the data set containing the instances generated by this
	 * strategy.
	 */
	private static String dataSetName = "NoBuoyStrategy";
	/**
	 * The attribute which is set as class attribute.
	 */
	private static String classAttributeName = "visualObservation";
	/**
	 * The attributes which compose the instances generated by this strategy.
	 */
	private static String[] strategyAttributes = new String[] { "ww3Height",
			"ww3Period", "ww3Direction", "visualObservation" };
	/**
	 * The options this strategy allows.
	 */
	private static Map<String, OptionType> options = new HashMap<String, OptionType>() {
		{
			put("latitudeGridPoint1", OptionType.DECIMAL);
			put("longitudeGridPoint1", OptionType.DECIMAL);
			put("utcSunriseHour", OptionType.INTEGER);
			put("utcSunriseMinute", OptionType.INTEGER);
			put("utcSunsetHour", OptionType.INTEGER);
			put("utcSunsetMinute", OptionType.INTEGER);
		}
	};

	@Override
	public Map<Forecast, Instance> generateLatestForecastInstances(
			WaveWatchSystem model, Map<String, Serializable> options) {
		Map<Forecast, Instance> generatedInstances = new HashMap<Forecast, Instance>();
		Point gridPoint = getGridPoint(options);
		List<Forecast> latestForecasts = model.getForecasts(gridPoint);
		for (Forecast forecast : latestForecasts) {
			Map<String, Double> instanceData = generateInstanceData(forecast,
					null);
			Instance instance = Util.createWekaInstance(instanceData,
					strategyAttributes, classAttributeName);
			generatedInstances.put(forecast, instance);
		}
		return generatedInstances;
	}

	@Override
	public Instances generateTrainningInstances(WaveWatchSystem model,
			Collection<VisualObservation> observations,
			Map<String, Serializable> options) {
		Point gridPoint = getGridPoint(options);
		Calendar sunrise = getSunrise(options);
		Calendar sunset = getSunset(options);
		Date from = oldestObservation(observations);
		Date to = mostRecentObservation(observations);
		Collection<Forecast> archivedForecasts = model.getArchivedForecasts(
				gridPoint, from, to);
		//For each day filter the forecast with greatest wave height which occur during daylight.
		Collection<Forecast> filteredForecasts = filterForecasts(archivedForecasts, sunrise, sunset);
		Collection<Map<String, Double>> instancesData = new Vector<Map<String, Double>>();
		//For each forecast, couple it with the corresponding visual observation to generate the instance.
		for (Forecast forecast : filteredForecasts) {
			for (VisualObservation observation : observations) {
				if (observation.equalsDate(forecast.getForecastDate())) {
					instancesData.add(generateInstanceData(forecast,
							observation));
					break;
				}
			}
		}	
		Instances instances = Util.createWekaInstances(dataSetName, instancesData, strategyAttributes, classAttributeName);
		
		return instances;
	}

	@Override
	public Map<String, OptionType> listAvailableOptions() {
		return options;
	}

	/**
	 * Filter forecasts using 2 filters:
	 * 1)Filter those forecasts which occur during daylight.
	 * 2)Filter the forecasts which has the max wave height during each day.
	 * 
	 * @param forecasts
	 *            to be filtered
	 * @param sunrise
	 *            the time the daylight begins
	 * @param sunset
	 *            the time the daylight ends
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Collection<Forecast> filterForecasts(
			Collection<Forecast> forecasts, Calendar sunrise, Calendar sunset) {
		Vector<Filter> filters = new Vector<Filter>();
		Filter dataTimeFilter = new DataTimeFilter(sunrise, sunset);
		filters.add(dataTimeFilter);
		filters.add(new MaxWaveHeightFilter());
		Filter compuestFilter = new AndFilter(filters);
		forecasts = (Vector<Forecast>) compuestFilter.executeFilter(new Vector(
				forecasts));
		return forecasts;
	}

	/**
	 * 
	 * Creates a map <AttributeName,AttributeValue> containing the data of an
	 * instance.
	 * 
	 * @param forecast
	 *            to obtain forecast related attributes
	 * @param observation
	 *            to obtain observation attribute
	 * @return
	 */
	private Hashtable<String, Double> generateInstanceData(Forecast forecast,
			VisualObservation observation) {
		Hashtable<String, Double> instanceData = new Hashtable<String, Double>();
		instanceData.put("ww3Height", forecast.getParameter(
				"combinedWaveHeight").getdValue());
		instanceData.put("ww3Period", forecast.getParameter("peakWavePeriod")
				.getdValue());
		instanceData.put("ww3Direction", forecast.getParameter(
				"peakWaveDirection").getdValue());
		if (observation != null)
			instanceData.put("visualObservation", observation.getWaveHeight());
		return instanceData;
	}

	/**
	 * Obtain grid point from options.
	 * 
	 * @param options
	 * @return
	 */
	private Point getGridPoint(Map<String, Serializable> options) {

		Float latitude = (Float) options.get("latitudeGridPoint1");
		Float longitude = (Float) options.get("longitudeGridPoint1");
		return new Point(latitude, longitude);
	}

	/**
	 * Obtain the most recent visual observation date.
	 * 
	 * @param observations
	 * @return
	 */
	private Date mostRecentObservation(
			Collection<VisualObservation> observations) {
		long mostRecentDate = Long.MIN_VALUE;
		for (VisualObservation observation : observations) {
			if (observation.getDate().getTime() > mostRecentDate) {
				mostRecentDate = observation.getDate().getTime();
			}
		}
		return new Date(mostRecentDate);
	}

	/**
	 * Obtain the oldest visual observation date.
	 * 
	 * @param observations
	 * @return
	 */
	private Date oldestObservation(
			Collection<VisualObservation> observations) {
		long oldestDate = Long.MAX_VALUE;
		for (VisualObservation observation : observations) {
			if (observation.getDate().getTime() < oldestDate) {
				oldestDate = observation.getDate().getTime();
			}
		}
		return new Date(oldestDate);
	}

	/**
	 * Obtain the sunset time from the options.
	 * 
	 * @param options
	 * @return
	 */
	private Calendar getSunset(Map<String, Serializable> options) {
		int sunsetHour = (Integer) options.get("utcSunsetHour");
		int sunsetMinute = (Integer) options.get("utcSunsetMinute");
		Calendar sunset = new GregorianCalendar(0, 0, 0, sunsetHour,
				sunsetMinute);
		return sunset;
	}

	/**
	 * Obtain the sunrise time from the options.
	 * 
	 * @param options
	 * @return
	 */
	private Calendar getSunrise(Map<String, Serializable> options) {
		int sunriseHour = (Integer) options.get("utcSunriseHour");
		int sunriseMinute = (Integer) options.get("utcSunriseMinute");
		Calendar sunrise = new GregorianCalendar(0, 0, 0, sunriseHour,
				sunriseMinute);
		return sunrise;
	}


}
