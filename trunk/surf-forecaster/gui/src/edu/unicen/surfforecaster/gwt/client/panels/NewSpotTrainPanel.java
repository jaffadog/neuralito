package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class NewSpotTrainPanel extends LazyPanel {
	
	public NewSpotTrainPanel() {
	}

	@Override
	protected Widget createWidget() {
		{
			final VerticalPanel containerPanel = new VerticalPanel();
			{
				Label lblObsFile = new Label("Observaciones visuales: ");
				containerPanel.add(lblObsFile);
			}
			{
				TextBox textBox = new TextBox();
				containerPanel.add(textBox);
			}
			{
				Button btnSave = new Button("New button");
				btnSave.setText("Grabar");
				containerPanel.add(btnSave);
			}
			{
				Button btnBack = new Button("New button");
				btnBack.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						((NewSpotPanel)getParent()).getNewSpotDataPanel().setVisible(true);
						setVisible(false);
					}
				});
				btnBack.setText("Volver");
				containerPanel.add(btnBack);
			}
			return containerPanel;
		}
		
	}

	

}
