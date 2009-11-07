package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.gwt.client.ForecastServices;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.DetailedForecastWindguruTableA;

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
		ForecastServices.Util.getInstance().getLatestForecasts(new Integer(localizationPanel.getSpotBoxDisplayValue()), new AsyncCallback<Map<String, List<ForecastDTO>>>(){
			public void onSuccess(Map<String, List<ForecastDTO>> result) {
				showSpotLatestForecast(result);
			}
				
			public void onFailure(Throwable caught) {
				
			}
		});
		
		
	}
	
	private void showSpotLatestForecast(Map<String, List<ForecastDTO>> forecasters) {
		lblTitle.setText(localizationPanel.getZoneBoxDisplayText() + " > " + localizationPanel.getSpotBoxDisplayText());
		
		//TODO decidir como elijo y cual elijo de los forecasters del spot para mostrar los currentpanels, por ahora harcodeo para recuperar el ww3
		List<ForecastDTO> ww3Forecaster = forecasters.get("WW3 Noaa Forecaster");
		CurrentForecastPanel current = new CurrentForecastPanel("Ahora", ww3Forecaster.size() > 0 ? ww3Forecaster.get(0) : null);
		flexTable.setWidget(0, 0, current);
		CurrentForecastPanel nextHours = new CurrentForecastPanel("+3 horas", ww3Forecaster.size() > 1 ? ww3Forecaster.get(1) : null);
		flexTable.setWidget(0, 1, nextHours);
		
		flexTable.setWidget(1, 0, new DetailedForecastWindguruTableA(forecasters, 0, 5));
		//flexTable.setWidget(2, 0, new ForecastTable(forecasters, 23, 46));
		//flexTable.setWidget(3, 0, new ForecastTable(forecasters, 46, null));
		
		flexTable.getFlexCellFormatter().setColSpan(1, 0, 2);
		flexTable.getFlexCellFormatter().setColSpan(2, 0, 2);
		flexTable.getFlexCellFormatter().setColSpan(3, 0, 2);
		
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_LEFT);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_LEFT);
		flexTable.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_LEFT);
	}

}
