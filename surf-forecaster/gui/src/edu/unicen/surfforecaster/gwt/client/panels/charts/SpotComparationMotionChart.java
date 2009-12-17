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
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.WW3Parameter;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;

public class SpotComparationMotionChart implements ISurfForecasterChart {

	private Map<Integer, Map<String, List<ForecastDTO>>> spotsLatestForecasts = null;
	private List<Integer> spotsIds = null;
	private List<String> spotsNames = null;
	private List<String> forecastersNames = null;
	
	public SpotComparationMotionChart(final Map<Integer, Map<String, List<ForecastDTO>>> spotsLatestForecasts, final List<Integer> spotsIds, 
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
	    data.addColumn(ColumnType.NUMBER, "Wave Height");
	    
	    for (int spotIndex = 0; spotIndex < spotsIds.size(); spotIndex++) {
	    	Integer spotId = spotsIds.get(spotIndex);
	    	String spotName = spotsNames.get(spotIndex);
	    	String forecasterName = this.forecastersNames.get(spotIndex);
	    	List<ForecastDTO> forecasts = spotsLatestForecasts.get(spotId).get(forecasterName);
	    	if (data.getNumberOfRows() == 0)
	    		data.addRows(spotsIds.size() * forecasts.size());
	    		
	    	for (int forecastIndex = 0; forecastIndex < forecasts.size(); forecastIndex++) {
				ForecastDTO forecastDTO = forecasts.get(forecastIndex);
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
				data.setValue(spotIndex * forecasts.size() + forecastIndex, 0, spotName);
	    		data.setValue(spotIndex * forecasts.size() + forecastIndex, 1, 2000 + forecastIndex);
	    		data.setValue(spotIndex * forecasts.size() + forecastIndex, 2, new Double(waveHeight));
			}
	    }
	    
		return data;
	}
}
