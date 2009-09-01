package edu.unicen.surfforecaster.client.panels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.client.GWTUtils;
import edu.unicen.surfforecaster.client.SurfForecasterConstants;

public class ContentPanel extends Composite {
	
	public ContentPanel(){}
	
	/**
	 * @wbp.parser.constructor
	 */
	public ContentPanel(SurfForecasterConstants localeConstants) {
		VerticalPanel contentVPanel = new VerticalPanel();
		contentVPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		contentVPanel.setWidth(GWTUtils.APLICATION_WIDTH);
		initWidget(contentVPanel);
		{
			LocalizationPanel localizationPanel = new LocalizationPanel(localeConstants);
			contentVPanel.add(localizationPanel);
		}
		
	}

}
