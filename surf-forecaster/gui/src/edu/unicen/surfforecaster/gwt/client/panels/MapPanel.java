package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.maps.client.InfoWindow;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.overlay.Overlay;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase.TextAlignConstant;

import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.gwt.client.ForecastServices;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;

public class MapPanel extends VerticalPanel {
	
	private MapWidget map;
	private TextBox txtSpotLong = null;
	private TextBox txtSpotLat = null;
	private TextBox txtBuoyLong = null;
	private TextBox txtBuoyLat = null;
	private final String MAP_HEIGHT = "540";
	private final String MAP_WIDTH = "720";
	private final String MAP_COORDINATE_FORMAT = "##0.0#";
	
	
	public MapPanel() {
		Label lblMapDescription = new Label(GWTUtils.LOCALE_CONSTANTS.mapHelpTip());
		lblMapDescription.addStyleName("gwt-Label-RegisterSectionDescription");
		this.add(lblMapDescription);
		
		HorizontalPanel mapHPanel = new HorizontalPanel();
		this.add(mapHPanel);
		
		LatLng cawkerCity = LatLng.newInstance(39.509,-98.434);
	    // Open a map centered on Cawker City, KS USA

	    map = new MapWidget(cawkerCity, 2);
	    map.setSize(MAP_WIDTH, MAP_HEIGHT);
	    
	    // Add some controls for the zoom level
	    //map.addControl(new LargeMapControl());
	    map.setUIToDefault();

	    
	    // Add a marker
	    //map.addOverlay(new Marker(cawkerCity));

	    // Add an info window to highlight a point of interest
//	    map.getInfoWindow().open(map.getCenter(), 
//	        new InfoWindowContent("World's Largest Ball of Sisal Twine"));
	    
	    map.addMapClickHandler(new MapClickHandler() {
	        public void onClick(MapClickEvent e) {
	          MapWidget sender = e.getSender();
	          Overlay overlay = e.getOverlay();
	          LatLng point = e.getLatLng();
	          if (overlay != null && overlay instanceof Marker) {
	            //sender.removeOverlay(overlay);
	        	Marker marker = (Marker)overlay;
	        	if (marker.getTitle().equals(GWTUtils.LOCALE_CONSTANTS.spot())) {
	        		InfoWindow info = sender.getInfoWindow();
		            info.setMaximizeEnabled(false);
		            InfoWindowContent content = new InfoWindowContent(getInfoWindowContent(GWTUtils.LOCALE_CONSTANTS.spotLocation(), marker.getLatLng()));
		            info.open(marker, content);
	        	} else {
	        		if (marker.getTitle().equals(GWTUtils.LOCALE_CONSTANTS.ww3GridPoint())) {
	        			InfoWindow info = sender.getInfoWindow();
			            info.setMaximizeEnabled(false);
			            InfoWindowContent content = new InfoWindowContent(getInfoWindowContent(GWTUtils.LOCALE_CONSTANTS.selectedForecaster(), marker.getLatLng()));
			            info.open(marker, content);
			            txtBuoyLong.setText(("" + NumberFormat.getFormat(MAP_COORDINATE_FORMAT).format(marker.getLatLng().getLongitude())));
			            txtBuoyLat.setText(("" + NumberFormat.getFormat(MAP_COORDINATE_FORMAT).format(marker.getLatLng().getLatitude())));
	        		}
	        	}
	        	
	          } else if (overlay == null){
	        	sender.clearOverlays();
	        	MarkerOptions options = MarkerOptions.newInstance();
	        	options.setTitle(GWTUtils.LOCALE_CONSTANTS.spot());
	            Marker marker = new Marker(point, options);
	            marker.getIcon().setImageURL(GWTUtils.IMAGE_SPOT);
	        	sender.addOverlay(marker);
	            InfoWindow info = sender.getInfoWindow();
	            info.setMaximizeEnabled(false);
	            InfoWindowContent content = new InfoWindowContent(getInfoWindowContent(GWTUtils.LOCALE_CONSTANTS.spotLocation(), point));
	            //content.setMaxContent("Hello Maps - more content");
	            //content.setMaxTitle("Hello Maps");
	            info.open(marker, content);
	            txtSpotLong.setText(("" + NumberFormat.getFormat(MAP_COORDINATE_FORMAT).format(marker.getLatLng().getLongitude())));
	            txtSpotLat.setText(("" + NumberFormat.getFormat(MAP_COORDINATE_FORMAT).format(marker.getLatLng().getLatitude())));
	            txtBuoyLong.setText("");
	            txtBuoyLat.setText("");
	            showWW3Buoys(sender, point);
	          }
	        }
	        
	        
	      });
	    // Add the map to the HTML host page
	    //RootPanel.get("mapsTutorial").add(map);
	    mapHPanel.add(map);
	    
	    FlexTable flexTable = new FlexTable();
	    flexTable.setCellSpacing(5);
	    mapHPanel.add(flexTable);
	    
	    Label lblSpot = new Label(GWTUtils.LOCALE_CONSTANTS.spot());
	    flexTable.setWidget(0, 0, lblSpot);
	    
	    Label lblSpotLat = new Label(GWTUtils.LOCALE_CONSTANTS.latitude());
	    flexTable.setWidget(1, 0, lblSpotLat);
	    
	    txtSpotLat = new TextBox();
	    txtSpotLat.setEnabled(false);
	    txtSpotLat.setMaxLength(10);
	    txtSpotLat.setTextAlignment(TextBoxBase.ALIGN_RIGHT);
	    flexTable.setWidget(2, 0, txtSpotLat);
	    txtSpotLat.setWidth("70");
	    
	    Label lblSpotLong = new Label(GWTUtils.LOCALE_CONSTANTS.longitude());
	    flexTable.setWidget(3, 0, lblSpotLong);
	    
	    txtSpotLong = new TextBox();
	    txtSpotLong.setEnabled(false);
	    txtSpotLong.setMaxLength(10);
	    txtSpotLong.setTextAlignment(TextBoxBase.ALIGN_RIGHT);
	    flexTable.setWidget(4, 0, txtSpotLong);
	    txtSpotLong.setWidth("70");
	    
	    Label lblSpace = new Label("");
	    lblSpace.setHeight("50");
	    flexTable.setWidget(5, 0, lblSpace);
	    
	    Label lblBuoy = new Label(GWTUtils.LOCALE_CONSTANTS.ww3GridPoint());
	    flexTable.setWidget(6, 0, lblBuoy);
	    
	    Label lblBuoyLat = new Label(GWTUtils.LOCALE_CONSTANTS.latitude());
	    flexTable.setWidget(7, 0, lblBuoyLat);
	    
	    txtBuoyLat = new TextBox();
	    txtBuoyLat.setEnabled(false);
	    txtBuoyLat.setMaxLength(10);
	    txtBuoyLat.setTextAlignment(TextBoxBase.ALIGN_RIGHT);
	    flexTable.setWidget(8, 0, txtBuoyLat);
	    txtBuoyLat.setWidth("70");
	    
	    Label lblBuoyLong = new Label(GWTUtils.LOCALE_CONSTANTS.longitude());
	    flexTable.setWidget(9, 0, lblBuoyLong);
	    
	    txtBuoyLong = new TextBox();
	    txtBuoyLong.setEnabled(false);
	    txtBuoyLong.setMaxLength(10);
	    txtBuoyLong.setTextAlignment(TextBoxBase.ALIGN_RIGHT);
	    flexTable.setWidget(10, 0, txtBuoyLong);
	    txtBuoyLong.setWidth("70");
	}
	
	private void showWW3Buoys(final MapWidget map, LatLng point){
		ForecastServices.Util.getInstance().getNearbyGridPoints(new Float(point.getLatitude()), new Float(point.getLongitude()), new AsyncCallback<List<PointDTO>>(){
			public void onSuccess(List<PointDTO> result) {
				if (result != null) {
					MarkerOptions options = MarkerOptions.newInstance();
					options.setTitle(GWTUtils.LOCALE_CONSTANTS.ww3GridPoint());
					
					Iterator<PointDTO> i = result.iterator();
					Marker marker = null;
					while (i.hasNext()) {
						PointDTO point = i.next();
						marker = new Marker(LatLng.newInstance(new Double(point.getLatitude()), new Double(point.getLongitude())), options);
				    	marker.getIcon().setImageURL(GWTUtils.IMAGE_BUOY);
				    	map.addOverlay(marker);
					}
					
				}
			}
				
			public void onFailure(Throwable caught) {
				
			}
		});
    }
	
	private String getInfoWindowContent(String title, LatLng point) {
		return  "<span class=\"gwt-MapInfoTitle\">" + title +"</span>" +
				"<span class=\"gwt-MapInfoText\">" +
					"<ul>" +
						"<li>" + GWTUtils.LOCALE_CONSTANTS.latitude() + ": <b>" + NumberFormat.getFormat(this.MAP_COORDINATE_FORMAT).format(point.getLatitude()) + "</b></li>" +
						"<li>" + GWTUtils.LOCALE_CONSTANTS.longitude() + ": <b>" + NumberFormat.getFormat(this.MAP_COORDINATE_FORMAT).format(point.getLongitude()) + "</b></li>" +
					"</ul>" +
				"</span>";
	}
	
	public String getSpotLong() {
		return this.txtSpotLong.getText();
	}
	
	public String getSpotLat() {
		return this.txtSpotLat.getText();
	}
	
	public String getBuoyLong() {
		return this.txtBuoyLong.getText();
	}
	
	public String getBuoyLat() {
		return this.txtBuoyLat.getText();
	}

}
