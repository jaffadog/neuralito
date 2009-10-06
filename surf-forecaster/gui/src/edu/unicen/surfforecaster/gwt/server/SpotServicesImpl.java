package edu.unicen.surfforecaster.gwt.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.SpotService;
import edu.unicen.surfforecaster.gwt.client.Area;
import edu.unicen.surfforecaster.gwt.client.Country;
import edu.unicen.surfforecaster.gwt.client.Spot;
import edu.unicen.surfforecaster.gwt.client.SpotServices;
import edu.unicen.surfforecaster.gwt.client.Zone;
import edu.unicen.surfforecaster.gwt.client.utils.SessionData;
import edu.unicen.surfforecaster.gwt.server.util.SpringGWTServlet;

public class SpotServicesImpl extends SpringGWTServlet implements SpotServices {
	/**
	 * Logger.
	 */
	Logger logger = Logger.getLogger(SpotServicesImpl.class);

	UserServicesImpl userService = new UserServicesImpl();
	
	private SpotService spotService;

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
	 * Return a sessionData object with all the values stored in the current
	 * session
	 * 
	 * @return SessionData or null if not exists any session values
	 */
	public SessionData getSessionData() {
		final HttpSession session = getSession();

		if ((String) session.getAttribute("gwtForecast-UserName") == null
				|| ((String) session.getAttribute("gwtForecast-UserName"))
						.equals("")) {
			System.out
					.println("the session is null or empty, this is the result after request for username: "
							+ (String) session
									.getAttribute("gwtForecast-UserName"));
			return null;
		} else {

			final SessionData sessionData = new SessionData();
			sessionData.setUserName(session
					.getAttribute("gwtForecast-UserName").toString());
			sessionData.setUserType(session
					.getAttribute("gwtForecast-UserType").toString());
			sessionData.setUserId(session.getAttribute("gwtForecast-UserId")
					.toString());
			return sessionData;
		}

	}

	/**
	 * Returns the currents user session in the browser
	 * 
	 * @return HttpSession session
	 */
	private HttpSession getSession() {
		final HttpServletRequest request = getThreadLocalRequest();
		final HttpSession session = request.getSession();
		return session;
	}

	public Map<String, Vector> getAreas() {
		final Area a1 = new Area("AN", "America del norte");
		final Area a2 = new Area("AS", "America del sur");
		final Area a3 = new Area("EU", "Europa");
		final Area a4 = new Area("OC", "Oceania");

		final Vector<Area> result = new Vector<Area>();
		result.add(a1);
		result.add(a2);
		result.add(a3);
		result.add(a4);

		final Map<String, Vector> result3 = new HashMap<String, Vector>();
		if (!result.isEmpty()) {
			result3.put("areas", result);
			result3.putAll(getCountries(result.elementAt(0).getId()));
		}
		return result3;
	}

	public Map<String, Vector> getCountries(final String area) {
		final Country a1 = new Country("AR", "Argentina", "AS");
		final Country a2 = new Country("VE", "Venezuela", "AS");
		final Country a3 = new Country("HW", "Hawaii", "AN");
		final Country a4 = new Country("FR", "Francia", "EU");

		final Vector<Country> result = new Vector<Country>();
		result.add(a1);
		result.add(a2);
		result.add(a3);
		result.add(a4);

		final Vector<Country> result2 = new Vector<Country>();
		final Iterator<Country> i = result.iterator();
		while (i.hasNext()) {
			final Country c = i.next();
			if (c.getParent().equals(area)) {
				result2.add(c);
			}
		}

		final Map<String, Vector> result3 = new HashMap<String, Vector>();
		if (!result2.isEmpty()) {
			result3.put("countries", result2);
			result3.putAll(getZones(result2.elementAt(0).getId()));
		}
		return result3;
	}

	public Map<String, Vector> getZones(final String country) {
		final Zone a1 = new Zone("1", "Mar del Plata Centro", "AR");
		final Zone a2 = new Zone("2", "OAHU North Shore", "HW");
		final Zone a3 = new Zone("3", "OAHU South Shore", "HW");
		final Zone a4 = new Zone("4", "Mar del Plata Sur", "AR");
		final Zone a5 = new Zone("5", "Marsella", "FR");

		final Vector<Zone> result = new Vector<Zone>();
		result.add(a1);
		result.add(a2);
		result.add(a3);
		result.add(a4);
		result.add(a5);

		final Vector<Zone> result2 = new Vector<Zone>();

		final Map<String, Vector> result3 = new HashMap<String, Vector>();
		final Iterator<Zone> i = result.iterator();
		while (i.hasNext()) {
			final Zone c = i.next();
			if (c.getParent().equals(country)) {
				result2.add(c);
			}
		}

		if (!result2.isEmpty()) {
			result3.put("zones", result2);
			result3.putAll(getSpots(result2.elementAt(0).getId()));
		}
		return result3;
	}

	public Map<String, Vector> getSpots(final String zone) {
		final Spot a1 = new Spot("1", "Le pistole", "5");
		final Spot a2 = new Spot("2", "Popular", "1");
		final Spot a3 = new Spot("3", "La paloma", "4");
		final Spot a4 = new Spot("4", "Chapa", "4");
		final Spot a5 = new Spot("5", "Pipeline", "2");
		final Spot a6 = new Spot("6", "Waimea", "2");
		final Spot a7 = new Spot("7", "Diamond head", "3");
		final Spot a8 = new Spot("8", "Ala moana", "3");
		final Spot a9 = new Spot("9", "La maquinita", "4");
		final Spot a10 = new Spot("10", "Mariano", "4");

		final Vector<Spot> result = new Vector<Spot>();
		result.add(a1);
		result.add(a2);
		result.add(a3);
		result.add(a4);
		result.add(a5);
		result.add(a6);
		result.add(a7);
		result.add(a8);
		result.add(a9);
		result.add(a10);

		final Vector<Spot> result2 = new Vector<Spot>();
		final Iterator<Spot> i = result.iterator();
		while (i.hasNext()) {
			final Spot c = i.next();
			if (c.getParent().equals(zone)) {
				result2.add(c);
			}
		}
		final Map<String, Vector> result3 = new HashMap<String, Vector>();
		if (!result2.isEmpty()) {
			result3.put("spots", result2);
		}

		return result3;
	}

	public Integer addSpot(final String spotName, final String longitude,
			final String latitude, final Integer zoneId,
			final Integer countryId, final String zoneName,
			final boolean public_) throws NeuralitoException {
		final SessionData sessionData = getSessionData();
		if (sessionData == null)
			throw new NeuralitoException(ErrorCode.USER_ID_INVALID);
		else {
			final double longitudeNum = new Double(longitude);
			final double latitudeNum = new Double(latitude);
			final Integer userId = new Integer(sessionData.getUserId());
			Integer result = null;
			if (zoneName.trim().equals("")) {
				result = spotService.addSpot(spotName, longitudeNum,
						latitudeNum, zoneId, userId, public_, "ATC");
			} else {
				// result = spotService.addZoneAndSpot(zoneName, countryId,
				// spotName, longitudeNum, latitudeNum, userId, public_);
				result = spotService.addZoneAndSpot(zoneName, 1, spotName,
						longitudeNum, latitudeNum, userId, public_, "ATC");
				System.out.println(public_);
			}
			return result;
		}
	}

}
