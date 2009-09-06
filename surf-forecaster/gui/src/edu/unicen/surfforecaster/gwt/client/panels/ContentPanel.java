package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.SimplePanel;

public class ContentPanel extends SimplePanel {
	
	private static ContentPanel instance = null;
	private final String PANEL_HEIGHT = "800";
	
	
	public static ContentPanel getInstance() {
        if (instance == null) {
            instance = new ContentPanel();
        }
        return instance;
    }

	public ContentPanel() {
		this.showMainVerticalPanel();
		this.setHeight(PANEL_HEIGHT);
	}
	
	public void showRegisterUserPanel(){
		RegisterUserPanel registerUserPanel = new RegisterUserPanel();	
		setWidget(registerUserPanel);
	}
	
	public void showMainVerticalPanel(){
		MainVerticalPanel mainVerticalPanel = new MainVerticalPanel();
		setWidget(mainVerticalPanel);
	}

}
