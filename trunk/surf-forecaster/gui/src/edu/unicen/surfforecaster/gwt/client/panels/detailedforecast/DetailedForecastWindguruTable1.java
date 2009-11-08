package edu.unicen.surfforecaster.gwt.client.panels.detailedforecast;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;

public class DetailedForecastWindguruTable1 implements IRenderDetailedForecastStrategy {
	
	private VerticalPanel completeDetailedForecastVPanel = new VerticalPanel();
	private Map<String, List<ForecastDTO>> forecasters = null;
	
	public DetailedForecastWindguruTable1(Map<String, List<ForecastDTO>> forecasters) {
		this.forecasters = forecasters;
	}

	@Override
	public Widget renderDetailedForecast() {
		completeDetailedForecastVPanel.add(new DetailedForecastWindguruTableA(forecasters, 0, 5));
		completeDetailedForecastVPanel.add(new DetailedForecastWindguruTableA(forecasters, 5, 10));
		completeDetailedForecastVPanel.add(new DetailedForecastWindguruTableA(forecasters, 10, 15));
		return completeDetailedForecastVPanel;
	}
}
