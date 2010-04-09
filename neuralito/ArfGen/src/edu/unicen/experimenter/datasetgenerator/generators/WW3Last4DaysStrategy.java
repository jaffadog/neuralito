package edu.unicen.experimenter.datasetgenerator.generators;

import java.io.Serializable;
import java.util.Arrays;
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
import edu.unicen.experimenter.datasetgenerator.generators.filters.AndFilter;
import edu.unicen.experimenter.datasetgenerator.generators.filters.DataTimeFilter;
import edu.unicen.experimenter.datasetgenerator.generators.filters.Filter;
import edu.unicen.experimenter.datasetgenerator.generators.filters.MaxWaveHeightFilter;
import edu.unicen.experimenter.util.Util;

public class WW3Last4DaysStrategy implements GenerationStrategy {
	private String strategyString;
	private String beach;
	private final String name;
	private final String description;
	private String shortDescription;
	private String[] strategyAttributes = {};
	private final String classAttribute = "visualObservation";
	private Double ww3Y;
	private Double ww3X;

	public WW3Last4DaysStrategy() {
		name = "WW3Last4DaysStrategy";
		description = "Esta estrategia usa los datos del ww3 y los combina con las observaciones visuales.\n"
				+ "Dado que del ww3 disponemos de datos cada tres horas y las observaciones son una por dia que representa \n"
				+ "la altura maxima que alcanzo una ola en el dia, las lecturas del ww3 se filtran dejando la   \n"
				+ "mayor ola captada, adicionalmente dado que las observaciones fueron tomadas durante las horas del \n"
				+ "dia en que hay luz solar, las lecturas del ww3 durante la noche tambien fueron filtradas. \n"
				+ "Esta estrategia agrupa las lecturas de ww3 del dia actual y tres dias antes y la combina con la observacion del dia actual \n";

	}

	public String getShortDescription() {
		return shortDescription;
	}

	public WW3Last4DaysStrategy(final String beach, final String[] years,
			final double ww3Y, final double ww3X) {
		this();
		this.beach = beach;
		shortDescription = "strategy[WW3Last4Days].beach[" + beach + "].years "
				+ Arrays.toString(years)
				+ ".months[1-12].height[unrestriced].ww3.1[" + ww3Y + ","
				+ ww3X + "]";
	}

	@Override
	public DataSet generateDataSet(
			final Hashtable<String, Object> dataCollection) {
		Vector<WaveWatchData> ww3DataSet = (Vector<WaveWatchData>) dataCollection
				.get("ww3Data");
		final Vector<VisualObservation> obsDataSet = (Vector<VisualObservation>) dataCollection
				.get("obsData");
		final Vector<Filter> filters = new Vector<Filter>();

		filters
				.add(new DataTimeFilter(new GregorianCalendar(0, 0, 0,
						Util.BEGINNING_HOUR, Util.BEGINNING_MINUTE),
						new GregorianCalendar(0, 0, 0, Util.END_HOUR,
								Util.END_MINUTE)));
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

	@Override
	public String getDescription() {

		return description;
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
			final Vector<VisualObservation> obsDataSet) {
		final Vector<DataSetInstance> arfDataSet = new Vector<DataSetInstance>();
		for (int i = 3; i < ww3DataSet.size(); i++) {
			/*
			 * This works because we have all days in ww3, if the strategy
			 * filter days we have to implement different
			 */
			final WaveWatchData ww3Data = ww3DataSet.get(i);
			final WaveWatchData ww3Data1DayPrev = ww3DataSet.get(i - 1);
			final WaveWatchData ww3Data2DayPrev = ww3DataSet.get(i - 2);
			final WaveWatchData ww3Data3DayPrev = ww3DataSet.get(i - 3);
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
				data.put("ww3Height!DayPrev", ww3Data1DayPrev.getHeight());
				data.put("ww3Period1DayPrev", ww3Data1DayPrev.getPeriod());
				data.put("ww3Direct!DayPrev", ww3Data1DayPrev.getDirection());
				data.put("ww3Height2DayPrev", ww3Data2DayPrev.getHeight());
				data.put("ww3Period2DayPrev", ww3Data2DayPrev.getPeriod());
				data.put("ww3Direct2DayPrev", ww3Data2DayPrev.getDirection());
				data.put("ww3Height3DayPrev", ww3Data3DayPrev.getHeight());
				data.put("ww3Period3DayPrev", ww3Data3DayPrev.getPeriod());
				data.put("ww3Direct3DayPrev", ww3Data3DayPrev.getDirection());
				data.put("visualObservation", obsData.getWaveHeight(beach));

				// Generate attributes array with the data hash keys, just first
				// time
				if (strategyAttributes.length == 0) {
					final Set<String> attributes = data.keySet();
					strategyAttributes = new String[attributes.size()];
					final Iterator<String> it = attributes.iterator();
					int j = 0;
					while (it.hasNext()) {
						strategyAttributes[j] = it.next();
						j++;
					}
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
		ww3Y = (Double) options.get("grid1Lat");
		ww3X = (Double) options.get("grid1Lon");

	}

}
