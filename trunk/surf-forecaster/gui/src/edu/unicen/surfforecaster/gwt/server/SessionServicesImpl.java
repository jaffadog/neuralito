package edu.unicen.surfforecaster.gwt.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.unicen.surfforecaster.gwt.client.utils.SessionData;
import edu.unicen.surfforecaster.gwt.server.util.SpringGWTServlet;

public class SessionServicesImpl extends SpringGWTServlet {
	
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

		session.removeAttribute("gwtForecast-UserName");
		session.removeAttribute("gwtForecast-UserType");
		session.removeAttribute("gwtForecast-UserId");
	}
}
