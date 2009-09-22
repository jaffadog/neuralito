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
			this.add(LocalizationPanel.getInstance());
		}
//		{
//			forecastTabPanel = new ForecastTabPanel();
//			this.add(forecastTabPanel);
//		}
		
	}
	
	public void createForecastTabPanel() {
		forecastTabPanel = new ForecastTabPanel();
		this.add(forecastTabPanel);
	}
	
	public void setPanelState(String historyToken){
		this.forecastTabPanel.setPanelState(historyToken);
	}
}
