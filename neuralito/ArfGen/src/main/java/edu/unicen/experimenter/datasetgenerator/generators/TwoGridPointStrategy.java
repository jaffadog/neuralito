package edu.unicen.experimenter.datasetgenerator.generators;

import java.io.Serializable;
import java.util.Collection;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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

public class TwoGridPointStrategy implements GenerationStrategy {
	private final String name = "TwoGridPointStrategy";
	private final String description = "Esta estrategia usa los datos del ww3 y los combina con las observaciones visuales.\n"
			+ "Dado que del ww3 disponemos de datos cada tres horas y las observaciones son una por dia que representa \n"
			+ "la altura maxima que alcanzo una ola en el dia, las lecturas del ww3 se filtran dejando la   \n"
			+ "mayor ola captada, adicionalmente dado que las observaciones fueron tomadas durante las horas del \n"
			+ "dia en que hay luz solar, las lecturas del ww3 durante la noche tambien fueron filtradas";
	private String strategyString;
	private String beach;
	private String shortDescription;
	private String[] strategyAttributes = {};
	private final String classAttribute = "visualObservation";
	private Double ww1Y;
	private Double ww1X;
	private Double ww2Y;
	private Double ww2X;
	private Map<String, Serializable> options;

	public TwoGridPointStrategy() {

	}

	public String getShortDescription() {
		return shortDescription;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataSet generateDataSet(
			final Hashtable<String, Object> dataCollection) {
		// Get wavewatch data loader.
		final WaveWatchLoader wwLoader = (WaveWatchLoader) dataCollection
				.get("ww3Data");

		// Read data for grid point 1
		Vector<WaveWatchData> ww3DataSet = new Vector(wwLoader
				.getWaveWatchData(ww1Y, ww1X));

		// Read data for grid point 2
		final Vector<WaveWatchData> ww32 = new Vector(wwLoader
				.getWaveWatchData(ww2Y, ww2X));

		// Get visual observations
		final Vector<VisualObservation> obsDataSet = new Vector(
				(Collection) dataCollection.get("obsData"));

		// Combine visual observations and wavewatch data
		final Vector<Filter> filters = new Vector<Filter>();

		filters
				.add(new DataTimeFilter(new GregorianCalendar(0, 0, 0,
						Util.BEGINNING_HOUR, Util.BEGINNING_MINUTE),
						new GregorianCalendar(0, 0, 0, Util.END_HOUR,
								Util.END_MINUTE)));
		filters.add(new MaxWaveHeightFilter());
		final Filter compuestFilter = new AndFilter(filters);
		ww3DataSet = (Vector<WaveWatchData>) compuestFilter
				.executeFilter(ww3DataSet);

		final Vector<DataSetInstance> arfDataArr = mergeData(ww3DataSet, ww32,
				obsDataSet);
		final DataSet dataSet = new DataSet(name, description, arfDataArr,
				strategyAttributes, classAttribute, this);
		strategyString(filters, strategyAttributes, classAttribute);

		return dataSet;
	}

	@Override
	public String getDescription() {

		return "Same as no buoy strtegy but using two ww3 grid points";
	}

	@Override
	public String getName() {

		return name;
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

	private Vector<DataSetInstance> mergeData(
			final Vector<WaveWatchData> ww3DataSet,
			final Vector<WaveWatchData> ww32,
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
				final WaveWatchData ww3Data2 = ww32.elementAt(ww3DataSet
						.indexOf(ww3Data));
				data.put("ww3Height", ww3Data.getHeight());
				data.put("ww3Period", ww3Data.getPeriod());
				data.put("ww3Direction", ww3Data.getDirection());
				data.put("ww3Height2", ww3Data2.getHeight());
				data.put("ww3Period2", ww3Data2.getPeriod());
				data.put("ww3Direction2", ww3Data2.getDirection());
				data.put("visualObservation", obsData.getWaveHeight(beach));

				if (strategyAttributes.length == 0) {
					final Set<String> attributes = data.keySet();
					strategyAttributes = new String[attributes.size()];
					final Iterator<String> it = attributes.iterator();
					int i = 0;
					while (it.hasNext()) {
						final String attribute = it.next();
						if (!attribute.equals("visualObservation")) {
							strategyAttributes[i] = attribute;
							i++;
						}
					}
					// Visual observation is the class attribute and should be
					// the last in the list.
					strategyAttributes[i] = "visualObservation";
				}
				final DataSetInstance arfData = new DataSetInstance(data);
				arfData.setDate(ww3Data.getDate());
				arfDataSet.add(arfData);

			}
		}

		return arfDataSet;
	}

	@Override
	public String getBeach() {
		return beach;
	}

	/**
	 * @see edu.unicen.experimenter.datasetgenerator.generators.GenerationStrategy#setOptions(java.util.Map)
	 */
	@Override
	public void setOptions(final Map<String, Serializable> options) {

		beach = (String) options.get("beach");
		ww1Y = (Double) options.get("grid1Lat");
		ww1X = (Double) options.get("grid1Lon");
		ww2Y = (Double) options.get("grid2Lat");
		ww2X = (Double) options.get("grid2Lon");
		shortDescription = "strategy[nobuoy].beach[" + beach
				+ ".months[1-12].height[unrestriced].ww3[" + ww1Y + "," + ww1X
				+ "]";
		this.options = options;
	}

	/**
	 * @see edu.unicen.experimenter.datasetgenerator.generators.GenerationStrategy#getStrategyOptions()
	 */
	@Override
	public Map<String, Serializable> getStrategyOptions() {

		return options;
	}

}
