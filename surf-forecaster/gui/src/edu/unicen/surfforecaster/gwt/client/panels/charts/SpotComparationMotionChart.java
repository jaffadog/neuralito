package edu.unicen.surfforecaster.gwt.client.panels.charts;

import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.MotionChart;
import com.google.gwt.visualization.client.visualizations.MotionChart.Options;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.WaveWatchParameter;
import edu.unicen.surfforecaster.gwt.client.dto.ForecastGwtDTO;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;

public class SpotComparationMotionChart implements ISurfForecasterChart {

	private Map<Integer, Map<String, List<ForecastGwtDTO>>> spotsLatestForecasts = null;
	private List<Integer> spotsIds = null;
	private List<String> spotsNames = null;
	private List<String> forecastersNames = null;
	
	public SpotComparationMotionChart(final Map<Integer, Map<String, List<ForecastGwtDTO>>> spotsLatestForecasts, final List<Integer> spotsIds, 
			final List<String> spotsNames, final List<String> forecastersNames) {
		this.spotsIds = spotsIds;
		this.spotsLatestForecasts = spotsLatestForecasts;
		this.spotsNames = spotsNames;
		this.forecastersNames = forecastersNames;
	}
	
	@Override
	public void render(final Panel parent) {
		VisualizationUtils.loadVisualizationApi("1.1",
	            new Runnable() {
	              public void run() {
		          	  MotionChart chart = new MotionChart(createTable(), createOptions());
	          	      parent.add(chart);
	              }				
        }, MotionChart.PACKAGE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Options createOptions() {
		Options options = Options.create();
		options.setHeight(300);
	    options.setWidth(950);
		return options;
	}

	@Override
	public DataTable createTable() {
		DataTable data = DataTable.create();	    
	    data.addColumn(ColumnType.STRING, "Spot");
	    data.addColumn(ColumnType.NUMBER, "Hours");
	    data.addColumn(ColumnType.NUMBER, GWTUtils.LOCALE_CONSTANTS.wave_height());
	    //data.addColumn(ColumnType.NUMBER, GWTUtils.LOCALE_CONSTANTS.wave_period());
	    
	    for (int spotIndex = 0; spotIndex < spotsIds.size(); spotIndex++) {
	    	Integer spotId = spotsIds.get(spotIndex);
	    	String spotName = spotsNames.get(spotIndex);
	    	String forecasterName = this.forecastersNames.get(spotIndex);
	    	List<ForecastGwtDTO> forecasts = spotsLatestForecasts.get(spotId).get(forecasterName);
	    	if (data.getNumberOfRows() == 0)
	    		data.addRows(spotsIds.size() * forecasts.size());
	    		
	    	for (int forecastIndex = 0; forecastIndex < forecasts.size(); forecastIndex++) {
				ForecastGwtDTO forecastDTO = forecasts.get(forecastIndex);
				//TODO generar las unidades en que se ve el sitio como alguna setting de usuario o usando cookies o algo y emprolijar la manera de levantarlo
				Unit heightUnitTarget = Unit.Meters;

				//wave height
				String waveHeight = forecastDTO.getMap().get(WaveWatchParameter.COMBINED_SWELL_WIND_WAVE_HEIGHT_V2.getValue()).getValue();
				try {
					waveHeight = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveHeight, Unit.Meters, heightUnitTarget));
				} catch (NeuralitoException e) {
					// TODO ver como manejar esta exvepcion de conversion de unidades
					e.printStackTrace();
				}
				data.setValue(spotIndex * forecasts.size() + forecastIndex, 0, spotName);
	    		data.setValue(spotIndex * forecasts.size() + forecastIndex, 1, forecastIndex);
	    		data.setValue(spotIndex * forecasts.size() + forecastIndex, 2, new Double(waveHeight));
			}
	    }
	    
		return data;
	}
}
