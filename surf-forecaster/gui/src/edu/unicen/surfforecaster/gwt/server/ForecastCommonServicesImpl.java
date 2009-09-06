package edu.unicen.surfforecaster.gwt.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.unicen.surfforecaster.common.services.MyServiceI;
import edu.unicen.surfforecaster.common.services.dto.HelloDTO;
import edu.unicen.surfforecaster.gwt.client.ForecastCommonServices;
import edu.unicen.surfforecaster.gwt.client.SessionData;
import edu.unicen.surfforecaster.gwt.client.User;
import edu.unicen.surfforecaster.gwt.server.util.SpringGWTServlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ForecastCommonServicesImpl extends SpringGWTServlet implements ForecastCommonServices {

	private MyServiceI service;

	public String testService(){
		return "This is a service test";
	}
	
	/**
	 * Check if exists any user with the username and password passed as parameters
	 * @param String userName
	 * @param String password
	 * @return User user if exist any user with that values or Null
	 */
	public User login(String userName, String password){
		try {
			Thread.sleep(2500);
			if (userName.equals("admin") && password.equals("admin")){
				User user = new User();
				HttpSession session = this.getSession();
				session.setMaxInactiveInterval(120); //120seg
				session.setAttribute("gwtForecast-UserName", user.getUserName());
				session.setAttribute("gwtForecast-UserType", user.getType());
				return user;
			} else{
				return null;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * Return a sessionData object with all the values stored in the current session
	 * @return SessionData or null if not exists any session values
	 */
	public SessionData getSessionData() {
		HttpSession session = this.getSession();
		
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
	
	
	/**
	 * Returns the currents user session in the browser
	 * @return HttpSession session
	 */
	private HttpSession getSession(){
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession();
		return session;
	}
	
	/**
	 * Removes all the session values stored in the current session
	 */
	public void closeSession() {
		HttpSession session = this.getSession();
		
		session.removeAttribute("gwtForecast-UserName");
		session.removeAttribute("gwtForecast-UserType");
	}
	/**
	 * @param service the service to set
	 */
	public void setService(MyServiceI service) {
		this.service = service;
	}

	/**
	 * @return the service
	 */
	public MyServiceI getService() {
		return service;
	}
	public HelloDTO test2(){
		return service.sayHello();
	};

}
