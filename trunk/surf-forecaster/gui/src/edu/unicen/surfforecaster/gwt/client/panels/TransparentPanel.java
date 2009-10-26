package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class TransparentPanel extends PopupPanel {
	
	private static TransparentPanel instance = null;
	
	public static TransparentPanel getInstance() {
        if (instance == null) {
            instance = new TransparentPanel(false);
        }
        return instance;
    }
	
	private TransparentPanel() {}

	private TransparentPanel(boolean autoHide) {
		super(autoHide);
		this.setStylePrimaryName("gwt-PopupPanel-Transparent");
		this.setPopupPosition(0, 0);
		this.add(new Label(""));
		this.setAnimationEnabled(true);
		
		//When window is resized this panel must cover all the new window dimension
		Window.addResizeHandler(new ResizeHandler() {
			public void onResize(ResizeEvent event) {
				setSize(Window.getClientWidth() + "", Window.getClientHeight() + "");
			}
			
		});
	}
	
	public void show() {
		Window.scrollTo(0, 0);
		Window.enableScrolling(false);
		this.setSize(Window.getClientWidth() + "", Window.getClientHeight() + "");
		super.show();
	}
	
	public void hide() {
		Window.enableScrolling(true);
		super.hide();
	}

}
