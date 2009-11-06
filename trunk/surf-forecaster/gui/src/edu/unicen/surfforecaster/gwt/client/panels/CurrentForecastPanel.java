package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.WW3Parameter;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;
import edu.unicen.surfforecaster.gwt.client.utils.images.arrows.s50.Arrows50PxFactory;
import edu.unicen.surfforecaster.gwt.client.utils.images.waves.s50.Waves50PxFactory;


public class CurrentForecastPanel extends FlexTable {

	public CurrentForecastPanel(){}
	
	public CurrentForecastPanel(String title, ForecastDTO forecast){
		{
			Label lblTitle = new Label(title);
			getFlexCellFormatter().setColSpan(0, 0, 2);
			getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
			setWidget(0,0, lblTitle);
			setCellSpacing(50);

			if (forecast != null) {
				//TODO generar las unidades en que se ve el sitio como alguna setting de usuario o usando cookies o algo y emprolijar la manera de levantarlo
				Unit heightUnitTarget = Unit.Meters;
				Unit speedUnitTarget = Unit.KilometersPerHour;
				Unit directionUnitTarget = Unit.Degrees;
				
				//wave height
				String waveHeight = forecast.getMap().get(WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.toString()).getValue();
				//wind speed
				String windSpeed = forecast.getMap().get(WW3Parameter.WIND_SPEED.toString()).getValue();
				//Wave direccion
				String waveDirection = forecast.getMap().get(WW3Parameter.PRIMARY_WAVE_DIRECTION.toString()).getValue();
				try {
					windSpeed = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(windSpeed, Unit.KilometersPerHour, speedUnitTarget));
					waveHeight = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveHeight, Unit.Meters, heightUnitTarget));
					waveDirection = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveDirection, Unit.Degrees, directionUnitTarget));
				} catch (NeuralitoException e) {
					// TODO ver como manejar esta exvepcion de conversion de unidades
					e.printStackTrace();
				}
				
				setWidget(1, 0, this.createTableItem("Viento", windSpeed + " " + forecast.getMap().get(WW3Parameter.WIND_SPEED.toString()).getUnit().toString(), "SSO", Arrows50PxFactory.getArrowIcon("23", windSpeed, directionUnitTarget, speedUnitTarget)));
				setWidget(1, 1, this.createTableItem("Dir. de la ola", waveDirection + " " + forecast.getMap().get(WW3Parameter.PRIMARY_WAVE_DIRECTION.toString()).getUnit().toString(), "Sudoeste", Arrows50PxFactory.getArrowIcon(waveDirection, directionUnitTarget)));
				setWidget(2, 0, this.createTableItem("Altura de la ola", waveHeight + " " + forecast.getMap().get(WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.toString()).getUnit().toString(), "", Waves50PxFactory.getWaveIcon(waveHeight, heightUnitTarget)));
				setWidget(2, 1, this.createTableItem("Período de ola", forecast.getMap().get(WW3Parameter.PRIMARY_WAVE_PERIOD.toString()).getValue() + " " + forecast.getMap().get(WW3Parameter.PRIMARY_WAVE_PERIOD.toString()).getUnit().toString(), "", "images/arrow.gif"));
			} else {
				lblTitle.setText(title + " - No disponible");
			}
		}
	}
	//TODO cuando todas la imagenes se levanten del factory eliminar este metodo que quedaria obsoleto
	private VerticalPanel createTableItem(String title, String value, String imageTitle, String imageUrl){
		VerticalPanel tableItem = new VerticalPanel();
		
		tableItem.setSpacing(10);
		tableItem.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		Label tableItemValue = new Label(value);
		Image image = new Image(imageUrl);
		image.setTitle(imageTitle);
		Label tableItemTitle = new Label(title);
		tableItem.add(tableItemValue);
		tableItem.add(image);
		tableItem.add(tableItemTitle);
		
		return tableItem;
	}
	
	private VerticalPanel createTableItem(String title, String value, String imageTitle, Image image){
		VerticalPanel tableItem = new VerticalPanel();
		
		tableItem.setSpacing(10);
		tableItem.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		Label tableItemValue = new Label(value);
		image.setTitle(imageTitle);
		image.setStyleName("gwt-image-wave");
		Label tableItemTitle = new Label(title);
		tableItem.add(tableItemValue);
		tableItem.add(image);
		tableItem.add(tableItemTitle);
		
		return tableItem;
	}
}
