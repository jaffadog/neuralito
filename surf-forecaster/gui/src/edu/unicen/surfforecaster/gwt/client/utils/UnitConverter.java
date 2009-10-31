package edu.unicen.surfforecaster.gwt.client.utils;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.Unit;

public class UnitConverter {

	public double convertValue(String value, Unit source, Unit target) throws NeuralitoException {
		double numValue = new Double(value);
		return this.convertValue(numValue, source,target);
	}
	
	public double convertValue(double value, Unit source, Unit target) throws NeuralitoException {
		
		
		double result = 0;
		
		if (source == target) {
			result = value;
		} else {
			switch (source) {
			case Meters:
				result = convertMetersTo(value, target);
				break;
			
			case KilometersPerHour:
				result = convertKilometersPerHourTo(value, target);
				break;
				
			default:
				//TODO crear una excepcion de conversion invalida
				throw new NeuralitoException();
			}
		}
		
		return result;
		
	}
	
	private double convertMetersTo(double value, Unit target) throws NeuralitoException {
		
		switch (target) {
		case Feets:
			return value / 0.3048;

		default:
			//TODO crear una excepcion de conversion invalida
			throw new NeuralitoException();
		}
	}
	
	private double convertKilometersPerHourTo(double value, Unit target) throws NeuralitoException {
		
		switch (target) {
		case Knots:
			return value / 1.852;
		
		case Meterspersecond:
			return value / 3.6;
		
		case MilesPerHour:
			return value / 1.6093487;

		default:
			//TODO crear una excepcion de conversion invalida
			throw new NeuralitoException();
		}
	}
	
	
	
	
	
	
	
	
}
