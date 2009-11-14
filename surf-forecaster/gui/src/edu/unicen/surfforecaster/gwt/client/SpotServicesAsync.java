package edu.unicen.surfforecaster.gwt.client;

import java.util.List;

import edu.unicen.surfforecaster.common.services.dto.AreaDTO;
import edu.unicen.surfforecaster.common.services.dto.CountryDTO;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.common.services.dto.ZoneDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SpotServicesAsync {
	void addSpot(String spotName, String spotLatitude, String spotLongitude, String buoyLatitude, String buoyLongitude, Integer zoneId, Integer countryId, 
			String zoneName, boolean public_, String timezone, AsyncCallback<Integer> callback);
	
	void getAreas(AsyncCallback<List<AreaDTO>> callback);
	
	void getCountries(AsyncCallback<List<CountryDTO>> callback);
	
	void getZones(Integer country, AsyncCallback<List<ZoneDTO>> callback);

	void getSpots(Integer zone, AsyncCallback<List<SpotDTO>> callback);
}
