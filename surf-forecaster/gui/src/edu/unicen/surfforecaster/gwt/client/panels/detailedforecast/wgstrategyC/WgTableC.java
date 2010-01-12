package edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.wgstrategyC;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.WaveWatchParameter;
import edu.unicen.surfforecaster.gwt.client.dto.ForecastGwtDTO;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.MiniForecastPopup;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;
import edu.unicen.surfforecaster.gwt.client.utils.images.waves.s30.Waves30PxFactory;

public class WgTableC extends FlexTable {
	//FlexTable for dates
	FlexTable datesTable = null;
	Integer from = null;
	Integer to = null;
	Map<Integer, Map<String, List<ForecastGwtDTO>>> forecasters = null;
	boolean isDatesAlreadyPrinted = false;
	Integer currentRow = 1;
	
	//Layout consts
	private static final String LABELS_COL_WIDTH = "145px";
	private static final String DETAILED_FORECAST_COL_WIDTH = "30px";
	
	/**
	 * Generates the complete forecaster table for all the spots in the map
	 * @param forecasters A map of spots, each with another map as value with one or two forecasters, the ww3 forecaster always 
	 * and if it has more forecasters, the one selected as default (if it is different than ww3 forecaster) The generated table only shows the wave height of 
	 * the non ww3 forecaster, with a popup in each element with all the ww3 data. If the ww3 is the unique forecaster of the spot, each table item will show
	 * the wave height of ww3, with the respective popup with the other detailed info.
	 * @param from - Represents the forecast position in the forecasts list to start in the table 
	 * @param to - Represents the last forecast to show, if null, goes to the end of the list of forecasts
	 */
	
	public WgTableC(Map<Integer, Map<String, List<ForecastGwtDTO>>> forecasters, List<Integer> spotsIds, List<String> spotsNames, 
			List<String> forecastersNames, Integer from, Integer to) {
		this.from = from;
		this.to = to;
		this.forecasters = forecasters;
		
		//This flexTable style
		this.addStyleName("gwt-FlexTable-WgTable");
		
		//FirstRow style
		this.getRowFormatter().addStyleName(0, "gwt-FlexTable-datesTable");
		this.getCellFormatter().setStyleName(0, 0, "gwt-FlexTable-whiteCell");
		//Col 0 width
		this.getFlexCellFormatter().setWidth(0, 0, WgTableC.LABELS_COL_WIDTH);
		//Col 0 style
		this.getColumnFormatter().addStyleName(0, "gwt-flextable-detailedForecast-col");
		
		//Iterates spots
		for (int i = 0; i < spotsIds.size(); i++) {
			Integer spotId = spotsIds.get(i);
			String spotName = spotsNames.get(i);
			String forecasterName = forecastersNames.get(i);
			Map<String, List<ForecastGwtDTO>> spotForecasters = forecasters.get(spotId);
			renderForecastsRow(spotForecasters, spotName, forecasterName, i);
		}
	}

	private void renderForecastsRow(Map<String, List<ForecastGwtDTO>> spotForecasters, String spotName, String forecasterName, int spotIndex) {
		List<ForecastGwtDTO> forecasts = spotForecasters.get(forecasterName);
		List<ForecastGwtDTO> ww3Forecasts = spotForecasters.get("WW3 Noaa Forecaster");
		int forecastIndex = 1;
		int max = (this.to != null) ? this.to : forecasts.size();
		//Spot and Forecaster name
		Label lblForecasterName = new Label(spotName + "  >>  " + forecasterName);
		lblForecasterName.addStyleName("gwt-Label-Forecaster-Name");
		this.setWidget(currentRow, 0, lblForecasterName);
		this.getFlexCellFormatter().setColSpan(currentRow, 0, (max - from) + 1);
		this.getFlexCellFormatter().addStyleName(currentRow, 0, "gwt-ForecasterName-Row-" + spotIndex);
		
		currentRow++;
		this.printLabels(currentRow);
		
		//Iterates forecastDTOs
		for (int i = this.from; i < max; i++) {
			ForecastGwtDTO forecastDTO = forecasts.get(i);
			ForecastGwtDTO ww3ForecastDTO = ww3Forecasts.get(i);
			if (!isDatesAlreadyPrinted){
				this.setWidget(0, forecastIndex, getDateLabel(forecastDTO));
				this.getColumnFormatter().setWidth(forecastIndex, WgTableC.DETAILED_FORECAST_COL_WIDTH);
			}
			this.setDetailedForecast(forecastDTO, ww3ForecastDTO, currentRow, forecastIndex);
			forecastIndex++;
		}
		isDatesAlreadyPrinted = true;
		currentRow += 2;
	}
	
	private Label getDateLabel(ForecastGwtDTO forecastDTO) {
		long miliDate = forecastDTO.getBaseDate().getTime() + (forecastDTO.getForecastTime() * 3600000);
		Date realDate = new Date(miliDate);
		Label lblDate = new Label(GWTUtils.getDayAbbr(realDate.getDay()) + " " + 
				NumberFormat.getFormat("00").format(realDate.getDate()) + " " + 
				NumberFormat.getFormat("00").format(realDate.getHours()) + GWTUtils.LOCALE_CONSTANTS.hour_abbr());
		lblDate.setWidth(WgTableC.DETAILED_FORECAST_COL_WIDTH);
		lblDate.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		return lblDate;
	}
	
	private void printLabels(int rowIndex) {
			Label waveHeight = new Label(GWTUtils.LOCALE_CONSTANTS.wave_height());
			waveHeight.addStyleName("gwt-Label-TableLabels");
			this.setWidget(rowIndex, 0, waveHeight);
			this.getCellFormatter().setHeight(rowIndex, 0, "30px");
			this.getFlexCellFormatter().setWidth(rowIndex, 0, WgTableC.LABELS_COL_WIDTH);
			this.getCellFormatter().setVerticalAlignment(rowIndex, 0, HasVerticalAlignment.ALIGN_MIDDLE);
			
			Label heightUnit = new Label("(" + GWTUtils.LOCALE_CONSTANTS.meters_abbr() + ")");
			heightUnit.addStyleName("gwt-Label-TableLabels");
			this.setWidget(rowIndex + 1, 0, heightUnit);
			this.getFlexCellFormatter().setWidth(rowIndex + 1, 0, WgTableC.LABELS_COL_WIDTH);
	}
	
	private void setDetailedForecast(ForecastGwtDTO forecastDTO, ForecastGwtDTO ww3ForecastDTO, int rowIndex, int colIndex) {
		Unit heightUnitTarget = Unit.Meters;

		//wave height
		String waveHeight = forecastDTO.getMap().get(WaveWatchParameter.COMBINED_SWELL_WIND_WAVE_HEIGHT_V2.getValue()).getValue();
		try {
			waveHeight = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveHeight, Unit.Meters, heightUnitTarget));
		} catch (NeuralitoException e) {
			// TODO ver como manejar esta exvepcion de conversion de unidades
			e.printStackTrace();
		}
		
		this.setWidget(rowIndex, colIndex, this.getWaveIcon(forecastDTO, ww3ForecastDTO));
		this.setWidget(rowIndex + 1, colIndex, new Label(waveHeight));
		
		this.getFlexCellFormatter().setHorizontalAlignment(rowIndex, colIndex, HasHorizontalAlignment.ALIGN_CENTER);
		this.getFlexCellFormatter().setHorizontalAlignment(rowIndex + 1, colIndex, HasHorizontalAlignment.ALIGN_CENTER);
		
		this.getColumnFormatter().setWidth(colIndex, WgTableC.DETAILED_FORECAST_COL_WIDTH);
		this.getColumnFormatter().addStyleName(colIndex, "gwt-flextable-detailedForecast-col");
	}
	
	private Image getWaveIcon(final ForecastGwtDTO forecastDTO, final ForecastGwtDTO ww3ForecastDTO) {
		
		Unit heightUnitTarget = Unit.Meters;
		//wave height
		String waveHeight = forecastDTO.getMap().get(WaveWatchParameter.COMBINED_SWELL_WIND_WAVE_HEIGHT_V2.getValue()).getValue();
		try {
			waveHeight = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveHeight, Unit.Meters, heightUnitTarget));
		} catch (NeuralitoException e) {
			// TODO ver como manejar esta exvepcion de conversion de unidades
			e.printStackTrace();
		}
		
		final Image icon = Waves30PxFactory.getWaveIcon(waveHeight, heightUnitTarget);
		
		final MiniForecastPopup popup = new MiniForecastPopup(ww3ForecastDTO);
		icon.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				popup.showRelativeTo(icon);
			}
			
		});
		icon.addMouseOutHandler(new MouseOutHandler(){
			public void onMouseOut(MouseOutEvent event) {
				popup.hide();
			}
			
		});
		
		return icon;
	}
}
