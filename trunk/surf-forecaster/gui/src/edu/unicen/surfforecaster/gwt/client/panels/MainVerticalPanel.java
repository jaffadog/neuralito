package edu.unicen.surfforecaster.gwt.client.panels;


import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;


public class MainVerticalPanel extends VerticalPanel {
	
	private ForecastTabPanel forecastTabPanel = null;
	AnonymousMessagePanel anonymousMessagePanel;
	
	public MainVerticalPanel() {
		
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		{
			anonymousMessagePanel = new AnonymousMessagePanel();
			this.add(anonymousMessagePanel);
		}
		{
			forecastTabPanel = new ForecastTabPanel();
			this.add(forecastTabPanel);
		}
		
	}
	
	public void setPanelState(String historyToken){
		//System.out.println("MainVerticalPanel->setPanelState: " + historyToken);
		this.forecastTabPanel.setPanelState(historyToken);
	}
}
