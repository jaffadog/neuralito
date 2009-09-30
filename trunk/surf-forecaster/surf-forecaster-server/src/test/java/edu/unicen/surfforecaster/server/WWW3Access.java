/**
 * 
 */
package edu.unicen.surfforecaster.server;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import ucar.ma2.ArrayDouble;
import ucar.ma2.ArrayFloat;
import ucar.nc2.Attribute;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;

/**
 * @author esteban
 * 
 */
public class WWW3Access {

	@Test
	public void openGribFile() {
		try {
			final File file = new File("e2.txt");
			// final NetcdfFile ncfile = NetcdfDataset
			// .open(file.getAbsolutePath());
			// final List<Variable> variables = ncfile.getVariables();
			// for (final Iterator iterator = variables.iterator(); iterator
			// .hasNext();) {
			// final Variable variable = (Variable) iterator.next();
			// final List<Attribute> attributes = variable.getAttributes();
			// System.out.println();
			// System.out.println("Var Name: " + variable.getName() + ". ");
			// for (final Iterator iterator2 = attributes.iterator(); iterator2
			// .hasNext();) {
			// final Attribute attribute = (Attribute) iterator2.next();
			// System.out.println("Attribute: " + attribute.getName()
			// + "       Val1---" + attribute.getStringValue()
			// + "       Val2--- " + attribute.getStringValue(2));
			// }
			// }
			// open = GridDataset.open(file.getAbsolutePath());
			final NetcdfFile dataFile = NetcdfDataset.open(file
					.getAbsolutePath());

			final List<Variable> variables = dataFile.getVariables();
			for (final Iterator iterator = variables.iterator(); iterator
					.hasNext();) {
				final Variable variable = (Variable) iterator.next();
				final List<Attribute> attributes = variable.getAttributes();
				System.out.println();
				System.out.println("Var Name: " + variable.getName() + ". ");
			}
			// final int NLVL = 2;
			// final int NLAT = 6;
			// final int NLON = 12;

			// These are used to construct some example data.
			final float SAMPLE_PRESSURE = 900.0f;
			// final float SAMPLE_TEMP = 9.0f;
			// final float START_LAT = 25.0f;
			// final float START_LON = -125.0f;
			// Get the latitude and longitude Variables.
			final Variable latVar = dataFile.findVariable("lat");
			if (latVar == null) {
				System.out.println("Cant find Variable latitude");
				return;
			}

			final Variable lonVar = dataFile.findVariable("lon");
			if (lonVar == null) {
				System.out.println("Cant find Variable longitude");
				return;
			}

			// Get the lat/lon data from the file.
			ArrayDouble.D1 latArray;
			ArrayDouble.D1 lonArray;

			latArray = (ArrayDouble.D1) latVar.read();
			lonArray = (ArrayDouble.D1) lonVar.read();

			// Check the coordinate variable data.
			// for (int lat = 0; lat < NLAT; lat++)
			// if (latArray.get(lat) != START_LAT + 5. * lat) {
			// System.err
			// .println("ERROR incorrect value in variable latitude");
			// }
			//
			// for (int lon = 0; lon < NLON; lon++)
			// if (lonArray.get(lon) != START_LON + 5. * lon) {
			// System.err
			// .println("ERROR incorrect value in variable longtitude");
			// }

			// Get the pressure and temperature variables.
			final Variable presVar = dataFile
					.findVariable("Primary_wave_direction");
			if (presVar == null) {
				System.out.println("Cant find Variable pressure");
				return;
			}

			// final Variable tempVar = dataFile.findVariable("temperature");
			// if (lonVar == null) {
			// System.out.println("Cant find Variable temperature");
			// return;
			// }

			final int[] shape = presVar.getShape();
			final int recLen = shape[0]; // number of times

			final int[] origin = new int[4];
			shape[0] = 1; // only one rec per read

			// loop over the rec dimension
			for (int rec = 0; rec < recLen; rec++) {
				origin[0] = rec; // read this index

				// read 3D array for that index
				ArrayFloat.D2 presArray;
				// final ArrayFloat.D3 tempArray;

				presArray = (ArrayFloat.D2) presVar.read(origin, shape)
						.reduce();
				// tempArray = (ArrayFloat.D3) tempVar.read(origin, shape)
				// .reduce();

				// now checking the value
				int count = 0;

				for (int lat = 0; lat < 40; lat++) {
					for (int lon = 0; lon < 49; lon++) {
						if (presArray.get(lat, lon) != SAMPLE_PRESSURE + count) {
							System.out.println(presArray.get(lat, lon));
							// System.err
							// .println("ERROR incorrect value in variable pressure or temperature");
						}
						count++;
					}
				}

			}

			// System.out.println(open.getInfo());
			// final List<GridDatatype> grids = open.getGrids();
			// for (final Iterator iterator = grids.iterator();
			// iterator.hasNext();) {
			// final GridDatatype gridDatatype = (GridDatatype) iterator
			// .next();
			// gridDatatype.getCoordinateSystem().getLatLon(2, 1).
			// System.out.println(gridDatatype.getInfo());
			// // gridDatatype.getYDimension().
			// }

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
