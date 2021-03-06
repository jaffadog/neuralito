package edu.unicen.surfforecaster.server.domain.wavewatch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import edu.unicen.surfforecaster.common.services.dto.WaveWatchParameter;
import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.entity.Point;
import edu.unicen.surfforecaster.server.domain.wavewatch.decoder.GribDecoder;
import edu.unicen.surfforecaster.server.domain.wavewatch.decoder.GribDecoderNetcdf;

public class ValidGridPointsGenerator {

	private static GribDecoder gribDecoder = new GribDecoderNetcdf();
	private static Logger log = Logger
			.getLogger(ValidGridPointsGenerator.class);

	public static void createCsvFile(final String file,
			final String destinationFile) throws IOException {
		log.info("Creating csv file for valid grid points. Source grib File: "
				+ file + ", destination file: " + destinationFile);
		final List<Point> validGridPoints = getValidGridPoints(file);
		writePointsToFile(validGridPoints, destinationFile);
	}

	/**
	 * @param gridFile
	 * @return
	 * @throws IOException
	 */
	private static List<Point> getValidGridPoints(final String gridFile)
			throws IOException {
		log.info("Decoding grib file to obtain valid grid points.");
		final List<WaveWatchParameter> parameters = new ArrayList<WaveWatchParameter>();
		parameters.add(WaveWatchParameter.COMBINED_SWELL_WIND_WAVE_HEIGHT_V2);
		final Collection<Forecast> forecastForTime = gribDecoder
				.decodeForecastForTime(new File(gridFile), parameters, 0);
		final List<Point> points = new ArrayList<Point>();
		for (final Iterator iterator = forecastForTime.iterator(); iterator
				.hasNext();) {
			final Forecast forecastArch = (Forecast) iterator.next();
			if (forecastArch.getParameter(
					WaveWatchParameter.COMBINED_SWELL_WIND_WAVE_HEIGHT_V2
							.getValue()).getfValue() != -1) {
				points.add(forecastArch.getPoint());
			}
		}
		log.info(points.size() + " valid grid points have been found.");
		return points;
	}

	private static void writePointsToFile(final List<Point> validGridPoints,
			final String destinationFile) throws IOException {
		log.info("Writing grid points to file.");
		final File file = new File(destinationFile);
		if (file.exists()) {
			log.info(file.getAbsolutePath());
			final boolean deleted = file.delete();
			if (!deleted)
				throw new RuntimeException("Error deleting temporary file("
						+ file.getAbsolutePath()
						+ "). Could not delete. Try removing file manually.");
		}

		final BufferedWriter output = new BufferedWriter(new FileWriter(file,
				false));
		for (final Iterator iterator = validGridPoints.iterator(); iterator
				.hasNext();) {
			final Point point = (Point) iterator.next();
			final String line = GridPointsFile.lineStart + point.getLatitude()
					+ GridPointsFile.fieldSeparator + point.getLongitude()
					+ GridPointsFile.lineEnd;
			output.append(line);
			output.newLine();
		}
		output.close();

		final long end = System.currentTimeMillis();
		log.info("Grid points written to: " + file.getAbsolutePath());
	}
}
