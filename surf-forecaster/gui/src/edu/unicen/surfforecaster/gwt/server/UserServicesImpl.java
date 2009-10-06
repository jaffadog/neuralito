package edu.unicen.surfforecaster.gwt.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.UserService;
import edu.unicen.surfforecaster.common.services.dto.UserDTO;
import edu.unicen.surfforecaster.common.services.dto.UserType;
import edu.unicen.surfforecaster.gwt.client.UserServices;
import edu.unicen.surfforecaster.gwt.client.utils.SessionData;
import edu.unicen.surfforecaster.gwt.server.util.SpringGWTServlet;

public class UserServicesImpl extends SpringGWTServlet implements UserServices {
	/**
	 * Logger.
	 */
	Logger logger = Logger.getLogger(UserServicesImpl.class);
	/**
	 * The user services interface.
	 */
	private UserService userService;
	
	/**
	 * @param service the service to set
	 */
	public void setUserService(final UserService service) {
		userService = service;
	}

	/**
	 * @return the user service
	 */
	public UserService getUserService() {
		return userService;
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
				|| ((String) session.getAttribute("gwtForecast-UserName")).equals("")) {
			System.out.println("the session is null or empty, this is the result after request for username: "
							+ (String) session.getAttribute("gwtForecast-UserName"));
			return null;
		} else {

			final SessionData sessionData = new SessionData();
			sessionData.setUserName(session.getAttribute("gwtForecast-UserName").toString());
			sessionData.setUserType(session.getAttribute("gwtForecast-UserType").toString());
			sessionData.setUserId(session.getAttribute("gwtForecast-UserId").toString());
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
		session.removeAttribute("gwtForecast-UserId");
	}

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
	public UserDTO login(final String userName, final String password) throws NeuralitoException{
		logger.log(Level.INFO,"ForecastCommonServicesImpl - login - Finding User: '" + userName + "'.");
		final UserDTO userDTO = userService.loginUser(userName, password);
		final HttpSession session = getSession();
		session.setMaxInactiveInterval(1200); // 120seg
		session.setAttribute("gwtForecast-UserName", userDTO.getUsername());
		session.setAttribute("gwtForecast-UserType", userDTO.getType());
		session.setAttribute("gwtForecast-UserId", userDTO.getId());
		logger.log(Level.INFO,"ForecastCommonServicesImpl - login - User: '" + userDTO.getUsername() + "' retrieved.");
		return userDTO;
	}

	public Integer addUser(String name, String lastname, String email,
			String username, String password, int type) throws NeuralitoException {
		
		logger.log(Level.INFO,"ForecastCommonServicesImpl - addUser - Adding user with username: '" + username + "'.");
		Integer result = userService.addUser(name, lastname, email, username, password, UserType.REGISTERED_USER);
		logger.log(Level.INFO,"ForecastCommonServicesImpl - addUser - User with username: '" + username + "' successfully added.");
		
		return result;
	}

}