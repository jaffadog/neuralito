package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.gwt.client.ForecastServices;

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
	
	public void getSpotLastestForecast(){
		ForecastServices.Util.getInstance().getWW3LatestForecasts(new Integer(localizationPanel.getSpotBoxDisplayValue()), new AsyncCallback<List<ForecastDTO>>(){
			public void onSuccess(List<ForecastDTO> result) {
				showSpotLatestForecast(result);
			}
				
			public void onFailure(Throwable caught) {
				
			}
		});
		
		
	}
	
	private void showSpotLatestForecast(List<ForecastDTO> forecasts) {
		lblTitle.setText(localizationPanel.getZoneBoxDisplayText() + " > " + localizationPanel.getSpotBoxDisplayText());
		
		CurrentForecastPanel current = new CurrentForecastPanel("Ahora", forecasts.size() > 0 ? forecasts.get(0) : null);
		flexTable.setWidget(0, 0, current);
		CurrentForecastPanel nextHours = new CurrentForecastPanel("+3 horas", forecasts.size() > 1 ? forecasts.get(1) : null);
		flexTable.setWidget(0, 1, nextHours);
		
		flexTable.setWidget(1, 0, new ForecastTable(forecasts));
		
		flexTable.getFlexCellFormatter().setColSpan(1, 0, 2);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
	}

}
