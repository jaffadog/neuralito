package edu.unicen.surfforecaster.gwt.client.panels.detailedforecast;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.WW3Parameter;
import edu.unicen.surfforecaster.gwt.client.dto.ForecastGwtDTO;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;
import edu.unicen.surfforecaster.gwt.client.utils.UnitTranslator;
import edu.unicen.surfforecaster.gwt.client.utils.images.arrows.s30.Arrows30PxFactory;
import edu.unicen.surfforecaster.gwt.client.utils.images.waves.s30.Waves30PxFactory;

public class MiniForecastPopup extends PopupPanel {
	
	private static final String ICON_SIZE = "30";
	
	public MiniForecastPopup(ForecastGwtDTO forecastDTO) {
		super(false);
		
		if (forecastDTO != null) {
			FlexTable table = new FlexTable();
			
			Label lblTitle = new Label("WW3");
			lblTitle.addStyleName("gwt-Label-MiniForecast-Title");
			table.setWidget(0,0, lblTitle);
			table.getFlexCellFormatter().setColSpan(0, 0, 2);
			table.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
			
			//TODO generar las unidades en que se ve el sitio como alguna setting de usuario o usando cookies o algo y poner esos dato del lado derecho te las sig
			//igualdades
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
				// The origin units is hardcoded because we knows which units types retrieve the service latestForecast
				windSpeed = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(windSpeed, Unit.KilometersPerHour, speedUnitTarget));
				windDirection = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(windDirection, Unit.Degrees, directionUnitTarget));
				waveHeight = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveHeight, Unit.Meters, heightUnitTarget));
				waveDirection = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveDirection, Unit.Degrees, directionUnitTarget));
				wavePeriod = NumberFormat.getFormat("###").format(UnitConverter.convertValue(wavePeriod, Unit.Seconds, periodUnitTarget));
			} catch (NeuralitoException e) {
				// TODO ver como manejar esta exvepcion de conversion de unidades
				e.printStackTrace();
			}
			
			table.setWidget(1, 0, this.createTableItem(GWTUtils.LOCALE_CONSTANTS.wind(), windSpeed + " " + UnitTranslator.getUnitAbbrTranlation(speedUnitTarget), 
					Arrows30PxFactory.getArrowIcon(windDirection, windSpeed, directionUnitTarget, speedUnitTarget)));
			table.setWidget(1, 1, this.createTableItem(GWTUtils.LOCALE_CONSTANTS.wave_direction(), "", 
					Arrows30PxFactory.getArrowIcon(waveDirection, directionUnitTarget)));
			table.setWidget(2, 0, this.createTableItem(GWTUtils.LOCALE_CONSTANTS.wave_height(), waveHeight + " " + UnitTranslator.getUnitAbbrTranlation(heightUnitTarget), 
					Waves30PxFactory.getWaveIcon(waveHeight, heightUnitTarget)));
			table.setWidget(2, 1, this.createTableItem(GWTUtils.LOCALE_CONSTANTS.wave_period(), wavePeriod, UnitTranslator.getUnitAbbrTranlation(periodUnitTarget)));
			
			table.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
			table.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_CENTER);
			table.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
			table.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_CENTER);
			
			this.setWidget(table);
		}
	}
	
	private VerticalPanel createTableItem(String title, String value, String unit){
		VerticalPanel tableItem = new VerticalPanel();
		tableItem.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		Label tableItemTitle = new Label(title);
		tableItemTitle.addStyleName("gwt-Label-ForecastItem-Title");
		tableItem.add(tableItemTitle);
		
		Label tableItemValue = new Label(value);
		tableItemValue.addStyleName("gwt-Label-MiniForecastItem-Value");
		tableItem.add(tableItemValue);
		tableItem.setCellHeight(tableItemValue, MiniForecastPopup.ICON_SIZE);
		tableItem.setCellVerticalAlignment(tableItemValue, HasVerticalAlignment.ALIGN_MIDDLE);
		
		Label tableItemUnit = new Label(unit);
		tableItem.add(tableItemUnit);
		
		return tableItem;
	}
	
	private VerticalPanel createTableItem(String title, String value, Image image){
		VerticalPanel tableItem = new VerticalPanel();
		tableItem.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		Label tableItemValue = new Label(value == "" ? image.getTitle() : value);
		
		image.setSize(MiniForecastPopup.ICON_SIZE, MiniForecastPopup.ICON_SIZE);
		
		Label tableItemTitle = new Label(title);
		tableItemTitle.addStyleName("gwt-Label-ForecastItem-Title");

		tableItem.add(tableItemTitle);
		tableItem.add(image);
		tableItem.add(tableItemValue);
		
		return tableItem;
	}
}
