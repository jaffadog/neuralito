package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.gwt.client.ForecastServices;
import edu.unicen.surfforecaster.gwt.client.dto.ForecastGwtDTO;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.RenderDetailedForecastContext;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.wgStrategyB.DetailedForecastWgStrategyB;

public class ForecastPanel extends LazyPanel {
	
	private LocalizationPanel localizationPanel = null;;
	private VerticalPanel container = null;
	private Widget detailedForecast = new Widget();
	
	public ForecastPanel() {
	}

	@Override
	protected Widget createWidget() {
		
		container = new VerticalPanel();
		container.setWidth("100%");
		
		{
			localizationPanel = new LocalizationPanel();
			localizationPanel.setBasePanel(this);
			container.add(localizationPanel);
		}
		
		return container;
	}
	
	public void getSpotLastestForecast(){
		this.detailedForecast.removeFromParent();
		this.detailedForecast = new Widget();
		ForecastServices.Util.getInstance().getLatestForecasts(new Integer(localizationPanel.getSpotBoxDisplayValue()), new AsyncCallback<Map<String, List<ForecastGwtDTO>>>(){
			public void onSuccess(Map<String, List<ForecastGwtDTO>> result) {
				showSpotLatestForecast(result);
			}
				
			public void onFailure(Throwable caught) {
				
			}
		});
		
		
	}
	
	private void showSpotLatestForecast(Map<String, List<ForecastGwtDTO>> forecasters) {
		RenderDetailedForecastContext renderContext = new RenderDetailedForecastContext(new DetailedForecastWgStrategyB(forecasters, localizationPanel));
		this.detailedForecast = renderContext.executeRenderStrategy(); 
		container.add(this.detailedForecast);
	}

}
