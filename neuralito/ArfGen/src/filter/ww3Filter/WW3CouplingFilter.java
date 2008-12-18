package filter.ww3Filter;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import ww3.WaveWatchData;

import buoy.BuoyData;

import filter.Filter;

public class WW3CouplingFilter extends Filter{
Collection buoyData;
	public WW3CouplingFilter(Collection buoyData){
		this.buoyData = buoyData;
	}
	@Override
	public Vector<?> executeFilter(Vector<?> WW3DataSet) {
				Collection<WaveWatchData> wwDatas = new Vector<WaveWatchData>();
		for (Iterator iterator = buoyData.iterator(); iterator.hasNext();) {
			BuoyData buoy = (BuoyData) iterator.next();
			WaveWatchData wwData =  getMoreRecentWaveWAtchOfBuoy(buoy, WW3DataSet);
			wwDatas.add(wwData);
		}
	return (Vector<?>) wwDatas;
	}
	private WaveWatchData getMoreRecentWaveWAtchOfBuoy(BuoyData buoy,
			Vector<?> WW3DataSet) {
		Long buoyTime = buoy.getDate().getTimeInMillis();
		WaveWatchData prev = null;
		for (Iterator iterator = WW3DataSet.iterator(); iterator.hasNext();) {
			WaveWatchData wwData = (WaveWatchData) iterator.next();
			if ( wwData.getDate().getTimeInMillis() < buoyTime )
				prev = wwData;			
			else
				break;
			
		}
		return prev;
	}

}
