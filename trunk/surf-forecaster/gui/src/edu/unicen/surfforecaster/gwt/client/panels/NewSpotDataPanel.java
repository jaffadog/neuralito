package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.AreaDTO;
import edu.unicen.surfforecaster.common.services.dto.CountryDTO;
import edu.unicen.surfforecaster.common.services.dto.ZoneDTO;
import edu.unicen.surfforecaster.gwt.client.SpotServices;
import edu.unicen.surfforecaster.gwt.client.utils.ClientI18NMessages;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.utils.LocalizationUtils;
import edu.unicen.surfforecaster.gwt.client.utils.Observable;
import edu.unicen.surfforecaster.gwt.client.utils.Observer;
import edu.unicen.surfforecaster.gwt.client.utils.TimeZones;
import edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad;

public class NewSpotDataPanel extends LazyPanel implements Observer{

	private ListBox areaBox = null;
	private ListBox countryBox = null;
	private ListBox timeZoneBox = null;
	private ListBox zoneBox;
	private TextBox zoneTxt;
	private PushButton chooseZoneBtn;
	private PushButton createZoneBtn;
	private MapPanel mapPanel;
	private RadioButton radioPublicButton;
	private TextBox spotTxt;
	
	private static final String INPUTS_WIDTH = "300px";
	private static final String TIME_INPUTS_WIDTH = "25px";
	private static final String TABLE_COL_0 = "133px";
	private static final String TABLE_COL_1 = "310px";
	private static final String TABLE_COL_2 = "522px";
	private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL() + "FileUploadServices";
	
	public NewSpotDataPanel() {
	}
	
	@Override
	protected Widget createWidget() {
		
		//VerticalPanel container = new VerticalPanel();
		final FlexTable flexTable = new FlexTable();
		flexTable.setWidth("100%");
		
		final MessagePanel errorPanel = new ErrorMsgPanel();
		errorPanel.setVisible(false);
		flexTable.setWidget(0, 0, errorPanel);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 3);
		
		Vector<String> message = new Vector<String>();
		message.add(ClientI18NMessages.getInstance().getMessage("CHANGES_SAVED_SUCCESFULLY"));
		final MessagePanel successPanel = new SuccessMsgPanel(message);
		successPanel.setVisible(false);
		flexTable.setWidget(1, 0, successPanel);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getFlexCellFormatter().setColSpan(1, 0, 3);
		
		
		Label lblTitle = new Label(GWTUtils.LOCALE_CONSTANTS.newSpotSectionTitle());
		lblTitle.addStyleName("gwt-Label-SectionTitle");
		flexTable.setWidget(2, 0, lblTitle);
		flexTable.getFlexCellFormatter().setColSpan(2, 0, 3);
		
		Label lblNewSpotDescription = new Label("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's " +
				"standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has " +
				"survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s " +
				"with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including " +
				"versions of Lorem Ipsum.");
		lblNewSpotDescription.addStyleName("gwt-Label-SectionDescription");
		flexTable.setWidget(3, 0, lblNewSpotDescription);
		flexTable.getFlexCellFormatter().setColSpan(3, 0, 3);

		final Label lblArea = new Label(GWTUtils.LOCALE_CONSTANTS.area() + ":");
		flexTable.setWidget(4, 0, lblArea);
		flexTable.getCellFormatter().setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		areaBox = new ListBox();
		areaBox.setWidth(NewSpotDataPanel.INPUTS_WIDTH);
		areaBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				setCountryListItems(new Integer(areaBox.getValue(areaBox.getSelectedIndex())));
			}
		});
		flexTable.setWidget(4, 1, areaBox);

		final Label lblCountry = new Label(GWTUtils.LOCALE_CONSTANTS.country() + ":");
		flexTable.setWidget(5, 0, lblCountry);
		flexTable.getCellFormatter().setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		countryBox = new ListBox();
		countryBox.setWidth(NewSpotDataPanel.INPUTS_WIDTH);
		countryBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				setZoneListItems(new Integer(countryBox.getValue(countryBox.getSelectedIndex())));
			}
		});
		flexTable.setWidget(5, 1, countryBox);
		
		final Label lblZone = new Label("* " + GWTUtils.LOCALE_CONSTANTS.zone() + ":");
		flexTable.setWidget(6, 0, lblZone);
		flexTable.getCellFormatter().setHorizontalAlignment(6, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		final Label lblSpot = new Label("* " + GWTUtils.LOCALE_CONSTANTS.spot() + ":");
		flexTable.setWidget(8, 0, lblSpot);
		flexTable.getCellFormatter().setHorizontalAlignment(8, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		final Label lblTimeZone = new Label(GWTUtils.LOCALE_CONSTANTS.timeZone() + ":");
		flexTable.setWidget(9, 0, lblTimeZone);
		flexTable.getCellFormatter().setHorizontalAlignment(9, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		zoneBox = new ListBox();
		zoneBox.setWidth(NewSpotDataPanel.INPUTS_WIDTH);
		flexTable.setWidget(6, 1, zoneBox);
		
		zoneTxt = new TextBox();
		zoneTxt.setMaxLength(50);
		zoneTxt.setWidth(NewSpotDataPanel.INPUTS_WIDTH);
		zoneTxt.setVisible(false);
		flexTable.setWidget(7, 1, zoneTxt);
		
		createZoneBtn = new PushButton();
		createZoneBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
					zoneBox.setEnabled(false);
					zoneTxt.setVisible(true);
					chooseZoneBtn.setVisible(true);
					createZoneBtn.setVisible(false);
			}
		});
		createZoneBtn.setSize("100", GWTUtils.PUSHBUTTON_HEIGHT);
		createZoneBtn.setText("Create a Zone");
		flexTable.setWidget(6, 2, createZoneBtn);
		flexTable.getCellFormatter().setHorizontalAlignment(6, 2, HasHorizontalAlignment.ALIGN_LEFT);
		
		chooseZoneBtn = new PushButton();
		chooseZoneBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
					zoneTxt.setText("");
					zoneBox.setEnabled(true);
					zoneTxt.setVisible(false);
					chooseZoneBtn.setVisible(false);
					createZoneBtn.setVisible(true);
			}
		});
		chooseZoneBtn.setSize("100", GWTUtils.PUSHBUTTON_HEIGHT);
		chooseZoneBtn.setText("Choose a Zone");
		chooseZoneBtn.setVisible(false);
		flexTable.setWidget(7, 2, chooseZoneBtn);
		flexTable.getCellFormatter().setHorizontalAlignment(7, 2, HasHorizontalAlignment.ALIGN_LEFT);

		spotTxt = new TextBox();
		spotTxt.setMaxLength(50);
		flexTable.setWidget(8, 1, spotTxt);
		spotTxt.setWidth(NewSpotDataPanel.INPUTS_WIDTH);
		
		timeZoneBox = new ListBox();
		timeZoneBox.setWidth(NewSpotDataPanel.INPUTS_WIDTH);
		flexTable.setWidget(9, 1, timeZoneBox);
		
		Label lblSpotVisibility = new Label(GWTUtils.LOCALE_CONSTANTS.spotVisibility() + ":");
		flexTable.setWidget(10, 0, lblSpotVisibility);
		flexTable.getCellFormatter().setHorizontalAlignment(10, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		HorizontalPanel radioPanel = new HorizontalPanel();
		radioPanel.setSpacing(5);
		flexTable.setWidget(10, 1, radioPanel);
		
		RadioButton radioPrivateButton = new RadioButton("visibilityRadioGroup", GWTUtils.LOCALE_CONSTANTS.private_());
		radioPrivateButton.setValue(true);
		radioPanel.add(radioPrivateButton);
		
		radioPublicButton = new RadioButton("visibilityRadioGroup", GWTUtils.LOCALE_CONSTANTS.public_());
		radioPanel.add(radioPublicButton);
		
		Label lblLocalization = new Label(GWTUtils.LOCALE_CONSTANTS.geographicLocalization());
		lblLocalization.addStyleName("gwt-Label-Title");
		flexTable.setWidget(11, 0, lblLocalization);
		flexTable.getFlexCellFormatter().setColSpan(11, 0, 3);
		
		mapPanel = new MapPanel();
		flexTable.setWidget(12, 0, mapPanel);
		flexTable.getCellFormatter().setHorizontalAlignment(12, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getFlexCellFormatter().setColSpan(12, 0, 3);
		
		Label lblTrainClassifier = new Label(GWTUtils.LOCALE_CONSTANTS.trainClassifier());
		lblTrainClassifier.addStyleName("gwt-Label-Title");
		flexTable.setWidget(13, 0, lblTrainClassifier);
		flexTable.getFlexCellFormatter().setColSpan(13, 0, 3);
		
		//Form
		final FormPanel form = new FormPanel();
		flexTable.setWidget(14, 0, form);
		flexTable.getFlexCellFormatter().setColSpan(14, 0, 3);
		form.setAction(UPLOAD_ACTION_URL);
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
	    form.setMethod(FormPanel.METHOD_POST);
	    form.addSubmitHandler(new SubmitHandler() {
			@Override
			public void onSubmit(SubmitEvent event) {
				//TODO hacer algo
				
			}
		});
	    form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				//TODO hacer algo 
				Window.alert("archivo subido");
			}
		});
	    
	    FlexTable formTable = new FlexTable();
	    form.setWidget(formTable);
	    //File Upload
	    Label lblVisualObs = new Label(GWTUtils.LOCALE_CONSTANTS.visualObservations());
	    formTable.setWidget(0, 0, lblVisualObs);
	    formTable.getFlexCellFormatter().setColSpan(0, 0, 10);
	    final FileUpload upload = new FileUpload();
	    upload.setName("uploadFormElement");
	    formTable.setWidget(1,0,upload);
	    formTable.getFlexCellFormatter().setColSpan(1, 0, 10);
	    //Day Light time inputs
	    Label lblDayLightTime = new Label(GWTUtils.LOCALE_CONSTANTS.dayLightTime());
	    formTable.setWidget(2, 0, lblDayLightTime);
	    formTable.getFlexCellFormatter().setColSpan(2, 0, 10);
	    Label lblFrom = new Label(GWTUtils.LOCALE_CONSTANTS.from());
	    formTable.setWidget(3, 0, lblFrom);
	    TextBox txtHour = new TextBox();
	    txtHour.setWidth(NewSpotDataPanel.TIME_INPUTS_WIDTH);
	    txtHour.setMaxLength(2);
	    formTable.setWidget(3, 1, txtHour);
	    Label lblHour = new Label(GWTUtils.LOCALE_CONSTANTS.hour_abbr());
	    formTable.setWidget(3, 2, lblHour);
	    TextBox txtMinutes = new TextBox();
	    txtMinutes.setWidth(NewSpotDataPanel.TIME_INPUTS_WIDTH);
	    txtMinutes.setMaxLength(2);
	    formTable.setWidget(3, 3, txtMinutes);
	    Label lblMinutes = new Label(GWTUtils.LOCALE_CONSTANTS.minutes_abbr());
	    formTable.setWidget(3, 4, lblMinutes);
	    Label lblTo = new Label(GWTUtils.LOCALE_CONSTANTS.to());
	    formTable.setWidget(3, 5, lblTo);
	    TextBox txtHour2 = new TextBox();
	    txtHour2.setWidth(NewSpotDataPanel.TIME_INPUTS_WIDTH);
	    txtHour2.setMaxLength(2);
	    formTable.setWidget(3, 6, txtHour2);
	    Label lblHour2 = new Label(GWTUtils.LOCALE_CONSTANTS.hour_abbr());
	    formTable.setWidget(3, 7, lblHour2);
	    TextBox txtMinutes2 = new TextBox();
	    txtMinutes2.setWidth(NewSpotDataPanel.TIME_INPUTS_WIDTH);
	    txtMinutes2.setMaxLength(2);
	    formTable.setWidget(3, 8, txtMinutes2);
	    Label lblMinutes2 = new Label(GWTUtils.LOCALE_CONSTANTS.minutes_abbr());
	    formTable.setWidget(3, 9, lblMinutes2);
		//Hidden spot id
	    final Hidden spotId = new Hidden();
	    spotId.setName("spotIdFormElement");
	    spotId.setID("spotIdFormElement");
	    spotId.setValue("0");
	    formTable.setWidget(5, 0, spotId);
	    formTable.getFlexCellFormatter().setColSpan(5, 0, 10);
		
		flexTable.getColumnFormatter().setWidth(0, NewSpotDataPanel.TABLE_COL_0);
		flexTable.getColumnFormatter().setWidth(1, NewSpotDataPanel.TABLE_COL_1);
		flexTable.getColumnFormatter().setWidth(2, NewSpotDataPanel.TABLE_COL_2);
		
		//Save Button	
		final HTMLButtonGrayGrad saveBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.save(), "NewSpotDataPanel-Save", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX);
//		flexTable.setWidget(15, 0, saveBtn);
//		flexTable.getFlexCellFormatter().setColSpan(15, 0, 0);
//		flexTable.getFlexCellFormatter().setHorizontalAlignment(15, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		
		saveBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				final Vector<String> messages = new Vector<String>();
				errorPanel.setVisible(false);
				successPanel.setVisible(false);
				int countryId = countryBox.getItemCount() == 0 ? 0 : new Integer(countryBox.getValue(countryBox.getSelectedIndex()));
				messages.addAll(validateForm());
				if (messages.isEmpty()){
//					int zoneId = zoneBox.getItemCount() == 0 ? 0 : new Integer(zoneBox.getValue(zoneBox.getSelectedIndex()));
//					
//					SpotServices.Util.getInstance().addSpot(spotTxt.getText().trim(), mapPanel.getSpotLat(), mapPanel.getSpotLong(),
//							mapPanel.getBuoyLat(), mapPanel.getBuoyLong(),  
//							zoneId, countryId, zoneTxt.getText().trim(), radioPublicButton.getValue(), 
//							timeZoneBox.getItemText(timeZoneBox.getSelectedIndex()).trim(), new AsyncCallback<Integer>(){
//						public void onSuccess(Integer result){
//							//clearFields();		
//							successPanel.setVisible(true);
//							
//			            }
//			            public void onFailure(Throwable caught){
//			            	if (((NeuralitoException)caught).getErrorCode().equals(ErrorCode.USER_SESSION_EMPTY_OR_EXPIRED) && 
//									Cookies.getCookie("surfForecaster-Username") != null) {
//								GWTUtils.showSessionExpiredLoginBox();
//							} else {
//								messages.add(ClientI18NMessages.getInstance().getMessage((NeuralitoException)caught));
//								errorPanel.setMessages(messages);
//								errorPanel.setVisible(true);
//							}
//			            }
//						});
					if (!upload.getFilename().trim().equals("")) {
						spotId.setValue("111");
						form.submit();
					}
				}
				else{
					errorPanel.setMessages(messages);
					errorPanel.setVisible(true);
				}
			}
		});
		
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
		
		this.setTimeZoneItems();
		
		return flexTable;
	}
	
	private Vector<String> validateForm() {
		Vector<String> messages = new Vector<String>();
		if (this.areaBox.getSelectedIndex() == -1)
			messages.add(GWTUtils.LOCALE_CONSTANTS.MANDATORY_AREA_VALUE());
		
		if (this.countryBox.getSelectedIndex() == -1)
			messages.add(GWTUtils.LOCALE_CONSTANTS.MANDATORY_COUNTRY_VALUE());
		
		if ((!this.zoneTxt.isVisible() && this.zoneBox.getSelectedIndex() == -1) || 
				(this.zoneTxt.isVisible() && this.zoneTxt.getText().trim().equals("")))
			messages.add(GWTUtils.LOCALE_CONSTANTS.MANDATORY_ZONE_NAME());
		
		if (this.spotTxt.getText().trim().equals(""))
			messages.add(GWTUtils.LOCALE_CONSTANTS.MANDATORY_SPOT_NAME());
		
		if (this.mapPanel.getSpotLat().trim().equals("") || this.mapPanel.getSpotLong().trim().equals(""))
			messages.add(GWTUtils.LOCALE_CONSTANTS.MANDATORY_SPOT_LAT_LONG());
		
		if (this.mapPanel.getBuoyLat().trim().equals("") || this.mapPanel.getBuoyLong().trim().equals(""))
			messages.add(GWTUtils.LOCALE_CONSTANTS.MANDATORY_BUOY_LAT_LONG());
		return messages;
	}
	
	private void setTimeZoneItems() {
		TimeZones timeZones = TimeZones.getInstance();
		Object[] keys = (Object[]) timeZones.getTimeZones().get("orderedKeys");
		
		for (int i = 0; i < keys.length; i++) 
			this.timeZoneBox.addItem((String)keys[i], (String)timeZones.getTimeZones().get(keys[i]));
	}

	public void update(Observable o, Object arg) {
		if (o == LocalizationUtils.getInstance()) {
			this.setAreaListItems();
			if (this.areaBox.getItemCount() > 0)
				this.setCountryListItems(new Integer(this.areaBox.getValue(this.areaBox.getSelectedIndex())));
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
		countryBox.clear();
		zoneBox.clear();
		Iterator<CountryDTO> i = LocalizationUtils.getInstance().getCountries(areaId).iterator(); 
		while (i.hasNext()){
			CountryDTO country = i.next();
			this.countryBox.addItem(country.getNames().get(GWTUtils.getCurrentLocaleCode()), country.getId().toString());
		}
		if (this.countryBox.getItemCount() > 0)
			this.setZoneListItems(new Integer(this.countryBox.getValue(this.countryBox.getSelectedIndex())));
	}
	
	private void setZoneListItems(Integer countryId){
		zoneBox.clear();
		SpotServices.Util.getInstance().getZones(countryId, new AsyncCallback<List<ZoneDTO>>(){
			public void onSuccess(List<ZoneDTO> result) {
				Iterator<ZoneDTO> i = result.iterator(); 
				while (i.hasNext()){
					ZoneDTO zone = i.next();
					zoneBox.addItem(zone.getName(), zone.getId().toString());
				}
			}
				
			public void onFailure(Throwable caught) {
				//TODO do something when the getspots methos fails
			}
		});
	}
}
