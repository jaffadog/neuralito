package edu.unicen.surfforecaster.gwt.client.panels.charts;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.ColumnChart;
import com.google.gwt.visualization.client.visualizations.ColumnChart.Options;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.WaveWatchParameter;
import edu.unicen.surfforecaster.gwt.client.dto.ForecastGwtDTO;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;
import edu.unicen.surfforecaster.gwt.client.utils.UnitTranslator;

public class SpotComparationColumnChart implements ISurfForecasterChart {

	private Map<Integer, Map<String, List<ForecastGwtDTO>>> spotsLatestForecasts = null;
	private List<Integer> spotsIds = null;
	private List<String> spotsNames = null;
	private List<String> forecastersNames = null;
	private String currentForecasterName = null;
	
	public SpotComparationColumnChart(final Map<Integer, Map<String, List<ForecastGwtDTO>>> spotsLatestForecasts, final List<Integer> spotsIds, 
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
		          	  ColumnChart chart = new ColumnChart(createTable(), createOptions());
		          	  parent.add(chart);
	              }
        }, ColumnChart.PACKAGE);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Options createOptions() {
		//TODO generar las unidades en que se ve el sitio como alguna setting de usuario o usando cookies o algo y emprolijar la manera de levantarlo
		Unit heightUnitTarget = Unit.Meters;
		
		Options options = Options.create();
		options.setHeight(300);
		options.setWidth(950);
		options.set3D(true);
		options.setShowCategories(true);
		options.setEnableTooltip(true);
		options.setMin(0);
		options.setMax(20);
		options.setTitleY(GWTUtils.LOCALE_CONSTANTS.wave_height() + " (" + UnitTranslator.getUnitAbbrTranlation(heightUnitTarget) + ")");
		return options;
	}

	@Override
	public DataTable createTable() {
		DataTable data = DataTable.create();
		
		data.addColumn(ColumnType.STRING, "Date");
  	    
		Iterator<String> i = spotsNames.iterator();
  	    while (i.hasNext()) {
  	    	String spotName = i.next();
  	    	data.addColumn(ColumnType.NUMBER, spotName);
  	    }
  	    
  	    data.addRows(4);
  	    data.setValue(0, 0, GWTUtils.LOCALE_CONSTANTS.now());
  	    for (int index = 1; index < data.getNumberOfRows(); index++) {
  	    	data.setValue(index, 0, "+" + index * 3 + " " + GWTUtils.LOCALE_CONSTANTS.hours());
		}
  	    
	    for (int spotIndex = 0; spotIndex < spotsIds.size(); spotIndex++) {
	    	Integer spotId = spotsIds.get(spotIndex);
	    	String forecasterName = this.forecastersNames.get(spotIndex);
	    	this.currentForecasterName = forecasterName;
	    	List<ForecastGwtDTO> forecasts = spotsLatestForecasts.get(spotId).get(forecasterName);
	    	int currentForecastIndex = GWTUtils.getCurrentForecastIndex(forecasts);
	    	if (currentForecastIndex != -1) {
		    	for (int forecastIndex = currentForecastIndex; forecastIndex < currentForecastIndex + data.getNumberOfRows(); forecastIndex++) {
		    		ForecastGwtDTO forecastDTO = null;
		    		if (forecasts.size() > forecastIndex){
		    			forecastDTO = forecasts.get(forecastIndex);
						//TODO generar las unidades en que se ve el sitio como alguna setting de usuario o usando cookies o algo y emprolijar la manera de levantarlo
						Unit heightUnitTarget = Unit.Meters;
						
						//wave height
						String waveHeight = this.getWaveHeight(forecastDTO);
						try {
							waveHeight = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveHeight, Unit.Meters, heightUnitTarget));
						} catch (NeuralitoException e) {
							// TODO ver como manejar esta exvepcion de conversion de unidades
							e.printStackTrace();
						}
						
			    		data.setValue(forecastIndex - currentForecastIndex, spotIndex + 1, new Double(waveHeight));
		    		}
				}
	    	}
	    }
  	    
		return data;
	}
	
	/**
	 * Returns the waveHeight if the forecast is from ww3 forecaster or the improved wave height if the forecast is from a skilled predictor 
	 * @param forecastDTO
	 * @return String waveHeight
	 */
	private String getWaveHeight(ForecastGwtDTO forecastDTO) {
		if (this.currentForecasterName.equals(GWTUtils.WW3_FORECASTER_NAME))
			return forecastDTO.getMap().get(WaveWatchParameter.COMBINED_SWELL_WIND_WAVE_HEIGHT_V2.getValue()).getValue();
		else
			return forecastDTO.getMap().get("improvedWaveHeight").getValue();
	}
}
