package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Enumeration;
import java.util.Vector;

import com.google.gwt.user.client.ui.Label;

import edu.unicen.surfforecaster.gwt.client.GWTUtils;

public class SuccessMsgPanel extends MessagePanel {
	
	public SuccessMsgPanel(){}
	
	/**
	 * @wbp.parser.constructor
	 */
	public SuccessMsgPanel(Vector<String> messages){
		super();
		this.addStyleName("gwt-successPanel");
		this.getImage().setUrl(GWTUtils.IMAGE_GOOD_ICON);
		
		for (Enumeration<String> e = messages.elements(); e.hasMoreElements();){
			Label lblMessage = new Label("- " + e.nextElement());
			lblMessage.addStyleName("gwt-Label-success");
			this.getMessagesVPanel().add(lblMessage);
		}
		
		
	}
}
