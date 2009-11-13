package edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.wgStrategyB;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.IRenderDetailedForecastStrategy;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;

public class DetailedForecastWgStrategyB implements IRenderDetailedForecastStrategy {
	
	private VerticalPanel completeDetailedForecastVPanel = new VerticalPanel();
	private Map<String, List<ForecastDTO>> forecasters = null;
	
	public DetailedForecastWgStrategyB(Map<String, List<ForecastDTO>> forecasters) {
		this.forecasters = forecasters;
	}

	@Override
	public Widget renderDetailedForecast() {
		completeDetailedForecastVPanel.add(new WgTableB(forecasters, 0, 1));
		completeDetailedForecastVPanel.add(new Label(GWTUtils.LOCALE_CONSTANTS.continue_() + "..."));
//		completeDetailedForecastVPanel.add(new WgTableB(forecasters, 23, 46));
//		completeDetailedForecastVPanel.add(new Label(GWTUtils.LOCALE_CONSTANTS.continue_() + "..."));
//		completeDetailedForecastVPanel.add(new WgTableB(forecasters, 46, null));
		return completeDetailedForecastVPanel;
	}
}
