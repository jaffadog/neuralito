package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.ColumnChart;
import com.google.gwt.visualization.client.visualizations.ColumnChart.Options;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.WW3Parameter;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;
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
	
	private List<String> forecastersNames = null;
	
	
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
			this.fillSpotProperties(spotsLatestForecasts, spotsIds, spotsNames);
			this.drawColumnChart(spotsLatestForecasts, spotsIds, spotsNames);
		} else {
			//TODO el mesagebox siguiente
			Window.alert("La cantuidad de spots a comparar tiene que ser entre 2 y 5");
			//TODO redirigir al panel de creacion de comparaciones
		}
		
	}
	
	private void drawColumnChart(final Map<Integer, Map<String, List<ForecastDTO>>> spotsLatestForecasts, final List<Integer> spotsIds, final List<String> spotsNames) {
	    VisualizationUtils.loadVisualizationApi("1.1",
	            new Runnable() {
	              public void run() {
	            	  DataTable data = createTable(spotsLatestForecasts, spotsIds, spotsNames);
	            	  
		          	  Options options = Options.create();
		          	  options.setHeight(300);
		          	  options.setTitle("Alturas de ola futuras");
		          	  options.setWidth(950);
		          	  options.set3D(true);
		          	  options.setShowCategories(true);
		          	  options.setEnableTooltip(true);
		          	  options.setMin(0);
		          	  options.setMax(20);
	            	  
		          	  ColumnChart viz = new ColumnChart(data, options);
	          	      setWidget(5, 0, viz);
	          	      getFlexCellFormatter().setColSpan(5, 0, 4);
	              }
        }, ColumnChart.PACKAGE);
	}
	
	private DataTable createTable(Map<Integer, Map<String, List<ForecastDTO>>> spotsLatestForecasts, List<Integer> spotsIds, List<String> spotsNames) {
		DataTable data = DataTable.create();
		
		data.addColumn(ColumnType.STRING, "Date");
  	    
		Iterator<String> i = spotsNames.iterator();
  	    while (i.hasNext()) {
  	    	String spotName = i.next();
  	    	data.addColumn(ColumnType.NUMBER, spotName);
  	    }
  	    
  	    data.addRows(4);
  	    data.setValue(0, 0, "Ahora");
  	    for (int index = 1; index < data.getNumberOfRows(); index++) {
  	    	data.setValue(index, 0, "+" + index * 3 + " horas");
		}
  	    
	    for (int spotIndex = 0; spotIndex < spotsIds.size(); spotIndex++) {
	    	Integer spotId = spotsIds.get(spotIndex);
	    	String forecasterName = this.forecastersNames.get(spotIndex);
	    	List<ForecastDTO> forecasts = spotsLatestForecasts.get(spotId).get(forecasterName);
	    	for (int forecastIndex = 0; forecastIndex < data.getNumberOfRows(); forecastIndex++) {
				ForecastDTO forecastDTO = forecasts.get(forecastIndex);
				//TODO generar las unidades en que se ve el sitio como alguna setting de usuario o usando cookies o algo y emprolijar la manera de levantarlo
				Unit heightUnitTarget = Unit.Meters;
				Unit speedUnitTarget = Unit.KilometersPerHour;
				Unit directionUnitTarget = Unit.Degrees;
				Unit periodUnitTarget = Unit.Seconds;
				
				//wave height
				String waveHeight = forecastDTO.getMap().get(WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.toString()).getValue();
				//wind speed
				String windSpeed = forecastDTO.getMap().get(WW3Parameter.WIND_SPEED.toString()).getValue();
				//Wave direccion
				String waveDirection = forecastDTO.getMap().get(WW3Parameter.PRIMARY_WAVE_DIRECTION.toString()).getValue();
				//Wave period
				String wavePeriod = forecastDTO.getMap().get(WW3Parameter.PRIMARY_WAVE_PERIOD.toString()).getValue();
				try {
					windSpeed = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(windSpeed, Unit.KilometersPerHour, speedUnitTarget));
					waveHeight = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveHeight, Unit.Meters, heightUnitTarget));
					waveDirection = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveDirection, Unit.Degrees, directionUnitTarget));
					wavePeriod = NumberFormat.getFormat("###").format(UnitConverter.convertValue(wavePeriod, Unit.Seconds, periodUnitTarget));
				} catch (NeuralitoException e) {
					// TODO ver como manejar esta exvepcion de conversion de unidades
					e.printStackTrace();
				}
				
	    		data.setValue(forecastIndex, spotIndex + 1, new Double(waveHeight));
			}
	    }
  	    
		return data;
	}

	private void fillSpotProperties(Map<Integer, Map<String, List<ForecastDTO>>> spotsLatestForecasts, List<Integer> spotsIds, List<String> spotsNames) {
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
			this.spotColor3.setVisible(true);
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
				this.spotColor4.setVisible(true);
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
					this.spotColor5.setVisible(true);
					this.forecastersNames.add(this.spotBox5.getValue(0));
				}
			}
		}
	}
	
	
}
