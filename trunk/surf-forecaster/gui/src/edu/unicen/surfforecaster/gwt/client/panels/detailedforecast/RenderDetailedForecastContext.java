package edu.unicen.surfforecaster.gwt.client.panels.detailedforecast;

import com.google.gwt.user.client.ui.Widget;

public class RenderDetailedForecastContext {
	
	private RenderDetailedForecastStrategy renderStrategy = null;
	
	public RenderDetailedForecastContext(RenderDetailedForecastStrategy strategy) {
		this.renderStrategy = strategy;
	}
	
	public RenderDetailedForecastStrategy getRenderStrategy() {
		return renderStrategy;
	}

	public void setRenderStrategy(RenderDetailedForecastStrategy renderStrategy) {
		this.renderStrategy = renderStrategy;
	}
	
	public Widget executeRenderStrategy() {
		return this.renderStrategy.renderDetailedForecast();
	}
}
