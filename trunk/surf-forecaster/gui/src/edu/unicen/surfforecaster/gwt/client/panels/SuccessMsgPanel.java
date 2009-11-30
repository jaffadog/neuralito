package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Vector;

import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;


public class SuccessMsgPanel extends MessagePanel {
	
	public SuccessMsgPanel(){
		super();
		this.addStyleName("gwt-successPanel");
		this.getImage().setUrl(GWTUtils.IMAGE_GOOD_ICON);
		this.setLabelsStyleNames("gwt-Label-success");
	}
	
	public SuccessMsgPanel(Vector<String> messages){
		this();
		this.setMessages(messages);
	}
	
	public SuccessMsgPanel(String message) {
		this();
		Vector<String> messages = new Vector<String>();
		messages.add(message);
		this.setMessages(messages);
	}
}
