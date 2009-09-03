package edu.unicen.surfforecaster.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.unicen.surfforecaster.common.services.dto.HelloDTO;


/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ForecastCommonServicesAsync {
  
	void testService(AsyncCallback<String> callback);
	void login(String userName, String password, AsyncCallback<User> callback);
	void getSessionData(AsyncCallback<SessionData> callback);
	void test2(AsyncCallback<HelloDTO> callback);
}
