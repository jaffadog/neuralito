/**
 * 
 */
package edu.unicen.surfforecaster.server;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import ucar.ma2.Array;
import ucar.ma2.IndexIterator;
import ucar.nc2.Attribute;
import ucar.nc2.VariableSimpleIF;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.GridDatatype;
import ucar.nc2.dt.grid.GridDataset;

/**
 * @author esteban
 * 
 */
public class testGribreader {
	@Test
	public void test() {

		try {

			final File file = new File(
					"src/test/resources/multi_1.glo_30m.DIRPW.grb2");

			final GridDataset gridDataSet = GridDataset.open(file
					.getAbsolutePath());
			final GridDatatype pwd = gridDataSet
					.findGridDatatype("Primary_wave_direction");
			System.out.println(pwd.getInfo());
			final VariableSimpleIF dataVariable = gridDataSet
					.getDataVariable("time");
			dataVariable.getName();
			// final List<GridDatatype> grids = gridDataSet.getGrids();
			// for (final Iterator iterator = grids.iterator();
			// iterator.hasNext();) {
			// final GridDatatype gridDatatype = (GridDatatype) iterator
			// .next();
			// System.out.println(gridDatatype.getName());
			// }
			final List<Attribute> attributes = pwd.getAttributes();
			for (final Iterator iterator = attributes.iterator(); iterator
					.hasNext();) {
				final Attribute attribute = (Attribute) iterator.next();
				System.out.println(attribute.getName());
				System.out.println(attribute.getStringValue());
				System.out.println("==============");
			}

			final GridCoordSystem pwdGcs = pwd.getCoordinateSystem();
			final int[] result = null;
			// Get index value for Lat 30.0 and Lon 179
			final int[] idx = pwdGcs.findXYindexFromLatLon(30.0, 179.0, result);
			// int[] idx = pwdGcs.findXYindexFromLatLon( 90.0, 0.0, result );
			// Extract data value for time 0, no Vert index, Lat index, Lon
			// index
			for (int i = 0; i < 37; i++) {
				final Array data = pwd.readDataSlice(i, -1, idx[1], idx[0]);
				// Another option: read all data values for timeIndex = 0
				// Array data = pwd.readVolumeData( 0 );
				final IndexIterator iter = data.getIndexIterator();
				while (iter.hasNext()) {
					final float val = iter.getFloatNext();
					System.out.println("Primary_wave_direction = " + val);
				}
			}
			System.out.println("Success");
		} catch (final Exception exc) {
			exc.printStackTrace();
		}
	}
}
