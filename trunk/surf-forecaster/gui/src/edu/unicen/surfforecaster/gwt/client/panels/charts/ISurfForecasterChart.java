package edu.unicen.surfforecaster.gwt.client.panels.charts;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.visualization.client.DataTable;

public interface ISurfForecasterChart {
	
	/**
	 * Load and render the chart
	 * @param parent Widget - The widget whose contains the generated chart
	 */
	void render(Panel parent);
	
	/**
	 * Create the options parameters to setup the chart
	 * @param <Options>
	 * @return Options
	 */
	<Options> Options createOptions();
	
	/**
	 * Create the table with the data to show in the chart
	 * @return DataTable
	 */
	DataTable createTable();
}
