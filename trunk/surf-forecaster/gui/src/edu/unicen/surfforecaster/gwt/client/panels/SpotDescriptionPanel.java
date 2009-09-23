package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SpotDescriptionPanel extends VerticalPanel {

	private LocalizationPanel localizationPanel;
	private Label lblTitle;
	private Label lblNewSpotDescription;

	public SpotDescriptionPanel() {
		{
			localizationPanel = new LocalizationPanel();
			localizationPanel.setBasePanel(this);
			add(localizationPanel);
		}
		{
			lblTitle = new Label();
			lblTitle.addStyleName("gwt-Label-SectionTitle");
			add(lblTitle);
		}
		{
			lblNewSpotDescription = new Label();
			lblNewSpotDescription.addStyleName("gwt-Label-RegisterSectionDescription");
			add(lblNewSpotDescription);
		}
	}
	
	public void showSpotDescription(){
		lblTitle.setText(localizationPanel.getZoneBoxDisplayValue() + " > " + localizationPanel.getSpotBoxDisplayValue());
		lblNewSpotDescription.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's " +
				"standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has " +
				"survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s " +
				"with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including " +
				"versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's " +
				"standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has " +
				"survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s " +
				"with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including " +
				"versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's " +
				"standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has " +
				"survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s " +
				"with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including " +
				"versions of Lorem Ipsum.");
	}

}
