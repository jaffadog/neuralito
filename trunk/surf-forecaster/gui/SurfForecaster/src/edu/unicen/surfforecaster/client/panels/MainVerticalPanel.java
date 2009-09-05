package edu.unicen.surfforecaster.client.panels;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.client.GWTUtils;
import edu.unicen.surfforecaster.client.SurfForecasterConstants;

public class MainVerticalPanel extends VerticalPanel {
	
	public MainVerticalPanel(){}
	
	/**
	 * @wbp.parser.constructor
	 */
	public MainVerticalPanel(SurfForecasterConstants localeConstants) {
		
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.setWidth(GWTUtils.APLICATION_WIDTH);
		{
			LocalizationPanel localizationPanel = new LocalizationPanel(localeConstants);
			this.add(localizationPanel);
		}
		{
			ForecastTabPanel forecastTabPanel = new ForecastTabPanel();
			this.add(forecastTabPanel);
		}
		
	}
}
