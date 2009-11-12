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
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.WW3Parameter;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;
import edu.unicen.surfforecaster.gwt.client.utils.images.arrows.s30.Arrows30PxFactory;
import edu.unicen.surfforecaster.gwt.client.utils.images.waves.s30.Waves30PxFactory;

public class MiniForecastPopup extends PopupPanel {
	
	private static final String ICON_SIZE = "30";
	
	public MiniForecastPopup(ForecastDTO forecastDTO) {
		super(false);
		
		if (forecastDTO != null) {
			FlexTable table = new FlexTable();
			
			Label lblTitle = new Label("WW3");
			lblTitle.addStyleName("gwt-Label-MiniForecast-Title");
			table.setWidget(0,0, lblTitle);
			table.getFlexCellFormatter().setColSpan(0, 0, 2);
			table.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
			
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
			
			table.setWidget(1, 0, this.createTableItem("Viento", windSpeed + " " + forecastDTO.getMap().get(WW3Parameter.WIND_SPEED.toString()).getUnit().toString(), 
					"SSO", Arrows30PxFactory.getArrowIcon("23", windSpeed, directionUnitTarget, speedUnitTarget)));
			table.setWidget(1, 1, this.createTableItem("Dir. ola", waveDirection + " " + forecastDTO.getMap().get(WW3Parameter.PRIMARY_WAVE_DIRECTION.toString()).getUnit().toString(), 
					"SW", Arrows30PxFactory.getArrowIcon(waveDirection, directionUnitTarget)));
			table.setWidget(2, 0, this.createTableItem("Altura ola", waveHeight + " " + forecastDTO.getMap().get(WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.toString()).getUnit().toString(), 
					"", Waves30PxFactory.getWaveIcon(waveHeight, heightUnitTarget)));
			table.setWidget(2, 1, this.createTableItem("Per�odo", wavePeriod, "", periodUnitTarget.toString()));
			
			table.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
			table.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_CENTER);
			table.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
			table.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_CENTER);
			
			this.setWidget(table);
		}
	}
	
	private VerticalPanel createTableItem(String title, String value, String imageTitle, Image image){
		VerticalPanel tableItem = new VerticalPanel();
		tableItem.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		Label tableItemValue = new Label(value);
		
		image.setTitle(imageTitle);
		image.setSize(MiniForecastPopup.ICON_SIZE, MiniForecastPopup.ICON_SIZE);
		
		Label tableItemTitle = new Label(title);
		tableItemTitle.addStyleName("gwt-Label-ForecastItem-Title");

		tableItem.add(tableItemTitle);
		tableItem.add(image);
		tableItem.add(tableItemValue);
		
		return tableItem;
	}
	
	private VerticalPanel createTableItem(String title, String value, String imageTitle, String unit){
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
}
