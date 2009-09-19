package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.HashMap;
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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.gwt.client.Area;
import edu.unicen.surfforecaster.gwt.client.Country;
import edu.unicen.surfforecaster.gwt.client.ForecastCommonServices;
import edu.unicen.surfforecaster.gwt.client.GWTUtils;

public class NewSpotDataPanel extends VerticalPanel {

	private Label errorlabel = null;
	private ListBox areaBox = null;
	private ListBox countryBox = null;
	private ListBox timeZoneBox = null;
	
	public NewSpotDataPanel() {
		setSpacing(10);
		this.setWidth(GWTUtils.APLICATION_WIDTH);
		
		final FlexTable flexTable = new FlexTable();
		Label lblTitle = new Label(GWTUtils.LOCALE_CONSTANTS.newSpotSectionTitle());
		lblTitle.addStyleName("gwt-Label-SectionTitle");
		this.add(lblTitle);
		
		Label lblNewSpotDescription = new Label("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's " +
				"standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has " +
				"survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s " +
				"with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including " +
				"versions of Lorem Ipsum.");
		lblNewSpotDescription.addStyleName("gwt-Label-RegisterSectionDescription");
		add(lblNewSpotDescription);
		
		this.add(flexTable);
		flexTable.setCellSpacing(5);
		//flexTable.setSize("450", "300");

		errorlabel = new Label(GWTUtils.LOCALE_CONSTANTS.starFields());
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
		flexTable.setWidget(4, 0, lblSpot);
		flexTable.getCellFormatter().setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		final Label lblTimeZone = new Label(GWTUtils.LOCALE_CONSTANTS.timeZone() + ":");
		flexTable.setWidget(5, 0, lblTimeZone);
		flexTable.getCellFormatter().setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		final TextBox zoneTxt = new TextBox();
		zoneTxt.setMaxLength(50);
		flexTable.setWidget(3, 2, zoneTxt);
		zoneTxt.setWidth("300");

		final TextBox spotTxt = new TextBox();
		spotTxt.setMaxLength(50);
		flexTable.setWidget(4, 2, spotTxt);
		spotTxt.setWidth("300");
		
		timeZoneBox = new ListBox();
		timeZoneBox.setWidth("300");
		flexTable.setWidget(5, 2, timeZoneBox);
		
		Label lblLocalization = new Label(GWTUtils.LOCALE_CONSTANTS.geographicLocalization());
		flexTable.setWidget(6, 0, lblLocalization);
		
		MapPanel mapPanel = new MapPanel();
		flexTable.setWidget(7, 2, mapPanel);
		
//		if (user == null){
//			flexTable.getCellFormatter().setVisible(5, 0, false);
//			flexTable.getCellFormatter().setVisible(5, 2, false);
//		}

		final HorizontalPanel btnsPanel = new HorizontalPanel();
		flexTable.setWidget(8, 0, btnsPanel);
		btnsPanel.setSpacing(5);
		flexTable.getCellFormatter().setHorizontalAlignment(8, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getFlexCellFormatter().setColSpan(8, 0, 3);

		final PushButton saveBtn = new PushButton();
		btnsPanel.add(saveBtn);
//		registerBtn.addClickListener(new ClickListener() {
//			public void onClick(final Widget sender) {
//				if (nameTxt.getText().trim() != "" && lastNameTxt.getText().trim() != "" && userTxt.getText().trim() != "" && passTxt.getText().trim() != ""){
//					int userPermissions = 2;
//					if (user != null && user.getIdUserType() == 1 && adminCheck.isChecked())
//						userPermissions = 1;
//					DBService.Util.getInstance().addNewUser(nameTxt.getText().trim(), lastNameTxt.getText().trim(), 
//							userTxt.getText().trim(), passTxt.getText().trim(), userPermissions, new AsyncCallback<Integer>(){
//						public void onSuccess(Integer result){
//							if (result == null){
//								//System.out.println("No se pudo salvar el usuario");
//								flexTable.getCellFormatter().setVisible(0, 0, false);
//							}
//							else
//								if (result > 0){
//									//System.out.println("El usuario fue grabado con exito.");
//									if (user != null && user.getIdUserType() == 1)
//										new MessageBoxUI("Aceptar", "Usuario registrado exitosamente!!!");
//									else
//										new MessageBoxUI("Aceptar", "Bienvenido al sistema!!!, usted ya ha sido registrado");
//									flexTable.getCellFormatter().setVisible(0, 0, false);
//									hide();
//								}
//								else{
//									//System.out.println("Ya existe un usuario con ese userName.");
//									errorlabel.setText("Ya existe ese nombre de usuario en el sistema, ingrese otro por favor.");
//									flexTable.getCellFormatter().setVisible(0, 0, true);
//								}
//			            }
//			            public void onFailure(Throwable caught){
//			            	
//			            	//System.out.println("fault adding a new user");
//			            	caught.printStackTrace();
//			            }
//						});
//				}
//				else{
//					errorlabel.setText("Los campos marcados con (*) son obligatorios.");
//					flexTable.getCellFormatter().setVisible(0, 0, true);
//				}
//			}
//		});
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
		flexTable.getFlexCellFormatter().setColSpan(6, 0, 3);
		
		this.setAreaListItems();
		this.setTimeZoneItems();
	}
	
	private void setTimeZoneItems() {
		HashMap<String, Object> timeZones = GWTUtils.getTimeZones();
		Object[] keys = (Object[]) timeZones.get("orderedKeys");
		
		for (int i = 0; i < keys.length; i++) 
			this.timeZoneBox.addItem((String)keys[i], (String)timeZones.get(keys[i]));
		
		
		
		
//		Set<Entry<String, Object>> set = timeZones.entrySet();
//		Iterator<Entry<String, Object>> i = set.iterator();
//		
//		while (i.hasNext()) {
//			Entry<String, Object> entry = i.next();
//			this.timeZoneBox.addItem(entry.getKey(), entry.getValue());
//		}
		
		
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
