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
	
	public User login(String userName, String password){
		if (userName.equals("admin") && password.equals("admin")){
			User user = new User();
			HttpServletRequest request = this.getThreadLocalRequest();
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(60); //60s
			session.setAttribute("gwtForecaster-Username", user.getUserName());
			session.setAttribute("gwtForecaster-UserType", user.getType());
			System.out.println(service.sayHello());
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

	/* (non-Javadoc)
	 * @see edu.unicen.surfforecaster.gwt.client.ForecastCommonServices#test()
	 */
	@Override
	public HelloDTO test2() {
		// TODO Auto-generated method stub
		return service.sayHello();
	}
}
