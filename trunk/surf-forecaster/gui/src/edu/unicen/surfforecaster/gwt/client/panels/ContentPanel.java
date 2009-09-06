package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.SimplePanel;

import edu.unicen.surfforecaster.gwt.client.SurfForecasterConstants;

public class ContentPanel extends SimplePanel {
	
	private SurfForecasterConstants localeConstants = null;
	private static ContentPanel instance = null;
	private final String PANEL_HEIGHT = "800";
	
	
	public static ContentPanel getInstance(SurfForecasterConstants localeConstants) {
        if (instance == null) {
            instance = new ContentPanel(localeConstants);
        }
        return instance;
    }
		
	public ContentPanel(){}
	
	/**
	 * @wbp.parser.constructor
	 */
	public ContentPanel(SurfForecasterConstants localeConstants) {
		this.localeConstants = localeConstants;
		this.showMainVerticalPanel();
		this.setHeight(PANEL_HEIGHT);
	}
	
	public void showRegisterUserPanel(){
		RegisterUserPanel registerUserPanel = new RegisterUserPanel(this.localeConstants);	
		setWidget(registerUserPanel);
	}
	
	public void showMainVerticalPanel(){
		MainVerticalPanel mainVerticalPanel = new MainVerticalPanel(this.localeConstants);
		setWidget(mainVerticalPanel);
	}

}
