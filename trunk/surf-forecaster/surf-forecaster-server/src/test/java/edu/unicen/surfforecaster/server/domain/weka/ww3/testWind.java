package edu.unicen.surfforecaster.server.domain.weka.ww3;

import java.util.Vector;

import edu.unicen.surfforecaster.server.domain.weka.WaveWatchLoader;
import edu.unicen.surfforecaster.server.domain.weka.WaveWatchData;
import edu.unicen.surfforecaster.server.domain.weka.util.Util;

public class testWind {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        double windU = 9.8;//-3.17;
		double windV = -3.0;//2.63;
	
		String[] years = new String[]{"2004"};
		Double ww3Y = Util.NORTH;
		Double ww3X = Util.WEST;
		System.out.println(Math.atan2(1,-1));
		System.out.println(270 - ( Math.atan2(windV,windU) * (180/Math.PI) ));
		
	//	Load buoy data Ww3 Vobs
		//buoyDataSet = new BuoyDataLoader().loadBuoyData(buoyFiles);
		Vector ww3DataSet  = (Vector<WaveWatchData>) new WaveWatchLoader().getWWData(years,ww3Y,ww3X);
		Util.printCollection(ww3DataSet);
	//	System.out.println(calculateWindDirection(windU, windV));
		
		

	}
	private static double calculateWindDirection(double windU, double windV) {
//		double DperR = 57.29578;
//		return 270 - ( Math.atan2(windV,windU) * DperR );	
		double a = windU/windV;
		System.out.println(a);
		double per = (180/Math.PI);
		System.out.println(per);
		int tita=0; 
		if (windV>=0)
			tita = 180;
		else if (windV<0 && windU<0)
			tita = 0;
		else if (windU>=0 && windV<0) 
		tita =360;
		System.out.println(tita);
		return Math.atan(a)*per + tita;
				
	}

}
