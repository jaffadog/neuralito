package edu.unicen.surfforecaster.gwt.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import edu.unicen.surfforecaster.common.services.dto.UserDTO;
import edu.unicen.surfforecaster.gwt.client.utils.SessionData;
import edu.unicen.surfforecaster.gwt.server.util.SpringGWTServlet;

public class ServicesImpl extends SpringGWTServlet {
	
	/**
	 * Logger.
	 */
	Logger logger = Logger.getLogger(ServicesImpl.class);
	
	/**
	 * Return a sessionData object with all the values stored in the current
	 * session
	 * 
	 * @return SessionData or null if not exists any session values
	 */
	public SessionData getSessionData() {
		final HttpSession session = getSession();
		if ((UserDTO)session.getAttribute("gwtForecast-User") == null) {
			logger.log(Level.INFO, "ServicesImpl - getSessionData - The session is null or empty.");
			return null;
		} else {
			final SessionData sessionData = new SessionData();
			sessionData.setUserDTO((UserDTO)session.getAttribute("gwtForecast-User"));
			logger.log(Level.INFO, "ServicesImpl - getSessionData - The session is open for user:" + sessionData.getUserDTO().getUsername());
			return sessionData;
		}

	}

	/**
	 * Returns the currents user session in the browser
	 * 
	 * @return HttpSession session
	 */
	protected HttpSession getSession() {
		final HttpServletRequest request = getThreadLocalRequest();
		final HttpSession session = request.getSession();
		return session;
	}

	/**
	 * Removes all the session values stored in the current session
	 */
	public void closeSession() {
		final HttpSession session = getSession();
		logger.log(Level.INFO, "ServicesImpl - closeSession - Closing the current session...");
		session.removeAttribute("gwtForecast-User");
		logger.log(Level.INFO, "ServicesImpl - closeSession - Session closed.");
	}
	
	/**
	 * Returns null or a userDTO object depending the session status
	 * @return UserDTO, the logged in user
	 */
	public UserDTO getUser() {
		final SessionData sessionData = this.getSessionData();
		if (sessionData == null)
			return null;
		else
			return sessionData.getUserDTO();
	}
}
