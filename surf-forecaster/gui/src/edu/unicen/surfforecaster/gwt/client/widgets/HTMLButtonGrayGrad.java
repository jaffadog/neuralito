package edu.unicen.surfforecaster.gwt.client.widgets;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.HTML;

public class HTMLButtonGrayGrad extends HTML implements MouseDownHandler, MouseUpHandler, MouseOutHandler {
	
	public final static int BUTTON_GRAY_GRAD_30PX = 30;
	public final static int BUTTON_GRAY_GRAD_60PX = 60;
	public final static int BUTTON_GRAY_GRAD_90PX = 90;
	public final static int BUTTON_GRAY_GRAD_120PX = 120;
	public final static int BUTTON_GRAY_GRAD_150PX = 150;
	public final static int BUTTON_GRAY_GRAD_180PX = 180;
	
	private boolean isEnabled = true;
	
	private String leftId = "";
	private String rightId = "";
	private String centerId = "";
	
	private int width = -999; //undefined
	
	public String getWidth() {
		if (this.width == -999)
			return null;
		return width + "px";
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public String getHeight() {
		return "26px";
	}

	public HTMLButtonGrayGrad(String text, String id) {
		super("<div id=\"gwt-surfForecaster-" + id + "-btn-left\" class=\"gwt-HTML-gray-grad-btn-left\"></div>" +
				"<div id=\"gwt-surfForecaster-" + id + "-btn-center\" class=\"gwt-HTML-gray-grad-btn-center\">" + text + "</div>" +
			  "<div id=\"gwt-surfForecaster-" + id + "-btn-right\" class=\"gwt-HTML-gray-grad-btn-right\"></div>", false);
		
		this.leftId = "gwt-surfForecaster-" + id + "-btn-left";
		this.centerId = "gwt-surfForecaster-" + id + "-btn-center";
		this.rightId = "gwt-surfForecaster-" + id + "-btn-right";
		
		this.addMouseDownHandler(this);
		this.addMouseUpHandler(this);
		this.addMouseOutHandler(this);
	}
	
	public HTMLButtonGrayGrad(String text, String id, int width) {
		
		this.width = width < 40 ? 40 : width;
		this.setWidth(this.width + "px");
		
		int btnWidth = this.width - 18; //real width is width - rounded corners images width left and right
 
		this.setHTML("<div id=\"gwt-surfForecaster-" + id + "-btn-left\" class=\"gwt-HTML-gray-grad-btn-left\"></div>" +
						"<div id=\"gwt-surfForecaster-" + id + "-btn-center\" class=\"gwt-HTML-gray-grad-btn-center\" style=\"width:" + btnWidth + "px\">" + text + "</div>" +
					 "<div id=\"gwt-surfForecaster-" + id + "-btn-right\" class=\"gwt-HTML-gray-grad-btn-right\"></div>");
		this.setWordWrap(false);
		
		this.leftId = "gwt-surfForecaster-" + id + "-btn-left";
		this.centerId = "gwt-surfForecaster-" + id + "-btn-center";
		this.rightId = "gwt-surfForecaster-" + id + "-btn-right";
		
		this.addMouseDownHandler(this);
		this.addMouseUpHandler(this);
		this.addMouseOutHandler(this);
	}
	
	public HTMLButtonGrayGrad(String text, String id, int width, String toolTip) {
		
		this.width = width < 40 ? 40 : width;
		this.setWidth(this.width + "px");
		
		int btnWidth = this.width - 18; //real width is width - rounded corners images width left and right
 
		this.setHTML("<div id=\"gwt-surfForecaster-" + id + "-btn-left\" class=\"gwt-HTML-gray-grad-btn-left\"></div>" +
						"<div id=\"gwt-surfForecaster-" + id + "-btn-center\" class=\"gwt-HTML-gray-grad-btn-center\" style=\"width:" + btnWidth + "px\">" + text + "</div>" +
					 "<div id=\"gwt-surfForecaster-" + id + "-btn-right\" class=\"gwt-HTML-gray-grad-btn-right\"></div>");
		this.setWordWrap(false);
		this.setTitle(toolTip);
		
		this.leftId = "gwt-surfForecaster-" + id + "-btn-left";
		this.centerId = "gwt-surfForecaster-" + id + "-btn-center";
		this.rightId = "gwt-surfForecaster-" + id + "-btn-right";
		
		this.addMouseDownHandler(this);
		this.addMouseUpHandler(this);
		this.addMouseOutHandler(this);
	}
	
	public native String setDownImage() /*-{
	
    	var leftId = this.@edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad::leftId;
    	var centerId = this.@edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad::centerId;
    	var rightId = this.@edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad::rightId;
    	
    	var leftBtn =  $doc.getElementById(leftId);
    	var centerBtn =  $doc.getElementById(centerId);
    	var rightBtn =  $doc.getElementById(rightId);
    	
    	leftBtn.style.background = 'url("./images/gray-grad-button-down-26px-left.gif")';
    	centerBtn.style.background = 'url("./images/gray-grad-button-down-26px-center.gif")';
    	rightBtn.style.background = 'url("./images/gray-grad-button-down-26px-right.gif")';

	}-*/;
	
	public native String setUpImage() /*-{
	
		var leftId = this.@edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad::leftId;
		var centerId = this.@edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad::centerId;
		var rightId = this.@edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad::rightId;
		
		var leftBtn =  $doc.getElementById(leftId);
		var centerBtn =  $doc.getElementById(centerId);
		var rightBtn =  $doc.getElementById(rightId);
		
		leftBtn.style.background = 'url("./images/gray-grad-button-up-26px-left.gif")';
		centerBtn.style.background = 'url("./images/gray-grad-button-up-26px-center.gif")';
		rightBtn.style.background = 'url("./images/gray-grad-button-up-26px-right.gif")';

	}-*/;

	@Override
	public void onMouseDown(MouseDownEvent event) {
		this.setDownImage();
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		this.setUpImage();	
	}
	
	@Override
	public void onMouseOut(MouseOutEvent event) {
		this.setUpImage();
	}
	
	public void setEnabled(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	public boolean isEnabled() {
		return this.isEnabled;
	}
}
