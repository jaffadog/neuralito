package edu.unicen.surfforecaster.gwt.client.panels;

import edu.unicen.surfforecaster.gwt.client.utils.Observable;
import edu.unicen.surfforecaster.gwt.client.utils.Observer;

public interface ILocalizationPanel extends Observer{

	public abstract String getZoneBoxDisplayText();

	public abstract String getSpotBoxDisplayText();

	public abstract String getSpotBoxDisplayValue();

	public abstract void update(Observable o, Object arg);

}