package edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.wgstrategyC;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.WW3Parameter;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.MiniForecastPopup;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;
import edu.unicen.surfforecaster.gwt.client.utils.images.arrows.s30.Arrows30PxFactory;
import edu.unicen.surfforecaster.gwt.client.utils.images.waves.s30.Waves30PxFactory;

public class WgTableC extends FlexTable {
	//FlexTable for dates
	FlexTable datesTable = null;
	Integer from = null;
	Integer to = null;
	Map<Integer, Map<String, List<ForecastDTO>>> forecasters = null;
	boolean isDatesAlreadyPrinted = false;
	Integer currentRow = 1;
	
	//Layout consts
	private static final String LABELS_COL_WIDTH = "145";
	private static final String DETAILED_FORECAST_COL_WIDTH = "30";
	
	/**
	 * Generates the complete forecaster table for all the spots in the map
	 * @param forecasters A map of spots, each with another map as value with one or two forecasters, the ww3 forecaster always 
	 * and if it has more forecasters, the one selected as default (if it is different than ww3 forecaster) The generated table only shows the wave height of 
	 * the non ww3 forecaster, with a popup in each element with all the ww3 data. If the ww3 is the unique forecaster of the spot, each table item will show
	 * the wave height of ww3, with the respective popup with the other detailed info.
	 * @param from - Represents the forecast position in the forecasts list to start in the table 
	 * @param to - Represents the last forecast to show, if null, goes to the end of the list of forecasts
	 */
	
	public WgTableC(Map<Integer, Map<String, List<ForecastDTO>>> forecasters, Integer from, Integer to) {
		this.from = from;
		this.to = to;
		this.forecasters = forecasters;
		
		//Iterates spots
		Set<Integer> spotsIds = forecasters.keySet();
		Iterator<Integer> i = spotsIds.iterator();
		while (i.hasNext()) {
			Integer spotId = i.next();
			Map<String, List<ForecastDTO>> spotForecasters = forecasters.get(spotId);
			renderSpotForecasters(spotForecasters);
		}
	}

	private void renderSpotForecasters(Map<String, List<ForecastDTO>> spotForecasters) {
		Set<String> forecasterNames = spotForecasters.keySet();
		Iterator<String> i = forecasterNames.iterator();
		while (i.hasNext()) {
			String forecasterName = i.next();
			List<ForecastDTO> forecasts = spotForecasters.get(forecasterName);
			renderForecastsRow(forecasterName, forecasts);
		}
	}

	private void renderForecastsRow(String forecasterName, List<ForecastDTO> forecasts) {
		int forecastIndex = 1;
		//Forecaster name and + link HPanel
		Label lblForecasterName = new Label(forecasterName);
		lblForecasterName.addStyleName("gwt-Label-Forecaster-Name");
		this.setWidget(currentRow, 0, lblForecasterName);
		this.getFlexCellFormatter().setColSpan(currentRow, 0, 2);
		this.getFlexCellFormatter().addStyleName(currentRow, 0, "gwt-ForecasterName-Row");
		
		currentRow++;
		this.printLabels(currentRow);
		
		int max = (this.to != null) ? this.to : forecasts.size();
		for (int i = this.from; i < max; i++) {
			ForecastDTO forecastDTO = forecasts.get(i);
			if (!isDatesAlreadyPrinted){
				this.setWidget(0, forecastIndex, getDateVPanel(forecastDTO));
				this.getColumnFormatter().setWidth(forecastIndex, WgTableC.DETAILED_FORECAST_COL_WIDTH);
			}
			this.setDetailedForecast(forecastDTO, currentRow, forecastIndex);
			forecastIndex++;
		}
		isDatesAlreadyPrinted = true;
		currentRow += 2;
	}
	
	private VerticalPanel getDateVPanel(ForecastDTO forecastDTO) {
		VerticalPanel datePanel = new VerticalPanel();
		datePanel.add(new Label(GWTUtils.LOCALE_CONSTANTS.monday_abbr()));
		datePanel.add(new Label("02"));
		datePanel.add(new Label("16" + GWTUtils.LOCALE_CONSTANTS.hour_abbr()));
		
		datePanel.setWidth("30");
		
		return datePanel;
	}
	
	private void printLabels(int rowIndex) {
			Label waveHeight = new Label(GWTUtils.LOCALE_CONSTANTS.wave_height());
			waveHeight.addStyleName("gwt-Label-TableLabels");
			this.setWidget(rowIndex, 0, waveHeight);
			this.getCellFormatter().setHeight(rowIndex, 0, "30");
			this.getCellFormatter().setVerticalAlignment(rowIndex, 0, HasVerticalAlignment.ALIGN_MIDDLE);
			
			Label heightUnit = new Label("(" + GWTUtils.LOCALE_CONSTANTS.meters_abbr() + ")");
			heightUnit.addStyleName("gwt-Label-TableLabels");
			this.setWidget(rowIndex + 1, 0, heightUnit);
			
			//Cols format
			this.getColumnFormatter().setWidth(0, WgTableC.LABELS_COL_WIDTH);
			
			//Cols style
			this.getColumnFormatter().addStyleName(0, "gwt-flextable-detailedForecast-col");
	}
	
	private Image getWaveIcon(final ForecastDTO forecastDTO) {
		
		Unit heightUnitTarget = Unit.Meters;
		//wave height
		String waveHeight = forecastDTO.getMap().get(WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.toString()).getValue();
		try {
			waveHeight = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveHeight, Unit.Meters, heightUnitTarget));
		} catch (NeuralitoException e) {
			// TODO ver como manejar esta exvepcion de conversion de unidades
			e.printStackTrace();
		}
		
		final Image icon = Waves30PxFactory.getWaveIcon(waveHeight, heightUnitTarget);
		
		final MiniForecastPopup popup = new MiniForecastPopup(forecastDTO);
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
	
	private void setDetailedForecast(ForecastDTO forecastDTO, int rowIndex, int colIndex) {
		Unit heightUnitTarget = Unit.Meters;
		//Unit speedUnitTarget = Unit.KilometersPerHour;
		//Unit directionUnitTarget = Unit.Degrees;
		//Unit periodUnitTarget = Unit.Seconds;
		
		//wave height
		String waveHeight = forecastDTO.getMap().get(WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.toString()).getValue();
//		//wind speed
//		String windSpeed = forecastDTO.getMap().get(WW3Parameter.WIND_SPEED.toString()).getValue();
//		//Wave direccion
//		String waveDirection = forecastDTO.getMap().get(WW3Parameter.PRIMARY_WAVE_DIRECTION.toString()).getValue();
//		//Wave period
//		String wavePeriod = forecastDTO.getMap().get(WW3Parameter.PRIMARY_WAVE_PERIOD.toString()).getValue();
		try {
//			windSpeed = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(windSpeed, Unit.KilometersPerHour, speedUnitTarget));
			waveHeight = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveHeight, Unit.Meters, heightUnitTarget));
//			waveDirection = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveDirection, Unit.Degrees, directionUnitTarget));
//			wavePeriod = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(wavePeriod, Unit.Seconds, periodUnitTarget));
		} catch (NeuralitoException e) {
			// TODO ver como manejar esta exvepcion de conversion de unidades
			e.printStackTrace();
		}
		
		this.setWidget(rowIndex, colIndex, this.getWaveIcon(forecastDTO));
		this.setWidget(rowIndex + 1, colIndex, new Label(waveHeight));
		
		this.getFlexCellFormatter().setHorizontalAlignment(rowIndex, colIndex, HasHorizontalAlignment.ALIGN_CENTER);
		this.getFlexCellFormatter().setHorizontalAlignment(rowIndex + 1, colIndex, HasHorizontalAlignment.ALIGN_CENTER);
		
		this.getColumnFormatter().setWidth(colIndex, WgTableC.DETAILED_FORECAST_COL_WIDTH);
		this.getColumnFormatter().addStyleName(colIndex, "gwt-flextable-detailedForecast-col");
	}
}
