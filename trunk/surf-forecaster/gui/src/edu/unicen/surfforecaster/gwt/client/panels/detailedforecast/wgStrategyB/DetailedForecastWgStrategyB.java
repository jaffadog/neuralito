package edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.wgStrategyB;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.IRenderDetailedForecastStrategy;

public class DetailedForecastWgStrategyB implements IRenderDetailedForecastStrategy {
	
	private VerticalPanel completeDetailedForecastVPanel = new VerticalPanel();
	private Map<String, List<ForecastDTO>> forecasters = null;
	
	public DetailedForecastWgStrategyB(Map<String, List<ForecastDTO>> forecasters) {
		this.forecasters = forecasters;
	}

	@Override
	public Widget renderDetailedForecast() {
		completeDetailedForecastVPanel.add(new WgTableB(forecasters, 0, 10));
		//completeDetailedForecastVPanel.add(new WgTableB(forecasters, 5, 10));
		//completeDetailedForecastVPanel.add(new WgTableB(forecasters, 10, 15));
		return completeDetailedForecastVPanel;
	}
}
