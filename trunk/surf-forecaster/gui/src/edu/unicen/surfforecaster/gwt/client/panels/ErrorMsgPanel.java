package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Enumeration;
import java.util.Vector;


import com.google.gwt.user.client.ui.Label;

import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;


public class ErrorMsgPanel extends MessagePanel {
	
	public ErrorMsgPanel(){}
	
	/**
	 * @wbp.parser.constructor
	 */
	public ErrorMsgPanel(Vector<String> messages){
		super();
		this.addStyleName("gwt-errorsPanel");
		this.getImage().setUrl(GWTUtils.IMAGE_ERROR_ICON);
		
		for (Enumeration<String> e = messages.elements(); e.hasMoreElements();){
			Label lblError = new Label("- " + e.nextElement());
			lblError.addStyleName("gwt-Label-error");
			this.getMessagesVPanel().add(lblError);
		}	
	}
}
