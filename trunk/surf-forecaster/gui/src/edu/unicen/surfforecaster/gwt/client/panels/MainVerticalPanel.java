package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.gwt.client.GWTUtils;

public class MainVerticalPanel extends VerticalPanel {
	
	private ForecastTabPanel forecastTabPanel = null;
	
	public MainVerticalPanel() {
		
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.setWidth(GWTUtils.APLICATION_WIDTH);
		{
			LocalizationPanel localizationPanel = new LocalizationPanel();
			this.add(localizationPanel);
		}
		{
			forecastTabPanel = new ForecastTabPanel();
			this.add(forecastTabPanel);
		}
		
	}
	
	public void setPanelState(String historyToken){
		this.forecastTabPanel.setPanelState(historyToken);
	}
}
