package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class TransparentPanel extends PopupPanel {
	
	private static TransparentPanel instance = null;
	
	public static TransparentPanel getInstance() {
        if (instance == null) {
            instance = new TransparentPanel(false);
        }
        return instance;
    }
	
	private TransparentPanel() {}

	private TransparentPanel(boolean autoHide) {
		super(autoHide);
		this.setSize(Window.getClientWidth() + "", Window.getClientHeight() + "");
		this.setStylePrimaryName("gwt-PopupPanel-Transparent");
		this.setPopupPosition(0, 0);
		this.add(new Label(""));
	}

}
