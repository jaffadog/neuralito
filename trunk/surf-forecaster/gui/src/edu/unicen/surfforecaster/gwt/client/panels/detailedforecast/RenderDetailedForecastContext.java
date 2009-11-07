package edu.unicen.surfforecaster.gwt.client.panels.detailedforecast;

import com.google.gwt.user.client.ui.Widget;

public class RenderDetailedForecastContext {
	
	private IRenderDetailedForecastStrategy renderStrategy = null;
	
	public RenderDetailedForecastContext(IRenderDetailedForecastStrategy strategy) {
		this.renderStrategy = strategy;
	}
	
	public IRenderDetailedForecastStrategy getRenderStrategy() {
		return renderStrategy;
	}

	public void setRenderStrategy(IRenderDetailedForecastStrategy renderStrategy) {
		this.renderStrategy = renderStrategy;
	}
	
	public Widget executeRenderStrategy() {
		return this.renderStrategy.renderDetailedForecast();
	}
}
