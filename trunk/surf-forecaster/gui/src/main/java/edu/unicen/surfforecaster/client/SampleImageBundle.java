package edu.unicen.surfforecaster.client;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

/**
 * This is a sample how to use image bundles in your gwt-maven project. 
 * @author Andrew
 */
public interface SampleImageBundle extends ImageBundle {

	/**
	 * @gwt.resource edu/unicen/surfforecaster/public/images/gwt-logo.png
	 */
	public AbstractImagePrototype getGWTLogo();

}
