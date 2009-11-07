package edu.unicen.surfforecaster.gwt.client.panels.detailedforecast;

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
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;
import edu.unicen.surfforecaster.gwt.client.utils.images.arrows.s30.Arrows30PxFactory;
import edu.unicen.surfforecaster.gwt.client.utils.images.waves.s30.Waves30PxFactory;

public class ForecastTable extends FlexTable {
	
	//Horizontal panel for dates
	HorizontalPanel datesHPanel = null;
	Integer from = null;
	Integer to = null;
	
	
	/**
	 * Generates the complete forecaster table for al the forecasters assigned to the selected spot
	 * @param forecasters The list of forecasters assigned to the selected spot
	 * @param from - Represents the forecast position in the forecasts list to start in the table 
	 * @param to - Represents the last forecast to show, if null, goes to the end of the list of forecasts
	 */
	public ForecastTable(Map<String, List<ForecastDTO>> forecasters, Integer from, Integer to) {
		
		this.from = from;
		this.to = to;
		//detailedForecastPanel = new FlexTable();
		//miniForecastHPanel = new HorizontalPanel();
		datesHPanel = new HorizontalPanel();
		
		
		datesHPanel.setSpacing(7);
		
		
		//First cell
		this.setWidget(0, 0, new Label("*"));
		this.getCellFormatter().setWidth(0, 0, "145");
		
		//Dates panel
		this.setWidget(0, 1, datesHPanel);
		
		/*******************************************************************/
		/****************** WW3 FORECASTS **********************************/
		/*******************************************************************/
		List<ForecastDTO> forecasts = forecasters.get("WW3 Noaa Forecaster");
		this.generateAllForecastersTable("WW3 Noaa Forecaster", forecasts, 0, true);
		
		
		
		/*******************************************************************/
		/****************** OTHER FORECASTERS ******************************/
		/*******************************************************************/
		Set<String> keys = forecasters.keySet();
		Iterator<String> i = keys.iterator();
		int forecasterIndex = 1;
		while (i.hasNext()) {
			String key = i.next();
			//if (!key.equals("WW3 Noaa Forecaster")) 
			{
				forecasts = forecasters.get(key);
				this.generateAllForecastersTable(key, forecasts, forecasterIndex, false);
				forecasterIndex++;
			}
		}
		
		
	}
	
	/**
	 * This method generates the external flextable for an specific forecast.
	 * @param forecasts - List of forecasts for a specific forecaster
	 * @param fillDatesPanel - If true adds dates to the date panel, this must be true once.
	 * @param forecasterIndex - The index row where the detailed forecaster flexTable will be located
	 */
	private void generateAllForecastersTable(String forecasterName, List<ForecastDTO> forecasts, int forecasterIndex, boolean fillDatesPanel) {
		
		//Horizontal panel for ww3 miniForecasts
		final HorizontalPanel miniForecastHPanel = new HorizontalPanel();
		miniForecastHPanel.setVisible(false);
		miniForecastHPanel.setSpacing(7);
		this.setWidget(1 + (forecasterIndex * 2), 1, miniForecastHPanel);
		
		//FlexTable for detailed ww3 forecast
		final FlexTable detailedForecastPanel = new FlexTable();
		detailedForecastPanel.setCellSpacing(5);
		
		//Forecaster name and + link HPanel
		HorizontalPanel forecastHPanel = new HorizontalPanel();
		this.setWidget(1 + (forecasterIndex * 2), 0, forecastHPanel);
		forecastHPanel.add(new Label(forecasterName));
		final Hyperlink lnkForecaster = new Hyperlink(" (-)", "");
		lnkForecaster.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (miniForecastHPanel.isVisible()) {
					miniForecastHPanel.setVisible(false);
					detailedForecastPanel.setVisible(true);
					lnkForecaster.setText("(-)");
				} else {
					miniForecastHPanel.setVisible(true);
					detailedForecastPanel.setVisible(false);
					lnkForecaster.setText("(+)");
				}
			}
		});
		forecastHPanel.add(lnkForecaster);
		forecastHPanel.setSpacing(5);
		this.getCellFormatter().setWidth(1 + (forecasterIndex * 2), 0, "145");
		
		//Print forecasts from WW3 forecaster
		this.printForecast(forecasts, detailedForecastPanel, miniForecastHPanel, fillDatesPanel, forecasterIndex);
		
		

	}
	
	/**
	 * This method generates the flexTable with detailed forecasts for a specific forecaster
	 * @param forecasts - List of forecasts for a specific forecaster
	 * @param detaildedForecastPanel - The table which contains all the forecasts for the list of forecasts as parameter
	 * @param miniForecastHPanel - The table which contains the forecasts represantation as a wave icon
	 * @param fillDatesPanel - If true adds dates to the date panel, this must be true once.
	 * @param forecasterIndex - The index row where the detailed forecaster flexTable will be located
	 */
	private void printForecast(List<ForecastDTO> forecasts, FlexTable detailedForecastPanel, HorizontalPanel miniForecastHPanel, 
			boolean fillDatesPanel, int forecasterIndex) {
		
		
		//Table left labels
		this.setDetailedLabels(detailedForecastPanel);
		
//		Iterator<ForecastDTO> i = forecasts.iterator();
		int forecastIndex = 0;
//		while (i.hasNext() && forecastIndex < 23) {
//			ForecastDTO forecastDTO = i.next();
		int max = (this.to != null) ? this.to : forecasts.size();
		for (int i = this.from; i < max; i++) {
			ForecastDTO forecastDTO = forecasts.get(i);
			if (fillDatesPanel)
				datesHPanel.add(this.getDateVPanel(forecastDTO));
			miniForecastHPanel.add(this.getWaveIcon(forecastDTO));
			this.setDetailedForecast(detailedForecastPanel, forecastDTO, forecastIndex + 1);
			forecastIndex++;
		}
		
		//Detailed forecaster panel
		this.setWidget(2 + (forecasterIndex * 2), 0, detailedForecastPanel);
		this.getFlexCellFormatter().setColSpan(2 + (forecasterIndex * 2), 0, 2);
	}
	
	private void setDetailedLabels(FlexTable detailedForecastPanel) {
		
		Label waveHeight = new Label("Altura ola"); 
		detailedForecastPanel.setWidget(0, 0, waveHeight);
		detailedForecastPanel.getCellFormatter().setHeight(0, 0, "30");
		detailedForecastPanel.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		
		detailedForecastPanel.setWidget(1, 0, new Label("(mts)"));
		
		Label waveDirection = new Label("Direccion olas");
		detailedForecastPanel.setWidget(2, 0, waveDirection);
		detailedForecastPanel.getCellFormatter().setHeight(2, 0, "30");
		detailedForecastPanel.getCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		
		detailedForecastPanel.setWidget(3, 0, new Label("Periodo(s)"));
		
		Label windDirection = new Label("Direccion viento"); 
		detailedForecastPanel.setWidget(4, 0, windDirection);
		detailedForecastPanel.getCellFormatter().setHeight(4, 0, "30");
		detailedForecastPanel.getCellFormatter().setVerticalAlignment(4, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		
		detailedForecastPanel.setWidget(5, 0, new Label("Vel. viento(Km/s)"));
		//This width fix the width of all the cells, due that is the widest cell
		detailedForecastPanel.getCellFormatter().setWidth(5, 0, "130");
	}
	
	private VerticalPanel getDateVPanel(ForecastDTO forecastDTO) {
		VerticalPanel datePanel = new VerticalPanel();
		datePanel.add(new Label("Lu"));
		datePanel.add(new Label("02"));
		datePanel.add(new Label("16h"));
		
		datePanel.setWidth("30");
		
		return datePanel;
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
	
	private void setDetailedForecast(FlexTable detailedForecastPanel, ForecastDTO forecastDTO, int index) {
		Unit heightUnitTarget = Unit.Meters;
		Unit speedUnitTarget = Unit.KilometersPerHour;
		Unit directionUnitTarget = Unit.Degrees;
		
		//wave height
		String waveHeight = forecastDTO.getMap().get(WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.toString()).getValue();
		//wind speed
		String windSpeed = forecastDTO.getMap().get(WW3Parameter.WIND_SPEED.toString()).getValue();
		//Wave direccion
		String waveDirection = forecastDTO.getMap().get(WW3Parameter.PRIMARY_WAVE_DIRECTION.toString()).getValue();
		try {
			windSpeed = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(windSpeed, Unit.KilometersPerHour, speedUnitTarget));
			waveHeight = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveHeight, Unit.Meters, heightUnitTarget));
			waveDirection = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveDirection, Unit.Degrees, directionUnitTarget));
		} catch (NeuralitoException e) {
			// TODO ver como manejar esta exvepcion de conversion de unidades
			e.printStackTrace();
		}
		
		detailedForecastPanel.setWidget(0, index, Waves30PxFactory.getWaveIcon(waveHeight, heightUnitTarget));
		detailedForecastPanel.setWidget(1, index, new Label(waveHeight));
		detailedForecastPanel.setWidget(2, index, Arrows30PxFactory.getArrowIcon(waveDirection, directionUnitTarget));
		detailedForecastPanel.setWidget(3, index, new Label(forecastDTO.getMap().get(WW3Parameter.PRIMARY_WAVE_PERIOD.toString()).getValue()));
		detailedForecastPanel.setWidget(4, index, Arrows30PxFactory.getArrowIcon("23", windSpeed, directionUnitTarget, speedUnitTarget));
		detailedForecastPanel.setWidget(5, index, new Label(forecastDTO.getMap().get(WW3Parameter.WIND_SPEED.toString()).getValue()));
	}
}
