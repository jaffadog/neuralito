package edu.unicen.surfforecaster.server;

import java.io.IOException;

import org.junit.Test;

import edu.unicen.surfforecaster.server.domain.ValidGridPointsGenerator;

public class ValidGridPointsGeneratorTest {
	@Test
	public void generateCsvFile() throws IOException {
		String file = "src/test/resources/multi_1.glo_30m.all.grb2";
		String destinationFile = "src/test/resources/grids.csv";
		ValidGridPointsGenerator.createCsvFile(file, destinationFile);
	}
}
