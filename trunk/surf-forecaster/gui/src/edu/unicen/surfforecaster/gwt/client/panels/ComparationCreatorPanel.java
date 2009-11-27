package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.gwt.client.SpotServices;
import edu.unicen.surfforecaster.gwt.client.SurfForecaster;
import edu.unicen.surfforecaster.gwt.client.UserServices;
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
	
	private static final String LISTBOX_WIDTH = "200px";
	private static final String LISTBOX_HEIGHT = "300px";
	private static final int MAX_SPOTS_TO_COMP = 5;
	
	//A hash with the current selectedSpotsBox items ids and zoneId of each one (filled when addItemsToSelectedSpotsList method is called)
	private Map<Integer, Integer> selectedSpots = new HashMap<Integer, Integer>();
	private Integer currentSelectedZone = null;
	private LinksLocalizationPanel localizationPanel;
	private DisclosurePanel myComparations;
	private FlexTable compDefTable;
	
	public ComparationCreatorPanel() {
		
		//Define comparations
		DisclosurePanel comparationDefinition = new DisclosurePanel("Seleccionar olas", true);
		this.setWidget(1, 0, comparationDefinition);
		{
			compDefTable = new FlexTable();
			comparationDefinition.setContent(compDefTable);
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
				addSpotBtn = new HTMLButtonGrayGrad(">>", "CreateComparationPanel-add", GWTUtils.BUTTON_GRAY_GRAD_SHORT);
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
				removeSpotBtn = new HTMLButtonGrayGrad("<<", "CreateComparationPanel-remove", GWTUtils.BUTTON_GRAY_GRAD_SHORT);
				compDefTable.setWidget(2, 0, removeSpotBtn);
				removeSpotBtn.addClickHandler(this);
				compDefTable.getFlexCellFormatter().setWidth(2, 0, removeSpotBtn.getWidth());
				compDefTable.getFlexCellFormatter().setHeight(2, 0, removeSpotBtn.getHeight());
				compDefTable.getFlexCellFormatter().setRowSpan(2, 0, 1);
			}
			{
				firstBtn = new HTMLButtonGrayGrad("First", "CreateComparationPanel-first", GWTUtils.BUTTON_GRAY_GRAD_SHORT);
				compDefTable.setWidget(1, 3, firstBtn);
				firstBtn.addClickHandler(this);
				compDefTable.getFlexCellFormatter().setWidth(1, 3, firstBtn.getWidth());
				compDefTable.getFlexCellFormatter().setHeight(1, 3, firstBtn.getHeight());
				compDefTable.getFlexCellFormatter().setRowSpan(1, 3, 1);
			}
			{
				upBtn = new HTMLButtonGrayGrad("Up", "CreateComparationPanel-up", GWTUtils.BUTTON_GRAY_GRAD_SHORT);
				compDefTable.setWidget(2, 1, upBtn);
				upBtn.addClickHandler(this);
				compDefTable.getFlexCellFormatter().setWidth(2, 1, upBtn.getWidth());
				compDefTable.getFlexCellFormatter().setHeight(2, 1, upBtn.getHeight());
				compDefTable.getFlexCellFormatter().setRowSpan(2, 1, 1);
			}
			{
				downBtn = new HTMLButtonGrayGrad("Down", "CreateComparationPanel-down", GWTUtils.BUTTON_GRAY_GRAD_SHORT);
				compDefTable.setWidget(3, 1, downBtn);
				downBtn.addClickHandler(this);
				compDefTable.getFlexCellFormatter().setWidth(3, 1, downBtn.getWidth());
				compDefTable.getFlexCellFormatter().setHeight(3, 1, downBtn.getHeight());
				compDefTable.getFlexCellFormatter().setRowSpan(3, 1, 1);
			}
			{
				lastBtn = new HTMLButtonGrayGrad("Last", "CreateComparationPanel-last", GWTUtils.BUTTON_GRAY_GRAD_SHORT);
				compDefTable.setWidget(4, 1, lastBtn);
				lastBtn.addClickHandler(this);
				compDefTable.getFlexCellFormatter().setWidth(4, 1, lastBtn.getWidth());
				compDefTable.getFlexCellFormatter().setHeight(4, 1, lastBtn.getHeight());
				compDefTable.getFlexCellFormatter().setRowSpan(4, 1, 1);
			}
			{
				compareBtn = new HTMLButtonGrayGrad("Compare", "CreateComparationPanel-Compare", GWTUtils.BUTTON_GRAY_GRAD_MEDIUM);
				compDefTable.setWidget(5, 2, compareBtn);
				compareBtn.addClickHandler(this);
			}
		}
		this.showAllowedPanels();
	}
	
	public void fillSpotsSelector() {
		final Integer zoneId = new Integer(localizationPanel.getZoneBoxDisplayValue());
		SpotServices.Util.getInstance().getSpots(zoneId, new AsyncCallback<List<SpotDTO>>(){
			public void onSuccess(List<SpotDTO> result) {
				fillSpotsListBox(result, zoneId);
			}
				
			public void onFailure(Throwable caught) {
				//TODO do something when the getspots methos fails
			}
		});
	}
	
	private void showAllowedPanels() {
		UserServices.Util.getInstance().hasAccessTo("addComparation", new AsyncCallback<Boolean>(){
			public void onSuccess(Boolean result) {
				if (result) {
					{
						saveBtn = new HTMLButtonGrayGrad("Save comparation", "CreateComparationPanel-Save", GWTUtils.BUTTON_GRAY_GRAD_MEDIUM);
						compDefTable.setWidget(5, 3, saveBtn);	
					}
					showMyComparationsPanel();
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
	
	private void showMyComparationsPanel() {
		//My comparations
		myComparations = new DisclosurePanel("Mis comparaciones", true);
		this.setWidget(0, 0, myComparations);
		{
			FlexTable myCompsTable = new FlexTable();
			myComparations.setContent(myCompsTable);
			{
				myCompsBox = new ListBox();
				myCompsTable.setWidget(0, 0, myCompsBox);
			}
		}
		
		//Set save button handler
		saveBtn.addClickHandler(this);
	}

	@Override
	public Widget getBasePanel() {
		return this.baseParentPanel;
	}

	@Override
	public void setBasePanel(Widget basePanel) {
		this.baseParentPanel = basePanel;
	}
	
	public void fillSpotsListBox(List<SpotDTO> spots, Integer zoneId) {
		spotBox.clear();
		this.currentSelectedZone = zoneId;
		if (spots.size() > 0) {
			Set<Integer> selectedSpotsIds = (Set<Integer>)selectedSpots.keySet();
			Iterator<SpotDTO> i = spots.iterator(); 
			while (i.hasNext()){
				SpotDTO spot = i.next();
				if (!selectedSpotsIds.contains(spot.getId()))
					spotBox.addItem(spot.getName(), spot.getId().toString());
			}
		}
	}

	@Override
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
		
	}
	
	private void makeComparation() {
		List<Integer> selectedSpots = new ArrayList<Integer>();
		List<String> selectedSpotsNames = new ArrayList<String>();
		
		for (int i = 0; i < selectedSpotsBox.getItemCount(); i++) {
			selectedSpots.add(new Integer(selectedSpotsBox.getValue(i)));
		}
		
		if (selectedSpots.size() < 2) {
			// TODO msgbox tiene para este mensaje
			Window.alert("Debe seleccionar al menos dos spots a comparar");
		} else if (selectedSpots.size() > ComparationCreatorPanel.MAX_SPOTS_TO_COMP) {
			// TODO msgbox tiene para este mensaje
			Window.alert("Debe seleccionar 5 spots como maximo");
		} else {
			for (int i = 0; i < selectedSpotsBox.getItemCount(); i++) {
				selectedSpotsNames.add(selectedSpotsBox.getItemText(i));
			}
			
			((SpotComparatorPanel)baseParentPanel).generateSpotsComparation(selectedSpots, selectedSpotsNames);
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
				System.out.println(selectedSpotZone);
				System.out.println(this.currentSelectedZone);
				
				if (selectedSpotZone != null && selectedSpotZone.intValue() == this.currentSelectedZone.intValue())
					spotBox.addItem(selectedSpotsBox.getItemText(i), selectedSpotsBox.getValue(i));
				selectedSpots.remove(new Integer(selectedSpotsBox.getValue(i)));
				selectedSpotsBox.removeItem(i);
				
			}
			//TODO mostrar un message box o algo que indique si que tiene que seleccionar algun alemente antes de apretras este boton (si no lo hizo) idem con demas
			//botones de este panel
		}
	}

}
