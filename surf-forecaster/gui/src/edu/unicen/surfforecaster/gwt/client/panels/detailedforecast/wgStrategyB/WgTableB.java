package edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.wgStrategyB;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
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

public class WgTableB extends FlexTable {
	
	//FlexTable for dates
	FlexTable datesTable = null;
	Integer from = null;
	Integer to = null;
	Map<String, List<ForecastDTO>> forecasters = null;
	
	//Layout consts
	private static final String LABELS_COL_WIDTH = "145px";
	private static final String DETAILED_FORECAST_COL_WIDTH = "30px";
	
	/**
	 * Generates the complete forecaster table for al the forecasters assigned to the selected spot
	 * @param forecasters The list of forecasters assigned to the selected spot
	 * @param from - Represents the forecast position in the forecasts list to start in the table 
	 * @param to - Represents the last forecast to show, if null, goes to the end of the list of forecasts
	 */
	public WgTableB(Map<String, List<ForecastDTO>> forecasters, Integer from, Integer to) {
		
		this.from = from;
		this.to = to;
		this.forecasters = forecasters;
		
		//Generate the dates panel
		datesTable = new FlexTable();
		
		//This flexTable style
		this.addStyleName("gwt-FlexTable-WgTable");
		
		//FirstRow style
		this.getCellFormatter().addStyleName(0, 1, "gwt-FlexTable-datesTable");
		
		//First cell
		VerticalPanel forecastInfo = new VerticalPanel();
		forecastInfo.add(new Label("04-10-09"));
		forecastInfo.add(new Label("UTC-10"));
		this.setWidget(0, 0, forecastInfo);
		this.getCellFormatter().setWidth(0, 0, WgTableB.LABELS_COL_WIDTH);
		
		//Dates panel
		this.setWidget(0, 1, datesTable);
		
		/*******************************************************************/
		/****************** WW3 FORECASTS **********************************/
		/*******************************************************************/
		List<ForecastDTO> forecasts = forecasters.get("WW3 Noaa Forecaster");
		this.generateAllForecastersTable("WW3 Noaa Forecaster", forecasts, 0, true, true);
		
		/*******************************************************************/
		/****************** OTHER FORECASTERS ******************************/
		/*******************************************************************/
		Set<String> keys = forecasters.keySet();
		Iterator<String> i = keys.iterator();
		int forecasterIndex = 1;
		while (i.hasNext()) {
			String key = i.next();
			if (!key.equals("WW3 Noaa Forecaster")) 
			{
				forecasts = forecasters.get(key);
				this.generateAllForecastersTable(key, forecasts, forecasterIndex, false, false);
				forecasterIndex++;
			}
		}
	}
	
	/**
	 * This method generates the external flextable for an specific forecast.
	 * @param forecasts - List of forecasts for a specific forecaster
	 * @param fillDatesPanel - If true adds dates to the date panel, this must be true once.
	 * @param forecasterIndex - The index row where the detailed forecaster flexTable will be located
	 * @param Generate detailedForecastPanel?
	 */
	private void generateAllForecastersTable(String forecasterName, List<ForecastDTO> forecasts, final int forecasterIndex, 
			boolean fillDatesPanel, boolean generateDetailed) {
		
		//Horizontal panel for ww3 miniForecasts
		final FlexTable miniForecastPanel = new FlexTable();
		miniForecastPanel.setVisible(false);

		//this.setWidget(1 + (forecasterIndex * 2), 1, miniForecastHPanel);
		
		//FlexTable for detailed forecast
		final FlexTable detailedForecastPanel;
		if (generateDetailed) {
			detailedForecastPanel = new FlexTable();
			this.setWidget(2 + (forecasterIndex * 2), 0, detailedForecastPanel);
		} else {
			detailedForecastPanel = null;
			miniForecastPanel.setVisible(true);
			this.setWidget(2 + (forecasterIndex * 2), 0, miniForecastPanel);
		}
		
		//ColSpan for the detailed and mini forecast flexTable
		this.getFlexCellFormatter().setColSpan(2 + (forecasterIndex * 2), 0, 2);
		
		//Forecaster name and + link HPanel
		HorizontalPanel forecastHPanel = new HorizontalPanel();
		this.setWidget(1 + (forecasterIndex * 2), 0, forecastHPanel);
		this.getFlexCellFormatter().setColSpan(1 + (forecasterIndex * 2), 0, 2);
		this.getFlexCellFormatter().addStyleName(1 + (forecasterIndex * 2), 0, "gwt-ForecasterName-Row");
		Label lblForecasterName = new Label(forecasterName);
		lblForecasterName.addStyleName("gwt-Label-Forecaster-Name");
		forecastHPanel.add(lblForecasterName);
		
		if (generateDetailed) {
			final Hyperlink lnkForecaster = new Hyperlink(" (-)", "");
			lnkForecaster.addStyleName("gwt-HyperLink-showMoreLess");
			lnkForecaster.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (miniForecastPanel.isVisible()) {
						miniForecastPanel.setVisible(false);
						setWidget(2 + (forecasterIndex * 2), 0, detailedForecastPanel);
						detailedForecastPanel.setVisible(true);
						lnkForecaster.setText("(-)");
					} else {
						miniForecastPanel.setVisible(true);
						setWidget(2 + (forecasterIndex * 2), 0, miniForecastPanel);
						detailedForecastPanel.setVisible(false);
						lnkForecaster.setText("(+)");
					}
				}
			});
			forecastHPanel.add(lnkForecaster);
		}
		forecastHPanel.setSpacing(5);
		//this.getCellFormatter().setWidth(1 + (forecasterIndex * 2), 0, "145");
		
		//Print forecasts from WW3 forecaster
		this.printForecast(forecasts, detailedForecastPanel, miniForecastPanel, fillDatesPanel, forecasterIndex);
		
		

	}
	
	/**
	 * This method generates the flexTable with detailed forecasts for a specific forecaster
	 * @param forecasts - List of forecasts for a specific forecaster
	 * @param detaildedForecastPanel - The table which contains all the forecasts for the list of forecasts as parameter
	 * @param miniForecastHPanel - The table which contains the forecasts represantation as a wave icon
	 * @param fillDatesPanel - If true adds dates to the date panel, this must be true once.
	 * @param forecasterIndex - The index row where the detailed forecaster flexTable will be located
	 */
	private void printForecast(List<ForecastDTO> forecasts, FlexTable detailedForecastPanel, FlexTable miniForecastPanel, 
			boolean fillDatesPanel, int forecasterIndex) {
		
		
		//Table left labels
		this.setDetailedLabels(detailedForecastPanel, miniForecastPanel);
		int forecastIndex = 0;
		int max = (this.to != null) ? this.to : forecasts.size();
		for (int i = this.from; i < max; i++) {
			ForecastDTO forecastDTO = forecasts.get(i);
			if (fillDatesPanel){
				datesTable.setWidget(0, forecastIndex, this.getDateLabel(forecastDTO));
				datesTable.getColumnFormatter().setWidth(forecastIndex, WgTableB.DETAILED_FORECAST_COL_WIDTH);
			}
			this.setDetailedForecast(detailedForecastPanel, miniForecastPanel, forecastDTO, forecastIndex + 1);
			forecastIndex++;
		}
	}
	
	private void setDetailedLabels(FlexTable detailedForecastPanel, FlexTable miniForecastPanel) {
		if (detailedForecastPanel != null) {
			Label waveHeight = new Label(GWTUtils.LOCALE_CONSTANTS.wave_height());
			waveHeight.addStyleName("gwt-Label-TableLabels");
			detailedForecastPanel.setWidget(0, 0, waveHeight);
			detailedForecastPanel.getCellFormatter().setHeight(0, 0, "30px");
			detailedForecastPanel.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
			
			
			
			Label heightUnit = new Label("(" + GWTUtils.LOCALE_CONSTANTS.meters_abbr() + ")");
			heightUnit.addStyleName("gwt-Label-TableLabels");
			detailedForecastPanel.setWidget(1, 0, heightUnit);
			
			
			
			Label waveDirection = new Label(GWTUtils.LOCALE_CONSTANTS.wave_direction());
			waveDirection.addStyleName("gwt-Label-TableLabels");
			detailedForecastPanel.setWidget(2, 0, waveDirection);
			detailedForecastPanel.getCellFormatter().setHeight(2, 0, "30px");
			detailedForecastPanel.getCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_MIDDLE);
			
			Label period = new Label(GWTUtils.LOCALE_CONSTANTS.wave_period() + " (" + GWTUtils.LOCALE_CONSTANTS.seconds_abbr() + ")");
			period.addStyleName("gwt-Label-TableLabels");
			detailedForecastPanel.setWidget(3, 0, period);
			
			Label windDirection = new Label(GWTUtils.LOCALE_CONSTANTS.wind_direction());
			windDirection.addStyleName("gwt-Label-TableLabels");
			detailedForecastPanel.setWidget(4, 0, windDirection);
			detailedForecastPanel.getCellFormatter().setHeight(4, 0, "30px");
			detailedForecastPanel.getCellFormatter().setVerticalAlignment(4, 0, HasVerticalAlignment.ALIGN_MIDDLE);
			
			Label windSpeed = new Label(GWTUtils.LOCALE_CONSTANTS.wind_speed() + " (" + GWTUtils.LOCALE_CONSTANTS.kilometers_per_hour_abbr() + ")");
			windSpeed.addStyleName("gwt-Label-TableLabels");
			detailedForecastPanel.setWidget(5, 0, windSpeed);
			
			//Cols format
			detailedForecastPanel.getColumnFormatter().setWidth(0, WgTableB.LABELS_COL_WIDTH);
			
			//Cols style
			detailedForecastPanel.getColumnFormatter().addStyleName(0, "gwt-flextable-detailedForecast-col");
		}
		
		Label waveHeight2 = new Label(GWTUtils.LOCALE_CONSTANTS.wave_height());
		waveHeight2.addStyleName("gwt-Label-TableLabels");
		miniForecastPanel.setWidget(0, 0, waveHeight2);
		miniForecastPanel.getCellFormatter().setHeight(0, 0, "30px");
		miniForecastPanel.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		
		Label heightUnit2 = new Label("(" + GWTUtils.LOCALE_CONSTANTS.meters_abbr() + ")");
		heightUnit2.addStyleName("gwt-Label-TableLabels");
		miniForecastPanel.setWidget(1, 0, heightUnit2);
		
		miniForecastPanel.getColumnFormatter().setWidth(0, WgTableB.LABELS_COL_WIDTH);
		miniForecastPanel.getColumnFormatter().addStyleName(0, "gwt-flextable-detailedForecast-col");
	}
	
	private Label getDateLabel(ForecastDTO forecastDTO) {
		long miliDate = forecastDTO.getBaseDate().getTime().getTime() + (forecastDTO.getForecastTime() * 3600000);
		Date realDate = new Date(miliDate);
		Label lblDate = new Label(GWTUtils.getDayAbbr(realDate.getDay()) + " " + 
				NumberFormat.getFormat("00").format(realDate.getDate()) + " " + 
				NumberFormat.getFormat("00").format(realDate.getHours()) + GWTUtils.LOCALE_CONSTANTS.hour_abbr());
		lblDate.setWidth(WgTableB.DETAILED_FORECAST_COL_WIDTH);
		lblDate.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		return lblDate;
	}
	
	private Image getWaveIcon(final ForecastDTO forecastDTO, boolean showPopup) {
		
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
		if (showPopup) {
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
		}
		
		return icon;
	}
	
	private void setDetailedForecast(FlexTable detailedForecastPanel, FlexTable miniForecastPanel, ForecastDTO forecastDTO, int index) {
		Unit heightUnitTarget = Unit.Meters;
		Unit speedUnitTarget = Unit.KilometersPerHour;
		Unit directionUnitTarget = Unit.Degrees;
		Unit periodUnitTarget = Unit.Seconds;
		
		//wave height
		String waveHeight = forecastDTO.getMap().get(WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.toString()).getValue();
		//wind speed
		String windSpeed = forecastDTO.getMap().get(WW3Parameter.WIND_SPEED.toString()).getValue();
		//wind windDirection
		String windDirection = forecastDTO.getMap().get(WW3Parameter.WIND_DIRECTION.toString()).getValue();
		//Wave direccion
		String waveDirection = forecastDTO.getMap().get(WW3Parameter.PRIMARY_WAVE_DIRECTION.toString()).getValue();
		//Wave period
		String wavePeriod = forecastDTO.getMap().get(WW3Parameter.PRIMARY_WAVE_PERIOD.toString()).getValue();
		try {
			windSpeed = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(windSpeed, Unit.KilometersPerHour, speedUnitTarget));
			windDirection = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(windDirection, Unit.Degrees, directionUnitTarget));
			waveHeight = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveHeight, Unit.Meters, heightUnitTarget));
			waveDirection = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveDirection, Unit.Degrees, directionUnitTarget));
			wavePeriod = NumberFormat.getFormat("###").format(UnitConverter.convertValue(wavePeriod, Unit.Seconds, periodUnitTarget));
		} catch (NeuralitoException e) {
			// TODO ver como manejar esta exvepcion de conversion de unidades
			e.printStackTrace();
		}
		
		
		//detailed Forecast table
		if (detailedForecastPanel != null) {
			detailedForecastPanel.setWidget(0, index, Waves30PxFactory.getWaveIcon(waveHeight, heightUnitTarget));
			detailedForecastPanel.setWidget(1, index, new Label(waveHeight));
			detailedForecastPanel.setWidget(2, index, Arrows30PxFactory.getArrowIcon(waveDirection, directionUnitTarget));
			detailedForecastPanel.setWidget(3, index, new Label(wavePeriod));
			detailedForecastPanel.setWidget(4, index, Arrows30PxFactory.getArrowIcon(windDirection, windSpeed, directionUnitTarget, speedUnitTarget));
			detailedForecastPanel.setWidget(5, index, new Label(windSpeed));
			
			//cols format
			detailedForecastPanel.getColumnFormatter().setWidth(index, WgTableB.DETAILED_FORECAST_COL_WIDTH);
			
			//cols style
			detailedForecastPanel.getColumnFormatter().addStyleName(index, "gwt-flextable-detailedForecast-col");
			
			//cell align
			detailedForecastPanel.getFlexCellFormatter().setHorizontalAlignment(0, index, HasHorizontalAlignment.ALIGN_CENTER);
			detailedForecastPanel.getFlexCellFormatter().setHorizontalAlignment(1, index, HasHorizontalAlignment.ALIGN_CENTER);
			detailedForecastPanel.getFlexCellFormatter().setHorizontalAlignment(2, index, HasHorizontalAlignment.ALIGN_CENTER);
			detailedForecastPanel.getFlexCellFormatter().setHorizontalAlignment(3, index, HasHorizontalAlignment.ALIGN_CENTER);
			detailedForecastPanel.getFlexCellFormatter().setHorizontalAlignment(4, index, HasHorizontalAlignment.ALIGN_CENTER);
			detailedForecastPanel.getFlexCellFormatter().setHorizontalAlignment(5, index, HasHorizontalAlignment.ALIGN_CENTER);
		}
		
		//mini forecast table
		miniForecastPanel.setWidget(0, index, this.getWaveIcon(forecastDTO, detailedForecastPanel != null ? true : false));
		miniForecastPanel.setWidget(1, index, new Label(waveHeight));
		
		miniForecastPanel.getFlexCellFormatter().setHorizontalAlignment(0, index, HasHorizontalAlignment.ALIGN_CENTER);
		miniForecastPanel.getFlexCellFormatter().setHorizontalAlignment(1, index, HasHorizontalAlignment.ALIGN_CENTER);
		
		miniForecastPanel.getColumnFormatter().setWidth(index, WgTableB.DETAILED_FORECAST_COL_WIDTH);
		miniForecastPanel.getColumnFormatter().addStyleName(index, "gwt-flextable-detailedForecast-col");
	}
}
