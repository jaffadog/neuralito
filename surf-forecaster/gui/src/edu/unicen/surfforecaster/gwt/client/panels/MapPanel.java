package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.maps.client.InfoWindow;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.overlay.Overlay;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MapPanel extends VerticalPanel {
	
	private MapWidget map;
	
	public MapPanel() {
		LatLng cawkerCity = LatLng.newInstance(39.509,-98.434);
	    // Open a map centered on Cawker City, KS USA

	    map = new MapWidget(cawkerCity, 2);
	    map.setSize("800px", "600px");
	    
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
	            showWW3Buoys(sender, point);
	          }
	        }
	        
	        private void showWW3Buoys(MapWidget map, LatLng point){
	        	MarkerOptions options = MarkerOptions.newInstance();
	        	options.setTitle("Boya WW3");
	        	Marker marker = new Marker(LatLng.newInstance(point.getLatitude() - 1, point.getLongitude() - 1), options);
	        	marker.getIcon().setImageURL("images/buoy.png");
	        	map.addOverlay(marker);
	        	marker = new Marker(LatLng.newInstance(point.getLatitude() - 1, point.getLongitude() + 1), options);
	        	map.addOverlay(marker);
	        	marker = new Marker(LatLng.newInstance(point.getLatitude() + 1, point.getLongitude() - 1), options);
	        	map.addOverlay(marker);
	        	marker = new Marker(LatLng.newInstance(point.getLatitude() + 1, point.getLongitude() + 1), options);
	        	map.addOverlay(marker);
	        }
	      });
	    // Add the map to the HTML host page
	    //RootPanel.get("mapsTutorial").add(map);
	    this.add(map);
	}

}
