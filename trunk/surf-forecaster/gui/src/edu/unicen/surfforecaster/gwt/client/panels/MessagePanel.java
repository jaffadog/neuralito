package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MessagePanel extends HorizontalPanel {
	
	Image image = null;
	VerticalPanel messagesVPanel = null;
	
	public MessagePanel(){
		setSpacing(10);
		setWidth("100%");
		
		{
			this.image = new Image();
			image.setSize("40", "40");
			add(image);
		}
		{
			this.messagesVPanel = new VerticalPanel();
			add(messagesVPanel);
		}
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public VerticalPanel getMessagesVPanel() {
		return messagesVPanel;
	}

	public void setMessagesVPanel(VerticalPanel messagesVPanel) {
		this.messagesVPanel = messagesVPanel;
	}
	
}
