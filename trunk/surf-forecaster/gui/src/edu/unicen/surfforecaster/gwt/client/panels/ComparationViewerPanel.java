package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.gwt.client.dto.ForecastGwtDTO;
import edu.unicen.surfforecaster.gwt.client.panels.charts.ISurfForecasterChart;
import edu.unicen.surfforecaster.gwt.client.panels.charts.SpotComparationColumnChart;
import edu.unicen.surfforecaster.gwt.client.panels.charts.SpotComparationMotionChart;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.RenderDetailedForecastContext;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.wgstrategyC.DetailedForecastWgStrategyC;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad;

public class ComparationViewerPanel extends FlexTable implements ISurfForecasterBasePanel, ClickHandler, ChangeHandler {
	
	private Widget baseParentPanel = null;
	
	private DisclosurePanel detailedCompTablePanel = null;
	
	private HTMLButtonGrayGrad backBtn = null;
	private HTMLButtonGrayGrad backBtn2 = null;
	private HTMLButtonGrayGrad refreshBtn = null;
	
	private ListBox spotBox1 = null;
	private ListBox spotBox2 = null;
	private ListBox spotBox3 = null;
	private ListBox spotBox4 = null;
	private ListBox spotBox5 = null;
	
	private Label spotName1 = null;
	private Label spotName2 = null;
	private Label spotName3 = null;
	private Label spotName4 = null;
	private Label spotName5 = null;
	
	private static final String FORECASTER_LABEL_WIDTH = "150px";
	private static final String FORECASTER_LIST_WIDTH = "250px";
	
	
	//Temporal data
	private List<String> forecastersNames = null;
	private List<String> spotsNames = null;
	private List<Integer> spotsIds = null;
	private Map<Integer, Map<String, List<ForecastGwtDTO>>> spotsLatestForecasts = null;
	private Widget detailedCompTable = null;
	
	public ComparationViewerPanel() {
		{
			DisclosurePanel spotsForecastersPanel = new DisclosurePanel(GWTUtils.LOCALE_CONSTANTS.chooseForecasters(), true);
			spotsForecastersPanel.setAnimationEnabled(true);
			spotsForecastersPanel.setWidth("100%");
			this.setWidget(0, 0, spotsForecastersPanel);
			{
				VerticalPanel spotsForecastersVPanel = new VerticalPanel();
				spotsForecastersVPanel.setWidth("100%");
				spotsForecastersPanel.setContent(spotsForecastersVPanel);
				{
					FlexTable spotsForecastersTable = new FlexTable();
					spotsForecastersVPanel.add(spotsForecastersTable);
					//Spot name labels
					{
						spotName1  = new Label("");
						spotName1.setWidth(ComparationViewerPanel.FORECASTER_LABEL_WIDTH);
						spotsForecastersTable.setWidget(0, 0, spotName1 );
						spotsForecastersTable.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
					}
					{
						spotName2  = new Label("");
						spotName2.setWidth(ComparationViewerPanel.FORECASTER_LABEL_WIDTH);
						spotsForecastersTable.setWidget(0, 2, spotName2 );
						spotsForecastersTable.getFlexCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT);
					}
					{
						spotName3  = new Label("");
						spotName3.setWidth(ComparationViewerPanel.FORECASTER_LABEL_WIDTH);
						spotsForecastersTable.setWidget(1, 0, spotName3 );
						spotsForecastersTable.getFlexCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
						spotName3.setVisible(false);
					}
					{
						spotName4  = new Label("");
						spotName4.setWidth(ComparationViewerPanel.FORECASTER_LABEL_WIDTH);
						spotsForecastersTable.setWidget(1, 2, spotName4 );
						spotsForecastersTable.getFlexCellFormatter().setHorizontalAlignment(1, 2, HasHorizontalAlignment.ALIGN_RIGHT);
						spotName4.setVisible(false);
					}
					{
						spotName5  = new Label("");
						spotName5.setWidth(ComparationViewerPanel.FORECASTER_LABEL_WIDTH);
						spotsForecastersTable.setWidget(2, 0, spotName5);
						spotsForecastersTable.getFlexCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
						spotName5.setVisible(false);
					}
					
					//Spots forecasters listboxes
					{
						spotBox1 = new ListBox();
						spotBox1.addChangeHandler(this);
						spotBox1.setWidth(ComparationViewerPanel.FORECASTER_LIST_WIDTH);
						spotsForecastersTable.setWidget(0, 1, spotBox1);
					}
					{
						spotBox2 = new ListBox();
						spotBox2.addChangeHandler(this);
						spotBox2.setWidth(ComparationViewerPanel.FORECASTER_LIST_WIDTH);
						spotsForecastersTable.setWidget(0, 3, spotBox2);
					}
					{
						spotBox3 = new ListBox();
						spotBox3.addChangeHandler(this);
						spotBox3.setWidth(ComparationViewerPanel.FORECASTER_LIST_WIDTH);
						spotsForecastersTable.setWidget(1, 1, spotBox3);
						spotBox3.setVisible(false);
					}
					{
						spotBox4 = new ListBox();
						spotBox4.addChangeHandler(this);
						spotBox4.setWidth(ComparationViewerPanel.FORECASTER_LIST_WIDTH);
						spotsForecastersTable.setWidget(1, 3, spotBox4);
						spotBox4.setVisible(false);
					}
					{
						spotBox5 = new ListBox();
						spotBox5.addChangeHandler(this);
						spotBox5.setWidth(ComparationViewerPanel.FORECASTER_LIST_WIDTH);
						spotsForecastersTable.setWidget(2, 1, spotBox5);
						spotBox5.setVisible(false);
					}
				}
				//Refrash button
				{
					refreshBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.refresh(), "ComparationViewerPanel-refresh", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_120PX, GWTUtils.LOCALE_CONSTANTS.refreshTip());
					refreshBtn.addClickHandler(this);
					spotsForecastersVPanel.add(refreshBtn);
					spotsForecastersVPanel.setCellHorizontalAlignment(refreshBtn, HasHorizontalAlignment.ALIGN_CENTER);
				}
				
			}
		}
		//Back button
		{
			backBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.back(), "ComparationViewerPanel-back", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX, GWTUtils.LOCALE_CONSTANTS.compBackTip());
			backBtn.addClickHandler(this);
			backBtn.setVisible(false);
			this.setWidget(7, 0, backBtn);
			this.getFlexCellFormatter().setColSpan(7, 0, 4);
			this.getFlexCellFormatter().setHorizontalAlignment(7, 0, HasHorizontalAlignment.ALIGN_CENTER);
		}
		{
			detailedCompTablePanel = new DisclosurePanel(GWTUtils.LOCALE_CONSTANTS.detailedForecastsTable(), false);
			detailedCompTablePanel.setAnimationEnabled(true);
			this.setWidget(8, 0, detailedCompTablePanel);
			this.getFlexCellFormatter().setColSpan(8, 0, 4);
		}
		//Back button2
		{
			backBtn2 = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.back(), "ComparationViewerPanel-back2", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX, GWTUtils.LOCALE_CONSTANTS.compBackTip());
			backBtn2.addClickHandler(this);
			this.setWidget(9, 0, backBtn2);
			this.getFlexCellFormatter().setColSpan(9, 0, 4);
			this.getFlexCellFormatter().setHorizontalAlignment(9, 0, HasHorizontalAlignment.ALIGN_CENTER);
		}
	}

	@Override
	public Widget getBasePanel() {
		return this.baseParentPanel;
	}

	@Override
	public void setBasePanel(Widget basePanel) {
		this.baseParentPanel = basePanel;
	}
	
	/**
	 * Executed when the user creates a comparation in comparationCreatorPanel and clicks "Compare" button.
	 * This method fills the comboBox components.
	 * @param spotsLatestForecasts
	 * @param spotsIds
	 * @param spotsNames
	 */
	public void renderComparation(final Map<Integer, Map<String, List<ForecastGwtDTO>>> spotsLatestForecasts, final List<Integer> spotsIds, final List<String> spotsNames) {
		this.spotsLatestForecasts = spotsLatestForecasts;
		this.spotsIds = spotsIds;
		this.spotsNames = spotsNames;
		
		if (spotsLatestForecasts.size() >= SpotComparatorPanel.MIN_SPOTS_TO_COMP && spotsLatestForecasts.size() <= SpotComparatorPanel.MAX_SPOTS_TO_COMP 
				&& spotsNames.size() >= SpotComparatorPanel.MIN_SPOTS_TO_COMP && spotsNames.size() <= SpotComparatorPanel.MAX_SPOTS_TO_COMP) {
			this.fillSpotProperties();
			this.drawColumnChart();
			this.drawMotionChart();
			detailedCompTablePanel.addOpenHandler(new OpenHandler<DisclosurePanel>() {
				public void onOpen(OpenEvent<DisclosurePanel> event) {
					if (detailedCompTable == null) {
						detailedCompTablePanel.setContent(new LoadingPanel(GWTUtils.LOCALE_CONSTANTS.loadingDetailedForecasts()));
						renderDetailedCompTable(spotsLatestForecasts, spotsIds, spotsNames);
					}
					backBtn.setVisible(true);
				}
			});
			detailedCompTablePanel.addCloseHandler(new CloseHandler<DisclosurePanel>() {
				public void onClose(CloseEvent<DisclosurePanel> event) {
					backBtn.setVisible(false);
				}
			});
			
		} else {
			new MessageBox(GWTUtils.LOCALE_CONSTANTS.close(), GWTUtils.LOCALE_CONSTANTS.twoToFiveSpotsToMakeComparation(), MessageBox.IconType.INFO);
		}
		
	}
	
	/**
	 * Executed when click refresh button to update the comparation after changed the forecasters boxes
	 */
	public void refreshComparation() {
		if (spotsLatestForecasts.size() >= SpotComparatorPanel.MIN_SPOTS_TO_COMP && spotsLatestForecasts.size() <= SpotComparatorPanel.MAX_SPOTS_TO_COMP && 
				spotsNames.size() >= SpotComparatorPanel.MIN_SPOTS_TO_COMP && spotsNames.size() <= SpotComparatorPanel.MAX_SPOTS_TO_COMP) {
			this.drawColumnChart();
			this.drawMotionChart();
			if (detailedCompTable != null && detailedCompTablePanel.isOpen())
				this.renderDetailedCompTable(spotsLatestForecasts, spotsIds, spotsNames);
			else if (detailedCompTable != null)
				detailedCompTable = null;
		} else {
			new MessageBox(GWTUtils.LOCALE_CONSTANTS.close(), GWTUtils.LOCALE_CONSTANTS.twoToFiveSpotsToMakeComparation(), MessageBox.IconType.INFO);
		}
		
	}
	
	private void fillSpotProperties() {
		this.forecastersNames = new ArrayList<String>();
		
		this.spotName1.setText(spotsNames.get(0));
		Iterator<String> i = spotsLatestForecasts.get(spotsIds.get(0)).keySet().iterator();
		while (i.hasNext()) {
			String forecasterName = i.next();
			this.spotBox1.addItem(forecasterName, forecasterName);
		}
		this.forecastersNames.add(this.spotBox1.getValue(0));
		
		this.spotName2.setText(spotsNames.get(1));
		i = spotsLatestForecasts.get(spotsIds.get(1)).keySet().iterator();
		while (i.hasNext()) {
			String forecasterName = i.next();
			this.spotBox2.addItem(forecasterName, forecasterName);
		}
		this.forecastersNames.add(this.spotBox2.getValue(0));
		
		if (spotsIds.size() >= 3) {
			this.spotName3.setText(spotsNames.get(2));
			i = spotsLatestForecasts.get(spotsIds.get(2)).keySet().iterator();
			while (i.hasNext()) {
				String forecasterName = i.next();
				this.spotBox3.addItem(forecasterName, forecasterName);
			}
			this.spotName3.setVisible(true);
			this.spotBox3.setVisible(true);
			this.forecastersNames.add(this.spotBox3.getValue(0));
			
			if (spotsIds.size() >= 4) {
				this.spotName4.setText(spotsNames.get(3));
				i = spotsLatestForecasts.get(spotsIds.get(3)).keySet().iterator();
				while (i.hasNext()) {
					String forecasterName = i.next();
					this.spotBox4.addItem(forecasterName, forecasterName);
				}
				this.spotName4.setVisible(true);
				this.spotBox4.setVisible(true);
				this.forecastersNames.add(this.spotBox4.getValue(0));
				
				if (spotsIds.size() == 5) {
					this.spotName5.setText(spotsNames.get(4));
					i = spotsLatestForecasts.get(spotsIds.get(4)).keySet().iterator();
					while (i.hasNext()) {
						String forecasterName = i.next();
						this.spotBox5.addItem(forecasterName, forecasterName);
					}
					
					this.spotName5.setVisible(true);
					this.spotBox5.setVisible(true);
					this.forecastersNames.add(this.spotBox5.getValue(0));
				}
			}
		}
	}
	
	private void drawColumnChart(){
		VerticalPanel chartContainer = new VerticalPanel();
		this.setWidget(1, 0, chartContainer);
	    this.getFlexCellFormatter().setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_CENTER);
	    
		Label lblTitle = new Label(GWTUtils.LOCALE_CONSTANTS.waveHeightNextHours());
		lblTitle.addStyleName("gwt-Label-Title");
		chartContainer.add(lblTitle);
		
		Label lblDescription = new Label(GWTUtils.LOCALE_CONSTANTS.waveHeightNextHoursDescr());
		chartContainer.add(lblDescription);
		lblDescription.addStyleName("gwt-Label-SectionDescription");
		
		LoadingPanel loadingPanel = new LoadingPanel(GWTUtils.LOCALE_CONSTANTS.loadingChart()) ;
		chartContainer.add(loadingPanel);
		
		ISurfForecasterChart columnChart = new SpotComparationColumnChart(spotsLatestForecasts, spotsIds, spotsNames, forecastersNames);
		columnChart.render(chartContainer, loadingPanel);
		
	}
	
	private void drawMotionChart(){
		VerticalPanel chartContainer = new VerticalPanel();
		this.setWidget(2, 0, chartContainer);
	    this.getFlexCellFormatter().setHorizontalAlignment(6, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		Label lblTitle = new Label(GWTUtils.LOCALE_CONSTANTS.waveHeightAllForecast());
		lblTitle.addStyleName("gwt-Label-Title");
		chartContainer.add(lblTitle);
		
		Label lblDescription = new Label(GWTUtils.LOCALE_CONSTANTS.waveHeightAllForecastDescr());
		chartContainer.add(lblDescription);
		lblDescription.addStyleName("gwt-Label-SectionDescription");
		
		LoadingPanel loadingPanel = new LoadingPanel(GWTUtils.LOCALE_CONSTANTS.loadingChart()) ;
		chartContainer.add(loadingPanel);
		
		ISurfForecasterChart motionChart = new SpotComparationMotionChart(spotsLatestForecasts, spotsIds, spotsNames, forecastersNames);
		motionChart.render(chartContainer, loadingPanel);
	}
	
	private void renderDetailedCompTable(Map<Integer, Map<String, List<ForecastGwtDTO>>> spotsLatestForecasts, List<Integer> spotsIds, List<String> spotsNames) {
		RenderDetailedForecastContext renderContext = new RenderDetailedForecastContext(new DetailedForecastWgStrategyC(spotsLatestForecasts, spotsIds, spotsNames, forecastersNames));
		detailedCompTable = renderContext.executeRenderStrategy();
		detailedCompTablePanel.setContent(detailedCompTable);
	}
	
	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if (sender == backBtn || sender == backBtn2)
			((SpotComparatorPanel)baseParentPanel).showComparationCreatorPanel();
		if (sender == refreshBtn)
			refreshComparation();
	}
	
	@Override
	public void onChange(ChangeEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if (sender == spotBox1)
			this.forecastersNames.set(0, this.spotBox1.getValue(this.spotBox1.getSelectedIndex()));
		if (sender == spotBox2)
			this.forecastersNames.set(1, this.spotBox2.getValue(this.spotBox2.getSelectedIndex()));
		if (sender == spotBox3)
			this.forecastersNames.set(2, this.spotBox3.getValue(this.spotBox3.getSelectedIndex()));
		if (sender == spotBox4)
			this.forecastersNames.set(3, this.spotBox4.getValue(this.spotBox4.getSelectedIndex()));
		if (sender == spotBox5)
			this.forecastersNames.set(4, this.spotBox5.getValue(this.spotBox5.getSelectedIndex()));
	}
	
	
}
