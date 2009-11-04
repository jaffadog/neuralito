package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.common.services.dto.WW3Parameter;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;
import edu.unicen.surfforecaster.gwt.client.utils.images.waves.s50.Waves50PxFactory;
import edu.unicen.surfforecaster.gwt.client.utils.images.waves.s50.Waves50pxImageBundle;


public class CurrentForecastPanel extends FlexTable {

	public CurrentForecastPanel(){}
	
	public CurrentForecastPanel(String title, ForecastDTO forecast){
		{
			Label lblTitle = new Label(title);
			getFlexCellFormatter().setColSpan(0, 0, 2);
			getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
			setWidget(0,0, lblTitle);
			setCellSpacing(50);
			Waves50pxImageBundle wavesImageBundle = (Waves50pxImageBundle) GWT.create(Waves50pxImageBundle.class);
			if (forecast != null) {
				String waveHeight = forecast.getMap().get(WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.toString()).getValue();
				//TODO generar las unidades en que se ve el sitio como alguna setting de usuario o usando cookies o algo y emprolijar la manera de levantarlo
				Unit target = Unit.Meters;
				try {
					waveHeight = NumberFormat.getFormat("###.#").format(UnitConverter.convertValue(waveHeight, Unit.Meters, target));
				} catch (NeuralitoException e) {
					// TODO ver como manejar esta exvepcion de conversion de unidades
					e.printStackTrace();
				}
				setWidget(1, 0, this.createTableItem("Viento", forecast.getMap().get(WW3Parameter.WIND_SPEED.toString()).getValue() + " " + forecast.getMap().get(WW3Parameter.WIND_SPEED.toString()).getUnit().toString(), "Este", /*wavesImageBundle.wave_0d75_1d49().createImage()*/ Waves50PxFactory.getWaveIcon(forecast.getMap().get(WW3Parameter.WIND_SPEED.toString()).getValue(), Unit.Meters)));
				setWidget(1, 1, this.createTableItem("Dir. de la ola", forecast.getMap().get(WW3Parameter.PRIMARY_WAVE_DIRECTION.toString()).getValue() + " " + forecast.getMap().get(WW3Parameter.PRIMARY_WAVE_DIRECTION.toString()).getUnit().toString(), "Sudoeste", "images/arrow.gif"));
				setWidget(2, 0, this.createTableItem("Altura de la ola", waveHeight + " " + forecast.getMap().get(WW3Parameter.COMBINED_SWELL_WIND_WAVE_HEIGHT.toString()).getUnit().toString(), "", Waves50PxFactory.getWaveIcon(waveHeight, target)));
				setWidget(2, 1, this.createTableItem("Período de ola", forecast.getMap().get(WW3Parameter.PRIMARY_WAVE_PERIOD.toString()).getValue() + " " + forecast.getMap().get(WW3Parameter.PRIMARY_WAVE_PERIOD.toString()).getUnit().toString(), "", "images/arrow.gif"));
			} else {
				lblTitle.setText(title + " - No disponible");
			}
		}
	}
	//TODO cuando todas la imagenes se levanten del factory eliminar este metodo que quedaria obsoleto
	private VerticalPanel createTableItem(String title, String value, String imageTitle, String imageUrl){
		VerticalPanel tableItem = new VerticalPanel();
		
		tableItem.setSpacing(10);
		tableItem.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		Label tableItemValue = new Label(value);
		Image image = new Image(imageUrl);
		image.setTitle(imageTitle);
		Label tableItemTitle = new Label(title);
		tableItem.add(tableItemValue);
		tableItem.add(image);
		tableItem.add(tableItemTitle);
		
		return tableItem;
	}
	
	private VerticalPanel createTableItem(String title, String value, String imageTitle, Image image){
		VerticalPanel tableItem = new VerticalPanel();
		
		tableItem.setSpacing(10);
		tableItem.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		Label tableItemValue = new Label(value);
		image.setTitle(imageTitle);
		image.setStyleName("gwt-image-wave");
		Label tableItemTitle = new Label(title);
		tableItem.add(tableItemValue);
		tableItem.add(image);
		tableItem.add(tableItemTitle);
		
		return tableItem;
	}
}
