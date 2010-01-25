package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;

public class LoadingPanel extends VerticalPanel {
	
	private Label lblText = null;
	
	public LoadingPanel(){
		this.setWidth("100%");
		this.setStylePrimaryName("gwt-VerticalPanel-LoadingPanel");
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		Image loadingImage = new Image(GWTUtils.IMAGE_BLUE_CIRCLE_LOADER);
		this.add(loadingImage);
		this.lblText = new Label();
	}
	
	public LoadingPanel(String text){
		this();
		this.setText(text);
		this.add(this.lblText);
	}
	
	public void setText(String text) {
		this.lblText = new Label(text);
	}
}
