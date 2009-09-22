package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ForecastPanel extends VerticalPanel {

	public ForecastPanel() {
		
		{
			LocalizationPanel localizationPanel = LocalizationPanel.getInstance();
			Label lblTitle = new Label(localizationPanel.getZoneBoxDisplayValue() + " > " + localizationPanel.getSpotBoxDisplayValue());
			lblTitle.addStyleName("gwt-Label-SectionTitle");
			add(lblTitle);
		}
		{
			CurrentForecastPanel current = new CurrentForecastPanel("Ahora");
			CurrentForecastPanel nextHours = new CurrentForecastPanel("+3 horas");
			
			FlexTable flexTable = new FlexTable();
			flexTable.setWidget(0, 0, current);
			flexTable.setWidget(0, 1, nextHours);
			flexTable.setWidget(1, 0, new Image("images/windguru.PNG"));
			flexTable.getFlexCellFormatter().setColSpan(1, 0, 2);
			flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
			flexTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
			flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
			
			add(flexTable);
		}
	}

}
