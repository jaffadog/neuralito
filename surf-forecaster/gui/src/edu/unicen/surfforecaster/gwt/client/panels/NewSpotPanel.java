package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.VerticalPanel;

public class NewSpotPanel extends VerticalPanel {
	
	private NewSpotDataPanel newSpotDataPanel = null;
	private NewSpotTrainPanel newSpotTrainPanel = null; 
	
	public NewSpotPanel() {
		newSpotDataPanel = new NewSpotDataPanel();
		add(newSpotDataPanel);
		newSpotTrainPanel = new NewSpotTrainPanel();
		newSpotTrainPanel.setVisible(false);
		add(newSpotTrainPanel);
	}

	public NewSpotDataPanel getNewSpotDataPanel() {
		return newSpotDataPanel;
	}

	public NewSpotTrainPanel getNewSpotTrainPanel() {
		return newSpotTrainPanel;
	}
}
