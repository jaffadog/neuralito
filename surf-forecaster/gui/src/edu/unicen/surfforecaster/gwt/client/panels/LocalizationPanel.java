package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Iterator;
import java.util.Vector;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.gwt.client.Area;
import edu.unicen.surfforecaster.gwt.client.ForecastCommonServices;
import edu.unicen.surfforecaster.gwt.client.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.User;

public class LocalizationPanel extends Composite{
	
	private ListBox areaBox = null;
	private ListBox countryBox = null;
	private ListBox zoneBox = null;
	private ListBox spotBox = null;
	
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
						areaBox = new ListBox();
						localizationForm.setWidget(0, 1, areaBox);
						areaBox.setWidth("200");
					}
					{
						Label lblCountry = new Label(GWTUtils.LOCALE_CONSTANTS.country() + ": ");
						localizationForm.setWidget(0, 2, lblCountry);
						lblCountry.setWidth("90");
					}
					{
						countryBox = new ListBox();
						localizationForm.setWidget(0, 3, countryBox);
						countryBox.setWidth("200");
					}
					{
						Label lblZone = new Label(GWTUtils.LOCALE_CONSTANTS.zone() + ": ");
						localizationForm.setWidget(1, 0, lblZone);
						lblZone.setWidth("90");
					}
					{
						zoneBox = new ListBox();
						localizationForm.setWidget(1, 1, zoneBox);
						zoneBox.setWidth("200");
					}
					{
						Label lblSpot = new Label(GWTUtils.LOCALE_CONSTANTS.spot() + ": ");
						localizationForm.setWidget(1, 2, lblSpot);
						lblSpot.setWidth("90");
					}
					{
						spotBox = new ListBox();
						localizationForm.setWidget(1, 3, spotBox);
						spotBox.setWidth("200");
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
		
		this.setAreaListItems();
	}
	
	private void setAreaListItems(){
//		ForecastCommonServices.Util.getInstance().getAreas(new AsyncCallback<Vector<Area>>(){
//			public void onSuccess(Vector<Area> result) {
//				if (result == null) {
//				} else {
//					Iterator<Area> i = result.iterator();
//					while (i.hasNext()){
//						Area area = i.next();
//						areaBox.addItem(area.getName(), area.getId());
//					}
//				}
//			}
//				
//			public void onFailure(Throwable caught) {
//				
//			}
//		});
		ForecastCommonServices.Util.getInstance().getArea(new AsyncCallback<Area>(){
			public void onSuccess(Area result) {
				if (result == null) {
				} else {
					System.out.println(result.getName());
				}
			}
				
			public void onFailure(Throwable caught) {
				
			}
		});
	}
}
