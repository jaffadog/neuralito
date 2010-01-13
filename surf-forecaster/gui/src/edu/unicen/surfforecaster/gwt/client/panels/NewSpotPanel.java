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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.AreaDTO;
import edu.unicen.surfforecaster.common.services.dto.CountryDTO;
import edu.unicen.surfforecaster.common.services.dto.PointDTO;
import edu.unicen.surfforecaster.common.services.dto.ZoneDTO;
import edu.unicen.surfforecaster.gwt.client.ForecastServices;
import edu.unicen.surfforecaster.gwt.client.SpotServices;
import edu.unicen.surfforecaster.gwt.client.dto.SpotGwtDTO;
import edu.unicen.surfforecaster.gwt.client.dto.WekaForecasterEvaluationGwtDTO;
import edu.unicen.surfforecaster.gwt.client.utils.ClientI18NMessages;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.utils.LocalizationUtils;
import edu.unicen.surfforecaster.gwt.client.utils.Observable;
import edu.unicen.surfforecaster.gwt.client.utils.Observer;
import edu.unicen.surfforecaster.gwt.client.utils.TimeZones;
import edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad;

/**
 * Used both for create and edit spots.
 * For create spots use the empty constructor, for edit use the constructor with spotId as parameter
 * @author MAXI
 *
 */
public class NewSpotPanel extends FlexTable implements Observer{

	private SpotGwtDTO spot = null;
	
	private ListBox areaBox = null;
	private ListBox countryBox = null;
	private ListBox timeZoneBox = null;
	private ListBox zoneBox;
	private TextBox zoneTxt;
	private HTMLButtonGrayGrad chooseZoneBtn;
	private HTMLButtonGrayGrad createZoneBtn;
	private MapPanel mapPanel;
	private TextBox spotTxt;
	private RadioButton radioPrivateButton;
	private RadioButton radioPublicButton;
	private FormPanel form;
	private FileUpload upload;
	private TextBox txtHour;
	private TextBox txtMinutes;
	private TextBox txtHour2;
	private TextBox txtMinutes2;
	private Hidden spotId;
	private Hidden latitudeGridPoint;
	private Hidden longitudeGridPoint;
	private RadioButton radioAppendButton;
	private RadioButton radioReplaceButton;
	private MessagePanel errorPanel;
	private MessagePanel successPanel;
	private FlexTable formTable;
	private Label lblTitle;
	
	//static fields
	private static String PANEL_MODE = "create";
	private static final String INPUTS_WIDTH = "300px";
	private static final String TIME_INPUTS_WIDTH = "25px";
	private static final String TABLE_COL_0 = "133px";
	private static final String TABLE_COL_1 = "310px";
	private static final String TABLE_COL_2 = "522px";
	private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL() + "FileUploadServices";

	private Label lblNewSpotDescription;
	
	public NewSpotPanel() {
		NewSpotPanel.PANEL_MODE = "create";
		this.setWidth("100%");
		
		lblTitle = new Label(GWTUtils.LOCALE_CONSTANTS.newSpotSectionTitle());
		lblTitle.addStyleName("gwt-Label-SectionTitle");
		this.setWidget(0, 0, lblTitle);
		this.getFlexCellFormatter().setColSpan(0, 0, 3);
		
		errorPanel = new ErrorMsgPanel();
		errorPanel.setVisible(false);
		this.setWidget(1, 0, errorPanel);
		this.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		this.getFlexCellFormatter().setColSpan(1, 0, 3);
		
		successPanel = new SuccessMsgPanel();
		successPanel.setVisible(false);
		this.setWidget(2, 0, successPanel);
		this.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		this.getFlexCellFormatter().setColSpan(2, 0, 3);
		
		lblNewSpotDescription = new Label(GWTUtils.LOCALE_CONSTANTS.registerNewSpotDesc());
		lblNewSpotDescription.addStyleName("gwt-Label-SectionDescription");
		this.setWidget(3, 0, lblNewSpotDescription);
		this.getFlexCellFormatter().setColSpan(3, 0, 3);

		final Label lblArea = new Label(GWTUtils.LOCALE_CONSTANTS.area() + ":");
		this.setWidget(4, 0, lblArea);
		this.getCellFormatter().setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		areaBox = new ListBox();
		areaBox.setWidth(NewSpotPanel.INPUTS_WIDTH);
		areaBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				setCountryListItems(new Integer(areaBox.getValue(areaBox.getSelectedIndex())));
			}
		});
		this.setWidget(4, 1, areaBox);

		final Label lblCountry = new Label(GWTUtils.LOCALE_CONSTANTS.country() + ":");
		this.setWidget(5, 0, lblCountry);
		this.getCellFormatter().setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		countryBox = new ListBox();
		countryBox.setWidth(NewSpotPanel.INPUTS_WIDTH);
		countryBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				setZoneListItems(new Integer(countryBox.getValue(countryBox.getSelectedIndex())));
			}
		});
		this.setWidget(5, 1, countryBox);
		
		final Label lblZone = new Label("* " + GWTUtils.LOCALE_CONSTANTS.zone() + ":");
		this.setWidget(6, 0, lblZone);
		this.getCellFormatter().setHorizontalAlignment(6, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		final Label lblSpot = new Label("* " + GWTUtils.LOCALE_CONSTANTS.spot() + ":");
		this.setWidget(8, 0, lblSpot);
		this.getCellFormatter().setHorizontalAlignment(8, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		final Label lblTimeZone = new Label(GWTUtils.LOCALE_CONSTANTS.timeZone() + ":");
		this.setWidget(9, 0, lblTimeZone);
		this.getCellFormatter().setHorizontalAlignment(9, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		zoneBox = new ListBox();
		zoneBox.setWidth(NewSpotPanel.INPUTS_WIDTH);
		this.setWidget(6, 1, zoneBox);
		
		zoneTxt = new TextBox();
		zoneTxt.setMaxLength(50);
		zoneTxt.setWidth(NewSpotPanel.INPUTS_WIDTH);
		zoneTxt.setVisible(false);
		this.setWidget(7, 1, zoneTxt);
		
		createZoneBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.createZone(), "NewSpotDataPanel-CreateZone", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_120PX);
		createZoneBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
					zoneBox.setEnabled(false);
					zoneTxt.setVisible(true);
					chooseZoneBtn.setVisible(true);
					createZoneBtn.setVisible(false);
			}
		});
		this.setWidget(6, 2, createZoneBtn);
		this.getCellFormatter().setHorizontalAlignment(6, 2, HasHorizontalAlignment.ALIGN_LEFT);
		
		chooseZoneBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.chooseZone(), "NewSpotDataPanel-CreateZone", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_120PX);
		chooseZoneBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
					chooseZoneBtnOnClick();
			}
		});
		chooseZoneBtn.setVisible(false);
		this.setWidget(7, 2, chooseZoneBtn);
		this.getCellFormatter().setHorizontalAlignment(7, 2, HasHorizontalAlignment.ALIGN_LEFT);

		spotTxt = new TextBox();
		spotTxt.setMaxLength(50);
		this.setWidget(8, 1, spotTxt);
		spotTxt.setWidth(NewSpotPanel.INPUTS_WIDTH);
		
		timeZoneBox = new ListBox();
		timeZoneBox.setWidth(NewSpotPanel.INPUTS_WIDTH);
		this.setWidget(9, 1, timeZoneBox);
		
		Label lblSpotVisibility = new Label(GWTUtils.LOCALE_CONSTANTS.spotVisibility() + ":");
		this.setWidget(10, 0, lblSpotVisibility);
		this.getCellFormatter().setHorizontalAlignment(10, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		HorizontalPanel radioPanel = new HorizontalPanel();
		radioPanel.setSpacing(5);
		this.setWidget(10, 1, radioPanel);
		
		radioPrivateButton = new RadioButton("visibilityRadioGroup", GWTUtils.LOCALE_CONSTANTS.private_());
		radioPrivateButton.setValue(true);
		radioPanel.add(radioPrivateButton);
		
		radioPublicButton = new RadioButton("visibilityRadioGroup", GWTUtils.LOCALE_CONSTANTS.public_());
		radioPanel.add(radioPublicButton);
		
		Label lblLocalization = new Label(GWTUtils.LOCALE_CONSTANTS.geographicLocalization());
		lblLocalization.addStyleName("gwt-Label-Title");
		this.setWidget(11, 0, lblLocalization);
		this.getFlexCellFormatter().setColSpan(11, 0, 3);
		
		mapPanel = new MapPanel();
		this.setWidget(12, 0, mapPanel);
		this.getCellFormatter().setHorizontalAlignment(12, 0, HasHorizontalAlignment.ALIGN_CENTER);
		this.getFlexCellFormatter().setColSpan(12, 0, 3);
		
		Label lblTrainClassifier = new Label(GWTUtils.LOCALE_CONSTANTS.trainClassifier());
		lblTrainClassifier.addStyleName("gwt-Label-Title");
		this.setWidget(13, 0, lblTrainClassifier);
		this.getFlexCellFormatter().setColSpan(13, 0, 3);
		
		form = new FormPanel();
		this.setWidget(15, 0, form);
		this.getFlexCellFormatter().setColSpan(15, 0, 3);
		form.setAction(UPLOAD_ACTION_URL);
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
	    form.setMethod(FormPanel.METHOD_POST);
	    form.addSubmitHandler(new SubmitHandler() {
			@Override
			public void onSubmit(SubmitEvent event) {
			}
		});
	    form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) { 
				//TODO revisar estos carteles en ie
				String results = event.getResults();
				String sCorrelation = results.substring(results.indexOf("correlation=") + 12, results.indexOf("|", results.indexOf("correlation=")));
				String sMeanAbsoluteError = results.substring(results.indexOf("|", results.indexOf("correlation=")) + 19, results.indexOf("|", results.indexOf("meanAbsoluteError=")));
				String classifierName = results.substring(results.indexOf("|", results.indexOf("meanAbsoluteError=")) + 16);
				double dCorrelation = new Double(sCorrelation);
				String correlationExpresion = "";
				if (dCorrelation <= 0.2)
					correlationExpresion = GWTUtils.LOCALE_CONSTANTS.veryBad();
				else if (dCorrelation <= 0.4)
					correlationExpresion = GWTUtils.LOCALE_CONSTANTS.bad();
				else if (dCorrelation <= 0.6)
					correlationExpresion = GWTUtils.LOCALE_CONSTANTS.good();
				else if (dCorrelation <= 0.8)
					correlationExpresion = GWTUtils.LOCALE_CONSTANTS.veryGood();
				else
					correlationExpresion = GWTUtils.LOCALE_CONSTANTS.veryBad();
				
				new MessageBox(GWTUtils.LOCALE_CONSTANTS.close(), GWTUtils.LOCALE_MESSAGES.wekaTrainingResults(correlationExpresion), MessageBox.IconType.INFO);
			}
		});
	    
	    formTable = new FlexTable();
	    form.setWidget(formTable);
	    //File Upload
	    Label lblVisualObs = new Label(GWTUtils.LOCALE_CONSTANTS.visualObservations());
	    formTable.setWidget(0, 0, lblVisualObs);
	    formTable.getFlexCellFormatter().setColSpan(0, 0, 10);
	    upload = new FileUpload();
	    upload.setWidth("500px");
	    upload.setName("uploadFormElement");
	    formTable.setWidget(1,0,upload);
	    formTable.getFlexCellFormatter().setColSpan(1, 0, 10);
	    //Day Light time inputs
	    Label lblDayLightTime = new Label(GWTUtils.LOCALE_CONSTANTS.dayLightTime());
	    formTable.setWidget(3, 0, lblDayLightTime);
	    formTable.getFlexCellFormatter().setColSpan(3, 0, 10);
	    Label lblFrom = new Label(GWTUtils.LOCALE_CONSTANTS.from());
	    formTable.setWidget(4, 0, lblFrom);
	    formTable.getFlexCellFormatter().setWidth(4, 0, "60px");
	    txtHour = new TextBox();
	    txtHour.setName("hourFormElement");
	    txtHour.setText("06");
	    txtHour.setWidth(NewSpotPanel.TIME_INPUTS_WIDTH);
	    txtHour.setMaxLength(2);
	    formTable.setWidget(4, 1, txtHour);
	    formTable.getFlexCellFormatter().setWidth(4, 1, NewSpotPanel.TIME_INPUTS_WIDTH);
	    Label lblHour = new Label(GWTUtils.LOCALE_CONSTANTS.hour_abbr());
	    formTable.setWidget(4, 2, lblHour);
	    formTable.getFlexCellFormatter().setWidth(4, 2, NewSpotPanel.TIME_INPUTS_WIDTH);
	    txtMinutes = new TextBox();
	    txtMinutes.setName("minutesFormElement");
	    txtMinutes.setText("30");
	    txtMinutes.setWidth(NewSpotPanel.TIME_INPUTS_WIDTH);
	    txtMinutes.setMaxLength(2);
	    formTable.setWidget(4, 3, txtMinutes);
	    formTable.getFlexCellFormatter().setWidth(4, 3, NewSpotPanel.TIME_INPUTS_WIDTH);
	    Label lblMinutes = new Label(GWTUtils.LOCALE_CONSTANTS.minutes_abbr());
	    formTable.setWidget(4, 4, lblMinutes);
	    formTable.getFlexCellFormatter().setWidth(4, 4, NewSpotPanel.TIME_INPUTS_WIDTH);
	    Label lblTo = new Label(GWTUtils.LOCALE_CONSTANTS.to());
	    formTable.setWidget(4, 5, lblTo);
	    formTable.getFlexCellFormatter().setWidth(4, 5, "60px");
	    txtHour2 = new TextBox();
	    txtHour2.setName("hour2FormElement");
	    txtHour2.setText("19");
	    txtHour2.setWidth(NewSpotPanel.TIME_INPUTS_WIDTH);
	    txtHour2.setMaxLength(2);
	    formTable.setWidget(4, 6, txtHour2);
	    formTable.getFlexCellFormatter().setWidth(4, 6, NewSpotPanel.TIME_INPUTS_WIDTH);
	    Label lblHour2 = new Label(GWTUtils.LOCALE_CONSTANTS.hour_abbr());
	    formTable.setWidget(4, 7, lblHour2);
	    formTable.getFlexCellFormatter().setWidth(4, 7, NewSpotPanel.TIME_INPUTS_WIDTH);
	    txtMinutes2 = new TextBox();
	    txtMinutes2.setName("minutes2FormElement");
	    txtMinutes2.setWidth(NewSpotPanel.TIME_INPUTS_WIDTH);
	    txtMinutes2.setMaxLength(2);
	    txtMinutes2.setText("30");
	    formTable.setWidget(4, 8, txtMinutes2);
	    formTable.getFlexCellFormatter().setWidth(4, 8, NewSpotPanel.TIME_INPUTS_WIDTH);
	    Label lblMinutes2 = new Label(GWTUtils.LOCALE_CONSTANTS.minutes_abbr());
	    formTable.setWidget(4, 9, lblMinutes2);
	    formTable.getFlexCellFormatter().setWidth(4, 9, "100%");
		spotId = new Hidden();
	    spotId.setName("spotIdFormElement");
	    spotId.setID("spotIdFormElement");
	    spotId.setValue("0");
	    formTable.setWidget(5, 0, spotId);
	    formTable.getFlexCellFormatter().setColSpan(5, 0, 10);
	    latitudeGridPoint = new Hidden();
	    latitudeGridPoint.setName("latitudeGridPointFormElement");
	    latitudeGridPoint.setID("latitudeGridPointFormElement");
	    latitudeGridPoint.setValue("0");
	    formTable.setWidget(6, 0, latitudeGridPoint);
	    formTable.getFlexCellFormatter().setColSpan(6, 0, 10);
	    longitudeGridPoint = new Hidden();
	    longitudeGridPoint.setName("longitudeGridPointFormElement");
	    longitudeGridPoint.setID("longitudeGridPointFormElement");
	    longitudeGridPoint.setValue("0");
	    formTable.setWidget(7, 0, longitudeGridPoint);
	    formTable.getFlexCellFormatter().setColSpan(7, 0, 10);
		
		this.getColumnFormatter().setWidth(0, NewSpotPanel.TABLE_COL_0);
		this.getColumnFormatter().setWidth(1, NewSpotPanel.TABLE_COL_1);
		this.getColumnFormatter().setWidth(2, NewSpotPanel.TABLE_COL_2);
		
		//Save Button	
		final HTMLButtonGrayGrad saveBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.save(), "NewSpotDataPanel-Save", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_120PX);
		this.setWidget(16, 0, saveBtn);
		this.getFlexCellFormatter().setColSpan(16, 0, 3);
		this.getFlexCellFormatter().setHorizontalAlignment(16, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		
		saveBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				final Vector<String> messages = new Vector<String>();
				errorPanel.setVisible(false);
				successPanel.setVisible(false);
				int countryId = countryBox.getItemCount() == 0 ? 0 : new Integer(countryBox.getValue(countryBox.getSelectedIndex()));
				messages.addAll(validateForm());
				if (messages.isEmpty()){
					int zoneId = zoneBox.getItemCount() == 0 ? 0 : new Integer(zoneBox.getValue(zoneBox.getSelectedIndex()));
					if (NewSpotPanel.PANEL_MODE.equals("create"))
						SpotServices.Util.getInstance().addSpot(spotTxt.getText().trim(), mapPanel.getSpotLat(), mapPanel.getSpotLong(),
								mapPanel.getBuoyLat(), mapPanel.getBuoyLong(),  
								zoneId, countryId, zoneTxt.getText().trim(), radioPublicButton.getValue(), 
								timeZoneBox.getValue(timeZoneBox.getSelectedIndex()).trim(), new AsyncCallback<Integer>(){
							public void onSuccess(Integer result){
								String message = ClientI18NMessages.getInstance().getMessage("CHANGES_SAVED_SUCCESFULLY");
								if (!upload.getFilename().trim().equals("")) {
									spotId.setValue(result.toString());
									latitudeGridPoint.setValue(mapPanel.getBuoyLat().replace(",", "."));
									longitudeGridPoint.setValue(mapPanel.getBuoyLong().replace(",", "."));
									message += " " + GWTUtils.LOCALE_CONSTANTS.trainingClassifier();
									form.submit();
								}
								clearFields();
								successPanel.setMessage(message);
								successPanel.setVisible(true);
								Window.scrollTo(0, 0);
								//TODO hacer que se actualizen los combos observer al dar de alta una ola
				            }
							
							public void onFailure(Throwable caught){
				            	if (((NeuralitoException)caught).getErrorCode().equals(ErrorCode.USER_SESSION_EMPTY_OR_EXPIRED) && 
										Cookies.getCookie("surfForecaster-Username") != null) {
									GWTUtils.showSessionExpiredLoginBox();
								} else {
									messages.add(ClientI18NMessages.getInstance().getMessage((NeuralitoException)caught));
									errorPanel.setMessages(messages);
									errorPanel.setVisible(true);
								}
				            	Window.scrollTo(0, 0);
				            }
						});
					else
						saveEditedSpot(zoneId, countryId);
				}
				else{
					errorPanel.setMessages(messages);
					errorPanel.setVisible(true);
					Window.scrollTo(0, 0);
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
	}
	
	/**
	 * Use this constructor to display the spot data for edit purpose
	 * @param idSpot Integer
	 */
	public NewSpotPanel(SpotGwtDTO spot) {
		this();
		this.spot = spot;
		this.getWekaForecasters();
		NewSpotPanel.PANEL_MODE = "edit";
		lblTitle.setText(GWTUtils.LOCALE_CONSTANTS.edit() + " " + spot.getName());
		lblNewSpotDescription.setVisible(false);
		//set spot name
		spotTxt.setText(spot.getName());
		//set timezone
		for (int i = 0; i < timeZoneBox.getItemCount(); i++) {
			if (timeZoneBox.getValue(i) == spot.getTimeZone())
				timeZoneBox.setSelectedIndex(i);
				break;
		}
		//set public or private
		if (spot.isPublik()) {
			radioPublicButton.setValue(true);
			radioPrivateButton.setValue(false);
		} else {
			radioPrivateButton.setValue(true);
			radioPublicButton.setValue(false);
		}
		
		//set spot and location
		mapPanel.setSpotLocation(spot.getPoint());
		
		HorizontalPanel radioObsPanel = new HorizontalPanel();
	    radioObsPanel.setSpacing(5);
		formTable.setWidget(2, 0, radioObsPanel);
		formTable.getFlexCellFormatter().setColSpan(2, 0, 10);
		radioReplaceButton = new RadioButton("obsRadioGroup", GWTUtils.LOCALE_CONSTANTS.replaceObservations());
		radioReplaceButton.setValue(true);
		radioObsPanel.add(radioReplaceButton);
		radioAppendButton = new RadioButton("obsRadioGroup", GWTUtils.LOCALE_CONSTANTS.appendObservations());
		radioObsPanel.add(radioAppendButton);
		
		if (LocalizationUtils.getInstance().getCallsQueue().allFinished())
			update(LocalizationUtils.getInstance(), null);
	}
	
	private void getWekaForecasters() {
		ForecastServices.Util.getInstance().getWekaForecasters(this.spot.getId(), new AsyncCallback<List<WekaForecasterEvaluationGwtDTO>>(){
			public void onSuccess(List<WekaForecasterEvaluationGwtDTO> result){
				if (result.size() > 0) {
					WekaEvaluationResultsPanel wekaEvaluationResultsPanel = new WekaEvaluationResultsPanel(result);
					//put it in main table
					setWidget(14, 0, wekaEvaluationResultsPanel);
					getFlexCellFormatter().setColSpan(14, 0, 3);
					//TODO esto esta mal porque si tiene muchos forecasters no se de cual sacar los horarios de luz solar, ahora lo saco del unico que tiene, el primero
					//set forecaster lightday hours and gridpoint
					txtHour.setText(result.get(0).getTrainningOptions().get("utcSunriseHour"));
					txtHour2.setText(result.get(0).getTrainningOptions().get("utcSunsetHour"));
					txtMinutes.setText(result.get(0).getTrainningOptions().get("utcSunriseMinute"));
					txtMinutes2.setText(result.get(0).getTrainningOptions().get("utcSunsetMinute"));
					PointDTO point = new PointDTO(new Float(result.get(0).getTrainningOptions().get("latitudeGridPoint1")), new Float(result.get(0).getTrainningOptions().get("longitudeGridPoint1")));
					mapPanel.setGridPointLocation(point);
				}
            }
			
			public void onFailure(Throwable caught){
            	if (((NeuralitoException)caught).getErrorCode().equals(ErrorCode.USER_SESSION_EMPTY_OR_EXPIRED) && 
						Cookies.getCookie("surfForecaster-Username") != null) {
					GWTUtils.showSessionExpiredLoginBox();
				} else {
					errorPanel.setMessage(ClientI18NMessages.getInstance().getMessage((NeuralitoException)caught));
					errorPanel.setVisible(true);
				}
            	Window.scrollTo(0, 0);
            }
		});
		
	}

	/**
	 * Invoked when clicks chooseZoneBtn
	 */
	private void chooseZoneBtnOnClick() {
		zoneTxt.setText("");
		zoneBox.setEnabled(true);
		zoneTxt.setVisible(false);
		chooseZoneBtn.setVisible(false);
		createZoneBtn.setVisible(true);
	}
	
	private void clearFields() {
		this.spotTxt.setText("");
		this.chooseZoneBtnOnClick();
		this.radioPrivateButton.setValue(true);
		this.mapPanel.clearMap();
	}
	/**
	 * This method is called only in edit mode when update observer method is called to update the localization listboxes items after zones list is loaded
	 */
	private void setLocalizationListBoxesValues() {
		//set area value
		for (int i = 0; i < areaBox.getItemCount(); i++) {
			if (new Integer(areaBox.getValue(i)).equals(spot.getArea().getId())) {
				areaBox.setSelectedIndex(i);
				break;
			}
		}
		//set country value
		for (int i = 0; i < countryBox.getItemCount(); i++) {
			if (new Integer(countryBox.getValue(i)).equals(spot.getCountry().getId())){
				countryBox.setSelectedIndex(i);
				break;
			}
		}
		//set zone value
		for (int i = 0; i < zoneBox.getItemCount(); i++) {
			if (new Integer(zoneBox.getValue(i)).equals(spot.getZone().getId())) {
				zoneBox.setSelectedIndex(i);
				break;
			}
		}
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
		
		if (this.zoneTxt.isVisible() && !this.zoneTxt.getText().trim().equals("") && !this.zoneTxt.getText().trim().matches(GWTUtils.ALPHANUM_SPACES_DASHES_NOT_START_WITH_NUM))
			messages.add(GWTUtils.LOCALE_MESSAGES.ALPHANUM_SPACES_DASHES_NOT_START_WITH_NUM(GWTUtils.LOCALE_CONSTANTS.zone()));
		
		if (this.spotTxt.getText().trim().equals(""))
			messages.add(GWTUtils.LOCALE_CONSTANTS.MANDATORY_SPOT_NAME());
		
		if (!this.spotTxt.getText().trim().equals("") && !this.spotTxt.getText().trim().matches(GWTUtils.ALPHANUM_SPACES_DASHES_NOT_START_WITH_NUM))
			messages.add(GWTUtils.LOCALE_MESSAGES.ALPHANUM_SPACES_DASHES_NOT_START_WITH_NUM(GWTUtils.LOCALE_CONSTANTS.spot()));
		
		if (this.mapPanel.getSpotLat().trim().equals("") || this.mapPanel.getSpotLong().trim().equals(""))
			messages.add(GWTUtils.LOCALE_CONSTANTS.MANDATORY_SPOT_LAT_LONG());
		
		if (this.mapPanel.getBuoyLat().trim().equals("") || this.mapPanel.getBuoyLong().trim().equals(""))
			messages.add(GWTUtils.LOCALE_CONSTANTS.MANDATORY_BUOY_LAT_LONG());
		
		if (!this.upload.getFilename().equals("") && 
				(this.txtHour.getText().trim().equals("") || this.txtHour2.getText().trim().equals("") ||
				 this.txtMinutes.getText().trim().equals("") || this.txtMinutes2.getText().trim().equals("")))
			messages.add(GWTUtils.LOCALE_CONSTANTS.MANDATORY_DAYLIGHT_TIME());
		
		if (!this.upload.getFilename().equals("")) {
			if (!this.txtHour.getText().trim().equals("") && !this.isValidHour(this.txtHour.getText().trim()))
				messages.add(GWTUtils.LOCALE_CONSTANTS.INVALID_HOUR_VALUE());
			if (!this.txtMinutes.getText().trim().equals("") && !this.isValidMinutes(this.txtMinutes.getText().trim()))
				messages.add(GWTUtils.LOCALE_CONSTANTS.INVALID_MINUTES_VALUE());
			if (!this.txtHour2.getText().trim().equals("") && !this.isValidHour(this.txtHour2.getText().trim()))
				messages.add(GWTUtils.LOCALE_CONSTANTS.INVALID_HOUR_VALUE());
			if (!this.txtMinutes2.getText().trim().equals("") && !this.isValidMinutes(this.txtMinutes2.getText().trim()))
				messages.add(GWTUtils.LOCALE_CONSTANTS.INVALID_MINUTES_VALUE());
		}
			
		
		
		return messages;
	}
	
	private boolean isValidHour(String hour) {
		if (!hour.matches(GWTUtils.REGEX_DIGITS))
			return false;
		int nHour = new Integer(hour);
		if (nHour >= 0 && nHour <= 23)
			return true;
		return false;
	}

	private boolean isValidMinutes(String minutes) {
		if (!minutes.matches(GWTUtils.REGEX_DIGITS))
			return false;
		int nMinutes = new Integer(minutes);
		if (nMinutes >= 0 && nMinutes <= 59)
			return true;
		return false;
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
			if (this.areaBox.getItemCount() > 0) {
				if (NewSpotPanel.PANEL_MODE.equals("edit"))
					this.setCountryListItems(spot.getArea().getId());
				else
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
		countryBox.clear();
		zoneBox.clear();
		Iterator<CountryDTO> i = LocalizationUtils.getInstance().getCountries(areaId).iterator(); 
		while (i.hasNext()){
			CountryDTO country = i.next();
			this.countryBox.addItem(country.getNames().get(GWTUtils.getCurrentLocaleCode()), country.getId().toString());
		}
		if (this.countryBox.getItemCount() > 0) {
			if (NewSpotPanel.PANEL_MODE.equals("edit"))
				this.setZoneListItems(spot.getCountry().getId());
			else
				this.setZoneListItems(new Integer(this.countryBox.getValue(this.countryBox.getSelectedIndex())));
		}
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
				
				//set Localization boxes values in edit mode
				if (NewSpotPanel.PANEL_MODE.equals("edit"))
					setLocalizationListBoxesValues();
			}
				
			public void onFailure(Throwable caught) {
				//TODO do something when the getspots methos fails
			}
		});
	}
	
	private void saveEditedSpot(Integer zoneId, Integer countryId) {
		//TODO este getpoint del if en realidad tendria que ser el getGridPoint, pero aun no esta hecho, cambiarlo cuando este , agregar tb al if que si cambio
		//las horas de luz de dia tambien tiene que reentrenar
		
			SpotServices.Util.getInstance().editSpot(spot.getId(), spotTxt.getText().trim(), mapPanel.getSpotLat(), mapPanel.getSpotLong(),
					mapPanel.getBuoyLat(), mapPanel.getBuoyLong(), zoneId, countryId, zoneTxt.getText().trim(), radioPublicButton.getValue(), 
					timeZoneBox.getItemText(timeZoneBox.getSelectedIndex()).trim(), new AsyncCallback<Integer>(){
				public void onSuccess(Integer result){
					if (!upload.getFilename().trim().equals("") /*|| cambio de horas*/) {
						spotId.setValue(result.toString());
						form.submit();
						//This force a retrain
					} else if (spot.getPoint().getLatitude() != new Float(mapPanel.getBuoyLat()) || spot.getPoint().getLongitude() != new Float(mapPanel.getBuoyLong())) {
						//TODO call to a retrain service with the new grid coordinates
					}
					//clearFields();
					successPanel.setVisible(true);
					
	            }
	            public void onFailure(Throwable caught){
	            	if (((NeuralitoException)caught).getErrorCode().equals(ErrorCode.USER_SESSION_EMPTY_OR_EXPIRED) && 
							Cookies.getCookie("surfForecaster-Username") != null) {
						GWTUtils.showSessionExpiredLoginBox();
					} else {
						errorPanel.setMessage(ClientI18NMessages.getInstance().getMessage((NeuralitoException)caught));
						errorPanel.setVisible(true);
					}
	            }
			});
		
		
	}
}
