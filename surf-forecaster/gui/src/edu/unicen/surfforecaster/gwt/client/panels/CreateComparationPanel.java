package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;

import edu.unicen.surfforecaster.common.services.dto.SpotDTO;

public class CreateComparationPanel extends FlexTable {
	
	private ListBox spotBox = null;
	private ListBox selectedSpotsBox = null;
	private static final String LISTBOX_HEIGHT = "500px";
	private static final String LISTBOX_WIDTH = "200px";
	
	public CreateComparationPanel() {
		{
			this.spotBox = new ListBox(true);
			this.spotBox.setSize(CreateComparationPanel.LISTBOX_WIDTH, CreateComparationPanel.LISTBOX_HEIGHT);
			this.setWidget(1, 0, this.spotBox);
			//this.getFlexCellFormatter().setHeight(1, 0, CreateComparationPanel.LISTBOX_HEIGHT);
		}
		{
			this.selectedSpotsBox = new ListBox(true);
			this.selectedSpotsBox.setSize(CreateComparationPanel.LISTBOX_WIDTH, CreateComparationPanel.LISTBOX_HEIGHT);
			this.setWidget(1, 2, this.selectedSpotsBox);
			//this.getFlexCellFormatter().setHeight(1, 2, CreateComparationPanel.LISTBOX_HEIGHT);
		}
		{
			PushButton compareBtn = new PushButton("Comparar");
			
		}
	}
	
	public void fillSpotsListBox(List<SpotDTO> spots) {
		spotBox.clear();
		Iterator<SpotDTO> i = spots.iterator(); 
		while (i.hasNext()){
			SpotDTO spot = i.next();
			spotBox.addItem(spot.getName(), spot.getId().toString());
		}
	}
}
