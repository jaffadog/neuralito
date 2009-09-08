package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;

public class ForecastPanel extends FlexTable {

	public ForecastPanel() {
		CurrentForecastPanel current = new CurrentForecastPanel("Ahora");
		CurrentForecastPanel nextHours = new CurrentForecastPanel("+ 3 horas");
		
		this.setWidget(0, 0, current);
		this.setWidget(0, 1, nextHours);
		this.setWidget(1, 0, new Image("images/windguru.PNG"));
		this.getFlexCellFormatter().setColSpan(1, 0, 2);
		this.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		this.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
		this.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
	}

}
