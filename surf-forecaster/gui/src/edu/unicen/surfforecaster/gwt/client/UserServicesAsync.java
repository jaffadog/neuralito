package edu.unicen.surfforecaster.gwt.client;

import edu.unicen.surfforecaster.common.services.dto.UserDTO;
import edu.unicen.surfforecaster.common.services.dto.UserType;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServicesAsync {
	void login(String userName, String password, AsyncCallback<UserDTO> callback);
	
	void addUser(String name, String lastname, String email, String username, String password, UserType userType, AsyncCallback<Integer> callback);
	
	void hasAccessTo(String action, AsyncCallback<Boolean> callback);

	void closeSession(AsyncCallback<Void> callback);
	
	void getLoggedUser(AsyncCallback<UserDTO> callback);

}
