package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

import edu.unicen.surfforecaster.gwt.client.GWTUtils;

public class LogoPanel extends Composite {

	public LogoPanel() {
		SimplePanel simplePanel = new SimplePanel();
		simplePanel.setHeight("99");
		simplePanel.setWidth(GWTUtils.APLICATION_WIDTH);
		initWidget(simplePanel);
		
		Image image = new Image("images/logo2.PNG");
		simplePanel.setWidget(image);
		
	}

}
