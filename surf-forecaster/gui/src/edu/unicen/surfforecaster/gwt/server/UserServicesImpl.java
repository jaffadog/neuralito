package edu.unicen.surfforecaster.gwt.server;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.UserService;
import edu.unicen.surfforecaster.common.services.dto.UserDTO;
import edu.unicen.surfforecaster.common.services.dto.UserType;
import edu.unicen.surfforecaster.gwt.client.UserServices;
import edu.unicen.surfforecaster.gwt.client.utils.SessionData;

@SuppressWarnings("serial")
public class UserServicesImpl extends ServicesImpl implements UserServices {

	/**
	 * The user services interface.
	 */
	private UserService userService;
	
	/**
	 * @param service the service to set
	 */
	public void setUserService(final UserService service) {
		userService = service;
	}

	/**
	 * @return the user service
	 */
	public UserService getUserService() {
		return userService;
	}
	
	/**
	 * Check if exists any user with the username and password passed as parameters
	 * 
	 * @param String userName
	 * @param String password
	 * @return User user if exist any user with that values or Null
	 */
	public UserDTO login(final String userName, final String password) throws NeuralitoException{
		logger.log(Level.INFO,"UserServicesImpl - login - Finding User: '" + userName + "'...");
		UserDTO userDTO = userService.loginUser(userName, password);
		//Set the user for this session
		SessionData.getInstance().clear();
		final HttpSession session = this.getSession();
		session.setAttribute("surfForecaster-User", userDTO);
		session.setMaxInactiveInterval(super.MAX_INACTIVE_INTERVAL);
		
		logger.log(Level.INFO,"UserServicesImpl - login - User: '" + userDTO.getUsername() + "' retrieved.");
		return userDTO;
	}
	
	/**
	 * Removes all the session values stored in the current session
	 * Remove the username cookie
	 */
	public void closeSession() {
		final HttpSession session = getSession();
		logger.log(Level.INFO, "ServicesImpl - closeSession - Closing the current session...");
		session.removeAttribute("surfForecaster-User");
		SessionData.getInstance().clear();
		logger.log(Level.INFO, "ServicesImpl - closeSession - Session closed.");
	}

	public Integer addUser(String name, String lastname, String email,
			String username, String password, UserType userType) throws NeuralitoException {
		
		logger.log(Level.INFO,"UserServicesImpl - addUser - Adding user with username: '" + username + "'...");
		Integer result = userService.addUser(name, lastname, email, username, password, userType);
		logger.log(Level.INFO,"UserServicesImpl - addUser - User with username: '" + username + "' successfully added.");
		
		return result;
	}

}
