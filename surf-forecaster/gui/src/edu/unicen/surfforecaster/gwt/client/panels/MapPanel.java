package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Iterator;
import java.util.List;

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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;

import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.gwt.client.ForecastServices;

public class MapPanel extends VerticalPanel {
	
	private MapWidget map;
	private TextBox txtSpotLong = null;
	private TextBox txtSpotLat = null;
	private TextBox txtBuoyLong = null;
	private TextBox txtBuoyLat = null;
	
	public MapPanel() {
		Label lblMapDescription = new Label("Clickee en la imagen para ubicar geograficamente la ola que desea pronosticar, o ingrese" +
				"manualmente las coordenadas en los campos a la derecha del mapa");
		lblMapDescription.addStyleName("gwt-Label-RegisterSectionDescription");
		this.add(lblMapDescription);
		
		HorizontalPanel mapHPanel = new HorizontalPanel();
		this.add(mapHPanel);
		
		LatLng cawkerCity = LatLng.newInstance(39.509,-98.434);
	    // Open a map centered on Cawker City, KS USA

	    map = new MapWidget(cawkerCity, 2);
	    map.setSize("720", "540");
	    
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
	        	if (marker.getTitle().equals("Playa")) {
	        		InfoWindow info = sender.getInfoWindow();
		            info.setMaximizeEnabled(false);
		            InfoWindowContent content = new InfoWindowContent("Seleccionaste la playa");
		            info.open(marker, content);
	        	} else {
	        		if (marker.getTitle().equals("Boya WW3")) {
	        			InfoWindow info = sender.getInfoWindow();
			            info.setMaximizeEnabled(false);
			            InfoWindowContent content = new InfoWindowContent("Boya seleccionada: <br> - Latitud: <b>" + marker.getLatLng().getLatitude() + "</b><br> - Longitud: <b>" + marker.getLatLng().getLongitude() + "</b>");
			            info.open(marker, content);
			            txtBuoyLong.setText(("" + marker.getLatLng().getLongitude()).substring(0, 10));
			            txtBuoyLat.setText(("" + marker.getLatLng().getLatitude()).substring(0, 10));
	        		}
	        	}
	        	
	          } else if (overlay == null){
	        	sender.clearOverlays();
	        	MarkerOptions options = MarkerOptions.newInstance();
	        	options.setTitle("Playa");
	            Marker marker = new Marker(point, options);
	            marker.getIcon().setImageURL("images/wave.png");
	        	sender.addOverlay(marker);
	            InfoWindow info = sender.getInfoWindow();
	            info.setMaximizeEnabled(false);
	            InfoWindowContent content = new InfoWindowContent("Coordenadas seleccionadas: <br> - Latitud: <b>" + point.getLatitude() + "</b><br> - Longitud: <b>" + point.getLongitude() + "</b>");
	            //content.setMaxContent("Hello Maps - more content");
	            //content.setMaxTitle("Hello Maps");
	            info.open(marker, content);
	            txtSpotLong.setText(("" + marker.getLatLng().getLongitude()).substring(0, 10));
	            txtSpotLat.setText(("" + marker.getLatLng().getLatitude()).substring(0, 10));
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
	    
	    Label lblSpot = new Label("Ola");
	    flexTable.setWidget(0, 0, lblSpot);
	    
	    Label lblSpotLong = new Label("Longitud");
	    flexTable.setWidget(1, 0, lblSpotLong);
	    
	    txtSpotLong = new TextBox();
	    txtSpotLong.setEnabled(false);
	    txtSpotLong.setMaxLength(10);
	    flexTable.setWidget(2, 0, txtSpotLong);
	    txtSpotLong.setWidth("100");
	    
	    Label lblSpotLat = new Label("Latitud");
	    flexTable.setWidget(3, 0, lblSpotLat);
	    
	    txtSpotLat = new TextBox();
	    txtSpotLat.setEnabled(false);
	    txtSpotLat.setMaxLength(10);
	    flexTable.setWidget(4, 0, txtSpotLat);
	    txtSpotLat.setWidth("100");
	    
	    Label lblSpace = new Label("");
	    lblSpace.setHeight("50");
	    flexTable.setWidget(5, 0, lblSpace);
	    
	    Label lblBuoy = new Label("Boya");
	    flexTable.setWidget(6, 0, lblBuoy);
	    
	    Label lblBuoyLong = new Label("Longitud");
	    flexTable.setWidget(7, 0, lblBuoyLong);
	    
	    txtBuoyLong = new TextBox();
	    txtBuoyLong.setEnabled(false);
	    txtBuoyLong.setMaxLength(10);
	    flexTable.setWidget(8, 0, txtBuoyLong);
	    txtBuoyLong.setWidth("100");
	    
	    Label lblBuoyLat = new Label("Latitud");
	    flexTable.setWidget(9, 0, lblBuoyLat);
	    
	    txtBuoyLat = new TextBox();
	    txtBuoyLat.setEnabled(false);
	    txtBuoyLat.setMaxLength(10);
	    flexTable.setWidget(10, 0, txtBuoyLat);
	    txtBuoyLat.setWidth("100");
	}
	
	private void showWW3Buoys(final MapWidget map, LatLng point){
		ForecastServices.Util.getInstance().getNearbyGridPoints(point.getLatitude(), point.getLongitude(), new AsyncCallback<List<PointDTO>>(){
			public void onSuccess(List<PointDTO> result) {
				if (result != null) {
					MarkerOptions options = MarkerOptions.newInstance();
					options.setTitle("Boya WW3");
					
					Iterator<PointDTO> i = result.iterator();
					Marker marker = null;
					while (i.hasNext()) {
						PointDTO point = i.next();
						marker = new Marker(LatLng.newInstance(point.getLatitude(), point.getLongitude()), options);
				    	marker.getIcon().setImageURL("images/buoy.png");
				    	map.addOverlay(marker);
					}
					
				}
			}
				
			public void onFailure(Throwable caught) {
				
			}
		});
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
