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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad;

public class ComparationCreatorPanel extends FlexTable implements ISurfForecasterBasePanel, ClickHandler {
	
	private ListBox spotBox = null;
	private ListBox selectedSpotsBox = null;
	private static final String LISTBOX_HEIGHT = "300px";
	private static final String LISTBOX_WIDTH = "200px";
	private HTMLButtonGrayGrad compareBtn = null;
	private Widget baseParentPanel = null;
	private HTMLButtonGrayGrad addSpotBtn;
	private HTMLButtonGrayGrad removeSpotBtn;
	private HTMLButtonGrayGrad firstBtn;
	private HTMLButtonGrayGrad upBtn;
	private HTMLButtonGrayGrad downBtn;
	private HTMLButtonGrayGrad lastBtn;
	
	//A hash with the current selectedSpotsBox items ids and zoneId of each one (filled when addItemsToSelectedSpotsList method is called)
	private Map<Integer, Integer> selectedSpots = new HashMap<Integer, Integer>();
	private Integer currentSelectedZone = null;
	
	public ComparationCreatorPanel() {
		//this.setBorderWidth(2);
		{
			this.spotBox = new ListBox(true);
			this.spotBox.setSize(ComparationCreatorPanel.LISTBOX_WIDTH, ComparationCreatorPanel.LISTBOX_HEIGHT);
			this.setWidget(0, 0, this.spotBox);
			this.getFlexCellFormatter().setRowSpan(0, 0, 4);
			this.getCellFormatter().setWidth(0, 0, ComparationCreatorPanel.LISTBOX_WIDTH);
		}
		{
			addSpotBtn = new HTMLButtonGrayGrad(">>", "CreateComparationPanel-add", 60);
			this.setWidget(0, 1, addSpotBtn);
			addSpotBtn.addClickHandler(this);
			this.getFlexCellFormatter().setWidth(0, 1, addSpotBtn.getWidth());
			this.getFlexCellFormatter().setHeight(0, 1, addSpotBtn.getHeight());
			this.getFlexCellFormatter().setRowSpan(0, 1, 1);
		}
		{
			this.selectedSpotsBox = new ListBox(true);
			this.selectedSpotsBox.setSize(ComparationCreatorPanel.LISTBOX_WIDTH, ComparationCreatorPanel.LISTBOX_HEIGHT);
			this.setWidget(0, 2, this.selectedSpotsBox);
			this.getFlexCellFormatter().setRowSpan(0, 2, 4);
			this.getFlexCellFormatter().setWidth(0, 2, ComparationCreatorPanel.LISTBOX_WIDTH);
		}
		{
			removeSpotBtn = new HTMLButtonGrayGrad("<<", "CreateComparationPanel-remove", 60);
			this.setWidget(1, 0, removeSpotBtn);
			removeSpotBtn.addClickHandler(this);
			this.getFlexCellFormatter().setWidth(1, 0, removeSpotBtn.getWidth());
			this.getFlexCellFormatter().setHeight(1, 0, removeSpotBtn.getHeight());
			this.getFlexCellFormatter().setRowSpan(1, 0, 1);
		}
		{
			firstBtn = new HTMLButtonGrayGrad("First", "CreateComparationPanel-first", 60);
			this.setWidget(0, 3, firstBtn);
			firstBtn.addClickHandler(this);
			this.getFlexCellFormatter().setWidth(0, 3, firstBtn.getWidth());
			this.getFlexCellFormatter().setHeight(0, 3, firstBtn.getHeight());
			this.getFlexCellFormatter().setRowSpan(0, 3, 1);
		}
		{
			upBtn = new HTMLButtonGrayGrad("Up", "CreateComparationPanel-up", 60);
			this.setWidget(1, 1, upBtn);
			upBtn.addClickHandler(this);
			this.getFlexCellFormatter().setWidth(1, 1, upBtn.getWidth());
			this.getFlexCellFormatter().setHeight(1, 1, upBtn.getHeight());
			this.getFlexCellFormatter().setRowSpan(1, 1, 1);
		}
		{
			downBtn = new HTMLButtonGrayGrad("Down", "CreateComparationPanel-down", 60);
			this.setWidget(2, 1, downBtn);
			downBtn.addClickHandler(this);
			this.getFlexCellFormatter().setWidth(2, 1, downBtn.getWidth());
			this.getFlexCellFormatter().setHeight(2, 1, downBtn.getHeight());
			this.getFlexCellFormatter().setRowSpan(2, 1, 1);
		}
		{
			lastBtn = new HTMLButtonGrayGrad("Last", "CreateComparationPanel-last", 60);
			this.setWidget(3, 1, lastBtn);
			lastBtn.addClickHandler(this);
			this.getFlexCellFormatter().setWidth(3, 1, lastBtn.getWidth());
			this.getFlexCellFormatter().setHeight(3, 1, lastBtn.getHeight());
			this.getFlexCellFormatter().setRowSpan(3, 1, 1);
		}
		{
			compareBtn = new HTMLButtonGrayGrad("Compare", "CreateComparationPanel-Compare", GWTUtils.BUTTON_GRAY_GRAD_MEDIUM);
			this.setWidget(4, 3, compareBtn);
			compareBtn.addClickHandler(this);
		}
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
			((SpotComparatorPanel)baseParentPanel).showComparationViewerPanel();
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
