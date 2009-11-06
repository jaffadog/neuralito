package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
	//Horizontal panel for miniForecasts
	HorizontalPanel miniForecastHPanel = null;
	//FlexTable for detailed spot forecast
	FlexTable detailedForecastPanel = null;
	
	public ForecastTable(List<ForecastDTO> forecasts) {
		detailedForecastPanel = new FlexTable();
		miniForecastHPanel = new HorizontalPanel();
		datesHPanel = new HorizontalPanel();
		
		miniForecastHPanel.setVisible(false);
		miniForecastHPanel.setSpacing(7);
		datesHPanel.setSpacing(7);
		detailedForecastPanel.setCellSpacing(5);
		
		//First cell
		this.setWidget(0, 0, new Label("*"));
		this.getCellFormatter().setWidth(0, 0, "145");
		
		//Dates panel
		this.setWidget(0, 1, datesHPanel);
		
		//Forecaster name
		HorizontalPanel forecastHPanel = new HorizontalPanel();
		forecastHPanel.add(new Label("WW3"));
		final Hyperlink lnkForecaster = new Hyperlink("(-)", "");
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
		this.setWidget(1, 0, forecastHPanel);
		this.getCellFormatter().setWidth(1, 0, "145");
		
		//Mini forecaster panel
		this.setWidget(1, 1, miniForecastHPanel);
		
		//Detailed forecaster panel
		this.setWidget(2, 0, detailedForecastPanel);
		this.getFlexCellFormatter().setColSpan(2, 0, 2);
		
		this.printForecast(forecasts);
	}
	
	private void printForecast(List<ForecastDTO> forecasts) {
		//Table left labels
		this.setDetailedLabels();
		
		
		
		
		Iterator<ForecastDTO> i = forecasts.iterator();
		int forecastIndex = 0;
		while (i.hasNext() && forecastIndex < 23) {
			ForecastDTO forecastDTO = i.next();
			datesHPanel.add(this.getDateVPanel(forecastDTO));
			miniForecastHPanel.add(this.getWaveIcon(forecastDTO));
			this.setDetailedForecast(forecastDTO, forecastIndex + 1);
			forecastIndex++;
		}
		//this.setWidget(1, forecastIndex, this.getMiniForecastPanel(forecastDTO));
	}
	
	private void setDetailedLabels() {
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
	
	private Image getWaveIcon(ForecastDTO forecastDTO) {
		Unit heightUnitTarget = Unit.Meters;
		//wave height
		String waveHeight = forecastDTO.getMap().get(WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.toString()).getValue();
		try {
			waveHeight = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveHeight, Unit.Meters, heightUnitTarget));
		} catch (NeuralitoException e) {
			// TODO ver como manejar esta exvepcion de conversion de unidades
			e.printStackTrace();
		}
		
		return Waves30PxFactory.getWaveIcon(waveHeight, heightUnitTarget);
	}
	
	private void setDetailedForecast(ForecastDTO forecastDTO, int index) {
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
