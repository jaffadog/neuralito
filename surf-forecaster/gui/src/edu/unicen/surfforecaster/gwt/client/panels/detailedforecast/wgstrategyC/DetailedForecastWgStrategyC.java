package edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.wgstrategyC;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.gwt.client.dto.ForecastGwtDTO;
import edu.unicen.surfforecaster.gwt.client.panels.ErrorMsgPanel;
import edu.unicen.surfforecaster.gwt.client.panels.MessagePanel;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.IRenderDetailedForecastStrategy;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;

public class DetailedForecastWgStrategyC implements IRenderDetailedForecastStrategy {
	
	private VerticalPanel completeDetailedForecastVPanel = null;
	private Map<Integer, Map<String, List<ForecastGwtDTO>>> forecasters = null;
	List<Integer> spotsIds = null;
	List<String> spotsNames = null;
	List<String> forecastersNames = null;
	
	/**
	 * This Strategy shows a detalied forecast table for multiple spots for comparation purposes
	 * table in wg format. 
	 * @param forecasters
	 * @param spotsIds
	 * @param spotsNames
	 * @param forecastersNames
	 */
	public DetailedForecastWgStrategyC(Map<Integer, Map<String, List<ForecastGwtDTO>>> forecasters, List<Integer> spotsIds, List<String> spotsNames, 
			List<String> forecastersNames) {
		this.forecasters = forecasters;
		this.spotsIds = spotsIds;
		this.spotsNames = spotsNames;
		this.forecastersNames = forecastersNames;
		this.completeDetailedForecastVPanel = new VerticalPanel();
		this.completeDetailedForecastVPanel.setWidth("100%");
	}

	@Override
	public Widget renderDetailedForecast() {
		if (this.forecasters != null && this.forecasters.size() > 0) {
			completeDetailedForecastVPanel.add(new WgTableC(forecasters, spotsIds, spotsNames, forecastersNames, 0, 3));
			completeDetailedForecastVPanel.add(new Label(GWTUtils.LOCALE_CONSTANTS.continue_() + "..."));
	//		completeDetailedForecastVPanel.add(new WgTableC(forecasters, spotsIds, spotsNames, forecastersNames, 23, 46));
	//		completeDetailedForecastVPanel.add(new Label(GWTUtils.LOCALE_CONSTANTS.continue_() + "..."));
	//		completeDetailedForecastVPanel.add(new WgTableC(forecasters, spotsIds, spotsNames, forecastersNames, 46, null));
		} else {
			final MessagePanel errorPanel = new ErrorMsgPanel();
			errorPanel.setMessage(GWTUtils.LOCALE_CONSTANTS.NOT_SPOT_FORECASTERS_DEFINED());
			completeDetailedForecastVPanel.add(errorPanel);
		}
		
		return completeDetailedForecastVPanel;
	}
}
