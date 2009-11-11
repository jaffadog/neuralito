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
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.RenderDetailedForecastContext;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.wgStrategyB.DetailedForecastWgStrategyB;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;

public class ForecastPanel extends LazyPanel {
	
	private LocalizationPanel localizationPanel;
	private Label lblTitle;
	private Label lblSubTitle;
	private FlexTable flexTable;
	private VerticalPanel container;
	
	public ForecastPanel() {
	}

	@Override
	protected Widget createWidget() {
		
		container = new VerticalPanel();
		
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
			lblSubTitle = new Label();
			//lblSubTitle.addStyleName("gwt-Label-SubTitle");
			container.add(lblSubTitle);
		}
		{
			flexTable = new FlexTable();
			container.add(flexTable);
			container.setCellHorizontalAlignment(flexTable, HasHorizontalAlignment.ALIGN_CENTER);
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
		lblSubTitle.setText("04-10-09 > [UTC-10] Pacific/Rarotonga, CKT");
		
		//TODO decidir como elijo y cual elijo de los forecasters del spot para mostrar los currentpanels, por ahora harcodeo para recuperar el ww3
		List<ForecastDTO> ww3Forecaster = forecasters.get("WW3 Noaa Forecaster");
		CurrentForecastPanel current = new CurrentForecastPanel("Ahora", ww3Forecaster.size() > 0 ? ww3Forecaster.get(0) : null);
		flexTable.setWidget(0, 0, current);
		CurrentForecastPanel nextHours = new CurrentForecastPanel("+3 horas", ww3Forecaster.size() > 1 ? ww3Forecaster.get(1) : null);
		flexTable.setWidget(0, 1, nextHours);
		
		RenderDetailedForecastContext renderContext = new RenderDetailedForecastContext(new DetailedForecastWgStrategyB(forecasters));
		Widget detailedForecast = renderContext.executeRenderStrategy(); 
		container.add(detailedForecast);
		
		//flexTable.getFlexCellFormatter().setColSpan(1, 0, 2);
		
		//flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		//flexTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
		container.setCellHorizontalAlignment(detailedForecast, HasHorizontalAlignment.ALIGN_LEFT);
	}

}
