package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.WW3Parameter;


public class CurrentForecastPanel extends FlexTable {

	public CurrentForecastPanel(){}
	
	public CurrentForecastPanel(String title, ForecastDTO forecast){
		{
			Label lblTitle = new Label(title);
			getFlexCellFormatter().setColSpan(0, 0, 2);
			getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
			setWidget(0,0, lblTitle);
			setCellSpacing(50);
			if (forecast != null) {
				setWidget(1, 0, this.createTableItem("Viento", forecast.getMap().get(WW3Parameter.WIND_SPEED.toString()).getValue() + " " + forecast.getMap().get(WW3Parameter.WIND_SPEED.toString()).getUnit().toString(), "Este", "images/arrow.gif"));
				setWidget(1, 1, this.createTableItem("Dir. de la ola", forecast.getMap().get(WW3Parameter.PRIMARY_WAVE_DIRECTION.toString()).getValue() + " " + forecast.getMap().get(WW3Parameter.PRIMARY_WAVE_DIRECTION.toString()).getUnit().toString(), "Sudoeste", "images/arrow.gif"));
				setWidget(2, 0, this.createTableItem("Altura de la ola", forecast.getMap().get(WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.toString()).getValue() + " " + forecast.getMap().get(WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.toString()).getUnit().toString(), "", "images/waves/wave1.png"));
				setWidget(2, 1, this.createTableItem("Período de ola", forecast.getMap().get(WW3Parameter.PRIMARY_WAVE_PERIOD.toString()).getValue() + " " + forecast.getMap().get(WW3Parameter.PRIMARY_WAVE_PERIOD.toString()).getUnit().toString(), "", "images/arrow.gif"));
			} else {
				lblTitle.setText(title + " - No disponible");
			}
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
