package edu.unicen.surfforecaster.gwt.client.panels;


import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;


public class LogoPanel extends Composite {

	public LogoPanel() {
		SimplePanel simplePanel = new SimplePanel();
		simplePanel.setHeight("105");
		simplePanel.setWidth(GWTUtils.APLICATION_WIDTH);
		initWidget(simplePanel);
		
		Image image = new Image(GWTUtils.IMAGE_LOGO);
		simplePanel.setWidget(image);
		
	}

}
