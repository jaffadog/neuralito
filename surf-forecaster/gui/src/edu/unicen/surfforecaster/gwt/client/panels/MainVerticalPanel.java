package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.gwt.client.GWTUtils;

public class MainVerticalPanel extends VerticalPanel {
	
	private ForecastTabPanel forecastTabPanel = null;
	
	public MainVerticalPanel() {
		
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.setWidth(GWTUtils.APLICATION_WIDTH);
		{
			forecastTabPanel = new ForecastTabPanel();
			this.add(forecastTabPanel);
		}
		
	}
	
//	public void createForecastTabPanel() {
//		System.out.println("creando tab Panel...");
//		if (forecastTabPanel != null) {
//			this.remove(forecastTabPanel);
//			forecastTabPanel = null;
//		}
//		forecastTabPanel = new ForecastTabPanel();
//		this.add(forecastTabPanel);
//	}
	
	public void setPanelState(String historyToken){
		System.out.println("MainVerticalPanel->setPanelState: " + historyToken);
		this.forecastTabPanel.setPanelState(historyToken);
	}
}
