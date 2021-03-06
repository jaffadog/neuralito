package edu.unicen.surfforecaster.gwt.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.common.services.dto.UserDTO;
import edu.unicen.surfforecaster.gwt.client.dto.SpotGwtDTO;
import edu.unicen.surfforecaster.gwt.client.utils.SessionData;
import edu.unicen.surfforecaster.gwt.server.util.SpringGWTServlet;
import edu.unicen.surfforecaster.gwt.server.util.UserRoles;

@SuppressWarnings("serial")
public class ServicesImpl extends SpringGWTServlet {
	
	/**
	 * Logger.
	 */
	Logger logger = Logger.getLogger(ServicesImpl.class);
	
	/**
	 * The max inactive interval between an action in the current session
	 * A negative value sets this value as infinite
	 */
	protected final int MAX_INACTIVE_INTERVAL = 2000; //10 seconds
	
	/**
	 * Return a sessionData object with all the values stored in the current session
	 * 
	 * @return SessionData if not exists any session values
	 * @throws NeuralitoException if the session is expired or empty
	 */
	public SessionData getSessionData() throws NeuralitoException {
		final HttpSession session = getSession();
		if ((UserDTO)session.getAttribute("surfForecaster-User") == null) {
			logger.log(Level.INFO, "ServicesImpl - getSessionData - Session is expired or empty.");
			throw new NeuralitoException(ErrorCode.USER_SESSION_EMPTY_OR_EXPIRED);
		} else {
			final SessionData sessionData = SessionData.getInstance();
			sessionData.setUserDTO((UserDTO)session.getAttribute("surfForecaster-User"));
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
		final HttpServletRequest request = /*new ThreadLocal<HttpServletRequest>().get();*/getThreadLocalRequest();
		final HttpSession session = request.getSession();
		if (session.getMaxInactiveInterval() != MAX_INACTIVE_INTERVAL)
			session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL); 
		return session;
	}
	
	/**
	 * Returns null or a userDTO object depending the session status
	 * @return UserDTO, the logged in user
	 */
	public UserDTO getLoggedUser() {
		logger.log(Level.INFO, "ServicesImpl - getLoggedUser - Retrieving session data....");
		SessionData sessionData;
		try {
			sessionData = this.getSessionData();
			if (sessionData != null)
				return sessionData.getUserDTO();
			else
				return null;
		} catch (NeuralitoException e) {
			return null;
		}
	}
	
	/**
	 * Checks if the session is opened or not expired and if the user role has privilege to perform that action
	 * @param action represents the action to perform by the current user
	 * @return Boolean depending if the current user has access to the specific action
	 * @throws NeuralitoException Could return an USER_SESSION_EXPIRED exception or an USER_ROLE_INSUFFICIENT 
	 */
	public boolean hasAccessTo(String action) throws NeuralitoException {
		logger.log(Level.INFO, "ServicesImpl - hasAccessTo - Retrieving session data....");
		SessionData sessionData = this.getSessionData();
		if (sessionData != null)
			return UserRoles.getInstance().hasPermission(sessionData.getUserDTO().getType(), action);
		
		return false;
	}
	
	protected SpotGwtDTO getSpotGwtDTO(SpotDTO spotDTO) {
		return new SpotGwtDTO(spotDTO.getId(), spotDTO.getName(), spotDTO.getPoint(), spotDTO.getZone(), 
				spotDTO.getCountry(), spotDTO.getArea(), spotDTO.getUserId(), spotDTO.isPublik(), spotDTO.getTimeZone().getID());
	}
}
