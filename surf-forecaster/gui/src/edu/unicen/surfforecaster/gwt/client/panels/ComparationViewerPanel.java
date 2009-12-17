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
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.ColumnChart;
import com.google.gwt.visualization.client.visualizations.MotionChart;
import com.google.gwt.visualization.client.visualizations.ColumnChart.Options;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.WW3Parameter;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.RenderDetailedForecastContext;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.wgstrategyC.DetailedForecastWgStrategyC;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;
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
	private Map<Integer, Map<String, List<ForecastDTO>>> spotsLatestForecasts = null;
	private Widget detailedCompTable = null;
	
	public ComparationViewerPanel() {
		{
			DisclosurePanel spotsForecastersPanel = new DisclosurePanel("Seleccione el pronosticador de cada spot", true);
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
					refreshBtn = new HTMLButtonGrayGrad("Actualizar", "ComparationViewerPanel-refresh", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_MEDIUM);
					refreshBtn.addClickHandler(this);
					spotsForecastersVPanel.add(refreshBtn);
					spotsForecastersVPanel.setCellHorizontalAlignment(refreshBtn, HasHorizontalAlignment.ALIGN_CENTER);
				}
				
			}
		}
		//Back button
		{
			backBtn = new HTMLButtonGrayGrad("Volver", "ComparationViewerPanel-back", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_MEDIUM);
			backBtn.addClickHandler(this);
			backBtn.setVisible(false);
			this.setWidget(7, 0, backBtn);
			this.getFlexCellFormatter().setColSpan(7, 0, 4);
			this.getFlexCellFormatter().setHorizontalAlignment(7, 0, HasHorizontalAlignment.ALIGN_CENTER);
		}
		{
			detailedCompTablePanel = new DisclosurePanel("Tabla de pronosticos detallados", false);
			detailedCompTablePanel.setAnimationEnabled(true);
			this.setWidget(8, 0, detailedCompTablePanel);
			this.getFlexCellFormatter().setColSpan(8, 0, 4);
		}
		//Back button2
		{
			backBtn2 = new HTMLButtonGrayGrad("Volver", "ComparationViewerPanel-back2", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_MEDIUM);
			backBtn2.addClickHandler(this);
			this.setWidget(9, 0, backBtn2);
			this.getFlexCellFormatter().setColSpan(9, 0, 4);
			this.getFlexCellFormatter().setHorizontalAlignment(9, 0, HasHorizontalAlignment.ALIGN_CENTER);
		}
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
	public void renderComparation(final Map<Integer, Map<String, List<ForecastDTO>>> spotsLatestForecasts, final List<Integer> spotsIds, final List<String> spotsNames) {
		this.spotsLatestForecasts = spotsLatestForecasts;
		this.spotsIds = spotsIds;
		this.spotsNames = spotsNames;
		
		//TODO hacer que el valor harcodedo 5 de la sig if sea una variable statica bien definida (ya se definio en el comparationCreatorPanel, tal vez habria que 
		//meterla en otro lado)
		if (spotsLatestForecasts.size() >= 2 && spotsLatestForecasts.size() <= 5 && spotsNames.size() >= 2 && spotsNames.size() <= 5) {
			this.fillSpotProperties();
			this.drawColumnChart(spotsLatestForecasts, spotsIds, spotsNames);
			this.drawMotionChart(spotsLatestForecasts, spotsIds, spotsNames);
			detailedCompTablePanel.addOpenHandler(new OpenHandler<DisclosurePanel>() {
				public void onOpen(OpenEvent<DisclosurePanel> event) {
					if (detailedCompTable == null)
						renderDetailedCompTable(spotsLatestForecasts, spotsIds, spotsNames);
					backBtn.setVisible(true);
				}
			});
			detailedCompTablePanel.addCloseHandler(new CloseHandler<DisclosurePanel>() {
				public void onClose(CloseEvent<DisclosurePanel> event) {
					backBtn.setVisible(false);
				}
			});
			
		} else {
			//TODO el mesagebox siguiente
			Window.alert("La cantuidad de spots a comparar tiene que ser entre 2 y 5");
			//TODO redirigir al panel de creacion de comparaciones
		}
		
	}
	
	/**
	 * Executed when click refresh button to update the comparation after changed the forecasters boxes
	 */
	public void refreshComparation() {
		//TODO hacer que el valor harcodedo 5 de la sig if sea una variable statica bien definida (ya se definio en el comparationCreatorPanel, tal vez habria que 
		//meterla en otro lado)
		if (spotsLatestForecasts.size() >= 2 && spotsLatestForecasts.size() <= 5 && spotsNames.size() >= 2 && spotsNames.size() <= 5) {
			this.drawColumnChart(spotsLatestForecasts, spotsIds, spotsNames);
			this.drawMotionChart(spotsLatestForecasts, spotsIds, spotsNames);
			if (detailedCompTable != null && detailedCompTablePanel.isOpen())
				this.renderDetailedCompTable(spotsLatestForecasts, spotsIds, spotsNames);
			else if (detailedCompTable != null)
				detailedCompTable = null;
		} else {
			//TODO el mesagebox siguiente
			Window.alert("La cantuidad de spots a comparar tiene que ser entre 2 y 5");
			//TODO redirigir al panel de creacion de comparaciones
		}
		
	}
	
	private void renderDetailedCompTable(Map<Integer, Map<String, List<ForecastDTO>>> spotsLatestForecasts, List<Integer> spotsIds, List<String> spotsNames) {
		RenderDetailedForecastContext renderContext = new RenderDetailedForecastContext(new DetailedForecastWgStrategyC(spotsLatestForecasts, spotsIds, spotsNames, forecastersNames));
		detailedCompTable = renderContext.executeRenderStrategy();
		detailedCompTablePanel.setContent(detailedCompTable);
	}
	
	private void drawColumnChart(final Map<Integer, Map<String, List<ForecastDTO>>> spotsLatestForecasts, final List<Integer> spotsIds, final List<String> spotsNames) {
	    VisualizationUtils.loadVisualizationApi("1.1",
	            new Runnable() {
	              public void run() {
	            	  DataTable data = createTable(spotsLatestForecasts, spotsIds, spotsNames);
	            	  
		          	  Options options = createOptions();
	            	  
		          	  ColumnChart viz = new ColumnChart(data, options);
	          	      setWidget(5, 0, viz);
	          	      getFlexCellFormatter().setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_CENTER);
	              }
        }, ColumnChart.PACKAGE);
	}
	
	private Options createOptions() {
		Options options = Options.create();
		options.setHeight(300);
		options.setTitle("Alturas de ola futuras");
		options.setWidth(950);
		options.set3D(true);
		options.setShowCategories(true);
		options.setEnableTooltip(true);
		options.setMin(0);
		options.setMax(20);
		return options;
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
	
	private void drawMotionChart(final Map<Integer, Map<String, List<ForecastDTO>>> spotsLatestForecasts, final List<Integer> spotsIds, final List<String> spotsNames) {
	    VisualizationUtils.loadVisualizationApi("1.1",
	            new Runnable() {
	              public void run() {
	            	  DataTable data = createMotionTable(spotsLatestForecasts, spotsIds, spotsNames);
	            	  
		          	  MotionChart.Options options = createMotionOptions();
	            	  
		          	  MotionChart viz = new MotionChart(data, options);
	          	      setWidget(6, 0, viz);
	          	    getFlexCellFormatter().setHorizontalAlignment(6, 0, HasHorizontalAlignment.ALIGN_CENTER);
	              }				
        }, MotionChart.PACKAGE);
	}
	
	private DataTable createMotionTable(Map<Integer, Map<String, List<ForecastDTO>>> spotsLatestForecasts, List<Integer> spotsIds, List<String> spotsNames) {
		DataTable data = DataTable.create();	    
	    data.addColumn(ColumnType.STRING, "Spot");
	    data.addColumn(ColumnType.NUMBER, "Hours");
	    data.addColumn(ColumnType.NUMBER, "Wave Height");
	    
	    for (int spotIndex = 0; spotIndex < spotsIds.size(); spotIndex++) {
	    	Integer spotId = spotsIds.get(spotIndex);
	    	String spotName = spotsNames.get(spotIndex);
	    	String forecasterName = this.forecastersNames.get(spotIndex);
	    	List<ForecastDTO> forecasts = spotsLatestForecasts.get(spotId).get(forecasterName);
	    	if (data.getNumberOfRows() == 0)
	    		data.addRows(spotsIds.size() * forecasts.size());
	    		
	    	for (int forecastIndex = 0; forecastIndex < forecasts.size(); forecastIndex++) {
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
				data.setValue(spotIndex * forecasts.size() + forecastIndex, 0, spotName);
	    		data.setValue(spotIndex * forecasts.size() + forecastIndex, 1, 2000 + forecastIndex);
	    		data.setValue(spotIndex * forecasts.size() + forecastIndex, 2, new Double(waveHeight));
			}
	    }
	    
	    
		return data;
	}
	
	private MotionChart.Options createMotionOptions() {
		MotionChart.Options options = MotionChart.Options.create();
		options.setHeight(300);
	    options.setWidth(950);
		return options;
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
