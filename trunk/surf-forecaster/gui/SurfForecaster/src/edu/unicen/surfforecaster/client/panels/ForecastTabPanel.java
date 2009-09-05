package edu.unicen.surfforecaster.client.panels;

import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ForecastTabPanel extends DecoratedTabPanel {
	
	public ForecastTabPanel() {
		{
			ForecastPanel forecastPanel = new ForecastPanel();
			this.add(forecastPanel, "Pronostico");
		}
		{
			WaveDescriptionPanel waveDescriptionPanel = new WaveDescriptionPanel();
			this.add(waveDescriptionPanel, "Descripcion de la ola");
		}
		{
			WaveComparatorPanel waveComparatorPanel = new WaveComparatorPanel();
			this.add(waveComparatorPanel, "Comparador");
		}
		{
			NewWavePanel newWavePanel = new NewWavePanel();
			this.add(newWavePanel, "Nueva ola");
		}
		
		setAnimationEnabled(true);
	}

}
