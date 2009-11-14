package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;

public class ContentPanel extends SimplePanel {
	
	private static ContentPanel instance = null;
	RegisterUserPanel registerUserPanel = null;
	MainVerticalPanel mainVerticalPanel = null;
	
	
	public static ContentPanel getInstance() {
        if (instance == null) {
            instance = new ContentPanel();
        }
        return instance;
    }

	private ContentPanel() {
		this.showMainVerticalPanel();
		this.setHeight(Window.getClientHeight() + "");
	}
	
	public void showRegisterUserPanel(){
		if (registerUserPanel == null)
			registerUserPanel = new RegisterUserPanel();

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
