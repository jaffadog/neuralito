package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CurrentForecastPanel extends FlexTable {

	public CurrentForecastPanel(){}
	
	public CurrentForecastPanel(String title){
		{
			Label lblTitle = new Label(title);
			getFlexCellFormatter().setColSpan(0, 0, 2);
			getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
			setWidget(0,0, lblTitle);
			
			setCellSpacing(50);	
			setWidget(1, 0, this.createTableItem("Viento", "17 Km/h", "Este", "images/arrow.gif"));
			setWidget(1, 1, this.createTableItem("Dir. de la ola", "", "Sudoeste", "images/arrow.gif"));
			setWidget(2, 0, this.createTableItem("Altura de la ola", "2,3 Mts", "", "images/arrow.gif"));
			setWidget(2, 1, this.createTableItem("Período de ola", "9 Seg", "", "images/arrow.gif"));
		}
	}
	
	private VerticalPanel createTableItem(String title, String value, String imageTitle, String imageUrl){
		VerticalPanel tableItem = new VerticalPanel();
		
		tableItem.setSpacing(10);
		tableItem.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		Label tableItemValue = new Label(value);
		Image image = new Image(imageUrl);
		image.setTitle(imageTitle);
		image.setSize("50", "50");
		Label tableItemTitle = new Label(title);
		tableItem.add(tableItemValue);
		tableItem.add(image);
		tableItem.add(tableItemTitle);
		
		return tableItem;
	}
}
