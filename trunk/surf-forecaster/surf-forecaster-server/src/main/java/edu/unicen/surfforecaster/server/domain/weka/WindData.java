package edu.unicen.surfforecaster.server.domain.weka;

import java.io.Serializable;

public class WindData implements Serializable{
private double longitud;
private double latitude;
private double windSpeed;
private double windDirection;
public WindData(double theLongitud, double theLatitude){
	this.longitud = longitud;
	this.latitude = latitude;
}
public double getWindSpeed() {
	return windSpeed;
}
public double getWindDirection(double windDirection) {
	return this.windDirection;
}


}
