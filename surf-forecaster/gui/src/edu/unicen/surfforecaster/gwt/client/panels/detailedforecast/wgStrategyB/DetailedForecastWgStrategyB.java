package edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.wgStrategyB;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.gwt.client.SpotServices;
import edu.unicen.surfforecaster.gwt.client.dto.ForecastGwtDTO;
import edu.unicen.surfforecaster.gwt.client.dto.SpotGwtDTO;
import edu.unicen.surfforecaster.gwt.client.panels.CurrentForecastPanel;
import edu.unicen.surfforecaster.gwt.client.panels.ErrorMsgPanel;
import edu.unicen.surfforecaster.gwt.client.panels.ILocalizationPanel;
import edu.unicen.surfforecaster.gwt.client.panels.MessagePanel;
import edu.unicen.surfforecaster.gwt.client.panels.detailedforecast.IRenderDetailedForecastStrategy;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.utils.TimeZones;

public class DetailedForecastWgStrategyB implements IRenderDetailedForecastStrategy {
	
	private VerticalPanel completeDetailedForecastVPanel = null;
	private Map<String, List<ForecastGwtDTO>> forecasters = null;
	private ILocalizationPanel localizationPanel = null;
	private HorizontalPanel spotDataHPanel = null;
	private static final String MAP_COORDINATE_FORMAT = "##0.0#####";
	
	/**
	 * This Strategy shows 2 panels with the next two forecasts (now and +3 hours), and shows below a detalied forecast
	 * table in wg format. 
	 * @param forecasters
	 * @param localizationPanel
	 */
	public DetailedForecastWgStrategyB(Map<String, List<ForecastGwtDTO>> forecasters, ILocalizationPanel localizationPanel, Integer spotId) {
		this.forecasters = forecasters;
		this.localizationPanel = localizationPanel;
		this.completeDetailedForecastVPanel = new VerticalPanel();
		this.completeDetailedForecastVPanel.setWidth("100%");
		this.spotDataHPanel = new HorizontalPanel();
		this.retrieveSpotData(spotId);
	}

	private void retrieveSpotData(Integer spotId) {
		SpotServices.Util.getInstance().getSpot(spotId, new AsyncCallback<SpotGwtDTO>(){
			public void onSuccess(SpotGwtDTO result) {
				fillSpotInfo(result);
			}
				
			public void onFailure(Throwable caught) {
				
			}
		});
	}
	
	private void fillSpotInfo(SpotGwtDTO spot) {
		Label spotLat = new Label(GWTUtils.LOCALE_CONSTANTS.spot() + " " + GWTUtils.LOCALE_CONSTANTS.lat_abbr() + ": ");
		Label spotLatValue = new Label(NumberFormat.getFormat(DetailedForecastWgStrategyB.MAP_COORDINATE_FORMAT).format(spot.getPoint().getLatitude()));
		spotLatValue.addStyleName("gwt-Label-SpotDataValue");
		Label spotLon = new Label(", " + GWTUtils.LOCALE_CONSTANTS.lon_abbr() + ": ");
		Label spotLonValue = new Label(NumberFormat.getFormat(DetailedForecastWgStrategyB.MAP_COORDINATE_FORMAT).format(spot.getPoint().getLongitude()));
		spotLonValue.addStyleName("gwt-Label-SpotDataValue");
		
		Label ww3Lat = new Label(", " + GWTUtils.LOCALE_CONSTANTS.justWW3GridPoint() + " " + GWTUtils.LOCALE_CONSTANTS.lat_abbr() + ": ");
		Label ww3LatValue = new Label(NumberFormat.getFormat(DetailedForecastWgStrategyB.MAP_COORDINATE_FORMAT).format(spot.getGridPoint().getLatitude()));
		ww3LatValue.addStyleName("gwt-Label-SpotDataValue");
		Label ww3Lon = new Label(", " + GWTUtils.LOCALE_CONSTANTS.lon_abbr() + ": ");
		Label ww3LonValue = new Label(NumberFormat.getFormat(DetailedForecastWgStrategyB.MAP_COORDINATE_FORMAT).format(spot.getGridPoint().getLongitude()));
		ww3LonValue.addStyleName("gwt-Label-SpotDataValue");
		
		Label timezone = new Label(", " + GWTUtils.LOCALE_CONSTANTS.timeZone() + ": ");
		Label timezoneValue = new Label(TimeZones.getInstance().getKey(spot.getTimeZone()));
		timezoneValue.addStyleName("gwt-Label-SpotDataValue");
		
		this.spotDataHPanel.add(spotLat);
		this.spotDataHPanel.add(spotLatValue);
		this.spotDataHPanel.add(spotLon);
		this.spotDataHPanel.add(spotLonValue);
		this.spotDataHPanel.add(ww3Lat);
		this.spotDataHPanel.add(ww3LatValue);
		this.spotDataHPanel.add(ww3Lon);
		this.spotDataHPanel.add(ww3LonValue);
		this.spotDataHPanel.add(timezone);
		this.spotDataHPanel.add(timezoneValue);
	}

	@Override
	public Widget renderDetailedForecast() {
		//Title
		Label lblTitle = new Label(localizationPanel.getZoneBoxDisplayText() + " > " + localizationPanel.getSpotBoxDisplayText());
		lblTitle.addStyleName("gwt-Label-SectionTitle");
		completeDetailedForecastVPanel.add(lblTitle);
		
		if (this.forecasters != null && this.forecasters.size() > 0) {
			completeDetailedForecastVPanel.add(this.spotDataHPanel);
			
			//Current forecast
			FlexTable flexTable = new FlexTable();
			List<ForecastGwtDTO> forecasts = null;
			String selectedForecasterName = null;
			//if spot have more than one forecaster, use the first diffenrent than ww3 forecaster, else use ww3
			if (this.forecasters.size() > 1){
				Set<String> keys = forecasters.keySet();
				Iterator<String> i = keys.iterator();
				while (i.hasNext()) {
					String forecasterName = i.next();
					if (!forecasterName.equals(GWTUtils.WW3_FORECASTER_NAME)) {
						forecasts = forecasters.get(forecasterName);
						selectedForecasterName = forecasterName;
						break;
					}
				}
			} else {
				forecasts = forecasters.get(GWTUtils.WW3_FORECASTER_NAME);
				selectedForecasterName = GWTUtils.WW3_FORECASTER_NAME;
			}
			int currentForecastIndex = GWTUtils.getCurrentForecastIndex(forecasts);
			CurrentForecastPanel current = new CurrentForecastPanel(selectedForecasterName, GWTUtils.LOCALE_CONSTANTS.now(), currentForecastIndex != -1 ? forecasts.get(currentForecastIndex) : null);
			flexTable.setWidget(0, 0, current);
			CurrentForecastPanel nextHours = new CurrentForecastPanel(selectedForecasterName, "+" + GWTUtils.LOCALE_CONSTANTS.num_3() + " " + GWTUtils.LOCALE_CONSTANTS.hours(), 
					currentForecastIndex != -1 && forecasts.size() >= currentForecastIndex + 2 ? forecasts.get(currentForecastIndex + 1) : null);
			flexTable.setWidget(0, 1, nextHours);
			completeDetailedForecastVPanel.add(flexTable);
			completeDetailedForecastVPanel.setCellHorizontalAlignment(flexTable, HasHorizontalAlignment.ALIGN_CENTER);
			
			//Table title
			Label lblTableTitle = new Label(GWTUtils.LOCALE_CONSTANTS.detailedForecastsTable());
			lblTableTitle.addStyleName("gwt-Label-Title");
			completeDetailedForecastVPanel.add(lblTableTitle);
			
			completeDetailedForecastVPanel.add(new WgTableB(forecasters, 0, 23));
			completeDetailedForecastVPanel.add(new Label(GWTUtils.LOCALE_CONSTANTS.continue_() + "..."));
			completeDetailedForecastVPanel.add(new WgTableB(forecasters, 23, 46));
			completeDetailedForecastVPanel.add(new Label(GWTUtils.LOCALE_CONSTANTS.continue_() + "..."));
			completeDetailedForecastVPanel.add(new WgTableB(forecasters, 46, null));
		} else {
			final MessagePanel errorPanel = new ErrorMsgPanel();
			errorPanel.setMessage(GWTUtils.LOCALE_CONSTANTS.NOT_SPOT_FORECASTERS_DEFINED());
			completeDetailedForecastVPanel.add(errorPanel);
		}
		
		return completeDetailedForecastVPanel;
	}
	
}
