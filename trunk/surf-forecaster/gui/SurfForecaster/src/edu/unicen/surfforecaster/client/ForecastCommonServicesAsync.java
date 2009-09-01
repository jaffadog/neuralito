package edu.unicen.surfforecaster.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ForecastCommonServicesAsync {
  
	void testService(AsyncCallback<String> callback);
}
