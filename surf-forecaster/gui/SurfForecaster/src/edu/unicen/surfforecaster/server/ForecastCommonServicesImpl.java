package edu.unicen.surfforecaster.server;

import edu.unicen.surfforecaster.client.ForecastCommonServices;
import edu.unicen.surfforecaster.client.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ForecastCommonServicesImpl extends RemoteServiceServlet implements ForecastCommonServices {

	public String testService(){
		return "This is a service test";
	}
	
	public User login(String userName, String password){
		if (userName.equals("admin") && password.equals("admin"))
			return new User();
		else
			return null;
	}
}
