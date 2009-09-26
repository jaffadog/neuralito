package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Enumeration;
import java.util.Vector;


import com.google.gwt.user.client.ui.Label;

import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;


public class SuccessMsgPanel extends MessagePanel {
	
	public SuccessMsgPanel(){
		super();
		this.addStyleName("gwt-successPanel");
		this.getImage().setUrl(GWTUtils.IMAGE_GOOD_ICON);
		this.setLabelsStyleNames("gwt-Label-success");
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public SuccessMsgPanel(Vector<String> messages){
		this();
		this.setMessages(messages);
	}
}
