package edu.unicen.surfforecaster.gwt.client.utils;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.Unit;

public final class UnitConverter {

	public static double convertValue(String value, Unit source, Unit target) throws NeuralitoException {
		double numValue = new Double(value.replace(",", "."));
		return convertValue(numValue, source,target);
	}
	
	public static double convertValue(double value, Unit source, Unit target) throws NeuralitoException {
		
		
		double result = 0;
		
		if (source == target) {
			result = value;
		} else {
			switch (source) {
			case Meters:
				result = convertMetersTo(value, target);
				break;
			
			case Feets:
				result = convertFeetsTo(value, target);
				break;
			
			case KilometersPerHour:
				result = convertKilometersPerHourTo(value, target);
				break;
			
			case Knots:
				result = convertKnotsTo(value, target);
				break;
				
			default:
				//TODO crear una excepcion de conversion invalida
				throw new NeuralitoException();
			}
		}
		
		return result;
		
	}
	
	private static double convertMetersTo(double value, Unit target) throws NeuralitoException {
		
		switch (target) {
		case Feets:
			return value / 0.3048;

		default:
			//TODO crear una excepcion de conversion invalida
			throw new NeuralitoException();
		}
	}
	
	private static double convertFeetsTo(double value, Unit target) throws NeuralitoException {
		
		switch (target) {
		case Meters:
			return value * 0.3048;

		default:
			//TODO crear una excepcion de conversion invalida
			throw new NeuralitoException();
		}
	}
	
	private static double convertKilometersPerHourTo(double value, Unit target) throws NeuralitoException {
		
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
	
	private static double convertKnotsTo(double value, Unit target) throws NeuralitoException {
		
		switch (target) {
		case KilometersPerHour:
			return value * 1.852;
			
		default:
			//TODO crear una excepcion de conversion invalida
			throw new NeuralitoException();
		}
	}
	
	
	
	
	
	
	
	
}
