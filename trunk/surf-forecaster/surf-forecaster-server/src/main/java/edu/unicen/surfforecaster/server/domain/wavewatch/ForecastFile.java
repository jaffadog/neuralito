package edu.unicen.surfforecaster.server.domain.wavewatch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import edu.unicen.surfforecaster.common.services.dto.WaveWatchParameter;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;

/**
 * A character separated file, each line containing one forecast data.
 * 
 * @author esteban
 * 
 */
public class ForecastFile extends File {
	/**
	 * The logger
	 */
	Logger log = Logger.getLogger(this.getClass());
	private final List<WaveWatchParameter> parameters;
	/**
	 * Delimiter characters of massive insert file.
	 */
	public final static String lineStart = "e";
	public final static String fieldSeparator = ",";
	public final static String lineEnd = "x";

	/**
	 * Creates a new forecast file object.
	 * 
	 * @param pathname
	 */
	public ForecastFile(final String pathname,
			final List<WaveWatchParameter> parameters) {
		super(pathname);
		this.parameters = parameters;
	}

	/**
	 * Writes the given forecasts to a text file, this file will be used to
	 * perform massive inserts into DB.
	 * 
	 * @param forecasts
	 * @throws IOException
	 */
	public void writeForecasts(final Collection<Forecast> forecasts)
			throws IOException {
		final BufferedWriter output = new BufferedWriter(new FileWriter(this,
				true), 10000);
		;

		for (final Iterator<Forecast> iterator = forecasts.iterator(); iterator
				.hasNext();) {
			final Forecast forecast = iterator.next();

			final Calendar calendar = new GregorianCalendar(TimeZone
					.getTimeZone("UTC"));
			calendar.setTime(forecast.getBaseDate());
			final int year = calendar.get(Calendar.YEAR);
			final int month = calendar.get(Calendar.MONTH) + 1;
			final int day = calendar.get(Calendar.DAY_OF_MONTH);
			final int hour = calendar.get(Calendar.HOUR_OF_DAY);
			final int minute = calendar.get(Calendar.MINUTE);
			final int seconds = calendar.get(Calendar.SECOND);
			final String parameters = getParameters(forecast);
			final String line = lineStart + year + "-" + month + "-" + day
					+ " " + hour + ":" + minute + ":" + seconds
					+ fieldSeparator + forecast.getForecastTime()
					+ fieldSeparator + forecast.getPoint().getLatitude()
					+ fieldSeparator + forecast.getPoint().getLongitude()
					+ parameters + lineEnd;
			output.append(line);
			output.newLine();
		}
		output.close();
		final long end = System.currentTimeMillis();
	}

	private String getParameters(final Forecast forecast) {
		String characterDelimitedParameters = "";
		for (final WaveWatchParameter parameter : parameters) {
			String value;
			if (forecast.getParameter(parameter.getValue()) != null) {
				value = forecast.getParameter(parameter.getValue()).toString();

			} else {
				value = "-1";
			}

			characterDelimitedParameters += fieldSeparator + value;
		}
		return characterDelimitedParameters;
	}

}
