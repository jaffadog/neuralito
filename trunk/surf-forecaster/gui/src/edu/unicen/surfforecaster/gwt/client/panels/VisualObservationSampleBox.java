package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;

public class VisualObservationSampleBox extends DialogBox {
	
	private static VisualObservationSampleBox instance = null;
	private final String crossIconHTML = "<div id=\"closeBoxDiv\" ><a onclick=\"closeObsFormatDialog()\">X</a></div>";
	
	//this var sets in which row must be inserted each element
	private int rowIndex = 0;
	
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
		container.setWidget(this.getRowIndex(), 0, lblTitle);
		
		Label lblDescription = new Label(GWTUtils.LOCALE_CONSTANTS.observationsFileFormatDesc());
		lblDescription.addStyleName("gwt-Label-SectionDescription");
		container.setWidget(this.getRowIndex(), 0, lblDescription);
		
		Image imgSample = new Image(GWTUtils.IMAGE_OBS_SAMPLE());
		container.setWidget(this.getRowIndex(), 0, imgSample);
		
		this.setWidget(container);
	}
	
	private int getRowIndex() {
		rowIndex++;
		return rowIndex - 1;
	}
	
	// Define closeDialog using JSNI
	private native void redefineClose(VisualObservationSampleBox sampleBox) /*-{
		$wnd['closeObsFormatDialog'] = function () {
			sampleBox.@edu.unicen.surfforecaster.gwt.client.panels.VisualObservationSampleBox::hide()();
		}
	}-*/;
}
