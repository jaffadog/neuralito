package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.gwt.client.GWTUtils;

public class LocalizationPanel extends Composite{
	
	public LocalizationPanel() {
		{
			DisclosurePanel disclosurePanel = new DisclosurePanel(GWTUtils.LOCALE_CONSTANTS.selectSpot(), true);
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
						Label lblArea = new Label(GWTUtils.LOCALE_CONSTANTS.area() + ": ");
						localizationForm.setWidget(0, 0, lblArea);
						lblArea.setWidth("90");
					}
					{
						ListBox areaBox = new ListBox();
						localizationForm.setWidget(0, 1, areaBox);
						areaBox.setWidth("200");
					}
					{
						Label lblCountry = new Label(GWTUtils.LOCALE_CONSTANTS.country() + ": ");
						localizationForm.setWidget(0, 2, lblCountry);
						lblCountry.setWidth("90");
					}
					{
						ListBox countryBox = new ListBox();
						localizationForm.setWidget(0, 3, countryBox);
						countryBox.setWidth("200");
					}
					{
						Label lblZone = new Label(GWTUtils.LOCALE_CONSTANTS.zone() + ": ");
						localizationForm.setWidget(1, 0, lblZone);
						lblZone.setWidth("90");
					}
					{
						ListBox zoneBox = new ListBox();
						localizationForm.setWidget(1, 1, zoneBox);
						zoneBox.setWidth("200");
					}
					{
						Label lblBeach = new Label(GWTUtils.LOCALE_CONSTANTS.spot() + ": ");
						localizationForm.setWidget(1, 2, lblBeach);
						lblBeach.setWidth("90");
					}
					{
						ListBox beachBox = new ListBox();
						localizationForm.setWidget(1, 3, beachBox);
						beachBox.setWidth("200");
					}
					{
						PushButton forecastButton = new PushButton(GWTUtils.LOCALE_CONSTANTS.forecast());
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
