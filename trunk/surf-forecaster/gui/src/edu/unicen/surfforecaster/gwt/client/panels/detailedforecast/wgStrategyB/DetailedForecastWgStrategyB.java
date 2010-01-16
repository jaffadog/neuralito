package edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.wgStrategyB;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.gwt.client.dto.ForecastGwtDTO;
import edu.unicen.surfforecaster.gwt.client.panels.CurrentForecastPanel;
import edu.unicen.surfforecaster.gwt.client.panels.ErrorMsgPanel;
import edu.unicen.surfforecaster.gwt.client.panels.ILocalizationPanel;
import edu.unicen.surfforecaster.gwt.client.panels.MessagePanel;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.IRenderDetailedForecastStrategy;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;

public class DetailedForecastWgStrategyB implements IRenderDetailedForecastStrategy {
	
	private VerticalPanel completeDetailedForecastVPanel = null;
	private Map<String, List<ForecastGwtDTO>> forecasters = null;
	private ILocalizationPanel localizationPanel = null;
	
	/**
	 * This Strategy shows 2 panels with the next two forecasts (now and +3 hours), and shows below a detalied forecast
	 * table in wg format. 
	 * @param forecasters
	 * @param localizationPanel
	 */
	public DetailedForecastWgStrategyB(Map<String, List<ForecastGwtDTO>> forecasters, ILocalizationPanel localizationPanel) {
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
		
		if (this.forecasters != null && this.forecasters.size() > 0) {
			//Subtitle
			Label lblSubTitle = new Label("04-10-09 > [UTC-10] Pacific/Rarotonga, CKT");
			completeDetailedForecastVPanel.add(lblSubTitle);
			
			//Current forecast
			FlexTable flexTable = new FlexTable();
			List<ForecastGwtDTO> forecaster = null;
			String selectedForecasterName = null;
			//if spot have more than one forecaster, use the first diffenrent than ww3 forecaster, else use ww3
			if (this.forecasters.size() > 1){
				Set<String> keys = forecasters.keySet();
				Iterator<String> i = keys.iterator();
				while (i.hasNext()) {
					String forecasterName = i.next();
					if (!forecasterName.equals(GWTUtils.WW3_FORECASTER_NAME)) {
						forecaster = forecasters.get(forecasterName);
						selectedForecasterName = forecasterName;
						break;
					}
				}
			} else {
				forecaster = forecasters.get(GWTUtils.WW3_FORECASTER_NAME);
				selectedForecasterName = GWTUtils.WW3_FORECASTER_NAME;
			}
			
			CurrentForecastPanel current = new CurrentForecastPanel(selectedForecasterName, GWTUtils.LOCALE_CONSTANTS.now(), forecaster.size() > 0 ? forecaster.get(0) : null);
			flexTable.setWidget(0, 0, current);
			CurrentForecastPanel nextHours = new CurrentForecastPanel(selectedForecasterName, "+" + GWTUtils.LOCALE_CONSTANTS.num_3() + " " + GWTUtils.LOCALE_CONSTANTS.hours(), forecaster.size() > 1 ? forecaster.get(1) : null);
			flexTable.setWidget(0, 1, nextHours);
			completeDetailedForecastVPanel.add(flexTable);
			completeDetailedForecastVPanel.setCellHorizontalAlignment(flexTable, HasHorizontalAlignment.ALIGN_CENTER);
			
			//Table title
			Label lblTableTitle = new Label(GWTUtils.LOCALE_CONSTANTS.detailedForecastsTable());
			lblTableTitle.addStyleName("gwt-Label-Title");
			completeDetailedForecastVPanel.add(lblTableTitle);
			
			completeDetailedForecastVPanel.add(new WgTableB(forecasters, 0, 23));
			completeDetailedForecastVPanel.add(new Label(GWTUtils.LOCALE_CONSTANTS.continue_() + "..."));
			completeDetailedForecastVPanel.add(new WgTableB(forecasters, 23, 46));
			completeDetailedForecastVPanel.add(new Label(GWTUtils.LOCALE_CONSTANTS.continue_() + "..."));
			completeDetailedForecastVPanel.add(new WgTableB(forecasters, 46, null));
		} else {
			final MessagePanel errorPanel = new ErrorMsgPanel();
			errorPanel.setMessage(GWTUtils.LOCALE_CONSTANTS.NOT_SPOT_FORECASTERS_DEFINED());
			completeDetailedForecastVPanel.add(errorPanel);
		}
		
		return completeDetailedForecastVPanel;
	}
}
