package edu.unicen.experimenter.datasetgenerator.data.wavewatch;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Vector;

import net.sourceforge.jgrib.GribFile;

import org.apache.log4j.Logger;

import edu.unicen.experimenter.util.Util;

/**
 * Class to decode grib encoded data and import it as WaveWatchData. The
 * imported data is serialized into .dat files.
 * 
 * @author esteban
 * 
 */
public class WaveWatchLoader {

	private static Logger logger = Logger.getLogger(WaveWatchLoader.class);
	private final String[] availableYears = { "1997", "1998", "1999", "2000",
			"2001", "2002", "2003", "2004" };

	/**
	 * Obtain wave watch data from serialized data.
	 * 
	 * @param years
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public Collection getWaveWatchData(final String[] years,
			final double latitude, final double longitude) {
		final Vector wwData = new Vector();
		for (int i = 0; i < years.length; i++) {
			final String fileName = ".//files//ww3//ww3_" + years[i] + "Lat("
					+ latitude + ")Long(" + longitude + ").data";
			// Read from disk using FileInputStream.
			FileInputStream f_in;
			try {
				f_in = new FileInputStream(fileName);
				// Read object using ObjectInputStream.
				ObjectInputStream obj_in;
				obj_in = new ObjectInputStream(f_in);
				// Read an object.
				Object obj;
				obj = obj_in.readObject();
				final Vector vec = (Vector) obj;
				wwData.addAll(vec);
				// return vec;
			} catch (final Exception e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return wwData;
	}

	/**
	 *Loads all the gribs for the specified year and for the specified Coords,
	 * and save them into a .Data file with the corresponding label
	 */
	public static void loadDataFromGrib(final String[] years,
			final double latitude, final double longitude) {
		for (int j = 0; j < years.length; j++) {
			String day;
			final Collection waveData = new Vector();
			for (int i = 1; i <= 12; i++) {
				if (i < 10) {
					day = "0" + Integer.toString(i);
				} else {
					day = Integer.toString(i);
				}
				final WaveWatchLoader m = new WaveWatchLoader();
				final WaveWatchLoader.WaveWatchDecoder reader = m.new WaveWatchDecoder(
						"files/WW3.gribs/nww3.hs." + years[j] + day + ".grb",
						"files/WW3.gribs/nww3.tp." + years[j] + day + ".grb",
						"files/WW3.gribs/nww3.dp." + years[j] + day + ".grb",
						"files/WW3.gribs/nww3.wind." + years[j] + day + ".grb");
				waveData.addAll(reader.decodeWaveWatchDataFor(latitude,
						longitude));

			}

			try {
				// Use a FileOutputStream to send data to a file
				// called myobject.data.

				final FileOutputStream f_out = new FileOutputStream(
						".//files//ww3//ww3_" + years[j] + "Lat(" + latitude
								+ ")Long(" + longitude + ").data");

				// Use an ObjectOutputStream to send object data to the
				// FileOutputStream for writing to disk.
				final ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
				// Pass our object to the ObjectOutputStream's
				// writeObject() method to cause it to be written out
				// to disk.
				obj_out.writeObject(waveData);
			} catch (final IOException e) {
				e.printStackTrace();
			}
			logger.info("Added year: " + years[j]);

		}
	}

	/**
	 * Decoder for NOAA WaveWatch grib(Grib1) encoded files.
	 * 
	 * @author esteban
	 * 
	 */
	public class WaveWatchDecoder {

		private GribFile gribHeight;
		private GribFile gribPeriod;
		private GribFile gribDirection;
		private final Logger logger = Logger.getLogger(this.getClass());
		private GribFile gribWind;

		/**
		 * Initializes the decoder with the files to decode.
		 * 
		 * @param heightsFile
		 *            the grib file containing the wave heights.
		 * @param periodFile
		 *            the grib file containing the wave periods.
		 * @param directionFile
		 *            the grib file containing the wave directions.
		 * @param windFile
		 *            the grib file containing the winds.
		 */
		public WaveWatchDecoder(final String heightsFile,
				final String periodFile, final String directionFile,
				final String windFile) {
			try {
				gribHeight = new GribFile(heightsFile);
				gribPeriod = new GribFile(periodFile);
				gribDirection = new GribFile(directionFile);
				gribWind = new GribFile(windFile);
				logger.info("Records Count:" + gribDirection.getRecordCount()
						+ "(Direction)" + "//" + gribHeight.getRecordCount()
						+ "(Height)" + "//" + gribPeriod.getRecordCount()
						+ "(Period)" + "//" + gribWind.getRecordCount()
						+ "(Wind)");
				// validate();
			} catch (final Exception e) {
				e.printStackTrace();
			}

		}

		/**
		 * Decode the wave watch data for the given geographic point.
		 * 
		 * @param latitude
		 *            The latitude to extract the grib data
		 * @param longitude
		 *            The longitud to extract the grib data
		 * @return Collection of WaveWatchData at the given latitud longitud.
		 */
		public Collection<WaveWatchData> decodeWaveWatchDataFor(
				final double latitude, final double longitude) {

			final Collection dataSet = new Vector();
			// int latIndex = 161;
			// int longIndex = 56;
			final Integer latIndex = findIndexForLatitude(latitude);
			final Integer longIndex = findIndexForLongitud(longitude);
			for (int i = 1; i < gribHeight.getRecordCount(); i++) {
				final WaveWatchData data = new WaveWatchData();
				data.setLatitude(latitude);
				data.setLongitude(longitude);
				try {
					data.setTime(gribHeight.getRecord(i).getTime());
					data.setDirection(gribDirection.getRecord(i).getValue(
							longIndex, latIndex));
					data.setHeight(gribHeight.getRecord(i).getValue(longIndex,
							latIndex));
					data.setPeriod(gribPeriod.getRecord(i).getValue(longIndex,
							latIndex));
					final double windU = gribWind.getRecord(2 * i - 1)
							.getValue(longIndex, latIndex);
					final double windV = gribWind.getRecord(2 * i).getValue(
							longIndex, latIndex);
					//				
					// logger.info("Index U: " +
					// gribWind.getRecord(2*i-1).getDescription() +" " + windU);
					// logger.info("Index V: " +
					// gribWind.getRecord(2*i).getDescription() +" " + windV);
					// // logger.info(gribWind.getRecord(2*i-1).getTime());
					// // logger.info(gribWind.getRecord(2*i).getTime());
					data.setWind(windU, windV);
					dataSet.add(data);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
			return dataSet;
		}

		/**
		 * Obtain the index for the given latitude.
		 * 
		 * @param latitude
		 * @return
		 */
		private Integer findIndexForLatitude(final double latitude) {
			try {
				final double[] yCoords = gribHeight.getRecord(1).getGDS()
						.getYCoords();
				for (int i = 1; i < yCoords.length; i++) {
					if (yCoords[i] == latitude) {
						logger.info("Obtained Index:" + i + " for latitude:"
								+ latitude);
						return i;

					}
				}
			} catch (final Exception e) {
			}
			throw new InvalidParameterException(
					"The given latitude is not defined for the given Grib File");
		}

		/**
		 * Obtain the index for the given longitude.
		 * 
		 * @param latitude
		 * @return
		 */
		private Integer findIndexForLongitud(final double longitud) {
			try {
				final double[] xCoords = gribHeight.getRecord(1).getGDS()
						.getXCoords();
				for (int i = 1; i < xCoords.length; i++) {
					if (xCoords[i] == longitud) {
						logger.info("Obtained Index:" + i + " for longitud:"
								+ longitud);
						return i;
					}
				}
			} catch (final Exception e) {
			}
			throw new InvalidParameterException(
					"The given longitud is not defined for the given Grib File");
		}
	}

	/**
	 * Routine to decode grib wave into WaveWatchData class and serialize it to
	 * disk. {@link WaveWatchData}. Each time WaveWatchData is modified this
	 * routine should be invoked.
	 * 
	 * @author esteban
	 * 
	 */
	public static void main(final String[] args) {
		System.out.println(" ");
		final WaveWatchLoader manager = new WaveWatchLoader();
		final String[] years = new String[] { "1997", "1998", "1999", "2000",
				"2001", "2002", "2003", "2004" };
		manager.loadDataFromGrib(years, Util.SOUTH, Util.EAST);
		manager.loadDataFromGrib(years, Util.SOUTH, Util.WEST);
		manager.loadDataFromGrib(years, Util.NORTH, Util.EAST);
		manager.loadDataFromGrib(years, Util.NORTH, Util.WEST);
	}

	/**
	 * @param ww3y
	 * @param ww3x
	 * @return
	 */
	public Collection<WaveWatchData> getWaveWatchData(final Double latitude,
			final Double longitude) {

		return this.getWaveWatchData(availableYears, latitude, longitude);
	}

}
