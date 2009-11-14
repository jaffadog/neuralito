package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.common.services.dto.AreaDTO;
import edu.unicen.surfforecaster.common.services.dto.CountryDTO;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.common.services.dto.ZoneDTO;
import edu.unicen.surfforecaster.gwt.client.SpotServices;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.utils.LocalizationUtils;
import edu.unicen.surfforecaster.gwt.client.utils.Observable;

public class LinksLocalizationPanel extends Composite implements ILocalizationPanel, ISurfForecasterBasePanel, 
		ClickHandler, ChangeHandler, BlurHandler{
	
	private ListBox areaBox = null;
	private Hyperlink areaLink = null;
	private ListBox countryBox = null;
	private Hyperlink countryLink = null;
	private ListBox zoneBox = null;
	private Hyperlink zoneLink = null;
	private ListBox spotBox = null;
	private Hyperlink spotLink = null;
	private PushButton button = null;
	private Widget baseParentPanel = null;
	
	private final static String INPUT_WIDTH = "200px";
	private FlexTable localizationForm;
	
	public LinksLocalizationPanel() {
		{
			DisclosurePanel disclosurePanel = new DisclosurePanel(GWTUtils.LOCALE_CONSTANTS.selectSpot(), true);
			disclosurePanel.setWidth("100%");
			disclosurePanel.setAnimationEnabled(true);
			initWidget(disclosurePanel);
			{
				localizationForm = new FlexTable();
				disclosurePanel.setContent(localizationForm);
				{
					areaLink = new Hyperlink("<Choose area>", "");
					localizationForm.setWidget(0, 0, areaLink);
					areaLink.addClickHandler(this);
				}
				{
					areaBox = new ListBox();
					areaBox.addChangeHandler(this);
					areaBox.addBlurHandler(this);
					areaBox.setWidth(LinksLocalizationPanel.INPUT_WIDTH);
				}
				{
					localizationForm.setWidget(0, 1, this.getSeparator());
				}
				{
					countryLink = new Hyperlink("<Choose country>", "");
					localizationForm.setWidget(0, 2, countryLink);
					countryLink.addClickHandler(this);
				}
				{
					countryBox = new ListBox();
					countryBox.addChangeHandler(this);
					countryBox.addBlurHandler(this);
					countryBox.setWidth(LinksLocalizationPanel.INPUT_WIDTH);
				}
				{
					localizationForm.setWidget(0, 3, this.getSeparator());
				}
				{
					zoneLink = new Hyperlink("<Choose zone>", "");
					localizationForm.setWidget(0, 4, zoneLink);
					zoneLink.addClickHandler(this);
				}
				{
					zoneBox = new ListBox();
					zoneBox.addChangeHandler(this);
					zoneBox.addBlurHandler(this);
					zoneBox.setWidth(LinksLocalizationPanel.INPUT_WIDTH);
				}
//				{
//					Label lblSpot = new Label(GWTUtils.LOCALE_CONSTANTS.spot() + ": ");
//					localizationForm.setWidget(1, 2, lblSpot);
//					lblSpot.setWidth("90");
//				}
				{
					spotBox = new ListBox();
//					spotBox.addChangeHandler(new ChangeHandler() {
//						public void onChange(ChangeEvent event) {
//						}
//					});
//					localizationForm.setWidget(1, 3, spotBox);
//					spotBox.setWidth(LinksLocalizationPanel.INPUT_WIDTH);
				}
//				{
//					button = new PushButton(GWTUtils.LOCALE_CONSTANTS.forecast());
//					button.setSize("90", GWTUtils.PUSHBUTTON_HEIGHT);
//					button.setEnabled(false);
//					button.addClickHandler(new ClickHandler() {
//						public void onClick(ClickEvent event) {
//							renderSpotInfo();
//						}
//					});
//					localizationForm.setWidget(2, 0, button);
//				}
//				localizationForm.getFlexCellFormatter().setColSpan(2, 0, 4);
//				localizationForm.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);		
//				localizationForm.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
//				localizationForm.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
//				localizationForm.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT);
//				localizationForm.getCellFormatter().setHorizontalAlignment(1, 2, HasHorizontalAlignment.ALIGN_RIGHT);
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
	
	private Label getSeparator() {
		return new Label(" > ");
	}
	
//	private void renderSpotInfo() {
//		if (this.baseParentPanel instanceof SpotDescriptionPanel) {
//			((SpotDescriptionPanel)this.baseParentPanel).showSpotDescription();
//		} else if (this.baseParentPanel instanceof ForecastPanel) {
//			((ForecastPanel)this.baseParentPanel).getSpotLastestForecast();	
//		}
//	}
	
	/**
	 * Set the forecast button enabled or disabled according if the spot listbox has a spot selected or not
	 */
//	private void setForecastButtonState() {
//		if (this.spotBox.getItemCount() > 0 && new Integer(this.spotBox.getValue(this.spotBox.getSelectedIndex())) > 0 )
//			this.button.setEnabled(true);
//		else
//			this.button.setEnabled(false);
//	}
	
	/**
	 * @return String - The Text showed in the Zone listbox widget
	 */
	public String getZoneBoxDisplayText(){
		return this.zoneBox.getItemText(this.zoneBox.getSelectedIndex());
	}
	
	/**
	 * @return String - The Text showed in the Spot listbox widget
	 */
	public String getSpotBoxDisplayText(){
		return this.spotBox.getItemText(this.spotBox.getSelectedIndex());
	}
	
	/**
	 * @return String - The value of the item selected in the Spot listbox widget
	 */
	public String getSpotBoxDisplayValue() {
		return this.spotBox.getValue(this.spotBox.getSelectedIndex());
	}

	/**
	 * @return Widget - A parent panel 1 or more levels higher that the user setted as the base panel for this widget
	 */
	public Widget getBasePanel() {
		return this.baseParentPanel;
	}

	/**
	 * Setup a panel that represents be the parent panel of the widget, this panel could not be the inmediatly parent 
	 */
	public void setBasePanel(Widget basePanel) {
		this.baseParentPanel = basePanel;
	}

	/**
	 * This class is an observer of LocalizationUtils, so must implements update method
	 */
	public void update(Observable o, Object arg) {
		if (o == LocalizationUtils.getInstance()) {
			this.setAreaListItems();
			if (this.areaBox.getItemCount() > 0) {
				areaLink.setText(areaBox.getItemText(areaBox.getSelectedIndex()));
				this.setCountryListItems(new Integer(this.areaBox.getValue(this.areaBox.getSelectedIndex())));
			}
		}
	}
	
	private void setAreaListItems() {
		Iterator<AreaDTO> i = LocalizationUtils.getInstance().getAreas().iterator(); 
		while (i.hasNext()){
			AreaDTO area = i.next();
			this.areaBox.addItem(area.getNames().get(GWTUtils.getCurrentLocaleCode()), area.getId().toString());
		}
	}
	
	private void setCountryListItems(Integer areaId) {
		this.countryBox.clear();
		this.zoneBox.clear();
		this.spotBox.clear();
		Iterator<CountryDTO> i = LocalizationUtils.getInstance().getCountries(areaId).iterator(); 
		while (i.hasNext()){
			CountryDTO country = i.next();
			this.countryBox.addItem(country.getNames().get(GWTUtils.getCurrentLocaleCode()), country.getId().toString());
			
		}
		if (this.countryBox.getItemCount() > 0) {
			this.setZoneListItems(new Integer(this.countryBox.getValue(this.countryBox.getSelectedIndex())));
			countryLink.setText(countryBox.getItemText(countryBox.getSelectedIndex()));
		}
	}
	
	private void setZoneListItems(Integer countryId){
		this.zoneBox.clear();
		this.spotBox.clear();
		SpotServices.Util.getInstance().getZones(countryId, new AsyncCallback<List<ZoneDTO>>(){
			public void onSuccess(List<ZoneDTO> result) {
				Iterator<ZoneDTO> i = result.iterator(); 
				while (i.hasNext()){
					ZoneDTO zone = i.next();
					zoneBox.addItem(zone.getName(), zone.getId().toString());
				}
				if (zoneBox.getItemCount() > 0) {
					zoneLink.setText(zoneBox.getItemText(zoneBox.getSelectedIndex()));
					setSpotListItems(new Integer(zoneBox.getValue(zoneBox.getSelectedIndex())));
				}
			}
				
			public void onFailure(Throwable caught) {
				//TODO do something when the getzones methos fails
			}
		});
	}
	
	private void setSpotListItems(Integer zoneId){
		this.spotBox.clear();
		SpotServices.Util.getInstance().getSpots(zoneId, new AsyncCallback<List<SpotDTO>>(){
			public void onSuccess(List<SpotDTO> result) {
				Iterator<SpotDTO> i = result.iterator(); 
				while (i.hasNext()){
					SpotDTO spot = i.next();
					spotBox.addItem(spot.getName(), spot.getId().toString());
				}
				
				//setForecastButtonState();
			}
				
			public void onFailure(Throwable caught) {
				//TODO do something when the getspots methos fails
			}
		});
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if (sender == areaLink) {
			localizationForm.setWidget(0, 0, areaBox);
			areaBox.setFocus(true);
		} else if (sender == countryLink) {
			localizationForm.setWidget(0, 2, countryBox);
			countryBox.setFocus(true);
		} else if (sender == zoneLink) {
			localizationForm.setWidget(0, 4, zoneBox);
			zoneBox.setFocus(true);
		}
	}

	@Override
	public void onChange(ChangeEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if (sender == areaBox) {
			setCountryListItems(new Integer(areaBox.getValue(areaBox.getSelectedIndex())));
			areaLink.setText(areaBox.getItemText(areaBox.getSelectedIndex()));
			localizationForm.setWidget(0, 0, areaLink);
		} else if (sender == countryBox) {
			setZoneListItems(new Integer(countryBox.getValue(countryBox.getSelectedIndex())));
			countryLink.setText(countryBox.getItemText(countryBox.getSelectedIndex()));
			localizationForm.setWidget(0, 2, countryLink);
		} else if (sender == zoneBox) {
			setSpotListItems(new Integer(zoneBox.getValue(zoneBox.getSelectedIndex())));
			zoneLink.setText(zoneBox.getItemText(zoneBox.getSelectedIndex()));
			localizationForm.setWidget(0, 4, zoneLink);
		}
		
	}

	@Override
	public void onBlur(BlurEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if (sender == areaBox) {
			areaLink.setText(areaBox.getItemText(areaBox.getSelectedIndex()));
			localizationForm.setWidget(0, 0, areaLink);
		} else if (sender == countryBox) {
			countryLink.setText(countryBox.getItemText(countryBox.getSelectedIndex()));
			localizationForm.setWidget(0, 2, countryLink);
		} else if (sender == zoneBox) {
			zoneLink.setText(zoneBox.getItemText(zoneBox.getSelectedIndex()));
			localizationForm.setWidget(0, 4, zoneLink);
		}
	}
}
