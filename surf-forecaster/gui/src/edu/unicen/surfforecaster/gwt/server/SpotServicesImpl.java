package edu.unicen.surfforecaster.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ForecastService;
import edu.unicen.surfforecaster.common.services.SpotService;
import edu.unicen.surfforecaster.common.services.dto.AreaDTO;
import edu.unicen.surfforecaster.common.services.dto.CountryDTO;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.common.services.dto.UserDTO;
import edu.unicen.surfforecaster.common.services.dto.ZoneDTO;
import edu.unicen.surfforecaster.gwt.client.SpotServices;
import edu.unicen.surfforecaster.gwt.client.utils.SessionData;

public class SpotServicesImpl extends ServicesImpl implements SpotServices {
	
	private SpotService spotService;
	private ForecastService forecastService;

	/**
	 * @param service
	 *            the service to set
	 */
	public void setSpotService(final SpotService service) {
		spotService = service;
	}

	/**
	 * @return the spot service
	 */
	public SpotService getSpotService() {
		return spotService;
	}
	
	/**
	 * @param service
	 *            the service to set
	 */
	public void setForecastService(final ForecastService service) {
		forecastService = service;
	}

	/**
	 * @return the forecast service
	 */
	public ForecastService getForecastService() {
		return forecastService;
	}
	
	public List<AreaDTO> getAreas() throws NeuralitoException {
		return spotService.getAreas();
			
	}
	
	public List<CountryDTO> getCountries() throws NeuralitoException {
		return new ArrayList<CountryDTO>();
		//TODO descomentar el metodo getcountries() cuando ya este implemetnado en el server
		//return spotService.getCountries();
	}
	
	public List<ZoneDTO> getZones(final Integer country) throws NeuralitoException {
		List<ZoneDTO> zones = new ArrayList<ZoneDTO>();
		if (this.getUser() != null)
			zones = spotService.getZones(country, this.getUser().getId());
		//TODO agregar el else con getzones(idcountry) si no esta logueado el usuario
		
		return zones;
	}
	
	public List<SpotDTO> getSpots(final Integer zone, final UserDTO user) throws NeuralitoException {
		List<SpotDTO> spots = new ArrayList<SpotDTO>();
		if (this.getUser() != null) {
			//TODO get spots descomentar la sig linea
			//spots = spotService.getSpots(zone, user.getId());
		}
		//TODO agregar el else con getpots(idzone) si no esta logueado el usuario

		return spots;
	}
	
//	public Map<String, List> getAreas() throws NeuralitoException {
//		final Map<String, List> result = new HashMap<String, List>();
//		
//		List<AreaDTO> areas = spotService.getAreas();
//		
//		
//		if (!areas.isEmpty()) {
//			result.put("areas", areas);
//			result.putAll(getCountries(areas.get(0).getId()));
//		}
//		return result;	
//	}
//	
//	public Map<String, List> getCountries(final Integer area) throws NeuralitoException {
//		final Map<String, List> result = new HashMap<String, List>();
//		
//		List<CountryDTO> countries = spotService.getCountries(area);
//		
//		
//		if (!countries.isEmpty()) {
//			result.put("countries", countries);
//			result.putAll(getZones(countries.get(0).getId(), this.getUser()));
//		}
//		return result;
//	}
//	
//	public Map<String, List> getZones(final Integer country, final UserDTO user) throws NeuralitoException {
//		final Map<String, List> result = new HashMap<String, List>();
//		List<ZoneDTO> zones = new ArrayList<ZoneDTO>();
//		if (user != null)
//			zones = spotService.getZones(country, user.getId());
//		//TODO agregar el else con getzones(idcountry) si no esta logueado el usuario
//		
//		if (!zones.isEmpty()) {
//			result.put("zones", zones);
//			result.putAll(getSpots(zones.get(0).getId(), this.getUser()));
//		}
//		return result;
//	}
//	
//	public Map<String, List> getSpots(final Integer zone, final UserDTO user) throws NeuralitoException {
//		final Map<String, List> result = new HashMap<String, List>();
//		List<SpotDTO> spots = new ArrayList<SpotDTO>();
//		if (user != null){
//			//TODO get spots descomentar la sig linea
//			//spots = spotService.getSpots(zone, user.getId());
//		}
//		//TODO agregar el else con getpots(idzone) si no esta logueado el usuario
//			
//		if (!spots.isEmpty())
//			result.put("spots", spots);
//
//		return result;
//	}
	
//	public Map<String, Vector> getAreas() {
//		final Area a1 = new Area("AN", "America del norte");
//		final Area a2 = new Area("AS", "America del sur");
//		final Area a3 = new Area("EU", "Europa");
//		final Area a4 = new Area("OC", "Oceania");
//
//		final Vector<Area> result = new Vector<Area>();
//		result.add(a1);
//		result.add(a2);
//		result.add(a3);
//		result.add(a4);
//
//		final Map<String, Vector> result3 = new HashMap<String, Vector>();
//		if (!result.isEmpty()) {
//			result3.put("areas", result);
//			result3.putAll(getCountries(result.elementAt(0).getId()));
//		}
//		return result3;
//	}

//	public Map<String, Vector> getCountries(final String area) {
//		final Country a1 = new Country("AR", "Argentina", "AS");
//		final Country a2 = new Country("VE", "Venezuela", "AS");
//		final Country a3 = new Country("HW", "Hawaii", "AN");
//		final Country a4 = new Country("FR", "Francia", "EU");
//
//		final Vector<Country> result = new Vector<Country>();
//		result.add(a1);
//		result.add(a2);
//		result.add(a3);
//		result.add(a4);
//
//		final Vector<Country> result2 = new Vector<Country>();
//		final Iterator<Country> i = result.iterator();
//		while (i.hasNext()) {
//			final Country c = i.next();
//			if (c.getParent().equals(area)) {
//				result2.add(c);
//			}
//		}
//
//		final Map<String, Vector> result3 = new HashMap<String, Vector>();
//		if (!result2.isEmpty()) {
//			result3.put("countries", result2);
//			result3.putAll(getZones(result2.elementAt(0).getId()));
//		}
//		return result3;
//	}

//	public Map<String, Vector> getZones(final String country) {
//		final Zone a1 = new Zone("1", "Mar del Plata Centro", "AR");
//		final Zone a2 = new Zone("2", "OAHU North Shore", "HW");
//		final Zone a3 = new Zone("3", "OAHU South Shore", "HW");
//		final Zone a4 = new Zone("4", "Mar del Plata Sur", "AR");
//		final Zone a5 = new Zone("5", "Marsella", "FR");
//
//		final Vector<Zone> result = new Vector<Zone>();
//		result.add(a1);
//		result.add(a2);
//		result.add(a3);
//		result.add(a4);
//		result.add(a5);
//
//		final Vector<Zone> result2 = new Vector<Zone>();
//
//		final Map<String, Vector> result3 = new HashMap<String, Vector>();
//		final Iterator<Zone> i = result.iterator();
//		while (i.hasNext()) {
//			final Zone c = i.next();
//			if (c.getParent().equals(country)) {
//				result2.add(c);
//			}
//		}
//
//		if (!result2.isEmpty()) {
//			result3.put("zones", result2);
//			result3.putAll(getSpots(result2.elementAt(0).getId()));
//		}
//		return result3;
//	}
//
//	public Map<String, Vector> getSpots(final String zone) {
//		final Spot a1 = new Spot("1", "Le pistole", "5");
//		final Spot a2 = new Spot("2", "Popular", "1");
//		final Spot a3 = new Spot("3", "La paloma", "4");
//		final Spot a4 = new Spot("4", "Chapa", "4");
//		final Spot a5 = new Spot("5", "Pipeline", "2");
//		final Spot a6 = new Spot("6", "Waimea", "2");
//		final Spot a7 = new Spot("7", "Diamond head", "3");
//		final Spot a8 = new Spot("8", "Ala moana", "3");
//		final Spot a9 = new Spot("9", "La maquinita", "4");
//		final Spot a10 = new Spot("10", "Mariano", "4");
//
//		final Vector<Spot> result = new Vector<Spot>();
//		result.add(a1);
//		result.add(a2);
//		result.add(a3);
//		result.add(a4);
//		result.add(a5);
//		result.add(a6);
//		result.add(a7);
//		result.add(a8);
//		result.add(a9);
//		result.add(a10);
//
//		final Vector<Spot> result2 = new Vector<Spot>();
//		final Iterator<Spot> i = result.iterator();
//		while (i.hasNext()) {
//			final Spot c = i.next();
//			if (c.getParent().equals(zone)) {
//				result2.add(c);
//			}
//		}
//		final Map<String, Vector> result3 = new HashMap<String, Vector>();
//		if (!result2.isEmpty()) {
//			result3.put("spots", result2);
//		}
//
//		return result3;
//	}

	public Integer addSpot(final String spotName, final String spotLongitude,
			final String spotLatitude, final String buoyLongitude, final String buoyLatitude, final Integer zoneId,
			final Integer countryId, final String zoneName,
			final boolean public_, final String timezone) throws NeuralitoException {
		
		logger.log(Level.INFO,"SpotServicesImpl - addSpot - Trying to add a new spot: '" + spotName + "'...");
		
		final SessionData sessionData = this.getSessionData();
		if (sessionData == null)
			throw new NeuralitoException(ErrorCode.USER_ID_INVALID);
		else {
			final double spotLongitudeNum = new Double(spotLongitude);
			final double spotLatitudeNum = new Double(spotLatitude);
			final double buoyLongitudeNum = new Double(buoyLongitude);
			final double buoyLatitudeNum = new Double(buoyLatitude);
			final Integer userId = new Integer(((UserDTO)sessionData.getUserDTO()).getId());
			Integer result = null;
			if (zoneName.trim().equals("")) {
				logger.log(Level.INFO,"SpotServicesImpl - addSpot - Adding only the spot: '" + spotName + "'...");
				result = spotService.addSpot(spotName, spotLongitudeNum,
						spotLatitudeNum, zoneId, userId, public_, timezone);
			} else {
				// result = spotService.addZoneAndSpot(zoneName, countryId,
				// spotName, longitudeNum, latitudeNum, userId, public_);
				logger.log(Level.INFO,"SpotServicesImpl - addSpot - Adding both zone: '"  + zoneName.trim() + "' and spot: '" + spotName + "'...");	
				result = spotService.addZoneAndSpot(zoneName.trim(), 1, spotName,
						spotLongitudeNum, spotLatitudeNum, userId, public_, timezone);
			}
			
			if (result != null) {
				//TODO activar la creacion de las coord de la boya de un spot
				//forecastService.createWW3Forecaster(result, new PointDTO(buoyLongitudeNum, buoyLatitudeNum));
				logger.log(Level.INFO,"SpotServicesImpl - addSpot - New spot '" + spotName + "' added");
			}
			
			return result;
		}
	}

	@Override
	public List<SpotDTO> getSpots(String spot) throws NeuralitoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ZoneDTO> getZones(String country) throws NeuralitoException {
		// TODO Auto-generated method stub
		return null;
	}

}