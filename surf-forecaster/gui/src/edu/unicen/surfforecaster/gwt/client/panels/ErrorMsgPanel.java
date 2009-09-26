package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Enumeration;
import java.util.Vector;


import com.google.gwt.user.client.ui.Label;

import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;


public class ErrorMsgPanel extends MessagePanel {
	
	public ErrorMsgPanel(){
		super();
		this.addStyleName("gwt-errorsPanel");
		this.getImage().setUrl(GWTUtils.IMAGE_ERROR_ICON);
		this.setLabelsStyleNames("gwt-Label-error");
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public ErrorMsgPanel(Vector<String> messages){
		this();
		this.setMessages(messages);	
	}
}
