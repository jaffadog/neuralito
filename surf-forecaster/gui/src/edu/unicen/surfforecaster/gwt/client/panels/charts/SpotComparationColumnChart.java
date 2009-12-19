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
import edu.unicen.surfforecaster.common.services.dto.WW3Parameter;
import edu.unicen.surfforecaster.gwt.client.dto.ForecastGwtDTO;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;

public class SpotComparationColumnChart implements ISurfForecasterChart {

	private Map<Integer, Map<String, List<ForecastGwtDTO>>> spotsLatestForecasts = null;
	private List<Integer> spotsIds = null;
	private List<String> spotsNames = null;
	private List<String> forecastersNames = null;
	
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
		Options options = Options.create();
		options.setHeight(300);
		options.setWidth(950);
		options.set3D(true);
		options.setShowCategories(true);
		options.setEnableTooltip(true);
		options.setMin(0);
		options.setMax(20);
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
  	    data.setValue(0, 0, "Ahora");
  	    for (int index = 1; index < data.getNumberOfRows(); index++) {
  	    	data.setValue(index, 0, "+" + index * 3 + " horas");
		}
  	    
	    for (int spotIndex = 0; spotIndex < spotsIds.size(); spotIndex++) {
	    	Integer spotId = spotsIds.get(spotIndex);
	    	String forecasterName = this.forecastersNames.get(spotIndex);
	    	List<ForecastGwtDTO> forecasts = spotsLatestForecasts.get(spotId).get(forecasterName);
	    	for (int forecastIndex = 0; forecastIndex < data.getNumberOfRows(); forecastIndex++) {
				ForecastGwtDTO forecastDTO = forecasts.get(forecastIndex);
				//TODO generar las unidades en que se ve el sitio como alguna setting de usuario o usando cookies o algo y emprolijar la manera de levantarlo
				Unit heightUnitTarget = Unit.Meters;
				Unit speedUnitTarget = Unit.KilometersPerHour;
				Unit directionUnitTarget = Unit.Degrees;
				Unit periodUnitTarget = Unit.Seconds;
				
				//wave height
				String waveHeight = forecastDTO.getMap().get(WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.toString()).getValue();
				//wind speed
				String windSpeed = forecastDTO.getMap().get(WW3Parameter.WIND_SPEED.toString()).getValue();
				//Wave direccion
				String waveDirection = forecastDTO.getMap().get(WW3Parameter.PRIMARY_WAVE_DIRECTION.toString()).getValue();
				//Wave period
				String wavePeriod = forecastDTO.getMap().get(WW3Parameter.PRIMARY_WAVE_PERIOD.toString()).getValue();
				try {
					windSpeed = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(windSpeed, Unit.KilometersPerHour, speedUnitTarget));
					waveHeight = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveHeight, Unit.Meters, heightUnitTarget));
					waveDirection = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveDirection, Unit.Degrees, directionUnitTarget));
					wavePeriod = NumberFormat.getFormat("###").format(UnitConverter.convertValue(wavePeriod, Unit.Seconds, periodUnitTarget));
				} catch (NeuralitoException e) {
					// TODO ver como manejar esta exvepcion de conversion de unidades
					e.printStackTrace();
				}
				
	    		data.setValue(forecastIndex, spotIndex + 1, new Double(waveHeight));
			}
	    }
  	    
		return data;
	}
}
