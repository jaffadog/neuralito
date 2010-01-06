package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.WW3Parameter;
import edu.unicen.surfforecaster.gwt.client.dto.ForecastGwtDTO;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;
import edu.unicen.surfforecaster.gwt.client.utils.UnitTranslator;
import edu.unicen.surfforecaster.gwt.client.utils.images.arrows.s50.Arrows50PxFactory;
import edu.unicen.surfforecaster.gwt.client.utils.images.waves.s50.Waves50PxFactory;


public class CurrentForecastPanel extends FlexTable {

	private static final String ICON_SIZE = "50";
	
	public CurrentForecastPanel(){}
	
	public CurrentForecastPanel(String title, ForecastGwtDTO forecast){
		{
			//Panel stylename
			this.addStyleName("gwt-FlexTable-CurrentForecast");
			
			Label lblTitle = new Label(title);
			lblTitle.addStyleName("gwt-Label-CurrentForecast-Title");
			this.setWidget(0,0, lblTitle);
			this.getFlexCellFormatter().setColSpan(0, 0, 2);
			this.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
			
			if (forecast != null) {
				//TODO generar las unidades en que se ve el sitio como alguna setting de usuario o usando cookies o algo y emprolijar la manera de levantarlo
				Unit heightUnitTarget = Unit.Meters;
				Unit speedUnitTarget = Unit.KilometersPerHour;
				Unit directionUnitTarget = Unit.Degrees;
				Unit periodUnitTarget = Unit.Seconds;
				
				//wave height
				String waveHeight = forecast.getMap().get(WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.toString()).getValue();
				//wind speed
				String windSpeed = forecast.getMap().get(WW3Parameter.WIND_SPEED.toString()).getValue();
				//wind windDirection
				String windDirection = forecast.getMap().get(WW3Parameter.WIND_DIRECTION.toString()).getValue();
				//Wave direccion
				String waveDirection = forecast.getMap().get(WW3Parameter.PRIMARY_WAVE_DIRECTION.toString()).getValue();
				//Wave period
				String wavePeriod = forecast.getMap().get(WW3Parameter.PRIMARY_WAVE_PERIOD.toString()).getValue();
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
				
				this.setWidget(1, 0, this.createTableItem(GWTUtils.LOCALE_CONSTANTS.wind(), windSpeed + " " + UnitTranslator.getUnitAbbrTranlation(speedUnitTarget), Arrows50PxFactory.getArrowIcon(windDirection, windSpeed, directionUnitTarget, speedUnitTarget)));
				this.setWidget(1, 1, this.createTableItem(GWTUtils.LOCALE_CONSTANTS.wave_direction(), "", Arrows50PxFactory.getArrowIcon(waveDirection, directionUnitTarget)));
				this.setWidget(2, 0, this.createTableItem(GWTUtils.LOCALE_CONSTANTS.wave_height(), waveHeight + " " + UnitTranslator.getUnitAbbrTranlation(heightUnitTarget), Waves50PxFactory.getWaveIcon(waveHeight, heightUnitTarget)));
				this.setWidget(2, 1, this.createTableItem(GWTUtils.LOCALE_CONSTANTS.wave_period(), wavePeriod, UnitTranslator.getUnitAbbrTranlation(periodUnitTarget)));
			} else {
				this.setWidget(1, 0, new Label(GWTUtils.LOCALE_CONSTANTS.not_available()));
				this.getFlexCellFormatter().setColSpan(1, 0, 2);
			}
		}
	}
	
	private VerticalPanel createTableItem(String title, String value, String unit){
		VerticalPanel tableItem = new VerticalPanel();
		tableItem.addStyleName("gwt-VerticalPanel-ForecastItem");
		tableItem.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		

		Label tableItemTitle = new Label(title);
		tableItemTitle.addStyleName("gwt-Label-ForecastItem-Title");
		tableItem.add(tableItemTitle);
		
		Label tableItemValue = new Label(value);
		tableItemValue.addStyleName("gwt-Label-ForecastItem-Value");
		tableItem.add(tableItemValue);
		tableItem.setCellHeight(tableItemValue, CurrentForecastPanel.ICON_SIZE);
		tableItem.setCellVerticalAlignment(tableItemValue, HasVerticalAlignment.ALIGN_MIDDLE);
		
		Label tableItemUnit = new Label(unit);
		tableItem.add(tableItemUnit);
		
		return tableItem;
	}
	
	private VerticalPanel createTableItem(String title, String value, Image image){
		VerticalPanel tableItem = new VerticalPanel();
		tableItem.addStyleName("gwt-VerticalPanel-ForecastItem");
		tableItem.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		Label tableItemValue = new Label(value == "" ? image.getTitle() : value);
		
		image.setSize(CurrentForecastPanel.ICON_SIZE, CurrentForecastPanel.ICON_SIZE);
		
		Label tableItemTitle = new Label(title);
		tableItemTitle.addStyleName("gwt-Label-ForecastItem-Title");
		
		tableItem.add(tableItemTitle);
		tableItem.add(image);
		tableItem.add(tableItemValue);
		
		return tableItem;
	}
}
