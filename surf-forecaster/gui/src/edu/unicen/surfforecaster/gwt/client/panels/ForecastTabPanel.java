package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.DecoratedTabPanel;

import edu.unicen.surfforecaster.gwt.client.GWTUtils;

public class ForecastTabPanel extends DecoratedTabPanel {
	
	public ForecastTabPanel() {
		{
			ForecastPanel forecastPanel = new ForecastPanel();
			this.add(forecastPanel, GWTUtils.LOCALE_CONSTANTS.forecast());
		}
		{
			WaveDescriptionPanel waveDescriptionPanel = new WaveDescriptionPanel();
			this.add(waveDescriptionPanel, GWTUtils.LOCALE_CONSTANTS.spotDescription());
		}
		{
			WaveComparatorPanel waveComparatorPanel = new WaveComparatorPanel();
			this.add(waveComparatorPanel, GWTUtils.LOCALE_CONSTANTS.spotComparator());
		}
		{
			NewWavePanel newWavePanel = new NewWavePanel();
			this.add(newWavePanel, GWTUtils.LOCALE_CONSTANTS.newSpot());
		}
		
		setAnimationEnabled(true);
		setWidth(GWTUtils.APLICATION_WIDTH);
		selectTab(0);
	}

}
