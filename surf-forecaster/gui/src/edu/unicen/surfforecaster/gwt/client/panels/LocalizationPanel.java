package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.gwt.client.Area;
import edu.unicen.surfforecaster.gwt.client.Country;
import edu.unicen.surfforecaster.gwt.client.Spot;
import edu.unicen.surfforecaster.gwt.client.SpotServices;
import edu.unicen.surfforecaster.gwt.client.Zone;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;

public class LocalizationPanel extends Composite implements ISurfForecasterBasePanel {
	
	private ListBox areaBox = null;
	private ListBox countryBox = null;
	private ListBox zoneBox = null;
	private ListBox spotBox = null;
	private PushButton forecastButton = null;
	private Widget baseParentPanel = null;
	
	public LocalizationPanel() {
		{
			DisclosurePanel disclosurePanel = new DisclosurePanel(GWTUtils.LOCALE_CONSTANTS.selectSpot(), true);
			disclosurePanel.setAnimationEnabled(true);
			initWidget(disclosurePanel);
			disclosurePanel.setWidth("850");
			{
				VerticalPanel verticalPanel = new VerticalPanel();
				disclosurePanel.setContent(verticalPanel);
				{
					Label lblMessage = new Label("");
					lblMessage.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
					verticalPanel.add(lblMessage);
				}
				{
					FlexTable localizationForm = new FlexTable();
					localizationForm.setCellSpacing(5);
					verticalPanel.add(localizationForm);
					{
						Label lblArea = new Label(GWTUtils.LOCALE_CONSTANTS.area() + ": ");
						localizationForm.setWidget(0, 0, lblArea);
						lblArea.setWidth("90");
					}
					{
						areaBox = new ListBox();
						areaBox.addChangeHandler(new ChangeHandler() {
							public void onChange(ChangeEvent event) {
								//setCountryListItems(areaBox.getValue(areaBox.getSelectedIndex()));
							}
						});
						localizationForm.setWidget(0, 1, areaBox);
						areaBox.setWidth("300");
					}
					{
						Label lblCountry = new Label(GWTUtils.LOCALE_CONSTANTS.country() + ": ");
						localizationForm.setWidget(0, 2, lblCountry);
						lblCountry.setWidth("90");
					}
					{
						countryBox = new ListBox();
						countryBox.addChangeHandler(new ChangeHandler() {
							public void onChange(ChangeEvent event) {
								//setZoneListItems(countryBox.getValue(countryBox.getSelectedIndex()));
							}
						});
						localizationForm.setWidget(0, 3, countryBox);
						countryBox.setWidth("300");
					}
					{
						Label lblZone = new Label(GWTUtils.LOCALE_CONSTANTS.zone() + ": ");
						localizationForm.setWidget(1, 0, lblZone);
						lblZone.setWidth("90");
					}
					{
						zoneBox = new ListBox();
						zoneBox.addChangeHandler(new ChangeHandler() {
							public void onChange(ChangeEvent event) {
								//setSpotListItems(zoneBox.getValue(zoneBox.getSelectedIndex()));
							}
						});
						localizationForm.setWidget(1, 1, zoneBox);
						zoneBox.setWidth("300");
					}
					{
						Label lblSpot = new Label(GWTUtils.LOCALE_CONSTANTS.spot() + ": ");
						localizationForm.setWidget(1, 2, lblSpot);
						lblSpot.setWidth("90");
					}
					{
						spotBox = new ListBox();
						spotBox.addChangeHandler(new ChangeHandler() {
							public void onChange(ChangeEvent event) {
							}
						});
						localizationForm.setWidget(1, 3, spotBox);
						spotBox.setWidth("300");
					}
					{
						forecastButton = new PushButton(GWTUtils.LOCALE_CONSTANTS.forecast());
						forecastButton.setSize("90", GWTUtils.PUSHBUTTON_HEIGHT);
						forecastButton.setEnabled(false);
						forecastButton.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								renderSpotInfo();
							}
						});
						localizationForm.setWidget(2, 0, forecastButton);
					}
					localizationForm.getFlexCellFormatter().setColSpan(2, 0, 4);
					localizationForm.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);		
					localizationForm.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
					localizationForm.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
					localizationForm.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT);
					localizationForm.getCellFormatter().setHorizontalAlignment(1, 2, HasHorizontalAlignment.ALIGN_RIGHT);
				}
			}
		}
		
		//this.setAreaListItems();
	}
	
//	private void setAreaListItems(){
//		SpotServices.Util.getInstance().getAreas(new AsyncCallback<Map<String, List>>(){
//			public void onSuccess(Map<String, List> result) {
//				if (result == null) {
//				} else {
//					Iterator i = null;
//					if (result.containsKey("areas")){
//						i = result.get("areas").iterator();
//						while (i.hasNext()){
//							Area area = (Area)i.next();
//							areaBox.addItem(area.getName(), area.getId());
//						}
//					}
//					
//					if (result.containsKey("countries")){
//						i = result.get("countries").iterator();
//						while (i.hasNext()){
//							Country country = (Country)i.next();
//							countryBox.addItem(country.getName(), country.getId());
//						}
//					}
//					
//					if (result.containsKey("zones")){
//						i = result.get("zones").iterator();
//						while (i.hasNext()){
//							Zone zone = (Zone)i.next();
//							zoneBox.addItem(zone.getName(), zone.getId());
//						}
//					}
//					
//					if (result.containsKey("spots")) {
//						i = result.get("spots").iterator();
//						while (i.hasNext()){
//							Spot spot = (Spot)i.next();
//							spotBox.addItem(spot.getName(), spot.getId());
//						}
//					}
//				}
//				setForecastButtonState();
//				//Show the forecastTabPanel after
//				if (spotBox.getItemCount() > 0 && new Integer(spotBox.getValue(spotBox.getSelectedIndex())) > 0 )
//					renderSpotInfo();
//			}
//				
//			public void onFailure(Throwable caught) {
//				
//			}
//		});
//	}
	
	private void renderSpotInfo() {
		if (this.baseParentPanel instanceof SpotDescriptionPanel) {
			((SpotDescriptionPanel)this.baseParentPanel).showSpotDescription();
		} else if (this.baseParentPanel instanceof ForecastPanel) {
			((ForecastPanel)this.baseParentPanel).showSpotForecast();	
		}
	}
	
//	private void setCountryListItems(String area){
//		countryBox.clear();
//		zoneBox.clear();
//		spotBox.clear();
//		setForecastButtonState();
//		SpotServices.Util.getInstance().getCountries(area, new AsyncCallback<Map<String, List>>(){
//			public void onSuccess(Map<String, List> result) {
//				if (result == null) {
//				} else {
//					Iterator i = null;
//					if (result.containsKey("countries")){
//						i = result.get("countries").iterator();
//						while (i.hasNext()){
//							Country country = (Country)i.next();
//							countryBox.addItem(country.getName(), country.getId());
//						}
//					}
//					
//					if (result.containsKey("zones")){
//						i = result.get("zones").iterator();
//						while (i.hasNext()){
//							Zone zone = (Zone)i.next();
//							zoneBox.addItem(zone.getName(), zone.getId());
//						}
//					}
//					
//					if (result.containsKey("spots")) {
//						i = result.get("spots").iterator();
//						while (i.hasNext()){
//							Spot spot = (Spot)i.next();
//							spotBox.addItem(spot.getName(), spot.getId());
//						}
//					}
//					
//				}
//				setForecastButtonState();
//			}
//				
//			public void onFailure(Throwable caught) {
//				
//			}
//		});
//	}
//	
//	private void setZoneListItems(String country){
//		zoneBox.clear();
//		spotBox.clear();
//		setForecastButtonState();
//		SpotServices.Util.getInstance().getZones(country, new AsyncCallback<Map<String, List>>(){
//			public void onSuccess(Map<String, List> result) {
//				if (result == null) {
//				} else {
//					Iterator i = null;
//					if (result.containsKey("zones")){
//						i = result.get("zones").iterator();
//						while (i.hasNext()){
//							Zone zone = (Zone)i.next();
//							zoneBox.addItem(zone.getName(), zone.getId());
//						}
//					}
//					if (result.containsKey("spots")) {
//						i = result.get("spots").iterator();
//						while (i.hasNext()){
//							Spot spot = (Spot)i.next();
//							spotBox.addItem(spot.getName(), spot.getId());
//						}
//					}
//				}
//				setForecastButtonState();
//			}
//				
//			public void onFailure(Throwable caught) {
//				
//			}
//		});
//	}
//	
//	private void setSpotListItems(String zone){
//		spotBox.clear();
//		setForecastButtonState();
//		SpotServices.Util.getInstance().getSpots(zone, new AsyncCallback<Map<String, List>>(){
//			public void onSuccess(Map<String, List> result) {
//				if (result == null) {
//				} else {
//					if (result.containsKey("spots")){
//						Iterator<Spot> i = result.get("spots").iterator();
//						while (i.hasNext()){
//							Spot spot = i.next();
//							spotBox.addItem(spot.getName(), spot.getId());
//						}
//					}
//				}
//				setForecastButtonState();
//			}
//				
//			public void onFailure(Throwable caught) {
//				
//			}
//		});
//	}
	
	/**
	 * Set the forecast button enabled or disabled according if the spot listbox has a spot selected or not
	 */
	private void setForecastButtonState() {
		if (this.spotBox.getItemCount() > 0 && new Integer(this.spotBox.getValue(this.spotBox.getSelectedIndex())) > 0 )
			this.forecastButton.setEnabled(true);
		else
			this.forecastButton.setEnabled(false);
	}
	
	public String getZoneBoxDisplayValue(){
		return this.zoneBox.getItemText(this.zoneBox.getSelectedIndex());
	}
	
	public String getSpotBoxDisplayValue(){
		return this.spotBox.getItemText(this.spotBox.getSelectedIndex());
	}

	public Widget getBasePanel() {
		return this.baseParentPanel;
	}

	public void setBasePanel(Widget basePanel) {
		this.baseParentPanel = basePanel;
	}
}
