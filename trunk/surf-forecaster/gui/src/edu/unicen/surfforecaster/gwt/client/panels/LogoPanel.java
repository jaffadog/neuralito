package edu.unicen.surfforecaster.gwt.client.panels;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;


public class LogoPanel extends Composite {

	public LogoPanel() {
		SimplePanel simplePanel = new SimplePanel();
		//simplePanel.setHeight("105");
		simplePanel.setWidth(GWTUtils.APLICATION_WIDTH);
		initWidget(simplePanel);
		
		Image image = new Image(GWTUtils.IMAGE_LOGO);
		image.addStyleName("gwt-Image-Logo");
		image.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem(GWTUtils.DEFAULT_HISTORY_TOKEN);
				//The previos statement call the history change event to reload the view
			}
		});
		simplePanel.setWidget(image);
		
	}

}
