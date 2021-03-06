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
import edu.unicen.surfforecaster.common.services.dto.WaveWatchParameter;
import edu.unicen.surfforecaster.gwt.client.dto.ForecastGwtDTO;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;
import edu.unicen.surfforecaster.gwt.client.utils.UnitTranslator;
import edu.unicen.surfforecaster.gwt.client.utils.images.arrows.s50.Arrows50PxFactory;
import edu.unicen.surfforecaster.gwt.client.utils.images.waves.s50.Waves50PxFactory;

public class CurrentForecastPanel extends FlexTable {

	private static final String ICON_SIZE = "50";
	private String currentForecasterName = null;
	
	public CurrentForecastPanel(){}
	//TODO aca tendria que mostrar el entrenado a menos que no tenga y mostrar el ww3, para esto tengo que ver que key de altura de ola uso del hash
	public CurrentForecastPanel(String forecasterName, String title, ForecastGwtDTO forecast){
		{
			//Panel stylename
			this.addStyleName("gwt-FlexTable-CurrentForecast");
			this.currentForecasterName = forecasterName;
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
				
				//TODO sacar los harcodeos del viento y poner bien los parametros
				//wave height
				String waveHeight = this.getWaveHeight(forecast);
				//wind speed
				String windSpeed = forecast.getMap().get(WaveWatchParameter.WIND_SPEED_V2.getValue()).getValue();
				//wind windDirection
				String windDirection = forecast.getMap().get(WaveWatchParameter.WIND_DIRECTION_V2.getValue()).getValue();
				//Wave direccion
				String waveDirection = forecast.getMap().get(WaveWatchParameter.PRIMARY_WAVE_DIRECTION_V2.getValue()).getValue();
				//Wave period
				String wavePeriod = forecast.getMap().get(WaveWatchParameter.PRIMARY_WAVE_PERIOD_V2.getValue()).getValue();
				try {
					windSpeed = NumberFormat.getFormat("###").format(UnitConverter.convertValue(windSpeed, Unit.KilometersPerHour, speedUnitTarget));
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
	
	/**
	 * Returns the waveHeight if the forecast is from ww3 forecaster or the improved wave height if the forecast is from a skilled predictor 
	 * @param forecastDTO
	 * @return String waveHeight
	 */
	private String getWaveHeight(ForecastGwtDTO forecastDTO) {
		if (this.currentForecasterName.equals(GWTUtils.WW3_FORECASTER_NAME))
			return forecastDTO.getMap().get(WaveWatchParameter.COMBINED_SWELL_WIND_WAVE_HEIGHT_V2.getValue()).getValue();
		else
			return forecastDTO.getMap().get("improvedWaveHeight").getValue();
	}
}
