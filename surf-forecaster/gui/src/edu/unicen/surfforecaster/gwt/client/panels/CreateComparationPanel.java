package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad;

public class CreateComparationPanel extends FlexTable implements ISurfForecasterBasePanel, ClickHandler {
	
	private ListBox spotBox = null;
	private ListBox selectedSpotsBox = null;
	private static final String LISTBOX_HEIGHT = "300px";
	private static final String LISTBOX_WIDTH = "200px";
	private HTMLButtonGrayGrad compareBtn = null;
	private Widget baseParentPanel = null;
	private HTMLButtonGrayGrad buttoncito;
	private HTMLButtonGrayGrad addSpotBtn;
	private HTMLButtonGrayGrad removeSpotBtn;
	private HTMLButtonGrayGrad firstBtn;
	private HTMLButtonGrayGrad upBtn;
	private HTMLButtonGrayGrad downBtn;
	private HTMLButtonGrayGrad lastBtn;
	
	public CreateComparationPanel() {
		this.setBorderWidth(2);
		{
			this.spotBox = new ListBox(true);
			this.spotBox.setSize(CreateComparationPanel.LISTBOX_WIDTH, CreateComparationPanel.LISTBOX_HEIGHT);
			this.setWidget(0, 0, this.spotBox);
			this.getFlexCellFormatter().setRowSpan(0, 0, 4);
			this.getCellFormatter().setWidth(0, 0, CreateComparationPanel.LISTBOX_WIDTH);
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
			this.selectedSpotsBox.setSize(CreateComparationPanel.LISTBOX_WIDTH, CreateComparationPanel.LISTBOX_HEIGHT);
			this.setWidget(0, 2, this.selectedSpotsBox);
			this.getFlexCellFormatter().setRowSpan(0, 2, 4);
			this.getFlexCellFormatter().setWidth(0, 2, CreateComparationPanel.LISTBOX_WIDTH);
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
	
	public void fillSpotsListBox(List<SpotDTO> spots) {
		spotBox.clear();
		Iterator<SpotDTO> i = spots.iterator(); 
		while (i.hasNext()){
			SpotDTO spot = i.next();
			spotBox.addItem(spot.getName(), spot.getId().toString());
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if (sender == compareBtn)
			((SpotComparatorPanel)baseParentPanel).showShowComparationPanel();
		//else if (sender == addSpotBtn)
			
		
	}
	
	private void addToSelectedSpotsList() {
		
	}

}
