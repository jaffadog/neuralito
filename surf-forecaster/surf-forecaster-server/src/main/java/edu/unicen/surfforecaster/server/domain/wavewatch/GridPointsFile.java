package edu.unicen.surfforecaster.server.domain.wavewatch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import com.enterprisedt.util.debug.Logger;

import edu.unicen.surfforecaster.server.domain.entity.Point;

public class GridPointsFile extends File {

	/**
	 * The logger to use.
	 */
	Logger log = Logger.getLogger(this.getClass());
	/**
	 * Delimiter characters of massive insert file.
	 */
	public final static String lineStart = "e";
	public final static String fieldSeparator = ",";
	public final static String lineEnd = "x";

	public GridPointsFile(String filePath) {
		super(filePath);
	}

	public void writePoints(Collection validGridPoints) throws IOException {
		log.info("Writing grid points to file.");
		final BufferedWriter output = new BufferedWriter(new FileWriter(this,
				false));
		for (Iterator iterator = validGridPoints.iterator(); iterator.hasNext();) {
			Point point = (Point) iterator.next();
			final String line = this.lineStart + point.getLatitude()
					+ this.fieldSeparator + point.getLongitude() + this.lineEnd;
			output.append(line);
			output.newLine();
		}
		output.close();

		final long end = System.currentTimeMillis();
		log.info("Grid points written to: " + this.getAbsolutePath());
	}

}
