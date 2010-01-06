package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad;

public class MessageBox extends DialogBox {
	
	private HorizontalPanel buttonsPanel = null;
	private static String ICON_WIDTH = "50px";
	private static String ICON_HEIGHT = "50px";
	private static int MESSAGEBOX_WIDTH = 500;

	public static enum IconType { GOOD, HELP, ERROR, INFO, WARNING; IconType(){}}
	
	public MessageBox(String message, IconType iconType) {
		super(false, true);
		
		setText("Surf - Forecaster");

		final FlexTable flexTable = new FlexTable();
		flexTable.setWidth("100%");
		setWidget(flexTable);

		final Image icon = this.getIcon(iconType);
		flexTable.setWidget(0, 0, icon);
		flexTable.getFlexCellFormatter().setWidth(0, 0, MessageBox.ICON_WIDTH);

		final Label messageLbl = new Label(message);
		flexTable.setWidget(0, 1, messageLbl);

		buttonsPanel = new HorizontalPanel();
		buttonsPanel.setSpacing(5);
		flexTable.setWidget(1, 0, buttonsPanel);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getFlexCellFormatter().setColSpan(1, 0, 2);

		//Show message
		this.center();
		this.checkForResize();
	}
	
	public MessageBox(String hideBtnLbl, String message, IconType iconType) {
		this(message, iconType);
		buttonsPanel.add(this.addBtn(hideBtnLbl));
	}
	
	private void checkForResize() {
		if (this.getOffsetWidth() > MessageBox.MESSAGEBOX_WIDTH) 
			this.setWidth(MessageBox.MESSAGEBOX_WIDTH + "px"); 
		
	}

	public HTMLButtonGrayGrad addBtn(String label){
		final HTMLButtonGrayGrad button = new HTMLButtonGrayGrad(label, "MessageBox-hide", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX);
		buttonsPanel.add(button);
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		return button;
	}
	
	public void addBtn(HTMLButtonGrayGrad button) {
		buttonsPanel.add(button);
	}
	
	private Image getIcon(IconType iconType) {
		Image icon = new Image();
		icon.setSize(MessageBox.ICON_WIDTH, MessageBox.ICON_HEIGHT);
		switch (iconType) {
		case WARNING:
			icon.setUrl(GWTUtils.IMAGE_WARNING_ICON);
			break;
		
		case INFO:
			icon.setUrl(GWTUtils.IMAGE_INFO_ICON);
			break;
		
		case ERROR:
			icon.setUrl(GWTUtils.IMAGE_ERROR_ICON);
			break;
		
		case GOOD:
			icon.setUrl(GWTUtils.IMAGE_GOOD_ICON);
			break;
		
		case HELP:
			icon.setUrl(GWTUtils.IMAGE_HELP_ICON);
			break;
			
		}
		return icon;
	}
	
	public HorizontalPanel getButtonsPanel() {
		return buttonsPanel;
	}
	
	/**
	 * Set the buttons panel
	 * @param buttonsPanel
	 */
	public void setButtonsPanel(HorizontalPanel buttonsPanel) {
		this.buttonsPanel = buttonsPanel;
	}
	

}
