package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Vector;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.DecoratedTabPanel;

import edu.unicen.surfforecaster.gwt.client.GWTUtils;

public class ForecastTabPanel extends DecoratedTabPanel {
	
	private Vector<String> historyTokens = null;
	
	public ForecastTabPanel() {
		
		this.historyTokens = new Vector<String>();
		
		{
			ForecastPanel forecastPanel = new ForecastPanel();
			this.add(forecastPanel, GWTUtils.LOCALE_CONSTANTS.forecast());
			this.historyTokens.add("forecastTab");
		}
		{
			SpotDescriptionPanel waveDescriptionPanel = new SpotDescriptionPanel();
			this.add(waveDescriptionPanel, GWTUtils.LOCALE_CONSTANTS.spotDescription());
			this.historyTokens.add("descriptionTab");
		}
		{
			SpotComparatorPanel waveComparatorPanel = new SpotComparatorPanel();
			this.add(waveComparatorPanel, GWTUtils.LOCALE_CONSTANTS.spotComparator());
			this.historyTokens.add("comparatorTab");
		}
		{
			NewSpotPanel newWavePanel = new NewSpotPanel();
			this.add(newWavePanel, GWTUtils.LOCALE_CONSTANTS.newSpot());
			this.historyTokens.add("newSpotTab");
		}
		
		setAnimationEnabled(true);
		setWidth(GWTUtils.APLICATION_WIDTH);
		selectTab(0);
		
		addSelectionHandler(new SelectionHandler<Integer>() {
			public void onSelection(SelectionEvent<Integer> event) {
				History.newItem(historyTokens.get(event.getSelectedItem()));
			}
		});
		
	}
	
	public void setPanelState(String historyToken){
		int index = historyTokens.indexOf(historyToken); 
		if (index < 0 || index >= this.getTabBar().getTabCount())
			index = 0;
		selectTab(index);
	}
	
	

}
