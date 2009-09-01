package edu.unicen.surfforecaster.server;

import edu.unicen.surfforecaster.client.ForecastCommonServices;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ForecastCommonServicesImpl extends RemoteServiceServlet implements ForecastCommonServices {
	
	public String testService(){
		return "This is a service test";
	}
}
