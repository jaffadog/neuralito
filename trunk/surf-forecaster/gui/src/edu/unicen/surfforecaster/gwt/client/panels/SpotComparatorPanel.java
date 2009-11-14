package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.VerticalPanel;

public class SpotComparatorPanel extends VerticalPanel {

	private LinksLocalizationPanel localizationPanel;
	
	public SpotComparatorPanel() {
		{
			localizationPanel = new LinksLocalizationPanel();
			this.add(localizationPanel);
		}
	}

}
