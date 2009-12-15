package edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.wgstrategyC;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.IRenderDetailedForecastStrategy;

public class DetailedForecastWgStrategyC implements IRenderDetailedForecastStrategy {
	
	private VerticalPanel completeDetailedForecastVPanel = null;
	private Map<Integer, Map<String, List<ForecastDTO>>> forecasters = null;
	List<Integer> spotsIds = null;
	List<String> spotsNames = null;
	
	/**
	 * This Strategy shows a detalied forecast table for multiple spots for comparation purposes
	 * table in wg format. 
	 * @param forecasters
	 * @param spotsIds
	 * @param spotsNames
	 */
	public DetailedForecastWgStrategyC(Map<Integer, Map<String, List<ForecastDTO>>> forecasters, List<Integer> spotsIds, List<String> spotsNames) {
		this.forecasters = forecasters;
		this.spotsIds = spotsIds;
		this.spotsNames = spotsNames;
		this.completeDetailedForecastVPanel = new VerticalPanel();
		this.completeDetailedForecastVPanel.setWidth("100%");
	}

	@Override
	public Widget renderDetailedForecast() {
		completeDetailedForecastVPanel.add(new WgTableC(forecasters, spotsIds, spotsNames, 0, 3));
//		completeDetailedForecastVPanel.add(new Label(GWTUtils.LOCALE_CONSTANTS.continue_() + "..."));
//		completeDetailedForecastVPanel.add(new WgTableC(forecasters, spotsIds, spotsNames, 23, 46));
//		completeDetailedForecastVPanel.add(new Label(GWTUtils.LOCALE_CONSTANTS.continue_() + "..."));
//		completeDetailedForecastVPanel.add(new WgTableC(forecasters, spotsIds, spotsNames, 46, null));
		
		return completeDetailedForecastVPanel;
	}
}
