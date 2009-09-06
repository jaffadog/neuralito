package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.gwt.client.GWTUtils;

public class MainVerticalPanel extends VerticalPanel {
	
	public MainVerticalPanel() {
		
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.setWidth(GWTUtils.APLICATION_WIDTH);
		{
			LocalizationPanel localizationPanel = new LocalizationPanel();
			this.add(localizationPanel);
		}
		{
			ForecastTabPanel forecastTabPanel = new ForecastTabPanel();
			this.add(forecastTabPanel);
		}
		
	}
}
