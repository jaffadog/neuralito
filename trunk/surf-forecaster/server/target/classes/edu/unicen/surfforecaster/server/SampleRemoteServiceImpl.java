package edu.unicen.surfforecaster.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.unicen.surfforecaster.client.SampleRemoteService;

public class SampleRemoteServiceImpl extends RemoteServiceServlet implements
		SampleRemoteService {

	public String doComplimentMe() {
		return "esteban";
//		return RandomCompliment.get();
	}	
}
