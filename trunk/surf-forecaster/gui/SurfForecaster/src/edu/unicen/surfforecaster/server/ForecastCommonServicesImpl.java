package edu.unicen.surfforecaster.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.unicen.surfforecaster.client.ForecastCommonServices;
import edu.unicen.surfforecaster.client.SessionData;
import edu.unicen.surfforecaster.client.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ForecastCommonServicesImpl extends RemoteServiceServlet implements ForecastCommonServices {

	public String testService(){
		return "This is a service test";
	}
	
	public User login(String userName, String password){
		if (userName.equals("admin") && password.equals("admin")){
			User user = new User();
			HttpServletRequest request = this.getThreadLocalRequest();
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(60); //60s
			session.setAttribute("gwtForecaster-Username", user.getUserName());
			session.setAttribute("gwtForecaster-UserType", user.getType());
			return user;
		}
		else{
			return null;
		}
	}
	
	public SessionData getSessionData() {
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession();
		
		SessionData sessionData = new SessionData();
		sessionData.setUserName((String)session.getAttribute("gwtForecaster-Username"));
		sessionData.setUserType(new Integer((String)session.getAttribute("gwtForecaster-UserType")));
		return sessionData;
	}
}
