package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.gwt.client.SpotServices;

public class SpotComparatorPanel extends VerticalPanel {

	private LinksLocalizationPanel localizationPanel;
	private DeckPanel deckPanel = null;
	//private CreateComparationPanel createComparationPanel;
	private static int CREATE_COMP_PANEL_INDEX = -1;
	private static int SHOW_COMP_PANEL_INDEX = -1;
	
	
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
					CreateComparationPanel createComparationPanel = new CreateComparationPanel();
					createComparationPanel.setBasePanel(this);
					deckPanel.add(createComparationPanel);
					deckPanel.setAnimationEnabled(true);
					SpotComparatorPanel.CREATE_COMP_PANEL_INDEX = deckPanel.getWidgetIndex(createComparationPanel);
					
					ShowComparationPanel showComparationPanel = new ShowComparationPanel();
					showComparationPanel.setBasePanel(this);
					deckPanel.add(showComparationPanel);
					SpotComparatorPanel.SHOW_COMP_PANEL_INDEX = deckPanel.getWidgetIndex(showComparationPanel);
				}
				this.add(deckPanel);
			}
		}
	}
	
	public void fillSpotsSelector() {
		Integer zoneId = new Integer(localizationPanel.getZoneBoxDisplayValue());
		SpotServices.Util.getInstance().getSpots(zoneId, new AsyncCallback<List<SpotDTO>>(){
			public void onSuccess(List<SpotDTO> result) {
				((CreateComparationPanel)deckPanel.getWidget(SpotComparatorPanel.CREATE_COMP_PANEL_INDEX)).fillSpotsListBox(result);
				deckPanel.showWidget(SpotComparatorPanel.CREATE_COMP_PANEL_INDEX);
			}
				
			public void onFailure(Throwable caught) {
				//TODO do something when the getspots methos fails
			}
		});
	}
	
	public void showShowComparationPanel() {
		System.out.println(1111);
		deckPanel.showWidget(SpotComparatorPanel.SHOW_COMP_PANEL_INDEX);
	}
	
	public void showCreateComparationPanel() {
		deckPanel.showWidget(SpotComparatorPanel.CREATE_COMP_PANEL_INDEX);
	}

}
