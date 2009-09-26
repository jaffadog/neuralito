package edu.unicen.surfforecaster.gwt.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.UserService;
import edu.unicen.surfforecaster.common.services.dto.UserDTO;
import edu.unicen.surfforecaster.common.services.dto.UserType;
import edu.unicen.surfforecaster.gwt.client.Area;
import edu.unicen.surfforecaster.gwt.client.Country;
import edu.unicen.surfforecaster.gwt.client.ForecastCommonServices;
import edu.unicen.surfforecaster.gwt.client.Spot;
import edu.unicen.surfforecaster.gwt.client.User;
import edu.unicen.surfforecaster.gwt.client.Zone;
import edu.unicen.surfforecaster.gwt.client.utils.SessionData;
import edu.unicen.surfforecaster.gwt.server.util.SpringGWTServlet;

public class ForecastCommonServicesImpl extends SpringGWTServlet implements
		ForecastCommonServices {
	/**
	 * Logger.
	 */
	Logger logger = Logger.getLogger(ForecastCommonServicesImpl.class);
	/**
	 * The user services interface.
	 */
	private UserService userService;

	/**
	 * Check if exists any user with the username and password passed as
	 * parameters
	 * 
	 * @param String
	 *            userName
	 * @param String
	 *            password
	 * @return User user if exist any user with that values or Null
	 */
	public User login(final String userName, final String password) {
		try {
			Thread.sleep(2500);
			try {
				final UserDTO userDTO = userService.loginUser(userName,
						password);
				final User user = new User();
				user.setId(userDTO.getId());
				user.setName(userDTO.getName());
				user.setLastName(userDTO.getLastName());
				user.setUserName(userDTO.getUsername());
				final HttpSession session = getSession();
				session.setMaxInactiveInterval(1200); // 120seg
				session
						.setAttribute("gwtForecast-UserName", user
								.getUserName());
				session.setAttribute("gwtForecast-UserType", user.getType());
				return user;
			} catch (final NeuralitoException e) {
				logger
						.log(
								Level.INFO,
								"User:'"
										+ userName
										+ "' could not login. User service failed to log the user.",
								e);
				return null;
			}
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public Area getArea() {
		return new Area("AN", "America del norte");
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
			sessionData.setUserType(new Integer(session.getAttribute(
					"gwtForecast-UserType").toString()));
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

	/**
	 * Removes all the session values stored in the current session
	 */
	public void closeSession() {
		final HttpSession session = getSession();

		session.removeAttribute("gwtForecast-UserName");
		session.removeAttribute("gwtForecast-UserType");
	}

	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(final UserService service) {
		userService = service;
	}

	/**
	 * @return the service
	 */
	public UserService getService() {
		return userService;
	}

	@Override
	public Integer addUser(String name, String lastname, String email,
			String username, String password, int type) {
		
		try {
			return userService.addUser(name, lastname, email, username, password, UserType.REGISTERED_USER);
		} catch (final NeuralitoException e) {
			logger.log(Level.INFO,"New User: '" + username + "' could not be added to the system.",e);
			return null;
		}
	}

}
