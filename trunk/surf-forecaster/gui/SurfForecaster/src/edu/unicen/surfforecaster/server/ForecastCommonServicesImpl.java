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
			session.setMaxInactiveInterval(60); //60seg
			session.setAttribute("gwtForecast-UserName", user.getUserName());
			session.setAttribute("gwtForecast-UserType", user.getType());
			return user;
		} else{
			return null;
		}
	}
	
	public SessionData getSessionData() {
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession();
		
		if ((String)session.getAttribute("gwtForecast-UserName") == null || ((String)session.getAttribute("gwtForecast-UserName")).equals("")) {
			System.out.println("the session is null or empty, this is the result after request for username: " + (String)session.getAttribute("gwtForecast-UserName"));
			return null;
		} else {
			
			SessionData sessionData = new SessionData();
			sessionData.setUserName(session.getAttribute("gwtForecast-UserName").toString());
			sessionData.setUserType(new Integer(session.getAttribute("gwtForecast-UserType").toString()));
			return sessionData;
		}
		
	}
	
	public void closeSession() {
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession();
		
		session.removeAttribute("gwtForecast-UserName");
		session.removeAttribute("gwtForecast-UserType");
	}
}
