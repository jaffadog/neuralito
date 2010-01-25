package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.gwt.client.ForecastServices;
import edu.unicen.surfforecaster.gwt.client.dto.ForecastGwtDTO;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.RenderDetailedForecastContext;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.wgStrategyB.DetailedForecastWgStrategyB;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;

public class ForecastPanel extends VerticalPanel {
	
	private LocalizationPanel localizationPanel = null;;
	//private VerticalPanel container = null;
	private Widget detailedForecast = new Widget();
	private LoadingPanel loadingPanel = null;
	
	public ForecastPanel() {
		this.setWidth("100%");
		
		{
			localizationPanel = new LocalizationPanel();
			localizationPanel.setBasePanel(this);
			this.add(localizationPanel);
		}
	}
	
	public void getSpotLastestForecast(){
		this.detailedForecast = new Widget();
		if (this.loadingPanel != null)
			this.loadingPanel.removeFromParent();
		this.loadingPanel = new LoadingPanel(GWTUtils.LOCALE_CONSTANTS.loadingSpotForecast());
		this.loadingPanel.addStyleName("gwt-VerticalPanel-LoadingPanel-ForecastTab");
		this.add(this.loadingPanel);
		final Integer spotId = new Integer(localizationPanel.getSpotBoxDisplayValue());
		ForecastServices.Util.getInstance().getLatestForecasts(spotId, new AsyncCallback<Map<String, List<ForecastGwtDTO>>>(){
			public void onSuccess(Map<String, List<ForecastGwtDTO>> result) {
				showSpotLatestForecast(result, spotId);
			}
				
			public void onFailure(Throwable caught) {
				
			}
		});
	}
	
	private void showSpotLatestForecast(Map<String, List<ForecastGwtDTO>> forecasters, Integer spotId) {
		this.clear();
		this.add(this.localizationPanel);
		RenderDetailedForecastContext renderContext = new RenderDetailedForecastContext(new DetailedForecastWgStrategyB(forecasters, localizationPanel, spotId));
		this.detailedForecast = renderContext.executeRenderStrategy(); 
		this.add(this.detailedForecast);
	}

}
