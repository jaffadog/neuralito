package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad;

public class VisualObservationSampleBox extends DialogBox {
	
	private static VisualObservationSampleBox instance = null;
	private final String crossIconHTML = "<div id=\"closeBoxDiv\" ><a onclick=\"closeObsFormatDialog()\">X</a></div>";
	
	//this var sets in which row must be inserted each element
	private int rowIndex = -1;
	
	public static VisualObservationSampleBox getInstance() {
        if (instance == null) {
            instance = new VisualObservationSampleBox();
        }
        return instance;
    }
	
	private VisualObservationSampleBox() {
		super(false, false);
		setAnimationEnabled(true);
		this.setHTML(this.crossIconHTML);
		this.redefineClose(this);
		this.setWidth("500px");
		
		FlexTable container = new FlexTable();
		
		Label lblTitle = new Label(GWTUtils.LOCALE_CONSTANTS.observationsFileFormat());
		lblTitle.addStyleName("gwt-Label-Title");
		container.setWidget(this.getNextIndex(), 0, lblTitle);
		
		Label lblDescription = new Label(GWTUtils.LOCALE_CONSTANTS.observationsFileFormatDesc());
		lblDescription.addStyleName("gwt-Label-SectionDescription");
		container.setWidget(this.getNextIndex(), 0, lblDescription);
		
		Image imgSample = new Image(GWTUtils.IMAGE_OBS_SAMPLE());
		container.setWidget(this.getNextIndex(), 0, imgSample);
		
		HTMLButtonGrayGrad closeBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.close(), "VisualObservationSampleBox-Close", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_60PX);
		container.setWidget(this.getNextIndex(), 0, closeBtn);
		closeBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		container.getFlexCellFormatter().setHorizontalAlignment(this.getCurrentRowIndex(), 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		this.setWidget(container);
	}
	
	private int getNextIndex() {
		rowIndex++;
		return rowIndex;
	}
	
	private int getCurrentRowIndex() {
		return rowIndex;
	}
	
	// Define closeDialog using JSNI
	private native void redefineClose(VisualObservationSampleBox sampleBox) /*-{
		$wnd['closeObsFormatDialog'] = function () {
			sampleBox.@edu.unicen.surfforecaster.gwt.client.panels.VisualObservationSampleBox::hide()();
		}
	}-*/;
}
