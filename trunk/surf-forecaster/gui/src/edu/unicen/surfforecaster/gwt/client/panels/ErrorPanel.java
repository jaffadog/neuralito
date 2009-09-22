package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Enumeration;
import java.util.Vector;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Image;

import edu.unicen.surfforecaster.gwt.client.GWTUtils;

public class ErrorPanel extends HorizontalPanel {
	
	public ErrorPanel(){}
	
	/**
	 * @wbp.parser.constructor
	 */
	public ErrorPanel(Vector<String> errors){
		
		addStyleName("gwt-errorsPanel");
		setSpacing(10);
		setWidth("100%");
		
		{
			Image image = new Image(GWTUtils.IMAGE_ERROR_ICON);
			image.setSize("40", "40");
			add(image);
		}
		{
			VerticalPanel errorsVPanel = new VerticalPanel();
			for (Enumeration<String> e = errors.elements(); e.hasMoreElements();){
				Label lblError = new Label(e.nextElement());
				lblError.setStylePrimaryName("gwt-Label-error");
				errorsVPanel.add(lblError);
			}
			add(errorsVPanel);
		}
		
		
	}
}
