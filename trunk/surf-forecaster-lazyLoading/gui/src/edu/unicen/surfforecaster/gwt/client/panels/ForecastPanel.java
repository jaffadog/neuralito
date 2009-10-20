package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.gwt.client.utils.LocalizationUtils;

public class ForecastPanel extends LazyPanel {
	
	private LocalizationPanel localizationPanel;
	private Label lblTitle;
	private FlexTable flexTable;
	
	public ForecastPanel() {
	}

	@Override
	protected Widget createWidget() {
		
		VerticalPanel container = new VerticalPanel();
		{
			localizationPanel = new LocalizationPanel();
			LocalizationUtils.getInstance().addObserver(localizationPanel);
			localizationPanel.setBasePanel(this);
			container.add(localizationPanel);
		}
		{
			lblTitle = new Label();
			lblTitle.addStyleName("gwt-Label-SectionTitle");
			container.add(lblTitle);
		}
		{
			
			
			flexTable = new FlexTable();
			container.add(flexTable);
		}
		return container;
	}
	
	public void showSpotForecast(){
		lblTitle.setText(localizationPanel.getZoneBoxDisplayValue() + " > " + localizationPanel.getSpotBoxDisplayValue());
		
		CurrentForecastPanel current = new CurrentForecastPanel("Ahora");
		CurrentForecastPanel nextHours = new CurrentForecastPanel("+3 horas");
		flexTable.setWidget(0, 0, current);
		flexTable.setWidget(0, 1, nextHours);
		flexTable.setWidget(1, 0, new Image("images/windguru.PNG"));
		
		flexTable.getFlexCellFormatter().setColSpan(1, 0, 2);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
	}

}
