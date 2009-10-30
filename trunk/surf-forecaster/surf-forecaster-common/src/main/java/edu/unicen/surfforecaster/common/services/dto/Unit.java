/**
 * 
 */
package edu.unicen.surfforecaster.common.services.dto;

import java.io.Serializable;

/**
 * Enumeration measurement units.
 * 
 * @author esteban
 * 
 */
public enum Unit implements Serializable{
	KilometersPerHour, Meters, Seconds, Degrees, Meterspersecond, Feets, MilesPerHour, Knots;
	
	Unit() {
		// GWT purpose.
	}
}
