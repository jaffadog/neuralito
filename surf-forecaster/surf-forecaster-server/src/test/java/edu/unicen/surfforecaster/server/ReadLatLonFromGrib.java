package edu.unicen.surfforecaster.server;

import java.io.File;
import java.util.Date;

import ucar.ma2.Array;
import ucar.ma2.IndexIterator;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.GridDatatype;
import ucar.nc2.dt.grid.GridDataset;

public class ReadLatLonFromGrib {
	public static void main(final String[] args) {
		final File file = new File(
				"src/test/resources/multi_1.glo_30m.all.grb2");
		final float lat = 39;
		final float lon = 179;
		final long startTime = new Date().getTime();
		// Read the values for the given location
		readValuesForLocation(file, lat, lon);

		final long endTime = new Date().getTime();
		// Calculate reading elapsed time(seconds).
		final long elapsedTime = (endTime - startTime) / 1000;
		System.out.println("Time elapsed for reading "
				+ parameters.values().length
				+ " parameters, for 31 times, in one location: " + elapsedTime
				+ " secs.");
	}

	private static enum parameters {
		Direction_of_swell_waves, Direction_of_wind_waves, Mean_period_of_swell_waves, Mean_period_of_wind_waves, Primary_wave_direction, Primary_wave_mean_period, Significant_height_of_combined_wind_waves_and_swell, Significant_height_of_swell_waves, Significant_height_of_wind_waves, Wind_direction_from_which_blowing, Wind_speed
	};

	private static void readValuesForLocation(final File file, final float lat,
			final float lon) {
		System.out.println("Reading grib values for lat: " + lat + " and lon: "
				+ lon);
		try {
			// For each parameter
			for (final Enum parameter : parameters.values()) {
				final GridDataset gridDataSet = GridDataset.open(file
						.getAbsolutePath());
				// For each time
				for (int i = 0; i < 31; i++) {
					final GridDatatype pwd = gridDataSet
							.findGridDatatype(parameter.name());
					final GridCoordSystem pwdGcs = pwd.getCoordinateSystem();
					final int[] result = null;
					// Get index value for lat/lon
					final int[] idx = pwdGcs.findXYindexFromLatLon(lat, lon,
							result);
					// Reads values for the given (location,parameter,time)
					final Array data = pwd.readDataSlice(i, -1, idx[1], idx[0]);
					final IndexIterator iter = data.getIndexIterator();
					while (iter.hasNext()) {
						// Obtain the value
						final float val = iter.getFloatNext();
					}
				}
			}
			System.out.println("Finish reading values");
		} catch (final Exception exc) {
			exc.printStackTrace();
		}
	}
}