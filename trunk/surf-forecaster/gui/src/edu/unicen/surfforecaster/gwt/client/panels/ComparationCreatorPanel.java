package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.gwt.client.SpotServices;
import edu.unicen.surfforecaster.gwt.client.SurfForecaster;
import edu.unicen.surfforecaster.gwt.client.UserServices;
import edu.unicen.surfforecaster.gwt.client.dto.ComparationGwtDTO;
import edu.unicen.surfforecaster.gwt.client.dto.SpotGwtDTO;
import edu.unicen.surfforecaster.gwt.client.utils.ClientI18NMessages;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.utils.SessionData;
import edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad;

public class ComparationCreatorPanel extends FlexTable implements ISurfForecasterBasePanel, ClickHandler {
	
	private ListBox spotBox = null;
	private ListBox selectedSpotsBox = null;
	private ListBox myCompsBox = null;
	private HTMLButtonGrayGrad compareBtn = null;
	private HTMLButtonGrayGrad saveBtn = null;
	private Widget baseParentPanel = null;
	private HTMLButtonGrayGrad addSpotBtn;
	private HTMLButtonGrayGrad removeSpotBtn;
	private HTMLButtonGrayGrad firstBtn;
	private HTMLButtonGrayGrad upBtn;
	private HTMLButtonGrayGrad downBtn;
	private HTMLButtonGrayGrad lastBtn;
	private LinksLocalizationPanel localizationPanel;
	private DisclosurePanel myComparations;
	private FlexTable compDefTable;
	private Label lblComparationDescription;
	private HTMLButtonGrayGrad cancelSaveCompBtn;
	private HTMLButtonGrayGrad saveCompBtn;
	private FlexTable savePanel;
	private TextBox txtCompName;
	private TextArea txtCompDescription;
	private MessagePanel errorPanel;
	private MessagePanel successPanel;
	
	private static final String LISTBOX_WIDTH = "200px";
	private static final String LISTBOX_HEIGHT = "300px";
	private static final int MAX_SPOTS_TO_COMP = 5;
	
	//A hash with the current selectedSpotsBox items ids and zoneId of each one (filled when addItemsToSelectedSpotsList method is called)
	private Map<Integer, Integer> selectedSpots = new HashMap<Integer, Integer>();
	private Integer currentSelectedZone = null;
	
	public ComparationCreatorPanel() {
		
		Label lblSectionTitle = new Label("Comparador de olas");
		lblSectionTitle.addStyleName("gwt-Label-SectionTitle");
		this.setWidget(0, 0, lblSectionTitle);
		
		errorPanel = new ErrorMsgPanel();
		errorPanel.setVisible(false);
		this.setWidget(1, 0, errorPanel);
		this.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		this.getFlexCellFormatter().setColSpan(1, 0, 3);
		
		Vector<String> message = new Vector<String>();
		message.add(ClientI18NMessages.getInstance().getMessage("CHANGES_SAVED_SUCCESFULLY"));
		successPanel = new SuccessMsgPanel(message);
		successPanel.setVisible(false);
		this.setWidget(2, 0, successPanel);
		this.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		this.getFlexCellFormatter().setColSpan(2, 0, 3);
		
		//Define comparations
		Label lblTitle = new Label("Olas a comparar");
		lblTitle.addStyleName("gwt-Label-Title");
		this.setWidget(4, 0, lblTitle);
		
		SimplePanel comparationDefinition = new SimplePanel();
		this.setWidget(5, 0, comparationDefinition);
		{
			compDefTable = new FlexTable();
			comparationDefinition.setWidget(compDefTable);
			{
				localizationPanel = new LinksLocalizationPanel(false, false);			
				localizationPanel.setBasePanel(this);
				compDefTable.setWidget(0, 0, localizationPanel);
				compDefTable.getFlexCellFormatter().setColSpan(0, 0, 4);
			}
			{
				this.spotBox = new ListBox(true);
				this.spotBox.setSize(ComparationCreatorPanel.LISTBOX_WIDTH, ComparationCreatorPanel.LISTBOX_HEIGHT);
				compDefTable.setWidget(1, 0, this.spotBox);
				compDefTable.getFlexCellFormatter().setRowSpan(1, 0, 4);
				compDefTable.getFlexCellFormatter().setWidth(1, 0, ComparationCreatorPanel.LISTBOX_WIDTH);
			}
			{
				addSpotBtn = new HTMLButtonGrayGrad(">>", "CreateComparationPanel-add", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_30PX);
				compDefTable.setWidget(1, 1, addSpotBtn);
				addSpotBtn.addClickHandler(this);
				compDefTable.getFlexCellFormatter().setWidth(1, 1, addSpotBtn.getWidth());
				compDefTable.getFlexCellFormatter().setHeight(1, 1, addSpotBtn.getHeight());
				compDefTable.getFlexCellFormatter().setRowSpan(1, 1, 1);
			}
			{
				this.selectedSpotsBox = new ListBox(true);
				this.selectedSpotsBox.setSize(ComparationCreatorPanel.LISTBOX_WIDTH, ComparationCreatorPanel.LISTBOX_HEIGHT);
				compDefTable.setWidget(1, 2, this.selectedSpotsBox);
				compDefTable.getFlexCellFormatter().setRowSpan(1, 2, 4);
				compDefTable.getFlexCellFormatter().setWidth(1, 2, ComparationCreatorPanel.LISTBOX_WIDTH);
			}
			{
				removeSpotBtn = new HTMLButtonGrayGrad("<<", "CreateComparationPanel-remove", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_30PX);
				compDefTable.setWidget(2, 0, removeSpotBtn);
				removeSpotBtn.addClickHandler(this);
				compDefTable.getFlexCellFormatter().setWidth(2, 0, removeSpotBtn.getWidth());
				compDefTable.getFlexCellFormatter().setHeight(2, 0, removeSpotBtn.getHeight());
				compDefTable.getFlexCellFormatter().setRowSpan(2, 0, 1);
			}
			{
				firstBtn = new HTMLButtonGrayGrad("First", "CreateComparationPanel-first", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_60PX);
				compDefTable.setWidget(1, 3, firstBtn);
				firstBtn.addClickHandler(this);
				compDefTable.getFlexCellFormatter().setWidth(1, 3, firstBtn.getWidth());
				compDefTable.getFlexCellFormatter().setHeight(1, 3, firstBtn.getHeight());
				compDefTable.getFlexCellFormatter().setRowSpan(1, 3, 1);
			}
			{
				upBtn = new HTMLButtonGrayGrad("Up", "CreateComparationPanel-up", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_60PX);
				compDefTable.setWidget(2, 1, upBtn);
				upBtn.addClickHandler(this);
				compDefTable.getFlexCellFormatter().setWidth(2, 1, upBtn.getWidth());
				compDefTable.getFlexCellFormatter().setHeight(2, 1, upBtn.getHeight());
				compDefTable.getFlexCellFormatter().setRowSpan(2, 1, 1);
			}
			{
				downBtn = new HTMLButtonGrayGrad("Down", "CreateComparationPanel-down", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_60PX);
				compDefTable.setWidget(3, 1, downBtn);
				downBtn.addClickHandler(this);
				compDefTable.getFlexCellFormatter().setWidth(3, 1, downBtn.getWidth());
				compDefTable.getFlexCellFormatter().setHeight(3, 1, downBtn.getHeight());
				compDefTable.getFlexCellFormatter().setRowSpan(3, 1, 1);
			}
			{
				lastBtn = new HTMLButtonGrayGrad("Last", "CreateComparationPanel-last", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_60PX);
				compDefTable.setWidget(4, 1, lastBtn);
				lastBtn.addClickHandler(this);
				compDefTable.getFlexCellFormatter().setWidth(4, 1, lastBtn.getWidth());
				compDefTable.getFlexCellFormatter().setHeight(4, 1, lastBtn.getHeight());
				compDefTable.getFlexCellFormatter().setRowSpan(4, 1, 1);
			}
			{
				compareBtn = new HTMLButtonGrayGrad("Compare", "CreateComparationPanel-Compare", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX);
				compDefTable.setWidget(5, 2, compareBtn);
				compareBtn.addClickHandler(this);
			}
		}
		this.showAllowedPanels();
	}
	
	@Override
	public Widget getBasePanel() {
		return this.baseParentPanel;
	}

	@Override
	public void setBasePanel(Widget basePanel) {
		this.baseParentPanel = basePanel;
	}
	
	/**
	 * Click event for all components of this panel
	 */
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if (sender == compareBtn)
			this.makeComparation();
		else if (sender == addSpotBtn) 
			this.addItemsToSelectedSpotsList();
		else if (sender == removeSpotBtn) 
			this.removeSelectedSpot();
		else if (sender == firstBtn) 
			this.moveSpotToTop();
		else if (sender == lastBtn) 
			this.moveSpotToBottom();
		else if (sender == upBtn) 
			this.moveSpotUp();
		else if (sender == downBtn) 
			this.moveSpotDown();
		else if (sender == saveBtn) 
			this.showSavePanel();
		else if (sender == saveCompBtn) 
			this.saveComparation();
		else if (sender == cancelSaveCompBtn) 
			this.setSavePanelVisible(false);
	}

	/**
	 * Get the spots for the selected zone for this user and
	 * Fill the spotsSelector once the spots being retrieved
	 */
	public void getSpotsAndFillSpotsSelector() {
		final Integer zoneId = new Integer(localizationPanel.getZoneBoxDisplayValue());
		SpotServices.Util.getInstance().getSpots(zoneId, new AsyncCallback<List<SpotGwtDTO>>(){
			public void onSuccess(List<SpotGwtDTO> result) {
				fillSpotsListBox(result, zoneId);
			}
				
			public void onFailure(Throwable caught) {
				System.out.println("ComparationCreatorPanel - getSpots failed for zone: " + zoneId);
				//TODO do something when the getspots methos fails
			}
		});
	}
	
	/**
	 * Fill the spotsSelector listBox
	 * @param spots
	 * @param zoneId
	 */
	public void fillSpotsListBox(List<SpotGwtDTO> spots, Integer zoneId) {
		spotBox.clear();
		this.currentSelectedZone = zoneId;
		if (spots.size() > 0) {
			Set<Integer> selectedSpotsIds = (Set<Integer>)selectedSpots.keySet();
			Iterator<SpotGwtDTO> i = spots.iterator(); 
			while (i.hasNext()){
				SpotGwtDTO spot = i.next();
				if (!selectedSpotsIds.contains(spot.getId()))
					spotBox.addItem(spot.getName(), spot.getId().toString());
			}
		}
	}
	
	private void moveSpotUp() {
		if (this.justOneSpotSelected(selectedSpotsBox)) {
			int index = selectedSpotsBox.getSelectedIndex();
			String item = selectedSpotsBox.getItemText(index);
			String value = selectedSpotsBox.getValue(index);
			if (index > 0) {
				selectedSpotsBox.insertItem(item, value, index - 1);
				selectedSpotsBox.removeItem(index + 1);
				selectedSpotsBox.setSelectedIndex(index - 1);
			}
		} else {
			// TODO msgbox tiene que seleccionar un solo spot para realizar esta accion
			Window.alert("Debe seleccionar un spot para realizar esta accion");
		}
	}

	private void moveSpotDown() {
		if (this.justOneSpotSelected(selectedSpotsBox)) {
			int index = selectedSpotsBox.getSelectedIndex();
			String item = selectedSpotsBox.getItemText(index);
			String value = selectedSpotsBox.getValue(index);
			if (index < selectedSpotsBox.getItemCount() - 1) {
				selectedSpotsBox.insertItem(item, value, index + 2);
				selectedSpotsBox.removeItem(index);
				selectedSpotsBox.setSelectedIndex(index + 1);
			}
		} else {
			// TODO msgbox tiene que seleccionar un solo spot para realizar esta accion
			Window.alert("Debe seleccionar un spot para realizar esta accion");
		}
	}

	private void moveSpotToBottom() {
		if (this.justOneSpotSelected(selectedSpotsBox)) {
			int index = selectedSpotsBox.getSelectedIndex();
			String item = selectedSpotsBox.getItemText(index);
			String value = selectedSpotsBox.getValue(index);
			selectedSpotsBox.removeItem(index);
			selectedSpotsBox.insertItem(item, value, selectedSpotsBox.getItemCount());
			selectedSpotsBox.setSelectedIndex(selectedSpotsBox.getItemCount() - 1);
		} else {
			// TODO msgbox tiene que seleccionar un solo spot para realizar esta accion
			Window.alert("Debe seleccionar un spot para realizar esta accion");
		}
	}

	private void moveSpotToTop() {
		if (this.justOneSpotSelected(selectedSpotsBox)) {
			int index = selectedSpotsBox.getSelectedIndex();
			String item = selectedSpotsBox.getItemText(index);
			String value = selectedSpotsBox.getValue(index);
			selectedSpotsBox.removeItem(index);
			selectedSpotsBox.insertItem(item, value, 0);
			selectedSpotsBox.setSelectedIndex(0);
		} else {
			// TODO msgbox tiene que seleccionar un solo spot para realizar esta accion
			Window.alert("Debe seleccionar un spot para realizar esta accion");
		}
	}

	private void addItemsToSelectedSpotsList() {
		
		ArrayList<Integer> spotBoxSelectedIndexes = new ArrayList<Integer>();
		//Add spots to selected list
		for (int i = 0; i < spotBox.getItemCount(); i++) {
			if (spotBox.isItemSelected(i)) {
				selectedSpotsBox.addItem(spotBox.getItemText(i), spotBox.getValue(i));
				spotBoxSelectedIndexes.add(i);
				selectedSpots.put(new Integer(spotBox.getValue(i)), this.currentSelectedZone );
			}
		}
		
		//remove from spot list (Inverse order to avoid problems with indexes once an item is deleted and it index is lower than the next item)
		Collections.sort(spotBoxSelectedIndexes);
		for (int i = spotBoxSelectedIndexes.size()-1; i >= 0; i--) {
			spotBox.removeItem(spotBoxSelectedIndexes.get(i));
		}
	}
	
	private void removeSelectedSpot() {
		for (int i = selectedSpotsBox.getItemCount() - 1 ; i >= 0; i--) {
			if (selectedSpotsBox.isItemSelected(i)) {
				Integer selectedSpotZone = selectedSpots.get(new Integer(selectedSpotsBox.getValue(i)));
				if (selectedSpotZone != null && selectedSpotZone.intValue() == this.currentSelectedZone.intValue())
					spotBox.addItem(selectedSpotsBox.getItemText(i), selectedSpotsBox.getValue(i));
				selectedSpots.remove(new Integer(selectedSpotsBox.getValue(i)));
				selectedSpotsBox.removeItem(i);
				
			}
			//TODO mostrar un message box o algo que indique si que tiene que seleccionar algun alemente antes de apretras este boton (si no lo hizo) idem con demas
			//botones de este panel
		}
	}

	/**
	 * Checks if a multiple select ListBox has just one item selected or more than one
	 * @return true if just one item of the list is selected, false in the other case
	 */
	private boolean justOneSpotSelected(ListBox multipleSelectList) {
		
		int firstSelected = multipleSelectList.getSelectedIndex();
		if (firstSelected == -1)
			return false;
		
		boolean result = false;
		for (int i = firstSelected; i < multipleSelectList.getItemCount(); i++) {
			if (multipleSelectList.isItemSelected(i) && result == false) //always true in first iteration
				result = true;
			else if (multipleSelectList.isItemSelected(i)){
				result = false;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Checks that the selected spots count is valid, it means that is between two and MAX_SPOTS_TO_COMP const value
	 * @return boolean
	 */
	private boolean validSelectedSpotsCount() {
		if (selectedSpotsBox.getItemCount() < 2 || selectedSpotsBox.getItemCount() > ComparationCreatorPanel.MAX_SPOTS_TO_COMP)
			return false;
		
		return true;
	}
	
	/**
	 * Checks there are between 2 and 5 spots selected and generates the comparation
	 */
	private void makeComparation() {
		errorPanel.setVisible(false);
		successPanel.setVisible(false);
		if (validSelectedSpotsCount()) {
			List<Integer> selectedSpots = new ArrayList<Integer>();
			List<String> selectedSpotsNames = new ArrayList<String>();
			for (int i = 0; i < selectedSpotsBox.getItemCount(); i++) {
				selectedSpots.add(new Integer(selectedSpotsBox.getValue(i)));
				selectedSpotsNames.add(selectedSpotsBox.getItemText(i));
			}
			((SpotComparatorPanel)baseParentPanel).generateSpotsComparation(selectedSpots, selectedSpotsNames);
		} else {
			errorPanel.setMessage("Debes elegin entre 2 y 5 spots para comparar");
			errorPanel.setVisible(true);
		}
		
		
		
	}
	
	/**
	 * Made a request to the server for the loggedin user comparations.
	 * If exist a user loggedin shows the comparation components in the view, with the comparations retrieved.
	 * 
	 */
	private void showAllowedPanels() {
		UserServices.Util.getInstance().getSpotsComparations(new AsyncCallback<List<ComparationGwtDTO>>(){
			public void onSuccess(List<ComparationGwtDTO> result) {
				if (result != null) {
					showMyComparationsPanel(result);
					createSavePanel();
					saveBtn = new HTMLButtonGrayGrad("Save...", "CreateComparationPanel-Save", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX);
					compDefTable.setWidget(5, 3, saveBtn);
					setSaveBtnClickHandler();
				}
				
			}

			public void onFailure(Throwable caught) {
				if (((NeuralitoException)caught).getErrorCode().equals(ErrorCode.USER_SESSION_EMPTY_OR_EXPIRED) && 
						Cookies.getCookie("surfForecaster-Username") != null) {
					GWTUtils.showSessionExpiredLoginBox();
				}
				SurfForecaster.getInstance().gotoHistoryToken();
			}
		});
	}
	
	private void setSaveBtnClickHandler() {
		//Set save button handler
		saveBtn.addClickHandler(this);
	}
	
	private void showMyComparationsPanel(List<ComparationGwtDTO> comparations) {
		//My comparations
		myComparations = new DisclosurePanel("Mis comparaciones", true);
		myComparations.setAnimationEnabled(true);
		this.setWidget(3, 0, myComparations);
		{
			FlexTable myCompsTable = new FlexTable();
			myComparations.setContent(myCompsTable);
			{
				myCompsBox = new ListBox();
				myCompsBox.setWidth("300px");
				myCompsTable.setWidget(0, 0, myCompsBox);
			}
			{
				lblComparationDescription = new Label("");
				myCompsTable.setWidget(1, 0, lblComparationDescription);
			}
		}
		
		this.fillCompsBox(comparations);
	}
	
	private void fillCompsBox(final List<ComparationGwtDTO> comparations) {
		myCompsBox.addItem("<Choose a comparation>", "-1");
		Iterator<ComparationGwtDTO> i = comparations.iterator();
		while (i.hasNext()) {
			ComparationGwtDTO comparationDTO = i.next();
			myCompsBox.addItem(comparationDTO.getName(), comparationDTO.getId().toString());
		}
		
		myCompsBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				lblComparationDescription.setText("");
				selectedSpotsBox.clear();
				selectedSpots.clear();
				for (int j = 0; j < comparations.size(); j++) {
					if (comparations.get(j).getId().intValue() == new Integer(myCompsBox.getValue(myCompsBox.getSelectedIndex())).intValue()) {
						lblComparationDescription.setText(comparations.get(j).getDescription());
						showComparationSpots(comparations.get(j).getSpots());
						break;
					}
				}
				fillSavePanel();
				setSavePanelVisible(false);
			}
		});
		SessionData.getInstance();
	}
	
	private void createSavePanel() {
		String txtWidth = "500px";
		String areaWidth = "500px";
		
		savePanel = new FlexTable();
		savePanel.setVisible(false);
		this.setWidget(7, 0, savePanel);
		{
			//Panel title
			Label lblTitle = new Label("Guardar comparacion");
			lblTitle.addStyleName("gwt-Label-Title");
			savePanel.setWidget(0, 0, lblTitle);
			savePanel.getFlexCellFormatter().setColSpan(0, 0, 3);
		}
		{
			Label lblCompName = new Label("* " + "Nombre de comparacion" + ": ");
			savePanel.setWidget(1, 0, lblCompName);
			savePanel.getFlexCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		}
		{
			txtCompName = new TextBox();
			txtCompName.setWidth(txtWidth);
			savePanel.setWidget(1, 1, txtCompName);
		}
		{
			saveCompBtn = new HTMLButtonGrayGrad("Save", "CreateComparationPanel-SaveComparation", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX);
			saveCompBtn.addClickHandler(this);
			savePanel.setWidget(1, 2, saveCompBtn);
		}
		{
			Label lblCompDescription = new Label("Descripcion" + ": ");
			savePanel.getFlexCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
			savePanel.getFlexCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_TOP);
			savePanel.setWidget(2, 0, lblCompDescription);
		}
		{
			txtCompDescription = new TextArea();
			txtCompDescription.setSize(areaWidth, "100px");
			savePanel.setWidget(2, 1, txtCompDescription);
		}
		{
			cancelSaveCompBtn = new HTMLButtonGrayGrad("Cancel", "CreateComparationPanel-CancelSaveComparation", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX);
			cancelSaveCompBtn.addClickHandler(this);
			savePanel.getFlexCellFormatter().setVerticalAlignment(2, 2, HasVerticalAlignment.ALIGN_TOP);
			savePanel.setWidget(2, 2, cancelSaveCompBtn);
		}
		this.fillSavePanel();
	}

	private void fillSavePanel() {
		if (!myCompsBox.getValue(myCompsBox.getSelectedIndex()).equals("-1")) {
			txtCompName.setText(myCompsBox.getItemText(myCompsBox.getSelectedIndex()));
			txtCompDescription.setText(lblComparationDescription.getText());
		} else {
			txtCompName.setText("");
			txtCompDescription.setText("");
		}
			
		
	}
	
	/**
	 * Sets the save panel visibility
	 * @param isVisible
	 */
	private void setSavePanelVisible(boolean isVisible) {
		if (isVisible) {
			saveBtn.setVisible(false);
			savePanel.setVisible(true);
		} else {
			saveBtn.setVisible(true);
			savePanel.setVisible(false);
		}
	}
	
	/**
	 * fill the selected spots listbox with the spots defined in the choosen comparation
	 */
	private void showComparationSpots(List<SpotGwtDTO> spots) {
		Iterator<SpotGwtDTO> i = spots.iterator();
		while (i.hasNext()) {
			SpotGwtDTO spotDTO = i.next();
			selectedSpotsBox.addItem(spotDTO.getName(), spotDTO.getId().toString());
			selectedSpots.put(spotDTO.getId(), spotDTO.getZone().getId());
		}
		//TODO ver si es necesario cachear los spots de cada zona para reducir llamadas al servidor , ver el resto del sistema por esto tambien. Ver si conviene
		//inhabilitar el listbox mientras se realiza la llamada, por seguridad
		this.getSpotsAndFillSpotsSelector();
	}
	
	/**
	 * Show savePanel
	 */
	private void showSavePanel() {
		errorPanel.setVisible(false);
		successPanel.setVisible(false);
		if (validSelectedSpotsCount()) {
			this.setSavePanelVisible(true);
		} else {
			errorPanel.setMessage("Debes elegin entre 2 y 5 spots para comparar");
			errorPanel.setVisible(true);
		}
			
	}
	
	private void saveComparation() {
		errorPanel.setVisible(false);
		successPanel.setVisible(false);
		boolean nameDuplicated = false;
		//check if comparation name already exists
		for (int i = 1; i < myCompsBox.getItemCount(); i++) {
			if (txtCompName.getText().trim().equals(myCompsBox.getItemText(i).trim())) {
				nameDuplicated = true;
				//TODO hacer esto en un msg box personalizado
				if (Window.confirm("Ya tenes una comparacion con este nombre. Deseaas sobreescribir?"))
					executeSaveComparation();
				break;
			}
		}
		if (!nameDuplicated)
			executeSaveComparation();
	}

	private void executeSaveComparation() {
		//generate spotsIds list
		final Vector<String> messages = new Vector<String>();
		errorPanel.setVisible(false);
		successPanel.setVisible(false);
		messages.addAll(validateForm());
		if (messages.isEmpty()){
			List<Integer> selectedSpots = new ArrayList<Integer>();
			for (int i = 0; i < selectedSpotsBox.getItemCount(); i++) {
				selectedSpots.add(new Integer(selectedSpotsBox.getValue(i)));
			}
			UserServices.Util.getInstance().saveComparation(txtCompName.getText().trim(), txtCompDescription.getText().trim(), selectedSpots, new AsyncCallback<Integer>(){
				public void onSuccess(Integer result) {
					if (result != null && result > 0) {
						successPanel.setVisible(true);
					} else {
						errorPanel.setMessage("Error guardando la comparacion, intentelo nuevamente.");
						errorPanel.setVisible(true);
					}
				}
	
				public void onFailure(Throwable caught) {
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
		} else {
			errorPanel.setMessages(messages);
			errorPanel.setVisible(true);
		}
	}

	private Vector<String> validateForm() {
		Vector<String> messages = new Vector<String>();
		
		if (!validSelectedSpotsCount())
			messages.add("Debe elegir entre 2 y 5 spots");
		
		//TODO validar aca y entodos los campos de introduccion de texto que no meta caracteres raros (ver como hacer algo generico para todo el sistema)
		if (txtCompName.getText().trim().equals(""))
			//messages.add(GWTUtils.LOCALE_CONSTANTS.MANDATORY_AREA_VALUE());
			messages.add("El campo nombre es obligatorio");
		
		return messages;
	}

}
