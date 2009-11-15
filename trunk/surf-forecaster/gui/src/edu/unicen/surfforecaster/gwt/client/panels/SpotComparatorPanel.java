package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.gwt.client.SpotServices;

public class SpotComparatorPanel extends VerticalPanel {

	private LinksLocalizationPanel localizationPanel;
	private DeckPanel deckPanel = null;
	//private CreateComparationPanel createComparationPanel;
	private static int CREATE_COMP_PANEL_INDEX = -1;
	
	
	public SpotComparatorPanel() {
		{
			this.setWidth("100%");
			{
				localizationPanel = new LinksLocalizationPanel(false, false);
				localizationPanel.setBasePanel(this);
				this.add(localizationPanel);
			}
			{
				deckPanel = new DeckPanel();
				{
					CreateComparationPanel createComparationPanel = new CreateComparationPanel();
					deckPanel.add(createComparationPanel);
					deckPanel.setAnimationEnabled(true);
					SpotComparatorPanel.CREATE_COMP_PANEL_INDEX = deckPanel.getWidgetIndex(createComparationPanel);
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

}
