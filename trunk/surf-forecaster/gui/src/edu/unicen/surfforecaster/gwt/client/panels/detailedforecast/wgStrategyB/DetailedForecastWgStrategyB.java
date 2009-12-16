package edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.wgStrategyB;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.gwt.client.panels.CurrentForecastPanel;
import edu.unicen.surfforecaster.gwt.client.panels.ILocalizationPanel;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.IRenderDetailedForecastStrategy;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;

public class DetailedForecastWgStrategyB implements IRenderDetailedForecastStrategy {
	
	private VerticalPanel completeDetailedForecastVPanel = null;
	private Map<String, List<ForecastDTO>> forecasters = null;
	private ILocalizationPanel localizationPanel = null;
	
	/**
	 * This Strategy shows 2 panels with the next two forecasts (now and +3 hours), and shows below a detalied forecast
	 * table in wg format. 
	 * @param forecasters
	 * @param localizationPanel
	 */
	public DetailedForecastWgStrategyB(Map<String, List<ForecastDTO>> forecasters, ILocalizationPanel localizationPanel) {
		this.forecasters = forecasters;
		this.localizationPanel = localizationPanel;
		this.completeDetailedForecastVPanel = new VerticalPanel();
		this.completeDetailedForecastVPanel.setWidth("100%");
	}

	@Override
	public Widget renderDetailedForecast() {
		//Title
		Label lblTitle = new Label(localizationPanel.getZoneBoxDisplayText() + " > " + localizationPanel.getSpotBoxDisplayText());
		lblTitle.addStyleName("gwt-Label-SectionTitle");
		completeDetailedForecastVPanel.add(lblTitle);
		
		//Subtitle
		Label lblSubTitle = new Label("04-10-09 > [UTC-10] Pacific/Rarotonga, CKT");
		//lblSubTitle.addStyleName("gwt-Label-SubTitle");
		completeDetailedForecastVPanel.add(lblSubTitle);
		
		//Current forecast
		FlexTable flexTable = new FlexTable();
		//TODO decidir como elijo y cual elijo de los forecasters del spot para mostrar los currentpanels, por ahora harcodeo para recuperar el ww3
		List<ForecastDTO> ww3Forecaster = forecasters.get("WW3 Noaa Forecaster");
		CurrentForecastPanel current = new CurrentForecastPanel(GWTUtils.LOCALE_CONSTANTS.now(), ww3Forecaster.size() > 0 ? ww3Forecaster.get(0) : null);
		flexTable.setWidget(0, 0, current);
		CurrentForecastPanel nextHours = new CurrentForecastPanel("+" + GWTUtils.LOCALE_CONSTANTS.num_3() + " " + GWTUtils.LOCALE_CONSTANTS.hours(), ww3Forecaster.size() > 1 ? ww3Forecaster.get(1) : null);
		flexTable.setWidget(0, 1, nextHours);
		completeDetailedForecastVPanel.add(flexTable);
		completeDetailedForecastVPanel.setCellHorizontalAlignment(flexTable, HasHorizontalAlignment.ALIGN_CENTER);
		
		completeDetailedForecastVPanel.add(new WgTableB(forecasters, 0, 13));
		completeDetailedForecastVPanel.add(new Label(GWTUtils.LOCALE_CONSTANTS.continue_() + "..."));
//		completeDetailedForecastVPanel.add(new WgTableB(forecasters, 23, 46));
//		completeDetailedForecastVPanel.add(new Label(GWTUtils.LOCALE_CONSTANTS.continue_() + "..."));
//		completeDetailedForecastVPanel.add(new WgTableB(forecasters, 46, null));
		
		return completeDetailedForecastVPanel;
	}
}
