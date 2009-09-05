package edu.unicen.surfforecaster.client.panels;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.client.GWTUtils;
import edu.unicen.surfforecaster.client.SurfForecasterConstants;

public class LocalizationPanel extends Composite{
	
	public LocalizationPanel() {}
	
	/**
	 * @wbp.parser.constructor
	 */
	public LocalizationPanel(SurfForecasterConstants localeConstants) {
		{
			DisclosurePanel disclosurePanel = new DisclosurePanel(localeConstants.selectWave(), true);
			disclosurePanel.setAnimationEnabled(true);
			initWidget(disclosurePanel);
			disclosurePanel.setWidth("650");
			{
				VerticalPanel verticalPanel = new VerticalPanel();
				disclosurePanel.setContent(verticalPanel);
				{
					Label lblMessage = new Label("");
					lblMessage.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
					verticalPanel.add(lblMessage);
				}
				{
					FlexTable localizationForm = new FlexTable();
					localizationForm.setCellSpacing(5);
					verticalPanel.add(localizationForm);
					{
						Label lblArea = new Label(localeConstants.area() + ": ");
						localizationForm.setWidget(0, 0, lblArea);
						lblArea.setWidth("90");
					}
					{
						ListBox areaBox = new ListBox();
						localizationForm.setWidget(0, 1, areaBox);
						areaBox.setWidth("200");
					}
					{
						Label lblCountry = new Label(localeConstants.country() + ": ");
						localizationForm.setWidget(0, 2, lblCountry);
						lblCountry.setWidth("90");
					}
					{
						ListBox countryBox = new ListBox();
						localizationForm.setWidget(0, 3, countryBox);
						countryBox.setWidth("200");
					}
					{
						Label lblZone = new Label(localeConstants.zone() + ": ");
						localizationForm.setWidget(1, 0, lblZone);
						lblZone.setWidth("90");
					}
					{
						ListBox zoneBox = new ListBox();
						localizationForm.setWidget(1, 1, zoneBox);
						zoneBox.setWidth("200");
					}
					{
						Label lblBeach = new Label(localeConstants.wave() + ": ");
						localizationForm.setWidget(1, 2, lblBeach);
						lblBeach.setWidth("90");
					}
					{
						ListBox beachBox = new ListBox();
						localizationForm.setWidget(1, 3, beachBox);
						beachBox.setWidth("200");
					}
					{
						PushButton forecastButton = new PushButton(localeConstants.forecast());
						forecastButton.setSize("90", GWTUtils.PUSHBUTTON_HEIGHT);
						localizationForm.setWidget(2, 0, forecastButton);
					}
					localizationForm.getFlexCellFormatter().setColSpan(2, 0, 4);
					localizationForm.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);		
					localizationForm.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
					localizationForm.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
					localizationForm.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT);
					localizationForm.getCellFormatter().setHorizontalAlignment(1, 2, HasHorizontalAlignment.ALIGN_RIGHT);
				}
			}
		}
	}

}
