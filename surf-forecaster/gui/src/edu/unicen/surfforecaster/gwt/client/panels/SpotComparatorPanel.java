package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.gwt.client.ForecastServices;
import edu.unicen.surfforecaster.gwt.client.SpotServices;

public class SpotComparatorPanel extends VerticalPanel {

	private LinksLocalizationPanel localizationPanel;
	private DeckPanel deckPanel = null;
	//private CreateComparationPanel createComparationPanel;
	private static int CREATE_COMP_PANEL_INDEX = -1;
	private static int VIEW_COMP_PANEL_INDEX = -1;
	
	
	public SpotComparatorPanel() {
		{
			//this.setWidth("100%");
			{
				localizationPanel = new LinksLocalizationPanel(false, false);
				localizationPanel.setBasePanel(this);
				this.add(localizationPanel);
			}
			{
				deckPanel = new DeckPanel();
				{
					ComparationCreatorPanel createComparationPanel = new ComparationCreatorPanel();
					createComparationPanel.setBasePanel(this);
					deckPanel.add(createComparationPanel);
					deckPanel.setAnimationEnabled(true);
					SpotComparatorPanel.CREATE_COMP_PANEL_INDEX = deckPanel.getWidgetIndex(createComparationPanel);
					
					ComparationViewerPanel comparationViewerPanel = new ComparationViewerPanel();
					comparationViewerPanel.setBasePanel(this);
					deckPanel.add(comparationViewerPanel);
					SpotComparatorPanel.VIEW_COMP_PANEL_INDEX = deckPanel.getWidgetIndex(comparationViewerPanel);
				}
				this.add(deckPanel);
			}
		}
	}
	
	public void fillSpotsSelector() {
		final Integer zoneId = new Integer(localizationPanel.getZoneBoxDisplayValue());
		SpotServices.Util.getInstance().getSpots(zoneId, new AsyncCallback<List<SpotDTO>>(){
			public void onSuccess(List<SpotDTO> result) {
				((ComparationCreatorPanel)deckPanel.getWidget(SpotComparatorPanel.CREATE_COMP_PANEL_INDEX)).fillSpotsListBox(result, zoneId);
				showComparationCreatorPanel();
			}
				
			public void onFailure(Throwable caught) {
				//TODO do something when the getspots methos fails
			}
		});
	}
	
	public void showComparationViewerPanel() {
		deckPanel.showWidget(SpotComparatorPanel.VIEW_COMP_PANEL_INDEX);
	}
	
	public void showComparationCreatorPanel() {
		deckPanel.showWidget(SpotComparatorPanel.CREATE_COMP_PANEL_INDEX);
	}

	public void generateSpotsComparation(final List<Integer> selectedSpots, final List<String> selectedSpotsNames) {
		ForecastServices.Util.getInstance().getLatestForecasts(selectedSpots, new AsyncCallback<Map<Integer, Map<String, List<ForecastDTO>>>>(){
			public void onSuccess(Map<Integer, Map<String, List<ForecastDTO>>> result) {
				((ComparationViewerPanel)deckPanel.getWidget(SpotComparatorPanel.VIEW_COMP_PANEL_INDEX)).renderComparation(result, selectedSpots, selectedSpotsNames);
				showComparationViewerPanel();
			}
				
			public void onFailure(Throwable caught) {
				//TODO do something when the getlastest forecasts methos fails
			}
		});
	}

}
