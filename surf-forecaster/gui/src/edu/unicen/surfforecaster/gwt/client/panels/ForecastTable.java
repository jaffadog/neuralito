package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.WW3Parameter;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;
import edu.unicen.surfforecaster.gwt.client.utils.images.waves.s50.Waves50PxFactory;

public class ForecastTable extends FlexTable {

	public ForecastTable(List<ForecastDTO> forecasts) {
		this.setWidget(0, 0, new Label("WW3"));
		this.setWidget(1, 0, this.getLabelsPanel());
		
		this.printForecast(forecasts);
	}
	
	private VerticalPanel getLabelsPanel() {
		VerticalPanel labelsPanel = new VerticalPanel();
		labelsPanel.add(new Label("Altura ola(m)"));
		labelsPanel.add(new Label("---"));
		labelsPanel.add(new Label("Direccion olas"));
		labelsPanel.add(new Label("Periodo(s)"));
		labelsPanel.add(new Label("Direccion viento"));
		labelsPanel.add(new Label("Vel. viento(Km/s)"));
		return labelsPanel;
	}
	
	private VerticalPanel getDatePanel(ForecastDTO forecastDTO) {
		
		VerticalPanel datePanel = new VerticalPanel();
		datePanel.add(new Label("Lu"));
		datePanel.add(new Label("02"));
		datePanel.add(new Label("16h"));
		
		return datePanel;
	}
	
	private VerticalPanel getForecastPanel(ForecastDTO forecast) {
		VerticalPanel labelsPanel = new VerticalPanel();
		
		String waveHeight = forecast.getMap().get(WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.toString()).getValue();
		//TODO generar las unidades en que se ve el sitio como alguna setting de usuario o usando cookies o algo y emprolijar la manera de levantarlo
		Unit target = Unit.Meters;
		try {
			waveHeight = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveHeight, Unit.Meters, target));
		} catch (NeuralitoException e) {
			// TODO ver como manejar esta exvepcion de conversion de unidades
			e.printStackTrace();
		}
		
		labelsPanel.add(Waves50PxFactory.getWaveIcon(waveHeight, target));
		labelsPanel.add(new Label(waveHeight));
		labelsPanel.add(new Label(forecast.getMap().get(WW3Parameter.PRIMARY_WAVE_DIRECTION.toString()).getValue()));
		labelsPanel.add(new Label(forecast.getMap().get(WW3Parameter.PRIMARY_WAVE_PERIOD.toString()).getValue()));
		labelsPanel.add(new Label("234"));
		labelsPanel.add(new Label(forecast.getMap().get(WW3Parameter.WIND_SPEED.toString()).getValue()));
		
		return labelsPanel;
	}
	
	private void printForecast(List<ForecastDTO> forecasts) {
		Iterator<ForecastDTO> i = forecasts.iterator();
		int forecastIndex = 1;
		while (i.hasNext()) {
			ForecastDTO forecastDTO = i.next();
			this.setWidget(0, forecastIndex, this.getDatePanel(forecastDTO));
			this.setWidget(1, forecastIndex, this.getForecastPanel(forecastDTO));
			forecastIndex++;
		}
	}

}
