package edu.unicen.experimenter.datasetgenerator.generators;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.Vector;

import edu.unicen.experimenter.datasetgenerator.DataSet;
import edu.unicen.experimenter.datasetgenerator.DataSetInstance;
import edu.unicen.experimenter.datasetgenerator.data.visualobservation.VisualObservation;
import edu.unicen.experimenter.datasetgenerator.data.wavewatch.WaveWatchData;
import edu.unicen.experimenter.datasetgenerator.data.wavewatch.WaveWatchLoader;
import edu.unicen.experimenter.datasetgenerator.generators.filters.AndFilter;
import edu.unicen.experimenter.datasetgenerator.generators.filters.DataTimeFilter;
import edu.unicen.experimenter.datasetgenerator.generators.filters.Filter;
import edu.unicen.experimenter.datasetgenerator.generators.filters.MaxWaveHeightFilter;
import edu.unicen.experimenter.util.Util;

public class OneGridPointStrategy implements GenerationStrategy {

	private final String name = "NoBuoyStrategy";

	private final String description = "Esta estrategia usa los datos del ww3 y los combina con las observaciones visuales.\n"
			+ "Dado que del ww3 disponemos de datos cada tres horas y las observaciones son una por dia que representa \n"
			+ "la altura maxima que alcanzo una ola en el dia, las lecturas del ww3 se filtran dejando la   \n"
			+ "mayor ola captada, adicionalmente dado que las observaciones fueron tomadas durante las horas del \n"
			+ "dia en que hay luz solar, las lecturas del ww3 durante la noche tambien fueron filtradas";

	private String strategyString;

	private String beach = null;
	private String[] strategyAttributes = {};
	private final String classAttribute = "visualObservation";
	/**
	 * Latitude of the wavewatch grid point to use.
	 */
	private Double ww3Y;
	/**
	 * Longitude of the wavewatch grid point to use.
	 */
	private Double ww3X;

	public String getBeach() {
		return beach;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	@Override
	public DataSet generateDataSet(
			final Hashtable<String, Object> dataCollection) {
		final WaveWatchLoader wwLoader = (WaveWatchLoader) dataCollection
				.get("ww3Data");
		Vector<WaveWatchData> ww3DataSet = (Vector<WaveWatchData>) wwLoader
				.getWaveWatchData(ww3Y, ww3X);

		final Vector<VisualObservation> obsDataSet = new Vector(
				(Collection) dataCollection.get("obsData"));

		final Vector<Filter> filters = new Vector<Filter>();
		final Calendar from = new GregorianCalendar(0, 0, 0,
				Util.BEGINNING_HOUR, Util.BEGINNING_MINUTE);
		final Calendar to = new GregorianCalendar(0, 0, 0, Util.END_HOUR,
				Util.END_MINUTE);
		from.setTimeZone(TimeZone.getTimeZone("UTC"));
		to.setTimeZone(TimeZone.getTimeZone("UTC"));
		filters.add(new DataTimeFilter(from, to));
		// filters.add(new DataWaveDirectionFilter(Util.minDirectionDegree,
		// Util.maxDirectionDegree));
		filters.add(new MaxWaveHeightFilter());
		final Filter compuestFilter = new AndFilter(filters);

		ww3DataSet = (Vector<WaveWatchData>) compuestFilter
				.executeFilter(ww3DataSet);

		final Vector<DataSetInstance> arfDataArr = mergeData(ww3DataSet,
				obsDataSet);
		final DataSet dataSet = new DataSet(name, description, arfDataArr,
				strategyAttributes, classAttribute, this);
		strategyString(filters, strategyAttributes, classAttribute);

		return dataSet;
	}

	private Vector<DataSetInstance> mergeData(
			final Vector<WaveWatchData> ww3DataSet,
			final Vector<VisualObservation> obsDataSet) {

		final Vector<DataSetInstance> arfDataSet = new Vector<DataSetInstance>();
		for (final Enumeration<WaveWatchData> e = ww3DataSet.elements(); e
				.hasMoreElements();) {
			final WaveWatchData ww3Data = e.nextElement();
			VisualObservation obsData = null;
			for (final Enumeration<VisualObservation> f = obsDataSet.elements(); f
					.hasMoreElements();) {
				final VisualObservation fObsData = f.nextElement();
				if (fObsData.equalsDate(ww3Data.getDate())) {
					obsData = fObsData;
					break;
				}
			}

			if (obsData != null) {
				final Hashtable<String, Double> data = new Hashtable<String, Double>();
				data.put("ww3Height", ww3Data.getHeight());
				data.put("ww3Period", ww3Data.getPeriod());
				data.put("ww3Direction", ww3Data.getDirection());
				data.put("visualObservation", obsData.getWaveHeight(beach));

				if (strategyAttributes.length == 0) {
					final Set<String> attributes = data.keySet();
					strategyAttributes = new String[attributes.size()];
					final Iterator<String> it = attributes.iterator();
					int i = 0;
					while (it.hasNext()) {
						strategyAttributes[i] = it.next();
						i++;
					}
				}

				final DataSetInstance arfData = new DataSetInstance(data);
				arfData.setDate(ww3Data.getDate());
				arfDataSet.add(arfData);
			}
		}

		return arfDataSet;
	}

	public void strategyString(final Vector<Filter> ww3Filters,
			final String[] strategyAttributes, final String classAttribute) {

		String text = "";
		text = name.toUpperCase() + "\n\n\t" + description + "\n\n" + "Beach: "
				+ beach + "\n\n";

		if (ww3Filters.size() > 0) {
			text += "WW3 FILTERS:\n";
			for (final Enumeration<Filter> e = ww3Filters.elements(); e
					.hasMoreElements();) {
				final Filter filter = e.nextElement();
				text += filter.toString();
			}
			text += "\n";
		}

		if (strategyAttributes.length > 0) {
			text += "INSTANCE ATTRIBUTES:\n\t";
			for (int i = 0; i < strategyAttributes.length; i++) {
				text += strategyAttributes[i] + ", ";
			}
			text += "\n\nCLASS ATTRIBUTE:\n\t" + classAttribute + "\n\n";
		}

		strategyString = text;

	}

	@Override
	public String toString() {
		return strategyString;
	}

	/**
	 * @see edu.unicen.experimenter.datasetgenerator.generators.GenerationStrategy#setOptions(java.util.Map)
	 */
	@Override
	public void setOptions(final Map<String, Serializable> options) {
		beach = (String) options.get("beach");
		ww3Y = (Double) options.get("grid1Lat");
		ww3X = (Double) options.get("grid1Lon");
		// shortDescription = "strategy[nobuoy].beach[" + beach
		// + ".months[1-12].height[unrestriced].ww3[" + ww3Y + "," + ww3X
		// + "]";
	}

}
