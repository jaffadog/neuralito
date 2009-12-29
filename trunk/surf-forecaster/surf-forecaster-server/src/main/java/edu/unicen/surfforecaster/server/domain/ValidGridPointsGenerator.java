package edu.unicen.surfforecaster.server.domain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import edu.unicen.surfforecaster.server.domain.decoder.GribDecoder;
import edu.unicen.surfforecaster.server.domain.decoder.GribDecoderNetcdf;
import edu.unicen.surfforecaster.server.domain.entity.Point;

public class ValidGridPointsGenerator {

	private static GribDecoder gribDecoder = new GribDecoderNetcdf();
	private static Logger log = Logger
			.getLogger(ValidGridPointsGenerator.class);

	public static void createCsvFile(final String file, String destinationFile)
			throws IOException {
		log.info("Creating csv file for valid grid points. Source grib File: "
				+ file + ", destination file: " + destinationFile);
		List<Point> validGridPoints = getValidGridPoints(file);
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
		final Collection<ForecastPlain> forecastForTime = gribDecoder
				.getForecastForTime(new File(gridFile), 0);
		final List<Point> points = new ArrayList<Point>();
		for (final Iterator iterator = forecastForTime.iterator(); iterator
				.hasNext();) {
			final ForecastPlain forecastArch = (ForecastPlain) iterator.next();
			points.add(new Point(forecastArch.getLatitude(), forecastArch
					.getLongitude()));
		}
		log.info(points.size()+" valid grid points have been found.");
		return points;
	}

	private static void writePointsToFile(List<Point> validGridPoints,	String destinationFile) throws IOException {
		log.info("Writing grid points to file.");
		File file = new File(destinationFile);
		final BufferedWriter output = new BufferedWriter(new FileWriter(file,
				false));
		for (Iterator iterator = validGridPoints.iterator(); iterator.hasNext();) {
			Point point = (Point) iterator.next();
			final String line = WaveWatchSystemV3.WaveWatchSystemImpl
					+ point.getLatitude() + WaveWatchSystemV3.WaveWatchSystemImpl
					+ point.getLongitude() + WaveWatchSystemV3.WaveWatchSystemImpl;
			output.append(line);
			output.newLine();
		}
		output.close();

		final long end = System.currentTimeMillis();
		log.info("Grid points written to: " + file.getAbsolutePath());
	}
}
