package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Vector;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DecoratedTabPanel;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.gwt.client.SurfForecaster;
import edu.unicen.surfforecaster.gwt.client.UserServices;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;

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
		
		setAnimationEnabled(true);
		setWidth(GWTUtils.APLICATION_WIDTH);
		selectTab(0);
		
		addSelectionHandler(new SelectionHandler<Integer>() {
			public void onSelection(SelectionEvent<Integer> event) {
				System.out.println("ForecastTabPanel->SelectionTabHandler:" + historyTokens.get(event.getSelectedItem()));
				History.newItem(historyTokens.get(event.getSelectedItem()));
			}
		});
		
		/**
		 * Check if the current user has access more authorized panels
		 */
		this.showAuthorizadTabs();
	}
	
	private void showAuthorizadTabs(){
		UserServices.Util.getInstance().hasAccessTo("addSpot", new AsyncCallback<Boolean>(){
			public void onSuccess(Boolean result) {
				if (result) {
					NewSpotPanel newSpotPanel = new NewSpotPanel();
					add(newSpotPanel, GWTUtils.LOCALE_CONSTANTS.newSpot());					
					historyTokens.add("newSpotTab");
					
					MySpotsPanel mySpotsPanel = new MySpotsPanel();
					add(mySpotsPanel, GWTUtils.LOCALE_CONSTANTS.mySpots());					
					historyTokens.add("mySpotsTab");
				}
				SurfForecaster.getInstance().gotoHistoryToken();
			}

			public void onFailure(Throwable caught) {
				if (((NeuralitoException)caught).getErrorCode().equals(ErrorCode.USER_SESSION_EMPTY_OR_EXPIRED) && 
						Cookies.getCookie("surfForecaster-Username") != null) {
					GWTUtils.showSessionExpiredLoginBox();
				}
				SurfForecaster.getInstance().gotoHistoryToken();
			}
		});
	}
	
	public void setPanelState(String historyToken){
		int index = historyTokens.indexOf(historyToken); 
		if (index < 0 || index >= this.getTabBar().getTabCount())
			index = 0;
		
		System.out.println("ForecastTabPanel->select tab: " + historyToken);
		selectTab(index);
	}
	
	

}
