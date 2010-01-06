package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.gwt.client.ForecastServices;
import edu.unicen.surfforecaster.gwt.client.dto.ForecastGwtDTO;

public class SpotComparatorPanel extends VerticalPanel {
	private DeckPanel deckPanel = null;
	
	private static int CREATE_COMP_PANEL_INDEX = -1;
	private static int VIEW_COMP_PANEL_INDEX = -1;
	public static final int MAX_SPOTS_TO_COMP = 5;
	public static final int MIN_SPOTS_TO_COMP = 2;
	
	
	public SpotComparatorPanel() {
		deckPanel = new DeckPanel();
		{
			ComparationCreatorPanel createComparationPanel = new ComparationCreatorPanel();
			createComparationPanel.setBasePanel(this);
			deckPanel.add(createComparationPanel);
			deckPanel.setAnimationEnabled(true);
			SpotComparatorPanel.CREATE_COMP_PANEL_INDEX = deckPanel.getWidgetIndex(createComparationPanel);
		}
		{
			ComparationViewerPanel comparationViewerPanel = new ComparationViewerPanel();
			comparationViewerPanel.setBasePanel(this);
			deckPanel.add(comparationViewerPanel);
			SpotComparatorPanel.VIEW_COMP_PANEL_INDEX = deckPanel.getWidgetIndex(comparationViewerPanel);
		}
		this.add(deckPanel);
		this.showComparationCreatorPanel();
	}
	
	public void showComparationViewerPanel() {
		deckPanel.showWidget(SpotComparatorPanel.VIEW_COMP_PANEL_INDEX);
	}
	
	public void showComparationCreatorPanel() {
		deckPanel.showWidget(SpotComparatorPanel.CREATE_COMP_PANEL_INDEX);
	}

	public void generateSpotsComparation(final List<Integer> selectedSpots, final List<String> selectedSpotsNames) {
		ForecastServices.Util.getInstance().getLatestForecasts(selectedSpots, new AsyncCallback<Map<Integer, Map<String, List<ForecastGwtDTO>>>>(){
			public void onSuccess(Map<Integer, Map<String, List<ForecastGwtDTO>>> result) {
				((ComparationViewerPanel)deckPanel.getWidget(SpotComparatorPanel.VIEW_COMP_PANEL_INDEX)).renderComparation(result, selectedSpots, selectedSpotsNames);
				showComparationViewerPanel();
			}
				
			public void onFailure(Throwable caught) {
				//TODO do something when the getlastest forecasts methos fails
			}
		});
	}

}
