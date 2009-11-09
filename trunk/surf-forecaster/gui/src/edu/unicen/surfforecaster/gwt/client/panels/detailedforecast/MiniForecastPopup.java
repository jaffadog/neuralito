package edu.unicen.surfforecaster.gwt.client.panels.detailedforecast;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
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

public class MiniForecastPopup extends PopupPanel {

	public MiniForecastPopup(ForecastDTO forecastDTO) {
		super(false);
		
		if (forecastDTO != null) {
			FlexTable table = new FlexTable();
			
			//TODO generar las unidades en que se ve el sitio como alguna setting de usuario o usando cookies o algo y emprolijar la manera de levantarlo
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
			
			table.setWidget(0, 0, this.createTableItem("Viento", windSpeed + " " + forecastDTO.getMap().get(WW3Parameter.WIND_SPEED.toString()).getUnit().toString(), 
					"SSO", Arrows30PxFactory.getArrowIcon("23", windSpeed, directionUnitTarget, speedUnitTarget)));
			table.setWidget(0, 1, this.createTableItem("Dir. ola", waveDirection + " " + forecastDTO.getMap().get(WW3Parameter.PRIMARY_WAVE_DIRECTION.toString()).getUnit().toString(), 
					"SW", Arrows30PxFactory.getArrowIcon(waveDirection, directionUnitTarget)));
			table.setWidget(1, 0, this.createTableItem("Período", forecastDTO.getMap().get(WW3Parameter.PRIMARY_WAVE_PERIOD.toString()).getValue(), "", 
					forecastDTO.getMap().get(WW3Parameter.PRIMARY_WAVE_PERIOD.toString()).getUnit().toString()));
			
			this.setWidget(table);
			this.addStyleName("gwt-Popup-MiniForecast");
		}
	}
	
	private VerticalPanel createTableItem(String title, String value, String imageTitle, Image image){
		VerticalPanel tableItem = new VerticalPanel();
		
		tableItem.setSpacing(1);
		tableItem.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		Label tableItemValue = new Label(value);
		image.setTitle(imageTitle);
		Label tableItemTitle = new Label(title);

		tableItem.add(tableItemTitle);
		tableItem.add(image);
		tableItem.add(tableItemValue);
		
		return tableItem;
	}
	
	private VerticalPanel createTableItem(String title, String value, String imageTitle, String unit){
		VerticalPanel tableItem = new VerticalPanel();
		
		tableItem.setSpacing(1);
		tableItem.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		Label tableItemValue = new Label(value);
		Label tableItemTitle = new Label(title);
		Label tableItemUnit = new Label(unit);
		
		tableItem.add(tableItemTitle);
		tableItem.add(tableItemValue);
		tableItem.add(tableItemUnit);
		
		return tableItem;
	}
}
