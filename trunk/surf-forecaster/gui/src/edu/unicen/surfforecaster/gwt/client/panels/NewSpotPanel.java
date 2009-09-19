package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.gwt.client.GWTUtils;

public class NewSpotPanel extends VerticalPanel {
	
	NewSpotDataPanel newSpotDataPanel = null;
	NewSpotTrainPanel newSpotTrainPanel = null; 
	
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
