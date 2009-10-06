package edu.unicen.surfforecaster.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.unicen.surfforecaster.common.services.dto.UserDTO;
import edu.unicen.surfforecaster.gwt.client.utils.SessionData;

public interface UserServicesAsync {
	
	void login(String userName, String password, AsyncCallback<UserDTO> callback);
	
	void addUser(String name, String lastname, String email, String username, String password, int type, AsyncCallback<Integer> callback);

	void getSessionData(AsyncCallback<SessionData> callback);

	void closeSession(AsyncCallback<Void> callback);
}
