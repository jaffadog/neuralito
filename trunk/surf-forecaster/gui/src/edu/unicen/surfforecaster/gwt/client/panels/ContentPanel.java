package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.SimplePanel;

import edu.unicen.surfforecaster.gwt.client.GWTUtils;

public class ContentPanel extends SimplePanel {
	
	private static ContentPanel instance = null;
	private final String PANEL_HEIGHT = "800";
	
	RegisterUserPanel registerUserPanel = null;
	MainVerticalPanel mainVerticalPanel = null;
	
	
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
		if (registerUserPanel == null)
			registerUserPanel = new RegisterUserPanel();

		if (!GWTUtils.VALID_HISTORY_TOKENS.contains("registerNewUser"))
			GWTUtils.VALID_HISTORY_TOKENS.add("registerNewUser");

		setWidget(registerUserPanel);
	}
	
	public void showMainVerticalPanel(){
		if (mainVerticalPanel == null)
			mainVerticalPanel = new MainVerticalPanel();
		setWidget(mainVerticalPanel);
	}
	
	public void setPanelState(String historyToken){
		if (historyToken.equals("registerNewUser"))
			this.showRegisterUserPanel();
		else if (historyToken.lastIndexOf("Tab") != -1) {
			this.mainVerticalPanel.setPanelState(historyToken);
			this.showMainVerticalPanel();
		}	
	}

}
