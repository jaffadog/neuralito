package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad;

public class ComparationViewerPanel extends FlexTable implements ISurfForecasterBasePanel, ClickHandler {
	
	private Widget baseParentPanel = null;
	private HTMLButtonGrayGrad backBtn = null;
	
	private ListBox spotBox1 = null;
	private ListBox spotBox2 = null;
	private ListBox spotBox3 = null;
	private ListBox spotBox4 = null;
	private ListBox spotBox5 = null;
	
	private Label spotColor1 = null;
	private Label spotColor2 = null;
	private Label spotColor3 = null;
	private Label spotColor4 = null;
	private Label spotColor5 = null;
	
	private Label spotName1 = null;
	private Label spotName2 = null;
	private Label spotName3 = null;
	private Label spotName4 = null;
	private Label spotName5 = null;
	
	private static final String COLOR_LABEL_WIDTH = "15px";
	private static final String COLOR_LABEL_HEIGHT = "15px";
	private static final String FORECASTER_LIST_WIDTH = "200px";
	
	
	public ComparationViewerPanel() {
		//Spot name labels
		{
			spotName1  = new Label("");
			this.setWidget(0, 1, spotName1 );
			this.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		}
		{
			spotName2  = new Label("");
			this.setWidget(1, 1, spotName2 );
			this.getFlexCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		}
		{
			spotName3  = new Label("");
			this.setWidget(2, 1, spotName3 );
			this.getFlexCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_RIGHT);
			spotName3.setVisible(false);
		}
		{
			spotName4  = new Label("");
			this.setWidget(3, 1, spotName4 );
			this.getFlexCellFormatter().setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_RIGHT);
			spotName4.setVisible(false);
		}
		{
			spotName5  = new Label("");
			this.setWidget(4, 1, spotName5);
			this.getFlexCellFormatter().setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_RIGHT);
			spotName5.setVisible(false);
		}
		
		//Spot color labels
		{
			spotColor1 = new Label("");
			spotColor1.setSize(ComparationViewerPanel.COLOR_LABEL_WIDTH, ComparationViewerPanel.COLOR_LABEL_HEIGHT);
			spotColor1.addStyleName("gwt-Label-SpotColor1");
			this.setWidget(0, 2, spotColor1);
		}
		{
			spotColor2 = new Label("");
			spotColor2.setSize(ComparationViewerPanel.COLOR_LABEL_WIDTH, ComparationViewerPanel.COLOR_LABEL_HEIGHT);
			spotColor2.addStyleName("gwt-Label-SpotColor2");
			this.setWidget(1, 2, spotColor2);
		}
		{
			spotColor3 = new Label("");
			spotColor3.setSize(ComparationViewerPanel.COLOR_LABEL_WIDTH, ComparationViewerPanel.COLOR_LABEL_HEIGHT);
			spotColor3.addStyleName("gwt-Label-SpotColor3");
			this.setWidget(2, 2, spotColor3);
			spotColor3.setVisible(false);
		}
		{
			spotColor4 = new Label("");
			spotColor4.setSize(ComparationViewerPanel.COLOR_LABEL_WIDTH, ComparationViewerPanel.COLOR_LABEL_HEIGHT);
			spotColor4.addStyleName("gwt-Label-SpotColor4");
			this.setWidget(3, 2, spotColor4);
			spotColor4.setVisible(false);
		}
		{
			spotColor5 = new Label("");
			spotColor5.setSize(ComparationViewerPanel.COLOR_LABEL_WIDTH, ComparationViewerPanel.COLOR_LABEL_HEIGHT);
			spotColor5.addStyleName("gwt-Label-SpotColor5");
			this.setWidget(4, 2, spotColor5);
			spotColor5.setVisible(false);
		}
		
		//Spots forecasters listboxes
		{
			spotBox1 = new ListBox();
			spotBox1.setWidth(ComparationViewerPanel.FORECASTER_LIST_WIDTH);
			this.setWidget(0, 3, spotBox1);
		}
		{
			spotBox2 = new ListBox();
			spotBox2.setWidth(ComparationViewerPanel.FORECASTER_LIST_WIDTH);
			this.setWidget(1, 3, spotBox2);
		}
		{
			spotBox3 = new ListBox();
			spotBox3.setWidth(ComparationViewerPanel.FORECASTER_LIST_WIDTH);
			this.setWidget(2, 3, spotBox3);
			spotBox3.setVisible(false);
		}
		{
			spotBox4 = new ListBox();
			spotBox4.setWidth(ComparationViewerPanel.FORECASTER_LIST_WIDTH);
			this.setWidget(3, 3, spotBox4);
			spotBox4.setVisible(false);
		}
		{
			spotBox5 = new ListBox();
			spotBox5.setWidth(ComparationViewerPanel.FORECASTER_LIST_WIDTH);
			this.setWidget(4, 3, spotBox5);
			spotBox5.setVisible(false);
			
		}
		
		//Back button
		{
			backBtn = new HTMLButtonGrayGrad("Volver", "ComparationViewerPanel-back", GWTUtils.BUTTON_GRAY_GRAD_MEDIUM);
			backBtn.addClickHandler(this);
			this.setWidget(7, 0, backBtn);
			this.getFlexCellFormatter().setColSpan(7, 0, 4);
		}
	}
	
	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if (sender == backBtn)
			((SpotComparatorPanel)baseParentPanel).showComparationCreatorPanel();		
	}

	@Override
	public Widget getBasePanel() {
		return this.baseParentPanel;
	}

	@Override
	public void setBasePanel(Widget basePanel) {
		this.baseParentPanel = basePanel;
	}

	public void renderComparation(Map<Integer, Map<String, List<ForecastDTO>>> spotsLatestForecasts, List<Integer> spotsIds, List<String> spotsNames) {
		//TODO hacer que el valor harcodedo 5 de la sig if sea una variable statica bien definida (ya se definio en el comparationCreatorPanel, tal vez habria que 
		//meterla en otro lado)
		if (spotsLatestForecasts.size() >= 2 && spotsLatestForecasts.size() <= 5 && spotsNames.size() >= 2 && spotsNames.size() <= 5) {
			this.fillSpotPoperties(spotsLatestForecasts, spotsIds, spotsNames);
		} else {
			//TODO el mesagebox siguiente
			Window.alert("La cantuidad de spots a comparar tiene que ser entre 2 y 5");
			//TODO redirigir al panel de creacion de comparaciones
		}
		
	}

	private void fillSpotPoperties(Map<Integer, Map<String, List<ForecastDTO>>> spotsLatestForecasts, List<Integer> spotsIds, List<String> spotsNames) {
		this.spotName1.setText(spotsNames.get(0));
		Iterator<String> i = spotsLatestForecasts.get(spotsIds.get(0)).keySet().iterator();
		while (i.hasNext()) {
			String forecasterName = i.next();
			this.spotBox1.addItem(forecasterName, forecasterName);
		}
		
		this.spotName2.setText(spotsNames.get(1));
		i = spotsLatestForecasts.get(spotsIds.get(1)).keySet().iterator();
		while (i.hasNext()) {
			String forecasterName = i.next();
			this.spotBox2.addItem(forecasterName, forecasterName);
		}
		
		if (spotsIds.size() >= 3) {
			this.spotName3.setText(spotsNames.get(2));
			i = spotsLatestForecasts.get(spotsIds.get(2)).keySet().iterator();
			while (i.hasNext()) {
				String forecasterName = i.next();
				this.spotBox3.addItem(forecasterName, forecasterName);
			}
			this.spotName3.setVisible(true);
			this.spotBox3.setVisible(true);
			this.spotColor3.setVisible(true);
			
			if (spotsIds.size() >= 4) {
				this.spotName4.setText(spotsNames.get(3));
				i = spotsLatestForecasts.get(spotsIds.get(3)).keySet().iterator();
				while (i.hasNext()) {
					String forecasterName = i.next();
					this.spotBox4.addItem(forecasterName, forecasterName);
				}
				this.spotName4.setVisible(true);
				this.spotBox4.setVisible(true);
				this.spotColor4.setVisible(true);
				
				if (spotsIds.size() == 5) {
					this.spotName5.setText(spotsNames.get(4));
					i = spotsLatestForecasts.get(spotsIds.get(4)).keySet().iterator();
					while (i.hasNext()) {
						String forecasterName = i.next();
						this.spotBox5.addItem(forecasterName, forecasterName);
					}
					
					this.spotName5.setVisible(true);
					this.spotBox5.setVisible(true);
					this.spotColor5.setVisible(true);
				}
			}
		}
	}
	
	
}
