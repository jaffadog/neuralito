package filter.ww3Filter;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import ww3.WaveWatchData;

import buoy.BuoyData;

import filter.Filter;

public class WW3CouplingFilter extends Filter {
	private Collection buoyData;
	private int maxHoursDifference = 12;

	public WW3CouplingFilter(Collection buoyData, int maxHourDifference) {
		this.buoyData = buoyData;
		this.maxHoursDifference = maxHoursDifference;
	}

	@Override
	public Vector<?> executeFilter(Vector<?> WW3DataSet) {
		Collection<WaveWatchData> wwDatas = new Vector<WaveWatchData>();
		for (Iterator iterator = buoyData.iterator(); iterator.hasNext();) {
			BuoyData buoy = (BuoyData) iterator.next();
			WaveWatchData wwData = getMoreRecentWaveWAtchOfBuoy(buoy, WW3DataSet);
			if (wwData != null)
				wwDatas.add(wwData);
		}
		return (Vector<?>) wwDatas;
	}

	private WaveWatchData getMoreRecentWaveWAtchOfBuoy(BuoyData buoy, Vector<?> WW3DataSet) {
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
	
	private WaveWatchData checkHoursDifference(WaveWatchData wwData, BuoyData buoy){
		long maxHourDiffMillisec = this.maxHoursDifference * 3600000; 
		if (Math.abs(wwData.getDate().getTimeInMillis() - buoy.getDate().getTimeInMillis()) > maxHourDiffMillisec)
			return null;
		return wwData;
	}
	
}
