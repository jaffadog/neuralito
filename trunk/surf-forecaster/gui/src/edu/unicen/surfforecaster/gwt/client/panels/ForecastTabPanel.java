package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Vector;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DecoratedTabPanel;

import edu.unicen.surfforecaster.gwt.client.ForecastCommonServices;
import edu.unicen.surfforecaster.gwt.client.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.SessionData;

public class ForecastTabPanel extends DecoratedTabPanel {
	
	private Vector<String> historyTokens = null;
	
	public ForecastTabPanel() {
		
		this.historyTokens = new Vector<String>();
		
		{
			ForecastPanel forecastPanel = new ForecastPanel();
			this.add(forecastPanel, GWTUtils.LOCALE_CONSTANTS.forecast());
			this.historyTokens.add("forecastTab");
			if (!GWTUtils.VALID_HISTORY_TOKENS.contains("forecastTab"))
				GWTUtils.VALID_HISTORY_TOKENS.add("forecastTab");
		}
		{
			SpotDescriptionPanel waveDescriptionPanel = new SpotDescriptionPanel();
			this.add(waveDescriptionPanel, GWTUtils.LOCALE_CONSTANTS.spotDescription());
			this.historyTokens.add("descriptionTab");
			if (!GWTUtils.VALID_HISTORY_TOKENS.contains("descriptionTab"))
				GWTUtils.VALID_HISTORY_TOKENS.add("descriptionTab");
		}
		{
			SpotComparatorPanel waveComparatorPanel = new SpotComparatorPanel();
			this.add(waveComparatorPanel, GWTUtils.LOCALE_CONSTANTS.spotComparator());
			this.historyTokens.add("comparatorTab");
			if (!GWTUtils.VALID_HISTORY_TOKENS.contains("comparatorTab"))
				GWTUtils.VALID_HISTORY_TOKENS.add("comparatorTab");
		}
		
		setAnimationEnabled(true);
		setWidth(GWTUtils.APLICATION_WIDTH);
		selectTab(0);
		
		addSelectionHandler(new SelectionHandler<Integer>() {
			public void onSelection(SelectionEvent<Integer> event) {
				System.out.println("ForecastTabPanel->SelectionTabHandler:" + historyTokens.get(event.getSelectedItem()));
				History.newItem(historyTokens.get(event.getSelectedItem()));
			}
		});
		
		//Check if exist any opened session
		this.getSessionData();
	}
	
	private void getSessionData(){
		ForecastCommonServices.Util.getInstance().getSessionData(new AsyncCallback<SessionData>(){
			public void onSuccess(SessionData result) {
				if (result != null) {
					NewSpotPanel newSpotPanel = new NewSpotPanel();
					add(newSpotPanel, GWTUtils.LOCALE_CONSTANTS.newSpot());					
					historyTokens.add("newSpotTab");
					if (!GWTUtils.VALID_HISTORY_TOKENS.contains("newSpotTab"))
						GWTUtils.VALID_HISTORY_TOKENS.add("newSpotTab");
				}
			}

			public void onFailure(Throwable caught) {
				
			}
		});
	}
	
	public void setPanelState(String historyToken){
		int index = historyTokens.indexOf(historyToken); 
		if (index < 0 || index >= this.getTabBar().getTabCount())
			index = 0;
		
		//if selected tab equals to newSpotTab (LazyPanel) its visibility should be set to true 
		if (historyTokens.get(index).equals("newSpotTab")) {
			((NewSpotPanel)this.getWidget(index)).getNewSpotDataPanel().setVisible(true);
		}
		System.out.println("ForecastTabPanel->select tab: " + historyToken);
		selectTab(index);
	}
	
	

}
