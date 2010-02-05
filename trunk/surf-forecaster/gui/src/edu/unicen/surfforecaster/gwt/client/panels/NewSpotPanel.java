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
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.ZoneDTO;
import edu.unicen.surfforecaster.gwt.client.ForecastServices;
import edu.unicen.surfforecaster.gwt.client.SpotServices;
import edu.unicen.surfforecaster.gwt.client.dto.AreaGwtDTO;
import edu.unicen.surfforecaster.gwt.client.dto.CountryGwtDTO;
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
public class NewSpotPanel extends FlexTable implements Observer, ClickHandler{

	private SpotGwtDTO spot = null;
	private String panelMode = "create";
	
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
	private LoadingPanel loadingAddingSpotPanel = null;
	private FlexTable formTable;
	private Label lblTitle;
	
	//static fields
	private static final String INPUTS_WIDTH = "300px";
	private static final String TIME_INPUTS_WIDTH = "25px";
	private static final String TABLE_COL_0 = "133px";
	private static final String TABLE_COL_1 = "310px";
	private static final String TABLE_COL_2 = "522px";
	private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL() + "FileUploadServices";
	
	//only for edit mode to know if day light time was edited
	private Integer hour = null;
	private Integer hour2 = null;
	private Integer minutes = null;
	private Integer minutes2 = null;
	private boolean hasWekaForecaster = false;

	private Label lblNewSpotDescription;
	private Hidden dayHoursChanged;
	private Hidden gridPointChanged;
	private Hyperlink lnkObsFormatSample;
	private HTMLButtonGrayGrad saveBtn;
	
	public NewSpotPanel() {
		this.panelMode = "create";
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
		
		loadingAddingSpotPanel = new LoadingPanel(GWTUtils.LOCALE_CONSTANTS.savingNewSpot());
		loadingAddingSpotPanel.setVisible(false);
		this.setWidget(3, 0, loadingAddingSpotPanel);
		this.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_CENTER);
		this.getFlexCellFormatter().setColSpan(3, 0, 3);
		
		lblNewSpotDescription = new Label(GWTUtils.LOCALE_CONSTANTS.registerNewSpotDesc());
		lblNewSpotDescription.addStyleName("gwt-Label-SectionDescription");
		this.setWidget(4, 0, lblNewSpotDescription);
		this.getFlexCellFormatter().setColSpan(4, 0, 3);

		final Label lblArea = new Label(GWTUtils.LOCALE_CONSTANTS.area() + ":");
		this.setWidget(5, 0, lblArea);
		this.getCellFormatter().setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		areaBox = new ListBox();
		areaBox.setWidth(NewSpotPanel.INPUTS_WIDTH);
		areaBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				setCountryListItems(new Integer(areaBox.getValue(areaBox.getSelectedIndex())));
			}
		});
		this.setWidget(5, 1, areaBox);

		final Label lblCountry = new Label(GWTUtils.LOCALE_CONSTANTS.country() + ":");
		this.setWidget(6, 0, lblCountry);
		this.getCellFormatter().setHorizontalAlignment(6, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		countryBox = new ListBox();
		countryBox.setWidth(NewSpotPanel.INPUTS_WIDTH);
		countryBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				setZoneListItems(new Integer(countryBox.getValue(countryBox.getSelectedIndex())));
			}
		});
		this.setWidget(6, 1, countryBox);
		
		final Label lblZone = new Label("* " + GWTUtils.LOCALE_CONSTANTS.zone() + ":");
		this.setWidget(7, 0, lblZone);
		this.getCellFormatter().setHorizontalAlignment(7, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		final Label lblSpot = new Label("* " + GWTUtils.LOCALE_CONSTANTS.spot() + ":");
		this.setWidget(9, 0, lblSpot);
		this.getCellFormatter().setHorizontalAlignment(9, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		final Label lblTimeZone = new Label(GWTUtils.LOCALE_CONSTANTS.timeZone() + ":");
		this.setWidget(10, 0, lblTimeZone);
		this.getCellFormatter().setHorizontalAlignment(10, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		zoneBox = new ListBox();
		zoneBox.setWidth(NewSpotPanel.INPUTS_WIDTH);
		this.setWidget(7, 1, zoneBox);
		
		zoneTxt = new TextBox();
		zoneTxt.setMaxLength(50);
		zoneTxt.setWidth(NewSpotPanel.INPUTS_WIDTH);
		zoneTxt.setVisible(false);
		this.setWidget(8, 1, zoneTxt);
		
		createZoneBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.createAZone(), "NewSpotDataPanel-CreateZone", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_120PX);
		createZoneBtn.addClickHandler(this);
		this.setWidget(7, 2, createZoneBtn);
		this.getCellFormatter().setHorizontalAlignment(7, 2, HasHorizontalAlignment.ALIGN_LEFT);
		
		chooseZoneBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.chooseAZone(), "NewSpotDataPanel-CreateZone", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_120PX);
		chooseZoneBtn.addClickHandler(this);
		chooseZoneBtn.setVisible(false);
		this.setWidget(8, 2, chooseZoneBtn);
		this.getCellFormatter().setHorizontalAlignment(8, 2, HasHorizontalAlignment.ALIGN_LEFT);

		spotTxt = new TextBox();
		spotTxt.setMaxLength(50);
		this.setWidget(9, 1, spotTxt);
		spotTxt.setWidth(NewSpotPanel.INPUTS_WIDTH);
		
		timeZoneBox = new ListBox();
		timeZoneBox.setWidth(NewSpotPanel.INPUTS_WIDTH);
		this.setWidget(10, 1, timeZoneBox);
		
		Label lblSpotVisibility = new Label(GWTUtils.LOCALE_CONSTANTS.spotVisibility() + ":");
		this.setWidget(11, 0, lblSpotVisibility);
		this.getCellFormatter().setHorizontalAlignment(11, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		HorizontalPanel radioPanel = new HorizontalPanel();
		radioPanel.setSpacing(5);
		this.setWidget(11, 1, radioPanel);
		
		radioPrivateButton = new RadioButton("visibilityRadioGroup", GWTUtils.LOCALE_CONSTANTS.private_());
		radioPrivateButton.setValue(true);
		radioPanel.add(radioPrivateButton);
		
		radioPublicButton = new RadioButton("visibilityRadioGroup", GWTUtils.LOCALE_CONSTANTS.public_());
		radioPanel.add(radioPublicButton);
		
		Label lblLocalization = new Label(GWTUtils.LOCALE_CONSTANTS.geographicLocalization());
		lblLocalization.addStyleName("gwt-Label-Title");
		this.setWidget(12, 0, lblLocalization);
		this.getFlexCellFormatter().setColSpan(12, 0, 3);
		
		mapPanel = new MapPanel();
		this.setWidget(13, 0, mapPanel);
		this.getCellFormatter().setHorizontalAlignment(13, 0, HasHorizontalAlignment.ALIGN_CENTER);
		this.getFlexCellFormatter().setColSpan(13, 0, 3);
		
		Label lblTrainClassifier = new Label(GWTUtils.LOCALE_CONSTANTS.trainClassifier());
		lblTrainClassifier.addStyleName("gwt-Label-Title");
		this.setWidget(14, 0, lblTrainClassifier);
		this.getFlexCellFormatter().setColSpan(14, 0, 3);
		
		form = new FormPanel();
		this.setWidget(16, 0, form);
		this.getFlexCellFormatter().setColSpan(16, 0, 3);
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
				String results = event.getResults();
				if (results.indexOf("</pre>") != -1) //only from browsers different than IE
					results = results.substring(0, results.indexOf("</pre>"));
				if (results.indexOf("NeuralitoException=") != -1) {
					new MessageBox(GWTUtils.LOCALE_CONSTANTS.close(), 
							ClientI18NMessages.getInstance().getMessage(results.substring(results.indexOf("NeuralitoException=") + 19)), MessageBox.IconType.ERROR);
					successPanel.setVisible(false);
					errorPanel.setVisible(false);
				} else {	
					errorPanel.setVisible(false);
					String sCorrelation = results.substring(results.indexOf("correlation=") + 12, results.indexOf("|", results.indexOf("correlation=")));
//					String sMeanAbsoluteError = results.substring(results.indexOf("|", results.indexOf("correlation=")) + 19, results.indexOf("|", results.indexOf("meanAbsoluteError=")));
//					String classifierName = results.substring(results.indexOf("|", results.indexOf("meanAbsoluteError=")) + 16);
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
						correlationExpresion = GWTUtils.LOCALE_CONSTANTS.excelent();
					
					new MessageBox(GWTUtils.LOCALE_CONSTANTS.close(), GWTUtils.LOCALE_MESSAGES.wekaTrainingResults(correlationExpresion), MessageBox.IconType.INFO);
					String message = ClientI18NMessages.getInstance().getMessage("CHANGES_SAVED_SUCCESFULLY");
					successPanel.setMessage(message);
					successPanel.setVisible(true);
				}
			}
		});
	    
	    formTable = new FlexTable();
	    form.setWidget(formTable);
	    //File Upload
	    Label lblVisualObs = new Label(GWTUtils.LOCALE_CONSTANTS.visualObservations());
	    formTable.setWidget(0, 0, lblVisualObs);
	    formTable.getFlexCellFormatter().setColSpan(0, 0, 7);
	    lnkObsFormatSample = new Hyperlink(GWTUtils.LOCALE_CONSTANTS.observationsFormatSample(), "");
	    lnkObsFormatSample.addClickHandler(this);
	    formTable.setWidget(0, 1, lnkObsFormatSample);
	    formTable.getFlexCellFormatter().setColSpan(0, 1, 3);
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
		
		saveBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.save(), "NewSpotDataPanel-Save", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_120PX);
		saveBtn.addClickHandler(this);
		this.setWidget(17, 0, saveBtn);
		this.getFlexCellFormatter().setColSpan(17, 0, 3);
		this.getFlexCellFormatter().setHorizontalAlignment(17, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		
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
		this.panelMode = "edit";
		lblTitle.setText(GWTUtils.LOCALE_CONSTANTS.edit() + " " + spot.getName());
		lblNewSpotDescription.setVisible(false);
		//set spot name
		spotTxt.setText(spot.getName());
		//set timezone
		for (int i = 0; i < timeZoneBox.getItemCount(); i++) {
			if (timeZoneBox.getValue(i).equals(spot.getTimeZone())) {
				timeZoneBox.setSelectedIndex(i);
				break;
			}
		}
		//set public or private
		if (spot.isPublik()) {
			radioPublicButton.setValue(true);
			radioPrivateButton.setValue(false);
		} else {
			radioPrivateButton.setValue(true);
			radioPublicButton.setValue(false);
		}
		
		//set spot location and gridpoint location. Gridpoint location must be first, due that spotlocation call async to get ww3 gridpoints and needs the gridpoint
		//location to know which color of buoy must choose
		mapPanel.setGridPointLocation(spot.getGridPoint());
		mapPanel.setSpotLocation(spot.getPoint());
		
		
		HorizontalPanel radioObsPanel = new HorizontalPanel();
	    radioObsPanel.setSpacing(5);
		formTable.setWidget(2, 0, radioObsPanel);
		formTable.getFlexCellFormatter().setColSpan(2, 0, 10);
		radioReplaceButton = new RadioButton("obsRadioGroupFormElement", GWTUtils.LOCALE_CONSTANTS.replaceObservations());
		radioReplaceButton.setValue(true);
		radioReplaceButton.setFormValue("replace");
		radioObsPanel.add(radioReplaceButton);
		radioAppendButton = new RadioButton("obsRadioGroupFormElement", GWTUtils.LOCALE_CONSTANTS.appendObservations());
		radioAppendButton.setFormValue("append");
		radioObsPanel.add(radioAppendButton);
		
		//Change hours hidden
		dayHoursChanged = new Hidden();
		dayHoursChanged.setName("dayHoursChangedFormElement");
		dayHoursChanged.setID("dayHoursChangedFormElement");
		dayHoursChanged.setValue("false");
	    formTable.setWidget(8, 0, dayHoursChanged);
	    formTable.getFlexCellFormatter().setColSpan(8, 0, 10);
	    
	    //Change gridPoint hidden
		gridPointChanged = new Hidden();
		gridPointChanged.setName("gridPointChangedFormElement");
		gridPointChanged.setID("gridPointChangedFormElement");
		gridPointChanged.setValue("false");
	    formTable.setWidget(9, 0, gridPointChanged);
	    formTable.getFlexCellFormatter().setColSpan(9, 0, 10);
		
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
					//this fields will be used to know if this values were edited
					hour = new Integer(txtHour.getText());
					hour2 = new Integer(txtHour2.getText());
					minutes = new Integer(txtMinutes.getText());
					minutes2 = new Integer(txtMinutes2.getText());
					
					hasWekaForecaster = true;
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
	 * Invoked when clicks createZoneBtn
	 */
	private void createZoneBtnOnClick() {
		zoneBox.setEnabled(false);
		zoneTxt.setVisible(true);
		chooseZoneBtn.setVisible(true);
		createZoneBtn.setVisible(false);
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
				if (this.panelMode.equals("edit"))
					this.setCountryListItems(spot.getArea().getId());
				else
					this.setCountryListItems(new Integer(this.areaBox.getValue(this.areaBox.getSelectedIndex())));
			}
		}
	}
	
	private void setAreaListItems() {
		Iterator<AreaGwtDTO> i = LocalizationUtils.getInstance().getAreas().iterator(); 
		while (i.hasNext()){
			AreaGwtDTO area = i.next();
			this.areaBox.addItem(area.getName(), area.getId().toString());		
		}
	}
	
	private void setCountryListItems(Integer areaId) {
		countryBox.clear();
		zoneBox.clear();
		Iterator<CountryGwtDTO> i = LocalizationUtils.getInstance().getCountries(areaId).iterator(); 
		while (i.hasNext()){
			CountryGwtDTO country = i.next();
			this.countryBox.addItem(country.getName(), country.getId().toString());
		}
		if (this.countryBox.getItemCount() > 0) {
			if (this.panelMode.equals("edit"))
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
				if (panelMode.equals("edit"))
					setLocalizationListBoxesValues();
			}
				
			public void onFailure(Throwable caught) {
				//TODO do something when the getspots methos fails
			}
		});
	}
	
	private void saveSpot() {
		Window.scrollTo(0, 0);
		final Vector<String> messages = new Vector<String>();
		errorPanel.setVisible(false);
		successPanel.setVisible(false);
		loadingAddingSpotPanel.setVisible(true);
		int countryId = countryBox.getItemCount() == 0 ? 0 : new Integer(countryBox.getValue(countryBox.getSelectedIndex()));
		messages.addAll(validateForm());
		if (messages.isEmpty()){
			int zoneId = zoneBox.getItemCount() == 0 ? 0 : new Integer(zoneBox.getValue(zoneBox.getSelectedIndex()));
			
			if (panelMode.equals("create"))
				SpotServices.Util.getInstance().addSpot(spotTxt.getText().trim(), mapPanel.getSpotLat(), mapPanel.getSpotLong(),
						mapPanel.getBuoyLat(), mapPanel.getBuoyLong(),  
						zoneId, countryId, zoneTxt.getText().trim(), radioPublicButton.getValue(), 
						timeZoneBox.getValue(timeZoneBox.getSelectedIndex()).trim(), new AsyncCallback<Integer>(){
					public void onSuccess(Integer result){
						loadingAddingSpotPanel.setVisible(false);
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
						//refresh localization lists on whole application
						LocalizationUtils.getInstance().checkCallsAndNotify();
						MySpotsPanel.getInstance().retrieveMySpots();
		            }
					
					public void onFailure(Throwable caught){
						loadingAddingSpotPanel.setVisible(false);
		            	if (((NeuralitoException)caught).getErrorCode().equals(ErrorCode.USER_SESSION_EMPTY_OR_EXPIRED) && 
								Cookies.getCookie("surfForecaster-Username") != null) {
							GWTUtils.showSessionExpiredLoginBox();
						} else {
							messages.add(ClientI18NMessages.getInstance().getMessage((NeuralitoException)caught));
							errorPanel.setMessages(messages);
							errorPanel.setVisible(true);
						}
		            }
				});
			else
				saveEditedSpot(zoneId, countryId);
		}
		else{
			loadingAddingSpotPanel.setVisible(false);
			errorPanel.setMessages(messages);
			errorPanel.setVisible(true);
		}
	}
	
	private void saveEditedSpot(Integer zoneId, Integer countryId) {
		//TODO este getpoint del if en realidad tendria que ser el getGridPoint, pero aun no esta hecho, cambiarlo cuando este , agregar tb al if que si cambio
		//las horas de luz de dia tambien tiene que reentrenar
		
		//check if gridPoint changed
		boolean changedBuoy = false;
		if (spot.getGridPoint().getLatitude() != new Float(mapPanel.getBuoyLat()).floatValue() || spot.getGridPoint().getLongitude() != new Float(mapPanel.getBuoyLong()).floatValue()) {
			changedBuoy = true;
			gridPointChanged.setValue("true");
		}
		final boolean changedGridPoint = changedBuoy;
		
		//check if dayLight hours changed
		boolean changedDayLight = false;
		if (hasWekaForecaster) {
			if (hour.intValue() != new Integer(txtHour.getText()).intValue() || hour2.intValue() != new Integer(txtHour2.getText()).intValue() || 
					minutes.intValue() != new Integer(txtMinutes.getText()).intValue() || minutes2.intValue() != new Integer(txtMinutes2.getText()).intValue()) {
				changedDayLight = true;
				dayHoursChanged.setValue("true");
			}
		}
		final boolean changedDayLightHours = changedDayLight;
		
		SpotServices.Util.getInstance().editSpot(spot.getId(), spotTxt.getText().trim(), mapPanel.getSpotLat(), mapPanel.getSpotLong(),
				mapPanel.getBuoyLat(), mapPanel.getBuoyLong(), zoneId, countryId, zoneTxt.getText().trim(), radioPublicButton.getValue(), 
				timeZoneBox.getValue(timeZoneBox.getSelectedIndex()).trim(), changedGridPoint, new AsyncCallback<Integer>(){
			public void onSuccess(Integer result){
				loadingAddingSpotPanel.setVisible(false);
				String message = ClientI18NMessages.getInstance().getMessage("CHANGES_SAVED_SUCCESFULLY");
				if ((hasWekaForecaster && changedGridPoint) || !upload.getFilename().trim().equals("") || changedDayLightHours) {
					//This force a retrain
					message += " " + GWTUtils.LOCALE_CONSTANTS.trainingClassifier();
					spotId.setValue(result.toString());
					latitudeGridPoint.setValue(mapPanel.getBuoyLat().replace(",", "."));
					longitudeGridPoint.setValue(mapPanel.getBuoyLong().replace(",", "."));
					form.submit();
					
				}
				successPanel.setMessage(message);
				successPanel.setVisible(true);
				MySpotsPanel.getInstance().retrieveMySpots();
            }
            public void onFailure(Throwable caught){
            	loadingAddingSpotPanel.setVisible(false);
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
	
	public void hideMessagePanels() {
		this.errorPanel.setVisible(false);
		this.successPanel.setVisible(false);
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if (sender == this.lnkObsFormatSample)
			this.showObsSamplePanel();
		else if (sender == this.saveBtn)
			this.saveSpot();
		else if (sender == this.chooseZoneBtn)
			this.chooseZoneBtnOnClick();
		else if (sender == this.createZoneBtn)
			this.createZoneBtnOnClick();
	}

	private void showObsSamplePanel() {
		VisualObservationSampleBox.getInstance().center();;
	}
}
