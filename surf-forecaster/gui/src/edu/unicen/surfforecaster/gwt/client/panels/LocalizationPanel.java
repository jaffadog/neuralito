package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.common.services.dto.ZoneDTO;
import edu.unicen.surfforecaster.gwt.client.SpotServices;
import edu.unicen.surfforecaster.gwt.client.dto.AreaGwtDTO;
import edu.unicen.surfforecaster.gwt.client.dto.CountryGwtDTO;
import edu.unicen.surfforecaster.gwt.client.dto.SpotGwtDTO;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.utils.LocalizationUtils;
import edu.unicen.surfforecaster.gwt.client.utils.Observable;
import edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad;

public class LocalizationPanel extends Composite implements ILocalizationPanel, ISurfForecasterBasePanel {
	
	private ListBox areaBox = null;
	private ListBox countryBox = null;
	private ListBox zoneBox = null;
	private ListBox spotBox = null;
	private HTMLButtonGrayGrad forecastButton = null;
	private Widget baseParentPanel = null;
	
	public LocalizationPanel() {
		{
			DisclosurePanel disclosurePanel = new DisclosurePanel(GWTUtils.LOCALE_CONSTANTS.selectSpot(), true);
			disclosurePanel.setWidth("100%");
			disclosurePanel.setAnimationEnabled(true);
			initWidget(disclosurePanel);
			this.setWidth("100%");
			{
				VerticalPanel verticalPanel = new VerticalPanel();
				verticalPanel.setWidth("100%");
				verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				disclosurePanel.setContent(verticalPanel);
				{
					FlexTable localizationForm = new FlexTable();
					localizationForm.setCellSpacing(5);
					verticalPanel.add(localizationForm);
					{
						Label lblArea = new Label(GWTUtils.LOCALE_CONSTANTS.area() + ": ");
						localizationForm.setWidget(0, 0, lblArea);
					}
					{
						areaBox = new ListBox();
						areaBox.addChangeHandler(new ChangeHandler() {
							public void onChange(ChangeEvent event) {
								setCountryListItems(new Integer(areaBox.getValue(areaBox.getSelectedIndex())));
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
								setZoneListItems(new Integer(countryBox.getValue(countryBox.getSelectedIndex())));
							}
						});
						localizationForm.setWidget(0, 3, countryBox);
						countryBox.setWidth("300");
					}
					{
						Label lblZone = new Label(GWTUtils.LOCALE_CONSTANTS.zone() + ": ");
						localizationForm.setWidget(1, 0, lblZone);
					}
					{
						zoneBox = new ListBox();
						zoneBox.addChangeHandler(new ChangeHandler() {
							public void onChange(ChangeEvent event) {
								setSpotListItems(new Integer(zoneBox.getValue(zoneBox.getSelectedIndex())));
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
					localizationForm.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
					localizationForm.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
					localizationForm.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT);
					localizationForm.getCellFormatter().setHorizontalAlignment(1, 2, HasHorizontalAlignment.ALIGN_RIGHT);
				}
				{
					//For button id appended at the end the current time in milliseconds to avoid duplicated ids along all of this panels creation in the app.
					forecastButton = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.forecast(), "LocalizationPanel-Forecast" + (new Date()).getTime() , HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_150PX);
					forecastButton.setEnabled(false);
					forecastButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							if (forecastButton.isEnabled())
								renderSpotInfo();
							else
								new MessageBox(GWTUtils.LOCALE_CONSTANTS.close(), GWTUtils.LOCALE_CONSTANTS.mustSelectSpot(), MessageBox.IconType.INFO);
						}
					});
					verticalPanel.add(forecastButton);
				}
			}
		}
		
		/**
		 * Make this panel an observer for LocalizationUtils object
		 */
		LocalizationUtils.getInstance().addObserver(this);
		
		/**
		 * Also, If all localization calls finished, force update (observer impl. method) to avoid the possibility that
		 * the observable class notify before this class adds himself as an observer 
		 */
		if (LocalizationUtils.getInstance().getCallsQueue().allFinished())
			update(LocalizationUtils.getInstance(), null);
	}
	
	private void renderSpotInfo() {
		if (this.baseParentPanel instanceof SpotDescriptionPanel) {
			((SpotDescriptionPanel)this.baseParentPanel).showSpotDescription();
		} else if (this.baseParentPanel instanceof ForecastPanel) {
			((ForecastPanel)this.baseParentPanel).getSpotLastestForecast();	
		}
	}
	
	/**
	 * Set the forecast button enabled or disabled according if the spot listbox has a spot selected or not
	 */
	private void setForecastButtonState() {
		if (this.spotBox.getItemCount() > 0 && new Integer(this.spotBox.getValue(this.spotBox.getSelectedIndex())) > 0 )
			this.forecastButton.setEnabled(true);
		else
			this.forecastButton.setEnabled(false);
	}
	
	/**
	 * @see edu.unicen.surfforecaster.gwt.client.panels.ILocalizationPanel#getZoneBoxDisplayText()
	 */
	public String getZoneBoxDisplayText(){
		return this.zoneBox.getItemText(this.zoneBox.getSelectedIndex());
	}
	
	/**
	 * @see edu.unicen.surfforecaster.gwt.client.panels.ILocalizationPanel#getSpotBoxDisplayText()
	 */
	public String getSpotBoxDisplayText(){
		return this.spotBox.getItemText(this.spotBox.getSelectedIndex());
	}
	
	/**
	 * @see edu.unicen.surfforecaster.gwt.client.panels.ILocalizationPanel#getSpotBoxDisplayValue()
	 */
	public String getSpotBoxDisplayValue() {
		return this.spotBox.getValue(this.spotBox.getSelectedIndex());
	}

	/**
	 * @see edu.unicen.surfforecaster.gwt.client.panels.ILocalizationPanel#getBasePanel()
	 */
	public Widget getBasePanel() {
		return this.baseParentPanel;
	}

	/**
	 * @see edu.unicen.surfforecaster.gwt.client.panels.ILocalizationPanel#setBasePanel(com.google.gwt.user.client.ui.Widget)
	 */
	public void setBasePanel(Widget basePanel) {
		this.baseParentPanel = basePanel;
	}

	/**
	 * @see edu.unicen.surfforecaster.gwt.client.panels.ILocalizationPanel#update(edu.unicen.surfforecaster.gwt.client.utils.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		if (o == LocalizationUtils.getInstance()) {
			this.setAreaListItems();
			if (this.areaBox.getItemCount() > 0)
				this.setCountryListItems(new Integer(this.areaBox.getValue(this.areaBox.getSelectedIndex())));
		}
	}
	
	private void setAreaListItems() {
		this.areaBox.clear();
		forecastButton.setEnabled(false);
		Iterator<AreaGwtDTO> i = LocalizationUtils.getInstance().getAreas().iterator(); 
		while (i.hasNext()){
			AreaGwtDTO area = i.next();
			this.areaBox.addItem(area.getName(), area.getId().toString());
		}
	}
	
	private void setCountryListItems(Integer areaId) {
		this.countryBox.clear();
		this.zoneBox.clear();
		this.spotBox.clear();
		forecastButton.setEnabled(false);
		Iterator<CountryGwtDTO> i = LocalizationUtils.getInstance().getCountries(areaId).iterator(); 
		while (i.hasNext()){
			CountryGwtDTO country = i.next();
			this.countryBox.addItem(country.getName(), country.getId().toString());
			
		}
		if (this.countryBox.getItemCount() > 0)
			this.setZoneListItems(new Integer(this.countryBox.getValue(this.countryBox.getSelectedIndex())));
	}
	
	private void setZoneListItems(Integer countryId){
		this.zoneBox.clear();
		this.spotBox.clear();
		forecastButton.setEnabled(false);
		SpotServices.Util.getInstance().getZones(countryId, new AsyncCallback<List<ZoneDTO>>(){
			public void onSuccess(List<ZoneDTO> result) {
				Iterator<ZoneDTO> i = result.iterator(); 
				while (i.hasNext()){
					ZoneDTO zone = i.next();
					zoneBox.addItem(zone.getName(), zone.getId().toString());
				}
				if (zoneBox.getItemCount() > 0)
					setSpotListItems(new Integer(zoneBox.getValue(zoneBox.getSelectedIndex())));
			}
				
			public void onFailure(Throwable caught) {
				//TODO do something when the getzones methos fails
			}
		});
	}
	
	private void setSpotListItems(Integer zoneId){
		this.spotBox.clear();
		forecastButton.setEnabled(false);
		SpotServices.Util.getInstance().getSpots(zoneId, new AsyncCallback<List<SpotGwtDTO>>(){
			public void onSuccess(List<SpotGwtDTO> result) {
				Iterator<SpotGwtDTO> i = result.iterator(); 
				while (i.hasNext()){
					SpotGwtDTO spot = i.next();
					spotBox.addItem(spot.getName(), spot.getId().toString());
				}
				
				setForecastButtonState();
			}
				
			public void onFailure(Throwable caught) {
				//TODO do something when the getspots methos fails
			}
		});
	}

	@Override
	public String getZoneBoxDisplayValue() {
		return null;
	}
}
