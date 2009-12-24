package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class MessageBoxUI extends DialogBox {
	
	private HorizontalPanel buttonsPanel = null;
	
	public MessageBoxUI(String buttonName, String message) {
		super(false, true);
		
		setText("ACSC");

		final FlexTable flexTable = new FlexTable();
		setWidget(flexTable);
		setSize("320", "240");
		flexTable.setSize("100%", "100%");

		final Image icon = new Image();
		flexTable.setWidget(0, 0, icon);
		flexTable.getCellFormatter().setHeight(0, 0, "50px");
		flexTable.getCellFormatter().setWidth(0, 0, "50px");
		icon.setSize("50px", "50px");
		icon.setUrl("images/info.jpg");

		final Label messageLbl = new Label(message);
		flexTable.setWidget(0, 1, messageLbl);

		buttonsPanel = new HorizontalPanel();
		flexTable.setWidget(1, 0, buttonsPanel);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		buttonsPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getFlexCellFormatter().setColSpan(1, 0, 2);

		buttonsPanel.add(this.addBtn(buttonName));
		//Show message
		this.center();
	}
	
	public Button addBtn(String label){
		final Button acceptButton = new Button();
		buttonsPanel.add(acceptButton);
		acceptButton.setText(label);
		acceptButton.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				hide();
			}
		});
		return acceptButton;
	}
	
	

}
