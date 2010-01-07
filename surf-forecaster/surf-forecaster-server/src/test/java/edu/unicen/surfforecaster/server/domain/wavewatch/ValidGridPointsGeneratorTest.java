package edu.unicen.surfforecaster.server.domain.wavewatch;

import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

import edu.unicen.surfforecaster.server.domain.wavewatch.ValidGridPointsGenerator;

public class ValidGridPointsGeneratorTest {
	@Test
	public void generateCsvFile() throws IOException {
		String file = "src/test/resources/nww3.all.grb";
		String destinationFile = "src/test/resources/grids.csv";
		ValidGridPointsGenerator.createCsvFile(file, destinationFile);
	}

	@Test
	public void test() throws IOException {
		Date date = new Date(0L);
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		System.out.println(date);
		System.out.println(date.getTimezoneOffset());
	}
}
