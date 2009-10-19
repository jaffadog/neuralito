package edu.unicen.surfforecaster.gwt.client;

import java.util.List;

import edu.unicen.surfforecaster.common.services.dto.AreaDTO;
import edu.unicen.surfforecaster.common.services.dto.CountryDTO;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.common.services.dto.ZoneDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SpotServicesAsync {
	void addSpot(String spotName, String spotLongitude, String spotLatitude, String buoyLongitude, String buoyLatitude, Integer zoneId, Integer countryId, 
			String zoneName, boolean public_, String timezone, AsyncCallback<Integer> callback);

//	Map<String, List> getAreas() throws NeuralitoException;
//
//	Map<String, List> getCountries(String area);
//
//	Map<String, List> getZones(String country);
//
//	Map<String, List> getSpots(String spot);
	
	void getAreas(AsyncCallback<List<AreaDTO>> callback);
	
	void getCountries(AsyncCallback<List<CountryDTO>> callback);
	
	void getZones(String country, AsyncCallback<List<ZoneDTO>> callback);

	void getSpots(String spot, AsyncCallback<List<SpotDTO>> callback);
}
