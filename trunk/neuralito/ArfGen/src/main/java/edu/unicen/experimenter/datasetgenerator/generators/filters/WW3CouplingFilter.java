package edu.unicen.experimenter.datasetgenerator.generators.filters;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import edu.unicen.experimenter.datasetgenerator.data.WaveData;
import edu.unicen.experimenter.datasetgenerator.data.buoyobservations.WaveBuoyObservation;
import edu.unicen.experimenter.datasetgenerator.data.wavewatch.WaveWatchData;




public class WW3CouplingFilter extends Filter {
	private Collection<WaveBuoyObservation> buoyData;
	private int maxHoursDifference = 12;
	private boolean sameDayRestrict = true;

	public WW3CouplingFilter(Collection<WaveBuoyObservation> buoyData) {
		this.buoyData = buoyData;
	}
	
	public WW3CouplingFilter(Collection<WaveBuoyObservation> buoyData, int maxHoursDifference, boolean sameDayRestrict) {
		this.buoyData = buoyData;
		this.maxHoursDifference = maxHoursDifference;
		this.sameDayRestrict = sameDayRestrict;
	}

	@Override
	public Vector<WaveData> executeFilter(Vector<?> WW3DataSet) {
		Vector<WaveData> wwDatas = new Vector<WaveData>();
		for (Iterator iterator = buoyData.iterator(); iterator.hasNext();) {
			WaveBuoyObservation buoy = (WaveBuoyObservation) iterator.next();
			WaveWatchData wwData = getMoreRecentWaveWAtchOfBuoy(buoy, WW3DataSet);
			if (wwData != null)
				wwDatas.add(wwData);
		}
		return wwDatas;
	}

	private WaveWatchData getMoreRecentWaveWAtchOfBuoy(WaveBuoyObservation buoy, Vector<?> WW3DataSet) {
		Long buoyTime = buoy.getDate().getTimeInMillis();
		WaveWatchData prev = null;
		WaveWatchData prox = null;
		
		for (Iterator iterator = WW3DataSet.iterator(); iterator.hasNext();) {
			WaveWatchData wwData = (WaveWatchData) iterator.next();
			if (wwData.getDate().getTimeInMillis() <= buoyTime)
				prev = wwData;
			else{
				prox = wwData;
				break;
			}
		}
		prev = this.checkHoursDifference(prev, buoy);
		prox = this.checkHoursDifference(prox, buoy);
		
		if (prev == null && prox == null)
			return null;
		else if (prev == null )
			return prox;
		else if (prox == null)
			return prev;
		else if (Math.abs(prev.getDate().getTimeInMillis() - buoyTime) <= Math.abs(prox.getDate().getTimeInMillis() - buoyTime))
			return prev;
		else
			return prox;
	}
	
	private WaveWatchData checkHoursDifference(WaveWatchData wwData, WaveBuoyObservation buoy){
		long maxHourDiffMillisec = this.maxHoursDifference * 3600000; 
		
		if (wwData == null)
			return null;
		
		if (this.sameDayRestrict && !buoy.equalsDate(wwData.getDate()))
			return null;
		
		if (Math.abs(wwData.getDate().getTimeInMillis() - buoy.getDate().getTimeInMillis()) > maxHourDiffMillisec)
			return null;
		
		return wwData;
	}
	
	public String toString(){
		return "\tWW3CouplingFilter\n\t\tMaxHoursDifference -> " + this.maxHoursDifference + "\n\t\tSameDayRestrict -> " + this.sameDayRestrict + "\n";
	}
}
