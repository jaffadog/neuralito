package edu.unicen.surfforecaster.gwt.client;

import java.util.List;

import edu.unicen.surfforecaster.common.services.dto.ZoneDTO;
import edu.unicen.surfforecaster.gwt.client.dto.AreaGwtDTO;
import edu.unicen.surfforecaster.gwt.client.dto.CountryGwtDTO;
import edu.unicen.surfforecaster.gwt.client.dto.SpotGwtDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SpotServicesAsync {
	void addSpot(String spotName, String spotLatitude, String spotLongitude, String buoyLatitude, String buoyLongitude, Integer zoneId, Integer countryId, 
			String zoneName, boolean public_, String timezone, AsyncCallback<Integer> callback);
	
	void editSpot(Integer spotId, String spotName, String spotLatitude, String spotLongitude, String buoyLatitude, String buoyLongitude, Integer zoneId, Integer countryId, 
			String zoneName, boolean public_, String timezone, boolean changedGridPoint, AsyncCallback<Integer> callback);
	
	void getAreas(String localeCode, AsyncCallback<List<AreaGwtDTO>> callback);
	
	void getCountries(String localeCode, AsyncCallback<List<CountryGwtDTO>> callback);
	
	void getZones(Integer country, AsyncCallback<List<ZoneDTO>> callback);

	void getSpots(Integer zone, AsyncCallback<List<SpotGwtDTO>> callback);
	
	void getSpotsCreatedBy(AsyncCallback<List<SpotGwtDTO>> callback);
	
	void deleteSpot(Integer spotId, AsyncCallback<Boolean> callback);
	
	void getSpot(Integer spotId, AsyncCallback<SpotGwtDTO> callback);
}
