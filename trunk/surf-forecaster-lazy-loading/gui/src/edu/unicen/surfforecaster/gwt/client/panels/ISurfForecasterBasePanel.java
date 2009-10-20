package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.Widget;

/**
 * This interface allows each panel the define a parent panel different to the above panel in the 
 * hierarchy
 * @author maximiliano.paolucci
 *
 */
public interface ISurfForecasterBasePanel {
	
	/**
	 * Sets the base panel of this panel (this define Who is the parent panel of this)
	 */
	public void setBasePanel(Widget basePanel);
	
	/**
	 * Retrieve the base panel of this panel (The result represent the parent panel of this)
	 */
	public Widget getBasePanel();
	
}
