package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ComparationViewerPanel extends VerticalPanel implements ISurfForecasterBasePanel, ClickHandler {
	
	private Widget baseParentPanel = null;
	private PushButton backBtn = null;
	
	public ComparationViewerPanel() {
		{
			Label lala = new Label("lalaalal");
			this.add(lala);
		}
		{
			backBtn = new PushButton("Volver");
			backBtn.addClickHandler(this);
			this.add(backBtn);
		}
	}
	
	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if (sender == backBtn)
			((SpotComparatorPanel)baseParentPanel).showCreateComparationPanel();		
	}

	@Override
	public Widget getBasePanel() {
		return this.baseParentPanel;
	}

	@Override
	public void setBasePanel(Widget basePanel) {
		this.baseParentPanel = basePanel;
	}
}
