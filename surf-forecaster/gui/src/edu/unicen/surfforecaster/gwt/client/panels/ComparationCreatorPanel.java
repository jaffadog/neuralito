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
import edu.unicen.surfforecaster.gwt.client.ComparationServices;
import edu.unicen.surfforecaster.gwt.client.SpotServices;
import edu.unicen.surfforecaster.gwt.client.SurfForecaster;
import edu.unicen.surfforecaster.gwt.client.UserServices;
import edu.unicen.surfforecaster.gwt.client.dto.ComparationGwtDTO;
import edu.unicen.surfforecaster.gwt.client.dto.SpotGwtDTO;
import edu.unicen.surfforecaster.gwt.client.utils.ClientI18NMessages;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
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
	private HTMLButtonGrayGrad deleteCompBtn;
	private FlexTable savePanel;
	private TextBox txtCompName;
	private TextArea txtCompDescription;
	private MessagePanel errorPanel;
	private MessagePanel successPanel;
	private Integer selectedCompId;
	
	private static final String LISTBOX_WIDTH = "200px";
	private static final String LISTBOX_HEIGHT = "286px"; //11 rows of the container flextable
	private static final String COMBOBOX_WIDTH = "300px";
	private static final String COMP_DEF_TABLE_CELL_HEIGHT = "26px";
	
	//A hash with the current selectedSpotsBox items ids and zoneId of each one (filled when addItemsToSelectedSpotsList method is called)
	private Map<Integer, Integer> selectedSpots = new HashMap<Integer, Integer>();
	private Integer currentSelectedZone = null;
	private boolean nameDuplicated;
	
	public ComparationCreatorPanel() {
		
		Label lblSectionTitle = new Label(GWTUtils.LOCALE_CONSTANTS.spotComparator());
		lblSectionTitle.addStyleName("gwt-Label-SectionTitle");
		this.setWidget(0, 0, lblSectionTitle);
		
		errorPanel = new ErrorMsgPanel();
		errorPanel.setVisible(false);
		this.setWidget(1, 0, errorPanel);
		this.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		Vector<String> message = new Vector<String>();
		message.add(ClientI18NMessages.getInstance().getMessage("CHANGES_SAVED_SUCCESFULLY"));
		successPanel = new SuccessMsgPanel(message);
		successPanel.setVisible(false);
		this.setWidget(2, 0, successPanel);
		this.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		Label lblSectionDescription = new Label(GWTUtils.LOCALE_CONSTANTS.compSectionDescription());
		this.setWidget(3, 0, lblSectionDescription);
		lblSectionDescription.addStyleName("gwt-Label-SectionDescription");
		
		//Define comparations
		Label lblTitle = new Label(GWTUtils.LOCALE_CONSTANTS.spotsToCompare());
		lblTitle.addStyleName("gwt-Label-Title");
		this.setWidget(5, 0, lblTitle);
		
		SimplePanel comparationDefinition = new SimplePanel();
		this.setWidget(6, 0, comparationDefinition);
		{
			compDefTable = new FlexTable();
			comparationDefinition.setWidget(compDefTable);
			{
				localizationPanel = new LinksLocalizationPanel(false, false);			
				localizationPanel.setBasePanel(this);
				compDefTable.setWidget(0, 0, localizationPanel);
				compDefTable.getFlexCellFormatter().setColSpan(0, 0, 4);
				compDefTable.getFlexCellFormatter().setHeight(0, 0, ComparationCreatorPanel.COMP_DEF_TABLE_CELL_HEIGHT);
			}
			{
				this.spotBox = new ListBox(true);
				this.spotBox.setSize(ComparationCreatorPanel.LISTBOX_WIDTH, ComparationCreatorPanel.LISTBOX_HEIGHT);
				compDefTable.setWidget(1, 0, this.spotBox);
				compDefTable.getFlexCellFormatter().setRowSpan(1, 0, 11);
				compDefTable.getFlexCellFormatter().setWidth(1, 0, ComparationCreatorPanel.LISTBOX_WIDTH);
			}
			{
				addSpotBtn = new HTMLButtonGrayGrad(">>", "CreateComparationPanel-add", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_30PX);
				compDefTable.setWidget(1, 1, addSpotBtn);
				addSpotBtn.addClickHandler(this);
				compDefTable.getFlexCellFormatter().setWidth(1, 1, addSpotBtn.getWidth());
				compDefTable.getFlexCellFormatter().setHeight(1, 1, ComparationCreatorPanel.COMP_DEF_TABLE_CELL_HEIGHT);
			}
			{
				this.selectedSpotsBox = new ListBox(true);
				this.selectedSpotsBox.setSize(ComparationCreatorPanel.LISTBOX_WIDTH, ComparationCreatorPanel.LISTBOX_HEIGHT);
				compDefTable.setWidget(1, 2, this.selectedSpotsBox);
				compDefTable.getFlexCellFormatter().setRowSpan(1, 2, 11);
				compDefTable.getFlexCellFormatter().setWidth(1, 2, ComparationCreatorPanel.LISTBOX_WIDTH);
			}
			{
				removeSpotBtn = new HTMLButtonGrayGrad("<<", "CreateComparationPanel-remove", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_30PX);
				compDefTable.setWidget(2, 0, removeSpotBtn);
				removeSpotBtn.addClickHandler(this);
				compDefTable.getFlexCellFormatter().setWidth(2, 0, removeSpotBtn.getWidth());
				compDefTable.getFlexCellFormatter().setHeight(2, 0, ComparationCreatorPanel.COMP_DEF_TABLE_CELL_HEIGHT);
			}
			{
				firstBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.first(), "CreateComparationPanel-first", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX, GWTUtils.LOCALE_CONSTANTS.saveTip());
				compDefTable.setWidget(1, 3, firstBtn);
				firstBtn.addClickHandler(this);
				compDefTable.getFlexCellFormatter().setWidth(1, 3, firstBtn.getWidth());
				compDefTable.getFlexCellFormatter().setHeight(1, 3, ComparationCreatorPanel.COMP_DEF_TABLE_CELL_HEIGHT);
			}
			{
				upBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.up(), "CreateComparationPanel-up", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX);
				compDefTable.setWidget(2, 1, upBtn);
				upBtn.addClickHandler(this);
				compDefTable.getFlexCellFormatter().setWidth(2, 1, upBtn.getWidth());
				compDefTable.getFlexCellFormatter().setHeight(2, 1, ComparationCreatorPanel.COMP_DEF_TABLE_CELL_HEIGHT);
			}
			{
				downBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.down(), "CreateComparationPanel-down", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX);
				compDefTable.setWidget(3, 1, downBtn);
				downBtn.addClickHandler(this);
				compDefTable.getFlexCellFormatter().setWidth(3, 1, downBtn.getWidth());
				compDefTable.getFlexCellFormatter().setHeight(3, 1, ComparationCreatorPanel.COMP_DEF_TABLE_CELL_HEIGHT);
			}
			{
				lastBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.last(), "CreateComparationPanel-last", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX);
				compDefTable.setWidget(4, 1, lastBtn);
				lastBtn.addClickHandler(this);
				compDefTable.getFlexCellFormatter().setWidth(4, 1, lastBtn.getWidth());
				compDefTable.getFlexCellFormatter().setHeight(4, 1, ComparationCreatorPanel.COMP_DEF_TABLE_CELL_HEIGHT);
			}
			{
				compareBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.compare(), "CreateComparationPanel-Compare", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX);
				compDefTable.setWidget(12, 2, compareBtn);
				compDefTable.getFlexCellFormatter().setHeight(12, 2, ComparationCreatorPanel.COMP_DEF_TABLE_CELL_HEIGHT);
				compDefTable.getFlexCellFormatter().setHorizontalAlignment(12, 2, HasHorizontalAlignment.ALIGN_RIGHT);
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
		else if (sender == cancelSaveCompBtn){
			errorPanel.setVisible(false);
			successPanel.setVisible(false);
			this.setSavePanelVisible(false);
		}
		else if (sender == deleteCompBtn) {
			DeleteCompConfirmMessageBox confirmBox = new DeleteCompConfirmMessageBox(GWTUtils.LOCALE_CONSTANTS.askForDeleteComp(), MessageBox.IconType.WARNING);
			confirmBox.setBasePanel(this);
		}
			
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
			new MessageBox(GWTUtils.LOCALE_CONSTANTS.close(), GWTUtils.LOCALE_CONSTANTS.mustSelectSpot(), MessageBox.IconType.INFO);
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
			new MessageBox(GWTUtils.LOCALE_CONSTANTS.close(), GWTUtils.LOCALE_CONSTANTS.mustSelectSpot(), MessageBox.IconType.INFO);
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
			new MessageBox(GWTUtils.LOCALE_CONSTANTS.close(), GWTUtils.LOCALE_CONSTANTS.mustSelectSpot(), MessageBox.IconType.INFO);
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
			new MessageBox(GWTUtils.LOCALE_CONSTANTS.close(), GWTUtils.LOCALE_CONSTANTS.mustSelectSpot(), MessageBox.IconType.INFO);
		}
	}

	private void addItemsToSelectedSpotsList() {
		if (spotBox.getSelectedIndex() == -1)
			new MessageBox(GWTUtils.LOCALE_CONSTANTS.close(), GWTUtils.LOCALE_CONSTANTS.mustSelectAtLeastOneSpotFromLeftList(), MessageBox.IconType.INFO);
		else {
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
	}
	
	private void removeSelectedSpot() {
		if (selectedSpotsBox.getSelectedIndex() == -1)
			new MessageBox(GWTUtils.LOCALE_CONSTANTS.close(), GWTUtils.LOCALE_CONSTANTS.mustSelectAtLeastOneSpotFromRightList(), MessageBox.IconType.INFO);
		else {
			for (int i = selectedSpotsBox.getItemCount() - 1 ; i >= 0; i--) {
				if (selectedSpotsBox.isItemSelected(i)) {
					Integer selectedSpotZone = selectedSpots.get(new Integer(selectedSpotsBox.getValue(i)));
					if (selectedSpotZone != null && selectedSpotZone.intValue() == this.currentSelectedZone.intValue())
						spotBox.addItem(selectedSpotsBox.getItemText(i), selectedSpotsBox.getValue(i));
					selectedSpots.remove(new Integer(selectedSpotsBox.getValue(i)));
					selectedSpotsBox.removeItem(i);
					
				}
			}
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
		if (selectedSpotsBox.getItemCount() < SpotComparatorPanel.MIN_SPOTS_TO_COMP || selectedSpotsBox.getItemCount() > SpotComparatorPanel.MAX_SPOTS_TO_COMP)
			return false;
		
		return true;
	}
	
	/**
	 * Checks there are between MIN_SPOTS_TO_COMP and MAX_SPOTS_TO_COMP spots selected and generates the comparation
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
			errorPanel.setMessage(GWTUtils.LOCALE_CONSTANTS.twoToFiveSpotsToMakeComparation());
			errorPanel.setVisible(true);
		}
		
		
		
	}
	
	private void getComparationsForUser() {
		ComparationServices.Util.getInstance().getComparationsForUserId(new AsyncCallback<List<ComparationGwtDTO>>(){
			public void onSuccess(List<ComparationGwtDTO> result) {
				if (result != null) {
					fillCompsBox(result);
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
	
	/**
	 * Made a request to the server for the loggedin user comparations.
	 * If exist a user loggedin shows the comparation components in the view, with the comparations retrieved.
	 * 
	 */
	private void showAllowedPanels() {
		UserServices.Util.getInstance().hasAccessTo("getComparations", new AsyncCallback<Boolean>(){
			public void onSuccess(Boolean result) {
				if (result) {
					showMyComparationsPanel();
					createSavePanel();
					saveBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.showSave(), "CreateComparationPanel-Save", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX, GWTUtils.LOCALE_CONSTANTS.saveTip());
					compDefTable.setWidget(12, 3, saveBtn);
					compDefTable.getFlexCellFormatter().setHeight(12, 3, ComparationCreatorPanel.COMP_DEF_TABLE_CELL_HEIGHT);
					setSaveBtnClickHandler();
					getComparationsForUser();
				}
				SurfForecaster.getInstance().gotoHistoryToken();
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
	
	private void showMyComparationsPanel() {
		//My comparations
		myComparations = new DisclosurePanel(GWTUtils.LOCALE_CONSTANTS.myComparations(), true);
		myComparations.setAnimationEnabled(true);
		this.setWidget(4, 0, myComparations);
		{
			FlexTable myCompsTable = new FlexTable();
			myComparations.setContent(myCompsTable);
			{
				myCompsBox = new ListBox();
				myCompsBox.addItem("<" + GWTUtils.LOCALE_CONSTANTS.chooseComparation() + ">", "-1");
				myCompsBox.setWidth(ComparationCreatorPanel.COMBOBOX_WIDTH);
				myCompsTable.setWidget(0, 0, myCompsBox);
				myCompsTable.getFlexCellFormatter().setWidth(0, 0, ComparationCreatorPanel.COMBOBOX_WIDTH);
			}
			{
				deleteCompBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.delete(), "CreateComparationPanel-DeleteComparation", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX);
				deleteCompBtn.setVisible(false);
				deleteCompBtn.addClickHandler(this);
				myCompsTable.setWidget(0, 1, deleteCompBtn);
				myCompsTable.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
				myCompsTable.getFlexCellFormatter().setWidth(0, 1, "100%");
			}
			{
				lblComparationDescription = new Label("");
				myCompsTable.setWidget(1, 0, lblComparationDescription);
				lblComparationDescription.addStyleName("gwt-Label-SectionDescription");
				myCompsTable.getFlexCellFormatter().setColSpan(1, 0, 2);
			}
		}
	}
	
	private void fillCompsBox(final List<ComparationGwtDTO> comparations) {
		myCompsBox.clear();
		myCompsBox.addItem("<" + GWTUtils.LOCALE_CONSTANTS.chooseComparation() + ">", "-1");
		Iterator<ComparationGwtDTO> i = comparations.iterator();
		while (i.hasNext()) {
			ComparationGwtDTO comparationDTO = i.next();
			myCompsBox.addItem(comparationDTO.getName(), comparationDTO.getId().toString());
		}
		//TODO cuando guardo una comparacion se tiene que agregar en el combobox, similar en el resto del sistema luego de altas y bajas
		myCompsBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				selectedCompId = new Integer(myCompsBox.getValue(myCompsBox.getSelectedIndex())).intValue();
				lblComparationDescription.setText("");
				selectedSpotsBox.clear();
				selectedSpots.clear();
				if (selectedCompId.intValue() == -1)
					deleteCompBtn.setVisible(false);
				else {
					deleteCompBtn.setVisible(true);
					for (int j = 0; j < comparations.size(); j++) {
						if (comparations.get(j).getId().intValue() == selectedCompId) {
							lblComparationDescription.setText(comparations.get(j).getDescription());
							showComparationSpots(comparations.get(j).getSpots());
							break;
						}
					}
				}
				fillSavePanel();
				setSavePanelVisible(false);
			}
		});
	}
	
	private void createSavePanel() {
		String txtWidth = "500px";
		String areaWidth = "500px";
		
		savePanel = new FlexTable();
		savePanel.setVisible(false);
		this.setWidget(7, 0, savePanel);
		{
			//Panel title
			Label lblTitle = new Label(GWTUtils.LOCALE_CONSTANTS.saveComparation());
			lblTitle.addStyleName("gwt-Label-Title");
			savePanel.setWidget(0, 0, lblTitle);
			savePanel.getFlexCellFormatter().setColSpan(0, 0, 3);
		}
		{
			Label lblCompName = new Label("* " + GWTUtils.LOCALE_CONSTANTS.comparationName() + ": ");
			savePanel.setWidget(1, 0, lblCompName);
			savePanel.getFlexCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		}
		{
			txtCompName = new TextBox();
			txtCompName.setWidth(txtWidth);
			txtCompName.setMaxLength(70);
			savePanel.setWidget(1, 1, txtCompName);
		}
		{
			saveCompBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.save(), "CreateComparationPanel-SaveComparation", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX);
			saveCompBtn.addClickHandler(this);
			savePanel.setWidget(1, 2, saveCompBtn);
		}
		{
			Label lblCompDescription = new Label(GWTUtils.LOCALE_CONSTANTS.description() + ": ");
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
			cancelSaveCompBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.cancel(), "CreateComparationPanel-CancelSaveComparation", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX);
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
			errorPanel.setMessage(GWTUtils.LOCALE_CONSTANTS.twoToFiveSpotsToSaveComparation());
			errorPanel.setVisible(true);
		}
			
	}
	
	private void saveComparation() {
		errorPanel.setVisible(false);
		successPanel.setVisible(false);
		nameDuplicated = false;
		//check if comparation name already exists
		for (int i = 1; i < myCompsBox.getItemCount(); i++) {
			if (txtCompName.getText().trim().equals(myCompsBox.getItemText(i).trim())) {
				nameDuplicated = true;
				SaveCompConfirmMessageBox confirmBox = new SaveCompConfirmMessageBox(GWTUtils.LOCALE_CONSTANTS.askForAnotherName(), MessageBox.IconType.WARNING);
				confirmBox.setBasePanel(this);
				break;
			}
		}
		if (!nameDuplicated)
			executeSaveComparation();
	}
	
	public void deleteComparation() {
		final Integer selectedCompId = new Integer(myCompsBox.getValue(myCompsBox.getSelectedIndex())).intValue();
		errorPanel.setVisible(false);
		successPanel.setVisible(false);
		ComparationServices.Util.getInstance().removeComparation(selectedCompId, new AsyncCallback<Boolean>(){
			public void onSuccess(Boolean result) {
				if (result) {
					//remove item from listbox
					for (int i = 0; i < myCompsBox.getItemCount(); i++) {
						if (selectedCompId.toString().equals(myCompsBox.getValue(i))) {
							myCompsBox.removeItem(i);
							break;
						}
					}
					lblComparationDescription.setText("");
					fillSavePanel();
					setSavePanelVisible(false);
					successPanel.setVisible(true);
				} else {
					errorPanel.setMessage(GWTUtils.LOCALE_CONSTANTS.ERROR_DELETING_COMPARATION());
					errorPanel.setVisible(true);
				}
			}

			public void onFailure(Throwable caught) {
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
	
	public void executeSaveComparation() {
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
			if (!nameDuplicated) {
				ComparationServices.Util.getInstance().addComparation(txtCompName.getText().trim(), txtCompDescription.getText().trim(), selectedSpots, new AsyncCallback<Integer>(){
					public void onSuccess(Integer result) {
						if (result != null && result > 0) {
							//refresh comparations list
							getComparationsForUser();
							successPanel.setVisible(true);
						} else {
							errorPanel.setMessage(GWTUtils.LOCALE_CONSTANTS.ERROR_SAVING_COMPARATION());
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
				ComparationServices.Util.getInstance().updateComparation(selectedCompId, txtCompDescription.getText().trim(), selectedSpots, new AsyncCallback<Integer>(){
					public void onSuccess(Integer result) {
						if (result != null && result > 0) {
							//refresh comparations list
							getComparationsForUser();
							successPanel.setVisible(true);
						} else {
							errorPanel.setMessage(GWTUtils.LOCALE_CONSTANTS.ERROR_SAVING_COMPARATION());
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
			}
		} else {
			errorPanel.setMessages(messages);
			errorPanel.setVisible(true);
		}
	}

	private Vector<String> validateForm() {
		Vector<String> messages = new Vector<String>();
		
		if (!validSelectedSpotsCount())
			messages.add(GWTUtils.LOCALE_CONSTANTS.twoToFiveSpotsToSaveComparation());
		
		if (txtCompName.getText().trim().equals(""))
			messages.add(GWTUtils.LOCALE_MESSAGES.MANDATORY_FIELD(GWTUtils.LOCALE_CONSTANTS.comparationName()));
		if (!txtCompName.getText().trim().equals("") && !txtCompName.getText().trim().matches(GWTUtils.ALPHANUM_SPACES_NOT_START_WITH_NUM))
			messages.add(GWTUtils.LOCALE_MESSAGES.ALPHANUM_SPACES_NOT_START_WITH_NUM(GWTUtils.LOCALE_CONSTANTS.comparationName()));
		return messages;
	}

}
