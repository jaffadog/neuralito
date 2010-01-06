package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Enumeration;
import java.util.Vector;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MessagePanel extends HorizontalPanel {
	Image image = null;
	VerticalPanel messagesVPanel = null;
	String labelsStyleNames = "";
	
	public String getLabelsStyleNames() {
		return this.labelsStyleNames;
	}

	public void setLabelsStyleNames(String labelsStyleNames) {
		this.labelsStyleNames = labelsStyleNames;
	}

	public MessagePanel(){
		setSpacing(10);
		setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		setWidth("100%");
		{
			this.image = new Image();
			this.image.setSize("50px", "50px");
			add(this.image);
			setCellWidth(this.image, "40px");
			setCellVerticalAlignment(this.image, HasVerticalAlignment.ALIGN_MIDDLE);
		}
		{
			this.messagesVPanel = new VerticalPanel();
			this.messagesVPanel.setWidth("100%");
			add(this.messagesVPanel);
		}
	}

	public Image getImage() {
		return this.image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public VerticalPanel getMessagesVPanel() {
		return this.messagesVPanel;
	}

	public void setMessagesVPanel(VerticalPanel messagesVPanel) {
		this.messagesVPanel = messagesVPanel;
	}
	
	/**
	 * Set a collection of messages to show in the same message panel
	 * @param Vector<String> messages
	 */
	public void setMessages(Vector<String> messages) {
		this.getMessagesVPanel().clear();
		for (Enumeration<String> e = messages.elements(); e.hasMoreElements();){
			Label lblMessage = new Label("- " + e.nextElement());
			lblMessage.addStyleName(this.getLabelsStyleNames());
			this.getMessagesVPanel().add(lblMessage);
		}
	}
	
	/**
	 * Set a message to show in the message panel
	 * @param String message
	 */
	public void setMessage(String message) {
		this.getMessagesVPanel().clear();
		Label lblMessage = new Label("- " + message);
		lblMessage.addStyleName(this.getLabelsStyleNames());
		this.getMessagesVPanel().add(lblMessage);
	}
}
