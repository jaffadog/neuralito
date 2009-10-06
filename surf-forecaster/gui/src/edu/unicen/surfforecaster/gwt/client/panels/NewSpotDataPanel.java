package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;


import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.gwt.client.Area;
import edu.unicen.surfforecaster.gwt.client.Country;
import edu.unicen.surfforecaster.gwt.client.ForecastCommonServices;
import edu.unicen.surfforecaster.gwt.client.utils.ClientI18NMessages;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.utils.TimeZones;

public class NewSpotDataPanel extends LazyPanel {

	private Label errorlabel = null;
	private ListBox areaBox = null;
	private ListBox countryBox = null;
	private ListBox timeZoneBox = null;
	private ListBox zoneBox;
	private TextBox zoneTxt;
	private PushButton chooseZoneBtn;
	private PushButton createZoneBtn;
	private MapPanel mapPanel;
	private RadioButton radioPublicButton;
	
	public NewSpotDataPanel() {}
	
	@Override
	protected Widget createWidget() {
		
		VerticalPanel container = new VerticalPanel();
		
		container.setSpacing(10);
		container.setWidth(GWTUtils.APLICATION_WIDTH);
		
		final MessagePanel errorPanel = new ErrorMsgPanel();
		errorPanel.setVisible(false);
		container.add(errorPanel);
		
		Vector<String> message = new Vector<String>();
		message.add(ClientI18NMessages.getInstance().getMessage("CHANGES_SAVED_SUCCESFULLY"));
		final MessagePanel successPanel = new SuccessMsgPanel(message);
		successPanel.setVisible(false);
		container.add(successPanel);
		
		
		Label lblTitle = new Label(GWTUtils.LOCALE_CONSTANTS.newSpotSectionTitle());
		lblTitle.addStyleName("gwt-Label-SectionTitle");
		container.add(lblTitle);
		
		Label lblNewSpotDescription = new Label("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's " +
				"standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has " +
				"survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s " +
				"with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including " +
				"versions of Lorem Ipsum.");
		lblNewSpotDescription.addStyleName("gwt-Label-RegisterSectionDescription");
		container.add(lblNewSpotDescription);
		
		final FlexTable flexTable = new FlexTable();
		container.add(flexTable);
		flexTable.setCellSpacing(5);

		errorlabel = new Label(GWTUtils.LOCALE_CONSTANTS.MANDATORY_FIELDS());
		errorlabel.setStylePrimaryName("gwt-Label-error");
		flexTable.setWidget(0, 0, errorlabel);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 3);
		flexTable.getCellFormatter().setVisible(0, 0, false);

		final Label lblArea = new Label(GWTUtils.LOCALE_CONSTANTS.area() + ":");
		flexTable.setWidget(1, 0, lblArea);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		areaBox = new ListBox();
		areaBox.setWidth("300");
		areaBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				setCountryListItems(areaBox.getValue(areaBox.getSelectedIndex()));
			}
		});
		flexTable.setWidget(1, 2, areaBox);

		final Label lblCountry = new Label(GWTUtils.LOCALE_CONSTANTS.country() + ":");
		flexTable.setWidget(2, 0, lblCountry);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		countryBox = new ListBox();
		countryBox.setWidth("300");
		flexTable.setWidget(2, 2, countryBox);
		
		final Label lblZone = new Label("* " + GWTUtils.LOCALE_CONSTANTS.zone() + ":");
		flexTable.setWidget(3, 0, lblZone);

		final Label lblSpot = new Label("* " + GWTUtils.LOCALE_CONSTANTS.spot() + ":");
		flexTable.setWidget(5, 0, lblSpot);
		flexTable.getCellFormatter().setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		final Label lblTimeZone = new Label(GWTUtils.LOCALE_CONSTANTS.timeZone() + ":");
		flexTable.setWidget(6, 0, lblTimeZone);
		flexTable.getCellFormatter().setHorizontalAlignment(6, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		zoneBox = new ListBox();
		zoneBox.setWidth("300");
		flexTable.setWidget(3, 2, zoneBox);
		
		zoneTxt = new TextBox();
		zoneTxt.setMaxLength(50);
		zoneTxt.setWidth("300");
		zoneTxt.setVisible(false);
		flexTable.setWidget(4, 2, zoneTxt);
		
		createZoneBtn = new PushButton();
		createZoneBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
					zoneBox.setEnabled(false);
					zoneTxt.setVisible(true);
					chooseZoneBtn.setVisible(true);
					createZoneBtn.setVisible(false);
			}
		});
		createZoneBtn.setHeight(GWTUtils.PUSHBUTTON_HEIGHT);
		createZoneBtn.setText("Create a Zone");
		flexTable.setWidget(3, 3, createZoneBtn);
		
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
		chooseZoneBtn.setHeight(GWTUtils.PUSHBUTTON_HEIGHT);
		chooseZoneBtn.setText("Choose a Zone");
		chooseZoneBtn.setVisible(false);
		flexTable.setWidget(4, 3, chooseZoneBtn);

		final TextBox spotTxt = new TextBox();
		spotTxt.setMaxLength(50);
		flexTable.setWidget(5, 2, spotTxt);
		spotTxt.setWidth("300");
		
		timeZoneBox = new ListBox();
		timeZoneBox.setWidth("300");
		flexTable.setWidget(6, 2, timeZoneBox);
		
		Label lblSpotVisibility = new Label(GWTUtils.LOCALE_CONSTANTS.spotVisibility() + ":");
		flexTable.setWidget(7, 0, lblSpotVisibility);
		
		HorizontalPanel radioPanel = new HorizontalPanel();
		radioPanel.setSpacing(5);
		flexTable.setWidget(7, 2, radioPanel);
		
		RadioButton radioPrivateButton = new RadioButton("visibilityRadioGroup", GWTUtils.LOCALE_CONSTANTS.private_());
		radioPrivateButton.setValue(true);
		radioPanel.add(radioPrivateButton);
		
		
		radioPublicButton = new RadioButton("visibilityRadioGroup", GWTUtils.LOCALE_CONSTANTS.public_());
		radioPanel.add(radioPublicButton);
		
		Label lblLocalization = new Label(GWTUtils.LOCALE_CONSTANTS.geographicLocalization());
		flexTable.setWidget(8, 0, lblLocalization);
		
		mapPanel = new MapPanel();
		flexTable.setWidget(9, 2, mapPanel);

		final HorizontalPanel btnsPanel = new HorizontalPanel();
		flexTable.setWidget(10, 0, btnsPanel);
		btnsPanel.setSpacing(5);
		flexTable.getCellFormatter().setHorizontalAlignment(10, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getFlexCellFormatter().setColSpan(10, 0, 3);

		final PushButton saveBtn = new PushButton();
		btnsPanel.add(saveBtn);
		saveBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				final Vector<String> messages = new Vector<String>();
				errorPanel.setVisible(false);
				successPanel.setVisible(false);
				int countryId = zoneBox.getItemCount() == 0 ? 0 : new Integer(zoneBox.getValue(zoneBox.getSelectedIndex()));
				if (spotTxt.getText().trim() != ""){
					int zoneId = zoneBox.getItemCount() == 0 ? 0 : new Integer(zoneBox.getValue(zoneBox.getSelectedIndex()));
					ForecastCommonServices.Util.getInstance().addSpot(spotTxt.getText().trim(), mapPanel.getSpotLong(), mapPanel.getSpotLat(),
							zoneId, countryId, zoneTxt.getText().trim(), radioPublicButton.getValue(), 
							new AsyncCallback<Integer>(){
						public void onSuccess(Integer result){
							//clearFields();		
							successPanel.setVisible(true);
			            }
			            public void onFailure(Throwable caught){
			            	messages.add(ClientI18NMessages.getInstance().getMessage((NeuralitoException)caught));
							errorPanel.setMessages(messages);
							errorPanel.setVisible(true);
			            }
						});
				}
				else{
					messages.add(GWTUtils.LOCALE_CONSTANTS.MANDATORY_FIELDS());
					errorPanel.setMessages(messages);
					errorPanel.setVisible(true);
				}
			}
		});
		
		saveBtn.setHeight(GWTUtils.PUSHBUTTON_HEIGHT);
		saveBtn.setText(GWTUtils.LOCALE_CONSTANTS.save());

		final PushButton trainBtn = new PushButton();
		trainBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				((NewSpotPanel)getParent()).getNewSpotTrainPanel().setVisible(true);
				setVisible(false);
			}
		});
		trainBtn.setHeight(GWTUtils.PUSHBUTTON_HEIGHT);
		trainBtn.setText(GWTUtils.LOCALE_CONSTANTS.train());
		btnsPanel.add(trainBtn);
		
		final PushButton cancelBtn = new PushButton();
		cancelBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem(GWTUtils.DEFAULT_HISTORY_TOKEN);
				//The previous statement call the history change event to reload the view
			}
		});
		cancelBtn.setHeight(GWTUtils.PUSHBUTTON_HEIGHT);
		cancelBtn.setText(GWTUtils.LOCALE_CONSTANTS.goBack());
		btnsPanel.add(cancelBtn);

		flexTable.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getFlexCellFormatter().setColSpan(8, 0, 3);
		
		this.setAreaListItems();
		this.setTimeZoneItems();
		
		return container;
	}
	
	private void setTimeZoneItems() {
		TimeZones timeZones = TimeZones.getInstance();
		Object[] keys = (Object[]) timeZones.getTimeZones().get("orderedKeys");
		
		for (int i = 0; i < keys.length; i++) 
			this.timeZoneBox.addItem((String)keys[i], (String)timeZones.getTimeZones().get(keys[i]));
	}
	
	private void setAreaListItems(){
		ForecastCommonServices.Util.getInstance().getAreas(new AsyncCallback<Map<String, Vector>>(){
			public void onSuccess(Map<String, Vector> result) {
				if (result == null) {
				} else {
					Iterator i = null;
					if (result.containsKey("areas")){
						i = result.get("areas").iterator();
						while (i.hasNext()){
							Area area = (Area)i.next();
							areaBox.addItem(area.getName(), area.getId());
						}
					}
					
					if (result.containsKey("countries")){
						i = result.get("countries").iterator();
						while (i.hasNext()){
							Country country = (Country)i.next();
							countryBox.addItem(country.getName(), country.getId());
						}
					}
					
				}
			}
				
			public void onFailure(Throwable caught) {
				
			}
		});
	}
	
	private void setCountryListItems(String area){
		countryBox.clear();
		ForecastCommonServices.Util.getInstance().getCountries(area, new AsyncCallback<Map<String, Vector>>(){
			public void onSuccess(Map<String, Vector> result) {
				if (result == null) {
				} else {
					Iterator i = null;
					if (result.containsKey("countries")){
						i = result.get("countries").iterator();
						while (i.hasNext()){
							Country country = (Country)i.next();
							countryBox.addItem(country.getName(), country.getId());
						}
					}
					
				}
			}
				
			public void onFailure(Throwable caught) {
				
			}
		});
	}

}
